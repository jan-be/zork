import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.ThreadLocalRandom;

class Dungeon {
    static Feld[][] felder;
    final Held held;
    private final double breite;
    private final DungeonDaten dungeonDaten;
    private final String name;
    private final DerClient client;
    private final Stage stage;
    private int aktX, aktY, level;
    private GraphicsContext g;
    private Painter painter;

    Dungeon(DungeonDaten dungeonDaten, double breite, Stage stage, String name, DerClient client) {
        this.dungeonDaten = dungeonDaten;
        this.breite = breite;
        this.name = name;
        this.client = client;
        this.stage = stage;
        level = client.getStartLevel();
        felder = new Feld[dungeonDaten.breite][dungeonDaten.hoehe];
        held = Assets.helden.get(name);
        felderLaden(level);
        HighscoreZeugs.zeitStarten();
    }

    private void felderLaden(int level) {
        held.monsterGetoetetImLevel = 0;
        held.gold = 0;
        held.leben = Held.maxLeben;
        held.ruestung = Held.anfangsruestung;
        held.schwerterAufgesammelt = 0;

        Assets.dinge.clear();
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                felder[x][y] = new Feld();
                felder[x][y].init(x, y, dungeonDaten.alleLevelDaten[level][y].charAt(x));
                if (dungeonDaten.alleLevelDaten[level][y].charAt(x) == 'S') {
                    aktX = x;
                    aktY = y;
                    held.x = aktX;
                    held.y = aktY;
                }
            }
        }
        nachbarfelderAufdecken();
    }

    private void nachbarfelderAufdecken() {
        felder[aktX][aktY].aufdecken();
        if (aktY >= 1) felder[aktX][aktY - 1].aufdecken();
        if (aktY <= dungeonDaten.hoehe - 2) felder[aktX][aktY + 1].aufdecken();
        if (aktX <= dungeonDaten.breite - 2) felder[aktX + 1][aktY].aufdecken();
        if (aktX >= 1) felder[aktX - 1][aktY].aufdecken();

        update(aktX, aktY);
        if (aktY >= 1) update(aktX, aktY - 1);
        if (aktY <= dungeonDaten.hoehe - 2) update(aktX, aktY + 1);
        if (aktX <= dungeonDaten.breite - 2) update(aktX + 1, aktY);
        if (aktX >= 1) update(aktX - 1, aktY);
    }

    private void update(int x, int y) {
        client.sendDingUndFeldUpdate(Assets.getDing(x, y), felder[x][y]);
    }

    void setFeld(Feld feld) {
        if (feld.x >= 0 && feld.y >= 0) felder[feld.x][feld.y] = feld;
    }

    void goWest() {
        if (aktX < 1) return;
        if (felder[aktX - 1][aktY].kannNichtBetretenWerden()) return;
        aktX--;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
        client.sendHeld();
    }

    void goEast() {
        if (aktX > dungeonDaten.breite - 2) return;
        if (felder[aktX + 1][aktY].kannNichtBetretenWerden()) return;
        aktX++;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
        client.sendHeld();
    }

    void goNorth() {
        if (aktY < 1) return;
        if (felder[aktX][aktY - 1].kannNichtBetretenWerden()) return;
        aktY--;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
        client.sendHeld();
    }

    void goSouth() {
        if (aktY > dungeonDaten.hoehe - 2) return;
        if (felder[aktX][aktY + 1].kannNichtBetretenWerden()) return;
        aktY++;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
        client.sendHeld();
    }

    void init(GraphicsContext g) {
        this.g = g;
        painter = new Painter(dungeonDaten, name, g);
    }

    void paint() {
        Platform.runLater(() -> {
            g.clearRect(0, 0, stage.getWidth(), stage.getHeight());
            for (int y = 0; y < dungeonDaten.hoehe; y++) {
                for (int x = 0; x < dungeonDaten.breite; x++) {
                    felder[x][y].paint(g);
                }
            }

            //Umrandung um alle Felder
            g.setStroke(Color.WHITE);
            g.strokeRect(Main.randSize, Main.randSize, breite - 2 * Main.randSize - Main.randSize / dungeonDaten.breite * 3, dungeonDaten.hoehe * Main.feldSize);

            painter.paint();
        });

    }

    void aktionAusfuehren() {
        if (Assets.hatMonster(aktX, aktY)) {
            kaempfen(aktX, aktY);
        } else if (Assets.hatHeiltrank(aktX, aktY)) {
            heilen();
        } else if (Assets.hatSchwert(aktX, aktY)) {
            schwertAufnehmen();
        } else if (felder[aktX][aktY].hatVersteckteTuer()) {
            naechstesLevelStartVersuch();
        } else if (Assets.hatBossmonster(aktX, aktY)) {
            kaempfen(aktX + Assets.getBossmonsterPosX(aktX, aktY), aktY + Assets.getBossmonsterPosY(aktX, aktY));
        }
        update(aktX, aktY);
    }

    void levelStarten(int level) {
        this.level = level;
        felderLaden(level);
    }

    private void naechstesLevelStartVersuch() {
        if (held.monsterGetoetetImLevel == dungeonDaten.anzahlMonsterProLevel[level]) {
            if (level + 1 < Main.ANZAHL_LEVEL) {
                client.naechtesLevel();
            } else {
                client.spielBeenden();
            }
        } else {
            Dialoge.erstMonsterToeten();
        }
    }

    private void schwertAufnehmen() {
        Musikspieler.playSound("schwertAufheben");

        Ding schwert = Assets.getDing(aktX, aktY);

        if (schwert == null) return;
        held.angriff += schwert.angriff;
        held.ruestung += schwert.ruestung;
        schwert.nochSichtbar = false;
        held.schwerterAufgesammelt++;
    }

    private void heilen() {
        Musikspieler.playSound("heiltrankTrinken");

        Ding heiltrank = Assets.getDing(aktX, aktY);

        held.leben += ThreadLocalRandom.current().nextInt(25, 50);

        if (heiltrank == null) return;

        heiltrank.maleAnklickbar--;
        if (heiltrank.maleAnklickbar == 0) {
            heiltrank.nochSichtbar = false;
        }
        if (held.leben > 300) {
            held.leben = 300;

        }

    }

    private void kaempfen(int x, int y) {
        Musikspieler.playSound("schlag");

        Ding gegner = Assets.getDing(x, y);

        int wert = ThreadLocalRandom.current().nextInt(1, 7);

        if (gegner == null) return;
        gegner.leben = gegner.leben - held.angriff;

        if (wert == 6) {
            held.leben -= 40;     // Held verliert und wird schwer verletzt
        } else if (wert >= 3) {
            held.leben -= gegner.angriff;
        } else {
            held.gold += ThreadLocalRandom.current().nextInt(0, 21);    // Held gewinnt haushoch
        }
        if (gegner.leben < 0) {
            held.monsterGetoetetImLevel++;
            client.monsterToeten();
            gegner.nochSichtbar = false;
        }
        if (held.leben <= 0) {
            if (Dialoge.sterben()) {
                neuStarten();
            }
        }
    }

    void neuStarten() {
        client.neuStarten();
    }
}
