import javafx.scene.image.Image;

import java.util.HashMap;

class Bilder {
    private static HashMap<String, Image> map = new HashMap<>();

    static void init() {
        String[] bilder = {
                "schwert", "monster", "held", "heiltrank", "rotesHerz", "grauesHerz", "coin", "mute", "unmute"
        };
        for (String s : bilder) {
            map.put(s, new Image("bilder/" + s + ".png"));
        }
    }

    static Image get(String bild) {
        return map.get(bild);
    }
}
