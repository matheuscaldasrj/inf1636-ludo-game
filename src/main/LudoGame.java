package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import drawing.BlueBoardColorImpl;
import drawing.GreenBoardColorImpl;
import drawing.LudoGameFrame;
import drawing.RedBoardColorImpl;
import drawing.YellowBoardColorImpl;
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
		
		Color yellowColor = new YellowBoardColorImpl().getColor();
		Color blueColor = new BlueBoardColorImpl().getColor();
		Color redColor = new RedBoardColorImpl().getColor();
		Color greenColor = new GreenBoardColorImpl().getColor();
		
		Piece piece1 = new Piece(0, 72, blueColor, false);
		Piece piece2 = new Piece(1, 73, blueColor, false);
		Piece piece3 = new Piece(2, 74, blueColor, false);
		Piece piece4 = new Piece(3, 75, blueColor, false);
		
		Piece piece5 = new Piece(4, 76, redColor, false);
		Piece piece6 = new Piece(5, 77, redColor, false);
		Piece piece7 = new Piece(6, 78, redColor, false);
		Piece piece8 = new Piece(7, 79, redColor, false);
		
		Piece piece9 = new Piece(8, 80, greenColor, false);
		Piece piece10 = new Piece(9, 81, greenColor, false);
		Piece piece11 = new Piece(10, 82, greenColor, false);
		Piece piece12 = new Piece(11, 83, greenColor, false);
		
		Piece piece13 = new Piece(12, 84, yellowColor, false);
		Piece piece14 = new Piece(13, 85, yellowColor, false);
		Piece piece15 = new Piece(14, 86, yellowColor, false);
		Piece piece16 = new Piece(15, 87, yellowColor, false);
		
		
		//more tests
		Piece piece17 = new Piece(16, 50, yellowColor, false);
		Piece piece18 = new Piece(17, 70, yellowColor, false);
		Piece piece19 = new Piece(18, 36, yellowColor, false);
		Piece piece20 = new Piece(20, 36, greenColor, false);
		Piece piece21 = new Piece(19, 5, yellowColor, false);
		Piece piece22 = new Piece(20, 5, yellowColor, false);
		Piece piece23 = new Piece(21, 52, blueColor, false);
			
		drawNextRound(Arrays.asList(piece1, piece2, piece3, piece4, piece5, piece6,
				piece7 ,piece8, piece9, piece10, piece11, piece12, piece13, piece14,
				piece15, piece16, piece17, piece18, piece19, piece20, piece21, piece22, piece23), Color.RED);
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
