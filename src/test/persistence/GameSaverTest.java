package persistence;

import model.GameOfLife;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GameSaverTest {
    GameSaver gameSaver;
    GameOfLife game1;
    GameOfLife game2;

    @BeforeEach
    public void setup() {
        gameSaver = new GameSaver();
        game1 = new GameOfLife(2, 3);
        game2 = new GameOfLife(4, 4);
    }

    @Test
    public void testSaveGame() {
        try {
            gameSaver.save(game1, "test-out-game1");
            gameSaver.save(game2, "test-out-game2");
            assertTrue(isReadableFile("./data/test-out-game1.json"));
            assertTrue(isReadableFile("./data/test-out-game2.json"));
            assertEquals(game1.toJson().toString(), readFileContents("./data/test-out-game1.json").trim());
            assertEquals(game2.toJson().toString(), readFileContents("./data/test-out-game2.json").trim());
        } catch (IOException e) {
            fail("Saving and opening a game should not throw IOException.");
        }
    }

    private boolean isReadableFile(String pathname) {
        File file = new File(pathname);
        return file.exists() && !file.isDirectory() && file.canRead();
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
