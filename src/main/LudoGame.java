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
		
		System.out.println("NotifyBoardclicks no LudoGame");
		
		// If the player clicked on a piece ===================================================
		if(isPiece) {
			if(hasRolled) {
				ArrayList<Piece> pieces = (ArrayList<Piece>) returnClick;
				System.out.println(pieces);
				
				p = rules.checkIfCorrectColor(playerTurn, pieces);
				if(p!=null) {
					
					if(rules.movePiece(p, roll)) {
						drawNextRound(this.pieces);
						
						// In case the player rolled 6 and has already made a move, he may roll again
						if(timesRolled6 > 0) {
							hasRolled = false;
							if(timesRolled6 == 2) 
								rules.sendPieceToStart(rules.getLastMovedPiece(playerId));
						}
					}
				
				}
			}else {
				System.out.println("Peca nao eh da cor do jogador");
			}
			
		// Acho que, segundo as regras no documento, voc� n�o tem escolha ao tirar 5: Se puder mover o pe�o, tem que tir�-lo da casa inicial	
			
		/*// If the player clicked on a initial square ===========================================
		} else if(returnClick != null) {
			//is the enumerator
			InitialSquare initialSquare = (InitialSquare) returnClick;
			System.out.println(initialSquare);
			
			if(rules.moveFromInitialSquare(initialSquare.getColor(), roll, playerTurn, pieces) == true);
		*/
			
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
		
		System.out.println("Vamos desenhar as pecas");
		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);
		
		hasRolled = false;
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
		
		//retriving fileGame
		FileGame fileGame = saveAndRestoreGame.loadGame(file);
		
		if(fileGame == null) {
			//error trying to read file
			return ;
		}
		
		//setting file game
		pieces = fileGame.getPieces();
		playerTurn = fileGame.getPlayerTurn();
		rules.setBoardSpaces(fileGame.getBoardSpaces());
		playerId = fileGame.getPlayerId();

		drawNextRound(pieces);
	}

	@Override
	public void onSaveGameClicked(ActionEvent event, File file) {
		System.out.println("Ludo game - onSaveGameClicked");
		System.out.println(event);
		System.out.println(file.getName());
		
		BoardSpace[] boardSpaces = rules.getBoardSpaces();
		//building file game
		FileGame fileGame = new FileGame(file, pieces, playerTurn, playerId, boardSpaces);
		//saving it
		saveAndRestoreGame.saveGame(fileGame);
	}

	@Override
	public void onRollDiceClicked(ActionEvent event) {
		System.out.println("Ludo game - onRollDiceClicked");
		System.out.println(event);
		// Makes the button "rollDie" (ControlPanel) get a random number and display it as an image in it's panel
		// If the player has already rolled the die on his turn, he may not roll again
		if(!hasRolled) {
			roll = rules.rollDie();
			ludoGameFrame.getControlPanel().setDieSide(roll);
			hasRolled = true;					
			
			if(roll == 6)
				timesRolled6++;
			
			if(turnsToFinishFirstRound > 0) {
				rules.moveFromInitialSquare(playerTurn, pieces);
				turnsToFinishFirstRound--;
				ludoGameFrame.setNewPieces(pieces);
				
			}
			else if(roll == 5) {
				if(rules.moveFromInitialSquare(/*squareColor,*/ playerTurn, pieces)) {
					drawNextRound(pieces);
					hasRolled = false;
				}
			}
			
		}
	}

	
}
