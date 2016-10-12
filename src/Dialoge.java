import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import org.controlsfx.dialog.Dialogs;

import java.util.Optional;

@SuppressWarnings("deprecation")
class Dialoge {
    private static boolean isJavaNeuer840() {
        String[] gesplitteteVerion = System.getProperty("java.version").split("_");
        Double majorVersion = Double.parseDouble(System.getProperty("java.specification.version"));
        return (Integer.parseInt(gesplitteteVerion[1]) >= 40 && majorVersion == 1.8) ||
                majorVersion > 1.8;
    }

    static boolean isServer() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Server oder Host");
        alert.setHeaderText("Server oder Host");
        alert.setContentText("Ist dieser PC Server oder Host");
        javafx.scene.control.ButtonType server = new javafx.scene.control.ButtonType("Server");
        javafx.scene.control.ButtonType client = new javafx.scene.control.ButtonType("Client");

        alert.getButtonTypes().setAll(server, client);

        Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        return result.get() == server;
    }

    static String name() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog("dick");
        dialog.setTitle("Name");
        dialog.setHeaderText("Wie willst du heißen");
        dialog.setContentText("gib deinen Namen ein");
        Optional<String> ergebnis = dialog.showAndWait();
        return ergebnis.get();

    }

    static String ipAdresse() {
        javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog("localhost");
        dialog.setTitle("IP-Adresse");
        dialog.setHeaderText("Wie lautet die IP-Adresse von deinem Mitspieler");
        dialog.setContentText("gib die IP-Adresse ein");
        Optional<String> ergebnis = dialog.showAndWait();
        return ergebnis.get();

    }

    static void beenden(Held kurt) {
        if (isJavaNeuer840()) {
            HighscoreZeugs.zeitStoppen();
            HighscoreZeugs.zeitHighscoreSpeichern();
            HighscoreZeugs.goldHighscoreSpeichern(kurt.gold);
            double ep = 10000 / (double) HighscoreZeugs.getZeitHighscore() * HighscoreZeugs.getGoldHighscore();
            HighscoreZeugs.epHighscoreSpeichern((int) Math.round(ep));

            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
            alert.setTitle("Props.");
            alert.setHeaderText("Du hast das Spiel beendet");
            alert.setContentText("Du hast das Spiel in " + HighscoreZeugs.getZeitGebrauchtString() + " beendet. " +
                    "\nDein Highscore: " + HighscoreZeugs.getHighscoreString() +
                    "\nDu hast dabei " + kurt.gold + " Gold eingesammelt" +
                    "\nDein Highscore: " + HighscoreZeugs.getGoldHighscore() + " Gold" +
                    "\nUnd so " + Math.round(ep) + " Erfahrungspunkte gesammelt" +
                    "\nDein Highscore: " + HighscoreZeugs.getEpHighscore() + " EP");
            alert.showAndWait();
            System.exit(0);
        } else {
            HighscoreZeugs.zeitStoppen();
            HighscoreZeugs.zeitHighscoreSpeichern();
            HighscoreZeugs.goldHighscoreSpeichern(kurt.gold);
            double ep = 10000 / (double) HighscoreZeugs.getZeitHighscore() * HighscoreZeugs.getGoldHighscore();
            HighscoreZeugs.epHighscoreSpeichern((int) Math.round(ep));

            Dialogs.create()
                    .title("Props.")
                    .masthead("Du hast das Spiel beendet")
                    .message("Du hast das Spiel in " + HighscoreZeugs.getZeitGebrauchtString() + " beendet. " +
                            "\nDein Highscore: " + HighscoreZeugs.getHighscoreString() +
                            "\nDu hast dabei " + kurt.gold + " Gold eingesammelt" +
                            "\nDein Highscore: " + HighscoreZeugs.getGoldHighscore() + " Gold" +
                            "\nUnd so " + Math.round(ep) + " Erfahrungspunkte gesammelt" +
                            "\nDein Highscore: " + HighscoreZeugs.getEpHighscore() + " EP")
                    .showInformation();
            System.exit(0);
        }
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    static Rectangle2D bildschirmWaehlen(int monitor) {
        if (isJavaNeuer840()) {
            javafx.scene.control.TextInputDialog dialog = new javafx.scene.control.TextInputDialog();
            dialog.setTitle("Gib den Monitor ein");
            dialog.setHeaderText("Gib den Monitor ein, auf dem das Spiel laufen soll");
            dialog.setContentText("Wenn leer startet es auf dem Primärmonitor");
            Optional<String> ergebnis = dialog.showAndWait();
            Rectangle2D size = null;
            try {
                if (!ergebnis.get().equals("")) {
                    monitor = Integer.parseInt(ergebnis.get()) - 1;
                }
                size = Screen.getScreens().get(monitor).getVisualBounds();
            } catch (Exception e) {
                javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setHeaderText("Es gibt den Monitor nicht");
                alert.setContentText("Was kannst du eigentlich?");
                alert.showAndWait();
                System.exit(1);
            }
            return size;
        } else {
            Optional<String> ergebnis = Dialogs.create()
                    .title("Gib den Monitor ein")
                    .masthead("Gib den Monitor ein, auf dem das Spiel laufen soll")
                    .message("Wenn leer startet es auf dem Primärmonitor")
                    .showTextInput();
            Rectangle2D size = null;
            try {
                if (!ergebnis.get().equals("")) {
                    monitor = Integer.parseInt(ergebnis.get()) - 1;
                }
                size = Screen.getScreens().get(monitor).getVisualBounds();
            } catch (Exception e) {
                Dialogs.create()
                        .title("döööh")
                        .masthead("Es gibt den Monitor nicht")
                        .message("Was kannst du eigentlich?")
                        .showError();
                System.exit(1);
            }
            return size;
        }
    }

    static void sterben() {
        if (isJavaNeuer840()) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Einfach zu schlecht.");
            alert.setHeaderText("Du bist gestorben");
            alert.setContentText("Das ist wirklich nicht so gedacht, dass man zwischendurch stirbt. " +
                    "\nDu musst wirklich schlecht sein.");
            alert.showAndWait();

            System.exit(0);
        } else {
            Dialogs.create()
                    .title("Einfach zu schlecht")
                    .masthead("Du bist gestorben")
                    .message("Das ist wirklich nicht so gedacht, dass man zwischendurch stirbt." +
                            "\nDu musst wirklich schlecht sein.")
                    .showWarning();
            System.exit(0);
        }
    }

    static void erstMonsterToeten() {
        if (isJavaNeuer840()) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Einfach schlecht.");
            alert.setHeaderText(null);
            alert.setContentText("Du musst erst alle Monster töten");
            alert.showAndWait();
        } else {
            Dialogs.create()
                    .title("Einfach schlecht.")
                    .masthead("Du musst erst alle Monster töten")
                    .message("hmm")
                    .showWarning();
        }
    }
}
