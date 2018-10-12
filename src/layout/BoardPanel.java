package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Iterator;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	
	private Graphics2D graphics2;
	private float initialSquareSize;
	float centerSquareSize;
	
	public BoardPanel() {
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		graphics2 = (Graphics2D) g;		
		
		keepAspectRatio();

		initialSquareSize = (float) (getSize().getWidth() * 2/ 5);

		paintInitialSquares();
		paintBoardSquares();

	}
	
	private void keepAspectRatio() {
		//lets keep aspect ratio
		int maxSize;
		if(getSize().getHeight() >= getSize().getWidth()) {
			maxSize = (int) getSize().getWidth();
		} else {
			maxSize = (int) getSize().getHeight();
		}
		
		setSize(maxSize,maxSize);
	}
	
	private void paintBoardSquares() {
		
		drawCenterRectangule();		
		

//		drawGreenTeam();
		drawBlueTeam();
//		drawRedTeam();
//		drawYellowTeam();
		

	}
	
	private void drawYellowTeam() {
		drawGameSquares(initialSquareSize + centerSquareSize, initialSquareSize, false, Color.YELLOW);
	}
	
	private void drawRedTeam() {
		drawGameSquares(0, initialSquareSize, false, Color.RED);
	}
	
	private void drawGreenTeam() {
		drawGameSquares(initialSquareSize, 0, true, Color.GREEN);
	}
	private void drawBlueTeam() {
		drawGameSquares(initialSquareSize, initialSquareSize  + centerSquareSize, true, Color.BLUE);	
	}
		
	private void drawGameSquares(float xPosition, float yPosition, boolean isVertical, Color color) {
		
		float rectWidth;
			
		if(isVertical) {
			rectWidth = (float) (getSize().getWidth() - 2*initialSquareSize) / 3;
		} else {
			rectWidth = (float) (getSize().getHeight() - 2*initialSquareSize) / 3;
		}
		graphics2.setPaint(Color.BLACK);
		Rectangle2D.Float rect;
	
		//index can be 0, 1 or 2
		int index = 0;
		
		for(int i=0;i<18;i++) {
			
			float rectX;
			float rectY;
			
			if(isVertical) {
				rectX = xPosition + rectWidth*index;;
				rectY = yPosition;				
			} else {
				rectX = xPosition;
				rectY = yPosition + rectWidth*index;
			}
		
			rect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectWidth);
			
			
			
			rect = fillColor(rect,i,color);
			
			graphics2.draw(rect);				
			index++;
			
			
			if(index != 0 && index % 3 == 0) {
				if(isVertical) {
					yPosition += rectWidth;
					xPosition = initialSquareSize;	
				} else {
					yPosition = initialSquareSize;
					xPosition += rectWidth;
				}				
				
				index = 0;
			}
					
		}
	}
	
	private Float fillColor(Rectangle2D.Float rect, int index, Color color) {
		//checks using index if it should be colored;
		
		if(index == 2) {
			graphics2.setColor(color);
			graphics2.fill(rect);
		}
		
		
		//back to black
		graphics2.setPaint(Color.BLACK);
		
		return rect;
	}
	private void drawCenterRectangule() {
		System.out.println("height: " + getSize().height);

		System.out.println("getWidth: " + getSize().getWidth());
		centerSquareSize = (float) (getSize().getHeight() - 2*initialSquareSize);
		Rectangle2D.Float rect = new Rectangle2D.Float(initialSquareSize, initialSquareSize , centerSquareSize,centerSquareSize);
		graphics2.setColor(Color.BLACK);
		graphics2.fill(rect);
		graphics2.draw(rect);
	}
	
	
	private void paintInitialSquares() {
				
		float rectX = (float) (getSize().getWidth() - initialSquareSize);
		float rectY = (float) (getSize().getHeight() - initialSquareSize);
		
		Rectangle2D.Float  rect1 = new Rectangle2D.Float(0, 0, initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.RED);
		graphics2.fill(rect1);
		
		
		Rectangle2D.Float  rect2 = new Rectangle2D.Float(rectX, 0, initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.GREEN);
		graphics2.fill(rect2);
		
		
		Rectangle2D.Float  rect3 = new Rectangle2D.Float(0, rectY, initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.BLUE);
		graphics2.fill(rect3);
		
		Rectangle2D.Float  rect4 = new Rectangle2D.Float(rectX, rectY , initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.YELLOW);
		graphics2.fill(rect4);
	}
	
	 
}
