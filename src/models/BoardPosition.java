package models;

public class BoardPosition {
	
	private float x;
	private float y;
	private int index;
	
	public BoardPosition(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	
	public float getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	

}
