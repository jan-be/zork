import com.esotericsoftware.kryonet.Client;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import javax.swing.*;
import java.net.InetAddress;

import static javax.swing.JOptionPane.showInputDialog;

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
        String name = JOptionPane.showInputDialog(null,
                "Wie willst du heißen", "Name");
        if (name == null) System.exit(0);
        return name;
    }

    static String ipAdresse() {
        return showInputDialog(null,
                "Wie lautet die IP-Adresse von deinem Mitspieler", "localhost");
    }

    static void beenden(Held held, Dungeon dungeon) {
        HighscoreZeugs.zeitStoppen();
        HighscoreZeugs.zeitHighscoreSpeichern();
        HighscoreZeugs.goldHighscoreSpeichern(held.gold);
        double ep = 1000000 / (double) HighscoreZeugs.zeitGebraucht * held.gold;
        HighscoreZeugs.epHighscoreSpeichern((int) Math.round(ep));

        Object[] optionen = {"Nochmal versuchen", "Verlassen"};

        int n = JOptionPane.showOptionDialog(null,
                "Du hast das Spiel in " + HighscoreZeugs.getZeitGebrauchtString() + " beendet. " +
                        "\nDein Highscore: " + HighscoreZeugs.getHighscoreString() +
                        "\nDu hast dabei " + held.gold + " Gold eingesammelt" +
                        "\nDein Highscore: " + HighscoreZeugs.getGoldHighscore() + " Gold" +
                        "\nUnd so " + Math.round(ep) + " Erfahrungspunkte gesammelt" +
                        "\nDein Highscore: " + HighscoreZeugs.getEpHighscore() + " EP",
                "Spiel beendet,",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, optionen, optionen[1]);
        if (n == 1) System.exit(0);
        else {
            dungeon.neuStarten();
        }
    }

    static Rectangle2D bildschirmWaehlen(int monitor) {
        String ergebnis = showInputDialog(
                "Gib den Monitor ein, auf dem das Spiel laufen soll", "1");
        if (ergebnis == null) System.exit(0);
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

    static String mitServerVerbinden() {
        Client client = new Client();
        InetAddress address = client.discoverHost(54777, 500);

        if (address != null) {
            String ipString = address.toString().substring(1);
            Object[] optionen = {
                    "Ja",
                    "Nein"};
            int n = JOptionPane.showOptionDialog(null,
                    "Im Netzwerk läuft bereits ein Server mit IP-Adresse \n" +
                            ipString + "\n" +
                            "möchtest du dich damit verbinden?",
                    "Mit Server verbinden?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    optionen,
                    optionen[0]);
            if (n == 0) {
                return ipString;
            }
        }

        return null;
    }

    static void serverGeleftet() {
        JOptionPane.showMessageDialog(null, "der Server hat das Spiel verlassen.\n" +
                "Das Spiel wird beendet.", "ALARM", 0);
        System.exit(0);
    }
}
