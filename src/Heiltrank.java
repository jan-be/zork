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
            double xPix = 20 + x * Main.feldSize;
            double yPix = 20 + y * Main.feldSize;

            g.drawImage(BilderGetter.heiltrankBild, xPix + Main.feldSize /10, yPix + Main.feldSize /10, Main.feldSize *4/5, Main.feldSize *4/5);
        }
    }

}
