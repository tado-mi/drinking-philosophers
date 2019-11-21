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

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import java.lang.*;
import java.lang.Thread.*;

public class Demo {
    private static final int CANVAS_SIZE = 360;
        // pixels in each direction;
        // needs to agree with size in dining.html

    public static void main(String[] args) {
        JFrame f = new JFrame("Dining");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Dining me = new Dining();

        final Coordinator c = new Coordinator();
        final Table t = new Table(c, CANVAS_SIZE);
        // arrange to call graphical setup from GUI thread
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    new UI(f, c, t);
                }
            });
        } catch (Exception e) {
            System.err.println("unable to create GUI");
        }

        f.pack();            // calculate size of frame
        f.setVisible(true);
    }
}
