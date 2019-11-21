import java.awt.*;

public class Fork {
    private Table t;
    private static final int XSIZE = 10;
    private static final int YSIZE = 10;
    private int orig_x;
    private int orig_y;
    private int x;
    private int y;

    // Constructor.
    // cx and cy indicate coordinates of center.
    // Note that fillOval method expects coordinates of upper left corner
    // of bounding box instead.
    //
    public Fork(Table T, int cx, int cy) {
        t = T;
        orig_x = cx;
        orig_y = cy;
        x = cx;
        y = cy;
    }

    public void reset() {
        clear();
        x = orig_x;
        y = orig_y;
        t.repaint();
    }

    // arguments are coordinates of acquiring philosopher's center
    //
    public void acquire(int px, int py) {
        clear();
        x = (orig_x + px)/2;
        y = (orig_y + py)/2;
        t.repaint();
    }

    public void release() {
        reset();
    }

    // render self
    //
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x-XSIZE/2, y-YSIZE/2, XSIZE, YSIZE);
    }

    // erase self
    //
    private void clear() {
        Graphics g = t.getGraphics();
        g.setColor(t.getBackground());
        g.fillOval(x-XSIZE/2, y-YSIZE/2, XSIZE, YSIZE);
    }
}
