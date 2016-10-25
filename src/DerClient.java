import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

public class DerClient {
    Stack<Ding> dinge = Assets.dinge;
    HashMap<String, Held> helden = Assets.helden;
    Dungeon dungeon;
    Client client;
    String name;

    public DerClient(String name) {
        this.name = name;

        client = new Client();
        client.start();

        Network.register(client);

        client.addListener(new Listener.ThreadedListener(new Listener() {
            @Override
            public void connected(Connection connection) {
                Network.Login login = new Network.Login();
                login.name = name;
                login.held = helden.get(name);
                client.sendTCP(login);
            }

            @Override
            public void received(Connection connection, Object object) {
                System.out.println("was received " + object.getClass().getName());
                if (object instanceof Network.AddHeld) {
                    Network.AddHeld msg = (Network.AddHeld) object;
                    helden.putIfAbsent(msg.held.name, msg.held);
                } else if (object instanceof Network.UpdateHeldVonServer) {
                    Network.UpdateHeldVonServer msg = (Network.UpdateHeldVonServer) object;
                    if (!msg.held.name.equals(name)) {
                        helden.put(msg.held.name, msg.held);
                    }
                } else if (object instanceof Network.RemoveHeld) {
                    Network.RemoveHeld msg = (Network.RemoveHeld) object;
                    helden.remove(msg.name);
                }
                if (dungeon != null) {
                    dungeon.paint();
                }
            }
        }));

        try {
            client.connect(5000, Main.ipAdresse, 54555, 54777);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dungeonInit(Dungeon dungeon) {
        this.dungeon = dungeon;

    }

    void sendHeld() {
        Network.UpdateHeldZuServer msg = new Network.UpdateHeldZuServer();
        msg.held = helden.get(name);
        client.sendTCP(msg);
    }
}
