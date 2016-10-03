import javax.swing.*;
import java.awt.*;
import java.util.Random;

class Held {
    private int x, y;
    private double angriff, ruestung, leben, anfangsleben;
    private int gold;
    int monsterGetoetet;
    private Random wuerfel;
    private DungeonDaten dungeonDaten;

    Held(DungeonDaten dungeonDaten) {
        this.dungeonDaten = dungeonDaten;
        leben = 255;
        anfangsleben = leben;
        angriff = 50;
        ruestung = 10;
        gold = 25;

        wuerfel = new Random();
    }

    void geheZu(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    void kaempfe(Monster gegner) {
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
            JOptionPane.showMessageDialog(null, "Du bist gestorben.", "Tot", 2);
            System.exit(0);
        }

    }

    void heilen(Heiltrank heiltrank) {
        if (heiltrank.lebenswiedergabe >= heiltrank.anfangsleben / 3) {
            leben += heiltrank.anfangsleben / 3;
            heiltrank.lebenswiedergabe -= heiltrank.anfangsleben / 3;
        }
    }

    void aufnehmen(Knife knife) {
        if (!knife.aufgesammelt) {
            angriff += knife.angriff;
            ruestung += knife.ruestung;
            knife.aufgesammelt = true;
        }
    }

    void paint(Graphics g) {
        int xPix = 20 + x * Frame.FELD_SIZE;
        int yPix = 20 + y * Frame.FELD_SIZE;

        if (leben / anfangsleben > 1) {
            g.setColor(Color.GREEN);
        } else {
            g.setColor(new Color(0, (int) (255 * leben / anfangsleben), 0));
        }
        //g.fillOval(xPix + 4, yPix + 4, 12, 12);
        g.drawImage(BilderGetter.heldBild, xPix + Frame.FELD_SIZE /10, yPix + Frame.FELD_SIZE /10, Frame.FELD_SIZE *4/5, Frame.FELD_SIZE *4/5, null);

        g.setColor(new Color(255, 255, 191));
        g.fillRect(120, 40 + dungeonDaten.hoehe * Frame.FELD_SIZE, Frame.FELD_SIZE * dungeonDaten.breite - 100, 70);
        g.setColor(new Color(0, 0, 0));
        g.drawRect(120, 40 + dungeonDaten.hoehe * Frame.FELD_SIZE, Frame.FELD_SIZE * dungeonDaten.breite - 100, 70);

        g.drawString("Held: Leben: " + leben + ", Angriff: " + angriff + ", Rüstung: " + ruestung + ", Gold: " + gold,
                124, 60 + dungeonDaten.hoehe * Frame.FELD_SIZE);
    }

}
