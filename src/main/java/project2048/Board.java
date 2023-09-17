package project2048;

import java.util.Random;

public class Board {

    private Tile[][] board;
    private int size;
    private Random rand = new Random();

    public Board(int size) {
        checkValidBoardSize(size);
        this.size = size;
        board = new Tile[size][size];
        initializeBoard();
        addRandomTile();
        addRandomTile();
    }

    public Board(Tile[][] board, int size) {
        rand.setSeed(1);
        checkValidBoardSize(size);
        this.board = board;
        this.size = size;
    }

    private void initializeBoard() {
        for (int n = 0; n < size; n++) {
            for (int m = 0 ; m < size; m++) {
                board[n][m] = new Tile(n, m);
            }
        }
    }

    public void addRandomTile() {
        int x = rand.nextInt(size);
        int y = rand.nextInt(size);
        while (board[x][y].getValue() != 0) {    
            x = rand.nextInt(size);
            y = rand.nextInt(size);
        }
        if (rand.nextInt(100) < 70) {
            board[x][y] = new Tile(x, y, 2);
        }
        else {
            board[x][y] = new Tile(x, y, 4);
        }
    }

    public void uppdateBoard(Tile[][] board) {
        this.board = board;
    }

    public int getSize() {
        return size;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public Tile getTile(int x, int y) {
        return board[x][y];
    }

    private void checkValidBoardSize(int size) {
        if (size < 2) {
            throw new IllegalArgumentException("The size of a board must be at least 2");
        }
    }

    @Override
    public String toString() {
        String tmp = "";
        for (int n = 0; n < size; n++) {
            for (int m = 0; m < size; m++) {
                tmp = tmp + board[n][m].getValue();
            }
            tmp = tmp + "\n";
        }
        return tmp;
    }
}
