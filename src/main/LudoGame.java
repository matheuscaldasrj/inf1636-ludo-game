package main;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import layout.LudoGameFrame;
import models.Piece;
import gameRules.GameRules;

public class LudoGame {
	
	LudoGameFrame ludoGameFrame;
	List<Piece> pieces = new ArrayList<Piece>();
	GameRules rules;
	
	public void startGame() {
		
		//lets draw initial board
		ludoGameFrame = new LudoGameFrame();
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);		
		
		rules = new GameRules();
		rules.CreatePieces();
	}
		
	public void nextRound(List<Piece> pieces) {
	
		
		ludoGameFrame.setNewPieces(pieces);
	}

	
}
