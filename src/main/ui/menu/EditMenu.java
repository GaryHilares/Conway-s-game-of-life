package ui.menu;

import model.GameOfLife;

import java.util.Scanner;

// Represents a menu that allows the user to edit his or her Game of Life board.
public class EditMenu extends Menu {
    private GameOfLife game;

    // MODIFIES: game, scanner
    // EFFECTS: Creates a new NewGameMenu that edits the given GameOfLife and reads input from the provided Scanner.
    public EditMenu(GameOfLife game, Scanner scanner) {
        super(scanner);
        this.game = game;
    }

    // EFFECTS: Prints the title of the menu.
    @Override
    protected void printTitle() {
        System.out.println("EDIT MODE");
    }

    // EFFECTS: Prints a prompt right before the user provides input.
    @Override
    protected void printPrompt() {
        System.out.println(game.toString("\n"));
        System.out.println("Enter two integers x and y to toggle that tile, or \"done\":");
        System.out.println("(0 <= x < width, 0 <= y < height)");
    }

    // EFFECTS: Reads the users' input and checks that it is either "done" or two valid non-negative integers.
    @Override
    protected String readInput() throws IllegalArgumentException {
        String input = super.readInput();
        if (!input.matches("(done)|([0-9]+ [0-9]+)")) {
            throw new IllegalArgumentException(input + " is not a valid input!");
        }
        return input;
    }
    
    // MODIFIES: this
    // EFFECTS: Toggles the given cell in the Game of life. Produces NextMenuSignal.PLAY_MENU iff the input is "done",
    //          null otherwise.
    @Override
    protected NextMenuSignal processInput(String input) throws IllegalArgumentException {
        if (input.equals("done")) {
            return NextMenuSignal.PLAY_MENU;
        }

        final int[] coords = parseCoordinates(input);
        assert coords.length == 2;
        final int x = coords[0];
        final int y = coords[1];

        if (x < 0 || x >= game.getWidth() || y < 0 || y >= game.getHeight()) {
            throw new IllegalArgumentException("The provided integers are out of the board's bounds!");
        }

        game.toggle(x, y);
        return null;
    }
}
