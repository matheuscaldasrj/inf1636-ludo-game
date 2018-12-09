/**
 * 
 */
package main;

public class Main {

	public static void main(String[] args) {
		System.out.println("Ludo game is now running!");
		LudoGame ludoGame = LudoGame.getGameRules();
		ludoGame.startGame();

	}
	

}
