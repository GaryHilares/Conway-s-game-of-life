package ui.gui;

import model.Event;
import model.EventLog;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Represents a window listener that can be used with a Swing JFrame to log events on exit.
public class LoggingWindowListener implements WindowListener {
    // EFFECTS: Do nothing (needed for complete interface implementation).
    @Override
    public void windowOpened(WindowEvent e) {
        // Do nothing.
    }

    // EFFECTS: Prints all the program's logs to the console, then exits the program.
    @Override
    public void windowClosing(WindowEvent e) {
        EventLog eventLog = EventLog.getInstance();
        for (Event event: eventLog) {
            System.out.println(event.toString());
        }
        System.exit(0);
    }

    // EFFECTS: Do nothing (needed for complete interface implementation).
    @Override
    public void windowClosed(WindowEvent e) {
        // Do nothing.
    }

    // EFFECTS: Do nothing (needed for complete interface implementation).
    @Override
    public void windowIconified(WindowEvent e) {
        // Do nothing.
    }

    // EFFECTS: Do nothing (needed for complete interface implementation).
    @Override
    public void windowDeiconified(WindowEvent e) {
        // Do nothing.
    }

    // EFFECTS: Do nothing (needed for complete interface implementation).
    @Override
    public void windowActivated(WindowEvent e) {
        // Do nothing.
    }

    // EFFECTS: Do nothing (needed for complete interface implementation).
    @Override
    public void windowDeactivated(WindowEvent e) {
        // Do nothing.
    }
}
