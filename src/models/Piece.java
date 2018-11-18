package models;

import java.awt.Color;

public class Piece {
	
	private int id;					// Identifies the piece in the pieces ArrayList
	private int index; 				// The piece's position in the board
	private Color color; 			// The piece's color
	private boolean isBarrier;		// States if the piece is a barrier or not
	private boolean hasFinished;	// States if the piece has reached the final position
	
	public Piece(int id, int index, Color color, boolean isBarrier) {
		this.id = id;
		this.index = index;
		this.color = color;
		this.isBarrier = isBarrier;

		hasFinished = false;
	}
	
	
	@Override
	public String toString() {
		String ret;
		ret = "id: '" + this.id + "', Index: '" + this.index;
		ret += "', IsBarrier: '" + this.isBarrier + "', hasFinished: '" + this.hasFinished + "'" ;
		ret += "', Color: '" + this.color + "'";
		return ret;
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
	
	// id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	
	

}
