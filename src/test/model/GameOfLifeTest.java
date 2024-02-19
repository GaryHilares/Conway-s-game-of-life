package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {
    GameOfLife game1;
    GameOfLife game2;

    @BeforeEach
    public void setup() {
        game1 = new GameOfLife(5, 3);
        game2 = new GameOfLife(8, 10);
        game2.toggle(2, 2);
        game2.toggle(2, 3);
        game2.toggle(2, 4);
    }

    @Test
    public void testConstructor() {
        assertEquals(5, game1.getWidth());
        assertEquals(3, game1.getHeight());
        assertEquals(8, game2.getWidth());
        assertEquals(10, game2.getHeight());
    }

    @Test
    public void testToString() {
        String game1Rep = new StringBuilder()
                .append(".....\n")
                .append(".....\n")
                .append(".....\n")
                .toString();
        String game2Rep = new StringBuilder()
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
        assertEquals(game1Rep, game1.toString());
        assertEquals(game2Rep, game2.toString());
    }

    @Test
    public void testToggle() {
        game1.toggle(0, 0);
        game1.toggle(1, 1);
        String actualRep1 = game1.toString();
        game1.toggle(1, 1);
        game1.toggle(1, 2);
        String actualRep2 = game1.toString();
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
    void getGenerationNumber() {
        assertEquals(1, game1.getGenerationNumber());
        assertEquals(1, game2.getGenerationNumber());
        game1.nextGeneration();
        assertEquals(2, game1.getGenerationNumber());
        assertEquals(1, game2.getGenerationNumber());
    }

    @Test
    void testNextGeneration() {
        String expectedAfter1 = game1.toString();
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
        game1.nextGeneration();
        game2.nextGeneration();
        assertEquals(expectedAfter1, game1.toString());
        assertEquals(expectedAfter2, game2.toString());
    }

    @Test
    void testPreviousGeneration() {
        String game1Rep = game1.toString();
        String game2Rep = game2.toString();
        game1.nextGeneration();
        game2.nextGeneration();
        game1.previousGeneration();
        game2.previousGeneration();
        assertEquals(game1Rep, game1.toString());
        assertEquals(game2Rep, game2.toString());
    }

    @Test
    void testNextGenerationAfterPreviousGeneration() {
        game1.nextGeneration();
        game2.nextGeneration();
        String game1Rep = game1.toString();
        String game2Rep = game2.toString();
        game1.previousGeneration();
        game2.previousGeneration();
        game1.nextGeneration();
        game2.nextGeneration();
        assertEquals(game1Rep, game1.toString());
        assertEquals(game2Rep, game2.toString());
    }
}