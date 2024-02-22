package ui.menu;

import model.GameOfLife;

import java.util.Scanner;

public class EditMenu {
    private GameOfLife game;
    private Scanner scanner;

    public EditMenu(GameOfLife game, Scanner scanner) {
        this.game = game;
        this.scanner = scanner;
    }

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

    private void printTitle() {
        System.out.println("EDIT MODE");
    }

    private void printPrompt() {
        System.out.println(game.toString());
        System.out.println("Enter two integers x and y to toggle that tile, or \"done\":");
        System.out.println("(0 <= x < width, 0 <= y < height)");
    }

    private String readInput() throws IllegalArgumentException {
        String input = scanner.nextLine();
        if (!input.matches("(done)|([0-9]+ [0-9]+)")) {
            throw new IllegalArgumentException(input + " is not a valid input!");
        }
        return input;
    }

    private NextMenuSignal processInput(String input) throws IllegalArgumentException {
        if (input.equals("done")) {
            return NextMenuSignal.PLAY_MENU;
        }

        String[] params = input.split(" ");
        if (params.length != 2) {
            throw new IllegalArgumentException("Incorrect amount of numbers provided!");
        }

        final int x;
        final int y;
        try {
            x = Integer.parseInt(params[0]);
            y = Integer.parseInt(params[1]);
        }  catch (NumberFormatException e) {
            throw new IllegalArgumentException("The provided numbers are not valid integers!");
        }

        if (x < 0 || x >= game.getWidth() || y < 0 || y >= game.getHeight()) {
            throw new IllegalArgumentException("The provided integers are out of the board's bounds!");
        }

        game.toggle(x, y);
        return null;
    }
}
