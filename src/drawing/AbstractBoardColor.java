package drawing;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D.Float;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractBoardColor {
	
	Color color;
	int indexToBeBlack = 5;
	List<Integer> indexToBeColored = Arrays.asList( 4,7, 10, 13, 16); 
	
	
	public AbstractBoardColor(Color color) {
		this.color = color;
	}
	
	public Float fillColor(Graphics2D graphics2, Float rect, int index){
		
		if(indexToBeColored.contains(index)) {
			graphics2.setColor(color);
			graphics2.fill(rect);
		} else if(indexToBeBlack == index) {
			graphics2.setColor(Color.BLACK);
			graphics2.fill(rect);
		}
		
		
		graphics2.setPaint(Color.BLACK);
		
		return rect;
		
	}

}
