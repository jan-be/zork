import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;

class Dungeon {
    private final Feld[][] feld;
    private final Held kurt;
    private int aktX, aktY, level;
    private final double breite;
    private final DungeonDaten dungeonDaten;

    Dungeon(DungeonDaten dungeonDaten, double breite) {
        this.dungeonDaten = dungeonDaten;
        this.breite = breite;
        feld = new Feld[dungeonDaten.breite][dungeonDaten.hoehe];
        kurt = new Held(dungeonDaten);
        felderLaden(level);
        HighscoreZeugs.zeitStarten();
    }

    private void felderLaden(int level) {
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                feld[x][y] = new Feld(x, y, dungeonDaten.alleLevelDaten[level][y].charAt(x));
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
        kurt.paint(g);

        //Umrandung um alle Felder
        g.setStroke(Color.WHITE);
        g.strokeRect(Main.randSize, Main.randSize, breite - 2 * Main.randSize - Main.randSize / dungeonDaten.breite * 3, dungeonDaten.hoehe * Main.feldSize);
    }

    void aktionAusfuehren() {
        if (feld[aktX][aktY].hatMonster()) {
            kaempfen();
        } else if (feld[aktX][aktY].hatHeiltrank()) {
            heilen();
        } else if (feld[aktX][aktY].hatSchwert()) {
            schwertAufnehmen();
        } else if (feld[aktX][aktY].hatVersteckteTuer()) {
            naechstesLevelStarten();
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
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Einfach schlecht.");
            alert.setHeaderText(null);
            alert.setContentText("Du musst erst alle Monster töten");
            alert.showAndWait();
        }
    }

    private void schwertAufnehmen() {
        Musikspieler.playSound("schwertAufheben");
        kurt.aufnehmen(feld[aktX][aktY].gibGegenstand());
    }

    private void heilen() {
        Musikspieler.playSound("heiltrankTrinken");
        kurt.heilen(feld[aktX][aktY].gibGegenstand());
    }

    private void kaempfen() {
        Musikspieler.playSound("schlag");
        kurt.kaempfe(feld[aktX][aktY].gibGegenstand());
    }
}
