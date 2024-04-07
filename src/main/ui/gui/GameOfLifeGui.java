package ui.gui;

import ui.gui.menu.MainMenu;
import ui.gui.menu.Menu;

import javax.swing.*;

// Graphic user interface that allows user to interact and play with the Conway's game of life simulator.
// Displays a list of cells (i.e. allows viewing a list of items) in edit and play modes.
public class GameOfLifeGui {
    JFrame frame;
    Menu menu;

    // EFFECTS: Initializes a new GUI with an empty frame.
    public GameOfLifeGui() {
        frame = new JFrame("Conway's game of life simulator");
        menu = null;
    }

    // MODIFIES: this
    // EFFECTS: Makes the GUI visible and runs the application.
    public void run() {
        this.setMenu(new MainMenu(this));
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.addWindowListener(new LoggingWindowListener());
    }

    // REQUIRES: menu != null
    // MODIFIES: this
    // EFFECTS: Moves the user from the previous menu to the given new menu.
    public void setMenu(Menu menu) {
        if (this.menu != null) {
            frame.remove(this.menu.getView());
        }
        this.menu = menu;
        frame.add(menu.getView());
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}
