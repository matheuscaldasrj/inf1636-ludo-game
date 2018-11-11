package models;

public class BoardPosition {
	
	private double x;
	private double y;
	private int index;
	
	public BoardPosition(double d, double e) {
		this.x = d;
		this.y = e;
	}
	
	
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
	public double getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	

}
