import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Feld {
    private int typ;
    int x, y;
    private boolean aufgedeckt;

    void init(int x, int y, char t) {
        this.x = x;
        this.y = y;
        if (t == 'W') typ = 1;      //Weg
        else if (t == 'V') typ = 2; //Versteckte Tür
        else if (t == 'S') typ = 3; //Beginn
        else if (t == 'M') typ = 4; //Monster
        else if (t == 'H') typ = 5; //heiltrank
        else if (t == 'K') typ = 6; //Schwert
        else if (t == 'B') typ = 7; //bossmonster
        else typ = 0;

        aufgedeckt = false;

        if (typ == 4 || typ == 5 || typ == 6 || typ == 7) {
            Ding ding = new Ding();
            if (typ == 4) {
                ding.init("monster", x, y, 10, 0, 255, 0);
            } else if (typ == 5) {
                ding.init("heiltrank", x, y, 0, 0, 0, 3);
            } else if (typ == 6) {
                ding.init("schwert", x, y, 10, 5, 0, 0);
            } else {
                ding.init("bossmonster", x, y, 50, 0, 1000, 0);
            }
            Assets.dinge.push(ding);
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
        Ding ding = Assets.getDing(x, y);
        if (ding != null) {
            ding.aufgedeckt = true;
        }
    }

    void paint(GraphicsContext g) {
        double xPix = Main.randSize + x * Main.feldSize;
        double yPix = Main.randSize + y * Main.feldSize;
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4) || typ == 5 || typ == 6 || typ == 7)
                //gelb-grün
                g.setFill(Color.color(0.33, 0.33, 0));
            else if (typ == 2)
                //lila
                g.setFill(Color.color(0.33, 0, 0.33));
            else if (typ == 3)
                //hellblau
                g.setFill(Color.color(0, 0.33, 0.33));
            else
                g.setFill(Color.BLACK);

            g.fillRect(xPix, yPix, Main.feldSize, Main.feldSize);
            g.setFill(Color.BLACK);
            g.setStroke(Color.BLACK);
            g.strokeRect(xPix, yPix, Main.feldSize, Main.feldSize);
        } else {
            g.setFill(Color.BLACK);
            g.fillRect(xPix, yPix, Main.feldSize, Main.feldSize);
        }
    }
}
