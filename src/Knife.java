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
            double xPix = Main.randSize + x * Main.feldSize;
            double yPix = Main.randSize + y * Main.feldSize;

            g.drawImage(BilderGetter.schwertBild, xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
        }
    }
}
