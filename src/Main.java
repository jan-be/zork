import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    static double feldSize = 40, randSize;
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();
    private static final int monitor = 1;
    private boolean muted = false;

    private Dungeon brett;

    @Override
    public void start(Stage stage) throws Exception {
        stage.initStyle(StageStyle.UNDECORATED);
        DungeonDaten dungeonDaten = new DungeonDaten();
        Rectangle2D size = Screen.getScreens().get(monitor).getVisualBounds();
        randSize = size.getWidth() / 20;
        feldSize = (int) (size.getWidth() / dungeonDaten.breite - randSize / dungeonDaten.breite * 2);
        brett = new Dungeon(dungeonDaten, size.getWidth());

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

        //Mutebutton
        ImageView muteView = new ImageView(BilderGetter.muteBild);
        muteView.setPreserveRatio(true);
        muteView.setFitWidth(size.getWidth() / 40);
        Button btnMute = new Button("", muteView);
        btnMute.setLayoutX(size.getWidth() / 20 * 19);
        btnMute.setLayoutY((size.getHeight()) / 20 * 19);
        btnMute.setOnAction(event -> {
            if (!muted) {
                Musikspieler.musikStoppen();
                muted = true;
                muteView.setImage(BilderGetter.unmuteBild);
            } else {
                Musikspieler.playHintergrundMusik();
                muted = false;
                muteView.setImage(BilderGetter.muteBild);
            }
        });
        btnMute.setStyle("-fx-base: black");
        root.getChildren().add(btnMute);
        btnMute.setFocusTraversable(false);

        GraphicsContext g = canvas.getGraphicsContext2D();
        stage.show();

        brett.paint(g);

        stage.setMaximized(true);


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
            } else if (key.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }

            g.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            brett.paint(g);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
