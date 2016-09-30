import java.awt.*;

public class Monster {
    public int x, y;

    // Public-Attribute ersparen sondierende und manipulierende Methoden
    // Verletzt zwar das Prinzip der Datenkapselung,
    // macht die Sache aber erheblich einfacher...
    public double angriff, ruestung, leben;
    public int gold;

    public Monster(int x, int y) {
        this.x = x;
        this.y = y;

        // erst mal feste Werte vergeben;
        // spaeter mit Zufallsgenerator festlegen
        leben = 255;
        angriff = 10;
        ruestung = 5;
        gold = 25;
    }

    public void paint(Graphics g) {
        if (leben > 0) {
            int xPix = 20 + x * 20;
            int yPix = 20 + y * 20;

            g.setColor(new Color((int) leben, 0, 0));
            g.fillOval(xPix + 5, yPix + 5, 10, 10);
        }
    }
}
