import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

class DerServer {
    private final Server server;
    private final HashMap<Integer, String> idToString = new HashMap<>();
    private int level = 0;

    DerServer() {
        server = new Server(20000, 20000);

        Network.register(server);

        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection c) {
                Network.RemoveHeld removeHeld = new Network.RemoveHeld();
                removeHeld.name = idToString.get(c.getID());
                server.sendToAllUDP(removeHeld);
            }

            @Override
            public void received(Connection c, Object object) {

                if (object instanceof Network.Login) {
                    String name = ((Network.Login) object).name;
                    if (!isValid(name)) {
                        server.sendToTCP(c.getID(), new Network.NameSchonVergeben());
                        c.close();
                        return;
                    }

                    idToString.put(c.getID(), name);

                    Network.Login msg = (Network.Login) object;

                    Network.AddHeld msgZuruck = new Network.AddHeld();
                    msgZuruck.held = msg.held;
                    server.sendToAllUDP(msgZuruck);

                    Network.LevelLaden levelLadenMsg = new Network.LevelLaden();
                    levelLadenMsg.level = level;
                    server.sendToUDP(c.getID(), levelLadenMsg);

                    if (c.getID() != 1) {
                        Network.AddDinge addDingeMsg = new Network.AddDinge();
                        addDingeMsg.dinge = Assets.dinge;
                        server.sendToUDP(c.getID(), addDingeMsg);

                        Network.AddFelder addFelderMsg = new Network.AddFelder();
                        addFelderMsg.felder = Dungeon.felder;
                        server.sendToUDP(c.getID(), addFelderMsg);
                    }

                } else if (object instanceof Network.UpdateHeldZuServer) {
                    Network.UpdateHeldZuServer msg = (Network.UpdateHeldZuServer) object;
                    Network.UpdateHeldVonServer zuruckMsg = new Network.UpdateHeldVonServer();
                    zuruckMsg.held = msg.held;
                    server.sendToAllUDP(zuruckMsg);

                } else if (object instanceof Network.UpdateDing) {
                    server.sendToAllUDP(object);

                } else if (object instanceof Network.UpdateFeld) {
                    server.sendToAllUDP(object);

                } else if (object instanceof Network.NaechstesLevelVonClient) {
                    Network.NaechstesLevelVonServer msg = new Network.NaechstesLevelVonServer();
                    level++;
                    msg.level = level;
                    server.sendToAllTCP(msg);

                } else if (object instanceof Network.MonsterGetoetet) {
                    server.sendToAllTCP(object);

                } else if (object instanceof Network.SpielBeenden) {
                    server.sendToAllTCP(object);

                } else if (object instanceof Network.SpielNeustarten) {
                    level = 0;
                    server.sendToAllTCP(object);

                } else if (object instanceof Network.Message) {
                    server.sendToAllTCP(object);
                }
            }

            private boolean isValid(String value) {
                if (value == null) return false;
                value = value.trim();
                for (String name : idToString.values()) {
                    if(name.equals(value)) return false;
                }
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
}
