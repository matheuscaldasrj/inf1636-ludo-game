package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D.Float;
import java.util.List;

import models.PointPosition;

public interface BoardColorInterface {

	public Float fillRectColor(Graphics2D graphics2d, Float rect, int index);
	public PointPosition getNextSquarePosition(int index, float rectSide, float currentX, float currentY);
	public int getBoardIndexByBlockIndex(int i);
	public int getInitialCircleBoardIndex();
	public double getAdjustInitialXCircle();
	public double getAdjustInitialYCircle();
	public Polygon getInitialTriangule(int xPosition, int yPosition, float rectSide);
	public Color getColor();

}
