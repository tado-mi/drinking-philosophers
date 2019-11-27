import java.awt.*;

public class Fork {

    private Table graph;

    // graphics
    // radius for painting
    static int radius = 10;
    // center
    private int x, y;
    // original x, y
    private int orig_x, orig_y;

    private boolean locked;

    // Constructor.
    public Fork(Table T, int x, int y) {
        graph = T;
        orig_x = x;
        orig_y = y;
        this.x = x;
        this.y = y;
        this.locked = false;
    }

    public void lock() {
      locked = true;
    }

    public void unlock() {
      locked = false;
    }

    public boolean isLocked() {
      return locked;
    }

    public boolean isFree() {
      return (x == orig_x && y == orig_y);
    }

    public void reset() {
        this.unlock();
        clear();
        x = orig_x;
        y = orig_y;
        resetGraph();
    }

    // arguments are coordinates of acquiring philosopher's center
    //
    public void acquire(Philosopher p) {
        lock();
        clear();
        x = (orig_x + p.x)/2;
        y = (orig_y + p.y)/2;
        resetGraph();
    }

    public void release() {
        reset();
    }

    // render self
    //
    public void draw(Graphics g) {
        g.setColor(Color.black);
        g.fillOval(x-radius/2, y-radius/2, radius, radius);
    }

    // erase self
    //
    private void clear() {
        Graphics g = graph.getGraphics();
        g.setColor(graph.getBackground());
        g.fillOval(x-radius/2, y-radius/2, radius, radius);
    }

    public void resetGraph() {
      graph.repaint();
    }
}
