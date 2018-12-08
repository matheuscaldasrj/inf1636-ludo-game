package drawing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JPanel;

import listeners.BoardEventListener;
import models.BoardPosition;
import models.InitialSquare;
import models.Piece;
import models.PointPosition;

public class BoardPanel extends JPanel {

	private final List<BoardEventListener> boardListeners = new ArrayList<BoardEventListener>();

	private Graphics2D graphics2;
	private float initialSquareSize;
	private float centerSquareSize;
	private int boardSize;
	private float rectSide;

	private BoardPosition[] boardPositions = new BoardPosition[88];

	private List<Piece> pieces = new ArrayList<Piece>();

	public BoardPanel() {
		addMouseClickLister();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		graphics2 = (Graphics2D) g;

		keepAspectRatio();

		initialSquareSize = (float) (getSize().getWidth() * 2 / 5);

		paintBoardSquares();

	}

	private void addMouseClickLister() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				int eventX = event.getX();
				int eventY = event.getY();
				Object resultClickEvent = findClickPosition(eventX, eventY);
				// lets notify listeners
				notifyListeners(resultClickEvent);
			}
		});
	}

	private Object findClickPosition(int eventX, int eventY) {
		int boardPositionX;
		int boardPositionY;
		int indexInTheBoard = 0;
		boolean hasFound = false;

		for (BoardPosition boardPosition : boardPositions) {
			if(indexInTheBoard == 72) {
				break;
			}
			boardPositionX = (int) boardPosition.getX();
			boardPositionY = (int) boardPosition.getY();
			hasFound = eventX > boardPositionX && (eventX < (boardPositionX + rectSide)) && eventY > boardPositionY
					&& (eventY < (boardPositionY + rectSide));
			if (hasFound) {
				break;
			}

			indexInTheBoard++;
		}

		if (!hasFound) {
			// we havent found it yet, lets try in the inital squares
			float rectX = (float) (getSize().getWidth() - initialSquareSize);
			float rectY = (float) (getSize().getHeight() - initialSquareSize);

			if (eventX > 0 && eventX < initialSquareSize && eventY > 0 && eventY < initialSquareSize) {
				return InitialSquare.VERMELHO;
			} else if (eventX > rectX && eventX < (rectX + initialSquareSize) && eventY > 0
					&& eventY < initialSquareSize) {
				return InitialSquare.VERDE;
			} else if (eventX > 0 && eventX < initialSquareSize && eventY > rectY
					&& eventY < (initialSquareSize + rectY)) {
				return InitialSquare.AZUL;
			} else if (eventX > rectX && eventX < (rectX + initialSquareSize) && eventY > rectY
					&& eventY < (initialSquareSize + rectY)) {
				return InitialSquare.AMARELO;
			} else {
				// a click was detected but the place is unknown
				return null;
			}
		} else {
			// a board position was found, lets check if there is a piece
			List<Piece> piecesInThisBoardPosition = new ArrayList<>();
			for (Piece piece : pieces) {
				if (piece.getIndex() == indexInTheBoard) {
					piecesInThisBoardPosition.add(piece);
				}
			}
			if (piecesInThisBoardPosition.size() > 0) {
				return piecesInThisBoardPosition;
			} else {
				return null;
			}
		}
	}

	private void keepAspectRatio() {
		// lets keep aspect ratio
		if (getSize().getHeight() >= getSize().getWidth()) {
			boardSize = (int) getSize().getWidth();
		} else {
			boardSize = (int) getSize().getHeight();
		}

		setSize(boardSize, boardSize);
	}

	private void paintBoardSquares() {
		rectSide = (float) (getSize().getWidth() - 2 * initialSquareSize) / 3;

		// initialSquare
		paintInitialSquares();

		// center
		drawCenterRectangule();

		// team squares
		drawGreenTeam();
		drawBlueTeam();
		drawRedTeam();
		drawYellowTeam();

		// drawInitialCircles
		drawInitialCicles();

		// draw Pieces
		prepareDrawPieces();

	}

	private void prepareDrawPieces() {
		// first lets create a map
		// boardIndexPosition -> all pieces in that boardPosition

		Map<Integer, List<Piece>> pieceMap = new HashMap<Integer, List<Piece>>();
		for (Piece piece : pieces) {
			if (pieceMap.get(piece.getIndex()) == null) {
				// is null, lets create
				pieceMap.put(piece.getIndex(), new ArrayList<Piece>());
			}

			List<Piece> pieces = pieceMap.get(piece.getIndex());
			pieces.add(piece);

		}

		// now lets draw pieces
		for (Entry<Integer, List<Piece>> entry : pieceMap.entrySet()) {
			drawPieces(entry.getValue());
		}
	}

	private void drawInitialCicles() {
		
		drawCircleByInitialSquarePosition(0, 0, new RedBoardColorImpl() );
		drawCircleByInitialSquarePosition(initialSquareSize + centerSquareSize, 0, new GreenBoardColorImpl() );
		drawCircleByInitialSquarePosition(0, initialSquareSize + +centerSquareSize, new BlueBoardColorImpl());
		drawCircleByInitialSquarePosition(initialSquareSize + centerSquareSize, initialSquareSize + centerSquareSize,  new YellowBoardColorImpl());

	}

	void drawCircleByInitialSquarePosition(float poisitionX, float positionY, BoardColorInterface boardColor) {
		Ellipse2D.Double circle;
		float initialRadius = initialSquareSize / 7;
		int initialCircleBoardIndex = boardColor.getInitialCircleBoardIndex();
		double adjustX = boardColor.getAdjustInitialXCircle();
		double adjustY = boardColor.getAdjustInitialYCircle();
		
		graphics2.setColor(Color.WHITE);
		// first
		circle = new Ellipse2D.Double(poisitionX + initialSquareSize / 6, positionY + initialSquareSize / 5, initialRadius,
				initialRadius);
		graphics2.fill(circle);	
		boardPositions[initialCircleBoardIndex] = new BoardPosition(adjustX * (poisitionX + initialSquareSize / 6) - 2, adjustY * ( positionY + initialSquareSize / 5) - 2);
		initialCircleBoardIndex++;
		
		//second
		circle = new Ellipse2D.Double(poisitionX + 0.7 * initialSquareSize, positionY + initialSquareSize / 5, initialRadius,
				initialRadius);
		graphics2.fill(circle);
		boardPositions[initialCircleBoardIndex] = new BoardPosition(adjustX * (poisitionX + 0.7 * initialSquareSize),  adjustY * ( positionY + initialSquareSize / 5));
		initialCircleBoardIndex++;		

		
		//third
		circle = new Ellipse2D.Double(poisitionX + initialSquareSize / 6, positionY + 0.6 * initialSquareSize, initialRadius,
				initialRadius);
		graphics2.fill(circle);
		boardPositions[initialCircleBoardIndex] = new BoardPosition(adjustX * (poisitionX + initialSquareSize / 6) - 2,   adjustY * ( positionY + 0.6 * initialSquareSize) - 2);
		initialCircleBoardIndex++;		

		//fourth
		circle = new Ellipse2D.Double(poisitionX + 0.7 * initialSquareSize, positionY + 0.6 * initialSquareSize, initialRadius,
				initialRadius);
		graphics2.fill(circle);
		boardPositions[initialCircleBoardIndex] = new BoardPosition(adjustX * (poisitionX + 0.7 * initialSquareSize),  adjustY *( positionY + 0.6 * initialSquareSize));	

		
		// back to black
		graphics2.setColor(Color.BLACK);
	}

	private void drawYellowTeam() {
		drawGameSquares(2 * initialSquareSize + centerSquareSize - rectSide, initialSquareSize + centerSquareSize,
				new YellowBoardColorImpl());
	}

	private void drawRedTeam() {
		drawGameSquares(0, initialSquareSize - rectSide, new RedBoardColorImpl());
	}

	private void drawGreenTeam() {
		drawGameSquares(initialSquareSize + 3 * rectSide, 0, new GreenBoardColorImpl());
	}

	private void drawBlueTeam() {
		drawGameSquares(initialSquareSize - rectSide, boardSize - rectSide, new BlueBoardColorImpl());
	}

	private void drawGameSquares(float xPosition, float yPosition, BoardColorInterface boardColor) {
		graphics2.setPaint(Color.BLACK);
		Rectangle2D.Float rect;

		for (int i = 0; i < 18; i++) {
			PointPosition pointPosition = boardColor.getNextSquarePosition(i, rectSide, xPosition, yPosition);
			xPosition = pointPosition.getX();
			yPosition = pointPosition.getY();

			int boardIndex = boardColor.getBoardIndexByBlockIndex(i);
			boardPositions[boardIndex] = new BoardPosition(xPosition, yPosition);

			rect = new Rectangle2D.Float(xPosition, yPosition, rectSide, rectSide);
			rect = boardColor.fillRectColor(graphics2, rect, i);
			graphics2.draw(rect);

			if (i == 3) {
				// if we are in index 3 we should draw initial triangle
				graphics2.setColor(Color.WHITE);
				Polygon polygon = boardColor.getInitialTriangule((int) xPosition, (int) yPosition, rectSide);
				graphics2.draw(polygon);

				graphics2.fill(polygon);
				graphics2.setColor(Color.BLACK);

			}

		}
	}

	private void drawCenterRectangule() {
		centerSquareSize = (float) (getSize().getHeight() - 2 * initialSquareSize);
		Rectangle2D.Float rect = new Rectangle2D.Float(initialSquareSize, initialSquareSize, centerSquareSize,
				centerSquareSize);
		graphics2.setColor(Color.BLACK);
		graphics2.fill(rect);
		graphics2.draw(rect);

		drawCenterTriangles((int) centerSquareSize);

	}

	private void drawCenterTriangles(int centerSquareSize) {
		// now lets draw triangules
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		Polygon triangle;

		// red triangle
		xPoints[0] = (int) initialSquareSize;
		yPoints[0] = (int) initialSquareSize;
		xPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		yPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		xPoints[2] = (int) (initialSquareSize);
		yPoints[2] = (int) (initialSquareSize + centerSquareSize);
		triangle = new Polygon(xPoints, yPoints, 3);
		drawCenterTriangle(triangle, new RedBoardColorImpl().getColor());

		// green triangle
		xPoints[0] = (int) initialSquareSize;
		yPoints[0] = (int) initialSquareSize;
		xPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		yPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		xPoints[2] = (int) (initialSquareSize + centerSquareSize);
		yPoints[2] = (int) (initialSquareSize);
		triangle = new Polygon(xPoints, yPoints, 3);
		drawCenterTriangle(triangle, new GreenBoardColorImpl().getColor());

		// yellow triangle
		xPoints[0] = (int) initialSquareSize + centerSquareSize;
		yPoints[0] = (int) initialSquareSize;
		xPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		yPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		xPoints[2] = (int) (initialSquareSize + centerSquareSize);
		yPoints[2] = (int) (initialSquareSize + centerSquareSize);
		triangle = new Polygon(xPoints, yPoints, 3);
		drawCenterTriangle(triangle, new YellowBoardColorImpl().getColor());

		// blue triangle
		xPoints[0] = (int) initialSquareSize;
		yPoints[0] = (int) initialSquareSize + centerSquareSize;
		xPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		yPoints[1] = (int) (initialSquareSize + 0.5 * centerSquareSize);
		xPoints[2] = (int) (initialSquareSize + centerSquareSize);
		yPoints[2] = (int) (initialSquareSize + centerSquareSize);
		triangle = new Polygon(xPoints, yPoints, 3);
		drawCenterTriangle(triangle, new BlueBoardColorImpl().getColor());

	}

	private void drawCenterTriangle(Polygon polygon, Color color) {
		graphics2.setColor(color);
		graphics2.draw(polygon);
		;
		graphics2.fill(polygon);
		// back to white
		graphics2.setColor(Color.WHITE);
	}

	private void paintInitialSquares() {

		float rectX = (float) (getSize().getWidth() - initialSquareSize);
		float rectY = (float) (getSize().getHeight() - initialSquareSize);
		
		//red
		Rectangle2D.Float rect1 = new Rectangle2D.Float(0, 0, initialSquareSize, initialSquareSize);
		graphics2.setColor((new RedBoardColorImpl().getColor()));
		graphics2.fill(rect1);

		//green
		Rectangle2D.Float rect2 = new Rectangle2D.Float(rectX, 0, initialSquareSize, initialSquareSize);
		graphics2.setColor((new GreenBoardColorImpl().getColor()));
		graphics2.fill(rect2);

		//blue
		Rectangle2D.Float rect3 = new Rectangle2D.Float(0, rectY, initialSquareSize, initialSquareSize);
		graphics2.setColor(new BlueBoardColorImpl().getColor());
		graphics2.fill(rect3);

		//yellow
		Rectangle2D.Float rect4 = new Rectangle2D.Float(rectX, rectY, initialSquareSize, initialSquareSize);
		graphics2.setColor((new YellowBoardColorImpl().getColor()));
		graphics2.fill(rect4);
	}

	private void drawPieces(List<Piece> pieces) {
		float radius = initialSquareSize / 10;
		BoardPosition boardPosition;
		Ellipse2D.Double circle;

		if (pieces.size() > 2) {
			System.err.println("Recebeu mais de duas pecas na mesma posicao. Erro para desenhar");
			return;
		}

		if (pieces.size() == 1) {
			Piece piece = pieces.get(0);
			if(piece.getHasFinished()) {
				return ;
			}
			boardPosition = boardPositions[piece.getIndex()];
			circle = new Ellipse2D.Double(boardPosition.getX() + initialSquareSize / 30,
					boardPosition.getY() + initialSquareSize / 30, radius, radius);
			
			//only to draw a 'border'
			//to avoid cases when for example
			// blue piece is in a blue board position
			graphics2.setColor(Color.WHITE);
			graphics2.draw(circle);
			
			graphics2.setColor(piece.getColor());
			graphics2.fill(circle);
		} else {
			// MUST BE ONLY 2 CASES

			Piece piece1 = pieces.get(0);
			Piece piece2 = pieces.get(1);
			boardPosition = boardPositions[piece1.getIndex()];

			if (piece1.getColor() == piece2.getColor()) {
				// same board position
				// same color
				
				if(!piece1.getHasFinished()) {
					///piece 1
					circle = new Ellipse2D.Double(boardPosition.getX() + initialSquareSize / 30,
							boardPosition.getY() + initialSquareSize / 30, radius, radius);
					
					//only to draw a 'border'
					//to avoid cases when for example
					// blue piece is in a blue board position
					graphics2.setStroke(new BasicStroke(6));
					graphics2.setColor(Color.WHITE);
					graphics2.draw(circle);
					
					graphics2.setColor(piece1.getColor());
					graphics2.fill(circle);
				}
			
				
				if(!piece2.getHasFinished()) {
					Ellipse2D.Double internCircle = new Ellipse2D.Double(
							boardPosition.getX() + initialSquareSize / 30 + radius / 4,
							boardPosition.getY() + initialSquareSize / 30 + radius / 4, radius / 2, radius / 2);
					
					Stroke currentStroke = graphics2.getStroke();
					graphics2.setStroke(new BasicStroke(6));
					graphics2.setColor(Color.WHITE);
					graphics2.draw(internCircle);
					
					graphics2.setColor(piece2.getColor());
					graphics2.fill(internCircle);
					
					graphics2.setStroke(currentStroke);
				}
				
			
			} else {
				// same board position
				// different color
				if(!piece1.getHasFinished()) {
					graphics2.setColor(piece1.getColor());
					circle = new Ellipse2D.Double(boardPosition.getX() + initialSquareSize / 30,
							boardPosition.getY() + initialSquareSize / 30, radius, radius);
					graphics2.fill(circle);
				}

				if(!piece2.getHasFinished()) { 
					Ellipse2D.Double internCircle = new Ellipse2D.Double(
							boardPosition.getX() + initialSquareSize / 30 + radius / 4,
							boardPosition.getY() + initialSquareSize / 30 + radius / 4, radius / 2, radius / 2);
					graphics2.setColor(piece2.getColor());
					graphics2.fill(internCircle);
				}
				

			}

		}

	}

	public void setNewPieces(List<Piece> pieces) {
		this.pieces = pieces;

		// when setting new pieces, lets repaint all board
		repaint();
	}

	private void notifyListeners(Object objectClicked) {
		boolean isPiece = false;
		for (BoardEventListener listener : boardListeners) {
			if (objectClicked instanceof List) {
				isPiece = true;
			}
			listener.notifyBoardClicks(objectClicked, isPiece);
		}
	}

	public void addBoardListener(BoardEventListener listener) {
		boardListeners.add(listener);
	}

	public void removeBoardListener(BoardEventListener listener) {
		boardListeners.remove(listener);
	}

}
