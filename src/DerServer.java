import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashSet;

public class DerServer {
    Server server;
    HashSet<Held> loggedIn = new HashSet<>();
    HashSet<String> namen = new HashSet<>();

    public DerServer() {
        server = new Server() {
            protected Connection newConnection() {
                return new HeldConnection();
            }
        };

        Network.register(server);

        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection c) {
                HeldConnection connection = (HeldConnection) c;
                loggedIn.remove(connection.held);
                Network.RemoveHeld removeHeld = new Network.RemoveHeld();
                removeHeld.name = connection.held.name;
                server.sendToAllTCP(removeHeld);
            }

            @Override
            public void received(Connection c, Object object) {
                HeldConnection connection = (HeldConnection) c;
                Held held = connection.held;

                if (object instanceof Network.Login) {
                    String name = ((Network.Login) object).name;
                    if (!isValid(name)) {
                        c.close();
                        return;
                    }

                    for (Held other : loggedIn) {
                        if (other.name.equals(name)) {
                            c.close();
                            return;
                        }
                    }

                    held = loadHeld(name);
                    loggedIn(connection, held);

                    Network.Login msg = (Network.Login) object;

                    Network.AddHeld msgZuruck = new Network.AddHeld();
                    msgZuruck.held = msg.held;
                    server.sendToAllTCP(msgZuruck);

                } else if (object instanceof Network.UpdateHeldVonServer) {
                    Network.UpdateHeldVonServer msg = (Network.UpdateHeldVonServer) object;
                    Network.UpdateHeldZuServer zuruckMsg = new Network.UpdateHeldZuServer();
                    zuruckMsg.held = msg.held;
                    server.sendToAllTCP(zuruckMsg);
                } else if (object instanceof Network.RemoveHeld) {
                    server.sendToAllTCP((Network.RemoveHeld) object);
                }
            }

            private boolean isValid(String value) {
                if (value == null) return false;
                value = value.trim();
                return value.length() != 0;
            }
        });

        try {
            server.bind(54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
    }

    private Held loadHeld(String name) {
        Held held = new Held();
        held.x = 0;
        held.y = 0;
        held.name = name;
        return held;
    }

    void loggedIn(HeldConnection c, Held held) {
        c.held = held;
        for (Held other : loggedIn) {
            Network.AddHeld addHeld = new Network.AddHeld();
            addHeld.held = other;
            server.sendToAllTCP(addHeld);
        }
        loggedIn.add(held);
        Network.AddHeld addHeld = new Network.AddHeld();
        addHeld.held = held;
        server.sendToAllTCP(addHeld);
    }

    static class HeldConnection extends Connection {
        public Held held;
    }
}
