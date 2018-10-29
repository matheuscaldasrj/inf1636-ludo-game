package gameRules;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.awt.Color;

import models.Piece;

public class GameRules {
	
	public List<Piece> pieces;
	private Color playerTurn;
	
	public GameRules() {
		pieces = new ArrayList<Piece>();
		playerTurn = Color.BLUE;
	}
	
	// Creates all the pieces of the game, setting their colors and initial positions.
	// Stores them in the "pieces" ArrayList
	public void CreatePieces() {
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
	
	public Color NextTurn() {
		if(playerTurn == Color.BLUE) playerTurn = Color.RED;
		
		else if(playerTurn == Color.RED) playerTurn = Color.GREEN;
		
		else if(playerTurn == Color.GREEN) playerTurn = Color.YELLOW;
		
		else playerTurn = Color.BLUE;
		
		return playerTurn;
	}
	
	public int RollDice() {
		Random rand = new Random();
		
		return rand.nextInt(6)+1;
	}
	
	public void MovePiece(Piece p, int numSpaces) {
		int index, newPos;
		
		index = p.getIndex();
		newPos = index + numSpaces;
		
		if(p.getColor() == Color.BLUE && newPos > 50) {
			newPos += 1;
		}
		p.setIndex(index + numSpaces);
	}
	
}
