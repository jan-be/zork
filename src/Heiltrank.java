import javafx.scene.canvas.GraphicsContext;

class Heiltrank {
    private int x, y;
    int lebenswiedergabe, anfangsleben;

    Heiltrank(int x, int y)
    {
        this.x = x;
        this.y = y;

        anfangsleben = 123;
        lebenswiedergabe = anfangsleben;
    }
    void paint(GraphicsContext g) {
        if (lebenswiedergabe > 1) {
            int xPix = 20 + x * Main.FELD_SIZE;
            int yPix = 20 + y * Main.FELD_SIZE;

            g.drawImage(BilderGetter.heiltrankBild, xPix + Main.FELD_SIZE /10, yPix + Main.FELD_SIZE /10, Main.FELD_SIZE *4/5, Main.FELD_SIZE *4/5);
        }
    }

}
