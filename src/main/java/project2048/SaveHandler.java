package project2048;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class SaveHandler implements ISaveHandler {

	@Override
	public void save(String filename, Game game) throws FileNotFoundException {	
		try (PrintWriter writer = new PrintWriter(new File(getFilePath(filename)))) {
			writer.println(game.getBoard().getSize());
			writer.println(game.getMaxTileValue());
			for (int i = 0; i < game.getBoard().getSize(); i++) {
				for (int j = 0; j < game.getBoard().getSize(); j++) {
					writer.println(game.getBoard().getBoard()[i][j]);
				}
			}
		}
	}

	@Override
	public Game load(String filename) throws FileNotFoundException {
		Game game;
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
			int size = Integer.parseInt(scanner.nextLine());
			int maxTileValue = Integer.parseInt(scanner.nextLine());
			Tile[][] board = new Tile[size][size];;
			for (int i = 0; i < size*size; i++) {
				String reader = scanner.nextLine();
				String[] tmp = reader.split(":");
				board[Integer.parseInt(tmp[1])][Integer.parseInt(tmp[2])] = new Tile(Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]), Integer.parseInt(tmp[0]));
			}
			game = new Game(board, size, maxTileValue);
		}
		return game;
	}

	public static String getFilePath(String filename) {
		try {
			System.out.println(SaveHandler.class.getResource("saves/").getFile() + filename + ".txt");
		}
		catch (Exception e) {
			System.out.println(e);
		}
		return SaveHandler.class.getResource("saves/").getFile() + filename + ".txt";
	}

}
