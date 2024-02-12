package model;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
    private List<Generation> generations;
    private int currentGeneration;

    // REQUIRES: width > 0, height > 0
    // EFFECTS: Builds a game of life with the given arbitrary dimensions.
    public GameOfLife(int width, int height) {
        generations = new ArrayList<>();
        generations.add(new Generation(width, height));
        currentGeneration = 0;
    }

    // REQUIRES: 0 <= x < width, 0 <= y < height
    // MODIFIES: this
    // EFFECTS: Toggles the cell at (x, y) (kills it if it is alive, revives it otherwise).
    public void toggle(int x, int y) {
        generations.get(currentGeneration).toggle(x, y);
    }

    // EFFECTS: Returns the 1-indexed generation number of the current generation.
    public int getGenerationNumber() {
        return currentGeneration + 1;
    }

    // EFFECTS: Returns width of the board.
    public int getWidth() {
        return generations.get(currentGeneration).getWidth();
    }

    // EFFECTS: Returns height of the board.
    public int getHeight() {
        return generations.get(currentGeneration).getHeight();
    }

    // EFFECTS: Returns a string representation of the board.
    public String toString() {
        return generations.get(currentGeneration).toString();
    }

    // MODIFIES: this
    // EFFECTS: Computes the next generation and moves the game state to it.
    public void nextGeneration() {
        if (currentGeneration + 1 >= generations.size()) {
            generations.add(generations.get(currentGeneration).nextGeneration());
        }
        currentGeneration++;
    }

    // REQUIRES getGenerationNumber() > 1
    // MODIFIES: this
    // EFFECTS: Returns to the previous generation.
    public void previousGeneration() {
        currentGeneration--;
    }
}
