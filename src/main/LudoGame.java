package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

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

	// Used so the class can be a Singleton
	private static LudoGame ludoGame = null;

	LudoGameFrame ludoGameFrame = new LudoGameFrame();
	SaveAndRestoreGame saveAndRestoreGame = new SaveAndRestoreGame();
	List<Piece> pieces;
	GameRules rules = GameRules.getGameRules();

	int roll; // How much the player got on his roll
	int timesRolled6; // How many times the player has rolled 6 on this turn
	boolean hasAStored6;// If the player rolled 6 in his last roll
	int storedRoll; // The roll the player had before bonus move rule (after capturing a piece)
					// applied
	boolean hasRolled; // Defines if the player has already rolled the die on his turn
	Color playerTurn; // Defines the color of the player that shall make a move
	int playerId; // Defines the id of the player that shall make a move
	int turnsToFinishFirstRound; // Used to control when the pieces don't get to start on the first house
	boolean capturedInFirstRound; // Used to check when a piece was captured after leaving the initial position on
									// the first round
	boolean capturedPieceInStartPos; //If the player captured a piece after leaving the initial square
	
	boolean hasUsedExtraMove; // Used to control the extra moves
	boolean firstTimeAddingListeners = true;

	public static LudoGame getGameRules() {
		if (ludoGame == null) {
			ludoGame = new LudoGame();
		}
		return ludoGame;
	}

	public void startGame() {
		pieces = new ArrayList<Piece>();

		// lets draw initial board
		ludoGameFrame.setTitle("Ludo game");
		ludoGameFrame.setVisible(true);

		playerTurn = Color.BLUE;
		playerId = 0;
		hasRolled = false;
		timesRolled6 = 0;
		turnsToFinishFirstRound = 4;
		hasUsedExtraMove = false;

		// creates the pieces
		pieces = rules.createPieces(pieces);

		addListeners();

		ludoGameFrame.setNewPieces(pieces);
	}

	private void addListeners() {
		if (firstTimeAddingListeners) {
			ludoGameFrame.addBoardListener(this);
			ludoGameFrame.addControlListener(this);
		}

		firstTimeAddingListeners = false;
	}

	private String translateColorName(Color color) {

		if (color.equals(Color.BLUE)) {
			return "Blue";
		} else if (color.equals(Color.RED)) {
			return "RED";
		} else if (color.equals(Color.GREEN)) {
			return "GREEN";
		} else if (color.equals(Color.YELLOW)) {
			return "YELLOW";
		}

		return "Unknown";

	}

	private void gameHasFinished(Color[] positions) {

		String message = "";
		message += "1 lugar: " + translateColorName(positions[0]) + "\n";
		message += "2 lugar: " + translateColorName(positions[1]) + "\n";
		message += "3 lugar: " + translateColorName(positions[2]) + "\n";
		message += "4 lugar: " + translateColorName(positions[3]) + "\n";

		JOptionPane.showMessageDialog(new JDialog(), message, "Game has finished", JOptionPane.INFORMATION_MESSAGE);

		int whatToDo = JOptionPane.showConfirmDialog(new JDialog(),
				"Game has finished, do you want to play a new game?", "Game has finished", JOptionPane.YES_NO_OPTION);

		if (whatToDo == JOptionPane.YES_OPTION) {
			System.out.println("new game");
			restartGame();
		} else if (whatToDo == JOptionPane.NO_OPTION || whatToDo == JOptionPane.CLOSED_OPTION) {
			System.out.println("Closing game by no button...");
			System.out.println("Closing game...");
			System.exit(0);
		}

	}

	@Override
	public void notifyBoardClicks(Object returnClick, boolean isPiece) {
		Piece p;

		System.out.println("NotifyBoardclicks no LudoGame");

		// If the player clicked on a piece
		// ===================================================
		if (isPiece) {
			if (hasRolled) {
				ArrayList<Piece> pieces = (ArrayList<Piece>) returnClick;

				p = rules.checkIfCorrectColor(playerTurn, pieces);
				System.out.println("Selected piece index: " + p.getId());
				/*
				 * The bonus movements the player has gained by: 1) Capturing a piece 2)
				 * Reaching the final space has priority over rolls of 6. This happens because
				 * the player might get another extra move with the extra move he used, so we
				 * must spend it as soon as possible.
				 *
				 * So, for example if the player rolls 6 and captures a piece, they'll be unable
				 * to roll again until they use up their extra move.
				 */
				if (p != null && !p.getHasFinished()) {

					// If the player can move the selected piece, moves it
					if (rules.checkIfCanMovePiece(p, roll)) {
						if(! capturedPieceInStartPos) {
							System.out.println("Moved the piece!");
							if (rules.getMovedFromInitialSquare()) {
								rules.moveFromInitialSquare();
							} else {
								Color[] playersPositions = rules.movePiece(p, this.pieces, playerTurn);
								if (playersPositions != null) {
									gameHasFinished(playersPositions);
								}
							}							
						}

						// Used to avoid problems with cases when a player falls into one of these
						// situations:
						/*
						 * 1) Rolled 6 but didn't capture a piece 2) Didn't roll 6 but captured a piece
						 * 3) Rolled 6 and captured a piece
						 */
						if (hasAStored6) {
							// Do nothing, the roll is already 6
						} else if (roll == 6 && !hasUsedExtraMove) {
							// Do nothing, the roll is already 6
						} else
							// We already used up all our moves
							roll = 0;

						// If the bonus move from capturing a piece when leaving the initial square on
						// the first move of this player on this game didn't happen
						if (!capturedInFirstRound) {
							// Checks if has a bonus move (+6) and if can move any piece with that bonus
							if (rules.getCanMoveAnotherPiece()
									&& rules.checkIfCanMakeAMove(playerTurn, this.pieces, 6)) {
								System.out.println("You can move another piece!");
								if (roll == 6) {
									hasAStored6 = true;
								}

								roll = 6;
								hasUsedExtraMove = true;
								rules.setCanMoveAnotherPiece(false);
								capturedPieceInStartPos = false;

								// Redraws the board to show the new die value. When the player clicks on
								// another piece, he'll move 6 spaces
								ludoGameFrame.setDieSide(6);
								ludoGameFrame.setNewPieces(this.pieces);
							}
							// When a player rolls 6, they can play again. Instead of passing their turn, we
							// just show the new piece positions
							else if (roll == 6) {
								hasRolled = false;
								hasAStored6 = false;

								ludoGameFrame.setEnableRollDieButton(true);
								ludoGameFrame.setNewPieces(this.pieces);

								hasUsedExtraMove = false;
							}
							// if the player hasn't rolled 6 or captured a piece, we can pass the turn to
							// the next player
							else {
								System.out.println("Didn't roll 6");
								ludoGameFrame.setShowDieSide(false);

								drawNextRound(this.pieces);

								hasUsedExtraMove = false;
							}
							capturedInFirstRound = false;

							// If the bonus move of the first round was used this turn, sets the roll back
							// to it's former value
						} else {
							roll = storedRoll;
							capturedInFirstRound = false;

							ludoGameFrame.setDieSide(roll);
							ludoGameFrame.setNewPieces(this.pieces);
						}

						// Can't move piece
					} else {
						System.out.println("Essa peca nao pode ser movida. Selecione outra");
					}
				}
			} else {
				System.out.println("Peca nao eh da cor do jogador");
			}

			// If the player clicked on nothing
			// ====================================================
		} else {
			// returnClick is null, its a unknown event
			System.out.println("Click identificado, mas nao eh um local inicial nem uma peca");
		}
	}

	// Gets and sets which is the next player to play and redraws the whole board
	// with the updated piece positions
	public void drawNextRound(List<Piece> pieces) {

		this.playerTurn = rules.getNextTurnColor(this.playerTurn);
		this.playerId++;
		if (this.playerId == 4)
			this.playerId = 0;

		ludoGameFrame.setTurnColor(playerTurn);
		ludoGameFrame.setShowDieSide(false);

		System.out.println(pieces);
		ludoGameFrame.setNewPieces(pieces);

		timesRolled6 = 0;
		hasRolled = false;
		hasAStored6 = false;
		rules.setCanMoveAnotherPiece(false);
		ludoGameFrame.setEnableRollDieButton(true);
	}

	@Override
	public void onNewGameButtonClicked(ActionEvent event) {
		System.out.println("Ludo game - onNewGameButtonClicked");
		restartGame();
	}

	public void restartGame() {
		rules = rules.getResetGameRules();
		this.startGame();
		ludoGameFrame.setTurnColor(playerTurn);
		ludoGameFrame.setShowDieSide(false);
	}

	@Override
	public void onLoadGameButtonClicked(ActionEvent event, File file) {
		System.out.println("Ludo game - onLoadGameButtonClicked");
		System.out.println(event);
		System.out.println(file.getName());

		// retrieving fileGame
		FileGame fileGame = saveAndRestoreGame.loadGame(file);

		if (fileGame == null) {
			// error trying to read file
			return;
		}

		// setting file game
		pieces = fileGame.getPieces();
		playerTurn = fileGame.getPlayerTurn();
		rules.setBoardSpaces(fileGame.getBoardSpaces());
		rules.setLastMovedPieceArray(fileGame.getLastMovedPieceArray());
		rules.setCapturedPiece(fileGame.getCapturedPiece());
		rules.setCanMoveAnotherPiece(fileGame.getCanMoveAnotherPiece());
		turnsToFinishFirstRound = fileGame.getTurnsToFinishFirstRound();
		capturedPieceInStartPos = fileGame.getCapturedPieceInStartPos();

		ludoGameFrame.setTurnColor(playerTurn);

		ludoGameFrame.setNewPieces(pieces);
	}

	@Override
	public void onSaveGameClicked(ActionEvent event, File file) {
		System.out.println("Ludo game - onSaveGameClicked");
		System.out.println(event);
		System.out.println(file.getName());

		BoardSpace[] boardSpaces = rules.getBoardSpaces();
		// building file game
		Piece[] lastMovedPieceArray = rules.getLastMovedPieceArray();

		Piece capturedPiece = rules.getCapturedPiece();
		boolean canMoveAnotherPiece = rules.getCanMoveAnotherPiece();

		FileGame fileGame = new FileGame(file, pieces, playerTurn, lastMovedPieceArray, boardSpaces,
				turnsToFinishFirstRound, capturedPiece, canMoveAnotherPiece, capturedPieceInStartPos);
		// saving it
		saveAndRestoreGame.saveGame(fileGame);
	}

	@Override
	public void onRollDiceClicked(ActionEvent event, Integer fakeValue) {
		System.out.println("Ludo game - onRollDiceClicked");
		System.out.println("FakeValue: " + fakeValue);
		System.out.println(event);

		// Makes the button "rollDie" (ControlPanel) get a random number and display it
		// as an image in it's panel
		// If the player has already rolled the die on his turn, he may not roll again
		if (!hasRolled) {
			System.out.println("We still havent rolled");
			ludoGameFrame.setEnableRollDieButton(false);

			if (fakeValue == null) {
				roll = rules.rollDie();
			} else {
				// its a fake roll, use defined value
				roll = fakeValue;
			}
			hasRolled = true;

			ludoGameFrame.setDieSide(roll);
			ludoGameFrame.setShowDieSide(true);

			// On the first round, each player, on their turn, starts with a piece at the
			// first position
			if (turnsToFinishFirstRound > 0 && timesRolled6 < 1) {
				rules.checkCanMoveFromInitialSquare(playerTurn, pieces);
				rules.moveFromInitialSquare();
				turnsToFinishFirstRound--;
				ludoGameFrame.setNewPieces(pieces);
				System.out.println("Turns to finish the first round: " + turnsToFinishFirstRound);

				// Used when the player captured a piece on his last move during the first
				// round.
				if (rules.getCanMoveAnotherPiece()) {
					System.out.println("You can move another piece in the first round!");
					if (roll == 6) {
						hasAStored6 = true;
					}

					// We have to store the roll so we can use it after we use the bonus move
					storedRoll = roll;
					roll = 6;
					capturedInFirstRound = true;

					rules.setCanMoveAnotherPiece(false);

					// Redraws the board to show the new die value. When the player clicks on
					// another piece, he'll move 6 spaces
					ludoGameFrame.setDieSide(6);
					ludoGameFrame.setNewPieces(this.pieces);
				}
			}
			// If the player can't make any moves with their current roll, the game
			// automatically passes their turn
			if (rules.checkIfCanMakeAMove(playerTurn, this.pieces, roll)) {

				if (roll == 6) {
					timesRolled6++;

					if (timesRolled6 == 3) {
						rules.sendPieceToStart(rules.getLastMovedPiece(playerId));
						ludoGameFrame.setShowDieSide(false);
						ludoGameFrame.setNewPieces(this.pieces);
						drawNextRound(pieces);
					}
					if (rules.breakBarriers(pieces, playerTurn)) {
						hasRolled = false;
						hasAStored6 = false;

						ludoGameFrame.setEnableRollDieButton(true);
						ludoGameFrame.setNewPieces(this.pieces);

						hasUsedExtraMove = false;
					}
				} else if (roll == 5) {
					if (rules.checkCanMoveFromInitialSquare(playerTurn, pieces)) {
						rules.moveFromInitialSquare();
						System.out.println("roll == 5, has been moved to initial square");
						
						if(rules.getCanMoveAnotherPiece()) {
							ludoGameFrame.setShowDieSide(true);
							ludoGameFrame.setDieSide(6);
							ludoGameFrame.setNewPieces(this.pieces);
							
							capturedPieceInStartPos = true;
						}else {
							ludoGameFrame.setShowDieSide(false);
							drawNextRound(pieces);							
						}

					}
				}
			}
			// There are no possible moves with this roll. We'll pass the turn to the next
			// player.
			else {
				drawNextRound(this.pieces);
			}
		}
	}
}
