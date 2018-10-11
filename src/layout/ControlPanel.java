package layout;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	
	
	public ControlPanel() {
		
	}
	

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);		
		Graphics2D g2 = (Graphics2D) graphics;
		g2.draw(new Rectangle2D.Float(0, 0, 300, 400));

	}
	 
}
