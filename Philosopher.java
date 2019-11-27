import java.awt.*;
import java.util.*;
import java.lang.Thread.*;

public class Philosopher extends Thread {

  private Random random;

  // states
  static final int THINK = 0, WAIT = 1, EAT = 2;
  // time options
  static final double time[] = {
    4.0, // think time
    2.0, // wait time
    3.0  // eat time
  };

  // relevant resources
  private ArrayList<Fork> adjList;

  // own state
  private int currentState;
  private int index;

  private Coordinator c;
  private Table graph;

  // graphics
  // radius for painting
  static int radius = 50;
  // center
  public int x, y;

  // Constructor.
  // cx and cy indicate coordinates of center
  public Philosopher(Table T, int x, int y, Coordinator C, int index) {

      this.random = new Random();
      this.currentState = THINK;

      this.index = index;

      this.graph = T;
      this.x = x;
      this.y = y;

      this.c = C;

      this.adjList = new ArrayList<Fork>();


  }

  public string toString() {

    String[] stringSet = {
      "thinking", // thinking state
      "waiting", // waiting state
      "eating" // eating state
    };

    return "Philosopher " + index + " is " + stringSet[currentState] + ".";

  }

  public void print() {
    System.out.println(toString());
  }

  public void addEdge(Fork f) {

    adjList.add(f);

  }

  private synchronized void take(Fork fork) throws ResetException {
    while(fork.isLocked()) {
      try {
        sleep(1000);
      } catch (InterruptedException e) {
        if(c.isReset()) {
          throw new ResetException();
        }
      }
    }
    fork.acquire(x, y);
  }

  // start method of Thread calls run;
  public void run() {
    for (;;) {
      try {

        if (c.gate()) delay(time[EAT]/2.0);
        think();
        print();

        if (c.gate()) delay(time[THINK]/2.0);
        waitFor();
        print();

        if (c.gate()) delay(time[WAIT]/2.0);
        eat();
        print();

      } catch(ResetException e) {
          currentState = THINK;
          resetGraph();
      }
    }
  }

  private void delay(double secs) throws ResetException {
      double FUDGE = 0.2;
      double ms = 1000 * secs;
      int window = (int) (2.0 * ms * FUDGE);
      int add_in = random.nextInt() % window;
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
      currentState = THINK;
      resetGraph();
  }

  private void waitFor() throws ResetException {
    currentState = WAIT;
    resetGraph();

    for (Fork f: this.adjList) {
      take(f)
      yield();
    }

  }

  private void eat() throws ResetException {
    currentState = EAT;
    resetGraph();

    for (Fork f: this.adjList) {
      f.release();
      yield();
    }

  }

  public void draw(Graphics g) {
    Color[] colorSet = {
      Color.blue, // thinking state
      Color.red, // waiting state
      Color.green // eating state
    };
    g.setColor(colorSet[currentState]);
    g.fillOval(x-radius/2, y-radius/2, radius, radius);
  }

  private void resetGraph() {
    graph.repaint();
    try {
      delay(time[currentState]);
    } catch (ResetException e) {

    }
  }

}
