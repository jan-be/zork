package zork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;

public class Spiel extends JApplet implements ActionListener, KeyListener {
    Dungeon brett;
    Button[] btn;
    DungeonDaten daten;

    public void init() {
        try {
            brett = new Dungeon();
            daten = new DungeonDaten();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(JOptionPane.getRootFrame(), "karte.txt nicht gefunden", "ALARM", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        addKeyListener(this);

        this.setSize(40 + daten.breite * 20, 140 + daten.hoehe * 20);

        this.setVisible(true);
        requestFocus();

        btn = new Button[4];

        btn[0] = new Button("west");
        btn[1] = new Button("north");
        btn[2] = new Button("east");
        btn[3] = new Button("south");

        btn[0].setBounds(20, 420, 40, 20);
        btn[1].setBounds(40, 400, 40, 20);
        btn[2].setBounds(60, 420, 40, 20);
        btn[3].setBounds(40, 440, 40, 20);

        getContentPane().setLayout(null);

        for (int b = 0; b < 4; b++) {
            getContentPane().add(btn[b]);
            btn[b].addActionListener(this);
        }
        repaint();
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
                brett.kaempfen();
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

        requestFocus();
        repaint();
    }

    public void paint(Graphics g) {
        brett.paint(g);
    }

}
