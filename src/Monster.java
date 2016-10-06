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
            double xPix = 20 + x * Main.feldSize;
            double yPix = 20 + y * Main.feldSize;

            g.drawImage(BilderGetter.monsterBild, xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
        }
    }
}
