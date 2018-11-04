package drawing;

import java.awt.Color;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

import models.PointPosition;

public class YellowBoardColorImpl extends AbstractBoardColor implements BoardColorInterface {
	
	static Color color = Color.YELLOW;
	static Map<Integer, Integer> indexMap;
	
	public YellowBoardColorImpl() {
		super(color, indexMap);
	}

	static
    {
		indexMap = new HashMap<Integer, Integer>();
		
		indexMap.put(0, 38);indexMap.put(1, 37);
		indexMap.put(2, 36);indexMap.put(3, 39);
		indexMap.put(4, 67);indexMap.put(5, 35);
		indexMap.put(6, 40);indexMap.put(7, 68);
		indexMap.put(8, 34);indexMap.put(9, 41);
		
		indexMap.put(10, 69);indexMap.put(11, 33);
		indexMap.put(12, 42);indexMap.put(13, 70);
		indexMap.put(14, 32);indexMap.put(15, 43);
		indexMap.put(16, 71);indexMap.put(17, 31);
		
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
	
	@Override
	public Polygon getInitialTriangule(int xPosition, int yPosition, float rectSide) {
		int[] xPoints = {(int) (xPosition + 0.75*rectSide),(int) (xPosition + 0.25*rectSide),(int) (xPosition + 0.75*rectSide)};
		int[] yPoints = {(int) (yPosition + 0.25*rectSide),(int) (yPosition + 0.5*rectSide),(int) (yPosition + 0.75*rectSide)};
		
		Polygon polygon = new Polygon(xPoints, yPoints, 3);

		return polygon;
	}


	

}
