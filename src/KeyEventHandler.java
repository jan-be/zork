import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class KeyEventHandler {
    KeyEventHandler(Scene scene, Dungeon brett, GraphicsContext g, Canvas canvas) {
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
}
