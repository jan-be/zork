import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

class MuteButton {
    private boolean muted = false;
    MuteButton(Rectangle2D size, Pane root) {
        ImageView muteView = new ImageView(Bilder.get("mute"));
        muteView.setPreserveRatio(true);
        muteView.setFitWidth(size.getWidth() / 40);
        Button btnMute = new Button("", muteView);
        btnMute.setLayoutX(size.getWidth() / 20 * 19);
        btnMute.setLayoutY((size.getHeight()) / 20 * 19);
        btnMute.setOnAction(event -> {
            if (!muted) {
                Musikspieler.musikStoppen();
                muted = true;
                muteView.setImage(Bilder.get("unmute"));
            } else {
                Musikspieler.playHintergrundMusik();
                muted = false;
                muteView.setImage(Bilder.get("mute"));
            }
        });
        btnMute.setStyle("-fx-base: black");
        root.getChildren().add(btnMute);
        btnMute.setFocusTraversable(false);
    }
}
