package gameRules;


import java.util.List;
import java.util.Random;

import javax.sound.midi.Synthesizer;

import main.LudoGame;

import java.awt.Color;

import models.BoardSpace;
import models.InitialSquare;
import models.Piece;

// This is the class which controls all the game's rules

public class GameRules {
	
	private BoardSpace[] boardSpaces = new BoardSpace[88];	//Represents all the board's spaces
	private Piece lastMovedPiece[] = new Piece[4];			//0 = blue / 1 = red / 2 = green / 3 = yellow 
	private Piece capturedPiece; 							//Where a captured piece will be stored
	private int currentFinishingPos;						//The space the current player has to reach with each of their pieces
	private int newPosition; 								//The position the piece will move to, if it is able to
	
	private boolean posReset = false;						//The piece has passed the final index of the white spaces in the board, so we must reset the index
	private boolean canMoveAnotherPiece = false;			//The piece captured another one, so it can move 6 spaces
	private boolean movedFromInitialSquare = false;			//The piece was moved from it's initial square
	
	// Used for the moveFromInitialSquare method:
	private Piece pieceToMove;								// The piece that will be moved
	private int currentStartingPos;							// The current player's starting position
	
	// Used so the class can be a Singleton
	private static GameRules rules = null;
	
	private GameRules() {
		for(int i = 0 ; i<88 ; i++) {
			boardSpaces[i] = new BoardSpace();
		}
		for(int i=0 ; i<4 ; i++) {
			lastMovedPiece[i] = new Piece(0, 0, Color.BLUE, false);
		}
	}
	
	public static GameRules getGameRules() {
		if(rules == null) {
			rules = new GameRules();
		}
		return rules;
	}
	
	public GameRules getResetGameRules() {
		return rules = new GameRules();
		
	}
	
	// Creates all the pieces of the game, setting their colors and initial positions.
	// Stores them in the "pieces" ArrayList
	public List<Piece> createPieces(List<Piece> pieces) {		
		
		int i=72, id=0;
		
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
			boardSpaces[i].setP1(pieces.get(id));
		}
		 
		return pieces;
	}
	
	//Gets which is the minimum Id to reference the received player's color in the pieces List
	private int getPieceMinimunId(Color playerColor) {
		int i = 0;
		
		if(playerColor == Color.BLUE) {
			i=0;   
		} else if(playerColor == Color.RED) {
			i=4; 
		} else if(playerColor == Color.GREEN){
			i=8; 
		} else if(playerColor == Color.YELLOW) {
			i=12; 
		}
		
		return i;
	}
	
	// Checks all the current player's pieces to see if they can make a move. If any of them can move, returns true. Else, false.
	public boolean checkIfCanMakeAMove(Color playerColor, List<Piece> pieces, int roll) {
		int id = getPieceMinimunId(playerColor);
		int maxId = id+4;
		
		if(roll == 5 && checkCanMoveFromInitialSquare(playerColor, pieces)) {

			movedFromInitialSquare = true;
			return true;
		}
		
		for(; id<maxId ; id++) {
			if(checkIfCanMovePiece(pieces.get(id), roll)) {

				return true;
			}
		}
		return false;
	}
	
	// Sends the received piece to one of it's color's initial houses
	public void sendPieceToStart(Piece p) {
		Color pieceColor = p.getColor();
		int pieceIndex = p.getIndex();
		int i, minIndex=0, maxIndex=0;
		
		if(pieceColor == Color.BLUE) {
			i=0; minIndex = 72; maxIndex = 76;  
		} else if(pieceColor == Color.RED) {
			i=4; minIndex = 76; maxIndex = 80;
		} else if(pieceColor == Color.GREEN){
			i=8; minIndex = 80; maxIndex = 84;
		} else if(pieceColor == Color.YELLOW) {
			i=12; minIndex = 84; maxIndex = 88;
		}			
		
		// Searches the initial space for a vacant one. When it finds one, moves the desired piece from it's current space to an initial space
		for(i = minIndex; i<maxIndex ; i++) {
			
			if(boardSpaces[i].getP1() == null) {
				if(boardSpaces[pieceIndex].getP1() != null && boardSpaces[pieceIndex].getP1().getId() == p.getId()) {
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
				break;
			}	
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
		
		//return 6; 
		return rand.nextInt(6)+1;
	}
	
	// Checks if the piece clicked is of the same color as this turn player's color
	// If the piece formed a barrier, returns the top piece (p1) 
	// If there are two pieces of different colors, returns the one with the same color as the player
	public Piece checkIfCorrectColor(Color player, List<Piece> p){
		for(Piece piece : p) {
			if((piece.getColor().equals(player)))
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
		else if(index == 13) spaceColor = Color.RED;
		else if(index == 26) spaceColor = Color.GREEN;
		else if(index == 39) spaceColor = Color.YELLOW;
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
		
		Piece fillerPiece = new Piece(0, 0, Color.BLACK, false);
		Color pieceColor = piece.getColor();
		Color spaceColor;
		
		if(piece.getColor().equals(Color.BLUE)) {
			minIndex = 45; maxIndex = 50; firstTrailPos = 52;
		
		//RED Pieces
		}else if(piece.getColor().equals(Color.RED)) {
			minIndex = 6; maxIndex = 11; firstTrailPos = 57; 
			
		//GREEN Pieces
		}else if(piece.getColor().equals(Color.GREEN)) {
			minIndex = 19; maxIndex = 24; firstTrailPos = 62;
			
		//YELLOW Pieces	
		}else if(piece.getColor().equals(Color.YELLOW)) {
			minIndex = 32; maxIndex = 37; firstTrailPos = 67; 
		}
		
		i = correctPieceNewPos(i, i+1, minIndex, maxIndex, firstTrailPos);
		
		// Check if there is a barrier in the way
		for(; i<=newPos || (i > newPos && posReset) ; i=correctPieceNewPos(i, i+1, minIndex, maxIndex, firstTrailPos) ) {
			if(boardSpaces[i].getP1() != null && 
					(boardSpaces[i].getP1().getIsBarrier() && 
							boardSpaces[i].getP1().getColor() != piece.getColor()))
				return null;
	
			// In case the index finishes a lap, it certainly is less or equal to the newPos
			// In that case, we don't need to use this flag anymore
			if(i==0) 
				posReset = false;
		}		
		
		if(boardSpaces[newPos].getP1() == null) { // Space is empty
			System.out.println("Space is empty");
			return fillerPiece;
		}
		else if(boardSpaces[newPos].getP2() == null) { // Space isn't empty, but can have one more piece
			
			spaceColor = checkIfSpecialSpace(newPos);
			
			if(boardSpaces[newPos].getP1().getColor().equals(piece.getColor())) { // The pieces are the same color

				if(spaceColor != Color.WHITE) { // There can't be two pieces in a special space if they are of the same color
					System.out.println("Duas pecas de mesma cor");
					return null;
					
				}else { // It's a regular space. The pieces become a barrier
					boardSpaces[newPos].getP1().setIsBarrier(true);
					piece.setIsBarrier(true);
					System.out.println("Became a barrier!");
					return fillerPiece;
				}	
				
			}else { // The pieces are of different colors
				if(spaceColor == Color.WHITE) {	// And the new position is a normal space
					System.out.println("Piece captured!");
					return boardSpaces[newPos].getP1();
					
				}else if(spaceColor == Color.BLACK) { // It's a shelter, so they can occupy the same position
					System.out.println("The space is a shelter!");
					return fillerPiece;
				}
				else if(boardSpaces[newPos].getP1().getColor() == spaceColor) { // The position is a starting position with a piece of the same color there
						System.out.println("Starting pos with piece color");
						System.out.println("Piece color: "+boardSpaces[newPos].getP1().getColor()+ " Space color: "+spaceColor);
						return fillerPiece;
					} 
					else { // The piece that was in the space wasn't of the same color as it, so the piece was captured
						return boardSpaces[newPos].getP1();
					}
				} 
		}
		return fillerPiece;
	}
	
	// If there is a barrier, forces one of the pieces that make part of it to move
	public boolean breakBarriers(List<Piece> pieces, Color playerColor) {
		int barrierCount = 0;
		int i = 0;
		int maxIndex;
		Piece pieceToMove = null;
		
		if(playerColor == Color.BLUE) i = 0;
		else if(playerColor == Color.RED) i = 4;
		else if(playerColor == Color.GREEN) i = 8;
		else if(playerColor == Color.YELLOW) i = 12;
		
		maxIndex = i + 4;
		for( ; i < maxIndex ; i++) {
			if(pieces.get(i).getIsBarrier()) {
				barrierCount++;
				pieceToMove = pieces.get(i);
				
				//If there is more than one barrier, the player has to choose a piece to move either way, so it doesn't make a difference
				if(barrierCount == 3) {
					return false;
				}
			}
		}
		// This player has no barriers
		if(barrierCount == 0) {
			return false;
		}
		
		if(checkIfCanMovePiece(pieceToMove, 6)) {
			movePiece(pieceToMove, pieces, playerColor);
		}else
			return false;
		
		return true;
	}
	
	//Removes the "p" piece from the "index" location
	private void removeFromPosition(Piece p, int index) {
		
		if(boardSpaces[index].getP1()!=null && boardSpaces[index].getP1().getId() == p.getId()) {
			if(boardSpaces[index].getP2() == null) {

				boardSpaces[index].setP1(null);				
			}
			else {

				boardSpaces[index].setP1(boardSpaces[index].getP2());
				boardSpaces[index].setP2(null);
			}
		} 
		else {
			System.out.println("P1 is null or the piece isn't in P1");
			boardSpaces[index].setP2(null);
		}
	}
	
	// Places the moved piece in it's new position in the BoardSpaces array and removes it from it's previous position
	// We already consider that, if the piece moved piece would capture another, it already happened and the space is now empty
	// We also consider that, if the piece couldn't move for some reason, it didn't. So, we don't take into account cases that would cause that
	private void updateBoardSpaces(Piece p, int previousIndex) {
		int pieceIndex = p.getIndex();
		
		// Placing in the new position
		
		// If the position is free, just move the piece
		if(boardSpaces[pieceIndex].getP1() == null) { 
			boardSpaces[pieceIndex].setP1(p);
		
		// If the colors are the same, forms a barrier
		}else if(boardSpaces[pieceIndex].getP1().getColor().equals(p.getColor())) {
			boardSpaces[pieceIndex].setP2(p);
			boardSpaces[pieceIndex].getP1().setIsBarrier(true);
			
		// If the colors aren't the same and is in a special space (can be shelter or first space)
		}else if(! checkIfSpecialSpace(pieceIndex).equals(p.getColor())) {
			boardSpaces[pieceIndex].setP2(p);	
		}
		
		removeFromPosition(p, previousIndex);
		
		// If the pieces on previousIndex were forming a barrier, change "isBarrier" to false on the moved piece
		// There isn't a problem to ALWAYS set this as false after moving, as it will only make a difference if there was a barrier already
		p.setIsBarrier(false); 
	}
	
	// Corrects the position to which the piece will move so it follows the board's rules
	private int correctPieceNewPos(int index, int newPos, int minIndex, int maxIndex, int firstTrailPos) {
		//System.out.println("Min index: "+minIndex+" MaxIndex: "+maxIndex+" FirstTrailPos: "+firstTrailPos);
		// If the piece will enter the colored trail
		if(newPos > maxIndex && index >= minIndex && index <=maxIndex) {
			newPos += (firstTrailPos - 1 - maxIndex);
		}
					
		// The board finished a lap, so the index must reset, starting from 0
		else if(newPos > 51 && index <= 51 && index >= 46 ) { 
			newPos -= 52;
			posReset = true;
		}
		return newPos;
	}
	
	// Checks the rules to see if the selected piece can move "numSpaces" positions
	public boolean checkIfCanMovePiece(Piece piece, int numSpaces) {
		int index;							// The last position and the one the piece will move to, respectively
		int minIndex=0, maxIndex=0; 		// The max and min positions a piece can be to get in the colored trail in one move
		int firstTrailPos=0;				// The first position and the finishing position of a colored trail, respectively
		
		//If the piece has finished, it can't be moved
		if(piece.getHasFinished())
			return false;
		
		index = piece.getIndex();
		newPosition = index + numSpaces;
		if(piece.getColor().equals(Color.BLUE)) {
			minIndex = 45; maxIndex = 50; firstTrailPos = 52; currentFinishingPos = 57;
		
		//RED Pieces
		}else if(piece.getColor().equals(Color.RED)) {
			minIndex = 6; maxIndex = 11; firstTrailPos = 57; currentFinishingPos = 62; 
			
		//GREEN Pieces
		}else if(piece.getColor().equals(Color.GREEN)) {
			minIndex = 19; maxIndex = 24; firstTrailPos = 62; currentFinishingPos = 67; 
			
		//YELLOW Pieces	
		}else if(piece.getColor().equals(Color.YELLOW)) {
			minIndex = 32; maxIndex = 37; firstTrailPos = 67; currentFinishingPos = 72; 
		}
		
		newPosition = correctPieceNewPos(index, newPosition, minIndex, maxIndex, firstTrailPos);
		
		// If the piece moved further from it's final position
		if(newPosition > currentFinishingPos){
			return false;
		} 
		// If the piece can't move for some reason
		else if((this.capturedPiece = checkPath(piece, newPosition)) == null) {
			return false;			
		}
		System.out.println("Can move the piece "+piece.getId()+" to the "+newPosition+" position");
		return true;
	}
	
	// Moves the piece to the "newPosition" index or removes it from the "boardSpaces" array if it reached it's finishing position.
	public void movePiece(Piece piece, List<Piece> pieces, Color playerColor) {
		int previousIndex = piece.getIndex();	// The position the piece was in before being moved
		
		// The piece has reached it's final space. We must remove it from the boardSpaces array.
		if(newPosition == currentFinishingPos) {
			piece.setHasFinished(true);
			removeFromPosition(piece, previousIndex);
			
			
			if(checkIfPlayerWon(pieces, playerColor)) {
				System.out.println("<<<<<<<<<<<<<<<< This player has won the game!!!!! >>>>>>>>>>>>>>>>>");
				//Exibir popup
			}
			canMoveAnotherPiece = true;

			return;
		}
		// If a piece was captured
		else if(! capturedPiece.getColor().equals(Color.BLACK)){ // A piece was captured
			System.out.println("Capturou a peca!");
			sendPieceToStart(capturedPiece);
			canMoveAnotherPiece = true;
		}
			
		piece.setIndex(newPosition);
		updateBoardSpaces(piece, previousIndex);

		// Saves this player's last moved piece
		int playerId;
		if(piece.getColor().equals(Color.BLUE))
			playerId = 0;
		else if(piece.getColor().equals(Color.RED))
			playerId = 1;
		else if(piece.getColor().equals(Color.GREEN))
			playerId = 2;
		else 
			playerId = 3;
		
		lastMovedPiece[playerId] = piece;		
			
		newPosition = 0;
		capturedPiece = null;
	}
	
	// Checks if all of the player's pieces have finished (hasFinished == true)
	private boolean checkIfPlayerWon(List<Piece> pieces, Color playerColor){
		int i = 0;
		int maxIndex;
		
		if(playerColor == Color.BLUE) i = 0;
		else if(playerColor == Color.RED) i = 4;
		else if(playerColor == Color.GREEN) i = 8;
		else if(playerColor == Color.YELLOW) i = 12;
		
		maxIndex = i + 4; 
		for(; i < maxIndex ; i++) {
			System.out.println("Piece "+i);
			// If one of the pieces hasn't finished, returns false
			if(! pieces.get(i).getHasFinished()) {
				return false;
			}
		}
		return true;
	}
	
	// Checks if it is possible to move the piece out of the initial position
	public boolean checkCanMoveFromInitialSquare(Color playerColor, List<Piece> p) {
		int i, iMax;			// The min and max id of the pieces of each team
		int minIndex, maxIndex; // The indexes the pieces of each color occupy on the initial square
			
		if(playerColor.equals(Color.BLUE)) {
			i=0; iMax = 4; minIndex = 72; maxIndex = 76; currentStartingPos = 0; 
		} else if(playerColor.equals(Color.RED)) {
			i=4; iMax = 8; minIndex = 76; maxIndex = 80; currentStartingPos = 13;
		} else if(playerColor.equals(Color.GREEN)){
			i=8; iMax=12; minIndex = 80; maxIndex = 84; currentStartingPos = 26;
		} else if(playerColor.equals(Color.YELLOW)){
			i=12; iMax=16; minIndex = 84; maxIndex = 88; currentStartingPos = 39;
		} else {
			System.err.println("Error trying to moveFromInitialSquare: unknown color");
			return false;
		}
		
		for(; i<iMax ; i++) {
			pieceToMove = p.get(i);
			
			// We look for the first piece that is available in the initial square
			if(pieceToMove.getIndex() >= minIndex && pieceToMove.getIndex() < maxIndex) {
				
				if(boardSpaces[currentStartingPos].getP1() != null) {
					
					// There is a piece of the same color as the current player, so the piece can't move out
					if(boardSpaces[currentStartingPos].getP1().getColor().equals(pieceToMove.getColor())) {
						return false;
					
					// The piece is of a different color, so it gets captured
					}else {
						capturedPiece = boardSpaces[currentStartingPos].getP1();
						return true;						
					}
				// The first space is empty
				}else {	
					movedFromInitialSquare = true;
					return true;
				}	
			}
		}
		// Eclipse doesn't allow the method to end without a return out of ifs or elses
		return true;
	}

	// Gets the stored pieceToMove and moves it to the currentStartingPos
	// If there is a different colored piece on the starting space, captures it
	public void moveFromInitialSquare() {
		
		// If there is a piece in the first space, captures it. 
		//(this piece being the same color as the moving piece was already contemplated on "checkCanMoveFromInitialSquare")
		if(boardSpaces[currentStartingPos].getP1() != null) { 
			System.out.println("Capturou a peca na saida!");
			sendPieceToStart(capturedPiece);
			canMoveAnotherPiece = true;
			
			capturedPiece = null;
		}
	
		// Moving the piece to it's first position
		boardSpaces[pieceToMove.getIndex()].setP1(null);			
		pieceToMove.setIndex(currentStartingPos);
		boardSpaces[currentStartingPos].setP1(pieceToMove);	
		
		movedFromInitialSquare = false;
	}
	
	public Piece getLastMovedPiece(int playerId) {
		return lastMovedPiece[playerId];
	}
	
	public Piece[] getLastMovedPieceArray() {
		return lastMovedPiece;
	}
	
	public void setLastMovedPieceArray(Piece[] lastMovedPieceArray) {
		this.lastMovedPiece = lastMovedPieceArray;
	}
	
	public boolean getCanMoveAnotherPiece() {
		return canMoveAnotherPiece;
	}
	public void setCanMoveAnotherPiece(boolean canMoveAnotherPiece) {
		this.canMoveAnotherPiece = canMoveAnotherPiece;
	}
	
	public void setLastMovedPiece(int playerId,Piece piece) {
		lastMovedPiece[playerId] = piece;;
	}
	
	public void setBoardSpaces(BoardSpace[] boardSpaces){
		this.boardSpaces = boardSpaces;
	}
	
	public BoardSpace[] getBoardSpaces(){
		return boardSpaces;
	}
	
	public boolean getMovedFromInitialSquare() {
		return movedFromInitialSquare;
	}

}
	
