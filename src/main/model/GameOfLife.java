package model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Represents a game of Conway's Game of Life. It manages information about the current generation and the (arbitrarily
// sized) timeline of generations that have appeared so far, allowing the user to "go back on time".
public class GameOfLife {
    private final List<Generation> generations;
    private int currentGeneration;

    // REQUIRES: width > 0, height > 0
    // EFFECTS: Builds a game of life with the given arbitrary dimensions.
    public GameOfLife(int width, int height) {
        generations = new ArrayList<>();
        generations.add(new Generation(width, height));
        currentGeneration = 0;
    }

    // REQUIRES: JSONObject represents a valid GameOfLife.
    // EFFECTS: Builds a game of life with the JSONObject.
    public GameOfLife(JSONObject source) throws JSONException {
        generations = new ArrayList<>();
        currentGeneration = source.getInt("currentGeneration");
        JSONArray generationsJson = source.getJSONArray("generations");
        for (int i = 0; i < generationsJson.length(); i++) {
            JSONObject generationJson = generationsJson.getJSONObject(i);
            generations.add(new Generation(generationJson));
        }
    }

    // EFFECTS: Returns the 1-indexed generation number of the current generation.
    public int getGenerationNumber() {
        return currentGeneration + 1;
    }

    // EFFECTS: Returns height of the board.
    public int getHeight() {
        return generations.get(currentGeneration).getHeight();
    }

    // EFFECTS: Returns width of the board.
    public int getWidth() {
        return generations.get(currentGeneration).getWidth();
    }

    // EFFECTS: Returns a JSONObject representation of the game, including all generations.
    public JSONObject toJson() {
        JSONObject gameOfLifeJson = new JSONObject();
        gameOfLifeJson.put("currentGeneration", currentGeneration);

        JSONArray generationsJson = new JSONArray();
        for (Generation generation: generations) {
            generationsJson.put(generation.toJson());
        }
        gameOfLifeJson.put("generations", generationsJson);
        return gameOfLifeJson;
    }

    // EFFECTS: Returns a string representation of the board with the given separator.
    public String toString(String sep) {
        return generations.get(currentGeneration).toString(sep);
    }

    // REQUIRES: 0 <= x < width, 0 <= y < height
    // MODIFIES: this
    // EFFECTS: Toggles the cell at (x, y) (kills it if it is alive, revives it otherwise);
    //          resets the timeline of generations (generations do not belong to the same timeline after the edition).
    public void toggle(int x, int y) {
        Generation generation = generations.get(currentGeneration);
        generation.toggle(x, y);
        generations.clear();
        generations.add(generation);
        currentGeneration = 0;
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

    // EFFECTS: Returns the cell at (x, y) of the current generation, or false if (x, y) is out of bonds.
    public boolean safelyGet(int x, int y) {
        return generations.get(currentGeneration).safelyGet(x, y);
    }
}
