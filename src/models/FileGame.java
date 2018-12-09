package models;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import drawing.BlueBoardColorImpl;
import drawing.GreenBoardColorImpl;
import drawing.RedBoardColorImpl;
import drawing.YellowBoardColorImpl;

public class FileGame implements Serializable {
	
	private File file;
	private List<Piece> pieces;
	private Color playerTurn;
	private BoardSpace[] boardSpaces;
	private Piece[] getLastMovedPieceArray;
	private int turnsToFinishFirstRound;
	private Piece capturedPiece;
	private boolean canMoveAnotherPiece;	
	private boolean capturedPieceInStartPos;
	
	private int numPieces = 0;
	private int numBoardSpace = 0;
	
	public FileGame(File file,
					List<Piece> pieces,
					Color playerTurn,
					Piece[] getLastMovedPieceArray,
					BoardSpace[] boardSpaces,
					int turnsToFinishFirstRound,
					Piece capturedPiece,
					boolean canMoveAnotherPiece,
					boolean capturedPieceInStartPos) {
		this.file = file;
		this.playerTurn = playerTurn;
		this.getLastMovedPieceArray = getLastMovedPieceArray;
		this.turnsToFinishFirstRound = turnsToFinishFirstRound;
		this.capturedPiece = capturedPiece;
		this.canMoveAnotherPiece = canMoveAnotherPiece;
		this.capturedPieceInStartPos = capturedPieceInStartPos;
		//method handles size
		setBoardSpaces(boardSpaces);
		setPieces(pieces);
	}
	
	public FileGame() {
		
	}
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public List<Piece> getPieces() {
		return pieces;
	}
	public void setPieces(List<Piece> pieces) {
		
		this.pieces = pieces;
		this.numPieces = this.pieces.size();
//		this.pieces = simulatePieces();
	}
	public Color getPlayerTurn() {
		return playerTurn;
	}
	public void setPlayerTurn(Color playerTurn) {
		this.playerTurn = playerTurn;
	}
	
	
	public int getTurnsToFinishFirstRound() {
		return turnsToFinishFirstRound;
	}
	public void setTurnsToFinishFirstRound(int turnsToFinish) {
		this.turnsToFinishFirstRound = turnsToFinish;
	}
	
	public Piece getCapturedPiece() {
		return capturedPiece;
	}
	public void setCapturedPiece(Piece capturedPiece) {
		this.capturedPiece = capturedPiece;
	}
	
	public Boolean getCapturedPieceInStartPos() {
		return capturedPieceInStartPos;
	}
	public void setCapturedPiece(boolean capturedPieceInStartPos) {
		this.capturedPieceInStartPos = capturedPieceInStartPos;
	}
	
	
	public boolean getCanMoveAnotherPiece() {
		return canMoveAnotherPiece;
	}
	public void setCanMoveAnotherPiece(boolean canMoveAnotherPiece) {
		this.canMoveAnotherPiece = canMoveAnotherPiece;
	}
	
	public Piece[] getLastMovedPieceArray() {
		return getLastMovedPieceArray;
	}
	public void setLastMovedPieceArray(Piece[] lastMovedPiece) {
		this.getLastMovedPieceArray = lastMovedPiece;
	}
	
	public BoardSpace[] getBoardSpaces() {
		return boardSpaces;
	}
	public void setBoardSpaces(BoardSpace[] boardSpaces) {
		this.boardSpaces = boardSpaces;
		this.numBoardSpace = boardSpaces.length;
	}
	private List<Piece> simulatePieces() {
		Color yellowColor = new YellowBoardColorImpl().getColor();
		Color blueColor = new BlueBoardColorImpl().getColor();
		Color redColor = new RedBoardColorImpl().getColor();
		Color greenColor = new GreenBoardColorImpl().getColor();
		
		Piece piece1 = new Piece(0, 72, redColor, false);
		Piece piece2 = new Piece(1, 73, redColor, false);
		Piece piece3 = new Piece(2, 74, redColor, false);
		Piece piece4 = new Piece(3, 75, redColor, false);
		
		Piece piece5 = new Piece(4, 76, redColor, false);
		Piece piece6 = new Piece(5, 77, redColor, false);
		Piece piece7 = new Piece(6, 78, redColor, false);
		Piece piece8 = new Piece(7, 79, redColor, false);
		
		Piece piece9 = new Piece(8, 80, blueColor, false);
		Piece piece10 = new Piece(9, 81, greenColor, false);
		Piece piece11 = new Piece(10, 82, blueColor, false);
		Piece piece12 = new Piece(11, 83, greenColor, false);
		
		Piece piece13 = new Piece(12, 84, blueColor, false);
		Piece piece14 = new Piece(13, 85, redColor, false);
		Piece piece15 = new Piece(14, 86, yellowColor, false);
		Piece piece16 = new Piece(15, 87, redColor, false);
		
		
		//more tests
		Piece piece17 = new Piece(16, 50, blueColor, false);
		Piece piece18 = new Piece(17, 70, yellowColor, false);
		Piece piece19 = new Piece(18, 36, redColor, false);
		Piece piece20 = new Piece(20, 36, blueColor, false);
		Piece piece21 = new Piece(19, 5, redColor, false);
		Piece piece22 = new Piece(20, 5, blueColor, false);
		Piece piece23 = new Piece(21, 52, blueColor, false);
		
		return Arrays.asList(piece1, piece2, piece3, piece4, piece5, piece6,
				piece7 ,piece8, piece9, piece10, piece11, piece12, piece13, piece14,
				piece15, piece16, piece17, piece18, piece19, piece20, piece21, piece22, piece23);
	}
	
}
