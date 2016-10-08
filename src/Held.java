import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.concurrent.ThreadLocalRandom;

class Held {
    private int x, y;
    private static final double maxLeben = 300;
    private double angriff = 50, ruestung = 10, leben = maxLeben;
    private int gold;
    int monsterGetoetetImLevel;
    private final DungeonDaten dungeonDaten;
    private int schwerterAufgesammelt;

    Held(DungeonDaten dungeonDaten) {
        this.dungeonDaten = dungeonDaten;
    }

    void geheZu(int xPos, int yPos) {
        x = xPos;
        y = yPos;
    }

    void kaempfe(Monster gegner) {
        if (gegner.leben > 0) {
            int wert = ThreadLocalRandom.current().nextInt(1,7);

            gegner.leben = gegner.leben - angriff;

            if (wert == 6) { // Held verliert und wird schwer verletzt
                leben = leben - 64;
            } else if (wert >= 3) {
                leben = leben - gegner.angriff;
            } else {
                gold += ThreadLocalRandom.current().nextInt(0,21);    // Held gewinnt haushoch
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
        leben += ThreadLocalRandom.current().nextInt(25, 40);
        heiltrank.maleAnklickbar--;
        if (leben > 300) {
            leben = 300;

        }
    }

    void aufnehmen(Knife knife) {
        angriff += knife.angriff;
        ruestung += knife.ruestung;
        knife.aufgesammelt = true;
        schwerterAufgesammelt++;
    }

    void paint(GraphicsContext g) {
        double echteBreite = dungeonDaten.breite * Main.feldSize;
        double xPix = Main.randSize + x * Main.feldSize;
        double yPix = Main.randSize + y * Main.feldSize;


        //das Bild vom Helden
        g.drawImage(BilderGetter.heldBild, xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);

        /*//das Infofeld
        g.setFill(Color.color(1, 1, 0.66));
        g.fillRect(120, 40 + dungeonDaten.hoehe * Main.feldSize, Main.feldSize * dungeonDaten.breite - 100, 70);*/

        /*//das Textzeugs
        g.setFill(Color.BLACK);
        g.fillText("Held: Leben: " + leben + ", Angriff: " + angriff + ", Rüstung: " + ruestung + ", Gold: " + gold,
                124, 60 + dungeonDaten.hoehe * Main.feldSize);*/

        //das Herzzeugs
        for (int i = 0; i < maxLeben / 30; i++) {
            g.drawImage(BilderGetter.grauesHerzBild, echteBreite / 2 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }
        for (int i = 0; i < leben / 30; i++) {
            g.drawImage(BilderGetter.herzBild, echteBreite / 2 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }

        //schwert, wenn aufgesammelt
        for (int i = 0; i < schwerterAufgesammelt; i++) {
            g.drawImage(BilderGetter.schwertBild, echteBreite / 4 * 3 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }

        //coin
        g.drawImage(BilderGetter.coinBild, echteBreite / 40 * 36, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        g.setFill(Color.WHITE);
        g.setFont(new Font(30));
        g.fillText(Integer.toString(gold), echteBreite / 40 * 37, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize + Main.feldSize / 2 + 2);
    }

}
