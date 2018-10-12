package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import models.PointPosition;

public class GreenBoardColorImpl extends AbstractBoardColor implements BoardColorInterface {
	
	static Color color = Color.GREEN;
	
	public GreenBoardColorImpl() {
		super(color);
	}

	
	@Override
	public PointPosition getNextSquarePosition(int index, float rectSide, float currentX, float currentY) {
		float rectX;
		float rectY;		

		if( index != 0 && index % 3 == 0) {
			//GO TO NEXT LEVEL
			rectX = currentX + 2*rectSide;
			rectY = currentY + rectSide;
		} else {
			rectX = currentX - rectSide;
			rectY = currentY;
		}
	
		return new PointPosition(rectX, rectY);
	}


	

}
