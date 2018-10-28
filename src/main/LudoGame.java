package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import layout.LudoGameFrame;
import models.Piece;

public class LudoGame {
	
	LudoGameFrame ludoGameFrame;
	List<Piece> pieces = new ArrayList<Piece>();
	
	public void startGame() {
		
		//lets draw initial board
		ludoGameFrame = new LudoGameFrame();
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);
		
	
	
	}
		
	public void nextRound(List<Piece> pieces) {
	
		
		ludoGameFrame.setNewPieces(pieces);
	}

	
}
