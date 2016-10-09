import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Gegenstand {
    private String typ;
    private int x, y;
    double angriff, ruestung, leben, maleAnklickbar;
    private double anfangsLeben;
    boolean nochSichtbar = true;

    Gegenstand(String typ, int x, int y, double angriff, double ruestung, double leben, double maleAnklickbar) {
        this.typ = typ;
        this.x = x;
        this.y = y;
        this.angriff = angriff;
        this.ruestung = ruestung;
        this.leben = leben;
        this.anfangsLeben = leben;
        this.maleAnklickbar = maleAnklickbar;
    }

    void paint(GraphicsContext g) {
        if (nochSichtbar) {
            double xPix = Main.randSize + x * Main.feldSize;
            double yPix = Main.randSize + y * Main.feldSize;

            if (typ.equals("monster")) {
                g.setFill(Color.RED);
                g.fillRect(xPix, yPix, leben / anfangsLeben * Main.feldSize, 3);
            } else if (typ.equals("heiltrank")) {
                g.setFill(Color.BLUE);
                g.fillRect(xPix, yPix, maleAnklickbar / 3 * Main.feldSize, 3);
            }
            g.drawImage(Bilder.get(typ), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
        }
    }
}
