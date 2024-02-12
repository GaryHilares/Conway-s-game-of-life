package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerationTest {
    Generation generation1;
    Generation generation2;

    @BeforeEach
    public void setup() {
        generation1 = new Generation(5, 3);
        generation2 = new Generation(8, 10);
        generation2.toggle(2, 2);
        generation2.toggle(2, 3);
        generation2.toggle(2, 4);
    }

    @Test
    public void testConstructor() {
        assertEquals(5, generation1.getWidth());
        assertEquals(3, generation1.getHeight());
        assertEquals(8, generation2.getWidth());
        assertEquals(10, generation2.getHeight());
    }

    @Test
    public void testToString() {
        String gen1Rep = new StringBuilder()
                .append(".....\n")
                .append(".....\n")
                .append(".....\n")
                .toString();
        String gen2Rep = new StringBuilder()
                .append("........\n")
                .append("........\n")
                .append("..O.....\n")
                .append("..O.....\n")
                .append("..O.....\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .toString();
        assertEquals(gen1Rep, generation1.toString());
        assertEquals(gen2Rep, generation2.toString());
    }

    @Test
    public void testToggle() {
        generation1.toggle(0, 0);
        generation1.toggle(1, 1);
        String actualRep1 = generation1.toString();
        generation1.toggle(1, 1);
        generation1.toggle(1, 2);
        String actualRep2 = generation1.toString();
        String expectedRep1 = new StringBuilder()
                .append("O....\n")
                .append(".O...\n")
                .append(".....\n")
                .toString();
        String expectedRep2 = new StringBuilder()
                .append("O....\n")
                .append(".....\n")
                .append(".O...\n")
                .toString();
        assertEquals(expectedRep1, actualRep1);
        assertEquals(expectedRep2, actualRep2);
    }

    @Test
    void testNextGeneration() {
        String expectedAfter1 = generation1.toString();
        String expectedAfter2 = new StringBuilder()
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .append(".OOO....\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .append("........\n")
                .toString();
        Generation nextGeneration1 = generation1.nextGeneration();
        Generation nextGeneration2 = generation2.nextGeneration();
        assertEquals(expectedAfter1, nextGeneration1.toString());
        assertEquals(expectedAfter2, nextGeneration2.toString());
    }
}
