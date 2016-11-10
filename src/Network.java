import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.Stack;

class Network {
    static void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Held.class);
        kryo.register(Login.class);
        kryo.register(UpdateHeldVonServer.class);
        kryo.register(UpdateHeldZuServer.class);
        kryo.register(RemoveHeld.class);
        kryo.register(AddHeld.class);
        kryo.register(Ding.class);
        kryo.register(UpdateDing.class);
        kryo.register(Stack.class);
        kryo.register(AddDinge.class);
        kryo.register(AddFelder.class);
        kryo.register(Feld[][].class);
        kryo.register(Feld[].class);
        kryo.register(Feld.class);
        kryo.register(UpdateFeld.class);
        kryo.register(LevelLaden.class);
        kryo.register(NaechstesLevelVonClient.class);
        kryo.register(NaechstesLevelVonServer.class);
        kryo.register(MonsterGetoetet.class);
        kryo.register(SpielBeenden.class);
        kryo.register(SpielNeustarten.class);
        kryo.register(Message.class);
    }

    static class Login {
        String name;
        Held held;
    }

    static class AddHeld {
        Held held;
    }

    static class UpdateHeldVonServer {
        Held held;
    }

    static class UpdateHeldZuServer {
        Held held;
    }

    static class RemoveHeld {
        String name;
    }

    static class AddDinge {
        Stack<Ding> dinge;
    }

    static class UpdateDing {
        Ding ding;
    }

    static class AddFelder {
        Feld[][] felder;
    }

    static class UpdateFeld {
        Feld feld;
    }

    static class LevelLaden {
        int level;
    }

    static class NaechstesLevelVonClient {
    }

    static class NaechstesLevelVonServer {
        int level;
    }

    static class MonsterGetoetet {
        int monsterGetoetet;
    }

    static class SpielBeenden {
    }

    static class SpielNeustarten {
    }

    static class Message {
        String message;
        String name;
    }
}
