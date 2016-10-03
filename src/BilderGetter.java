import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

class BilderGetter {
    static Image schwertBild,
            monsterBild,
            heldBild,
            heiltrankBild;

    static void bilderGetten() {
        try {
            schwertBild = getBild("schwert");
            monsterBild = getBild("monster");
            heldBild = getBild("held");
            heiltrankBild = getBild("heiltrank");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Image getBild(String typ) throws IOException {
        Image bild;
        bild = ImageIO.read(MethodHandles.lookup().lookupClass().getResource(
                "bilder/" + typ + ".png"
        ));
        return bild;
    }
}
