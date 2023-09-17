package project2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

    Game game;

    @BeforeEach
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
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Game(1, 2047);
        });
    }
    
    @Test
    public void testMoveLeft() {
        String expected = """
            4800
            0200
            2400
            4000
                """;

        game.moveLeft();
        assertEquals(expected, game.toString());
    }

    @Test
    public void testMoveRight() {
        String expected = """
            0048
            0020
            0024
            0004
                """;

        game.moveRight();
        assertEquals(expected, game.toString());
    }

    @Test
    public void testMoveUp() {
        String expected = """
            4248
            0204
            0000
            0000
                """;

        game.moveUp();
        assertEquals(expected, game.toString());
    }

    @Test
    public void testMoveDown() {
        String expected = """
            0000
            0000
            0204
            4248
                """;

        game.moveDown();
        assertEquals(expected, game.toString());
    }

    @Test
    public void testGameWon() {
        //Creates the board:
        //1024,1024,00
        //0000
        //0000
        //0000
        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
        tiles[0][0] = new Tile(0, 0, 1024);
        tiles[0][1] = new Tile(1, 0, 1024);
        game = new Game(tiles, 4, 2048);

        assertFalse(game.getGameWon());
        game.moveLeft();
        assertTrue(game.getGameWon());
    }

    @Test
    public void testGameOver() {
        //Creates the board:
        //2424
        //4242
        //2428
        //4244
        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
        tiles[0][0] = new Tile(0, 0, 2);
        tiles[0][1] = new Tile(1, 0, 4);
        tiles[0][2] = new Tile(2, 0, 2);
        tiles[0][3] = new Tile(3, 0, 4);
        tiles[1][0] = new Tile(0, 1, 4);
        tiles[1][1] = new Tile(1, 1, 2);
        tiles[1][2] = new Tile(2, 1, 4);
        tiles[1][3] = new Tile(3, 1, 2);
        tiles[2][0] = new Tile(0, 2, 2);
        tiles[2][1] = new Tile(1, 2, 4);
        tiles[2][2] = new Tile(2, 2, 2);
        tiles[2][3] = new Tile(3, 2, 8);
        tiles[3][0] = new Tile(0, 3, 4);
        tiles[3][1] = new Tile(1, 3, 2);
        tiles[3][2] = new Tile(2, 3, 4);
        tiles[3][3] = new Tile(3, 3, 4);
        game = new Game(tiles, 4, 2048);

        assertFalse(game.getGameOver());
        game.moveLeft();
        assertTrue(game.getGameOver());
    }

    @Test
    public void testToString() {
        String expected = """
            2244
            0000
            2004
            0004
                """;
        String actual = game.toString();
        assertEquals(expected, actual);
    }
}
