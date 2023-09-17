package project2048;


public class Game {

    private Board board;
    private boolean GameWon;
    private int maxTileValue; 
    private boolean GameOver;

    public Game(int size, int maxTileValue) {
        checkValidMaxTileValue(maxTileValue);
        board = new Board(size);
        GameOver = false;
        GameWon = false;
        this.maxTileValue = maxTileValue;
    }

    public Game(Tile[][] board, int size, int maxTileValue) {
        this.board = new Board(board, size);
        this.maxTileValue = maxTileValue;
        GameOver = false;
        GameWon = false;
    }

    public boolean getGameOver() {
        return GameOver;
    }

    public boolean getGameWon() {
        return GameWon;
    }

    public int getMaxTileValue() {
        return maxTileValue;
    }

    public Board getBoard() {
        return board;
    }

    private void compress(Tile[][] board) {
        Boolean changed = false;
        Tile[][] tmp = new Tile[this.board.getSize()][this.board.getSize()];
        int pos;
        for (int i = 0; i < this.board.getSize(); i++) {
            pos = 0;
            for (int j = 0; j < this.board.getSize(); j++) {
                if (board[i][j].getValue() != 0) {
                    tmp[i][pos] = board[i][j];
                    if (j != pos){
                        changed = true;
                    }
                    pos ++;
                }
            }
        }
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int j = 0; j < this.board.getSize(); j++) {
                if (tmp[i][j] == null) {
                    tmp[i][j] = new Tile(j, i);
                }
            }
        }
        if (changed) {
            getBoard().uppdateBoard(tmp);
        }
    }

    private void merge(Tile[][] board) {
        Tile[][] tmp = board;
        Boolean changed = true;
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int j = 0; j < this.board.getSize() - 1; j++) {
                if (tmp[i][j].getValue() == tmp[i][j+1].getValue() && tmp[i][j].getValue() != 0) {
                    tmp[i][j] = tmp[i][j].mergeTiles(tmp[i][j+1], i, j);
                    tmp[i][j+1] = new Tile(j, i);
                    changed = false;
                }
            }
        }
        if (changed) {
            getBoard().uppdateBoard(tmp);
        }
    }

    private void reverse() {
        Tile[][] board = this.board.getBoard();
        Tile[][] tmp = new Tile[this.board.getSize()][this.board.getSize()];
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int j = 0; j < this.board.getSize(); j++) {
                tmp[i][j] = board[i][this.board.getSize() - 1 - j];
            }
        }
        getBoard().uppdateBoard(tmp);
    }

    private void transpose() {
        Tile[][] board = this.board.getBoard();
        Tile[][] tmp = new Tile[this.board.getSize()][this.board.getSize()];
        for (int i = 0; i < this.board.getSize(); i++) {
            for (int j = 0; j < this.board.getSize(); j++) {
                tmp[i][j] = board[j][i];
            }
        }
        getBoard().uppdateBoard(tmp);
    }

    public void updateTileLocation() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j].getX() != i) {
                    board.getBoard()[i][j].setX(i);
                }
                if (board.getBoard()[i][j].getY() != j) {
                    board.getBoard()[i][j].setY(j);
                }
            }
        }
    }

    private boolean checkFullBoard() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void checkValidMaxTileValue(int value) {
        double result = Math.log(value) / Math.log(2);
        if (((result % 1) != 0) ) {
            throw new IllegalArgumentException("Not a valid number, value of a tile must be 2^n for a positive integer n");
        }
    }

    private void isGameOver() {
        if (checkFullBoard()) {
            Boolean mergable = false;
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    if (i == board.getSize() - 1 && j == board.getSize() - 1) {
                        continue;
                    }
                    else if (i == board.getSize() - 1) {
                        if (board.getBoard()[i][j].isMergableWith(board.getBoard()[i][j+1])) {
                            mergable = true;
                        }
                    }
                    else if (j == board.getSize() - 1) {
                        if (board.getBoard()[i][j].isMergableWith(board.getBoard()[i+1][j])) {
                            mergable = true;
                        }
                    }
                    else {
                        if (board.getBoard()[i][j].isMergableWith(board.getBoard()[i+1][j]) || board.getBoard()[i][j].isMergableWith(board.getBoard()[i][j+1])) {
                            mergable = true;
                        }
                    }
                }
            }
        if (!mergable && !GameWon) {
            GameOver = true;
            }
        }
    }

    private void isGameWon() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getBoard()[i][j].getValue() == maxTileValue) {
                    GameWon = true;
                }
            }
        }
    }

    public void moveLeft() {
        if (GameOver) {
            throw new IllegalStateException("Game Over");
        }
        compress(board.getBoard());
        merge(board.getBoard());
        compress(board.getBoard());
        if (!checkFullBoard()) {
            board.addRandomTile();
        }
        isGameWon();
        isGameOver();
        updateTileLocation();
    }

    public void moveRight() {
        reverse();
        moveLeft();
        reverse();
        updateTileLocation();
    }

    public void moveUp() {
        transpose();
        moveLeft();
        transpose();
        updateTileLocation();
    }

    public void moveDown() {
        transpose();
        moveRight();
        transpose();
        updateTileLocation();
    }

    @Override
    public String toString() {
        return board.toString();
    }
}
