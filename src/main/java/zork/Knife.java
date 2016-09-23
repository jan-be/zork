package zork;

import java.awt.*;

public class Knife {
    public int x, y;

    // Public-Attribute ersparen sondierende und manipulierende Methoden
    // Verletzt zwar das Prinzip der Datenkapselung,
    // macht die Sache aber erheblich einfacher...
    public double angriff, ruestung;
    public int gold;
    boolean aufgesammelt;

    public Knife(int x, int y) {
        this.x = x;
        this.y = y;

        aufgesammelt = false;

        // erst mal feste Werte vergeben;
        // spaeter mit Zufallsgenerator festlegen
        angriff = 10;
        ruestung = 5;
        gold = 25;
    }

    public void paint(Graphics g) {
        if (!aufgesammelt) {
            int xPix = 20 + x * 20;
            int yPix = 20 + y * 20;

            g.setColor(Color.RED);
            g.fillOval(xPix + 5, yPix + 5, 10, 10);
        }
    }
}
