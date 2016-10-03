import java.awt.*;

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

    void paint(Graphics g) {
        if (leben > 0) {
            int xPix = 20 + x * Frame.FELD_SIZE;
            int yPix = 20 + y * Frame.FELD_SIZE;

            g.setColor(new Color((int) leben, 0, 0));
            //g.fillOval(xPix + 5, yPix + 5, 10, 10);
            g.drawImage(BilderGetter.monsterBild, xPix + Frame.FELD_SIZE /10, yPix + Frame.FELD_SIZE /10, Frame.FELD_SIZE *4/5, Frame.FELD_SIZE *4/5, null);
        }
    }
}
