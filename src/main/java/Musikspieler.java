import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

class Musikspieler {
    private static MediaPlayer player;
    static void play(String titel) {
        new JFXPanel();
        Media audioFile = new Media(Musikspieler.class.getResource("musik/"+titel+".mp3").toExternalForm());
        player = new MediaPlayer(audioFile);
        player.setOnEndOfMedia(() -> player.seek(Duration.ZERO));
        player.play();
        //test
    }
}
