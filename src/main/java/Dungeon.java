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
        if (aktX < 1) return;                                  // zork.Dungeon-Grenze West erreicht
        if (!feld[aktX - 1][aktY].kannBetretenWerden()) return;  // im Westen ist kein Weg
        aktX--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goEast() {
        if (aktX > dungeonDaten.breite - 2) return;                      // zork.Dungeon-Grenze Ost erreicht
        if (!feld[aktX + 1][aktY].kannBetretenWerden()) return;   // im Osten ist kein Weg
        aktX++;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goNorth() {
        if (aktY < 1) return;                                    // zork.Dungeon-Grenze Nord erreicht
        if (!feld[aktX][aktY - 1].kannBetretenWerden()) return;    // im Norden kein Weg
        aktY--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    void goSouth() {
        if (aktY > dungeonDaten.hoehe - 2) return;                        // zork.Dungeon-Grenze Sued erreicht
        if (!feld[aktX][aktY + 1].kannBetretenWerden()) return;    // im Sueden kein Weg
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

    void aktionAusfuehren() { //wenn true, dann wird nächstes Level gestartet
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

    private void kaempfen() {
        if (feld[aktX][aktY].hatMonster()) {
            kurt.kaempfe(feld[aktX][aktY].gibMonster());
        }
        if (kurt.monsterGetoetet == dungeonDaten.getAnzahlMonster()) {
            zeitStoppen();
            highscoreSpeichern();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Du hast in " + zeitGebrauchtString() + " alle Monster getötet. Dein Highscore: " + preferences.get("highscoreString", "0"),
                    "Fertig", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void heilen() {
        if (feld[aktX][aktY].hatHeiltrank()) {
            kurt.heilen(feld[aktX][aktY].gibHeiltrank());
        }
    }

    private void zeitStoppen() {
        zeitGebraucht = System.currentTimeMillis() - zeitAmAnfang;
    }

    private String zeitGebrauchtString() {
        return String.format("%02d min, %02d sec", TimeUnit.MILLISECONDS.toMinutes(zeitGebraucht),
                TimeUnit.MILLISECONDS.toSeconds(zeitGebraucht) +
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(zeitGebraucht)));
    }

    private void highscoreSpeichern() {
        if (Long.parseLong(preferences.get("highscore", "10000000")) > zeitGebraucht) {
            preferences.put("highscore", Long.toString(zeitGebraucht));
            preferences.put("highscoreString", zeitGebrauchtString());
        }
    }
}
