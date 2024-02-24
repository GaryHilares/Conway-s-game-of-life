package persistence;

import java.io.File;

import model.GameOfLife;

// Represents a utility that provides functionality for loading saved Game of Life games.
public class GameLoader {
    // TODO: Add tests for this function.
    // EFFECTS: Returns true if a saved game exists, false otherwise.
    public boolean existsSavedGame() {
        File file = new File("./data/save.json");
        return file.exists() && !file.isDirectory() && file.canRead();
    }

    // TODO: Add tests for this function.
    // TODO: Implement this function.
    // EFFECTS: Returns the GameOfLife object represented by the saved file;
    //          throws IOException if file cannot be read (e.g. if it does not exist), and
    //          throws JSONException if file can be read but is invalid JSON.
    public GameOfLife loadSavedGame() {
        return null;
    }
}
