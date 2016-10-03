import java.awt.*;

class Feld {
    private int typ;
    private int x, y;
    private Monster monster;
    private Heiltrank heiltrank;
    private Knife knife;
    private DungeonDaten dungeonDaten;

    private boolean aufgedeckt;


    Feld(int x, int y, char t, DungeonDaten dungeonDaten) {
        this.dungeonDaten = dungeonDaten;
        this.x = 20 + x * Frame.FELD_SIZE;
        this.y = 20 + y * Frame.FELD_SIZE;
        if (t == 'W') typ = 1;      //Weg
        else if (t == 'V') typ = 2; //Versteckte Tür
        else if (t == 'S') typ = 3; //Beginn
        else if (t == 'M') typ = 4; //Monster
        else if (t == 'H') typ = 5; //heiltrank
        else if (t == 'K') typ = 6; //Schwert
        else typ = 0;

        aufgedeckt = false;

        if (typ == 4) {
            monster = new Monster(x, y);
        } else if (typ == 5) {
            heiltrank = new Heiltrank(x, y);
        } else if (typ == 6) {
            knife = new Knife(x, y);
        }
    }

    boolean kannBetretenWerden() {
        return (typ != 0);
    }

    boolean hatMonster() {
        return (typ == 4);
    }

    boolean hatHeiltrank() {
        return (typ == 5);
    }

    boolean hatKnife() {
        return typ == 6 && (!knife.aufgesammelt);
    }

    boolean hatVersteckteTuer() {
        return typ == 2;
    }


    Monster gibMonster() {
        return monster;
    }

    Heiltrank gibHeiltrank() {
        return heiltrank;
    }

    Knife gibKnife() {
        return knife;
    }

    void aufdecken() {
        aufgedeckt = true;
    }

    void paint(Graphics g) {
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4) || typ == 5 || typ == 6)
                g.setColor(new Color(91, 91, 0));
            else if (typ == 2)
                g.setColor(new Color(91, 0, 91));
            else if (typ == 3)
                g.setColor(new Color(0, 91, 91));
            else
                g.setColor(Color.BLACK);

            g.fillRect(x, y, Frame.FELD_SIZE, Frame.FELD_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(x, y, Frame.FELD_SIZE, Frame.FELD_SIZE);

            if (monster != null)
                monster.paint(g);
            if (heiltrank != null)
                heiltrank.paint(g);
            if (knife != null)
                knife.paint(g);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, Frame.FELD_SIZE, Frame.FELD_SIZE);
        }
    }

    void werteVomGegenstandZeigen(Graphics g) {
        if (hatMonster()) {
            if (monster.leben > 0) {
                g.drawString("Monster: Leben: " + monster.leben + ", Angriff: " + monster.angriff + ", Rüstung: " + monster.ruestung + ", Gold: " + monster.gold,
                        124, 80 + dungeonDaten.hoehe * Frame.FELD_SIZE);
            } else {
                g.drawString("Monster tot", 124, 80 + dungeonDaten.hoehe * Frame.FELD_SIZE);
            }
        } else if (hatHeiltrank()) {
            if (heiltrank.lebenswiedergabe > 1) {
                g.drawString("Der Heiltrank kann noch " + heiltrank.lebenswiedergabe + " wiedergeben", 124, 80 + dungeonDaten.hoehe * Frame.FELD_SIZE);
            } else {
                g.drawString("Heiltrank alle", 124, 80 + dungeonDaten.hoehe * Frame.FELD_SIZE);
            }
        }
    }

}
