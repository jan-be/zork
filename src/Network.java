import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.Stack;

public class Network {
    static public void register(EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(Held.class);
        kryo.register(Login.class);
        kryo.register(UpdateHeldVonServer.class);
        kryo.register(UpdateHeldZuServer.class);
        kryo.register(RemoveHeld.class);
        kryo.register(AddHeld.class);
        kryo.register(Ding.class);
        kryo.register(UpdateDingZuServer.class);
        kryo.register(UpdateDingVonServer.class);
        kryo.register(Stack.class);
        kryo.register(AddDinge.class);
    }

    static public class Login {
        public String name;
        public Held held;
        public Stack<Ding> dinge;
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

    //TODO add server, client
    static public class AddDinge {
        public Stack<Ding> dinge;
    }

    //TODO add server, client
    static public class UpdateDingZuServer {
        public Ding ding;
    }

    //TODO add server, client
    static public class UpdateDingVonServer {
        public Ding ding;
    }
}
