import java.awt.*;
import javax.swing.*;

// Graphics panel in which V and forks appear.
//
public class Table extends JPanel {
    private static final int n = 5;

    // following fields are set by constructor:
    private final Coordinator c;
    private Fork[] forks;
    private Philosopher[] V;

    // Constructor
    //
    // Note that angles are measured in radians, not degrees.
    // The origin is the upper left corner of the frame.
    //
    public Table(Coordinator C, int s) {
      c = C;

      setPreferredSize(new Dimension(s, s));

      forks = new Fork[n];
      for (int i = 0; i < n; i++) {
        double angle = Math.PI/2 + 2*Math.PI/n*(i-0.5);
        double x = s/2.0 + s/6.0 * Math.cos(angle);
        double y = s/2.0 - s/6.0 * Math.sin(angle);
        forks[i] = new Fork(this, (int) x, (int) y);
      }

      V = new Philosopher[n];
      for (int i = 0; i < n; i++) {
        double angle = Math.PI/2 + 2*Math.PI/n*i;
        double x = s/2.0 + s/3.0 * Math.cos(angle);
        double y = s/2.0 - s/3.0 * Math.sin(angle);
        V[i] = new Philosopher(this, (int) x, (int) y, c);
        V[i].addEdge(forks[i]);
        V[i].addEdge(forks[(i+1) % n]);
        V[i].start();
      }
    }

    public void pause() {
        c.pause();
        // force V to notice change in coordinator state:
        for (int i = 0; i < n; i++) {
            V[i].interrupt();
        }
    }

    // Called by the UI when it wants to start over.
    //
    public void reset() {
      c.reset();
      // force V to notice change in coordinator state:
      for (int i = 0; i < n; i++) {
          V[i].interrupt();
      }
      for (int i = 0; i < n; i++) {
          forks[i].reset();
      }
    }

    public void paintComponent(Graphics g) {

      super.paintComponent(g);

      for (int i = 0; i < n; i++) {
        forks[i].draw(g);
        V[i].draw(g);
      }

      g.setColor(Color.black);
      g.drawRect(0, 0, getWidth()-1, getHeight()-1);

    }


}
