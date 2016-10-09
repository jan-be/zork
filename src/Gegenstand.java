import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Gegenstand {
    final String typ;
    final int x;
    final int y;
    final double angriff;
    final double ruestung;
    double leben;
    double maleAnklickbar;
    private final double anfangsLeben;
    boolean nochSichtbar = true;
    boolean aufgedeckt = false;

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
        if (nochSichtbar && aufgedeckt) {
            double xPix = Main.randSize + x * Main.feldSize;
            double yPix = Main.randSize + y * Main.feldSize;

            switch (typ) {
                case "monster":
                    g.setFill(Color.RED);
                    g.fillRect(xPix, yPix, leben / anfangsLeben * Main.feldSize, 3);
                    break;
                case "heiltrank":
                    g.setFill(Color.BLUE);
                    g.fillRect(xPix, yPix, maleAnklickbar / 3 * Main.feldSize, 3);
                    break;
                case "bossmonster":
                    g.setFill(Color.RED);
                    g.fillRect(xPix, yPix, leben / anfangsLeben * Main.feldSize * 2, 6);
                    break;
            }
            if (!typ.equals("bossmonster")) {
                g.drawImage(Bilder.get(typ), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
            } else {
                g.drawImage(Bilder.get("monster"), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5 * 2, Main.feldSize * 4 / 5 * 2);
            }
        }
    }
}
