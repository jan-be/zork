import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.concurrent.ThreadLocalRandom;

class Dungeon {
    private final Feld[][] feld;
    private final Held held;
    private final double breite;
    private final DungeonDaten dungeonDaten;
    private final Assets assets = new Assets();
    private int aktX, aktY, level;

    Dungeon(DungeonDaten dungeonDaten, double breite) {
        this.dungeonDaten = dungeonDaten;
        this.breite = breite;
        feld = new Feld[dungeonDaten.breite][dungeonDaten.hoehe];
        held = assets.helden.get(1);
        felderLaden(level);
        HighscoreZeugs.zeitStarten();
    }

    private void felderLaden(int level) {
        assets.dinge.clear();
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                feld[x][y] = new Feld(x, y, dungeonDaten.alleLevelDaten[level][y].charAt(x), assets);
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
        feld[aktX][aktY].aufdecken();
        if (aktY >= 1) feld[aktX][aktY - 1].aufdecken();
        if (aktY <= dungeonDaten.hoehe - 2) feld[aktX][aktY + 1].aufdecken();
        if (aktX <= dungeonDaten.breite - 2) feld[aktX + 1][aktY].aufdecken();
        if (aktX >= 1) feld[aktX - 1][aktY].aufdecken();
    }

    void goWest() {
        if (aktX < 1) return;
        if (feld[aktX - 1][aktY].kannNichtBetretenWerden()) return;
        aktX--;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
    }

    void goEast() {
        if (aktX > dungeonDaten.breite - 2) return;
        if (feld[aktX + 1][aktY].kannNichtBetretenWerden()) return;
        aktX++;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
    }

    void goNorth() {
        if (aktY < 1) return;
        if (feld[aktX][aktY - 1].kannNichtBetretenWerden()) return;
        aktY--;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
    }

    void goSouth() {
        if (aktY > dungeonDaten.hoehe - 2) return;
        if (feld[aktX][aktY + 1].kannNichtBetretenWerden()) return;
        aktY++;
        held.x = aktX;
        held.y = aktY;
        nachbarfelderAufdecken();
    }

    void paint(GraphicsContext g) {
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                feld[x][y].paint(g);
            }
        }

        //Umrandung um alle Felder
        g.setStroke(Color.WHITE);
        g.strokeRect(Main.randSize, Main.randSize, breite - 2 * Main.randSize - Main.randSize / dungeonDaten.breite * 3, dungeonDaten.hoehe * Main.feldSize);

        Painter.paint(assets.dinge, assets.helden, dungeonDaten, g);
    }

    void aktionAusfuehren() {
        if (assets.hatMonster(aktX, aktY)) {
            kaempfen(aktX, aktY);
        } else if (assets.hatHeiltrank(aktX, aktY)) {
            heilen();
        } else if (assets.hatSchwert(aktX, aktY)) {
            schwertAufnehmen();
        } else if (feld[aktX][aktY].hatVersteckteTuer()) {
            naechstesLevelStarten();
        } else if (assets.hatBossmonster(aktX, aktY)) {
            kaempfen(aktX + assets.getBossmonsterPosX(aktX, aktY), aktY + assets.getBossmonsterPosY(aktX, aktY));
        }
    }

    private void naechstesLevelStarten() {
        if (held.monsterGetoetetImLevel == dungeonDaten.anzahlMonsterProLevel[level]) {
            if (level + 1 < Main.ANZAHL_LEVEL) {
                level++;
                felderLaden(level);
                held.monsterGetoetetImLevel = 0;
            } else {
                Dialoge.beenden(held);
            }
        } else {
            Dialoge.erstMonsterToeten();
        }
    }

    private void schwertAufnehmen() {
        Musikspieler.playSound("schwertAufheben");

        Ding schwert = assets.getDing(aktX, aktY);

        held.angriff += schwert.angriff;
        held.ruestung += schwert.ruestung;
        schwert.nochSichtbar = false;
        held.schwerterAufgesammelt++;
    }

    private void heilen() {
        Musikspieler.playSound("heiltrankTrinken");

        Ding heiltrank = assets.getDing(aktX, aktY);

        held.leben += ThreadLocalRandom.current().nextInt(25, 50);
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

        Ding gegner = assets.getDing(x, y);

        int wert = ThreadLocalRandom.current().nextInt(1, 7);

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
            gegner.nochSichtbar = false;
        }
        if (held.leben <= 0) {
            Dialoge.sterben();
        }
    }
}
