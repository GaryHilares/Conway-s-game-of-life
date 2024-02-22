package ui.menu;

import model.GameOfLife;

import java.util.InputMismatchException;
import java.util.Scanner;

public class NewGameMenu {
    private GameOfLife game;
    private Scanner scanner;

    public NewGameMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public NextMenuSignal run() {
        NextMenuSignal ret = null;
        printTitle();
        while (ret == null) {
            printPrompt();
            try {
                int[] input = readInput();
                ret = processInput(input);
            } catch (IllegalArgumentException e) {
                System.out.println("The provided values were invalid.");
                System.out.println(e.getMessage());
            }
        }
        return ret;
    }

    public GameOfLife getGame() {
        return game;
    }

    private void printTitle() {
        System.out.println("SETUP MODE");
    }

    private void printPrompt() {
        System.out.println("How *long* and *tall* do you want your board to be?");
        System.out.println("(Choose two arbitrary integers x and y so that x > 0 and y > 0)");
    }

    private int[] readInput() throws IllegalArgumentException {
        try {
            final int x = scanner.nextInt();
            final int y = scanner.nextInt();

            // Ignore newline if it exists.
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            if (x <= 0 || y <= 0) {
                throw new IllegalArgumentException("A provided integer n does not fulfill n > 0.");
            }
            int[] ret = new int[2];
            ret[0] = x;
            ret[1] = y;
            return ret;
        } catch (InputMismatchException e) {
            throw new IllegalArgumentException("The provided values are not integers!");
        }
    }

    private NextMenuSignal processInput(int[] input) {
        assert input.length == 2;
        game = new GameOfLife(input[0], input[1]);
        return NextMenuSignal.EDIT_MENU;
    }
}
