import java.util.Stack;

class Rucksack {
    final Stack<Gegenstand> stack = new Stack<>();

    Gegenstand get(int x, int y) {
        for (Gegenstand g : stack) {
            if (g.x == x && g.y == y) {
                return g;
            }
        }
        return null;
    }

    boolean hatMonster(int x, int y) {
        return get(x, y) != null && get(x, y).typ.equals("monster") && get(x, y).nochSichtbar;
    }

    boolean hatHeiltrank(int x, int y) {
        return get(x, y) != null && get(x, y).typ.equals("heiltrank") && get(x, y).nochSichtbar;
    }

    boolean hatSchwert(int x, int y) {
        return get(x, y) != null && get(x, y).typ.equals("schwert") && get(x, y).nochSichtbar;
    }

    boolean hatBossmonster(int x, int y) {
        return get(x, y) != null && get(x, y).typ.equals("bossmonster") && get(x, y).nochSichtbar ||
                get(x - 1, y) != null && get(x - 1, y).typ.equals("bossmonster") && get(x - 1, y).nochSichtbar ||
                get(x, y - 1) != null && get(x, y - 1).typ.equals("bossmonster") && get(x, y - 1).nochSichtbar ||
                get(x - 1, y - 1) != null && get(x - 1, y - 1).typ.equals("bossmonster") && get(x - 1, y - 1).nochSichtbar
                ;
    }

    int getBossmonsterPosX(int x, int y) {
        if (get(x, y) != null && get(x, y).typ.equals("bossmonster") && get(x, y).nochSichtbar ||
                get(x, y - 1) != null && get(x, y - 1).typ.equals("bossmonster") && get(x, y - 1).nochSichtbar) {
            return 0;
        } else {
            return -1;
        }
    }

    int getBossmonsterPosY(int x, int y) {
        if (get(x, y) != null && get(x, y).typ.equals("bossmonster") && get(x, y).nochSichtbar  ||
                get(x - 1, y) != null && get(x - 1, y).typ.equals("bossmonster") && get(x - 1, y).nochSichtbar) {
            return 0;
        } else {
            return -1;
        }
    }
}
