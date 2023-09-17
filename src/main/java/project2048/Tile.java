package project2048;

public class Tile {
    
    private int value;
    private int x;
    private int y;

    public Tile(int x, int y) {
        checkNotNegative(x);
        checkNotNegative(y);
        value = 0;
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, int value) {
        this(x, y);
        checkValidValue(value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        checkNotNegative(x);
        this.x = x;
    }

    public void setY(int y) {
        checkNotNegative(y);
        this.y = y;
    }

    public Tile mergeTiles(Tile otherTile, int x, int y) {
        checkNotSameTile(otherTile);
        checkEqualValue(otherTile);
        return new Tile(x, y, value*2);
    }

    public boolean isMergableWith(Tile otherTile) {
        checkNotSameTile(otherTile);
        return getValue() == otherTile.getValue();
    }

    private void checkEqualValue(Tile otherTile) {
        if (!isMergableWith(otherTile)) {
            throw new IllegalArgumentException("Can't merge tiles with different values");
        }
    }

    private void checkNotSameTile(Tile otherTile) {
        if (otherTile == this) {
            throw new IllegalArgumentException("A tile can't merge with it self");
        }   
    }

    private void checkNotNegative(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Must be a positive integer");
        }
    }

    private void checkValidValue(int value) {
        double result = Math.log(value) / Math.log(2);
        if ((result % 1) != 0 && value != 0) {
            throw new IllegalArgumentException("Not a valid number, value of a tile must be 2^n for a positive integer n");
        }
    }

    @Override
    public String toString() {
        return value + ":" + x + ":" + y;
    }
}
