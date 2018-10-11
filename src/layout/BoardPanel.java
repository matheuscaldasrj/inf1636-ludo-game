package layout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	
	
	public BoardPanel() {
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);		
		Graphics2D graphics2 = (Graphics2D) g;
		
		float squareSize = 300;
		
		float rectWidth = (float) (getSize().getWidth() - squareSize);
		float rectHeight = (float) (getSize().getHeight() - squareSize);
		
		Rectangle2D.Float  rect1 = new Rectangle2D.Float(0, 0, squareSize, squareSize);
		graphics2.setColor(Color.RED);
		graphics2.fill(rect1);
		
		
		Rectangle2D.Float  rect2 = new Rectangle2D.Float(rectWidth, 0, squareSize, squareSize);
		graphics2.setColor(Color.GREEN);
		graphics2.fill(rect2);
		
		
		Rectangle2D.Float  rect3 = new Rectangle2D.Float(0, rectHeight, squareSize, squareSize);
		graphics2.setColor(Color.BLUE);
		graphics2.fill(rect3);
		
		Rectangle2D.Float  rect4 = new Rectangle2D.Float(rectWidth, rectHeight , squareSize, squareSize);
		graphics2.setColor(Color.YELLOW);
		graphics2.fill(rect4);
				

	}
	
	 
}
