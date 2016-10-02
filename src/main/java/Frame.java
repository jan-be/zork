import javax.swing.*;
import java.awt.*;

public class Frame {

    static final int FELD_SIZE = 45;
    static final int ANZAHL_LEVEL = DungeonDaten.getAnzahlLevel();

    public static void main(String[] args) {
        DungeonDaten dungeonDaten = new DungeonDaten();
        JFrame jFrame = new JFrame("ZORK");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Spiel spiel = new Spiel(dungeonDaten);
        Dimension dimension = new Dimension(40 + dungeonDaten.breite * FELD_SIZE, 140 + dungeonDaten.hoehe * FELD_SIZE);
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
