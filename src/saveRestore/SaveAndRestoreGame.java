package saveRestore;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import drawing.BlueBoardColorImpl;
import drawing.GreenBoardColorImpl;
import drawing.RedBoardColorImpl;
import drawing.YellowBoardColorImpl;
import models.FileGame;
import models.Piece;

public class SaveAndRestoreGame {

	public void saveGame(FileGame fileGame) {
		File file = fileGame.getFile();

		try {
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(fileGame);
			objectOutputStream.close();
			fileOutputStream.close();
			
			System.out.println("Game has been succefully saved");

		} catch (IOException e) {
			System.err.println("Error trying to save to file: " + file.getAbsolutePath());
			System.err.print(e.getMessage());
		}

	}

	public FileGame loadGame(File file) {
		try {
			FileInputStream fileOutputStream = new FileInputStream(file);
			ObjectInputStream objectOutputStream = new ObjectInputStream(fileOutputStream);
			FileGame fileGame = (FileGame) objectOutputStream.readObject();
			fileOutputStream.close();
			objectOutputStream.close();
			return fileGame;
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Error trying to read from file: " + file.getAbsolutePath());
			return null;
		}

		

	}

	// FAKE
	private List<Piece> simulatePieces() {
		Color yellowColor = new YellowBoardColorImpl().getColor();
		Color blueColor = new BlueBoardColorImpl().getColor();
		Color redColor = new RedBoardColorImpl().getColor();
		Color greenColor = new GreenBoardColorImpl().getColor();

		Piece piece1 = new Piece(0, 72, blueColor, false);
		Piece piece2 = new Piece(1, 73, blueColor, false);
		Piece piece3 = new Piece(2, 74, blueColor, false);
		Piece piece4 = new Piece(3, 75, blueColor, false);

		Piece piece5 = new Piece(4, 76, redColor, false);
		Piece piece6 = new Piece(5, 77, redColor, false);
		Piece piece7 = new Piece(6, 78, redColor, false);
		Piece piece8 = new Piece(7, 79, redColor, false);

		Piece piece9 = new Piece(8, 80, greenColor, false);
		Piece piece10 = new Piece(9, 81, greenColor, false);
		Piece piece11 = new Piece(10, 82, greenColor, false);
		Piece piece12 = new Piece(11, 83, greenColor, false);

		Piece piece13 = new Piece(12, 84, yellowColor, false);
		Piece piece14 = new Piece(13, 85, yellowColor, false);
		Piece piece15 = new Piece(14, 86, yellowColor, false);
		Piece piece16 = new Piece(15, 87, yellowColor, false);

		// more tests
		Piece piece17 = new Piece(16, 50, yellowColor, false);
		Piece piece18 = new Piece(17, 70, yellowColor, false);
		Piece piece19 = new Piece(18, 36, yellowColor, false);
		Piece piece20 = new Piece(20, 36, greenColor, false);
		Piece piece21 = new Piece(19, 5, yellowColor, false);
		Piece piece22 = new Piece(20, 5, yellowColor, false);
		Piece piece23 = new Piece(21, 52, blueColor, false);

		return Arrays.asList(piece1, piece2, piece3, piece4, piece5, piece6, piece7, piece8, piece9, piece10, piece11,
				piece12, piece13, piece14, piece15, piece16, piece17, piece18, piece19, piece20, piece21, piece22,
				piece23);
	}
}
