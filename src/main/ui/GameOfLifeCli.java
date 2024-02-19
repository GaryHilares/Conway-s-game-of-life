package ui;

import model.GameOfLife;

import java.util.Scanner;

// Command line interface that allows user to interact and play with the Conway's game of life simulator.
// Displays a list of cells (i.e. allows viewing a list of items) in edit and play modes.
public class GameOfLifeCli {
    GameOfLife game;
    Scanner scanner;

    public GameOfLifeCli() {
        scanner = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: Processes user input for the setup
    public void setup() {
        setupDimensions();
        editMode();
    }

    // MODIFIES: this
    // EFFECTS: Sets the dimensions of the board to arbitrary numbers provided by user.
    public void setupDimensions() {
        System.out.println("SETUP MODE");
        final int height;
        final int width;
        System.out.println("How *tall* do you want your board to be?");
        System.out.println("(Choose an arbitrary integer n so that n > 0)");
        height = scanner.nextInt();
        System.out.println("How *long* do you want your board to be?");
        System.out.println("(Choose another arbitrary integer n so that n > 0)");
        width = scanner.nextInt();
        game = new GameOfLife(width, height);
    }

    // MODIFIES: this
    // EFFECTS: Sets the tiles of the board to the state provided by the user.
    public void editMode() {
        System.out.println("EDIT MODE");
        boolean done = false;
        do {
            System.out.println(game.toString());
            System.out.println("Enter two integers x and y to toggle that tile, or \"done\":");
            System.out.println("(0 <= x < width, 0 <= y < height)");
            if (scanner.hasNextInt()) {
                final int x = scanner.nextInt();
                if (scanner.hasNextInt()) {
                    final int y = scanner.nextInt();
                    game.toggle(x, y);
                } else {
                    System.out.println("The input is not an integer!");
                }
            } else {
                String input = scanner.nextLine();
                if (input.equals("done")) {
                    done = true;
                } else if (!input.trim().isEmpty()) {
                    System.out.println("The input is not valid!");
                }
            }
        } while (!done);
    }

    // REQUIRES: Game has already been set up.
    // MODIFIES: this
    // EFFECTS: Processes user input for the game.
    public void run() {
        System.out.println("PLAY MODE");
        String input;
        do {
            System.out.println(String.format("Generation %d:", game.getGenerationNumber()));
            System.out.println(game.toString());
            System.out.println("What action do you want to do?");
            System.out.println("(\"next\" to go to next generation, \"prev\" to go to previous generation,"
                             + "\"edit\" to go into edition mode (deletes previous generations), or \"quit\" to quit)");
            input = scanner.nextLine();
            if (input.equals("next") || input.equals("n")) {
                game.nextGeneration();
            } else if (input.equals("prev") || input.equals("p")) {
                if (game.getGenerationNumber() == 1) {
                    System.out.println("There are no previous generations left.");
                } else {
                    game.previousGeneration();
                }
            } else if (input.equals("edit") || input.equals("e")) {
                editMode();
            } else if (!input.equals("quit") && !input.equals("q")) {
                System.out.println("That is not a valid command!");
            }
        } while (!input.equals("quit") && !input.equals("q"));
    }
}
