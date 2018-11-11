package models;
import java.awt.Color;

import drawing.BoardColorInterface;
import drawing.BlueBoardColorImpl;
import drawing.RedBoardColorImpl;
import drawing.GreenBoardColorImpl;
import drawing.YellowBoardColorImpl;

public enum InitialSquare {
	 AZUL(new BlueBoardColorImpl()), 
	 VERMELHO(new RedBoardColorImpl()),
	 VERDE(new GreenBoardColorImpl()),
	 AMARELO(new YellowBoardColorImpl());

	private final Color color;
	
	 private InitialSquare(BoardColorInterface boardColor) {
		 this.color = boardColor.getColor();
	 }
}
