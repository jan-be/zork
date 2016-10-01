import java.awt.*;

class Knife {
    private int x, y, gold;
    double angriff, ruestung;
    boolean aufgesammelt;
    private BilderGetter bilderGetter;

    Knife(int x, int y, BilderGetter bilderGetter) {
        this.x = x;
        this.y = y;
        this.bilderGetter = bilderGetter;

        aufgesammelt = false;

        // erst mal feste Werte vergeben;
        // spaeter mit Zufallsgenerator festlegen
        angriff = 10;
        ruestung = 5;
        gold = 25;
    }

    void paint(Graphics g) {
        if (!aufgesammelt) {
            int xPix = 20 + x * Frame.FELD_SIZE;
            int yPix = 20 + y * Frame.FELD_SIZE;

            g.setColor(Color.YELLOW);
            //g.fillOval(xPix + 5, yPix + 5, 10, 10);
            g.drawImage(bilderGetter.schwertBild, xPix + Frame.FELD_SIZE /10, yPix + Frame.FELD_SIZE /10, Frame.FELD_SIZE *4/5, Frame.FELD_SIZE *4/5, null);
        }
    }
}
