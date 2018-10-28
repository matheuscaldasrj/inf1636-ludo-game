package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.HashMap;
import java.util.Map;

import models.PointPosition;

public class GreenBoardColorImpl extends AbstractBoardColor implements BoardColorInterface {
	
	static Color color = Color.GREEN;
	static Map<Integer, Integer> indexMap;
	
	public GreenBoardColorImpl() {
		super(color, indexMap);
	}
	
	static
    {
		indexMap = new HashMap<Integer, Integer>();
		
		indexMap.put(0, 25);indexMap.put(1, 24);
		indexMap.put(2, 23);indexMap.put(3, 26);
		indexMap.put(4, 62);indexMap.put(5, 22);
		indexMap.put(6, 27);indexMap.put(7, 63);
		indexMap.put(8, 21);indexMap.put(9, 28);
		
		indexMap.put(10, 64);indexMap.put(11, 20);
		indexMap.put(12, 29);indexMap.put(13, 65);
		indexMap.put(14, 19);indexMap.put(15, 30);
		indexMap.put(16, 66);indexMap.put(17, 18);
		
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
