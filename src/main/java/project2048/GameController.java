package project2048;

import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameController {

    public Game game;
    private ISaveHandler saveHandler = new SaveHandler();

    @FXML private Pane board;
    @FXML private TextField filename;
    @FXML private Text fileMessage;
    @FXML private Button handleSave, handleLoad;


    @FXML
    private void initialize() {
        game = new Game(4, 2048);
        createBoard();
        drawBoard();
    }

    private void drawBoard() {
        for (int y = 0; y < game.getBoard().getSize(); y++) {
            for (int x = 0; x < game.getBoard().getSize(); x++) {
                Pane pane = (Pane) board.getChildren().get(y * game.getBoard().getSize() + x);
                pane.setStyle("-fx-background-color: " + getTileColor(game.getBoard().getTile(y, x)) + "; -fx-border-color: black; -fx-border-radius: 5; -fx-background-radius: 5;");
                Text text = (Text) pane.getChildren().get(0);
                text.setText(game.getBoard().getBoard()[y][x].getValue() != 0 ? Integer.toString(game.getBoard().getBoard()[y][x].getValue()) : "");
            }
        }
    }

    private void createBoard() {
        board.getChildren().clear();
        for (int y = 0; y < game.getBoard().getSize(); y++) {
            for (int x = 0; x < game.getBoard().getSize(); x++) {
                Pane tile = new Pane();
                tile.setTranslateX(x * board.getPrefHeight()/game.getBoard().getSize());
                tile.setTranslateY(y * board.getPrefHeight()/game.getBoard().getSize());
                tile.setPrefWidth(board.getPrefHeight()/game.getBoard().getSize());
                tile.setPrefHeight(board.getPrefHeight()/game.getBoard().getSize());
                Text text = new Text();
                text.setTranslateX(tile.getPrefHeight()/2-9);
                text.setTranslateY(tile.getPrefHeight()/2+7);
                text.setStyle("-fx-font-size: 20px;");
                tile.getChildren().add(text);
                board.getChildren().add(tile);
            }
        }
    }

    public void handleMovement(KeyEvent e) {
        if (!game.getGameOver() && !game.getGameWon()) {
            switch (e.getCode()) {
                case W: game.moveUp();drawBoard();      break;
                case S: game.moveDown();drawBoard();    break;
                case A: game.moveLeft();drawBoard();    break;
                case D: game.moveRight();drawBoard();   break;
                default: ;
            }   
            if (this.game.getGameWon()) {
                this.gameWon();
            }
            else if (this.game.getGameOver()) {
                this.gameLost();
            }
        }
    }

    private void gameWon() {
        Text gameWon = new Text("Game Won!");
        gameWon.setStyle("-fx-font-size: 70px;");
        gameWon.setTranslateX(20);
        gameWon.setTranslateY(170);
        gameWon.setFill(Color.GREEN);
        gameWon.setStroke(Color.BLACK);
        board.getChildren().add(gameWon);
    }

    private void gameLost() {
        Text gameLost = new Text("Game Lost");
        gameLost.setStyle("-fx-font-size: 70px;");
        gameLost.setTranslateX(30);
        gameLost.setTranslateY(170);
        gameLost.setFill(Color.RED);
        gameLost.setStroke(Color.BLACK);
        board.getChildren().add(gameLost);
        System.out.println("GameOver");
    }

    private String getTileColor(Tile tile) {
        if (tile.getValue() == 0) {
            return "#ffffff";
        } else if (tile.getValue() == 2) {
            return "#eee4da";
        } else if (tile.getValue() == 4) {
            return "#ede0c8";
        } else if (tile.getValue() == 8) {
            return "#f2b179";
        } else if (tile.getValue() == 16) {
            return "#f59563";
        } else if (tile.getValue() == 32) {
            return "#f67c60";
        } else if (tile.getValue() == 64) {
            return "#f65e3b";
        } else if (tile.getValue() == 128) {
            return "#edcf73";
        } else if (tile.getValue() == 256) {
            return "#edcc62";
        } else if (tile.getValue() == 512) {
            return "#edc850";
        } else if (tile.getValue() == 1024) {
            return "#edc53f";
        } else if (tile.getValue() == 2048) {
            return "#edc22d";
        }
        return "#000000";
    }

    @FXML
    private void handle3x3() {
        fileMessage.setVisible(false);
        game = new Game(3, 256);
        createBoard();
        drawBoard();
    }

    @FXML
    private void handle4x4() {
        fileMessage.setVisible(false);
        game = new Game(4, 2048);
        createBoard();
        drawBoard();
    }

    @FXML
    private void handle5x5() {
        fileMessage.setVisible(false);
        game = new Game(5, 2048);
        createBoard();
        drawBoard();
    }

    private String getFilename() {
        String filename = this.filename.getText();
        if (filename.isEmpty()) {
            filename = "saveFile";
        }
        return filename;
    }

    @FXML
    private void handleSave() {
        if (!game.getGameOver() && !game.getGameWon()) {
            try {
                if (getFilename().matches("^[a-zA-Z0-9]+$")) {
                    saveHandler.save(getFilename(), game);
                }
                else {
                    throw new FileNotFoundException();
                }
                fileMessage.setText("File saved to " + getFilename() + ".txt");
                fileMessage.setVisible(true);
            } catch (FileNotFoundException e) {
                fileMessage.setText("Invalid filename");
                fileMessage.setVisible(true);
            }
        }
        else if (game.getGameOver()) {
            fileMessage.setText("Can't save when game is over");
            fileMessage.setVisible(true);
        }
        else if (game.getGameWon()) {
            fileMessage.setText("Can't save when game is won");
            fileMessage.setVisible(true);
        }
    }

    @FXML
    private void handleLoad() {
        try {
            game = saveHandler.load(getFilename());
            fileMessage.setText("Sucsessfully loaded " + getFilename() + ".txt");
            fileMessage.setVisible(true);
        } catch (FileNotFoundException ex) {
            fileMessage.setText("The file " + getFilename() + ".txt does not exist");
            fileMessage.setVisible(true);
        }
        catch (NumberFormatException ex) {
            fileMessage.setText("The file is not a valid gamefile");
            fileMessage.setVisible(true);
        }
        createBoard();
        drawBoard();
    }
    
}
