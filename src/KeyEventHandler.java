import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

class KeyEventHandler {
    KeyEventHandler(Scene scene, Dungeon dungeon) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.W) {
                dungeon.goNorth();
            } else if (key.getCode() == KeyCode.A) {
                dungeon.goWest();
            } else if (key.getCode() == KeyCode.S) {
                dungeon.goSouth();
            } else if (key.getCode() == KeyCode.D) {
                dungeon.goEast();
            } else if (key.getCode() == KeyCode.UP) {
                dungeon.goNorth();
            } else if (key.getCode() == KeyCode.LEFT) {
                dungeon.goWest();
            } else if (key.getCode() == KeyCode.DOWN) {
                dungeon.goSouth();
            } else if (key.getCode() == KeyCode.RIGHT) {
                dungeon.goEast();
            } else if (key.getCode() == KeyCode.SPACE) {
                dungeon.aktionAusfuehren();
            } else if (key.getCode() == KeyCode.ESCAPE) {
                System.exit(0);
            }

            dungeon.paint();
        });
    }
}
