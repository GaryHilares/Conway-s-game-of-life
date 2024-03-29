package ui.gui.menu;

import model.GameOfLife;
import model.Generation;
import ui.gui.GameOfLifeGui;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

// Represents a menu that lists all generations in a game (i.e. displays a list of all Xs in a Y). Provides options to
// add new generations, reset the generations, and go to a specific generation.
public class GenerationListMenu extends Menu {
    GameOfLife game;
    JPanel generationList;
    JScrollPane generationListScrollPane;

    // EFFECTS: Creates a GenerationListMenu associated with the given GUI that displays the given game.
    public GenerationListMenu(GameOfLifeGui gui, GameOfLife game) {
        super(gui);
        this.game = game;
        this.generationList = new JPanel();
        this.generationListScrollPane = new JScrollPane(this.generationList);
        this.generationListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.generationListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.updateGenerationList();
        this.addElements();
    }

    // REQUIRES: this not modified since the last call to updateGenerationList().
    // MODIFIES: this
    // EFFECTS: Adds the menu elements to the view.
    @Override
    protected void addElements() {
        JPanel topPanel = createButtonBox();
        JScrollPane centerPanel = getScrollPane();
        view.setLayout(new BorderLayout());
        view.add(topPanel, BorderLayout.PAGE_START);
        view.add(centerPanel, BorderLayout.CENTER);
    }

    // EFFECTS: Produces a list of button components with each of the generations in current game.
    private List<JComponent> generateButtons() {
        List<JComponent> res = new ArrayList<>();
        List<Generation> generations = game.getGenerations();
        for (int i = 0; i < generations.size(); i++) {
            int aliveCells = generations.get(i).countAliveCells();
            JButton button = new JButton(String.format("Generation %d (%d cells alive)", i, aliveCells));
            final int index = i;
            button.addActionListener(e -> {
                game.setCurrentGeneration(index + 1);
                gui.setMenu(new PlayMenu(gui, game));
            });
            res.add(button);
        }
        return res;
    }

    // MODIFIES: this
    // EFFECTS: Updates the generation list to display the current amount of buttons.
    private void updateGenerationList() {
        generationList.removeAll();
        layOutComponents(generationList, generateButtons());
        generationList.revalidate();
        generationList.repaint();
        generationListScrollPane.revalidate();
        generationListScrollPane.repaint();
    }

    // EFFECTS: Returns a JScrollPane that displays the generation list.
    private JScrollPane getScrollPane() {
        return generationListScrollPane;
    }

    // EFFECTS: Creates a button box with "New Generation" and "Reset Generations" buttons.
    private JPanel createButtonBox() {
        JPanel panel = new JPanel();
        panel.add(createNewGenerationButton());
        panel.add(createResetGenerationsButton());
        return panel;
    }

    // EFFECTS: Creates a new "New Generation" button.
    private JButton createNewGenerationButton() {
        JButton button = new JButton("New Generation");
        button.addActionListener(e -> {
            game.addGeneration();
            updateGenerationList();
        });
        return button;
    }

    // EFFECTS: Creates a new "Reset Generations" button.
    private JButton createResetGenerationsButton() {
        JButton button = new JButton("Reset Generations");
        button.addActionListener(e -> {
            game.resetGenerations();
            updateGenerationList();
        });
        return button;
    }

    // MODIFIES: panel
    // EFFECTS: Lays out the components in the panel in a single column with padding.
    private void layOutComponents(JPanel panel, List<JComponent> comps) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalStrut(50));
        for (int i = 0; i < comps.size(); i++) {
            if (i != 0) {
                panel.add(Box.createVerticalStrut(20));
            }
            final JComponent comp = comps.get(i);
            comp.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            panel.add(comp);
        }
    }
}
