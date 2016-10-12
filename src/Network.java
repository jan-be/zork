import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Held.class);
        kryo.register(Login.class);
        kryo.register(UpdateHeldVonServer.class);
        kryo.register(UpdateHeldZuServer.class);
        kryo.register(RemoveHeld.class);
        kryo.register(AddHeld.class);
    }

    static public class Login {
        public String name;
        public Held held;
    }

    static public class AddHeld {
        public Held held;
    }

    static public class UpdateHeldVonServer {
        public Held held;
    }

    static public class UpdateHeldZuServer {
        public Held held;
    }

    static public class RemoveHeld {
        public String name;
    }
}
