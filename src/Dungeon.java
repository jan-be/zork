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
        } else if (feld[aktX][aktY].hatKnife()) {
            knifeAufnehmen();
        } else if (feld[aktX][aktY].hatVersteckteTuer()) {
            naechstesLevelStarten();
        }
    }

    private void naechstesLevelStarten() {
        if (level < Main.ANZAHL_LEVEL - 1) {
            if (kurt.monsterGetoetetImLevel == dungeonDaten.anzahlMonsterProLevel[level]) {
                level++;
                felderLaden(level);
                kurt.monsterGetoetetImLevel = 0;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Einfach schlecht.");
                alert.setHeaderText(null);
                alert.setContentText("Du musst erst alle Monster töten");
                alert.showAndWait();
            }
        } else {
            spielBeenden();
        }
    }

    private void knifeAufnehmen() {
        Musikspieler.playAktionsSound("schwertAufheben");
        kurt.aufnehmen(feld[aktX][aktY].gibKnife());
    }

    private void heilen() {
        Musikspieler.playAktionsSound("heiltrankTrinken");
        kurt.heilen(feld[aktX][aktY].gibHeiltrank());
    }

    private void kaempfen() {
        Musikspieler.playAktionsSound("schlag");
        kurt.kaempfe(feld[aktX][aktY].gibMonster());
    }

    private void spielBeenden() {
        HighscoreZeugs.zeitStoppen();
        HighscoreZeugs.highscoreSpeichern();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Props.");
        alert.setHeaderText("Du hast das Spiel beendet");
        alert.setContentText("Du hast das Spiel in " + HighscoreZeugs.getZeitGebrauchtString() + " beendet. " +
                "\nDein Highscore: " + HighscoreZeugs.getHighscoreString());
        alert.showAndWait();
        System.exit(0);
    }
}
