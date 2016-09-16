import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Random;

public class Held {
    private int x, y;
    double angriff, ruestung, leben;
    int gold;
    Random wuerfel;

    DungeonDaten dd;

    public Held() throws FileNotFoundException {
        leben = 255;
        angriff = 50;
        ruestung = 10;
        gold = 25;

        dd = new DungeonDaten();
        wuerfel = new Random();
    }

    public void geheZu(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    public void kaempfe(Monster gegner) {
        if (gegner.leben > 0) {
            int wert = wuerfel.nextInt(6) + 1;

            gegner.leben = gegner.leben - angriff;

            if (wert == 6) { // Held verliert und wird schwer verletzt
                leben = leben - 64;
            } else if (wert >= 3) {
                leben = leben - gegner.angriff;
            } else {
                gold += wuerfel.nextInt(20);    // Held gewinnt haushoch
            }
        }
        if (leben <= 0) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "ALARM", "Tot", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void paint(Graphics g) {
        int xPix = 20 + x * 20;
        int yPix = 20 + y * 20;

        g.setColor(new Color(0, (int) leben, 0));
        g.fillOval(xPix + 4, yPix + 4, 12, 12);

        g.setColor(new Color(255, 255, 191));
        g.fillRect(120, 40 + dd.hoehe * 20, 20 * dd.breite - 100, 70);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(120, 40 + dd.hoehe * 20, 20 * dd.breite - 100, 70);

        g.drawString("Held: Leben: " + leben + ", Angriff: " + angriff + ", Rüstung: " + ruestung + ", Gold: " + gold,
                124, 60 + dd.hoehe * 20);
    }

}
