import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

class Musikspieler {
    @SuppressWarnings("FieldCanBeLocal")
    private static MediaPlayer aktionsPlayer;
    private static MediaPlayer hintergrundPlayer;
    private static boolean muted = false;

    static void playSound(String titel) {
        if (!muted) {
            Media audioFile = new Media(Musikspieler.class.getResource("musik/" + titel + ".mp3").toExternalForm());
            aktionsPlayer = new MediaPlayer(audioFile);
            aktionsPlayer.play();
        }
    }

    static void playHintergrundMusik() {
        muted = false;
        Media audioFile = new Media(Musikspieler.class.getResource("musik/hintergrund.mp3").toExternalForm());
        hintergrundPlayer = new MediaPlayer(audioFile);
        hintergrundPlayer.setOnEndOfMedia(() -> hintergrundPlayer.seek(Duration.ZERO));
        hintergrundPlayer.play();
    }

    static void musikStoppen() {
        muted = true;
        hintergrundPlayer.stop();
    }
}
