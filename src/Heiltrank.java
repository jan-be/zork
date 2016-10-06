import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Heiltrank {
    private int x, y;
    double maleAnklickbar = 3;

    Heiltrank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void paint(GraphicsContext g) {
        if (maleAnklickbar > 0) {
            double xPix = Main.randSize + x * Main.feldSize;
            double yPix = Main.randSize + y * Main.feldSize;

            g.setFill(Color.BLUE);
            g.fillRect(xPix, yPix, maleAnklickbar / 3 * Main.feldSize, 3);
            g.drawImage(BilderGetter.heiltrankBild, xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
        }
    }

}
