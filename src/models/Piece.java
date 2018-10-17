package models;

import java.awt.Color;

public class Piece {
	
	private int index;
	private Color color;
	private boolean isBarrier;
	
	
	public Piece(int index, Color color, boolean isBarrier) {
		this.index = index;
		this.color = color;
		this.isBarrier = isBarrier;
	}
	
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	public boolean getIsBarrier() {
		return isBarrier;
	}
	public void setIsBarrier(boolean isBarrier) {
		this.isBarrier = isBarrier;
	}

	
	
	

}
