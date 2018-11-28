package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import drawing.LudoGameFrame;
import gameRules.GameRules;
import listeners.BoardEventListener;
import listeners.ControlEventListener;
import models.BoardSpace;
import models.FileGame;
import models.Piece;
import saveRestore.SaveAndRestoreGame;

// This is the class that contains everything. It controls the flow of the game
public class LudoGame implements BoardEventListener, ControlEventListener {
	
	LudoGameFrame ludoGameFrame = new LudoGameFrame();
	SaveAndRestoreGame saveAndRestoreGame = new SaveAndRestoreGame();
	List<Piece> pieces;
	GameRules rules = new GameRules();
	
	int roll;			// How much the player got on his roll
	int timesRolled6;	// How many times the player has rolled 6 on this turn
	boolean hasRolled;	// Defines if the player has already rolled the die on his turn
	Color playerTurn;	// Defines the color of the player that shall make a move
	int playerId;		// Defines the id of the player that shall make a move
	int turnsToFinishFirstRound; // Used to control when the pieces don't get to start on the first house
	
	public void startGame() {
		pieces = new ArrayList<Piece>();
		
		//lets draw initial board
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);		
		
		playerTurn = Color.BLUE;
		playerId = 0;
		hasRolled = false;
		timesRolled6 = 0;
		turnsToFinishFirstRound = 4;
		
		// creates the pieces
		pieces = rules.createPieces(pieces);
		
		addListeners();
		
		ludoGameFrame.setNewPieces(pieces);
	}
	
	private void addListeners() {
		ludoGameFrame.addBoardListener(this);
		ludoGameFrame.addControlListener(this);
		
	}
	@Override
	public void notifyBoardClicks(Object returnClick, boolean isPiece) {
		Piece p;
		boolean hasChecked = false; // Used to determine when the player is going to move 6 spaces after capturing a piece
		
		System.out.println("NotifyBoardclicks no LudoGame");
		
		// If the player clicked on a piece ===================================================
		if(isPiece) {
			if(hasRolled) {
				ArrayList<Piece> pieces = (ArrayList<Piece>) returnClick;
				//System.out.println(pieces);
				
				p = rules.checkIfCorrectColor(playerTurn, pieces);
				
				if(p!=null) {						
					
					// Used when the player captured a piece on his last move
					if(roll != 6 && rules.getCanMoveAnotherPiece()) {
						System.out.println("You can move another piece!");
						roll = 6;
						hasChecked = true;
						// Redraws the board to show the new die value. When the player clicks on another piece, he'll move 6 spaces
						ludoGameFrame.getControlPanel().setDieSide(6);
						ludoGameFrame.setNewPieces(this.pieces);
					}
					
					if(rules.movePiece(p, roll)) {
						
						// When a player rolls 6, they can play again. Instead of passing their turn, we just show the new piece positions
						if(roll == 6 && !hasChecked) {  
							System.out.println("Rolled 6!!");
							hasRolled = false;
							ludoGameFrame.getControlPanel().getRollDieButton().setEnabled(true);
							ludoGameFrame.setNewPieces(this.pieces);
						}
	
						// if the player hasn't rolled 6 or captured a piece, we can pass the turn to the next player
						else { 
							System.out.println("Didn't roll 6");
							ludoGameFrame.getControlPanel().setShowDieSide(false);
							
							// If the player captured a piece, we can't pass their turn yet. They have to move again before we can do so.
							if(rules.getCanMoveAnotherPiece() && !hasChecked)
								ludoGameFrame.setNewPieces(this.pieces);
							else
								drawNextRound(this.pieces);
						}
					}
				}
			}else {
				System.out.println("Peca nao eh da cor do jogador");
			}
			
		// If the player clicked on nothing ====================================================
		} else {
			//returnClick is null, its a unknown event
			System.out.println("Click identificado, mas nao eh um local inicial nem uma peca");
		}
	}
	
	// Gets and sets which is the next player to play and redraws the whole board with the updated piece positions
	public void drawNextRound(List<Piece> pieces) {
		
		this.playerTurn = rules.getNextTurnColor(this.playerTurn);
		this.playerId++;
		if(this.playerId == 4)
			this.playerId = 0;
		
		ludoGameFrame.getControlPanel().setTurnColor(playerTurn);
		
		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);
		
		timesRolled6 = 0;
		hasRolled = false;
		ludoGameFrame.getControlPanel().getRollDieButton().setEnabled(true);
	}

	@Override
	public void onNewGameButtonClicked(ActionEvent event) {
		System.out.println("Ludo game - onNewGameButtonClicked");
		System.out.println(event);
		
	}

	@Override
	public void onLoadGameButtonClicked(ActionEvent event, File file) {
		System.out.println("Ludo game - onLoadGameButtonClicked");
		System.out.println(event);
		System.out.println(file.getName());
		
		//retrieving fileGame
		FileGame fileGame = saveAndRestoreGame.loadGame(file);
		
		if(fileGame == null) {
			//error trying to read file
			return ;
		}
		
		//setting file game
		pieces = fileGame.getPieces();
		playerTurn = fileGame.getPlayerTurn();
		rules.setBoardSpaces(fileGame.getBoardSpaces());
		rules.setLastMovedPieceArray(fileGame.getLastMovedPieceArray());
		
		drawNextRound(pieces);
	}

	@Override
	public void onSaveGameClicked(ActionEvent event, File file) {
		System.out.println("Ludo game - onSaveGameClicked");
		System.out.println(event);
		System.out.println(file.getName());
		
		BoardSpace[] boardSpaces = rules.getBoardSpaces();
		//building file game
		Piece[] lastMovedPiece = rules.getLastMovedPieceArray();
		FileGame fileGame = new FileGame(file, pieces, playerTurn, lastMovedPiece, boardSpaces);
		//saving it
		saveAndRestoreGame.saveGame(fileGame);
	}

	@Override
	public void onRollDiceClicked(ActionEvent event, Integer fakeValue) {
		System.out.println("Ludo game - onRollDiceClicked");
		System.out.println("FakeValue: " + fakeValue);
		System.out.println(event);
		
		// Makes the button "rollDie" (ControlPanel) get a random number and display it as an image in it's panel
		// If the player has already rolled the die on his turn, he may not roll again
		if(!hasRolled) {
			
			ludoGameFrame.getControlPanel().getRollDieButton().setEnabled(false);
			
			if(fakeValue == null) {
				roll = rules.rollDie();				
			} else {
				//its a fake roll, use defined value
				roll = fakeValue;
			}
			hasRolled = true;

			ludoGameFrame.getControlPanel().setDieSide(roll);
			ludoGameFrame.getControlPanel().setShowDieSide(true);
			
			// On the first round, each player, on their turn, starts with a piece at the first position
			if(turnsToFinishFirstRound > 0 && timesRolled6 < 1) {
				rules.moveFromInitialSquare(playerTurn, pieces);
				turnsToFinishFirstRound--;
				ludoGameFrame.setNewPieces(pieces);
				System.out.println("Turns to finish the first round: "+ turnsToFinishFirstRound);
				
			}
			
			if(roll == 6) {
				timesRolled6++;
				if(timesRolled6 == 3) {
					rules.sendPieceToStart(rules.getLastMovedPiece(playerId));
					timesRolled6=0;
					ludoGameFrame.getControlPanel().setShowDieSide(false);
					ludoGameFrame.setNewPieces(this.pieces);
				}	
			}
			else if(roll == 5) {
				if(rules.moveFromInitialSquare(playerTurn, pieces)) {
					drawNextRound(pieces);
				}
			}		
		}
	}
}
