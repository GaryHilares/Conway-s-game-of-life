package ui.gui.menu;

import model.GameOfLife;
import ui.gui.GameOfLifeGui;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

// Represents a menu to edit the user's Conway's game of life.
public class EditMenu extends Menu {
    GameOfLife game;

    // EFFECTS: Builds a new EditMenu associated with the given GUI that edits the given game.
    public EditMenu(GameOfLifeGui gui, GameOfLife game) {
        super(gui);
        this.game = game;
        this.addElements();
    }

    // MODIFIES: this
    // EFFECTS: Adds the menu elements to the view.
    @Override
    public void addElements() {
        JPanel topPanel = createButtonBox();
        JScrollPane centerPanel = createBoard();
        view.setLayout(new BorderLayout());
        view.add(topPanel, BorderLayout.PAGE_START);
        view.add(centerPanel, BorderLayout.CENTER);
    }

    // EFFECTS: Creates a new button box with a "Submit button".
    private JPanel createButtonBox() {
        JPanel buttonBox = new JPanel();
        buttonBox.add(createSubmitButton());
        return buttonBox;
    }

    // MODIFIES: this
    // EFFECTS: Creates a new "Submit" button.
    private JButton createSubmitButton() {
        JButton submitButton = new JButton("Done!");
        submitButton.addActionListener(e -> gui.setMenu(new PlayMenu(gui, game)));
        return submitButton;
    }

    // MODIFIES: this
    // EFFECTS: Creates an interactive board that can be used to edit the game state.
    private JScrollPane createBoard() {
        JPanel board = new JPanel();
        board.setLayout(null);
        for (int x = 0; x < game.getWidth(); x++) {
            for (int y = 0; y < game.getHeight(); y++) {
                final int buttonX = x;
                final int buttonY = y;
                JButton button = new JButton();
                button.setBounds(50 * x, 50 * y, 50, 50);
                button.setBackground(game.safelyGet(buttonX, buttonY) ? Color.BLACK : Color.WHITE);
                button.addActionListener(e -> {
                    game.toggle(buttonX, buttonY);
                    button.setBackground(game.safelyGet(buttonX, buttonY) ? Color.BLACK : Color.WHITE);
                });
                board.add(button);
            }
        }
        board.setPreferredSize(new Dimension(game.getWidth() * 50, game.getHeight() * 50));
        JScrollPane res = new JScrollPane(board);
        res.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        res.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return res;
    }
}
