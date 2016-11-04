import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javax.swing.*;

class Dialoge {
    static boolean isServer() {
        Object[] optionen = {
                "Server",
                "Client"};
        int n = JOptionPane.showOptionDialog(null,
                "Ist dieser PC Server oder Client",
                "Server oder Client?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionen,
                optionen[0]);
        return n == 0;
    }

    static String name() {
        return JOptionPane.showInputDialog(null,
                "Wie willst du heißen", "Name");
    }

    static String ipAdresse() {
        return JOptionPane.showInputDialog(null,
                "Wie lautet die IP-Adresse von deinem Mitspieler", "localhost");
    }

    static void beenden(Held kurt) {
        HighscoreZeugs.zeitStoppen();
        HighscoreZeugs.zeitHighscoreSpeichern();
        HighscoreZeugs.goldHighscoreSpeichern(kurt.gold);
        double ep = 10000 / (double) HighscoreZeugs.getZeitHighscore() * HighscoreZeugs.getGoldHighscore();
        HighscoreZeugs.epHighscoreSpeichern((int) Math.round(ep));

        JOptionPane.showMessageDialog(null,
                "Du hast das Spiel in " + HighscoreZeugs.getZeitGebrauchtString() + " beendet. " +
                "\nDein Highscore: " + HighscoreZeugs.getHighscoreString() +
                "\nDu hast dabei " + kurt.gold + " Gold eingesammelt" +
                "\nDein Highscore: " + HighscoreZeugs.getGoldHighscore() + " Gold" +
                "\nUnd so " + Math.round(ep) + " Erfahrungspunkte gesammelt" +
                "\nDein Highscore: " + HighscoreZeugs.getEpHighscore() + " EP");
        System.exit(0);
    }

    static Rectangle2D bildschirmWaehlen(int monitor) {
        String ergebnis = JOptionPane.showInputDialog(
                "Gib den Monitor ein, auf dem das Spiel laufen soll", "1");
        Rectangle2D size = null;
        try {
            if (!ergebnis.equals("")) {
                monitor = Integer.parseInt(ergebnis) - 1;
            }
            size = Screen.getScreens().get(monitor).getVisualBounds();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Es gibt den Monitor nicht", "ALARM", 0);
            System.exit(1);
        }
        return size;
    }

    static boolean sterben() {
        Object[] optionen = {"Verlassen", "Nochmal versuchen"};

        int n = JOptionPane.showOptionDialog(null,
                "Du bist gestorben.", "Tot,",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, optionen, optionen[1]);
        if (n == 0) System.exit(0);
        return n == 1;
    }

    static void erstMonsterToeten() {
        JOptionPane.showMessageDialog(null,
                "Du musst erst alle Monster töten",
                "so läuft's halt", JOptionPane.WARNING_MESSAGE);
    }
}
