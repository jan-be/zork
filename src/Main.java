import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;

import static java.awt.Toolkit.getDefaultToolkit;

public class Main extends Application {

    static double feldSize = 40;
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();

    private Dungeon brett;

    @Override
    public void start(Stage stage) throws Exception {
        DungeonDaten dungeonDaten = new DungeonDaten();
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        feldSize = size.getWidth()/dungeonDaten.breite - size.getWidth()/dungeonDaten.breite/dungeonDaten.breite;
        brett = new Dungeon(dungeonDaten);

        stage.setTitle("ZORK");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(size.getWidth(), size.getHeight());
        root.getChildren().add(pane);
        pane.getChildren().add(canvas);
        stage.initStyle(StageStyle.UNDECORATED);
        pane.setStyle("-fx-background-color: black");

        /*ImageView imageView = new ImageView(BilderGetter.dancerAnimation);
        imageView.setX(50);
        imageView.setY(50);
        root.getChildren().add(imageView);*/

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

            g.clearRect(0,0,canvas.getWidth(), canvas.getHeight());
            brett.paint(g);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
