package drawing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import listeners.ControlEventListener;


// This is the panel which contains every button that controls the game

public class ControlPanel extends JPanel {
	
	private final List<ControlEventListener> controlEventListeners = new ArrayList<ControlEventListener>();
	
	JButton rollDieButton = new JButton ("Lançar dado");
	
	final JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	
	JButton newGameButton = new JButton ("Novo Jogo");
	JButton loadGameButton = new JButton ("Carregar jogo");
	JButton saveGameButton = new JButton ("Salvar Jogo");
	JLabel playerToPlay = new JLabel("À Jogar:", JLabel.CENTER);
	
	private Image dieSides[] = new Image[6];
	private int dieSide = 0;
	private Color turnColor;
	Rectangle2D.Float rect = new Rectangle2D.Float(130, 360, 140, 140);
	
	public ControlPanel() {
		this.setLayout(null);
		
		turnColor = Color.BLUE;
		

		addButtonsAndLabels();
		prepareActionButtons();
		
		loadDieImages();
	}
	
	private void loadDieImages() {
		// Loads all the images for the die's sides
		for(int i=0 ; i<6 ; i++) {
			try {
				dieSides[i] = ImageIO.read(new File("DieImages/Dado" + (i+1) + ".png"));
				
			} catch (IOException e) {
				System.out.println("Error while trying to open die image file");
				e.printStackTrace();
			}
		}	
	}
	
	private void addButtonsAndLabels() {
		newGameButton.setBounds(122, 50, 150, 50);
		this.add(newGameButton);
		loadGameButton.setBounds(122, 120, 150, 50);
		this.add(loadGameButton);
		saveGameButton.setBounds(122, 190, 150, 50);
		this.add(saveGameButton);
		playerToPlay.setFont(new Font("Serif", Font.BOLD, 25));
		playerToPlay.setBounds(122, 280, 150, 50);
		this.add(playerToPlay);
		
		rollDieButton.setBounds(122, 550, 150, 50);
		this.add(rollDieButton);
		
	}
	
	private void prepareActionButtons() {
		newGameButton.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent e)
			  {
			    notifyNewGameButtonListeners(e);
			  }
		});
		
		loadGameButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e)
				  {
				  	File fileSelected = showOpenFileDialog();
				  	if(fileSelected != null) {
				  		notifyLoadButtonListeners(e,fileSelected);				  		
				  	}
				  }
			});
		
		saveGameButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e)
				  {
				  	File fileSelected = showSaveFileDialog();
				  	if(fileSelected != null) {
				  		notifySaveButtonListeners(e,fileSelected);
				  	}
				  }
			});
		
		rollDieButton.addActionListener(new ActionListener(){
			  public void actionPerformed(ActionEvent e)
				  {
				    notifyRollDiceButtonListeners(e);
				  }
			});
		
		
		fileChooser.setFileFilter(new FileFilter() {
			   public String getDescription() {
			       return "Txt files (*.txt)";
			   }

			   public boolean accept(File f) {
			       if (f.isDirectory()) {
			           return true;
			       } else {
			           String filename = f.getName().toLowerCase();
			           return filename.endsWith(".txt") || filename.endsWith(".txt") ;
			       }
			   }
			});
	}
	
	private File showOpenFileDialog()  {
		int returnValue = fileChooser.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			return selectedFile;			    
		}
		
		return null;
	}
	
	private File showSaveFileDialog() {
		int returnValue = fileChooser.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = fileChooser.getSelectedFile();
			System.out.println(selectedFile.getAbsolutePath());
			return selectedFile;			    
		}
		
		return null;
	}
	
	// Draws the control panel
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);		
		Graphics2D g2 = (Graphics2D) graphics;

		g2.setColor(turnColor);
		g2.fill(rect);
		g2.drawImage(dieSides[dieSide], 150, 380, null);

	}

	// Gets the button that rolls the die
	public JButton getRollDieButton() {
		return rollDieButton;
	}

	// Sets which side of the die will be displayed by the paint component
	public void setDieSide(int dieSide) {
		this.dieSide = dieSide - 1;

		repaint();
	}

	public void setTurnColor(Color turnColor) {
		this.turnColor = turnColor;

		repaint();
	}

	private void notifyNewGameButtonListeners(ActionEvent objectClicked) {
		for (ControlEventListener listener : controlEventListeners) {
			listener.onNewGameButtonClicked(objectClicked);
		}
	}
	
	private void notifyLoadButtonListeners(ActionEvent event, File selectedFile) {
		for (ControlEventListener listener : controlEventListeners) {
			listener.onLoadGameButtonClicked(event, selectedFile);
		}
	}
	
	private void notifySaveButtonListeners(ActionEvent objectClicked, File selectedFile) {
		for (ControlEventListener listener : controlEventListeners) {
			listener.onSaveGameClicked(objectClicked, selectedFile);
		}
	}
	
	private void notifyRollDiceButtonListeners(ActionEvent objectClicked) {
		for (ControlEventListener listener : controlEventListeners) {
			listener.onRollDiceClicked(objectClicked);
		}
	}

	public void addControlListener(ControlEventListener listener) {
		controlEventListeners.add(listener);
	}

	public void removeControlListener(ControlEventListener listener) {
		controlEventListeners.remove(listener);
	}

}
