package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import drawing.LudoGameFrame;
import gameRules.GameRules;
import models.Piece;

public class LudoGame {
	
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
