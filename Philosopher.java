import java.awt.*;
import java.util.*;
import java.lang.Thread.*;

public class Philosopher extends Thread {

  private Random random;

  // states
  static final int THINK = 0, WAIT = 1, EAT = 2;
  static final String[] stringSet = {
    "thinking", // thinking state
    "waiting", // waiting state
    "eating" // eating state
  };
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
  // center
  public int x, y;
  public Color color;

  // Constructor.
  // cx and cy indicate coordinates of center
  public Philosopher(Table table, Coordinator C, int index) {

    this.random = new Random();
    this.currentState = THINK;

    // for printing
    this.index = index;

    this.graph = table;

    this.c = C;

    this.adjList = new ArrayList<Fork>();

    int lm = 256;
    int r = random.nextInt() % lm;
    while (r < 0) r = r + lm;
    int g = random.nextInt() % lm;
    while (g < 0) g = g + lm;
    int b = random.nextInt() % lm;
    while (b < 0) b = b + lm;
    this.color = new Color(r, g, b);

  }

  public String toString() {
    return "Philosopher " + index + " is " + stringSet[currentState] + ".";
  }

  public Color getColor() {
    return this.color;
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

    fork.acquire(this);

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

    Random random = new Random();

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
      take(f);
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

  public void set(int x, int y) {

    this.x = x;
    this.y = y;

  }

  public void draw(Graphics g) {

    Image img; String imgFilename;
    int x = this.x, y = this.y;

    // draw photo
    String ext = index == 4? ".png" : ".jpg";
    imgFilename = "../img/" + index + ext;

    // width of photo
    int width = Math.min(graph.getWidth() / 4, 200);

    x = this.x - width/2;
    y = this.y - width/2;

    img = Toolkit.getDefaultToolkit().getImage(imgFilename);
    int w = img.getWidth(null);
    int h = img.getHeight(null);

    double scale = (1.0 * width) / w;
    w = (int) (scale * w);
    h = (int) (scale * h);
    g.drawImage(img, x, y, w, h, graph);
    y = y + h + 3;

    // draw state
    imgFilename = "../img/" + stringSet[currentState] + ".png";
    img = Toolkit.getDefaultToolkit().getImage(imgFilename);

    g.drawImage(img, x, y, 50, 50, graph);
    x = x + 60;
    y = y + 20;
    g.setColor(new Color(0, 0, 0));
    g.drawString(stringSet[currentState], x, y);

  }

  private void resetGraph() {
    graph.repaint();
    try {
      delay(time[currentState]);
    } catch (ResetException e) {
      System.err.println("Reset exception.");
    }
  }

}
