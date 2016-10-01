import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

class Dungeon {
    private Feld[][] feld;
    private Held kurt;
    private int aktX, aktY, level;
    private long zeitAmAnfang, zeitGebraucht;
    private Preferences preferences = Preferences.userNodeForPackage(Dungeon.class);
    private DungeonDaten dungeonDaten;
    private BilderGetter bilderGetter;

    Dungeon(DungeonDaten dungeonDaten, BilderGetter bilderGetter) {
        this.dungeonDaten = dungeonDaten;
        this.bilderGetter = bilderGetter;
        feld = new Feld[dungeonDaten.breite][dungeonDaten.hoehe];
        kurt = new Held(dungeonDaten, bilderGetter);
        zeitAmAnfang = System.currentTimeMillis();
        felderLaden(level);
    }

    private void felderLaden(int level) {
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                feld[x][y] = new Feld(x, y, dungeonDaten.alleLevelDaten[level][y].charAt(x), dungeonDaten, bilderGetter);
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
        if (!feld[aktX - 1][aktY].kannBetretenWerden()) return;
        aktX--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goEast() {
        if (aktX > dungeonDaten.breite - 2) return;
        if (!feld[aktX + 1][aktY].kannBetretenWerden()) return;
        aktX++;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goNorth() {
        if (aktY < 1) return;
        if (!feld[aktX][aktY - 1].kannBetretenWerden()) return;
        aktY--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goSouth() {
        if (aktY > dungeonDaten.hoehe - 2) return;
        if (!feld[aktX][aktY + 1].kannBetretenWerden()) return;
        aktY++;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void paint(Graphics g) {
        for (int y = 0; y < dungeonDaten.hoehe; y++) {
            for (int x = 0; x < dungeonDaten.breite; x++) {
                feld[x][y].paint(g);
            }
        }
        kurt.paint(g);
        feld[aktX][aktY].werteVomGegenstandZeigen(g);
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
        if (level < Frame.ANZAHL_LEVEL-1) {
            level++;
            felderLaden(level);
        } else {
            JOptionPane.showMessageDialog(null, "Damn Daniel, \n du hast es geschafft.", "Props.", 1);
        }
    }

    private void knifeAufnehmen() {
        if (feld[aktX][aktY].hatKnife()) {
            kurt.aufnehmen(feld[aktX][aktY].gibKnife());
        }
    }

    private void heilen() {
        if (feld[aktX][aktY].hatHeiltrank()) {
            kurt.heilen(feld[aktX][aktY].gibHeiltrank());
        }
    }

    private void kaempfen() {
        if (feld[aktX][aktY].hatMonster()) {
            kurt.kaempfe(feld[aktX][aktY].gibMonster());
        }
        if (kurt.monsterGetoetet == dungeonDaten.anzahlMonsterProLevel[level]) {
            zeitStoppen();
            highscoreSpeichern();
            JOptionPane.showMessageDialog(null, "Du hast in " + zeitFormatieren(zeitGebraucht) + " alle Monster getötet. " +
                            "\n Dein Highscore: " + zeitFormatieren(Long.parseLong(preferences.get("highscore", "0"))),
                    "Fertig", 1);
        }
    }

    private void zeitStoppen() {
        zeitGebraucht = System.currentTimeMillis() - zeitAmAnfang;
    }

    private void highscoreSpeichern() {
        if (Long.parseLong(preferences.get("highscore", "10000000")) > zeitGebraucht) {
            preferences.put("highscore", Long.toString(zeitGebraucht));
        }
    }

    private static String zeitFormatieren(long millis) {
        long minuten = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minuten);
        long sekunden = TimeUnit.MILLISECONDS.toSeconds(millis);

        if (minuten == 1 && sekunden == 1) {
            return minuten + " Minute " + sekunden + " Sekunde";
        } else if (minuten == 1) {
            return minuten + " Minute " + sekunden + " Sekunden";
        } else if (minuten > 1 && sekunden == 1) {
            return minuten + " Minuten " + sekunden + " Sekunde";
        } else if (minuten > 1) {
            return minuten + " Minuten " + sekunden + " Sekunden";
        } else if (sekunden == 1) {
            return ("scheiß cheater (ง ͠° ͟ل͜ ͡°)ง");
        } else {
            return sekunden + " Sekunden";
        }
    }

}
