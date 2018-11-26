package gameRules;


import java.util.List;
import java.util.Random;

import main.LudoGame;

import java.awt.Color;

import models.BoardSpace;
import models.InitialSquare;
import models.Piece;

// This is the class which controls all the game's rules

public class GameRules {
	
	private BoardSpace[] boardSpaces = new BoardSpace[88];
	private Piece lastMovedPiece[] = new Piece[4]; // 0 = blue / 1 = red / 2 = green / 3 = yellow 
	
	private boolean posReset = false;
	private boolean canMoveAnotherPiece = false;
	
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
	public List<Piece> createPieces(List<Piece> pieces) {		
		
		int i=72, id=0;
		
		pieces.add(new Piece(0, 72, Color.BLUE, false));
		pieces.add(new Piece(1, 2, Color.BLUE, false));
		pieces.add(new Piece(2, 3, Color.BLUE, false));
		pieces.add(new Piece(3, 73, Color.BLUE, false));
		boardSpaces[72].p1 = pieces.get(0);
		boardSpaces[2].p1 = pieces.get(1);
		boardSpaces[3].p1 = pieces.get(2);
		boardSpaces[73].p1 = pieces.get(3);
		id=4;
		i=76;
		/*// Create BLUE
		// Create BLUE
		for(; i<76 ; i++, id++) {
			pieces.add(new Piece(id, i,Color.BLUE,false));
			boardSpaces[i].setP1(pieces.get(id));
		}
		
		// Create RED
		for(; i<80 ; i++, id++) {
			pieces.add(new Piece(id,i,Color.RED, false));
			boardSpaces[i].setP1(pieces.get(id));
		}
		
		// Create GREEN
		for(; i<84 ; i++, id++) {
			pieces.add(new Piece(id,i,Color.GREEN, false));
			boardSpaces[i].setP1(pieces.get(id));
		}
		
		// Create YELLOW
		for(; i<88 ; i++, id++) {
			pieces.add(new Piece(id,i,Color.YELLOW, false));
			boardSpaces[i].p1 = pieces.get(id);
		}*/
		
		
		pieces.add(new Piece(12, 84,Color.YELLOW, false));
		pieces.add(new Piece(13, 85,Color.YELLOW, false));
		pieces.add(new Piece(14, 86,Color.YELLOW, false));
		pieces.add(new Piece(15, 51,Color.YELLOW, false));
		boardSpaces[84].p1 = pieces.get(12);
		boardSpaces[85].p1 = pieces.get(13);
		boardSpaces[86].p1 = pieces.get(14);
		boardSpaces[51].p1 = pieces.get(15);
			boardSpaces[i].setP1(pieces.get(id));
		}
		 
		return pieces;
	}
	
	// Sends the received piece to one of it's color's initial houses
	public void sendPieceToStart(Piece p) {
		Color pieceColor = p.getColor();
		int pieceIndex = p.getIndex();
		int i, iMax=0, minIndex=0, maxIndex=0;
		
		System.out.println("Id of the piece in the "+ pieceIndex + "position: "+ boardSpaces[pieceIndex].p1.getId());
		
		if(pieceColor == Color.BLUE) {
			i=0; iMax = 4; minIndex = 72; maxIndex = 76;  
		} else if(pieceColor == Color.RED) {
			i=4; iMax = 8; minIndex = 76; maxIndex = 80; 
		} else if(pieceColor == Color.GREEN){
			i=8; iMax=12; minIndex = 80; maxIndex = 84;
		} else if(pieceColor == Color.YELLOW) {
			i=12; iMax=16; minIndex = 84; maxIndex = 88;
		}
		
		// Searches the initial space for a vacant one. When it finds one, moves the desired piece from it's current space to an initial space
		for(i = minIndex; i<maxIndex ; i++) {
			if(boardSpaces[i].getP1() == null) {
				if(boardSpaces[pieceIndex].getP1().getId() == p.getId()) {
					if(boardSpaces[pieceIndex].getP2() == null) {
						boardSpaces[pieceIndex].setP1(null);
					}else {
						boardSpaces[pieceIndex].setP1(boardSpaces[pieceIndex].getP2());  
						boardSpaces[pieceIndex].setP2(null); 
					}	
				}else 
					boardSpaces[p.getIndex()].setP2(null);
				
				p.setIndex(i);
				boardSpaces[i].setP1(p);
			}
		}
	}
	
	// Places the moved piece in it's new position in the BoardSpaces array and removes it from it's previous position
	private void updateBoardSpaces(Piece p, int roll) {
		int pieceIndex = p.getIndex();
		int previousIndex = pieceIndex - roll;
		
		// Placing in the new position
		if(boardSpaces[pieceIndex].getP1() == null) {
			boardSpaces[pieceIndex].setP1(p);
			
		}else if(boardSpaces[pieceIndex].getP1().getColor() == p.getColor()) {
			boardSpaces[pieceIndex].setP2(p);
			boardSpaces[pieceIndex].getP1().setIsBarrier(true);
			
		}else {
			sendPieceToStart(boardSpaces[pieceIndex].getP1());
			boardSpaces[pieceIndex].setP1(p);
		}
		
		if(previousIndex < 0) {
			previousIndex += 52;
		}
		
		// Removing from the previous position
		if(boardSpaces[previousIndex].getP1().getId() == p.getId()) {
			if(boardSpaces[previousIndex].getP2() == null) {
				boardSpaces[previousIndex].setP1(null);				
			}else {
				boardSpaces[previousIndex].setP1(boardSpaces[pieceIndex - roll].getP2());
				boardSpaces[previousIndex].setP2(null);;
			}
		} else {
			boardSpaces[previousIndex].setP2(null);
		}
		// If the pieces on previousIndex were forming a barrier, change "isBarrier" to false on the moved piece
		// There isn't a problem to ALWAYS set this as false after moving, as it will only make a difference if there was a barrier already
		p.setIsBarrier(false); 
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
		
		return 6; //rand.nextInt(6)+1;
	}
	
	// Checks if the piece clicked is of the same color as this turn player's color
	// If the piece formed a barrier, returns the top piece (p1) 
	public Piece checkIfCorrectColor(Color player, List<Piece> p){
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
	// If the move forms a barrier, sets "isBarrier" as true on the top piece (p1)
	private Piece checkPath(Piece piece, int newPos){
		int i = piece.getIndex();
		int minIndex=0,maxIndex=0,firstTrailPos=0;
		
		int shelterPosition = 13; 
		Piece fillerPiece = new Piece(0, 0, Color.BLACK, false);
		Color pieceColor = piece.getColor();
		Color spaceColor;
		
		if(piece.getColor() == Color.BLUE) {
			minIndex = 45; maxIndex = 50; firstTrailPos = 52;
		
		//RED Pieces
		}else if(piece.getColor() == Color.RED) {
			minIndex = 6; maxIndex = 11; firstTrailPos = 57; 
			
		//GREEN Pieces
		}else if(piece.getColor() == Color.GREEN) {
			minIndex = 19; maxIndex = 24; firstTrailPos = 62;
			
		//YELLOW Pieces	
		}else if(piece.getColor() == Color.YELLOW) {
			minIndex = 32; maxIndex = 37; firstTrailPos = 67; 
		}
		
		i = correctPieceNewPos(i, i+1, minIndex, maxIndex, firstTrailPos);
		
		// Check if there is a barrier in the way
		for(; i<=newPos || (i > newPos && posReset) ; i=correctPieceNewPos(i, i+1, minIndex, maxIndex, firstTrailPos) ) {
			if(boardSpaces[i].p1 != null && (boardSpaces[i].p1.getIsBarrier() && boardSpaces[i].p1.getColor() != piece.getColor()))
				return null;
	
			// In case the index finishes a lap, it certainly is less or equal to the newPos
			// In that case, we don't need to use this flag anymore
			if(i==0) 
				posReset = false;
		}
		
		if(piece.getColor() == Color.BLUE) {
			minIndex = 45; maxIndex = 50; firstTrailPos = 52;
		
		//RED Pieces
		}else if(piece.getColor() == Color.RED) {
			minIndex = 6; maxIndex = 11; firstTrailPos = 57; 
			
		//GREEN Pieces
		}else if(piece.getColor() == Color.GREEN) {
			minIndex = 19; maxIndex = 24; firstTrailPos = 62;
			
		//YELLOW Pieces	
		}else if(piece.getColor() == Color.YELLOW) {
			minIndex = 32; maxIndex = 37; firstTrailPos = 67; 
		}
		
		i = correctPieceNewPos(i, i+1, minIndex, maxIndex, firstTrailPos);
		
		// Check if there is a barrier in the way
		for(; i<=newPos || (i > newPos && posReset) ; i=correctPieceNewPos(i, i+1, minIndex, maxIndex, firstTrailPos) ) {
			if(boardSpaces[i].p1 != null && (boardSpaces[i].p1.getIsBarrier() && boardSpaces[i].p1.getColor() != piece.getColor()))
				return null;
	
			// In case the index finishes a lap, it certainly is less or equal to the newPos
			// In that case, we don't need to use this flag anymore
			if(i==0) 
				posReset = false;
		}
		
		if(boardSpaces[newPos].getP1() == null) { // Space is empty
			return fillerPiece;
			
		}else if(boardSpaces[newPos].getP2() == null) { // Space isn't empty, but can have one more piece
			
			spaceColor = checkIfSpecialSpace(newPos);
			
			if(boardSpaces[newPos].getP1().getColor() == piece.getColor()) { // The pieces are the same color
				System.out.println("Cor da peça na pos final: " + boardSpaces[newPos].getP1().getColor());
				if(spaceColor != Color.WHITE) { // There can't be two pieces in a special space if they are of the same color
					System.out.println(spaceColor);
					return null;
				}else { // It's a regular space. The pieces become a barrier
					boardSpaces[newPos].getP1().setIsBarrier(true);
					piece.setIsBarrier(true);
					return fillerPiece;
				}
				
			} else { // The pieces are of different colors
				if(spaceColor != Color.WHITE) {	// And the new position is a special space
					if(boardSpaces[newPos].getP1().getColor() == spaceColor || spaceColor == Color.BLACK) { // Two different colored pieces can occupy this space
						return fillerPiece;
					} else { // The piece that was in the space wasn't of the same color as it, so the piece was captured
						return boardSpaces[newPos].getP1();
					}
				} else { // It's a regular space, so the piece was captured
					return boardSpaces[newPos].getP1();
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
			int tempNewPos = 0; // <============================== corrigir
//			int tempNewPos = correctPieceNewPos(i, tempNewPos, minIndex, maxIndex, firstTrailPos); 
			System.out.println("É uma barreira: " + boardSpaces[i].getP1().getIsBarrier());
			if(boardSpaces[i].getP1().getIsBarrier())
				return null;
		}
		return fillerPiece;
	}
	
	// Corrects the position to which the piece will move so it follows the board's rules
	private int correctPieceNewPos(int index, int newPos, int minIndex, int maxIndex, int firstTrailPos) {
		// If the piece will enter the colored trail
		if(newPos > maxIndex && index >= minIndex && index <=maxIndex) 
			newPos += (firstTrailPos - 1 - maxIndex);
					
		// The board finished a lap, so the index must reset, starting from 0
		else if(newPos > 51 && index <= 51 && index >= 46 ) { 
			newPos -= 52;
			posReset = true;
		}
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
		
		canMoveAnotherPiece = false;	// Resets this flag
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
		System.out.println("Pre correção " + newPos);
		// The board finished a lap, so the index must reset, starting from 0
		if(newPos > 51 && index <= 51 && index >= 46 ) { 
			newPos -= 52;
			System.out.println("Pos correção " + newPos);
			posReset = true;
		}
		
		capturedPiece = checkPath(piece, newPos);
		
		if(newPos == finishingPos) {	// The piece has reached it's final space
			piece.setHasFinished(true);
			
		} else if(capturedPiece == null) { // The piece can't move
			System.out.println("A peça não pode se mover");
			return false;			
		
		}else if(capturedPiece.getColor() != Color.BLACK){ // A piece was captured
			System.out.println("Capturou a peça!");
			sendPieceToStart(capturedPiece);
			canMoveAnotherPiece = true;
		}
			
		piece.setIndex(newPos);
		System.out.println("Atualizou a posição");
		updateBoardSpaces(piece, numSpaces);
		System.out.println("Atualizou o vetor de espaços");
		
		// Saves this player's last moved piece
		for(int i = 0 ; i < 4 ; i++) {
			if(piece.getColor() == lastMovedPiece[i].getColor()) {
				lastMovedPiece[i] = piece;
			}
		}
		
		return true;
	}
	
	public boolean moveFromInitialSquare(/*Color squareColor,*/ /*int roll,*/ Color playerColor, List<Piece> p) {
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
						if(boardSpaces[startingPos].getP1() != null) {
							System.out.println("Posição não é null");
							if(boardSpaces[startingPos].getP1().getColor() == piece.getColor()) {
								return false;
								
							}else {
								piece.setIndex(startingPos);
								boardSpaces[startingPos].setP2(piece);
								return true;
							}
						}else {
							piece.setIndex(startingPos);
							boardSpaces[startingPos].setP1(piece);
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
	public boolean getCanMoveAnotherPiece() {
		return canMoveAnotherPiece;
	
	public void setLastMovedPiece(int playerId,Piece piece) {
		lastMovedPiece[playerId] = piece;;
	}
	
	public void setBoardSpaces(BoardSpace[] boardSpaces){
		this.boardSpaces = boardSpaces;
	}
	
	public BoardSpace[] getBoardSpaces(){
		return boardSpaces;
	}
}
	
