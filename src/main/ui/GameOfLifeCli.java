package ui;

import model.GameOfLife;

import java.util.Scanner;

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
        final int height;
        final int width;
        System.out.println("How tall do you want your board to be?");
        System.out.println("(Choose an arbitrary integer n so that n > 0)");
        height = scanner.nextInt();
        System.out.println("How long do you want your board to be?");
        System.out.println("(Choose an arbitrary integer n so that n > 0)");
        width = scanner.nextInt();
        game = new GameOfLife(width, height);
    }

    // MODIFIES: this
    // EFFECTS: Sets the tiles of the board to the state provided by the user.
    public void editMode() {
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
                    System.out.println("The input is not valid!");
                }
            } else {
                String input = scanner.nextLine();
                if (input.equals("done")) {
                    done = true;
                } else {
                    System.out.println("The input is not valid!");
                }
            }
        } while (!done);
    }

    // REQUIRES: Game has already been set up.
    // MODIFIES: this
    // EFFECTS: Processes user input for the game.
    public void run() {
        String input;
        do {
            System.out.println(String.format("Generation %d:", game.getGenerationNumber()));
            System.out.println(game.toString());
            System.out.println("What do you want to do?");
            input = scanner.nextLine();
            if (input.equals("next")) {
                game.nextGeneration();
            } else if (input.equals("prev")) {
                if (game.getGenerationNumber() == 1) {
                    System.out.println("There are no previous generations left.");
                } else {
                    game.previousGeneration();
                }
            } else if (input.equals("edit")) {
                editMode();
            } else if (!input.equals("quit")) {
                System.out.println("That is not a valid command!");
            }
        } while (!input.equals("quit"));
    }
}