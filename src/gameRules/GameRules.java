package gameRules;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import main.LudoGame;

import java.awt.Color;

import models.InitialSquare;
import models.Piece;

import gameRules.BoardSpace;

// This is the class which controls all the game's rules

public class GameRules {
	
	private BoardSpace[] boardSpaces = new BoardSpace[88];
	private Piece lastMovedPiece[] = new Piece[4]; // 0 = blue / 1 = red / 2 = green / 3 = yellow 
	
	public GameRules() {
		for(int i = 0 ; i<88 ; i++) {
			boardSpaces[i] = new BoardSpace();
		}
		for(int i=0 ; i<4 ; i++) {
			lastMovedPiece[i] = new Piece(0, 0, Color.BLUE, false);
		}
	}
	
	// Creates all the pieces of the game, setting their colors and initial positions.
	// Stores them in the "pieces" ArrayList
	public ArrayList<Piece> createPieces(ArrayList<Piece> pieces) {		
		
		int i=72, id=0;
		
		// Create BLUE
		for(; i<76 ; i++, id++) {
			pieces.add(new Piece(id, i,Color.BLUE,false));
			boardSpaces[i].p1 = pieces.get(id);
		}
		
		// Create RED
		for(; i<80 ; i++, id++) {
			pieces.add(new Piece(id,i,Color.RED, false));
			boardSpaces[i].p1 = pieces.get(id);
		}
		
		// Create GREEN
		for(; i<84 ; i++, id++) {
			pieces.add(new Piece(id,i,Color.GREEN, false));
			boardSpaces[i].p1 = pieces.get(id);
		}
		
		// Create YELLOW
		for(; i<88 ; i++, id++) {
			pieces.add(new Piece(id,i,Color.YELLOW, false));
			boardSpaces[i].p1 = pieces.get(id);
		}
		 
		return pieces;
	}
	
	// Sends the received piece to one of it's color's initial houses
	public void sendPieceToStart(Piece p) {
		Color pieceColor = p.getColor();
		int pieceIndex = p.getIndex();
		int i, iMax, minIndex, maxIndex;
		
		if(pieceColor == Color.BLUE) {
			i=0; iMax = 4; minIndex = 72; maxIndex = 76;  
		} else if(pieceColor == Color.RED) {
			i=4; iMax = 8; minIndex = 76; maxIndex = 80; 
		} else if(pieceColor == Color.GREEN){
			i=8; iMax=12; minIndex = 80; maxIndex = 84;
		} else {
			i=12; iMax=16; minIndex = 84; maxIndex = 88;
		}
		
		for(i = minIndex; i<maxIndex ; i++) {
			if(boardSpaces[i].p1 == null) {
				if(boardSpaces[pieceIndex].p1.getId() == p.getId()) {
					if(boardSpaces[pieceIndex].p2 == null) {
						boardSpaces[pieceIndex].p1 = null; 
					}else {
						boardSpaces[pieceIndex].p1 = boardSpaces[pieceIndex].p2;  
						boardSpaces[pieceIndex].p2 = null; 
					}	
				}else 
					boardSpaces[p.getIndex()].p2 = null;
				
				p.setIndex(i);
				boardSpaces[i].p1 = p;
			}
		}
	}
	
	// Places the moved piece in it's new position in the BoardSpaces array and removes it from it's previous position
	private void updateBoardSpaces(Piece p, int roll) {
		int pieceIndex = p.getIndex();
		int previousIndex = pieceIndex - roll;
		
		// Placing in the new position
		if(boardSpaces[pieceIndex].p1 == null) {
			boardSpaces[pieceIndex].p1 = p;
			
		}else if(boardSpaces[pieceIndex].p1.getColor() == p.getColor()) {
			boardSpaces[pieceIndex].p2 = p;
			boardSpaces[pieceIndex].p1.setIsBarrier(true);
			
		}else {
			sendPieceToStart(boardSpaces[pieceIndex].p1);
			boardSpaces[pieceIndex].p1 = p;
		}
		
		if(previousIndex < 0) {
			previousIndex += 52;
		}
		
		// Removing from the previous position
		if(boardSpaces[previousIndex].p1.getId() == p.getId()) {
			if(boardSpaces[previousIndex].p2 == null) {
				boardSpaces[previousIndex].p1 = null;				
			}else {
				boardSpaces[previousIndex].p1 = boardSpaces[pieceIndex - roll].p2;
				boardSpaces[previousIndex].p2 = null;
			}
		} else {
			boardSpaces[previousIndex].p2 = null;
		}
	}
	
	public Color getNextTurnColor(Color playerTurn) {
		if(playerTurn == Color.BLUE) playerTurn = Color.RED;
		
		else if(playerTurn == Color.RED) playerTurn = Color.GREEN;
		
		else if(playerTurn == Color.GREEN) playerTurn = Color.YELLOW;
		
		else playerTurn = Color.BLUE;
		
		return playerTurn;
	}
	
	public int rollDie() {
		Random rand = new Random();
		
		return 5; //rand.nextInt(6)+1;
	}
	
	// Checks if the piece clicked is of the same color as this turn player's color
	public Piece checkIfCorrectColor(Color player, ArrayList<Piece> p){
		for(Piece piece : p) {
			if(piece.getColor() == player)
				return piece;
		}
		return null;	
	}
	
	// Returns the color of the space:
	// If it's a first space, returns the color of the player
	// If it's a shelter, returns black
	// If it's a regular space, return white
	private Color checkIfSpecialSpace(int index) {
		Color spaceColor = Color.WHITE; // This represents the normal spaces
		
		if (index == 0)		spaceColor = Color.BLUE;
		else if(index == 13) 	spaceColor = Color.RED;
		else if(index == 26) spaceColor = Color.GREEN;
		else if(index == 39)	spaceColor = Color.YELLOW;
		else if(index == 9 || index == 22 || index == 35 || index == 48) // This space is a shelter
			spaceColor = Color.BLACK;
		return spaceColor;
	}
	
	// Checks the next spaces to see if there is a barrier or a piece of another player. 
	// If the player captured a piece, returns a reference to it
	// returns null if there is another player's barrier on the way or if the ending space is unavailable
	// returns a "filler piece" if the piece can move but no piece was captured
	// If the move forms a barrier, sets "isBarrier" as true
	private Piece checkPath(Piece piece, int newPos){
		int i = piece.getIndex()+1;
		int minIndex=0,maxIndex=0,firstTrailPos=0, finishingPos=0;
		
		int shelterPosition = 13; 
		Piece fillerPiece = new Piece(0, 0, Color.BLACK, false);
		Color pieceColor = piece.getColor();
		Color spaceColor;
		
		if(boardSpaces[newPos].p1 == null) { // Space is empty
			return fillerPiece;
			
		}else if(boardSpaces[newPos].p2 == null) { // Space isn't empty, but can have one more piece
			
			spaceColor = checkIfSpecialSpace(newPos);
			
			if(boardSpaces[newPos].p1.getColor() == piece.getColor()) { // The pieces are the same color
				System.out.println("Cor da pe�a na pos final: " + boardSpaces[newPos].p1.getColor());
				if(spaceColor != Color.WHITE) { // There can't be two pieces in a special space if they are of the same color
					System.out.println(spaceColor);
					return null;
				}else { // It's a regular space. The pieces become a barrier
					boardSpaces[newPos].p1.setIsBarrier(true);
					piece.setIsBarrier(true);
					return fillerPiece;
				}
				
			} else { // The pieces are of different colors
				if(spaceColor != Color.WHITE) {	// And the new position is a special space
					if(boardSpaces[newPos].p1.getColor() == spaceColor || spaceColor == Color.BLACK) { // Two different colored pieces can occupy this space
						return fillerPiece;
					} else { // The piece that was in the space wasn't of the same color as it, so the piece was captured
						return boardSpaces[newPos].p1;
					}
				} else { // It's a regular space, so the piece was captured
					return boardSpaces[newPos].p1;
				}
			}
		}
		
		if(piece.getColor() == Color.BLUE) {
			minIndex = 45; maxIndex = 50; firstTrailPos = 52; finishingPos = 57;
		
		//RED Pieces
		}else if(piece.getColor() == Color.RED) {
			minIndex = 6; maxIndex = 11; firstTrailPos = 57; finishingPos = 62;
			
		//GREEN Pieces
		}else if(piece.getColor() == Color.GREEN) {
			minIndex = 19; maxIndex = 24; firstTrailPos = 62; finishingPos = 67;
			
		//YELLOW Pieces	
		}else if(piece.getColor() == Color.YELLOW) {
			minIndex = 32; maxIndex = 37; firstTrailPos = 67; finishingPos = 72;
		}
		
		// Check if there is a barrier in the way
		for(; i<=newPos ; i++) {
			int tempNewPos = correctPieceNewPos(i, tempNewPos, minIndex, maxIndex, firstTrailPos); // <============================== corrigir
			System.out.println("� uma barreira: " + boardSpaces[i].p1.getIsBarrier());
			if(boardSpaces[i].p1.getIsBarrier())
				return null;
		}
		return fillerPiece;
	}
	
	// Corrects the position to which the piece will move so it follows the board's rules
	private int correctPieceNewPos(int index, int newPos, int minIndex, int maxIndex, int firstTrailPos) {
		// If the piece will enter the colored trail
		if(newPos > maxIndex && index >= minIndex && index <=maxIndex) 
			newPos += (firstTrailPos - 1 - maxIndex);
					
		// If the piece is in the colored trail
		else if(newPos >= firstTrailPos) {
						
			// The final move must land perfectly on the last space. If it doesn't, the piece doesn't move
			if(newPos > firstTrailPos + 5)
				return index;
		}
		return newPos;
	}
	
	// Moves the piece according to the rules and the number the player got with the die
	public boolean movePiece(Piece piece, int numSpaces) {
		int index, newPos;						// The last position and the one the piece will move to, respectively
		int minIndex=0, maxIndex=0; 			// The max and min positions a piece can be to get in the colored trail in one move
		int firstTrailPos=0, finishingPos=0;	// The first position and the finishing position of a colored trail, respectively
		int firstPos = 0;						// The first position of the board, relative to the color of the piece
		boolean posReset = false;
		Piece capturedPiece;
		
		index = piece.getIndex();
		newPos = index + numSpaces;
		
		//BLUE Pieces
		if(piece.getColor() == Color.BLUE) {
			minIndex = 45; maxIndex = 50; firstTrailPos = 52; finishingPos = 57; firstPos = 0;
		
		//RED Pieces
		}else if(piece.getColor() == Color.RED) {
			minIndex = 6; maxIndex = 11; firstTrailPos = 57; finishingPos = 62; firstPos = 13;
			
		//GREEN Pieces
		}else if(piece.getColor() == Color.GREEN) {
			minIndex = 19; maxIndex = 24; firstTrailPos = 62; finishingPos = 67; firstPos = 26;
			
		//YELLOW Pieces	
		}else if(piece.getColor() == Color.YELLOW) {
			minIndex = 32; maxIndex = 37; firstTrailPos = 67; finishingPos = 72; firstPos = 39;
			
		}
		
		newPos = correctPieceNewPos(index, newPos, minIndex, maxIndex, firstTrailPos);
		System.out.println("Pre corre��o " + newPos);
		// The board finished a lap, so the index must reset, starting from 0
		if(newPos > 51 && index <= 51 && index >= 46 ) { 
			newPos -= 52;
			System.out.println("Pos corre��o " + newPos);
			posReset = true;
		}
		
		capturedPiece = checkPath(piece, newPos);
		
		if(newPos == finishingPos) {	// The piece has reached it's final space
			piece.setHasFinished(true);
			
		} else if(capturedPiece == null) { // The piece can't move
			System.out.println("A pe�a n�o pode se mover");
			return false;			
		
		}else if(capturedPiece.getColor() != Color.BLACK){ // A piece was captured
			System.out.println("Capturou a pe�a!");
			sendPieceToStart(capturedPiece);
		}
			
		piece.setIndex(newPos);
		System.out.println("Atualizou a posi��o");
		updateBoardSpaces(piece, numSpaces);
		System.out.println("Atualizou o vetor de espa�os");
		
		// Saves this player's last moved piece
		for(int i = 0 ; i < 4 ; i++) {
			if(piece.getColor() == lastMovedPiece[i].getColor()) {
				lastMovedPiece[i] = piece;
			}
		}
		
		return true;
	}
	
	public boolean moveFromInitialSquare(/*Color squareColor,*/ /*int roll,*/ Color playerColor, ArrayList<Piece> p) {
		int i, iMax;			// The min and max id of the pieces of each team
		int minIndex, maxIndex; // The indexes the pieces of each color occupy on the initial square
		int startingPos;		// The index of the initial space for each color
		
	//	if(squareColor == playerColor) {
			//if(roll == 5) {	
				if(playerColor == Color.BLUE) {
					i=0; iMax = 4; minIndex = 72; maxIndex = 76; startingPos = 0; 
				} else if(playerColor == Color.RED) {
					i=4; iMax = 8; minIndex = 76; maxIndex = 80; startingPos = 13;
				} else if(playerColor == Color.GREEN){
					i=8; iMax=12; minIndex = 80; maxIndex = 84; startingPos = 26;
				} else {
					i=12; iMax=16; minIndex = 84; maxIndex = 88; startingPos = 39;
				}
				
				for(; i<iMax ; i++) {
					Piece piece = p.get(i);
					
					if(piece.getIndex() >= minIndex && piece.getIndex() < maxIndex) {
						if(boardSpaces[startingPos].p1 != null) {
							System.out.println("Posi��o n�o � null");
							if(boardSpaces[startingPos].p1.getColor() == piece.getColor()) {
								return false;
								
							}else {
								piece.setIndex(startingPos);
								boardSpaces[startingPos].p2 = piece;
								return true;
							}
						}else {
							piece.setIndex(startingPos);
							boardSpaces[startingPos].p1 = piece;
							return true;
						}
						
					}
				}
			//}
		//}
		return false;
	}
	public Piece getLastMovedPiece(int playerId) {
		return lastMovedPiece[playerId];
	}
}
	
