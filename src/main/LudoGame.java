package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drawing.LudoGameFrame;
import gameRules.GameRules;
import listeners.BoardEventListener;
import models.InitialSquare;
import models.Piece;

// This is the class that contains everything. It controls the flow of the game
public class LudoGame implements BoardEventListener {
	
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
		
		
		
		ludoGameFrame.addBoardListener(this);
		
		simulatePieces();
		
	}
	
	
	public void simulatePieces() {
		
		Piece piece2 = new Piece(1, 23, Color.RED, false);
		Piece piece4 = new Piece(3, 71, Color.BLUE, false);
		Piece piece5 = new Piece(4, 53, Color.BLUE, false);
		Piece piece8 = new Piece(7, 10, Color.YELLOW, false);
		Piece piece10 = new Piece(9, 0, Color.GREEN, false);
		Piece piece6 = new Piece(5, 34, Color.GREEN, true);
		Piece piece11 = new Piece(8, 34, Color.RED, true);
		Piece piece7 = new Piece(6, 25, Color.YELLOW, true);
		Piece piece3 = new Piece(2, 25, Color.RED, true);
		Piece piece9 = new Piece(8, 2, Color.RED, true);
		Piece piece1 = new Piece(0, 2, Color.RED, false);
		
		drawNextRound(Arrays.asList(piece1, piece2, piece3,piece4, piece5, piece6, piece7, piece8, piece9, piece10, piece11), Color.red);
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
			List<Piece> pieces = (List<Piece>) returnClick;
			System.out.println(pieces);
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
