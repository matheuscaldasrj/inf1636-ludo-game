package main;

import java.util.ArrayList;
import java.util.List;

import gameRules.GameRules;
import layout.LudoGameFrame;
import models.Piece;

public class LudoGame {
	
	LudoGameFrame ludoGameFrame = new LudoGameFrame();
	List<Piece> pieces = new ArrayList<Piece>();
	GameRules rules = new GameRules();
	
	public void startGame() {
		
		//lets draw initial board
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);		
		
//		rules.CreatePieces();
		
	
	}
		
	public void drawNextRound(List<Piece> pieces) {
		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);
	}

	
}
