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
        kryo.register(UpdateDingZuServer.class);
        kryo.register(UpdateDingVonServer.class);
        kryo.register(Stack.class);
        kryo.register(AddDingeVonServer.class);
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

    static class AddDingeVonServer {
        Stack<Ding> dinge;
    }

    static class UpdateDingZuServer {
        Ding ding;
    }

    static class UpdateDingVonServer {
        Ding ding;
    }
}
