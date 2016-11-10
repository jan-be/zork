import com.esotericsoftware.minlog.Log;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();
    static double feldSize = 40, randSize;
    static String ipAdresse;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        String name = Dialoge.name();
        Assets.init(name);
        Log.set(Log.LEVEL_WARN);


        String vielleichtIp = Dialoge.mitServerVerbinden();
        if (vielleichtIp == null && Dialoge.isServer()) {
            new DerServer();
            ipAdresse = "localhost";
        } else if (vielleichtIp == null) {
            ipAdresse = Dialoge.ipAdresse();
        } else {
            ipAdresse = vielleichtIp;
        }


        DerClient client = new DerClient(name);

        int monitor = 0;
        stage.initStyle(StageStyle.UNDECORATED);
        Rectangle2D size = Dialoge.bildschirmWaehlen(monitor);

        DungeonDaten dungeonDaten = new DungeonDaten();
        randSize = size.getWidth() / 20;
        feldSize = (int) (size.getWidth() / dungeonDaten.breite - randSize / dungeonDaten.breite * 2);
        Dungeon dungeon = new Dungeon(dungeonDaten, size.getWidth(), stage, name, client);

        stage.setTitle("ZORK");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(size.getMinX());
        stage.setY(size.getMinY());
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(size.getWidth(), size.getHeight() + 40);
        canvas.setFocusTraversable(true);
        root.getChildren().add(pane);
        pane.getChildren().add(canvas);
        pane.setStyle("-fx-background-color: black");
        scene.getStylesheets().add(this.getClass() .getResource("/style.css").toExternalForm());
        stage.setMaximized(true);

        Bilder.init();
        client.dungeonInit(dungeon);
        new MuteButton(size, root);

        client.textGebiet = new TextGebiet(client, root, size);

        GraphicsContext g = canvas.getGraphicsContext2D();
        dungeon.init(g);
        Painter.init(dungeonDaten, name, g);
        dungeon.paint();
        stage.show();

        Musikspieler.playHintergrundMusik();

        new KeyEventHandler(scene, dungeon);

        client.dingeVomServerEinbauen();

        //TODO Inventarframe einbauen
//        InventarFrame.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
