//
// Simple Java implementation of the classic Drinking Philosophers problem.
//
//
// Written by Michael Scott, 1997; updated 2013 to use Swing.
// Updated again in 2019 to drop support for applets.
//

import javax.swing.*;

public class Demo {

    public static void main(String[] args) {

      final Coordinator coordinator = new Coordinator();

      String filename = args[0];
      Table table;

      if (args.length > 1) {

        table = new Table(coordinator, filename, args[1].equals("-photo"));

      } else {

        table = new Table(coordinator, filename, false);

      }
      JFrame frame = new JFrame(filename);

      // arrange to call graphical setup from GUI thread
      try {
        SwingUtilities.invokeAndWait(new Runnable() {
          public void run() {
            new UI(frame, coordinator, table);
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
