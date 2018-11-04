package layout;

import java.awt.Graphics;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	JButton rollDie = new JButton ("Rolar dado");
	public ControlPanel() {
		this.setLayout(null);
		
		rollDie.setBounds(60, 500, 100, 50);
		this.add(rollDie);
	}
	

	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);		
		Graphics2D g2 = (Graphics2D) graphics;

	}
	 
}
