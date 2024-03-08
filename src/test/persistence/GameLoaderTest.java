package persistence;

import model.GameOfLife;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class GameLoaderTest {
    GameLoader gameLoader;

    @BeforeEach
    public void setup() {
        gameLoader = new GameLoader();
    }

    @Test
    public void testExistsSavedGame() {
        assertTrue(gameLoader.existsSavedGame("test-in-game1"));
        assertTrue(gameLoader.existsSavedGame("test-in-game2"));
        assertFalse(gameLoader.existsSavedGame("test-in-game3"));
        assertFalse(gameLoader.existsSavedGame("some-game1"));
    }

    @Test
    public void testLoadSavedGame() {
        try {
            GameOfLife game1 = gameLoader.loadSavedGame("test-in-game1");
            GameOfLife game2 = gameLoader.loadSavedGame("test-in-game2");
            assertEquals(2, game1.getWidth());
            assertEquals(3, game1.getHeight());
            assertEquals(1, game1.getGenerationNumber());
            assertEquals("OOO...", game1.toString(""));
            assertEquals(4, game2.getWidth());
            assertEquals(4, game2.getHeight());
            assertEquals(2, game2.getGenerationNumber());
            assertEquals("................", game2.toString(""));
        } catch (IOException e) {
            fail("Opening the file should not have caused an IOException.");
        } catch (JSONException e) {
            fail("Trying to read the JSON data should not have caused a JSONException.");
        }
    }
}
