import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import static java.lang.System.currentTimeMillis;

class HighscoreZeugs {
    static private long zeitAmAnfang;

    static long zeitGebraucht;
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

    static void zeitHighscoreSpeichern() {
        if (Long.parseLong(preferences.get("zeithighscore", "10000000")) > zeitGebraucht) {
            preferences.put("zeithighscore", Long.toString(zeitGebraucht));
        }
    }

    private static long getZeitHighscore() {
        return Long.parseLong(preferences.get("zeithighscore", "0"));
    }

    static void epHighscoreSpeichern(int ep) {
        if (Integer.parseInt(preferences.get("ephighscore", "0")) < ep) {
            preferences.put("ephighscore", Integer.toString(ep));
        }
    }

    static int getEpHighscore() {
        return Integer.parseInt(preferences.get("ephighscore", "0"));
    }

    static String getHighscoreString() {
        return zeitFormatieren(getZeitHighscore());
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

    static void goldHighscoreSpeichern(int gold) {
        if (Integer.parseInt(preferences.get("goldhighscore", "0")) < gold) {
            preferences.put("goldhighscore", Integer.toString(gold));
        }
    }

    static int getGoldHighscore() {
        return Integer.parseInt(preferences.get("goldhighscore", "0"));
    }
}
