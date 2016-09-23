package zork;

import java.awt.*;
import java.io.FileNotFoundException;

public class Feld {
    private int typ;
    private int x, y;
    private Monster monster;
    private Heiltrank heiltrank;
    private Knife knife;

    // Aufgabe 2.3.7
    boolean aufgedeckt;
    DungeonDaten dungeonDaten;


    public Feld(int x, int y, char t) {
        this.x = 20 + x * 20;
        this.y = 20 + y * 20;
        if (t == 'W') typ = 1;      //Weg
        else if (t == 'V') typ = 2; //Versteckte Tür
        else if (t == 'S') typ = 3; //Beginn
        else if (t == 'M') typ = 4; //Monster
        else if (t == 'H') typ = 5; //heiltrank
        else if (t == 'K') typ = 6; //Schwert
        else typ = 0;

        aufgedeckt = false;

        if (typ == 4)
            monster = new Monster(x, y);
        if (typ == 5)
            heiltrank = new Heiltrank(x, y);
        if (typ == 6)
            knife = new Knife(x, y);

        try {
            dungeonDaten = new DungeonDaten();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getTyp() {
        return typ;
    }

    public boolean kannBetretenWerden()
    // liefert True, wenn das zork.Feld ein Weg (1) ist oder wenn es sich
    // um das Startfeld oder ein Monsterfeld handelt
    {
        return (typ != 0);
    }

    public boolean hatMonster() {
        return (typ == 4);
    }

    public boolean hatHeiltrank() {
        return (typ == 5);
    }
    public boolean hatKnife() {
        return (typ == 6);
    }


    public Monster gibMonster() {
        return monster;
    }

    public Heiltrank gibHeiltrank() {
        return heiltrank;
    }

    public void aufdecken() {
        aufgedeckt = true;
    }

    public void paint(Graphics g) {
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4) || typ == 5 || typ == 6)
                g.setColor(new Color(91, 91, 0));
            else if (typ == 2)
                g.setColor(new Color(91, 0, 91));
            else if (typ == 3)
                g.setColor(new Color(0, 91, 91));
            else
                g.setColor(Color.BLACK);

            g.fillRect(x, y, 20, 20);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, 20, 20);

            if (monster != null)
                monster.paint(g);
            if (heiltrank != null)
                heiltrank.paint(g);
            if (knife != null)
                knife.paint(g);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, 20, 20);
        }
    }

    public void werteVomMonsterZeigen(Graphics g) {
        if (hatMonster()) {
            if (monster.leben > 0) {
                g.drawString("Monster: Leben: " + monster.leben + ", Angriff: " + monster.angriff + ", Rüstung: " + monster.ruestung + ", Gold: " + monster.gold,
                        124, 80 + dungeonDaten.hoehe * 20);
            } else {
                g.drawString("Monster tot", 124, 80 + dungeonDaten.hoehe * 20);
            }
        }
    }

}
