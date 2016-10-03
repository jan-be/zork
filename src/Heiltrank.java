import java.awt.*;

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
    void paint(Graphics g) {
        if (lebenswiedergabe > 1) {
            int xPix = 20 + x * Frame.FELD_SIZE;
            int yPix = 20 + y * Frame.FELD_SIZE;

            g.setColor(Color.BLUE);
            //g.fillOval(xPix + 5, yPix + 5, 10, 10);
            g.drawImage(BilderGetter.heiltrankBild, xPix + Frame.FELD_SIZE /10, yPix + Frame.FELD_SIZE /10, Frame.FELD_SIZE *4/5, Frame.FELD_SIZE *4/5, null);
        }
    }

}
