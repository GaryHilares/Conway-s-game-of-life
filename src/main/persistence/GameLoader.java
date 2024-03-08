package persistence;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import model.GameOfLife;
import org.json.JSONException;
import org.json.JSONObject;

// Represents a utility that provides functionality for loading saved Game of Life games.
public class GameLoader {
    // REQUIRES: name matches the following regex "[A-Za-z0-9-]{1,20}".
    // EFFECTS: Returns true if a saved game exists, false otherwise.
    public boolean existsSavedGame(String name) {
        return new File("./data/" + name + ".json").exists();
    }

    // REQUIRES: name matches the following regex "[A-Za-z0-9-]{1,20}".
    // EFFECTS: Returns the GameOfLife object represented by the saved file;
    //          throws IOException if file cannot be read (e.g. if it does not exist), and
    //          throws JSONException if file can be read but is invalid JSON.
    public GameOfLife loadSavedGame(String name) throws IOException, JSONException {
        final String savedContent = readFileContents("./data/" + name + ".json").trim();
        JSONObject gameJson = new JSONObject(savedContent);
        return new GameOfLife(gameJson);
    }

    private String joinStrings(String sep, List<String> strings) {
        StringBuilder appender = new StringBuilder();
        for (String string: strings) {
            appender.append(string);
            appender.append(sep);
        }
        return appender.toString();
    }

    private String readFileContents(String pathname) throws IOException {
        return joinStrings("\n", Files.readAllLines(Paths.get(pathname)));
    }
}
