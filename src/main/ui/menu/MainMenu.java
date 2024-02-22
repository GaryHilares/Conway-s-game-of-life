package ui.menu;

import model.GameOfLife;

import java.util.Scanner;

public class MainMenu {
    GameOfLife game;
    Scanner scanner;

    public MainMenu(Scanner scanner) {
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

    public GameOfLife getGame() {
        return game;
    }

    private void printTitle() {
        System.out.println("MAIN MENU");
    }

    private void printPrompt() {
        System.out.println("Would you like to load or start a new game?");
        System.out.println("(Type \"new\" to start a new game and \"load\" to load).");
    }

    private String readInput() throws IllegalArgumentException {
        String input = scanner.nextLine();
        if (!input.equals("new") && !input.equals("load")) {
            throw new IllegalArgumentException("The action provided does not exist!");
        }
        return input;
    }

    private NextMenuSignal processInput(String input) throws IllegalArgumentException {
        if (input.equals("new")) {
            return NextMenuSignal.NEW_GAME_MENU;
        } else {
            /*GameLoader loader = new GameLoader();
            if (!loader.existsSavedGame()) {
                throw new IllegalArgumentException("Saved game does not exist!");
            }
            game = loader.loadSavedGame();*/
            System.out.println("Loaded game!");
            game = new GameOfLife(5, 5);
            return NextMenuSignal.PLAY_MENU;
        }
    }
}
