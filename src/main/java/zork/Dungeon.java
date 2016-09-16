package zork;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

public class Dungeon {
    private Feld[][] feld;
    private DungeonDaten daten;
    public Held kurt;
    private int aktX, aktY;
    private long zeitAmAnfang, zeitGebraucht;
    Preferences preferences;

    @SuppressWarnings("fallthrough")
    public Dungeon() throws FileNotFoundException {
        daten = new DungeonDaten();
        feld = new Feld[daten.breite][daten.hoehe];
        kurt = new Held();
        zeitAmAnfang = System.currentTimeMillis();

        for (int y = 0; y < daten.hoehe; y++) {
            for (int x = 0; x < daten.breite; x++) {
                feld[x][y] = new Feld(x, y, daten.daten[y].charAt(x));
                if (daten.daten[y].charAt(x) == 'S') {
                    aktX = x;
                    aktY = y;
                    kurt.geheZu(aktX, aktY);
                }
            }
        }

        preferences = Preferences.userNodeForPackage(Dungeon.class);
    }

    public void nachbarfelderAufdecken() {
        if (aktY >= 1) feld[aktX][aktY - 1].aufdecken();
        if (aktY <= daten.hoehe - 2) feld[aktX][aktY + 1].aufdecken();
        if (aktX <= daten.breite - 2) feld[aktX + 1][aktY].aufdecken();
        if (aktX >= 1) feld[aktX - 1][aktY].aufdecken();
    }

    public void goWest() {
        if (aktX < 1) return;                                  // zork.Dungeon-Grenze West erreicht
        if (!feld[aktX - 1][aktY].kannBetretenWerden()) return;  // im Westen ist kein Weg
        aktX--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    public void goEast() {
        if (aktX > daten.breite - 2) return;                      // zork.Dungeon-Grenze Ost erreicht
        if (!feld[aktX + 1][aktY].kannBetretenWerden()) return;   // im Osten ist kein Weg
        aktX++;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    public void goNorth() {
        if (aktY < 1) return;                                    // zork.Dungeon-Grenze Nord erreicht
        if (!feld[aktX][aktY - 1].kannBetretenWerden()) return;    // im Norden kein Weg
        aktY--;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    public void goSouth() {
        if (aktY > daten.hoehe - 2) return;                        // zork.Dungeon-Grenze Sued erreicht
        if (!feld[aktX][aktY + 1].kannBetretenWerden()) return;    // im Sueden kein Weg
        aktY++;
        kurt.geheZu(aktX, aktY);
        nachbarfelderAufdecken();
    }

    public void paint(Graphics g) {
        for (int y = 0; y < daten.hoehe; y++)
            for (int x = 0; x < daten.breite; x++)
                feld[x][y].paint(g);
        kurt.paint(g);
        feld[aktX][aktY].werteVomMonsterZeigen(g);
    }

    public void kaempfen() {
        if (feld[aktX][aktY].hatMonster()) {
            kurt.kaempfe(feld[aktX][aktY].gibMonster());
        }
        if (kurt.monsterGetoetet == daten.getAnzahlMonster()) {
            zeitStoppen();
            highscoreSpeichern();
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(),
                    "Du hast in " + zeitGebrauchtString() + " alle Monster getötet. Dein Highscore: " + preferences.get("highscoreString", "0"),
                    "Fertig", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void zeitStoppen() {
        zeitGebraucht = System.currentTimeMillis()-zeitAmAnfang;
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
