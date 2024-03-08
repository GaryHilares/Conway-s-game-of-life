package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests for the Generation class in the model package.
public class GenerationTest {
    Generation generation1;
    Generation generation2;

    @BeforeEach
    public void setup() {
        generation1 = new Generation(5, 3);
        generation2 = new Generation(3, 5);
    }

    @Test
    public void testConstructorFromWidthAndHeight() {
        assertEquals(5, generation1.getWidth());
        assertEquals(3, generation1.getHeight());
        assertEquals(3, generation2.getWidth());
        assertEquals(5, generation2.getHeight());
    }

    @Test
    public void testConstructorFromJson() {
        final String stringForGen3 = joinStrings("", Arrays.asList("O", "."));
        final String stringForGen4 = joinStrings("", Arrays.asList("O...", "....", "....", "....", "...O"));
        JSONObject sourceForGen3 = new JSONObject();
        sourceForGen3.put("width", 1);
        sourceForGen3.put("height", 2);
        sourceForGen3.put("board", stringForGen3);
        Generation generation3 = new Generation(sourceForGen3);
        JSONObject sourceForGen4 = new JSONObject();
        sourceForGen4.put("width", 4);
        sourceForGen4.put("height", 5);
        sourceForGen4.put("board", stringForGen4);
        Generation generation4 = new Generation(sourceForGen4);
        assertEquals(1, generation3.getWidth());
        assertEquals(2, generation3.getHeight());
        assertEquals(stringForGen3, generation3.toString(""));
        assertEquals(4, generation4.getWidth());
        assertEquals(5, generation4.getHeight());
        assertEquals(stringForGen4, generation4.toString(""));
    }

    @Test
    void testNextGeneration() {
        generation2.toggle(1, 1);
        generation2.toggle(1, 2);
        generation2.toggle(1, 3);
        final String sep = "";
        final String expectedStringForNextGen1 = joinStrings(sep, Arrays.asList(".....", ".....", "....."));
        final String expectedStringForNextGen2 = joinStrings(sep, Arrays.asList("...", "...", "OOO", "...", "..."));
        assertEquals(expectedStringForNextGen1, generation1.nextGeneration().toString(sep));
        assertEquals(expectedStringForNextGen2, generation2.nextGeneration().toString(sep));
    }

    @Test
    public void testToString() {
        final String sep = "";
        final String expectedStringForGen1 = joinStrings(sep, Arrays.asList(".....", ".....", "....."));
        final String expectedStringForGen2 = joinStrings(sep, Arrays.asList("...", "...", "...", "...", "..."));
        assertEquals(expectedStringForGen1, generation1.toString(sep));
        assertEquals(expectedStringForGen2, generation2.toString(sep));
    }

    @Test
    public void testToggle() {
        // Toggling once should result in a "O" in the given coordinates.
        generation1.toggle(0, 0);
        generation2.toggle(1, 1);
        // Toggling twice should result in a "." in the given coordinates.
        for (int i = 0; i < 2; i++) {
            generation1.toggle(1, 1);
        }
        // Toggling thrice should result in a "." in the given coordinates.
        for (int i = 0; i < 3; i++) {
            generation2.toggle(1, 2);
        }
        final String sep = "";
        final String expectedStringForGen1 = joinStrings(sep, Arrays.asList("O....", ".....", "....."));
        final String expectedStringForGen2 = joinStrings(sep, Arrays.asList("...", ".O.", ".O.", "...", "..."));
        assertEquals(expectedStringForGen1, generation1.toString(sep));
        assertEquals(expectedStringForGen2, generation2.toString(sep));
    }

    @Test
    public void testToJson() {
        generation2.toggle(2, 1);
        generation2.toggle(1, 1);
        final String expectedStringForGen1 = joinStrings("", Arrays.asList(".....", ".....", "....."));
        final String expectedStringForGen2 = joinStrings("", Arrays.asList("...", ".OO", "...", "...", "..."));
        final JSONObject actualJsonForGen1 = generation1.toJson();
        final JSONObject actualJsonForGen2 = generation2.toJson();
        assertEquals(5, actualJsonForGen1.optInt("width"));
        assertEquals(3, actualJsonForGen1.optInt("height"));
        assertEquals(expectedStringForGen1, actualJsonForGen1.optString("board"));
        assertEquals(3, actualJsonForGen2.optInt("width"));
        assertEquals(5, actualJsonForGen2.optInt("height"));
        assertEquals(expectedStringForGen2, actualJsonForGen2.optString("board"));
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
