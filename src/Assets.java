import java.util.HashMap;
import java.util.Stack;

@SuppressWarnings("ConstantConditions")
class Assets {
    static Stack<Ding> dinge = new Stack<>();
    static final HashMap<String, Held> helden = new HashMap<>();

    static void init(String name) {
        Held held = new Held();
        held.name = name;
        helden.put(name, held);
    }

    static Ding getDing(int x, int y) {
        for (int i = 1; i < dinge.size(); i++) {
            if (dinge.get(i).x == x && dinge.get(i).y == y) {
                return dinge.get(i);
            }
        }
        return null;
    }

    static void setDing(Ding ding) {
        try {
            boolean done = false;
            for (int i = 0; i < dinge.size(); i++) {
                if (dinge.get(i).x == ding.x && dinge.get(i).y == ding.y) {
                    dinge.remove(i);
                    dinge.add(i,ding);
                    done = true;
                }
            }
            if(!done) {
                dinge.push(ding);
            }
        } catch (Exception ignore) {
        }
    }

    static boolean hatMonster(int x, int y) {
        return getDing(x, y) != null && getDing(x, y).typ.equals("monster") && getDing(x, y).nochSichtbar;
    }

    static boolean hatHeiltrank(int x, int y) {
        return getDing(x, y) != null && getDing(x, y).typ.equals("heiltrank") && getDing(x, y).nochSichtbar;
    }

    static boolean hatSchwert(int x, int y) {
        return getDing(x, y) != null && getDing(x, y).typ.equals("schwert") && getDing(x, y).nochSichtbar;
    }

    static boolean hatBossmonster(int x, int y) {
        return getDing(x, y) != null && getDing(x, y).typ.equals("bossmonster") && getDing(x, y).nochSichtbar ||
                getDing(x - 1, y) != null && getDing(x - 1, y).typ.equals("bossmonster") && getDing(x - 1, y).nochSichtbar ||
                getDing(x, y - 1) != null && getDing(x, y - 1).typ.equals("bossmonster") && getDing(x, y - 1).nochSichtbar ||
                getDing(x - 1, y - 1) != null && getDing(x - 1, y - 1).typ.equals("bossmonster") && getDing(x - 1, y - 1).nochSichtbar;
    }

    static int getBossmonsterPosX(int x, int y) {
        if (getDing(x, y) != null && getDing(x, y).typ.equals("bossmonster") && getDing(x, y).nochSichtbar ||
                getDing(x, y - 1) != null && getDing(x, y - 1).typ.equals("bossmonster") && getDing(x, y - 1).nochSichtbar) {
            return 0;
        } else {
            return -1;
        }
    }

    static int getBossmonsterPosY(int x, int y) {
        if (getDing(x, y) != null && getDing(x, y).typ.equals("bossmonster") && getDing(x, y).nochSichtbar ||
                getDing(x - 1, y) != null && getDing(x - 1, y).typ.equals("bossmonster") && getDing(x - 1, y).nochSichtbar) {
            return 0;
        } else {
            return -1;
        }
    }
}
