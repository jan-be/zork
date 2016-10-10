import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

class Dungeon {
    private final Feld[][] feld;
    private final Held kurt;
    private final double breite;
    private final DungeonDaten dungeonDaten;
    private final Rucksack rucksack = new Rucksack();
    private int aktX, aktY, level;

    Dungeon(DungeonDaten dungeonDaten, double breite) {
        this.dungeonDaten = dungeonDaten;
        this.breite = breite;
        feld = new Feld[dungeonDaten.breite][dungeonDaten.hoehe];
        kurt = new Held(dungeonDaten);
        felderLaden(level);
        HighscoreZeugs.zeitStarten();
    }

    private void felderLaden(int level) {
        rucksack.stack.clear();
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                feld[x][y] = new Feld(x, y, dungeonDaten.alleLevelDaten[level][y].charAt(x), rucksack);
                if (dungeonDaten.alleLevelDaten[level][y].charAt(x) == 'S') {
                    aktX = x;
                    aktY = y;
                    kurt.geheZu(aktX, aktY);
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
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goEast() {
        if (aktX > dungeonDaten.breite - 2) return;
        if (feld[aktX + 1][aktY].kannNichtBetretenWerden()) return;
        aktX++;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goNorth() {
        if (aktY < 1) return;
        if (feld[aktX][aktY - 1].kannNichtBetretenWerden()) return;
        aktY--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goSouth() {
        if (aktY > dungeonDaten.hoehe - 2) return;
        if (feld[aktX][aktY + 1].kannNichtBetretenWerden()) return;
        aktY++;
        kurt.geheZu(aktX, aktY);
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

        for (int i = 0; i < rucksack.stack.size(); i++) {
            rucksack.stack.get(i).paint(g);
        }

        kurt.paint(g);
    }

    void aktionAusfuehren() {
        if (rucksack.hatMonster(aktX, aktY)) {
            kaempfen(aktX, aktY);
        } else if (rucksack.hatHeiltrank(aktX, aktY)) {
            heilen();
        } else if (rucksack.hatSchwert(aktX, aktY)) {
            schwertAufnehmen();
        } else if (feld[aktX][aktY].hatVersteckteTuer()) {
            naechstesLevelStarten();
        } else if (rucksack.hatBossmonster(aktX, aktY)) {
            kaempfen(aktX + rucksack.getBossmonsterPosX(aktX, aktY), aktY + rucksack.getBossmonsterPosY(aktX, aktY));
        }
    }

    private void naechstesLevelStarten() {

        if (kurt.monsterGetoetetImLevel == dungeonDaten.anzahlMonsterProLevel[level]) {
            if (level + 1 < Main.ANZAHL_LEVEL) {
                level++;
                felderLaden(level);
                kurt.monsterGetoetetImLevel = 0;
            } else {
                Dialoge.beenden(kurt);
            }
        } else {
            Dialoge.erstMonsterToeten();
        }
    }

    private void schwertAufnehmen() {
        Musikspieler.playSound("schwertAufheben");
        kurt.aufnehmen(rucksack.get(aktX, aktY));
    }

    private void heilen() {
        Musikspieler.playSound("heiltrankTrinken");
        kurt.heilen(rucksack.get(aktX, aktY));
    }

    private void kaempfen(int x, int y) {
        Musikspieler.playSound("schlag");
        kurt.kaempfe(rucksack.get(x, y));
    }
}
