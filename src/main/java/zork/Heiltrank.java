package zork;

import java.awt.*;

public class Heiltrank {
    public int x, y;
    public int lebenswiedergabe;
    public final int anfangsleben;

    public Heiltrank(int x, int y)
    {
        this.x = x;
        this.y = y;

        anfangsleben = 123;
        lebenswiedergabe = anfangsleben;
    }
    public void paint(Graphics g) {
        if (lebenswiedergabe > 1) {
            int xPix = 20 + x * 20;
            int yPix = 20 + y * 20;

            g.setColor(Color.BLUE);
            g.fillOval(xPix + 5, yPix + 5, 10, 10);
        }
    }

}
