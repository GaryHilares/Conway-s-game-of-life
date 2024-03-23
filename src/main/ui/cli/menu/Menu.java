package ui.cli.menu;

import java.util.Scanner;

// Represents the general operations that need to be performed by a menu.
public abstract class Menu {
    private final Scanner scanner;

    // MODIFIES: scanner
    // EFFECTS: Creates a new Menu that reads input from the provided Scanner.
    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    // MODIFIES: this
    // EFFECTS: Runs the menu loop, printing the title, prompting the user, reading the input, and processing it.
    public NextMenuSignal run() {
        NextMenuSignal ret = null;
        printTitle();
        while (ret == null) {
            printPrompt();
            try {
                String input = readInput();
                ret = processInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println("The provided values were invalid.");
                System.out.println(e.getMessage());
            }
        }
        return ret;
    }

    // EFFECTS: Prints the title of the menu when the menu is accessed.
    protected abstract void printTitle();

    // EFFECTS: Prints the prompt of the menu to the console each time that input is required from the user.
    protected abstract void printPrompt();

    // MODIFIES: this
    // EFFECTS: Reads next line of input; subclasses may parse the input and throw IllegalArgumentException when it is
    //          illegal. Updates the scanner to read the next input.
    protected String readInput() throws IllegalArgumentException {
        return scanner.nextLine();
    }

    // EFFECTS: Processes the input, making necessary modifications. Subclasses may throw IllegalArgumentException if
    //          input provided is invalid.
    protected abstract NextMenuSignal processInput(String input) throws IllegalArgumentException;

    // EFFECTS: Parses the input as two integers separated by a space, returning an integer array of length 2;
    //          throws IllegalArgumentException if given input is not in correct format.
    int[] parseCoordinates(String input) throws IllegalArgumentException {
        String[] params = input.split(" ");

        if (params.length != 2) {
            throw new IllegalArgumentException("Incorrect amount of numbers provided!");
        }

        try {
            final int x = Integer.parseInt(params[0]);
            final int y = Integer.parseInt(params[1]);
            int[] ret = new int[2];
            ret[0] = x;
            ret[1] = y;
            return ret;
        }  catch (NumberFormatException e) {
            throw new IllegalArgumentException("The provided numbers are not valid integers!");
        }
    }
}
