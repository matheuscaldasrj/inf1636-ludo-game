package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import layout.LudoGameFrame;
import models.Piece;

public class LudoGame {
	
	LudoGameFrame ludoGameFrame;
	
	public void startGame() {
		ludoGameFrame = new LudoGameFrame();
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);
		

	}
	
	public void metodoQuaquer() {

		
		Piece peca1 = new Piece(32, Color.GREEN, false);

		Piece peca2 = new Piece(35, Color.GREEN, false);
		
		List pieces = new ArrayList<>();
		
		pieces.add(peca1);
		pieces.add(peca2);
		
		ludoGameFrame.drawPieces(pieces);
	}
	
}
