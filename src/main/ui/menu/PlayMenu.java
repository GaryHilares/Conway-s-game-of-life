package ui.menu;

import model.GameOfLife;

import java.util.Scanner;

public class PlayMenu {
    private GameOfLife game;
    private Scanner scanner;

    public PlayMenu(GameOfLife game, Scanner scanner) {
        this.game = game;
        this.scanner = scanner;
    }

    public NextMenuSignal run() {
        NextMenuSignal ret = null;
        printTitle();
        while (ret == null) {
            printPrompt();
            String input = readInput();
            ret = processInput(input);
        }
        return ret;
    }

    private void printTitle() {
        System.out.println("PLAY MODE");
    }

    private void printPrompt() {
        System.out.println(String.format("Generation %d:", game.getGenerationNumber()));
        System.out.println(game.toString());
        System.out.println("What action do you want to do?");
        System.out.println("(\"next\" to go to next generation, \"prev\" to go to previous generation,"
                + "\"edit\" to go into edition mode (deletes previous generations), or \"quit\" to quit)");
    }

    private String readInput() {
        return scanner.nextLine();
    }

    private NextMenuSignal processInput(String input) {
        if (input.equals("next") || input.equals("n")) {
            return onNext();
        } else if (input.equals("prev") || input.equals("p")) {
            return onPrev();
        } else if (input.equals("edit") || input.equals("e")) {
            return onEdit();
        } else if (input.equals("save") || input.equals("s")) {
            return onSave();
        } else if (input.equals("quit") || input.equals("q")) {
            return onQuit();
        } else {
            System.out.println("That is not a valid command!");
            return null;
        }
    }

    private NextMenuSignal onNext() {
        game.nextGeneration();
        return null;
    }

    private NextMenuSignal onPrev() {
        if (game.getGenerationNumber() == 1) {
            System.out.println("There are no previous generations left.");
        } else {
            game.previousGeneration();
        }
        return null;
    }

    private NextMenuSignal onEdit() {
        return NextMenuSignal.EDIT_MENU;
    }

    private NextMenuSignal onSave() {
        // TODO: Implement on save method.
        // new GameSaver().save(game);
        return null;
    }

    private NextMenuSignal onQuit() {
        return NextMenuSignal.QUIT;
    }
}
