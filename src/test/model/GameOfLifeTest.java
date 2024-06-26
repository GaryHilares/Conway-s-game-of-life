package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for the GameOfLife class in the model package.
public class GameOfLifeTest {
    GameOfLife game1;
    GameOfLife game2;

    @BeforeEach
    public void setup() {
        game1 = new GameOfLife(3, 4);
        game2 = new GameOfLife(5, 3);
    }

    @Test
    public void testConstructorFromWidthAndHeight() {
        assertEquals(3, game1.getWidth());
        assertEquals(4, game1.getHeight());
        assertEquals(5, game2.getWidth());
        assertEquals(3, game2.getHeight());
    }

    @Test
    public void testConstructorFromJson() {
        JSONObject sourceForGame3 = new JSONObject("{\"currentGeneration\":0,\"generations\":[{\"width\":5," +
                "\"board\":\"...O.\",\"height\":1}]}");
        JSONObject sourceForGame4 = new JSONObject("{\"currentGeneration\":1,\"generations\":[{\"width\":1," +
                "\"board\":\".O\",\"height\":2}, {\"width\":1,\"board\":\"..\",\"height\":2}]}");
        GameOfLife game3 = new GameOfLife(sourceForGame3);
        assertEquals(1, game3.getGenerationNumber());
        assertEquals(5, game3.getWidth());
        assertEquals(1, game3.getHeight());
        assertEquals("...O.", game3.toString(""));
        GameOfLife game4 = new GameOfLife(sourceForGame4);
        assertEquals(2, game4.getGenerationNumber());
        assertEquals(1, game4.getWidth());
        assertEquals(2, game4.getHeight());
        assertEquals("..", game4.toString(""));
    }

    @Test
    void testGetGenerationNumber() {
        assertEquals(1, game1.getGenerationNumber());
        assertEquals(1, game2.getGenerationNumber());
        game1.nextGeneration();
        assertEquals(2, game1.getGenerationNumber());
        assertEquals(1, game2.getGenerationNumber());
    }

    @Test
    public void testToJsonWithSingleGeneration() {
        game1.toggle(1, 1);
        for (int i = 1; i <= 3; i++) {
            game2.toggle(i, 1);
        }
        final JSONObject actualJsonForGame1 = game1.toJson();
        final JSONArray actualJsonForGensInGame1 = actualJsonForGame1.optJSONArray("generations");
        final JSONObject actualJsonForGen1InGame1 = actualJsonForGensInGame1.optJSONObject(0);
        final String expectedStringForGen1InGame1 = joinStrings("", Arrays.asList("...", ".O.", "...", "..."));
        final JSONObject actualJsonForGame2 = game2.toJson();
        final JSONArray actualJsonForGensInGame2 = actualJsonForGame2.optJSONArray("generations");
        final JSONObject actualJsonForGen1InGame2 = actualJsonForGensInGame2.optJSONObject(0);
        final String expectedStringForGen1InGame2 = joinStrings("", Arrays.asList(".....", ".OOO.", "....."));
        assertEquals(0, actualJsonForGame1.optInt("currentGeneration"));
        assertEquals(3, actualJsonForGen1InGame1.optInt("width"));
        assertEquals(4, actualJsonForGen1InGame1.optInt("height"));
        assertEquals(expectedStringForGen1InGame1, actualJsonForGen1InGame1.optString("board"));
        assertEquals(0, actualJsonForGame2.optInt("currentGeneration"));
        assertEquals(5, actualJsonForGen1InGame2.optInt("width"));
        assertEquals(3, actualJsonForGen1InGame2.optInt("height"));
        assertEquals(expectedStringForGen1InGame2, actualJsonForGen1InGame2.optString("board"));
    }

    @Test
    public void testToJsonWithMultipleGenerations() {
        game1.nextGeneration();
        for(int i = 0; i < 3; i++) {
            game2.nextGeneration();
        }
        game2.previousGeneration();
        final JSONObject actualJsonForGame1 = game1.toJson();
        final JSONArray actualJsonForGensInGame1 = actualJsonForGame1.optJSONArray("generations");
        final JSONObject actualJsonForGame2 = game2.toJson();
        final JSONArray actualJsonForGensInGame2 = actualJsonForGame2.optJSONArray("generations");
        assertEquals(1, actualJsonForGame1.optInt("currentGeneration"));
        assertEquals(2, actualJsonForGensInGame1.length());
        assertEquals(2, actualJsonForGame2.optInt("currentGeneration"));
        assertEquals(4, actualJsonForGensInGame2.length());
    }

    @Test
    public void testToString() {
        game2.toggle(2, 2);
        final String expectedStringForGame1 = joinStrings("\n", Arrays.asList("...", "...", "...", "..."));
        final String expectedStringForGame2 = joinStrings("", Arrays.asList(".....", ".....", "..O.."));
        assertEquals(expectedStringForGame1, game1.toString("\n"));
        assertEquals(expectedStringForGame2, game2.toString(""));
    }

    @Test
    public void testToggleWithSingleGeneration() {
        // Toggling once should result in a "O" in the given coordinates.
        game1.toggle(1, 1);
        game2.toggle(2, 2);
        // Toggling twice should result in a "." in the given coordinates.
        for (int i = 0; i < 2; i++) {
            game1.toggle(1, 2);
        }
        // Toggling thrice should result in a "." in the given coordinates.
        for (int i = 0; i < 3; i++) {
            game2.toggle(4, 2);
        }
        final String sep = "";
        final String expectedStringForGame1 = joinStrings(sep, Arrays.asList("...", ".O.", "...", "..."));
        final String expectedStringForGame2 = joinStrings(sep, Arrays.asList(".....", ".....", "..O.O"));
        assertEquals(expectedStringForGame1, game1.toString(sep));
        assertEquals(expectedStringForGame2, game2.toString(sep));
    }

    @Test
    void testToggleWithMultipleGenerations() {
        game1.nextGeneration();
        assertEquals(2, game1.getGenerationNumber());
        game1.nextGeneration();
        assertEquals(3, game1.getGenerationNumber());
        game1.nextGeneration();
        assertEquals(4, game1.getGenerationNumber());
        game1.previousGeneration();
        assertEquals(3, game1.getGenerationNumber());
        game1.toggle(0, 0);
        assertEquals(1, game1.getGenerationNumber());
    }

    @Test
    void testNextGeneration() {
        game1.toggle(1, 1);
        game2.toggle(1, 1);
        game2.toggle(2, 1);
        game2.toggle(3, 1);
        game1.nextGeneration();
        game2.nextGeneration();
        final String sep = "b";
        final String expectedStringForGame1 = joinStrings(sep, Arrays.asList("...", "...", "...", "..."));
        final String expectedStringForGame2 = joinStrings(sep, Arrays.asList("..O..", "..O..", "..O.."));
        assertEquals(expectedStringForGame1, game1.toString(sep));
        assertEquals(expectedStringForGame2, game2.toString(sep));
    }

    @Test
    void testNextGenerationAfterPreviousGeneration() {
        final String sep = "c";
        game1.nextGeneration();
        game2.nextGeneration();
        String snapshotOfGame1 = game1.toString(sep);
        String snapshotOfGame2 = game2.toString(sep);
        game1.previousGeneration();
        game2.previousGeneration();
        game1.nextGeneration();
        game2.nextGeneration();
        assertEquals(snapshotOfGame1, game1.toString(sep));
        assertEquals(snapshotOfGame2, game2.toString(sep));
    }

    @Test
    void testPreviousGeneration() {
        final String sep = "d";
        String game1Rep = game1.toString(sep);
        String game2Rep = game2.toString(sep);
        game1.nextGeneration();
        game2.nextGeneration();
        game1.previousGeneration();
        game2.previousGeneration();
        assertEquals(game1Rep, game1.toString(sep));
        assertEquals(game2Rep, game2.toString(sep));
    }

    @Test
    public void testSafelyGetInBounds() {
        game1.toggle(2, 1);
        assertTrue(game1.safelyGet(2, 1));
        assertFalse(game1.safelyGet(1, 1));
        game2.toggle(0, 0);
        assertTrue(game2.safelyGet(0, 0));
        assertFalse(game2.safelyGet(1, 0));
        assertFalse(game2.safelyGet(0, 1));
    }

    @Test
    public void testSafelyGetOutOfBounds() {
        for (int x = 0; x < game2.getWidth(); x++) {
            for (int y = 0; y < game2.getHeight(); y++) {
                game2.toggle(x, y);
            }
        }
        assertFalse(game2.safelyGet(-3, -2));
        assertFalse(game2.safelyGet(-1, 0));
        assertFalse(game2.safelyGet(1, -5));
        assertFalse(game2.safelyGet(3, 4));
        assertFalse(game2.safelyGet(2, 7));
        assertFalse(game2.safelyGet(5, 6));
    }

    @Test
    void testAddGeneration() {
        game1.toggle(1, 1);
        game2.toggle(1, 1);
        game2.toggle(2, 1);
        game2.toggle(3, 1);
        game1.addGeneration();
        game2.addGeneration();
        game2.addGeneration();
        assertEquals(1, game1.getGenerationNumber());
        assertEquals(1, game2.getGenerationNumber());
        assertEquals(2, game1.getGenerations().size());
        assertEquals(3, game2.getGenerations().size());
        game1.setCurrentGeneration(2);
        game2.setCurrentGeneration(3);
        final String sep = "b";
        final String expectedStringForGame1 = joinStrings(sep, Arrays.asList("...", "...", "...", "..."));
        final String expectedStringForGame2 = joinStrings(sep, Arrays.asList(".....", ".OOO.", "....."));
        assertEquals(expectedStringForGame1, game1.toString(sep));
        assertEquals(expectedStringForGame2, game2.toString(sep));
    }

    @Test
    void testSetCurrentGeneration() {
        game1.addGeneration();
        game1.addGeneration();
        game1.addGeneration();
        game1.setCurrentGeneration(3);
        assertEquals(3, game1.getGenerationNumber());
        game1.setCurrentGeneration(1);
        assertEquals(1, game1.getGenerationNumber());
        game1.setCurrentGeneration(2);
        assertEquals(2, game1.getGenerationNumber());
        game1.setCurrentGeneration(4);
        assertEquals(4, game1.getGenerationNumber());
    }

    @Test
    void testResetGenerations() {
        Generation firstGeneration = game1.getGenerations().get(0);
        game1.nextGeneration();
        game1.nextGeneration();
        game1.nextGeneration();
        game1.resetGenerations();
        assertEquals(1, game1.getGenerationNumber());
        assertEquals(1, game1.getGenerations().size());
        assertEquals(firstGeneration, game1.getGenerations().get(0));
    }

    private String joinStrings(String sep, List<String> strings) {
        StringBuilder appender = new StringBuilder();
        for (String string: strings) {
            appender.append(string);
            appender.append(sep);
        }
        return appender.toString();
    }
}