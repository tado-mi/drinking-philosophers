//
// Simple Java implementation of the classic Dining Philosophers problem.
//
// No synchronization (yet).
//
// Graphics are *very* naive.  Philosophers are big blobs.
// Forks are little blobs.
//
// Written by Michael Scott, 1997; updated 2013 to use Swing.
// Updated again in 2019 to drop support for applets.
//

import javax.swing.*;

public class Demo {

    private static final int CANVAS_SIZE = 360;

    public static void main(String[] args) {

      final Coordinator coordinator = new Coordinator();
      final Table graph = new Table(coordinator, CANVAS_SIZE);

      JFrame frame = new JFrame("Drinking Philosophers");

      // arrange to call graphical setup from GUI thread
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            new UI(frame, coordinator, graph);
          }
        });
      } catch (Exception e) {
          System.err.println("unable to create GUI");
      }

      frame.pack(); // calculate size of frame
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
    }
}
