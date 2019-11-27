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
    private Philosopher owner;

    // Constructor.
    public Fork(Table T, int x, int y) {
        graph = T;
        orig_x = x;
        orig_y = y;
        this.x = x;
        this.y = y;
        this.locked = false;
        this.owner = null;
    }

    public void lock() {
      locked = true;
    }

    public void unlock() {
      locked = false;
      owner = null;
    }

    public boolean isLocked() {
      return locked;
    }

    public boolean isFree() {
      return owner == null;
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
        owner = p;
        x = (orig_x + owner.x)/2;
        y = (orig_y + owner.y)/2;
        resetGraph();
    }

    public void release() {
        reset();
    }

    public void set(int x, int y) {
      orig_x = x;
      orig_y = y;
      if (! isLocked()) {
        this.x = orig_x;
        this.y = orig_y;
      } else {
        x = (orig_x + owner.x)/2;
        y = (orig_y + owner.y)/2;
      }
    }

    // render self
    //
    public void draw(Graphics g) {

      g.setColor(new Color(255, 100, 0));
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
