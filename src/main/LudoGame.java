package main;

import java.util.ArrayList;
import java.util.List;

import drawing.LudoGameFrame;
import drawing.ControlPanel;
import gameRules.GameRules;
import models.Piece;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This is the class that contains everything. It controls the flow of the game

public class LudoGame {
	
	LudoGameFrame ludoGameFrame = new LudoGameFrame();
	List<Piece> pieces = new ArrayList<Piece>();
	GameRules rules = new GameRules();
	int roll;
	Color playerTurn;
	
	public void startGame() {
		
		//lets draw initial board
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);		
		
		playerTurn = Color.BLUE;
		
		// creates the pieces
		rules.createPieces();
		
		// Makes the button "rollDie" (ControlPanel) get a random number and display it as an image in it's panel
		ludoGameFrame.getControlPanel().getRollDieButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				roll = rules.rollDie();
				
				ludoGameFrame.getControlPanel().setDieSide(roll);
			}
		});
	}

	
	
	// Redraws the whole board with the updated piece positions
	public void drawNextRound(List<Piece> pieces, Color nextPlayerColor) {
		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);
	}


	
}
