package projektv4;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class GameWindow {
	
	private JFrame gameWindow;
	private Board board;
	private JLabel turn;
	private JSlider volume;
	//private JTextArea textArea;
	
	public JPanel infoPanel;
	static final Color color = new Color(255,255,255,223);
	
	public JLabel pInfoLabel;
	public JLabel eInfoLabel;
	public JLabel pStatsLabel1;
	public JLabel pStatsLabel2;
	public JLabel pStatsLabel3;
	public JLabel pStatsLabel4;
	public JLabel eStatsLabel1;
	public JLabel eStatsLabel2;
	public JLabel eStatsLabel3;
	public JLabel eStatsLabel4;
	String gameWindowMusic = "music/gameWindow.wav";
	
	
	FileWriter fw = null;
	File file2;
	
	public GameWindow(boolean multiplayer, File file, int musicVolume, boolean isMusic) {
		if(!multiplayer) gameWindow = new JFrame("New Game");
		else gameWindow = new JFrame("Multiplayer Game");
		gameWindow.setSize(800,500);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setResizable(false);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		
		final JPanel gamePanel = new JPanel();
		gamePanel.setLayout(null);
		if(!multiplayer) turn = new JLabel("Twoja tura");
		else turn = new JLabel("Tura 1 gracza");
		
		this.board = new Board(this, multiplayer, file, 1);
		board.setBounds(150,200,500,250);
		board.setOpaque(false);
		gameWindow.add(board);
		
		//textArea = new JTextArea();
		//JScrollPane scrollPane = new JScrollPane(textArea);
		//scrollPane.setBounds(20, 70, 110, 330);
		//scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		//gamePanel.add(scrollPane);
		
		turn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		turn.setHorizontalAlignment(SwingConstants.CENTER);
		turn.setBounds(300, 18, 200, 26);
		turn.setOpaque(true);
		turn.setBackground(Color.WHITE);
		gamePanel.add(turn);
		
		infoPanel = new JPanel();
		infoPanel.setLayout(null);
		infoPanel.setBounds(150, 70, 500, 100);
		infoPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
		
		pInfoLabel = new JLabel();
		pInfoLabel.setBounds(3,3,94,94);
		pInfoLabel.setOpaque(true);
		pInfoLabel.setVisible(false);
		infoPanel.add(pInfoLabel);
		
		eInfoLabel = new JLabel("");
		eInfoLabel.setBounds(403,3,94,94);
		eInfoLabel.setOpaque(true);
		eInfoLabel.setVisible(false);
		infoPanel.add(eInfoLabel);
		
		pStatsLabel1 = new JLabel();
		pStatsLabel1.setBounds(110,6,110,20);
		pStatsLabel1.setOpaque(true);
		pStatsLabel1.setVisible(false);
		infoPanel.add(pStatsLabel1);
		
		pStatsLabel2 = new JLabel();
		pStatsLabel2.setBounds(110,28,110,20);
		pStatsLabel2.setOpaque(true);
		pStatsLabel2.setVisible(false);
		infoPanel.add(pStatsLabel2);
		
		pStatsLabel3 = new JLabel();
		pStatsLabel3.setBounds(110,50,110,20);
		pStatsLabel3.setOpaque(true);
		pStatsLabel3.setVisible(false);
		infoPanel.add(pStatsLabel3);
		
		pStatsLabel4 = new JLabel();
		pStatsLabel4.setBounds(110,72,110,20);
		pStatsLabel4.setOpaque(true);
		pStatsLabel4.setVisible(false);
		infoPanel.add(pStatsLabel4);
		
		eStatsLabel1 = new JLabel();
		eStatsLabel1.setBounds(280,6,110,20);
		eStatsLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
		eStatsLabel1.setOpaque(true);
		eStatsLabel1.setVisible(false);
		infoPanel.add(eStatsLabel1);
		
		eStatsLabel2 = new JLabel();
		eStatsLabel2.setBounds(280,28,110,20);
		eStatsLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
		eStatsLabel2.setOpaque(true);
		eStatsLabel2.setVisible(false);
		infoPanel.add(eStatsLabel2);
		
		eStatsLabel3 = new JLabel();
		eStatsLabel3.setBounds(280,50,110,20);
		eStatsLabel3.setHorizontalAlignment(SwingConstants.RIGHT);
		eStatsLabel3.setOpaque(true);
		eStatsLabel3.setVisible(false);
		infoPanel.add(eStatsLabel3);
		
		eStatsLabel4 = new JLabel();
		eStatsLabel4.setBounds(280,72,110,20);
		eStatsLabel4.setHorizontalAlignment(SwingConstants.RIGHT);
		eStatsLabel4.setOpaque(true);
		eStatsLabel4.setVisible(false);
		infoPanel.add(eStatsLabel4);
		
		JLabel versusLabel = new JLabel("vs");
		versusLabel.setHorizontalAlignment(SwingConstants.CENTER);
		versusLabel.setBounds(230,40,40,20);
		versusLabel.setOpaque(true);
		infoPanel.add(versusLabel);
		
		gamePanel.add(infoPanel);
		
		final JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(689, 22, 50, 22);
		gamePanel.add(menuBar);
		
		final JMenu menu = new JMenu("menu");
		menuBar.add(menu);
		
		final JCheckBox music = new JCheckBox("muzyka");
		music.setSelected(isMusic);
		music.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(music.isSelected()==false) SoundHandler.pauseMusic();
				else {
					SoundHandler.resumeMusic();
				}
			}
		});
		menu.add(music);
		
		volume = new JSlider(-40,0,musicVolume);
	    volume.addChangeListener(new ChangeListener() {
	    	@Override
	    	public void stateChanged(ChangeEvent e) {
		    SoundHandler.changeVolume(volume.getValue());
	    }
	    });
		menu.add(volume);
		
		final JMenuItem save = new JMenuItem("zapisz");
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fC = new JFileChooser();
				fC.setDialogTitle("Wybierz plik");
				int returnVal = fC.showSaveDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            file2 = fC.getSelectedFile();
				}
				try {
					fw = new FileWriter(file2);
					BufferedWriter bw = new BufferedWriter(fw);
					int size = board.pUnits.size();
					bw.write(String.valueOf(multiplayer)+"\n");

					bw.write(size+"\n");
					for(int i = 0;i<size;i++) {
						bw.write(board.pUnits.get(i).getNumber()+" "+board.pUnits.get(i).getPosition().getXNum()+" "+board.pUnits.get(i).getPosition().getYNum()+"\n");
					}
					size = board.eUnits.size();
					bw.write(size+"\n");
					for(int i = 0;i<size;i++) {
						bw.write(board.eUnits.get(i).getNumber()+" "+board.eUnits.get(i).getPosition().getXNum()+" "+board.eUnits.get(i).getPosition().getYNum()+"\n");
					}
					bw.write(String.valueOf(board.getPlayerTurn())+"\n");
					bw.write(String.valueOf(board.getActions()));
					bw.close();
				} catch (IOException e4) {
					e4.printStackTrace();
				}
			}
		});
		menu.add(save);
		
		final JMenuItem exit = new JMenuItem("wyjdź z gry");
		exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}
		});
		menu.add(exit);
		
		final JMenuItem toMenu = new JMenuItem("wyjdź do menu głównego");
		toMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				SoundHandler.stopMusic();
				new MainMenu();
				gameWindow.dispose();
			}
		});
		menu.add(toMenu);
		
		final JLabel gameBackground = new JLabel("");
		try {
			Image background = ImageIO.read(getClass().getResource("/battlefield.png"));
			gameBackground.setIcon(new ImageIcon(background));
			gameBackground.setBounds(0, 0, 800, 500);
			gamePanel.add(gameBackground);
			} catch (Exception e) {
				System.out.println("battlefield.png is missing");
			}
		

		SoundHandler.playMusic(gameWindowMusic);
		SoundHandler.changeVolume(volume.getValue());
		if(!music.isSelected()){
			SoundHandler.pauseMusic();
		}

		gameWindow.add(gamePanel);
		gameWindow.setVisible(true);	
		
	}
	public void setTurn(String g) {
		turn.setText(g);
	}
	
	public void setText(JLabel a, String g) {
		a.setText(g);
	}
	
	public void setVisibility(JLabel a, boolean b) {
		a.setVisible(b);
	}
	
	public void setIcon(JLabel a, String g) {
		try {
			Image icon = ImageIO.read(getClass().getResource("/"+g));
			a.setIcon(new ImageIcon(icon));
		} catch(Exception e) {
			//System.out.println(g+" is missing");
		}
	}
	
	public JFrame getFrame() {
		return gameWindow;
	}

}
