package project2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BoardTest {
    
    private Board board;

    @BeforeEach
    public void setup() {
        //Creates the board:
        //2200
        //0000
        //0000
        //0000
        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile(j, i);
            }
        }
        tiles[0][0] = new Tile(0, 0, 2);
        tiles[0][1] = new Tile(1, 0, 2);
        board = new Board(tiles, 4);
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Board(0);
        });
        board = new Board(4);
        int zeroCounter = 0;
        int valueCounter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getBoard()[i][j].getValue() == 2 ||  board.getBoard()[i][j].getValue() == 4) {
                    valueCounter ++;
                }
                else if (board.getBoard()[i][j].getValue() == 0) {
                    zeroCounter ++;
                }
            }
        }
        assertEquals(2, valueCounter);
        assertEquals(14, zeroCounter);
    }

    @Test
    public void testAddRandomTile() {
        board.addRandomTile();
        int counter = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (board.getBoard()[i][j].getValue() == 2 ||  board.getBoard()[i][j].getValue() == 4) {
                    counter ++;
                }
            }
        }
        assertEquals(3, counter);
    }

    @Test
    public void testToString() {
        String expected = """
            2200
            0000
            0000
            0000
                """;
        String actual = board.toString();
        assertEquals(expected, actual);
    }

}
