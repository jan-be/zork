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
        level++;
        felderLaden(level);
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
        if (kurt.monsterGetoetet == dungeonDaten.anzahlMonster) {
            zeitStoppen();
            highscoreSpeichern();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Du hast in " + zeitFormatieren(zeitGebraucht) + " alle Monster getötet. Dein Highscore: " +
                            zeitFormatieren(Long.parseLong(preferences.get("highscore", "0"))),
                    "Fertig", JOptionPane.INFORMATION_MESSAGE);
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
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return (String.valueOf(minutes) +
                " Minuten " +
                seconds +
                " Sekunden");
    }


}
