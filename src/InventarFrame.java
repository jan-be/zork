import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

class InventarFrame {
    static void show() {
        Stage stage = new Stage();
        stage.setTitle("Inventar");
        Pane root = new Pane();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setX(500);
        stage.setY(500);
        StackPane pane = new StackPane();
        Canvas canvas = new Canvas(500, 500);
        root.getChildren().add(pane);
        pane.getChildren().add(canvas);
        pane.setStyle("-fx-background-color: black");
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.drawImage(Bilder.get("schwert"),50,50);

        stage.show();
    }
}
