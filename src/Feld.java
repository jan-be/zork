import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Feld {
    private final int typ;
    private final double xPixel, yPixel;
    private final int x, y;
    private boolean aufgedeckt;
    private final Rucksack rucksack;

    Feld(int x, int y, char t, Rucksack rucksack) {
        this.x = x;
        this.y = y;
        this.xPixel = Main.randSize + x * Main.feldSize;
        this.yPixel = Main.randSize + y * Main.feldSize;
        this.rucksack = rucksack;
        if (t == 'W') typ = 1;      //Weg
        else if (t == 'V') typ = 2; //Versteckte Tür
        else if (t == 'S') typ = 3; //Beginn
        else if (t == 'M') typ = 4; //Monster
        else if (t == 'H') typ = 5; //heiltrank
        else if (t == 'K') typ = 6; //Schwert
        else if (t == 'B') typ = 7; //bossmonster
        else typ = 0;

        aufgedeckt = false;

        if (typ == 4) {
            rucksack.stack.push(new Gegenstand("monster", x, y, 10, 0, 255, 0));
        } else if (typ == 5) {
            rucksack.stack.push(new Gegenstand("heiltrank", x, y, 0, 0, 0, 3));
        } else if (typ == 6) {
            rucksack.stack.push(new Gegenstand("schwert", x, y, 10, 5, 0, 0));
        } else if (typ == 7) {
            rucksack.stack.push(new Gegenstand("bossmonster", x, y, 50, 0, 1000, 0));
        }
    }

    boolean kannNichtBetretenWerden() {
        return (typ == 0);
    }

    boolean hatVersteckteTuer() {
        return typ == 2;
    }

    void aufdecken() {
        aufgedeckt = true;
        if (rucksack.get(x, y) != null) {
            rucksack.get(x, y).aufgedeckt = true;
        }
    }

    void paint(GraphicsContext g) {
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4) || typ == 5 || typ == 6 || typ == 7)
                g.setFill(Color.color(0.33, 0.33, 0));
            else if (typ == 2)
                g.setFill(Color.color(0.33, 0, 0.33));
            else if (typ == 3)
                g.setFill(Color.color(0, 0.33, 0.33));
            else
                g.setFill(Color.BLACK);

            g.fillRect(xPixel, yPixel, Main.feldSize, Main.feldSize);
            g.setFill(Color.BLACK);
            g.setStroke(Color.BLACK);
            g.strokeRect(xPixel, yPixel, Main.feldSize, Main.feldSize);
        } else {
            g.setFill(Color.BLACK);
            g.fillRect(xPixel, yPixel, Main.feldSize, Main.feldSize);
        }
    }
}
