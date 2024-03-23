package ui.gui.menu;

import ui.gui.GameOfLifeGui;

import javax.swing.*;

// Abstract class for a Menu. It controls the associated GUI of the menu and its view.
public abstract class Menu {
    protected GameOfLifeGui gui;
    protected JPanel view;

    // EFFECTS: Constructs a menu associated to the given GUI.
    public Menu(GameOfLifeGui gui) {
        this.gui = gui;
        this.view = new JPanel();
    }

    // EFFECTS: Returns the current view.
    public JPanel getView() {
        return view;
    }

    // REQUIRES: menu != null
    // MODIFIES: this
    // EFFECTS: Moves the user to the given menu.
    protected void goToMenu(Menu menu) {
        gui.setMenu(menu);
    }

    // MODIFIES: this
    // EFFECTS: Adds the menu elements to the view.
    protected abstract void addElements();
}
