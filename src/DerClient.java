import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;

class DerClient {
    private final HashMap<String, Held> helden = Assets.helden;
    private Dungeon dungeon;
    private final Client client;
    private final String name;
    private Stack<Ding> tempDinge = new Stack<>();
    private Feld[][] tempFelder;
    private int level;
    TextFeld textFeld;

    DerClient(String name) {
        this.name = name;

        client = new Client(20000, 20000);
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
            public void disconnected(Connection connection) {
                Dialoge.serverGeleftet();
            }

            @Override
            public void received(Connection connection, Object object) {
                if (object instanceof Network.AddHeld) {
                    Network.AddHeld msg = (Network.AddHeld) object;
                    helden.putIfAbsent(msg.held.name, msg.held);
                    sendHeld();

                } else if (object instanceof Network.UpdateHeldVonServer) {
                    Network.UpdateHeldVonServer msg = (Network.UpdateHeldVonServer) object;
                    if (!msg.held.name.equals(name)) {
                        helden.put(msg.held.name, msg.held);
                    }

                } else if (object instanceof Network.RemoveHeld) {
                    Network.RemoveHeld msg = (Network.RemoveHeld) object;
                    helden.remove(msg.name);

                } else if (object instanceof Network.UpdateDing) {
                    Network.UpdateDing msg = (Network.UpdateDing) object;
                    Assets.setDing(msg.ding);

                } else if (object instanceof Network.AddDinge) {
                    Network.AddDinge msg = (Network.AddDinge) object;
                    tempDinge = msg.dinge;

                } else if (object instanceof Network.AddFelder) {
                    Network.AddFelder msg = (Network.AddFelder) object;
                    tempFelder = msg.felder;

                } else if (object instanceof Network.UpdateFeld) {
                    Network.UpdateFeld msg = (Network.UpdateFeld) object;
                    if (dungeon != null) dungeon.setFeld(msg.feld);

                } else if (object instanceof Network.LevelLaden) {
                    Network.LevelLaden msg = (Network.LevelLaden) object;
                    level = msg.level;

                } else if (object instanceof Network.NaechstesLevelVonServer) {
                    Network.NaechstesLevelVonServer msg = (Network.NaechstesLevelVonServer) object;
                    dungeon.levelStarten(msg.level);

                } else if (object instanceof Network.MonsterGetoetet) {
                    Network.MonsterGetoetet msg = (Network.MonsterGetoetet) object;
                    dungeon.held.monsterGetoetetImLevel = msg.monsterGetoetet;

                } else if (object instanceof Network.SpielBeenden) {
                    Dialoge.beenden(dungeon.held, dungeon);

                } else if (object instanceof Network.SpielNeustarten) {
                    HighscoreZeugs.zeitStarten();
                    dungeon.levelStarten(0);

                } else if (object instanceof Network.Message) {
                    Network.Message msg = (Network.Message) object;
                    textFeld.empfangen(msg.message, msg.name);

                }else if(object instanceof Network.NameSchonVergeben) {
                    Dialoge.nameSchonVergeben();
                }


                if (dungeon != null) dungeon.paint();
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
        client.sendUDP(msg);
    }

    void sendDingUndFeldUpdate(Ding ding, Feld feld) {
        if (ding != null) {
            Network.UpdateDing msg = new Network.UpdateDing();
            msg.ding = ding;
            client.sendUDP(msg);
        }

        if (feld != null) {
            Network.UpdateFeld msg2 = new Network.UpdateFeld();
            msg2.feld = feld;
            client.sendUDP(msg2);
        }
    }

    void dingeVomServerEinbauen() {
        if (!tempDinge.empty()) {
            Assets.dinge = tempDinge;
        }
        if (tempFelder != null) {
            Dungeon.felder = tempFelder;
        }
    }

    int getLevelAmAnfang() {
        return level;
    }

    void naechtesLevel() {
        Network.NaechstesLevelVonClient msg = new Network.NaechstesLevelVonClient();
        client.sendTCP(msg);
    }

    void monsterToeten() {
        Network.MonsterGetoetet msg = new Network.MonsterGetoetet();
        msg.monsterGetoetet = dungeon.held.monsterGetoetetImLevel;
        client.sendTCP(msg);
    }

    void spielBeenden() {
        Network.SpielBeenden msg = new Network.SpielBeenden();
        client.sendTCP(msg);
    }

    void neuStarten() {
        Network.SpielNeustarten msg = new Network.SpielNeustarten();
        client.sendTCP(msg);
    }

    void nachrichtSenden(String text) {
        Network.Message msg = new Network.Message();
        msg.message = text;
        msg.name = name;
        client.sendTCP(msg);
    }
}
