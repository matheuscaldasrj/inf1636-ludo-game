package layout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class LudoGameFrame extends JFrame {
	
	private final int WIDTH_DEFAULT=1200;
	private final int HEIGHT_DEFAULT=800;

	
	public LudoGameFrame() {		
		
		buildSplitPanel();
		
		setLayoutPositions();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
	}
	
	private void buildSplitPanel() {
		
		JPanel boardPanel = buildBoardPanel();
		JPanel controlPanel = buildControlPanel();
		
		JSplitPane splitPane = new JSplitPane();
        splitPane.setSize(WIDTH_DEFAULT, HEIGHT_DEFAULT);
        splitPane.setDividerSize(0);

        splitPane.setDividerLocation(WIDTH_DEFAULT*2/3);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setLeftComponent(boardPanel);
        splitPane.setRightComponent(controlPanel);
        
        this.add(splitPane);
	}
	
	
	private JPanel buildBoardPanel() {
		BoardPanel boardPanel = new BoardPanel();
		boardPanel.setSize(WIDTH_DEFAULT*2/3,HEIGHT_DEFAULT);
		boardPanel.setBackground(Color.WHITE);
		System.out.println("boardSize");
		System.out.println(boardPanel.getSize());
		return boardPanel;
	}
		
	private JPanel buildControlPanel() {
		ControlPanel controlPanel = new ControlPanel();
		controlPanel.setSize(WIDTH_DEFAULT,HEIGHT_DEFAULT);
		controlPanel.setBackground(Color.RED);		
		return controlPanel;
	}
	
	private void setLayoutPositions() {
		setSize(WIDTH_DEFAULT,HEIGHT_DEFAULT);
		Toolkit toolkit =Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		int sl=screenSize.width;
		int sa=screenSize.height;
		int x=sl/2-WIDTH_DEFAULT/2;
		int y=sa/2-HEIGHT_DEFAULT/2;
		setBounds(x,y,WIDTH_DEFAULT,HEIGHT_DEFAULT);
		
	}
	

}
