package project2048;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TileTest {
    private Tile tile1, tile2, tile3;

    @BeforeEach
    public void setup() {
        tile1 = new Tile(0, 0, 2);
        tile2 = new Tile(1, 0, 2);
        tile3 = new Tile(1,1);
    }

    @Test
    public void testConstructor() {
        assertEquals("0:1:1", tile3.toString());
        assertThrows(IllegalArgumentException.class, () -> {
            new Tile(-1, -3, -4);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Tile(2, 0, 7);
        });
    }

    @Test
    public void testMergeTiles() {
        Tile actual = tile1.mergeTiles(tile2, 0, 0);
        Tile expected = new Tile(0, 0, 4);
        assertEquals(expected.toString(), actual.toString());
        assertThrows(IllegalArgumentException.class, () -> {
            tile1.mergeTiles(tile3, 0, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            tile1.mergeTiles(tile1, 0, 0);
        });
    }

    @Test
    public void testIsMergableWith() {
        assertTrue(tile1.isMergableWith(tile2));
        assertFalse(tile1.isMergableWith(tile3));
        assertThrows(IllegalArgumentException.class, () -> {
            tile1.mergeTiles(tile1, 0, 0);
        });
    }

    @Test
    public void testToString() {
        assertEquals("2:0:0", tile1.toString());
        tile3 = tile1.mergeTiles(tile2, 3, 4);
        assertEquals("4:3:4", tile3.toString());
    }
}
