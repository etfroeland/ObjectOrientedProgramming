package project2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SaveHandlerTest {

    private Game game;
    private SaveHandler saveHandler = new SaveHandler();

    @BeforeAll
    public void setup() {
        //Creates the board:
        //2244
        //0000
        //2004
        //0004
        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
        tiles[0][0] = new Tile(0, 0, 2);
        tiles[0][1] = new Tile(1, 0, 2);
        tiles[0][2] = new Tile(2, 0, 4);
        tiles[0][3] = new Tile(3, 0, 4);
        tiles[2][0] = new Tile(0, 2, 2);
        tiles[2][3] = new Tile(3, 2, 4);
        tiles[3][3] = new Tile(3, 3, 4);
        game = new Game(tiles, 4, 2048);
    }
    
    @Test
    public void testSave() {
        game.updateTileLocation();
        try {
            saveHandler.save("new-test-save", game);
        } catch (Exception e) {
            fail("Could not save file");
        }
        
        byte[] testFile = null, newFile = null;

		try {
			testFile = Files.readAllBytes(new File(SaveHandler.class.getResource("saves/").getFile() + "test-save.txt").toPath());
		} catch (IOException e) {
			fail("Could not load test-save");
		}

		try {
			newFile = Files.readAllBytes(new File(SaveHandler.class.getResource("saves/").getFile() + "new-test-save.txt").toPath());
		} catch (IOException e) {
			fail("Could not load new-test-save");
		}
		assertNotNull(testFile);
		assertNotNull(newFile);
		assertTrue(Arrays.equals(testFile, newFile));
    }

    @Test
    public void testLoad() {
        Game testSave;
		try {
			testSave = saveHandler.load("test-save");
		} catch (FileNotFoundException e) {
			fail("Could not load saved file");
			return;
		}
		assertEquals(game.toString(), testSave.toString());
		assertFalse(game.getGameOver());

        assertThrows(FileNotFoundException.class, () -> {
            game = saveHandler.load("no-file");
        });

        assertThrows(Exception.class, () -> {
            game = saveHandler.load("invalid-gamefile");
        });
    }

    @AfterAll
	static void teardown() {
		File newTestSaveFile = new File(SaveHandler.class.getResource("saves/").getFile() + "new-test-save.txt");
		newTestSaveFile.delete();
	}
}
