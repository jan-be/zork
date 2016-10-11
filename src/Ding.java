class Ding {
    final String typ;
    final int x;
    final int y;
    final double angriff;
    final double ruestung;
    final double anfangsLeben;
    double leben;
    double maleAnklickbar;
    boolean nochSichtbar = true;
    boolean aufgedeckt = false;

    Ding(String typ, int x, int y, double angriff, double ruestung, double leben, double maleAnklickbar) {
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
