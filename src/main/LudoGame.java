package main;

import java.awt.Color;

import java.util.ArrayList;
import java.util.List;

import layout.LudoGameFrame;
import models.Piece;
import gameRules.GameRules;

public class LudoGame {
	
	LudoGameFrame ludoGameFrame;
	GameRules rules;
	
	public void startGame() {
		ludoGameFrame = new LudoGameFrame();
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);		
		
		rules = new GameRules();
		rules.CreatePieces();
	}
	
}
