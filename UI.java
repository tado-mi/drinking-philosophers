import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

// Class UI is the user interface.  It displays a Table canvas above
// a row of buttons.  Actions (event handlers) are defined for each of
// the buttons.  Depending on the state of the UI, either the "run" or
// the "pause" button is the default (highlighted in most window
// systems); it will often self-push if you hit carriage return.
//
class UI extends JPanel {
    private final Coordinator c;
    private final Table t;

    private final JRootPane root;
    private static final int externalBorder = 6;

    private static final int stopped = 0;
    private static final int running = 1;
    private static final int paused = 2;

    private int state = stopped;

    // Constructor
    //
    public UI(RootPaneContainer pane, Coordinator C, Table T) {
        final UI u = this;
        c = C;
        t = T;

        final JPanel b = new JPanel();   // button panel

        final JButton runButton = new JButton("Run");
        final JButton pauseButton = new JButton("Pause");
        final JButton resetButton = new JButton("Reset");
        final JButton quitButton = new JButton("Quit");

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.resume();
                root.setDefaultButton(pauseButton);
            }
        });
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.pause();
                root.setDefaultButton(runButton);
            }
        });
        resetButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                t.reset();
                root.setDefaultButton(runButton);
            }
        });
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // put the buttons into the button panel:
        b.setLayout(new FlowLayout());
        b.add(runButton);
        b.add(pauseButton);
        b.add(resetButton);
        b.add(quitButton);

        // put the Table canvas and the button panel into the UI:
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(
            externalBorder, externalBorder, externalBorder, externalBorder));
        add(t);
        add(b);

        // put the UI into the Frame
        pane.getContentPane().add(this);
        root = getRootPane();
        root.setDefaultButton(runButton);
    }
}
