/**
 * 
 */
package main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import models.Piece;

public class Main {

	public static void main(String[] args) {
		System.out.println("Ludo gasssme is snow running!!");
		LudoGame ludoGame = new LudoGame();
		ludoGame.startGame();
		
		
		//simulando uma jogada
		
		List<Piece> pieces = new ArrayList<Piece>();
		
		Piece peca1 = new Piece(0,5, Color.BLUE, true);
		Piece peca2 = new Piece(1,7, Color.BLUE, true);
		Piece peca3 = new Piece(2,65, Color.BLUE, true);
		Piece peca4 = new Piece(3,35, Color.BLUE, true);

		Piece peca5 = new Piece(4,21, Color.RED, true);
		Piece peca6 = new Piece(5,30, Color.RED, true);
		Piece peca7 = new Piece(6,70, Color.RED, true);
		Piece peca8 = new Piece(7,27, Color.RED, true);
		
		Piece peca9 = new Piece(8,70, Color.GREEN, true);
		Piece peca10= new Piece(9,25, Color.GREEN, true);
		Piece peca11 = new Piece(10,17, Color.GREEN, true);
		Piece peca12 = new Piece(11,2, Color.GREEN, true);
		
		Piece peca13 = new Piece(12,2, Color.YELLOW, true);
		Piece peca14 = new Piece(13,14, Color.YELLOW, true);
		Piece peca15 = new Piece(14,4, Color.YELLOW, true);
		Piece peca16 = new Piece(15,0, Color.YELLOW, true);

		pieces.add(peca1);pieces.add(peca2);pieces.add(peca3);pieces.add(peca4);
		pieces.add(peca5);pieces.add(peca6);pieces.add(peca7);pieces.add(peca8);
		pieces.add(peca9);pieces.add(peca10);pieces.add(peca11);pieces.add(peca12);
		pieces.add(peca13);pieces.add(peca14);pieces.add(peca15);pieces.add(peca16);

		
		ludoGame.drawNextRound(pieces);

	}
	

}
