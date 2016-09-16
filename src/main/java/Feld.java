import java.awt.*;
import java.io.FileNotFoundException;

public class Feld {
    private int typ;
    private int x, y;
    private Monster monster;

    // Aufgabe 2.3.7
    boolean aufgedeckt;
    DungeonDaten dungeonDaten;


    public Feld(int x, int y, char t) {
        this.x = 20 + x * 20;
        this.y = 20 + y * 20;
        if (t == 'W') typ = 1;      //Weg
        else if (t == 'V') typ = 2; //Versteckte Tür
        else if (t == 'S') typ = 3; //Start
        else if (t == 'M') typ = 4; //Monster
        else typ = 0;

        aufgedeckt = false;

        if (typ == 4)
            monster = new Monster(x, y);
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
    // liefert True, wenn das Feld ein Weg (1) ist oder wenn es sich 
    // um das Startfeld oder ein Monsterfeld handelt
    {
        return ((typ == 1) || (typ == 3) || (typ == 4));
    }

    public boolean hatMonster() {
        return (typ == 4);
    }

    public Monster gibMonster() {
        return monster;
    }

    public void aufdecken() {
        aufgedeckt = true;
    }

    public void paint(Graphics g) {
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4))
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
