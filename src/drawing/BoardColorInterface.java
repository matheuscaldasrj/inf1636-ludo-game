package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Float;

import models.PointPosition;

public interface BoardColorInterface {
	
	public Float fillColor(Graphics2D graphics2d, Float rect, int index);
	PointPosition getNextSquarePosition(int index, float rectSide, float currentX, float currentY);
	
}
