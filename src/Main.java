import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {
    static double feldSize = 40, randSize;
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();

    @Override
    public void start(Stage stage) throws Exception {
        int monitor = 0;
        Rectangle2D size = Dialoge.bildschirmWaehlen(stage, monitor);

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
        stage.setMaximized(true);

        Bilder.init();
        new MuteButton(size, root);

        GraphicsContext g = canvas.getGraphicsContext2D();
        stage.show();

        brett.paint(g);

        Musikspieler.playHintergrundMusik();

        new KeyEventHandler(scene, brett, g, canvas);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
