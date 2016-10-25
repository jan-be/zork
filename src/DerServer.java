import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashSet;

public class DerServer {
    Server server;
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
                Network.RemoveHeld removeHeld = new Network.RemoveHeld();
                removeHeld.name = connection.held.name;
                server.sendToAllTCP(removeHeld);
            }

            @Override
            public void received(Connection c, Object object) {
                if (object instanceof Network.Login) {
                    String name = ((Network.Login) object).name;
                    if (!isValid(name)) {
                        c.close();
                        return;
                    }

                    Network.Login msg = (Network.Login) object;

                    Network.AddHeld msgZuruck = new Network.AddHeld();
                    msgZuruck.held = msg.held;
                    server.sendToAllTCP(msgZuruck);

                } else if (object instanceof Network.UpdateHeldZuServer) {
                    Network.UpdateHeldZuServer msg = (Network.UpdateHeldZuServer) object;
                    Network.UpdateHeldVonServer zuruckMsg = new Network.UpdateHeldVonServer();
                    zuruckMsg.held = msg.held;
                    server.sendToAllTCP(zuruckMsg);
                } else if (object instanceof Network.RemoveHeld) {
                    server.sendToAllTCP(object);
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

    static class HeldConnection extends Connection {
        Held held;
    }
}
