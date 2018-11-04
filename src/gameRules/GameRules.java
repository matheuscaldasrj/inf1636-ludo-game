package gameRules;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

import main.LudoGame;

import java.awt.Color;

import models.Piece;

// This is the class which controls all the game's rules

public class GameRules {
	
	public List<Piece> pieces;
	
	public GameRules() {
		pieces = new ArrayList<Piece>();
	}
	
	// Creates all the pieces of the game, setting their colors and initial positions.
	// Stores them in the "pieces" ArrayList
	public void createPieces() {
		int i=0;
		
		// Create BLUE
		for(; i<4 ; i++) {
			pieces.add(new Piece(i, 4,Color.BLUE,false));
		}
		
		// Create RED
		for(; i<8 ; i++) {
			pieces.add(new Piece(i,5,Color.RED, false));
		}
		
		// Create GREEN
		for(; i<12 ; i++) {
			pieces.add(new Piece(i,6,Color.GREEN, false));
		}
		
		// Create YELLOW
		for(; i<16 ; i++) {
			pieces.add(new Piece(i,7,Color.YELLOW, false));
		}
	}
	
	public Color nextTurn(Color playerTurn) {
		if(playerTurn == Color.BLUE) playerTurn = Color.RED;
		
		else if(playerTurn == Color.RED) playerTurn = Color.GREEN;
		
		else if(playerTurn == Color.GREEN) playerTurn = Color.YELLOW;
		
		else playerTurn = Color.BLUE;
		
		return playerTurn;
	}
	
	public int rollDie() {
		Random rand = new Random();
		
		return rand.nextInt(6)+1;
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
	public void movePiece(Piece p, int numSpaces) {
		int index, newPos;
		
		index = p.getIndex();
		newPos = index + numSpaces;
		
		//BLUE Pieces
		if(p.getColor() == Color.BLUE) {
			newPos = correctPieceNewPos(index, newPos, 45, 50, 52);
			
			if(newPos == 57) 
				p.setHasFinished(true);
		
		// The board finished a lap, so the index must reset, starting from 0
		// It doesn't apply if it's a blue piece.
		// That also implies the other pieces aren't about to enter their colored trail.
		}else if(newPos > 51 && index <= 51 && index >= 46 ) { 
			newPos -= 52;
			
		//RED Pieces
		}else if(p.getColor() == Color.RED) {
			newPos = correctPieceNewPos(index, newPos, 6, 11, 57);
			
			if(newPos == 62) 
				p.setHasFinished(true);
			
		//GREEN Pieces
		}else if(p.getColor() == Color.GREEN) {
			newPos = correctPieceNewPos(index, newPos, 19, 24, 62);
			
			if(newPos == 67) 
				p.setHasFinished(true);
			
		//YELLOW Pieces	
		}else if(p.getColor() == Color.YELLOW) {
			newPos = correctPieceNewPos(index, newPos, 32, 37, 67);
			
			if(newPos == 72) 
				p.setHasFinished(true);
		}		
		
		p.setIndex(newPos);
		
		
	}
	
}
