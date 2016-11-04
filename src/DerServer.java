import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.HashMap;

class DerServer {
    private final Server server;
    private final HashMap<Integer, String> idToString = new HashMap<>();

    DerServer() {
        server = new Server();

        Network.register(server);

        //noinspection BooleanMethodIsAlwaysInverted,BooleanMethodIsAlwaysInverted
        server.addListener(new Listener() {
            @Override
            public void disconnected(Connection c) {
                Network.RemoveHeld removeHeld = new Network.RemoveHeld();
                removeHeld.name = idToString.get(c.getID());
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

                    idToString.put(c.getID(), name);

                    Network.Login msg = (Network.Login) object;

                    Network.AddHeld msgZuruck = new Network.AddHeld();
                    msgZuruck.held = msg.held;
                    server.sendToAllTCP(msgZuruck);

                    if (c.getID() != 1) {
                        Network.AddDingeVonServer msg2 = new Network.AddDingeVonServer();
                        msg2.dinge = Assets.dinge;
                        server.sendToTCP(c.getID(), msg2);
                    }

                } else if (object instanceof Network.UpdateHeldZuServer) {
                    Network.UpdateHeldZuServer msg = (Network.UpdateHeldZuServer) object;
                    Network.UpdateHeldVonServer zuruckMsg = new Network.UpdateHeldVonServer();
                    zuruckMsg.held = msg.held;
                    server.sendToAllTCP(zuruckMsg);

                } else if (object instanceof Network.UpdateDingZuServer) {
                    Network.UpdateDingZuServer msg = (Network.UpdateDingZuServer) object;
                    Network.UpdateDingVonServer msgZuruck = new Network.UpdateDingVonServer();
                    msgZuruck.ding = msg.ding;
                    server.sendToAllTCP(msgZuruck);

                }
            }

            @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
}
