package layout;

import java.awt.Graphics;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	
	
	public ControlPanel() {
		
	}
	

	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);		
		graphics.drawRect(10, 10, 200, 100);

	}
	 
}
