import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {

    static final int FELD_SIZE = 40;
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();

    private Dungeon brett;

    @Override
    public void start(Stage stage) throws Exception {
        DungeonDaten dungeonDaten = new DungeonDaten();
        brett = new Dungeon(dungeonDaten);

        stage.setTitle("ZORK");
        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);
        Canvas canvas = new Canvas(40 + dungeonDaten.breite * FELD_SIZE, 140 + dungeonDaten.hoehe * FELD_SIZE);
        group.getChildren().add(canvas);

        ImageView imageView = new ImageView(BilderGetter.dancerAnimation);
        imageView.setX(50);
        imageView.setY(50);
        group.getChildren().add(imageView);

        GraphicsContext g = canvas.getGraphicsContext2D();

        brett.paint(g);

        stage.show();

        Musikspieler.playHintergrundMusik();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W) {
                brett.goNorth();
            } else if (key.getCode() == KeyCode.A) {
                brett.goWest();
            } else if (key.getCode() == KeyCode.S) {
                brett.goSouth();
            } else if (key.getCode() == KeyCode.D) {
                brett.goEast();
            } else if (key.getCode() == KeyCode.UP) {
                brett.goNorth();
            } else if (key.getCode() == KeyCode.LEFT) {
                brett.goWest();
            } else if (key.getCode() == KeyCode.DOWN) {
                brett.goSouth();
            } else if (key.getCode() == KeyCode.RIGHT) {
                brett.goEast();
            } else if (key.getCode() == KeyCode.SPACE) {
                brett.aktionAusfuehren();
            }

            brett.paint(g);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
