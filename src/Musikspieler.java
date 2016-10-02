import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

class Musikspieler {
    private static MediaPlayer player;
    @SuppressWarnings("FieldCanBeLocal")
    private static MediaPlayer player2;

    static void playAktionsSound(String titel) {
        new JFXPanel();
        Media audioFile = new Media(Musikspieler.class.getResource("musik/" + titel + ".mp3").toExternalForm());
        player2 = new MediaPlayer(audioFile);
        player2.play();
    }

    static void playHintergrundMusik() {
        new JFXPanel();
        Media audioFile = new Media(Musikspieler.class.getResource("musik/hintergrund.mp3").toExternalForm());
        player = new MediaPlayer(audioFile);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        player.play();
    }
}
