import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Optional;

public class Main extends Application {

    static double feldSize = 40, randSize;
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();
    private static int monitor = 0;

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void start(Stage stage) throws Exception {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("monitor nummer");
        dialog.setHeaderText("Gib den Monitor ein, auf dem das Spiel laufen soll");
        dialog.setContentText("mit abbrechen startet es auf dem Primärmonitor");
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
        DungeonDaten dungeonDaten = new DungeonDaten();
        randSize = size.getWidth() / 20;
        feldSize = (int) (size.getWidth() / dungeonDaten.breite - randSize / dungeonDaten.breite * 2);
        Dungeon brett = new Dungeon(dungeonDaten, size.getWidth());

        stage.setTitle("ZORK");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(size.getMinX());
        stage.setY(size.getMinY());
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(size.getWidth(), size.getHeight() + 40);
        root.getChildren().add(pane);
        pane.getChildren().add(canvas);
        pane.setStyle("-fx-background-color: black");

        MuteButton.buttonZeigen(size, root);

        GraphicsContext g = canvas.getGraphicsContext2D();
        stage.show();

        brett.paint(g);

        stage.setMaximized(true);

        Musikspieler.playHintergrundMusik();

        KeyEventHandler.handleKeys(scene, brett, g, canvas);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
