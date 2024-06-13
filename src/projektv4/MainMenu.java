package projektv4;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainMenu implements Runnable{
	
	private JFrame menuWindow;
	private JSlider volume;
	private JCheckBox music;
	
	File file;
	FileReader fr = null;
	String linia = "";
	String mainMenuMusic = "music/mainMenu.wav";


	public MainMenu() {
		// TODO Auto-generated constructor stub
		menuWindow = new JFrame("Main menu");
		menuWindow.setSize(800,500);
		menuWindow.setLocationRelativeTo(null);
		menuWindow.setResizable(false);
		menuWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		final JPanel menuPanel = new JPanel();
		menuPanel.setLayout(null);
		
		final JButton newGame = new JButton("Nowa gra");
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SoundHandler.stopMusic();
				new GameWindow(false, null, volume.getValue(), music.isSelected());
				menuWindow.dispose();
			}
		});
		newGame.setBounds(350, 100, 100, 25);
		menuPanel.add(newGame);
		
		final JButton multiplayer = new JButton("Wielu graczy");
		multiplayer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SoundHandler.stopMusic();
				new GameWindow(true, null, volume.getValue(), music.isSelected());
				menuWindow.dispose();
			}
		});
		multiplayer.setBounds(340, 140, 120, 23);
		menuPanel.add(multiplayer);
		
		final JButton loadGame = new JButton("Wczytaj grę");
		loadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fC = new JFileChooser();
				fC.setDialogTitle("Wybierz plik");
				int returnVal = fC.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            file = fC.getSelectedFile();
		            System.out.println("Wybrano plik: " +
		            		fC.getSelectedFile());
				}
				else {System.out.println("Nie wybrano pliku"); 
				}
				try {
					fr = new FileReader(file);
					} catch (FileNotFoundException e1) {
					System.out.println("BLAD PRZY OTWIERANIU PLIKU!");
					System.exit(1);
					}
				BufferedReader bfr = new BufferedReader(fr);
				try {
						linia = bfr.readLine();
						if(!Boolean.valueOf(linia)) {
							SoundHandler.stopMusic();
							new GameWindow(false, file, volume.getValue(), music.isSelected());
							menuWindow.dispose();
						}
						else {
							SoundHandler.stopMusic();
							new GameWindow(true, file, volume.getValue(), music.isSelected());
							menuWindow.dispose();
						}

					while((linia = bfr.readLine()) != null){


					}
					} catch (IOException e2) {
					System.out.println("BLAD ODCZYTU Z PLIKU!");
					System.exit(2);
					}
				try {fr.close();} catch (IOException e3) {
					System.out.println("BLAD PRZY ZAMYKANIU PLIKU!");
					System.exit(3);
					}
				
			}
		});
		loadGame.setBounds(340, 180, 120, 23);
		menuPanel.add(loadGame);
		
		final JButton instructions = new JButton("Jak grać?");
		instructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(menuWindow, "Rozgrywka polega na wykonywaniu akcji swoimi jednostkami, celem pokonania jednostek wroga.\n"
						+ "Akcja to ruch lub atak. W swojej turze gracz może wykonać 2 akcje, po czym następuje tura przeciwnika.\n"
						+ "Następnie przeciwnik wykonuje 2 akcje i ponownie następuje tura gracza. Proces jest powtarzany aż do\n"
						+ "momentu, gdy na polu bitwy pozostały tylko jednostki gracza - zwycięstwo lub tylko jednostki przeciwnika - porażka.\n\n"
						+ "W trybie Nowa Gra powstaje nowa gra w której przeciwnikiem jest komputer.\n"
						+ "W trybie Wielu Graczy powstaje nowa gra, w której przeciwnikiem jest drugi gracz.\n"
						+ "Wczytaj grę pozwala na wczytanie wcześniej zapisanej gry w jednym z powyższych trybów"
						+ "\ni kontynuowanie rozgrywki"
						,"Jak grać?",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		instructions.setBounds(340, 220, 120, 23);
	    menuPanel.add(instructions);
	    
		music = new JCheckBox("Muzyka");
		music.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(music.isSelected()==false) SoundHandler.pauseMusic();
				else {
					SoundHandler.resumeMusic();
				}
			}
		});
		music.setSelected(true);
		music.setHorizontalAlignment(JCheckBox.CENTER);
		music.setBounds(340, 260, 120, 23);
		music.setBackground(Color.WHITE);
		music.setOpaque(true);
	    menuPanel.add(music);
		
	    volume = new JSlider(-40, 0, -20);
	    volume.addChangeListener(new ChangeListener() {
	    	@Override
	    	public void stateChanged(ChangeEvent e) {
		    SoundHandler.changeVolume(volume.getValue());
	    }
	    });

	    volume.setBounds(300, 300, 200, 10);
		menuPanel.add(volume);
		
		final JButton credits = new JButton("Twórcy");
		credits.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(menuWindow, "Bohaterowie Miecza i Łuku\u2122\nCode: Oskar Stanisławczyk\nDesign: Oskar Stanisławczyk"
						+ "\nArtwork and graphics: Oskar Stanisławczyk\n"
						+ "Tracks:\n"
						+ "1. Gates Of Glory by Alexander Nakarada (CreatorChords)\n"
						+ "2. Battle Of The Creek by Alexander Nakarada (CreatorChords)\n"
						+ "| https://creatorchords.com\r\n"
						+ "Music promoted by https://www.free-stock-music.com\r\n"
						+ "Creative Commons / Attribution 4.0 International (CC BY 4.0)\r\n"
						+ "https://creativecommons.org/licenses/by/4.0/"
						,"Twórcy",JOptionPane.INFORMATION_MESSAGE);
			}
		});	
		credits.setBounds(340, 340, 120, 23);
		menuPanel.add(credits);
		
		final JButton exit = new JButton("Wyjscie");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		exit.setBounds(340, 380, 120, 23);
		menuPanel.add(exit);
		
		final JLabel menuBackground = new JLabel("");
		try {
		Image background = ImageIO.read(getClass().getResource("/mainMenu.png"));
		menuBackground.setIcon(new ImageIcon(background));
		menuBackground.setBounds(0, 0, 800, 500);
		menuPanel.add(menuBackground);
		} catch (Exception e) {
			System.out.println("menu.png is missing");
		}
		
		SoundHandler.playMusic(mainMenuMusic);
		SoundHandler.changeVolume(volume.getValue());
		
		menuWindow.add(menuPanel);
		menuWindow.setVisible(true);
	}
	
	public File getFile() {
		return file;
	}

	@Override
	public void run() {
	}

}
