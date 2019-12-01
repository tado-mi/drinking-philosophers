import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

// Graphics panel in which V and E appear.
//
public class Table extends JPanel {

  private int n;
  private int m;

  // following fields are set by constructor:
  private final Coordinator c;
  private Fork[] E;
  private Philosopher[] V;

  public Color[] colorSet;

  // General case, with filename containing the graph
  public Table(Coordinator C, String filename) {

    c = C;
    // initial size
    int s = 400;

    setPreferredSize(new Dimension(s, s));

    try {

      Scanner scanner = new Scanner(new File(filename));
      // first line = `n m`
      String str = scanner.nextLine();
      String[] arr = str.split(" ");

      n = Integer.parseInt(arr[0]);
      setColors();

      V = new Philosopher[n];
      for (int i = 0; i < n; i = i + 1) {
        V[i] = new Philosopher(this, c, i);
      }
      calculateCoordinates(s);

      m = Integer.parseInt(arr[1]);
      E = new Fork[m];
      for (int i = 0; i < m; i = i + 1) {
        E[i] = new Fork(this);
      }

      while (scanner.hasNext()) {

        str = scanner.nextLine();
        arr = str.split(" ");
        if (arr[0].equals("#")) // comment
          continue;

        Fork e = E[Integer.parseInt(arr[2])];

        Philosopher u = V[Integer.parseInt(arr[0])];
        u.addEdge(e);
        e.addVertex(u);

        Philosopher v = V[Integer.parseInt(arr[1])];
        v.addEdge(e);
        e.addVertex(v);

      }

      for (Philosopher p: V) {
        p.start();
      }

    } catch (IOException e) {

    }

  }

  //
  // Note that angles are measured in radians, not degrees.
  // The origin is the upper left corner of the frame.
  //
  private void calculateCoordinates(int s) {

    for (int i = 0; i < n; i++) {

      double angle = Math.PI/2 + 2*Math.PI/n*i;
      double x = s/2.0 + s/3.0 * Math.cos(angle);
      double y = s/2.0 - s/3.0 * Math.sin(angle);
      V[i].set((int) x, (int) y);

    }

  }

  private void setColors() {

    Random random = new Random();
    int lm = 256;

    if (n > 10) { // populate with random colors
      colorSet = new Color[n];
      for (int i = 0; i < n; i = i + 1) {
        int r = random.nextInt() % lm;
        while (r < 0) r = r + lm;
        int g = random.nextInt() % lm;
        while (g < 0) g = g + lm;
        int b = random.nextInt() % lm;
        while (b < 0) b = b + lm;
        colorSet[i] = new Color(r, g, b);
      }
    } else { // populate with pretty colors
      colorSet = new Color[10];
      colorSet[0] = new Color(255, 155, 155);
      colorSet[1] = new Color(255, 200, 155);
      colorSet[2] = new Color(205, 255, 155);
      colorSet[3] = new Color(150, 205, 255);
      colorSet[4] = new Color(155, 255, 255);
      colorSet[5] = new Color(255, 150, 200);
      colorSet[6] = new Color(200, 150, 255);
      colorSet[7] = new Color(150, 205, 255);
      colorSet[8] = new Color(155, 255, 205);
      colorSet[9] = new Color(255, 255, 150);
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
  public void reset() {
    c.reset();
    // force V to notice change in coordinator state:
    for (int i = 0; i < n; i++) {
        V[i].interrupt();
    }
    for (int i = 0; i < n; i++) {
        E[i].reset();
    }
  }

  public void paintComponent(Graphics g) {

    super.paintComponent(g);
    int s = Math.min(getWidth() - 1, getHeight() - 1);
    calculateCoordinates(s);

    for (Fork f: E) {
      f.draw(g);
    }

    for (Philosopher p: V) {
      p.draw(g);
    }

    g.setColor(Color.black);
    g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

  }

}
