import java.awt.*;
import java.awt.geom.Line2D;


public class Fork {

    private Table graph;

    private boolean locked;
    private Philosopher owner;

    // two philosophers that could be the owner of the "fork"
    private Philosopher u, v;

    // Constructor.
    public Fork(Table table) {

      graph = table;
      this.locked = false;

      this.owner = null;
      this.u = null;
      this.v = null;

    }

    public void addVertex(Philosopher p) {
      if (u == null) {
        u = p;
      } else if (v == null) {
        v = p;
        // e is an edge from u to v
        // => 'owner' of e is philosopher named v
        owner = v;
      } else {
        System.out.println("Error at adding vertex");
      }
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

    public void reset() {
        this.unlock();
        resetGraph();
    }

    // arguments are coordinates of acquiring philosopher's center
    public void acquire(Philosopher p) {
      lock();
      owner = p;
      resetGraph();
    }

    public void release() {
      reset();
    }

    // render self
    public void draw(Graphics g) {

      g.setColor(new Color(0, 0, 0));
      if (isLocked()) {
        g.setColor(owner.getColor());
      }

      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(5));
      g2.draw(new Line2D.Float(u.x, u.y, v.x, v.y));

    }

    public void resetGraph() {
      graph.repaint();
    }
}
