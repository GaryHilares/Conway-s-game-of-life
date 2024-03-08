package ui.menu;

import model.GameOfLife;
import org.json.JSONException;
import persistence.GameLoader;

import java.io.IOException;
import java.util.Scanner;

// Represents the main menu of the application, providing options to start a new game or load one.
public class MainMenu extends Menu {
    private GameOfLife game;

    // MODIFIES: scanner
    // EFFECTS: Creates a new MainMenu that reads input from the provided Scanner.
    public MainMenu(Scanner scanner) {
        super(scanner);
    }

    // EFFECTS: Returns the game loaded by the "load" option.
    public GameOfLife getGame() {
        return game;
    }

    // EFFECTS: Prints title of the main menu.
    @Override
    protected void printTitle() {
        System.out.println("MAIN MENU");
    }

    // EFFECTS: Prints prompt right before the user provides input.
    @Override
    protected void printPrompt() {
        System.out.println("Would you like to load or start a new game?");
        System.out.println("(Type \"new\" to start a new game and \"load\" to load).");
    }

    // EFFECTS: Reads the input from the user and checks that it is a valid action.
    @Override
    protected String readInput() throws IllegalArgumentException {
        String input = super.readInput();
        if (!input.equals("new") && !input.equals("load")) {
            throw new IllegalArgumentException("The action provided does not exist!");
        }
        return input;
    }

    // MODIFIES: this
    // EFFECTS: Processes the users' input by either sending him to the new game menu or by loading a game.
    //          Returns either NextMenuSignal.NEW_GAME_MENU or NextMenuSignal.PLAY_MENU depending on the option
    //          selected.
    @Override
    protected NextMenuSignal processInput(String input) throws IllegalArgumentException {
        if (input.equals("new")) {
            return NextMenuSignal.NEW_GAME_MENU;
        } else {
            GameLoader loader = new GameLoader();
            if (!loader.existsSavedGame("save")) {
                throw new IllegalArgumentException("Saved game does not exist!");
            }
            try {
                game = loader.loadSavedGame("save");
                return NextMenuSignal.PLAY_MENU;
            } catch (IOException | JSONException e) {
                System.out.println("There was an error while opening the file. Perhaps your saved file is corrupted?");
                return null;
            }
        }
    }
}
