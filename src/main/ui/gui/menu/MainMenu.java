package ui.gui.menu;

import model.GameOfLife;
import org.json.JSONException;
import persistence.GameLoader;
import ui.gui.GameOfLifeGui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.io.IOException;

// Represents the main menu that allows the user to start a new Conway's game of life or load a previous one.
public class MainMenu extends Menu {
    // EFFECTS: Builds a new MainMenu associated with the given GUI.
    public MainMenu(GameOfLifeGui gui) {
        super(gui);
        this.addElements();
    }

    // MODIFIES: this
    // EFFECTS: Adds the menu elements to the view.
    @Override
    protected void addElements() {
        JButton newGameButton = createNewGameButton();
        JButton loadGameButton = createLoadGameButton();
        JLabel image = getImage();
        if (image != null) {
            layOutComponents(Arrays.asList(newGameButton, loadGameButton, image));
        } else {
            layOutComponents(Arrays.asList(newGameButton, loadGameButton));
        }
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

    // EFFECTS: Produces a new "New game" button, with its associated behavior.
    private JButton createNewGameButton() {
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> goToMenu(new NewGameMenu(gui)));
        return newGameButton;
    }

    // EFFECTS: Produces a new "Load game" button, with its associated behavior.
    private JButton createLoadGameButton() {
        JButton loadGameButton = new JButton("Load Game");
        loadGameButton.addActionListener(e -> {
            GameLoader loader = new GameLoader();
            if (!loader.existsSavedGame("save")) {
                JOptionPane.showMessageDialog(view, "Saved game does not exist!");
            }
            try {
                GameOfLife game =  loader.loadSavedGame("save");
                goToMenu(new PlayMenu(gui, game));
            } catch (IOException | JSONException err) {
                String message = "There was an error while opening your saved file. Perhaps it is corrupted?";
                JOptionPane.showMessageDialog(view, message);
            }
        });
        return loadGameButton;
    }

    // MODIFIES: this
    // EFFECT: Loads and produces it, or shows an error.
    private JLabel getImage() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("./src/game-of-life.png"));
            Image image = bufferedImage.getScaledInstance(200, 200, BufferedImage.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(image));
            return label;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(view, "Error loading the image!");
            return null;
        }
    }
}
