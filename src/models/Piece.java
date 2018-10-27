package models;

import java.awt.Color;

public class Piece {
	
	private int index; 				// The piece's position
	private Color color; 			// The piece's color
	private boolean isBarrier;		// States if the piece is a barrier or not
	private boolean hasFinished;	// States if the piece has reached the final position
	
	
	public Piece(Color c) {
		index = -1;
		color = c;
		hasFinished = false;
		isBarrier = false;
	}
	
	// Index
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	// Color
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	// isBarrier
	public boolean getIsBarrier() {
		return isBarrier;
	}
	public void setIsBarrier(boolean isBarrier) {
		this.isBarrier = isBarrier;
	}
	
	// hasFinished
	public boolean getHasFinished() {
		return hasFinished;
	}
	public void setHasFinished(boolean hasFinished) {
		this.hasFinished = hasFinished;
	}

	
	
	

}
