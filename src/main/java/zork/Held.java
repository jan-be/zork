package zork;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Held {
    private int x, y;
    double angriff, ruestung, leben, anfangsleben;
    int gold, monsterGetoetet;
    Random wuerfel;
    DungeonDaten dungeonDaten = new DungeonDaten();

    public Held() {
        leben = 255;
        anfangsleben = leben;
        angriff = 50;
        ruestung = 10;
        gold = 25;

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
            if (gegner.leben < 0) {
                monsterGetoetet++;
            }
        }
        if (leben <= 0) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "ALARM", "Tot", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }

    public void heilen(Heiltrank heiltrank) {
        if (heiltrank.lebenswiedergabe >= heiltrank.anfangsleben / 3) {
            leben += heiltrank.anfangsleben / 3;
            heiltrank.lebenswiedergabe -= heiltrank.anfangsleben / 3;
        }
    }

    public void aufnehmen(Knife knife) {
        if (!knife.aufgesammelt) {
            angriff += knife.angriff;
            knife.aufgesammelt = true;
        }
    }

    public void paint(Graphics g) {
        int xPix = 20 + x * 20;
        int yPix = 20 + y * 20;

        if (leben / anfangsleben > 1) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(new Color(0, (int) (255 * leben / anfangsleben), 0));
        }
        g.fillOval(xPix + 4, yPix + 4, 12, 12);

        g.setColor(new Color(255, 255, 191));
        g.fillRect(120, 40 + dungeonDaten.hoehe * 20, 20 * dungeonDaten.breite - 100, 70);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(120, 40 + dungeonDaten.hoehe * 20, 20 * dungeonDaten.breite - 100, 70);

        g.drawString("Held: Leben: " + leben + ", Angriff: " + angriff + ", Rüstung: " + ruestung + ", Gold: " + gold,
                124, 60 + dungeonDaten.hoehe * 20);
    }

}
