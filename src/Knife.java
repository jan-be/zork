import javafx.scene.canvas.GraphicsContext;

class Knife {
    private int x, y, gold;
    double angriff, ruestung;
    boolean aufgesammelt;

    Knife(int x, int y) {
        this.x = x;
        this.y = y;

        aufgesammelt = false;

        // erst mal feste Werte vergeben;
        // spaeter mit Zufallsgenerator festlegen
        angriff = 10;
        ruestung = 5;
        gold = 25;
    }

    void paint(GraphicsContext g) {
        if (!aufgesammelt) {
            int xPix = 20 + x * Main.FELD_SIZE;
            int yPix = 20 + y * Main.FELD_SIZE;

            g.drawImage(BilderGetter.schwertBild, xPix + Main.FELD_SIZE /10, yPix + Main.FELD_SIZE /10, Main.FELD_SIZE *4/5, Main.FELD_SIZE *4/5);
        }
    }
}
