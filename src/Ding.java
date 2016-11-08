class Ding {
    String typ;
    int x;
    int y;
    double angriff;
    double ruestung;
    double anfangsLeben;
    double leben;
    double maleAnklickbar;
    boolean nochSichtbar = true;
    boolean aufgedeckt = false;

    void init(String typ, int x, int y, double angriff, double ruestung, double leben, double maleAnklickbar) {
        this.typ = typ;
        this.x = x;
        this.y = y;
        this.angriff = angriff;
        this.ruestung = ruestung;
        this.leben = leben;
        this.anfangsLeben = leben;
        this.maleAnklickbar = maleAnklickbar;
    }
}
