import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Monster {
    private final int x;
    private final int y;
    final double angriff = 10;
    double leben = 255;
    private final double anfangsLeben = leben;

    Monster(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void paint(GraphicsContext g) {
        if (leben > 0) {
            double xPix = Main.randSize + x * Main.feldSize;
            double yPix = Main.randSize + y * Main.feldSize;

            g.setFill(Color.RED);
            g.fillRect(xPix, yPix, leben / anfangsLeben * Main.feldSize, 3);
            g.drawImage(BilderGetter.monsterBild, xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
        }
    }
}
