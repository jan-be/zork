import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Feld {
    private final int typ;
    private final double x;
    private final double y;
    private boolean aufgedeckt;

    private Gegenstand gegenstand;


    Feld(int x, int y, char t) {
        this.x = Main.randSize + x * Main.feldSize;
        this.y = Main.randSize + y * Main.feldSize;
        if (t == 'W') typ = 1;      //Weg
        else if (t == 'V') typ = 2; //Versteckte Tür
        else if (t == 'S') typ = 3; //Beginn
        else if (t == 'M') typ = 4; //Monster
        else if (t == 'H') typ = 5; //heiltrank
        else if (t == 'K') typ = 6; //Schwert
        else typ = 0;

        aufgedeckt = false;

        if (typ == 4) {
            gegenstand = new Gegenstand("monster", x, y, 10, 0, 255, 0);
        } else if (typ == 5) {
            gegenstand = new Gegenstand("heiltrank", x, y, 0, 0, 0, 3);
        } else if (typ == 6) {
            gegenstand = new Gegenstand("schwert", x, y, 10, 5, 0, 0);
        }
    }

    boolean kannNichtBetretenWerden() {
        return (typ == 0);
    }

    boolean hatMonster() {
        return (typ == 4 && gegenstand.nochSichtbar);
    }

    boolean hatHeiltrank() {
        return (typ == 5 && gegenstand.nochSichtbar);
    }

    boolean hatSchwert() {
        return typ == 6 && gegenstand.nochSichtbar;
    }

    boolean hatVersteckteTuer() {
        return typ == 2;
    }

    Gegenstand gibGegenstand() {
        return gegenstand;
    }

    void aufdecken() {
        aufgedeckt = true;
    }

    void paint(GraphicsContext g) {
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4) || typ == 5 || typ == 6)
                g.setFill(Color.color(0.33, 0.33, 0));
            else if (typ == 2)
                g.setFill(Color.color(0.33, 0, 0.33));
            else if (typ == 3)
                g.setFill(Color.color(0, 0.33, 0.33));
            else
                g.setFill(Color.BLACK);

            g.fillRect(x, y, Main.feldSize, Main.feldSize);
            g.setFill(Color.BLACK);
            g.setStroke(Color.BLACK);
            g.strokeRect(x, y, Main.feldSize, Main.feldSize);

            if (gegenstand != null) {
                gegenstand.paint(g);
            }
        } else {
            g.setFill(Color.BLACK);
            g.fillRect(x, y, Main.feldSize, Main.feldSize);
        }
    }

}
