import java.awt.*;
import java.util.*;
import java.lang.Thread.*;

public class Philosopher extends Thread {

    private static final Color THINK_COLOR = Color.blue;
    private static final Color WAIT_COLOR = Color.red;
    private static final Color EAT_COLOR = Color.green;

    private static final double THINK_TIME = 4.0;
    private static final double FUMBLE_TIME = 2.0;
        // time between becoming hungry and grabbing first fork
    private static final double EAT_TIME = 3.0;

    private Coordinator c;
    private Table t;
    private static final int XSIZE = 50;
    private static final int YSIZE = 50;
    private int x;
    private int y;
    private Fork left_fork;
    private Fork right_fork;
    private ArrayList<Fork> adjList;
    private Random prn;
    private Color color;

    // Constructor.
    // cx and cy indicate coordinates of center
    // Note that fillOval method expects coordinates of upper left corner
    // of bounding box instead.
    //
    public Philosopher(Table T, int cx, int cy,
                       Fork lf, Fork rf, Coordinator C) {
        t = T;
        x = cx;
        y = cy;
        left_fork = lf;
        right_fork = rf;
        c = C;
        prn = new Random();
        color = THINK_COLOR;

        // initialize the adjList with the incoming edges
        // adjList = new ArrayList<Fork>();
    }

    // start method of Thread calls run; you don't
    //
    public void run() {
        for (;;) {
            try {
                if (c.gate()) delay(EAT_TIME/2.0);
                think();
                if (c.gate()) delay(THINK_TIME/2.0);
                hunger();
                if (c.gate()) delay(FUMBLE_TIME/2.0);
                eat();
            } catch(ResetException e) {
                color = THINK_COLOR;
                t.repaint();
            }
        }
    }

    // render self
    //
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(x-XSIZE/2, y-YSIZE/2, XSIZE, YSIZE);
    }

    // sleep for secs +- FUDGE (%) seconds
    //
    private static final double FUDGE = 0.2;
    private void delay(double secs) throws ResetException {
        double ms = 1000 * secs;
        int window = (int) (2.0 * ms * FUDGE);
        int add_in = prn.nextInt() % window;
        int original_duration = (int) ((1.0-FUDGE) * ms + add_in);
        int duration = original_duration;
        for (;;) {
            try {
                Thread.sleep(duration);
                return;
            } catch(InterruptedException e) {
                if (c.isReset()) {
                    throw new ResetException();
                } else {        // suspended
                    c.gate();   // wait until resumed
                    duration = original_duration / 2;
                    // don't wake up instantly; sleep for about half
                    // as long as originally instructed
                }
            }
        }
    }

    private void think() throws ResetException {
        color = THINK_COLOR;
        t.repaint();
        delay(THINK_TIME);
    }

    private void hunger() throws ResetException {
        color = WAIT_COLOR;
        t.repaint();
        delay(FUMBLE_TIME);
        left_fork.acquire(x, y);
        yield();    // you aren't allowed to remove this
        right_fork.acquire(x, y);
    }

    private void eat() throws ResetException {
        color = EAT_COLOR;
        t.repaint();
        delay(EAT_TIME);
        left_fork.release();
        yield();    // you aren't allowed to remove this
        right_fork.release();
    }
}
