package layout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	
	private Graphics2D graphics2;
	private float initialSquareSize = 320;
	float centerSquareHeigth;
	
	public BoardPanel() {
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		graphics2 = (Graphics2D) g;		
		
		//TODO 
		//understand why this setSize is necessary
//		setSize(800,800);
		System.out.println("paintComponent construtor");
		System.out.println(getSize());
		
		paintInitialSquares();
//		paintBoardSquares();

	}
	
	private void paintBoardSquares() {
		
		drawCenterRectangule();		
		
		drawGreenTeam();
		drawBlueTeam();
		drawRedTeam();
		drawYellowTeam();
		

	}
	
	private void drawYellowTeam() {
		drawGameSquares(initialSquareSize + centerSquareHeigth, initialSquareSize, true);
	}
	
	private void drawRedTeam() {
		drawGameSquares(0, initialSquareSize, true);
	}
	
	private void drawGreenTeam() {
		drawGameSquares(initialSquareSize, 0, false);
	}
	private void drawBlueTeam() {
		drawGameSquares(initialSquareSize, initialSquareSize  + centerSquareHeigth, false);	
	}
		
	private void drawGameSquares(float xPosition, float yPosition, boolean isVertical) {
		
		float rectWidth;
			
		if(!isVertical) {
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
			
			if(!isVertical) {
				rectX = xPosition + rectWidth*index;;
				rectY = yPosition;				
			} else {
				rectX = xPosition;
				rectY = yPosition + rectWidth*index;
			}
			

			rect = new Rectangle2D.Float(rectX, rectY, rectWidth, rectWidth);
			graphics2.draw(rect);				
			index++;
			
			
			if(index != 0 && index % 3 == 0) {
				System.out.println(i);
				if(!isVertical) {
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
	

	
	private void drawCenterRectangule() {
		float centerSquareWidth = (float) (getSize().getWidth() - 2*initialSquareSize) -1;
		centerSquareHeigth = (float) (getSize().getHeight() - 2*initialSquareSize);
		Rectangle2D.Float rect = new Rectangle2D.Float(initialSquareSize, initialSquareSize - 1, centerSquareWidth,centerSquareHeigth);
		graphics2.setColor(Color.YELLOW);
		graphics2.fill(rect);
		graphics2.draw(rect);
	}
	
	
	private void paintInitialSquares() {
				
		float rectWidth = (float) (getSize().getWidth() - initialSquareSize);
		float rectHeight = (float) (getSize().getHeight() - initialSquareSize);
		
		Rectangle2D.Float  rect1 = new Rectangle2D.Float(0, 0, initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.RED);
		graphics2.fill(rect1);
		
		
		Rectangle2D.Float  rect2 = new Rectangle2D.Float(rectWidth, 0, initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.GREEN);
		graphics2.fill(rect2);
		
		
		Rectangle2D.Float  rect3 = new Rectangle2D.Float(0, rectHeight, initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.BLUE);
		graphics2.fill(rect3);
		
		Rectangle2D.Float  rect4 = new Rectangle2D.Float(rectWidth, rectHeight , initialSquareSize, initialSquareSize);
		graphics2.setColor(Color.YELLOW);
		graphics2.fill(rect4);
	}
	
	 
}
