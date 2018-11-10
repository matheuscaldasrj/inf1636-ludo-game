package models;
import java.awt.Color;

public enum InitialSquare {
	 AZUL(Color.BLUE), 
	 VERMELHO(Color.RED),
	 VERDE(Color.GREEN),
	 AMARELO(Color.YELLOW);

	private final Color color;
	
	 private InitialSquare(Color color) {
		 this.color = color;
	 }
}
