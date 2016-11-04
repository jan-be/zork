import java.util.ArrayList;

public class DingStack {
    int anzahl;
    ArrayList<Ding> dinge;

    Ding top() {
        if (anzahl > 0) {
            return dinge.get(anzahl-1);
        }
        return null;
    }

    void push(Ding ding) {
        if (anzahl > 0) {
            dinge.set(anzahl-1, ding);
            anzahl++;
        }
    }

    void pop() {
        if (anzahl > 0) {
            anzahl--;
        }
    }

    Ding get(int index) {
        if (anzahl > 0) {
            return dinge.get(index-1);
        }
        return null;
    }

    int size() {
        return anzahl;
    }

    void remove(int index) {
        if (anzahl > 0) {
            dinge.remove(index-1);
        }
    }

    void add(int index, Ding ding) {
        if (anzahl > 0) {
            dinge.add(index, ding);
            anzahl++;
        }
    }

    void clear() {
        anzahl = 0;
    }
}
