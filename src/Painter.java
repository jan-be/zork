import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Collection;
import java.util.HashMap;

class Painter {
    private static HashMap<String, Held> helden;
    private static DungeonDaten dungeonDaten;
    private static GraphicsContext g;
    private static String name;

    static void init(DungeonDaten dungeonDaten, String name, GraphicsContext g) {
        Painter.helden = Assets.helden;
        Painter.dungeonDaten = dungeonDaten;
        Painter.name = name;
        Painter.g = g;
    }

    static void paint() {
        for (Ding d : Assets.dinge) {
            dingPaint(g, d);
        }
        eigenenHeldenPaint(dungeonDaten, g, helden.get(name));
        Collection<Held> heldenCollection = helden.values();
        for (Held h : heldenCollection) {
            alleHeldenPaint(g, h);
        }
    }

    private static void eigenenHeldenPaint(DungeonDaten dungeonDaten, GraphicsContext g, Held held) {
        double echteBreite = dungeonDaten.breite * Main.feldSize;

        //das Herzzeugs
        for (int i = 0; i < Held.maxLeben / 30; i++) {
            g.drawImage(Bilder.get("grauesHerz"), echteBreite / 2 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }
        for (int i = 0; i < held.leben / 30; i++) {
            g.drawImage(Bilder.get("rotesHerz"), echteBreite / 2 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }

        //schwert, wenn aufgesammelt
        for (int i = 0; i < held.schwerterAufgesammelt; i++) {
            g.drawImage(Bilder.get("schwert"), echteBreite / 4 * 3 + i * echteBreite / 60, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        }

        //coin
        g.drawImage(Bilder.get("coin"), echteBreite / 40 * 36, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize, Main.feldSize / 3 * 2, Main.feldSize / 3 * 2);
        g.setFill(Color.WHITE);
        g.setFont(new Font(20));
        g.fillText(Integer.toString(held.gold), echteBreite / 40 * 37, echteBreite / 30 + dungeonDaten.hoehe * Main.feldSize + Main.randSize + Main.feldSize / 2 + 2);
    }

    private static void alleHeldenPaint(GraphicsContext g, Held held) {
        double xPix = Main.randSize + held.x * Main.feldSize;
        double yPix = Main.randSize + held.y * Main.feldSize;

        //das Bild vom Helden
        g.drawImage(Bilder.get("held"), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);

        g.setTextAlign(TextAlignment.CENTER);
        g.fillText(held.name, xPix + Main.feldSize / 2, yPix-2);
    }

    private static void dingPaint(GraphicsContext g, Ding ding) {
        if (ding.nochSichtbar && ding.aufgedeckt) {
            double xPix = Main.randSize + ding.x * Main.feldSize;
            double yPix = Main.randSize + ding.y * Main.feldSize;

            switch (ding.typ) {
                case "monster":
                    g.setFill(Color.RED);
                    g.fillRect(xPix, yPix, ding.leben / ding.anfangsLeben * Main.feldSize, 3);
                    break;
                case "heiltrank":
                    g.setFill(Color.BLUE);
                    g.fillRect(xPix, yPix, ding.maleAnklickbar / 3 * Main.feldSize, 3);
                    break;
                case "bossmonster":
                    g.setFill(Color.RED);
                    g.fillRect(xPix, yPix, ding.leben / ding.anfangsLeben * Main.feldSize * 2, 6);
                    break;
            }
            if (!ding.typ.equals("bossmonster")) {
                g.drawImage(Bilder.get(ding.typ), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5, Main.feldSize * 4 / 5);
            } else {
                g.drawImage(Bilder.get("monster"), xPix + Main.feldSize / 10, yPix + Main.feldSize / 10, Main.feldSize * 4 / 5 * 2, Main.feldSize * 4 / 5 * 2);

            }
        }
    }
}
