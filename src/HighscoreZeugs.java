import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import static java.lang.System.currentTimeMillis;

class HighscoreZeugs {
    static private long zeitAmAnfang, zeitGebraucht;
    static private final Preferences preferences = Preferences.userNodeForPackage(HighscoreZeugs.class);

    static void zeitStarten() {
        zeitAmAnfang = currentTimeMillis();
    }

    static String getZeitGebrauchtString() {
        return zeitFormatieren(zeitGebraucht);
    }

    static void zeitStoppen() {
        zeitGebraucht = System.currentTimeMillis() - zeitAmAnfang;
    }

    static void highscoreSpeichern() {
        if (Long.parseLong(preferences.get("highscore", "10000000")) > zeitGebraucht) {
            preferences.put("highscore", Long.toString(zeitGebraucht));
        }
    }

    static String getHighscoreString() {
        return zeitFormatieren(Long.parseLong(preferences.get("highscore", "0")));
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
