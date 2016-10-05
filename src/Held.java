import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

import java.util.Random;

class Held {
    private int x, y;
    private double angriff, ruestung, leben, anfangsleben;
    private int gold;
    int monsterGetoetetImLevel;
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
                monsterGetoetetImLevel++;
            }
        }
        if (leben <= 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Einfach zu schlecht.");
            alert.setHeaderText("Du bist gestorben");
            alert.setContentText("Das ist wirklich nicht so gedacht, dass man zwischendurch stirbt. " +
                    "\nDu musst wirklich schlecht sein.");
            alert.showAndWait();

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

    void paint(GraphicsContext g) {
        int xPix = 20 + x * Main.FELD_SIZE;
        int yPix = 20 + y * Main.FELD_SIZE;

        if (leben / anfangsleben > 1) {
            g.setFill(Color.GREEN);
        } else {
            g.setFill(Color.color(0, leben / anfangsleben, 0));
        }
        //g.fillOval(xPix + 4, yPix + 4, 12, 12);
        g.drawImage(BilderGetter.heldBild, xPix + Main.FELD_SIZE /10, yPix + Main.FELD_SIZE /10, Main.FELD_SIZE *4/5, Main.FELD_SIZE *4/5);

        g.setFill(Color.color(1, 1, 0.66));
        g.fillRect(120, 40 + dungeonDaten.hoehe * Main.FELD_SIZE, Main.FELD_SIZE * dungeonDaten.breite - 100, 70);
        g.setFill(Color.color(0, 0, 0));
        g.strokeRect(120, 40 + dungeonDaten.hoehe * Main.FELD_SIZE, Main.FELD_SIZE * dungeonDaten.breite - 100, 70);

        g.fillText("Held: Leben: " + leben + ", Angriff: " + angriff + ", Rüstung: " + ruestung + ", Gold: " + gold,
                124, 60 + dungeonDaten.hoehe * Main.FELD_SIZE);
    }

}
