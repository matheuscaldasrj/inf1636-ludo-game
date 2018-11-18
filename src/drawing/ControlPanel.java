package drawing;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;
import javax.swing.JPanel;


// This is the panel which contains every button that controls the game

public class ControlPanel extends JPanel {
	JButton rollDieButton = new JButton ("Rolar dado");
	private Image dieSides[] = new Image[6];
	private int dieSide = 0;
	private Color turnColor;
	Rectangle2D.Float rect = new Rectangle2D.Float(130, 360, 140, 140);
	
	public ControlPanel() {
		this.setLayout(null);
		
		turnColor = Color.BLUE;
		rollDieButton.setBounds(150, 500, 100, 50);
		this.add(rollDieButton);
		
		// Loads all the images for the die's sides
		for(int i=0 ; i<6 ; i++) {
			try {
				dieSides[i] = ImageIO.read(new File("DieImages/Dado" + (i+1) + ".png"));
				
			} catch (IOException e) {
				System.out.println("Error while trying to open die image file");
				e.printStackTrace();
			}
		}
	}
	
	// Draws the control panel
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);		
		Graphics2D g2 = (Graphics2D) graphics;
		
		g2.setColor(turnColor);
		g2.fill(rect);
		g2.drawImage(dieSides[dieSide], 150, 380, null);

	}
	
	// Gets the button that rolls the die
	public JButton getRollDieButton() {
		return rollDieButton;
	}
	
	// Sets which side of the die will be displayed by the paint component
	public void setDieSide(int dieSide) {
		this.dieSide = dieSide-1;
		
		repaint();
	}
	public void setTurnColor(Color turnColor) {
		this.turnColor = turnColor;
		
		repaint();
	}
	 
}
