package main;

import java.util.ArrayList;
import java.util.List;

import gameRules.GameRules;
import layout.LudoGameFrame;
import models.Piece;
import java.awt.Color;
import java.awt.event.ActionListener;

public class LudoGame implements ActionListener {
	
	LudoGameFrame ludoGameFrame = new LudoGameFrame();
	List<Piece> pieces = new ArrayList<Piece>();
	GameRules rules = new GameRules();
	
	public void startGame() {
		
		//lets draw initial board
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);		
		
		// creates the pieces
		rules.createPieces();
	}
	
	
	
		
	public void drawNextRound(List<Piece> pieces, Color nextPlayerColor) {
		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);
	}

	
}
