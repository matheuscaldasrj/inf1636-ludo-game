package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.HashMap;
import java.util.Map;

import models.PointPosition;

public class RedBoardColorImpl extends AbstractBoardColor implements BoardColorInterface {
	
	static Color color = Color.RED;
	static Map<Integer, Integer> indexMap;
	
	public RedBoardColorImpl() {
		super(color, indexMap);
	}

	static
    {
		indexMap = new HashMap<Integer, Integer>();
		
		indexMap.put(0, 12);indexMap.put(1, 11);
		indexMap.put(2, 10);indexMap.put(3, 13);
		indexMap.put(4, 57);indexMap.put(5, 9);
		indexMap.put(6, 14);indexMap.put(7, 58);
		indexMap.put(8, 8);indexMap.put(9, 15);
		
		indexMap.put(10, 59);indexMap.put(11, 7);
		indexMap.put(12, 16);indexMap.put(13, 60);
		indexMap.put(14, 6);indexMap.put(15, 17);
		indexMap.put(16, 61);indexMap.put(17, 5);
		
    }
	
	@Override
	public PointPosition getNextSquarePosition(int index, float rectSide, float currentX, float currentY) {
		float rectX;
		float rectY;		

		if( index != 0 && index % 3 == 0) {
			//GO TO NEXT LEVEL
			rectX = currentX + rectSide;
			rectY = currentY - 2*rectSide;
		} else {
			rectX = currentX;
			rectY = currentY + rectSide;
		}
	
		return new PointPosition(rectX, rectY);
	}


	

}
