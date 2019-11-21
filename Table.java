import java.awt.*;
import javax.swing.*;


// Graphics panel in which philosophers and forks appear.
//
public class Table extends JPanel {
    private static final int NUM_PHILS = 5;

    // following fields are set by construcctor:
    private final Coordinator c;
    private Fork[] forks;
    private Philosopher[] philosophers;

    // Constructor
    //
    // Note that angles are measured in radians, not degrees.
    // The origin is the upper left corner of the frame.
    //
    public Table(Coordinator C, int CANVAS_SIZE) {    // constructor
        c = C;
        forks = new Fork[NUM_PHILS];
        philosophers = new Philosopher[NUM_PHILS];
        setPreferredSize(new Dimension(CANVAS_SIZE, CANVAS_SIZE));

        for (int i = 0; i < NUM_PHILS; i++) {
            double angle = Math.PI/2 + 2*Math.PI/NUM_PHILS*(i-0.5);
            forks[i] = new Fork(this,
                (int) (CANVAS_SIZE/2.0 + CANVAS_SIZE/6.0 * Math.cos(angle)),
                (int) (CANVAS_SIZE/2.0 - CANVAS_SIZE/6.0 * Math.sin(angle)));
        }
        for (int i = 0; i < NUM_PHILS; i++) {
            double angle = Math.PI/2 + 2*Math.PI/NUM_PHILS*i;
            philosophers[i] = new Philosopher(this,
                (int) (CANVAS_SIZE/2.0 + CANVAS_SIZE/3.0 * Math.cos(angle)),
                (int) (CANVAS_SIZE/2.0 - CANVAS_SIZE/3.0 * Math.sin(angle)),
                forks[i],
                forks[(i+1) % NUM_PHILS],
                c);
            philosophers[i].start();
        }
    }

    public void pause() {
        c.pause();
        // force philosophers to notice change in coordinator state:
        for (int i = 0; i < NUM_PHILS; i++) {
            philosophers[i].interrupt();
        }
    }

    // Called by the UI when it wants to start over.
    //
    public void reset() {
        c.reset();
        // force philosophers to notice change in coordinator state:
        for (int i = 0; i < NUM_PHILS; i++) {
            philosophers[i].interrupt();
        }
        for (int i = 0; i < NUM_PHILS; i++) {
            forks[i].reset();
        }
    }

    // The following method is called automatically by the graphics
    // system when it thinks the Table canvas needs to be re-displayed.
    // This can happen because code elsewhere in this program called
    // repaint(), or because of hiding/revealing or open/close
    // operations in the surrounding window system.
    //
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < NUM_PHILS; i++) {
            forks[i].draw(g);
            philosophers[i].draw(g);
        }
        g.setColor(Color.black);
        g.drawRect(0, 0, getWidth()-1, getHeight()-1);
    }


}
