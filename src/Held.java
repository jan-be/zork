import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.concurrent.ThreadLocalRandom;

class Held {
    private int x, y;
    private static final double maxLeben = 300;
    private double angriff = 50, ruestung = 10, leben = maxLeben;
    int gold;
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

    void kaempfe(Gegenstand gegner) {
        int wert = ThreadLocalRandom.current().nextInt(1, 7);

        gegner.leben = gegner.leben - angriff;

        if (wert == 6) { // Held verliert und wird schwer verletzt
            leben = leben - 40;
        } else if (wert >= 3) {
            leben = leben - gegner.angriff;
        } else {
            gold += ThreadLocalRandom.current().nextInt(0, 21);    // Held gewinnt haushoch
        }
        if (gegner.leben < 0) {
            monsterGetoetetImLevel++;
            gegner.nochSichtbar=false;
        }

        if (leben <= 0) {
            Dialoge.sterben();
        }

    }

    void heilen(Gegenstand heiltrank) {
        leben += ThreadLocalRandom.current().nextInt(25, 50);
        heiltrank.maleAnklickbar--;
        if (heiltrank.maleAnklickbar == 0) {
            heiltrank.nochSichtbar = false;
        }
        if (leben > 300) {
            leben = 300;

        }
    }

    void aufnehmen(Gegenstand schwert) {
        angriff += schwert.angriff;
        ruestung += schwert.ruestung;
        schwert.nochSichtbar = false;
        schwerterAufgesammelt++;
    }

    void paint(GraphicsContext g) {
        double echteBreite = dungeonDaten.breite * Main.feldSize;
        double xPix = Main.randSize + x * Main.feldSize;
        double yPix = Main.randSize + y * Main.feldSize;


        //das Bild vom Helden
        g.drawImage(Bilder.get("held"), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);

        //das Herzzeugs
        for (int i = 0; i < maxLeben / 30; i++) {
            g.drawImage(Bilder.get("grauesHerz"), echteBreite / 2 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }
        for (int i = 0; i < leben / 30; i++) {
            g.drawImage(Bilder.get("rotesHerz"), echteBreite / 2 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }

        //schwert, wenn aufgesammelt
        for (int i = 0; i < schwerterAufgesammelt; i++) {
            g.drawImage(Bilder.get("schwert"), echteBreite / 4 * 3 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }

        //coin
        g.drawImage(Bilder.get("coin"), echteBreite / 40 * 36, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        g.setFill(Color.WHITE);
        g.setFont(new Font(30));
        g.fillText(Integer.toString(gold), echteBreite / 40 * 37, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize + Main.feldSize / 2 + 2);
    }

}
