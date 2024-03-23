package ui.cli.menu;

import model.GameOfLife;
import persistence.GameSaver;

import java.io.IOException;
import java.util.Scanner;

// Represents a menu that allows the user to play the Game of life.
public class PlayMenu extends Menu {
    private GameOfLife game;

    // MODIFIES: game, scanner
    // EFFECTS: Creates a new PlayMenu that plays the given GameOfLife and reads input from the provided Scanner.
    public PlayMenu(GameOfLife game, Scanner scanner) {
        super(scanner);
        this.game = game;
    }

    // EFFECTS: Prints the menu's title.
    @Override
    protected void printTitle() {
        System.out.println("PLAY MODE");
    }

    // EFFECTS: Prints the menu's prompt.
    @Override
    protected void printPrompt() {
        System.out.println(String.format("Generation %d:", game.getGenerationNumber()));
        System.out.println(game.toString("\n"));
        System.out.println("What action do you want to do?");
        System.out.println("(\"next\" to go to next generation, \"prev\" to go to previous generation,"
                + "\"edit\" to go into edition mode (deletes previous generations), or \"quit\" to quit)");
    }

    // MODIFIES: this
    // EFFECTS: Processes the user's input and calls the corresponding command;
    //          throws IllegalArgumentException if command is not valid.
    @Override
    protected NextMenuSignal processInput(String input) throws IllegalArgumentException {
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
            throw new IllegalArgumentException("That is not a valid command!");
        }
    }

    // MODIFIES: this
    // EFFECTS: Sets the game to the next generation. Always returns null.
    private NextMenuSignal onNext() {
        game.nextGeneration();
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Sets the game to the previous generation. Always returns null.
    private NextMenuSignal onPrev() {
        if (game.getGenerationNumber() == 1) {
            System.out.println("There are no previous generations left.");
        } else {
            game.previousGeneration();
        }
        return null;
    }

    // EFFECTS: Sends the user to the edit menu. Always returns NextMenuSignal.EDIT_MENU.
    private NextMenuSignal onEdit() {
        return NextMenuSignal.EDIT_MENU;
    }

    // EFFECTS: Saves the game to a file. Always returns null.
    private NextMenuSignal onSave() {
        try {
            new GameSaver().save(game, "save");
            System.out.println("Saved successfully!");
        } catch (IOException e) {
            System.out.println("Could not save game!");
            System.out.println(e.getMessage());
        }
        return null;
    }

    // EFFECTS: Quits the game. Always returns NextMenuSignal.QUIT.
    private NextMenuSignal onQuit() {
        return NextMenuSignal.QUIT;
    }
}
