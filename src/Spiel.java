import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class Spiel extends JPanel implements ActionListener, KeyListener {
    private Dungeon brett;
    private JButton[] btn;

    Spiel(DungeonDaten dungeonDaten) {
        BilderGetter.bilderGetten();
        brett = new Dungeon(dungeonDaten);

        addKeyListener(this);

        setLayout(null);
        btn = new JButton[4];

        btn[0] = new JButton("west");
        btn[1] = new JButton("north");
        btn[2] = new JButton("east");
        btn[3] = new JButton("south");

        btn[0].setBounds(20, dungeonDaten.hoehe*Frame.FELD_SIZE +60, 40, 20);
        btn[1].setBounds(40, dungeonDaten.hoehe*Frame.FELD_SIZE +40, 40, 20);
        btn[2].setBounds(60, dungeonDaten.hoehe*Frame.FELD_SIZE +60, 40, 20);
        btn[3].setBounds(40, dungeonDaten.hoehe*Frame.FELD_SIZE +80, 40, 20);


        for (int b = 0; b < 4; b++) {
            add(btn[b]);
            btn[b].addActionListener(this);
            btn[b].setFocusable(false);
            btn[b].setMargin(new Insets(0,0,0,0));
        }
        setFocusable(true);

        Musikspieler.playHintergrundMusik();
    }

    public void keyTyped(KeyEvent k) {
    }

    public void keyPressed(KeyEvent k) {
        switch (k.getKeyCode()) {
            case 38:
                brett.goNorth();
                break;
            case 40:
                brett.goSouth();
                break;
            case 37:
                brett.goWest();
                break;
            case 39:
                brett.goEast();
                break;
            case 32:
                brett.aktionAusfuehren();
        }
        repaint();
    }

    public void keyReleased(KeyEvent k) {
    }


    public void actionPerformed(ActionEvent ereignis)
    // Aufgabe 2.3.2 
    {
        if (ereignis.getSource() == btn[0])
            brett.goWest();
        else if (ereignis.getSource() == btn[2])
            brett.goEast();
        if (ereignis.getSource() == btn[1])
            brett.goNorth();
        else if (ereignis.getSource() == btn[3])
            brett.goSouth();

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        brett.paint(g);
    }

}
