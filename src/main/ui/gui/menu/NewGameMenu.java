package ui.gui.menu;

import model.GameOfLife;
import ui.gui.GameOfLifeGui;

import javax.swing.*;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;

// Represents a menu to create a new board of Conway's game of life.
public class NewGameMenu extends Menu {
    // EFFECTS: Builds a new NewGameMenu associated with the given GUI.
    public NewGameMenu(GameOfLifeGui gui) {
        super(gui);
        this.addElements();
    }

    // MODIFIES: this
    // EFFECTS: Adds the menu elements to the view.
    @Override
    protected void addElements() {
        JLabel labelX = new JLabel("How long do you want your board to be?");
        JLabel labelY = new JLabel("How tall do you want your board to be?");
        JSpinner spinnerX = new JSpinner();
        spinnerX.setMaximumSize(new Dimension(100, 30));
        JSpinner spinnerY = new JSpinner();
        spinnerY.setMaximumSize(new Dimension(100, 30));
        JButton submitButton = new JButton("Ok!");
        submitButton.addActionListener(e -> {
            int x = (Integer) spinnerX.getValue();
            int y = (Integer) spinnerY.getValue();
            if (x <= 0 || y <= 0) {
                JOptionPane.showMessageDialog(view, "One of the input numbers is <= 0!");
            } else {
                goToMenu(new EditMenu(gui, new GameOfLife(x, y)));
            }
        });
        layOutComponents(Arrays.asList(labelX, spinnerX, labelY, spinnerY, submitButton));
    }

    // MODIFIES: this
    // EFFECTS: Lays out the components in a single column with padding.
    private void layOutComponents(List<JComponent> comps) {
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
        view.add(Box.createVerticalStrut(50));
        for (int i = 0; i < comps.size(); i++) {
            if (i != 0) {
                view.add(Box.createVerticalStrut(20));
            }
            final JComponent comp = comps.get(i);
            comp.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            view.add(comp);
        }
    }
}
