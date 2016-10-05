import javafx.scene.canvas.GraphicsContext;

class Monster {
    private int x, y;
    double angriff, ruestung, leben;
    int gold;

    Monster(int x, int y) {
        this.x = x;
        this.y = y;

        // erst mal feste Werte vergeben;
        // spaeter mit Zufallsgenerator festlegen
        leben = 255;
        angriff = 10;
        ruestung = 5;
        gold = 25;
    }

    void paint(GraphicsContext g) {
        if (leben > 0) {
            int xPix = 20 + x * Main.FELD_SIZE;
            int yPix = 20 + y * Main.FELD_SIZE;

            g.drawImage(BilderGetter.monsterBild, xPix + Main.FELD_SIZE / 10, yPix + Main.FELD_SIZE / 10, Main.FELD_SIZE * 4 / 5, Main.FELD_SIZE * 4 / 5);
        }
    }
}
