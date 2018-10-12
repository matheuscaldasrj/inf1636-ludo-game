package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Float;
import java.util.Iterator;

import javax.swing.JPanel;

import drawing.BlueBoardColorImpl;
import drawing.BoardColorInterface;
import drawing.GreenBoardColorImpl;
import drawing.RedBoardColorImpl;
import drawing.YellowBoardColorImpl;
import models.PointPosition;

public class BoardPanel extends JPanel {
	
	private Graphics2D graphics2;
	private float initialSquareSize;
	private float centerSquareSize;
	private int boardSize;
	private float rectSide;
	
	public BoardPanel() {
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		graphics2 = (Graphics2D) g;		
		
		keepAspectRatio();

		initialSquareSize = (float) (getSize().getWidth() * 2/ 5);

		
		paintBoardSquares();

	}
	
	private void keepAspectRatio() {
		//lets keep aspect ratio
		if(getSize().getHeight() >= getSize().getWidth()) {
			boardSize = (int) getSize().getWidth();
		} else {
			boardSize = (int) getSize().getHeight();
		}
		
		setSize(boardSize,boardSize);
	}
	
	private void paintBoardSquares() {
		rectSide = (float) (getSize().getWidth() - 2*initialSquareSize) / 3;
		
		//initialSquare
		paintInitialSquares();
		
		//center
		drawCenterRectangule();
		
		//team squares
		drawGreenTeam();
		drawBlueTeam();
		drawRedTeam();
		drawYellowTeam();
		

	}
	
	private void drawYellowTeam() {
		drawGameSquares(2*initialSquareSize + centerSquareSize -rectSide, initialSquareSize + centerSquareSize, new YellowBoardColorImpl());
	}
	
	private void drawRedTeam() {
		drawGameSquares(0, initialSquareSize - rectSide, new RedBoardColorImpl());
	}
	
	private void drawGreenTeam() {
		drawGameSquares(initialSquareSize + 3*rectSide, 0, new GreenBoardColorImpl());
	}
	private void drawBlueTeam() {
		drawGameSquares(initialSquareSize - rectSide, boardSize - rectSide, new BlueBoardColorImpl());	
	}
	

	private void drawGameSquares(float xPosition, float yPosition, BoardColorInterface boardColor) {
				
		graphics2.setPaint(Color.BLACK);
		Rectangle2D.Float rect;
	
		
		for(int i=0;i<18;i++) {
			PointPosition pointPosition = boardColor.getNextSquarePosition(i, rectSide, xPosition, yPosition);
			xPosition = pointPosition.getX();
			yPosition = pointPosition.getY();
			rect = new Rectangle2D.Float(xPosition, yPosition, rectSide, rectSide);
			rect = boardColor.fillColor(graphics2, rect, i);
			graphics2.draw(rect);				
					
		}
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
