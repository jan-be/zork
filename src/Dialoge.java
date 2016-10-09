import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

class Dialoge {
    static void beenden(Held kurt) {
        HighscoreZeugs.zeitStoppen();
        HighscoreZeugs.zeitHighscoreSpeichern();
        HighscoreZeugs.goldHighscoreSpeichern(kurt.gold);
        double ep = 1000 / (double) HighscoreZeugs.getZeitHighscore() * HighscoreZeugs.getGoldHighscore();
        HighscoreZeugs.epHighscoreSpeichern((int) Math.round(ep));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
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
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    static Rectangle2D bildschirmWaehlen(Stage stage, int monitor) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Gib den Monitor ein");
        dialog.setHeaderText("Gib den Monitor ein, auf dem das Spiel laufen soll");
        dialog.setContentText("Wenn leer startet es auf dem Primärmonitor");
        Optional<String> ergebnis = dialog.showAndWait();
        stage.initStyle(StageStyle.UNDECORATED);
        Rectangle2D size = null;
        try {
            if (!ergebnis.get().equals("")) {
                monitor = Integer.parseInt(ergebnis.get()) - 1;
            }
            size = Screen.getScreens().get(monitor).getVisualBounds();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Es gibt den Monitor nicht");
            alert.setContentText("Was kannst du eigentlich?");
            alert.showAndWait();
            System.exit(1);
        }
        return size;
    }

    static void sterben() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Einfach zu schlecht.");
        alert.setHeaderText("Du bist gestorben");
        alert.setContentText("Das ist wirklich nicht so gedacht, dass man zwischendurch stirbt. " +
                "\nDu musst wirklich schlecht sein.");
        alert.showAndWait();

        System.exit(0);
    }
}
