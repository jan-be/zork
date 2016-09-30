import javax.swing.*;
import java.awt.*;

public class Frame {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("ZORK");
        DungeonDaten dungeonDaten = new DungeonDaten();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Spiel spiel = new Spiel(dungeonDaten);
        Dimension dimension = new Dimension(40 + dungeonDaten.breite * 20, 140 + dungeonDaten.hoehe * 20);
        jFrame.getContentPane().add(spiel);
        spiel.setPreferredSize(dimension);
        jFrame.pack();

        jFrame.setVisible(true);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
