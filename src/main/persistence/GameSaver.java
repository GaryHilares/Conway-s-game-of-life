package persistence;

import model.GameOfLife;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

// Represents a utility that provides functionality for saving Game of Life games.
public class GameSaver {
    // REQUIRES: name matches the following regex "[A-Za-z0-9-]{1,20}".
    // EFFECTS: Writes current game of life to a file;
    //          throws IOException if the data could not be written (e.g. due to insufficient permissions).
    public void save(GameOfLife game, String name) throws IOException {
        JSONObject gameJson = game.toJson();
        FileWriter writer = new FileWriter("./data/" + name + ".json");
        gameJson.write(writer).close();
    }
}
