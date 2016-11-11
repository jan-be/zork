import java.util.HashMap;
import java.util.Stack;

class Assets {
    static Stack<Ding> dinge = new Stack<>();
    static final HashMap<String, Held> helden = new HashMap<>();

    static void init(String name) {
        Held held = new Held();
        held.name = name;
        helden.put(name, held);
    }

    static Ding getDing(int x, int y) {
        for (Ding d : dinge) {
            if (d.x == x && d.y == y) {
                return d;
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
        Ding ding = getDing(x, y);
        return ding != null && ding.typ.equals("monster") && ding.nochSichtbar;
    }

    static boolean hatHeiltrank(int x, int y) {
        Ding ding = getDing(x, y);
        return ding != null && ding.typ.equals("heiltrank") && ding.nochSichtbar;
    }

    static boolean hatSchwert(int x, int y) {
        Ding ding = getDing(x, y);
        return ding != null && ding.typ.equals("schwert") && ding.nochSichtbar;
    }

    static boolean hatBossmonster(int x, int y) {
        Ding ding = getDing(x, y);
        Ding dingXM1 = getDing(x - 1, y);
        Ding dingYM1 = getDing(x, y - 1);
        Ding dingBM1 = getDing(x - 1, y - 1);
        return ding != null && ding.typ.equals("bossmonster") && ding.nochSichtbar ||
                dingXM1 != null && dingXM1.typ.equals("bossmonster") && dingXM1.nochSichtbar ||
                dingYM1 != null && dingYM1.typ.equals("bossmonster") && dingYM1.nochSichtbar ||
                dingBM1 != null && dingBM1.typ.equals("bossmonster") && dingBM1.nochSichtbar;
    }

    static int getBossmonsterPosX(int x, int y) {
        Ding ding = getDing(x, y);
        Ding dingYM1 = getDing(x, y - 1);
        if (ding != null && ding.typ.equals("bossmonster") && ding.nochSichtbar ||
                dingYM1 != null && dingYM1.typ.equals("bossmonster") && dingYM1.nochSichtbar) {
            return 0;
        } else {
            return -1;
        }
    }

    static int getBossmonsterPosY(int x, int y) {
        Ding ding = getDing(x, y);
        Ding dingXM1 = getDing(x - 1, y);
        if (ding != null && ding.typ.equals("bossmonster") && ding.nochSichtbar ||
                dingXM1 != null && dingXM1.typ.equals("bossmonster") && dingXM1.nochSichtbar) {
            return 0;
        } else {
            return -1;
        }
    }
}
