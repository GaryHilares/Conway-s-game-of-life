package ui.gui.menu;

import model.GameOfLife;
import persistence.GameSaver;
import ui.gui.GameOfLifeGui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

// Represents a menu to interact with the user's Conway's game of life.
public class PlayMenu extends Menu {
    private GameOfLife game;
    private JButton[][] buttons;

    // EFFECTS: Builds a new PlayMenu associated with the given GUI that displays the given game.
    public PlayMenu(GameOfLifeGui gui, GameOfLife game) {
        super(gui);
        this.game = game;
        this.buttons = new JButton[game.getWidth()][game.getHeight()];
        this.addElements();
    }

    // MODIFIES: this
    // EFFECTS: Adds the menu elements to the view.
    @Override
    protected void addElements() {
        JPanel topPanel = createButtonBox();
        JScrollPane centerPanel = createBoard();
        view.setLayout(new BorderLayout());
        view.add(topPanel, BorderLayout.PAGE_START);
        view.add(centerPanel, BorderLayout.CENTER);
    }

    // EFFECTS: Creates a board that can be used to view the game state.
    private JScrollPane createBoard() {
        JPanel board = new JPanel();
        board.setLayout(null);
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                buttons[x][y] = new JButton();
                buttons[x][y].setBounds(50 * x, 50 * y, 50, 50);
                buttons[x][y].setBackground(game.safelyGet(x, y) ? Color.BLACK : Color.WHITE);
                board.add(buttons[x][y]);
            }
        }
        board.setPreferredSize(new Dimension(game.getWidth() * 50, game.getHeight() * 50));
        JScrollPane res = new JScrollPane(board);
        res.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        res.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return res;
    }

    // EFFECTS: Creates a button box with "Edit", "Save", "Previous", "Next" and "Main menu" buttons.
    private JPanel createButtonBox() {
        JPanel buttonBox = new JPanel();
        buttonBox.add(createGenerationsButton());
        buttonBox.add(createPrevButton());
        buttonBox.add(createNextButton());
        buttonBox.add(createEditButton());
        buttonBox.add(createSaveButton());
        buttonBox.add(createMainMenuButton());
        return buttonBox;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new "Edit" button.
    private JButton createEditButton() {
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(e -> goToMenu(new EditMenu(gui, game)));
        return editButton;
    }

    // EFFECTS: Creates a new "Save" button.
    private JButton createSaveButton() {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                new GameSaver().save(game, "save");
                JOptionPane.showMessageDialog(view, "Saved successfully!");
            } catch (IOException err) {
                JOptionPane.showMessageDialog(view, "An error occurred while trying to save your game.");
            }
        });
        return saveButton;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new "Previous" button.
    private JButton createPrevButton() {
        JButton prevButton = new JButton("Previous");
        prevButton.addActionListener(e -> {
            if (game.getGenerationNumber() == 1) {
                JOptionPane.showMessageDialog(view, "There are no previous generations!");
            } else {
                game.previousGeneration();
            }
            updateBoard();
        });
        return prevButton;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new "Next" button.
    private JButton createNextButton() {
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(e -> {
            game.nextGeneration();
            updateBoard();
        });
        return nextButton;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new "Main menu" button.
    private JButton createMainMenuButton() {
        JButton nextButton = new JButton("Main menu");
        nextButton.addActionListener(e -> gui.setMenu(new MainMenu(gui)));
        return nextButton;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new "Generations" button.
    private JButton createGenerationsButton() {
        JButton nextButton = new JButton("Generations");
        nextButton.addActionListener(e -> gui.setMenu(new GenerationListMenu(gui, game)));
        return nextButton;
    }

    // MODIFIES: this
    // EFFECTS: Updates the button colors to make them match the game state.
    private void updateBoard() {
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                buttons[x][y].setBackground(game.safelyGet(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
    }
}
