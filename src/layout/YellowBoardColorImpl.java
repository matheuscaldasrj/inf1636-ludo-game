package layout;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;

import models.PointPosition;

public class YellowBoardColorImpl extends AbstractBoardColor implements BoardColorInterface {
	
	static Color color = Color.YELLOW;
	
	public YellowBoardColorImpl() {
		super(color);
	}

	
	@Override
	public PointPosition getNextSquarePosition(int index, float rectSide, float currentX, float currentY) {
		float rectX;
		float rectY;		

		if( index != 0 && index % 3 == 0) {
			//GO TO NEXT LEVEL
			rectX = currentX - rectSide;
			rectY = currentY + 2*rectSide;
		} else {
			rectX = currentX;
			rectY = currentY - rectSide;
		}
	
		return new PointPosition(rectX, rectY);
	}


	

}
