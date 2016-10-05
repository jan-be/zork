import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

class Musikspieler {
    private static MediaPlayer hintergrundPlayer;
    @SuppressWarnings("FieldCanBeLocal")
    private static MediaPlayer aktionsPlayer;

    static void playAktionsSound(String titel) {
        Media audioFile = new Media(Musikspieler.class.getResource("musik/" + titel + ".mp3").toExternalForm());
        aktionsPlayer = new MediaPlayer(audioFile);
        aktionsPlayer.play();
    }

    static void playHintergrundMusik() {
        Media audioFile = new Media(Musikspieler.class.getResource("musik/hintergrund.mp3").toExternalForm());
        hintergrundPlayer = new MediaPlayer(audioFile);
        hintergrundPlayer.setOnEndOfMedia(() -> hintergrundPlayer.seek(Duration.ZERO));
        hintergrundPlayer.play();
    }
}
