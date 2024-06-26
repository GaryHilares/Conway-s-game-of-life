package model;

import java.util.ArrayList;
import java.util.Collections;
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
    // MODIFIES: this, EventLog.getInstance()
    // EFFECTS: Builds a game of life with the given arbitrary dimensions.
    public GameOfLife(int width, int height) {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Created new GameOfLife (" + width + "x" + height + ")."));
        generations = new ArrayList<>();
        generations.add(new Generation(width, height));
        currentGeneration = 0;
    }

    // REQUIRES: JSONObject represents a valid GameOfLife.
    // MODIFIES: this, EventLog.getInstance()
    // EFFECTS: Builds a game of life with the JSONObject.
    public GameOfLife(JSONObject source) throws JSONException {
        generations = new ArrayList<>();
        currentGeneration = source.getInt("currentGeneration");
        JSONArray generationsJson = source.getJSONArray("generations");
        for (int i = 0; i < generationsJson.length(); i++) {
            JSONObject generationJson = generationsJson.getJSONObject(i);
            generations.add(new Generation(generationJson));
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Loaded " + generationsJson.length() + " generations from JSON to the game."));
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

    // MODIFIES: EventLog.getInstance()
    // EFFECTS: Returns a JSONObject representation of the game, including all generations.
    public JSONObject toJson() {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Converting " + generations.size() + " generations in game to JSON."));
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

    // MODIFIES: this, EventLog.getInstance()
    // EFFECTS: Computes the next generation and moves the game state to it.
    public void nextGeneration() {
        if (currentGeneration + 1 >= generations.size()) {
            addGeneration();
        }
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Going to next generation (generation " + (currentGeneration + 1) + ")."));
        currentGeneration++;
    }

    // REQUIRES getGenerationNumber() > 1
    // MODIFIES: this, EventLog.logEvent
    // EFFECTS: Returns to the previous generation.
    public void previousGeneration() {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Going to previous generation (generation " + (currentGeneration - 1) + ")."));
        currentGeneration--;
    }

    // EFFECTS: Returns the cell at (x, y) of the current generation, or false if (x, y) is out of bonds.
    public boolean safelyGet(int x, int y) {
        return generations.get(currentGeneration).safelyGet(x, y);
    }

    // EFFECTS: Return generation list as an unmodifiable list.
    public List<Generation> getGenerations() {
        return Collections.unmodifiableList(generations);
    }

    // REQUIRES: 1 <= generation <= getGenerations().size()
    // MODIFIES: this, EventLog.getInstance()
    // EFFECT: Sets the current generation to the given 1-indexed generation.
    public void setCurrentGeneration(int newGeneration) {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Setting current generation to " + newGeneration + "."));
        currentGeneration = newGeneration - 1;
    }

    // MODIFIES: this, EventLog.getInstance()
    // EFFECTS: Removes all of the generations in this game of life, except the first one.
    public void resetGenerations() {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Resetting generations to first generation."));
        Generation initialGeneration = generations.get(0);
        generations.clear();
        generations.add(initialGeneration);
        currentGeneration = 0;
    }

    // MODIFIES: this, EventLog.getInstance()
    // EFFECTS: Creates new generation at the end of the generation list.
    public void addGeneration() {
        EventLog eventLog = EventLog.getInstance();
        eventLog.logEvent(new Event("Creating new generation at the end of generations."));
        generations.add(generations.get(generations.size() - 1).nextGeneration());
    }
}
