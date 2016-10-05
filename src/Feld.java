import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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
        this.x = 20 + x * Main.FELD_SIZE;
        this.y = 20 + y * Main.FELD_SIZE;
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

    void paint(GraphicsContext g) {
        if (aufgedeckt) {
            if ((typ == 1) || (typ == 4) || typ == 5 || typ == 6)
                g.setFill(Color.color(0.33, 0.33, 0));
            else if (typ == 2)
                g.setFill(Color.color(0.33, 0, 0.33));
            else if (typ == 3)
                g.setFill(Color.color(0, 0.33, 0.33));
            else
                g.setFill(Color.BLACK);

            g.fillRect(x, y, Main.FELD_SIZE, Main.FELD_SIZE);
            g.setFill(Color.BLACK);
            g.strokeRect(x+1, y+1, Main.FELD_SIZE-2, Main.FELD_SIZE-2);

            if (monster != null)
                monster.paint(g);
            if (heiltrank != null)
                heiltrank.paint(g);
            if (knife != null)
                knife.paint(g);
        } else {
            g.setFill(Color.BLACK);
            g.fillRect(x, y, Main.FELD_SIZE, Main.FELD_SIZE);
        }
    }

    void werteVomGegenstandZeigen(GraphicsContext g) {
        if (hatMonster()) {
            if (monster.leben > 0) {
                g.fillText("Monster: Leben: " + monster.leben + ", Angriff: " + monster.angriff + ", Rüstung: " + monster.ruestung + ", Gold: " + monster.gold,
                        124, 80 + dungeonDaten.hoehe * Main.FELD_SIZE);
            } else {
                g.fillText("Monster tot", 124, 80 + dungeonDaten.hoehe * Main.FELD_SIZE);
            }
        } else if (hatHeiltrank()) {
            if (heiltrank.lebenswiedergabe > 1) {
                g.fillText("Der Heiltrank kann noch " + heiltrank.lebenswiedergabe + " wiedergeben", 124, 80 + dungeonDaten.hoehe * Main.FELD_SIZE);
            } else {
                g.fillText("Heiltrank alle", 124, 80 + dungeonDaten.hoehe * Main.FELD_SIZE);
            }
        }
    }

}
