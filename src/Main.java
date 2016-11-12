import com.esotericsoftware.minlog.Log;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
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

        //IP-Adresse zum verbinden wählen
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

        //Auflösung festlegen
        int monitor = 0;
        Rectangle2D size = Dialoge.bildschirmWaehlen(monitor);
        DungeonDaten dungeonDaten = new DungeonDaten();
        randSize = size.getWidth() / 20;
        feldSize = (int) (size.getWidth() / dungeonDaten.breite - randSize / dungeonDaten.breite * 2);
        Dungeon dungeon = new Dungeon(dungeonDaten, size.getWidth(), stage, name, client);

        //JavaFX initialisieren
        stage.setTitle("ZORK");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(size.getMinX());
        stage.setY(size.getMinY());
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(size.getWidth(), size.getHeight() + 40);
        canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> canvas.requestFocus());
        root.getChildren().add(pane);
        pane.getChildren().add(canvas);
        pane.setStyle("-fx-background-color: black");
        scene.getStylesheets().add(this.getClass().getResource("/style.css").toExternalForm());
        stage.setMaximized(true);

        //auskommentieren für Fenstermodus
        stage.initStyle(StageStyle.UNDECORATED);

        //Man könnte es zwar auch in einer Zeile schreiben, aber so finde ich es übersichtlicher
        //noinspection UnnecessaryLocalVariable
        TextFeld textFeld = new TextFeld(client, root, size);
        client.textFeld = textFeld;

        //Dinge, die nicht am Anfang passieren dürfen
        Bilder.init();
        client.dungeonInit(dungeon);
        new MuteButton(size, root);
        GraphicsContext g = canvas.getGraphicsContext2D();
        dungeon.init(g);
        dungeon.paint();
        Musikspieler.playHintergrundMusik();
        new KeyEventHandler(scene, dungeon);
        client.dingeVomServerEinbauen();

        //als allerletztes wird das Fenster angezeigt
        stage.show();
    }

    @Override
    public void stop() throws Exception {
        System.exit(0);
    }
}
