package drawing;

import java.awt.Color;
import java.awt.Polygon;
import java.util.HashMap;
import java.util.Map;

import models.PointPosition;

public class BlueBoardColorImpl extends AbstractBoardColor implements BoardColorInterface {
	
	static Color color = Color.BLUE;
	static Map<Integer, Integer> indexMap;
	
	
	public BlueBoardColorImpl() {
		super(color, indexMap);		
	}
	
	
	
	static
    {
		indexMap = new HashMap<Integer, Integer>();
		
		indexMap.put(0, 51);indexMap.put(1, 50);
		indexMap.put(2, 49);indexMap.put(3, 0);
		indexMap.put(4, 52);indexMap.put(5, 48);
		indexMap.put(6, 1);indexMap.put(7, 53);
		indexMap.put(8, 47);indexMap.put(9, 2);
		
		indexMap.put(10, 54);indexMap.put(11, 46);
		indexMap.put(12, 3);indexMap.put(13, 55);
		indexMap.put(14, 45);indexMap.put(15, 4);
		indexMap.put(16, 56);indexMap.put(17, 44);
		
    }
	
	@Override
	public PointPosition getNextSquarePosition(int index, float rectSide, float currentX, float currentY) {
		float rectX;
		float rectY;		

		if( index != 0 && index % 3 == 0) {
			//GO TO NEXT LEVEL
			rectX = currentX - 2*rectSide;
			rectY = currentY - rectSide;
		} else {
			rectX = currentX + rectSide;
			rectY = currentY;
		}
	
		return new PointPosition(rectX, rectY);
	}

	@Override
	public Polygon getInitialTriangule(int xPosition, int yPosition, float rectSide) {
		
		int[] xPoints = {(int) (xPosition + 0.25*rectSide),(int) (xPosition + 0.5*rectSide),(int) (xPosition + 0.75*rectSide)};
		int[] yPoints = {(int) (yPosition + 0.75*rectSide),(int) (yPosition + 0.25*rectSide),(int) (yPosition + 0.75*rectSide)};
		
		Polygon polygon = new Polygon(xPoints, yPoints, 3);

		return polygon;
		
	}



	

}
