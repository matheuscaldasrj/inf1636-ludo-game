package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drawing.LudoGameFrame;
import drawing.ControlPanel;
import gameRules.GameRules;
import listeners.BoardEventListener;
import models.InitialSquare;
import models.Piece;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// This is the class that contains everything. It controls the flow of the game

public class LudoGame implements ActionListener, BoardEventListener {
	
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
		Piece piece = new Piece(2, 5, Color.red, false);
		drawNextRound(Arrays.asList(piece), Color.red);
		ludoGameFrame.addBoardListener(this);
	}

	
	
	// Redraws the whole board with the updated piece positions
	public void drawNextRound(List<Piece> pieces, Color nextPlayerColor) {
		System.out.println("Vamos desenhar as pecas");
		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);
	}

	@Override
	public void notifyBoardClicks(Object returnClick, boolean isPiece) {
		System.out.println("NotifyBoardclicks no LudoGame");
		
		if(isPiece) {
			Piece piece = (Piece) returnClick;
			System.out.println(piece);
		} else if(returnClick != null) {
			//is the enum
			InitialSquare initialSquare = (InitialSquare) returnClick;
			System.out.println(initialSquare);
		} else {
			//returnClick is null, its a unkown event
			System.out.println("Click identificado, mas nao eh um local inicial nem uma peca");
		}


		
	}


	
}
