package ui.cli.menu;

import model.GameOfLife;

import java.security.InvalidParameterException;
import java.util.Scanner;

// Represent a menu that allows the user to create a new game of life.
public class NewGameMenu extends Menu {
    private GameOfLife game;

    // MODIFIES: scanner
    // EFFECTS: Creates a new NewGameMenu that reads input from the provided Scanner.
    public NewGameMenu(Scanner scanner) {
        super(scanner);
    }

    // EFFECTS: Returns the game created by the user's interaction with this menu.
    public GameOfLife getGame() {
        return game;
    }

    // EFFECTS: Prints the title of the menu.
    @Override
    protected void printTitle() {
        System.out.println("SETUP MODE");
    }

    // EFFECTS: Prints a prompt right before the user provides input.
    @Override
    protected void printPrompt() {
        System.out.println("How *long* and *tall* do you want your board to be?");
        System.out.println("(Choose two arbitrary integers x and y so that x > 0 and y > 0)");
    }

    // EFFECTS: Reads the users' input and checks that it is two valid positive integers.
    @Override
    protected String readInput() throws IllegalArgumentException {
        String input = super.readInput();
        if (!input.matches("[1-9][0-9]* [1-9][0-9]*")) {
            throw new InvalidParameterException("Values provided are not two positive integers!");
        }
        return input;
    }

    // MODIFIES: this
    // EFFECTS: Processes the user input by creating a new Game of life with the provided dimensions;
    //          always returns NextMenuSignal.EDIT_MENU.
    @Override
    protected NextMenuSignal processInput(String input) throws IllegalArgumentException {
        int[] coords = parseCoordinates(input);
        assert coords.length == 2;
        game = new GameOfLife(coords[0], coords[1]);
        return NextMenuSignal.EDIT_MENU;
    }
}
