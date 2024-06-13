package projektv4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener  {

	private final Tile[][] board;
    private final GameWindow gameWindow;
    
    public final LinkedList<Unit> pUnits;
    public final LinkedList<Unit> eUnits;
    public List<Tile> movable;
    
    private int actions=0;
    private int maxActions = 2;
    private boolean playerTurn = true;
    private boolean multiplayer = false;
    private File file;

    private Unit currUnit;
    private int currX;
    private int currY;
    
	FileReader fr = null;
	String linia = "";
	
	public Board(GameWindow g, boolean b, File f, int boardNumber) {
		multiplayer = b;
		file = f;
		this.gameWindow = g;
        board = new Tile[10][5];
        pUnits = new LinkedList<Unit>();
        eUnits = new LinkedList<Unit>();
        setLayout(new GridLayout(5, 10, 0, 0));

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 10; x++) {
            	if(boardNumber == 1){
            	board[x][y] = new Tile(this, x, y);
            	this.add(board[x][y]);
            	}
            	if(boardNumber == 2){
            	if((x!=5&&y!=4)||(x!=7&&y!=2)||(x!=2&&y!=1)){
            	board[x][y] = new Tile(this, x, y);
            	this.add(board[x][y]);
            	}
            	else board[x][y].setBackground(Color.RED);
            	}
            }
        }
        
        initializeUnits(file);
	}
	
	private void initializeUnits(File f) {
		if(f==null) {
		board[0][0].put(new Knight(1, board[0][0], "/pknight.png"));
		board[0][1].put(new Archer(1, board[0][1], "/parcher.png"));
		board[0][2].put(new Horseman(1, board[0][2], "/phorseman.png"));
		board[0][3].put(new Mage(1, board[0][3], "/pmage.png"));
		board[0][4].put(new Assasin(1, board[0][4], "/passasin.png"));
		board[9][0].put(new Knight(0, board[9][0], "/eknight.png"));
		board[9][1].put(new Archer(0, board[9][1], "/earcher.png"));
		board[9][2].put(new Horseman(0, board[9][2], "/ehorseman.png"));
		board[9][3].put(new Mage(0, board[9][3], "/emage.png"));
		board[9][4].put(new Assasin(0, board[9][4], "/eassasin.png"));
            for (int y = 0; y < 5; y++) {
                pUnits.add(board[0][y].getOccupyingUnit());
                eUnits.add(board[9][y].getOccupyingUnit());
            }
		}
		else {
			try {
				fr = new FileReader(file);
				} catch (FileNotFoundException e1) {
				System.out.println("BLAD PRZY OTWIERANIU PLIKU!");
				System.exit(1);
				}
			BufferedReader bfr = new BufferedReader(fr);
			try {
					bfr.readLine();
					linia = bfr.readLine();
					for(int i = 0;i< Integer.valueOf(linia);i++) {
					int n = Character.getNumericValue(bfr.read());
					bfr.read();
					int x = Character.getNumericValue(bfr.read());
					bfr.read();
					int y = Character.getNumericValue(bfr.read());
					bfr.read();
					switch(n) {
					case 1:
					board[x][y].put(new Knight(1, board[x][y], "/pknight.png"));
					break;
					case 2:
					board[x][y].put(new Archer(1, board[x][y], "/parcher.png"));
					break;
					case 3:
					board[x][y].put(new Horseman(1, board[x][y], "/phorseman.png"));
					break;
					case 4:
					board[x][y].put(new Mage(1, board[x][y], "/pmage.png"));
					break;
					case 5:
					board[x][y].put(new Assasin(1, board[x][y], "/passasin.png"));
					break;
					}
					}
					linia = bfr.readLine();
					for(int i = 0;i< Integer.valueOf(linia);i++) {
					int n = Character.getNumericValue(bfr.read());
					bfr.read();
					int x = Character.getNumericValue(bfr.read());
					bfr.read();
					int y = Character.getNumericValue(bfr.read());
					bfr.read();
					switch(n) {
					case 1:
					board[x][y].put(new Knight(0, board[x][y], "/eknight.png"));
					break;
					case 2:
					board[x][y].put(new Archer(0, board[x][y], "/earcher.png"));
					break;
					case 3:
					board[x][y].put(new Horseman(0, board[x][y], "/ehorseman.png"));
					break;
					case 4:
					board[x][y].put(new Mage(0, board[x][y], "/emage.png"));
					break;
					case 5:
					board[x][y].put(new Assasin(0, board[x][y], "/eassasin.png"));
					break;
					}
					//System.out.print(n+" "+x+" "+y+"\n");
					}
					for(int i = 0;i<10;i++) {
						for(int j = 0;j<5;j++) {
							if(board[i][j].isOccupied()) {
								Unit unit = board[i][j].getOccupyingUnit();
								if(unit.getSide()==1) {
									pUnits.add(unit);
								}
								else {
									eUnits.add(unit);
								}
							}
						}
					}
					linia = bfr.readLine();
					if(Boolean.valueOf(linia)==false) {
						playerTurn = false;
						if(multiplayer == true) gameWindow.setTurn("Tura 2 gracza");
						else {
							gameWindow.setTurn("Tura przeciwnika");
							aiTurn();
						}
					}
					linia = bfr.readLine();
					actions = Integer.valueOf(linia);
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
	}
	
	public Tile[][] getTileArray() {
        return this.board;
    }

    public boolean getTurn() {
        return playerTurn;
    }
    
    public int getActions() {
    	return actions;
    }

    public void setCurrUnit(Unit u) {
        this.currUnit = u;
    }

    public Unit getCurrUnit() {
        return this.currUnit;
    }
    
    public boolean getPlayerTurn() {
    	return playerTurn;
    }
    
    public void fight(Tile a, Tile b) {
    	//Unit first = a.getOccupyingUnit(), second = b.getOccupyingUnit();
    	b.getOccupyingUnit().setHealth(b.getOccupyingUnit().getHealth()-a.getOccupyingUnit().getDamage());
    	if(!multiplayer) {
    	if(a.getOccupyingUnit().getSide()==1) {
    		System.out.print("Twój "+a.getOccupyingUnit().getName()
    			+" zaatakował przeciwnego "+b.getOccupyingUnit().getName()+", zadając "+a.getOccupyingUnit().getDamage()
    			+" punktów obrażeń, ");
    		if(b.getOccupyingUnit().getHealth()<=0) System.out.print("cel ataku nie żyje\n");
    		else System.out.print("celowi ataku zostało "+b.getOccupyingUnit().getHealth()+" punktów życia\n");
    	}
    	else {
    		System.out.print("Przeciwny "+a.getOccupyingUnit().getName()+" zaatakował twojego "+b.getOccupyingUnit().getName()
    				+ ", zadając "+a.getOccupyingUnit().getDamage()+" punktów obrażeń, ");
    		if(b.getOccupyingUnit().getHealth()<=0) System.out.print("cel ataku nie żyje\n");
    		else System.out.print("celowi ataku zostało "+b.getOccupyingUnit().getHealth()+" punktów życia\n");
    	}
    	}
    	else {
        	if(a.getOccupyingUnit().getSide()==1) {
        		System.out.print(a.getOccupyingUnit().getName()+" gracza 1 zaatakował "+b.getOccupyingUnit().getName()+" gracza 2, zadając "+a.getOccupyingUnit().getDamage()
        			+" punktów obrażeń, ");
        		if(b.getOccupyingUnit().getHealth()<=0) System.out.print("cel ataku nie żyje\n");
        		else System.out.print("celowi ataku zostało "+b.getOccupyingUnit().getHealth()+" punktów życia\n");
        	}
        	else {
        		System.out.print(a.getOccupyingUnit().getName()+" gracza 2 zaatakował "+b.getOccupyingUnit().getName()+" gracza 1, zadając "+a.getOccupyingUnit().getDamage()
            			+" punktów obrażeń, ");
        		if(b.getOccupyingUnit().getHealth()<=0) System.out.print("cel ataku nie żyje\n");
        		else System.out.print("celowi ataku zostało "+b.getOccupyingUnit().getHealth()+" punktów życia\n");
        	}
    		
    	}
    }
    
    public void endGame(boolean b) {
    	if(multiplayer == false){
    	if(b) {
    		JOptionPane.showMessageDialog(gameWindow.getFrame(), "Zwycięstwo!","Koniec Gry",JOptionPane.INFORMATION_MESSAGE);
    	}
    	else JOptionPane.showMessageDialog(gameWindow.getFrame(), "Porażka!","Koniec Gry",JOptionPane.ERROR_MESSAGE);
    	}
    	else {
        	if(b) {
        		JOptionPane.showMessageDialog(gameWindow.getFrame(), "Zwycięstwo 1 gracza!","Koniec Gry",JOptionPane.INFORMATION_MESSAGE);
        	}
        	else JOptionPane.showMessageDialog(gameWindow.getFrame(), "Zwycięstwo 2 gracza!","Koniec Gry",JOptionPane.INFORMATION_MESSAGE);
    		
    	}
		SoundHandler.stopMusic();
    	new MainMenu();
    	gameWindow.getFrame().dispose();
    }
    
    public void pVanish() {
    	gameWindow.setVisibility(gameWindow.pStatsLabel1, false);
    	gameWindow.setVisibility(gameWindow.pStatsLabel2, false);
    	gameWindow.setVisibility(gameWindow.pStatsLabel3, false);
    	gameWindow.setVisibility(gameWindow.pStatsLabel4, false);
    	gameWindow.setVisibility(gameWindow.pInfoLabel, false);
    }
    
    public void pShow(Unit unit) {
    	gameWindow.setVisibility(gameWindow.pStatsLabel1, true);
    	gameWindow.setVisibility(gameWindow.pStatsLabel2, true);
    	gameWindow.setVisibility(gameWindow.pStatsLabel3, true);
    	gameWindow.setVisibility(gameWindow.pStatsLabel4, true);
    	gameWindow.setVisibility(gameWindow.pInfoLabel, true);
    	gameWindow.setText(gameWindow.pStatsLabel1, "Życie: "+unit.getHealth());
    	gameWindow.setText(gameWindow.pStatsLabel2, "Obrażenia: "+unit.getDamage());
    	gameWindow.setText(gameWindow.pStatsLabel3, "Prędkość: "+unit.getSpeed());
    	gameWindow.setText(gameWindow.pStatsLabel4, "Zasięg: "+unit.getRange());
    	gameWindow.setIcon(gameWindow.pInfoLabel, unit.getIcon());
    }
    
    public void eVanish() {
    	gameWindow.setVisibility(gameWindow.eStatsLabel1, false);
    	gameWindow.setVisibility(gameWindow.eStatsLabel2, false);
    	gameWindow.setVisibility(gameWindow.eStatsLabel3, false);
    	gameWindow.setVisibility(gameWindow.eStatsLabel4, false);
    	gameWindow.setVisibility(gameWindow.eInfoLabel, false);
    }
    
    public void eShow(Unit unit) {
    	gameWindow.setVisibility(gameWindow.eStatsLabel1, true);
    	gameWindow.setVisibility(gameWindow.eStatsLabel2, true);
    	gameWindow.setVisibility(gameWindow.eStatsLabel3, true);
    	gameWindow.setVisibility(gameWindow.eStatsLabel4, true);
    	gameWindow.setVisibility(gameWindow.eInfoLabel, true);
    	gameWindow.setText(gameWindow.eStatsLabel1, "Życie: "+unit.getHealth());
    	gameWindow.setText(gameWindow.eStatsLabel2, "Obrażenia: "+unit.getDamage());
    	gameWindow.setText(gameWindow.eStatsLabel3, "Prędkość: "+unit.getSpeed());
    	gameWindow.setText(gameWindow.eStatsLabel4, "Zasięg: "+unit.getRange());
    	gameWindow.setIcon(gameWindow.eInfoLabel, unit.getIcon());
    }
    
    public boolean aiTurn() {
    	
        List<Tile> legalMoves;
        List<Tile> legalAttacks;
        Tile tile;
        try {
        for(int i = 0;i<eUnits.size();i++) {
        	currUnit = eUnits.get(i);
        	legalAttacks = currUnit.getLegalAttacks(this);
        	if(!legalAttacks.isEmpty()) {
        		tile = legalAttacks.get(0);
        		fight(currUnit.getPosition(), tile);
            	if(tile.getOccupyingUnit().getHealth()<=0) {
            		if(currUnit.getSide()==1) {
            			eUnits.remove(tile.getOccupyingUnit());
            			tile.removeUnit();
            			if(eUnits.isEmpty()) endGame(true);
            		}
            		else {
            			pUnits.remove(tile.getOccupyingUnit());
            			tile.removeUnit();
            			if(pUnits.isEmpty()) endGame(false);
            		}
            	}
            	currUnit = null;
        		actions++;
        		i--;
                if(actions>=maxActions) {
                	playerTurn = !playerTurn;
                	actions = 0;
                	if(playerTurn == true) {
                		gameWindow.setTurn("Twoja tura");
                		return true;
                	}
                	else gameWindow.setTurn("Tura przeciwnika");
                }
        		
        	}
        }
        for(int i = 0;i<maxActions;i++) {
        	Random rand1 = new Random();
        	int a = rand1.nextInt(eUnits.size());
        	currUnit = eUnits.get(a);
        	legalMoves = currUnit.getLegalMoves(this);
        	if(!legalMoves.isEmpty()) {
				Random rand2 = new Random();
				int b = rand2.nextInt(legalMoves.size());
				tile = legalMoves.get(b);
				tile.setDisplay(true);
				currUnit.move(tile);
                currUnit = null;
                actions++;
                if(actions>=maxActions) {
                	playerTurn = !playerTurn;
                	actions = 0;
                	if(playerTurn == true) {
                		gameWindow.setTurn("Twoja tura");
                		return true;
                	}
                	else gameWindow.setTurn("Tura przeciwnika");
                }
        	}
        }
        }
        catch(Exception ex) {
        	return false;
        }
        repaint();
    	return true;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        //super.paintComponent(g);

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 10; x++) {
                Tile tile = board[x][y];
                tile.paintComponent(g);
            }
        }

        if (currUnit != null) {
            if ((currUnit.getSide() == 1 && playerTurn)
                    || (currUnit.getSide() == 0 && !playerTurn)) {
                final Image i = currUnit.getImage();
                g.drawImage(i, currX, currY, null);
                
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        currX = e.getX();
        currY = e.getY();

        Tile tile = (Tile) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (tile.isOccupied()) {
        	currUnit = tile.getOccupyingUnit();
        	
        	if(currUnit.getSide()==1) pShow(currUnit);
        	if(currUnit.getSide()==0) eShow(currUnit);

            if (currUnit.getSide() == 0 && playerTurn)
                return;
            if (currUnit.getSide() == 1 && !playerTurn)
                return;
            tile.setDisplay(false);
            
            List<Tile> legalMoves = currUnit.getLegalMoves(this);
            for(int i = 0;i<10;i++) {
            	for(int j = 0; j<5;j++) {
            		if(legalMoves.contains(board[i][j])) board[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
            	}
            }
            List<Tile> legalAttacks = currUnit.getLegalAttacks(this);
            for(int i = 0;i<10;i++) {
            	for(int j = 0; j<5;j++) {
            		if(legalAttacks.contains(board[i][j])) board[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            	}
            }
        }
        repaint();
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		currX = e.getX() - 25;
	    currY = e.getY() - 25;
	    
	    try {
	    Tile tile = (Tile) this.getComponentAt(new Point(e.getX(), e.getY()));
	    if(tile.isOccupied()) {
	    	if(tile.getOccupyingUnit().getSide()==0) eShow(tile.getOccupyingUnit());
	    	if(tile.getOccupyingUnit().getSide()==1) pShow(tile.getOccupyingUnit());
	    }
	    else {
	    	if(tile.getOccupyingUnit().getSide()==0) eVanish();
	    	if(tile.getOccupyingUnit().getSide()==1) pVanish();
	    }
	    }
	    catch(Exception ex) {
	    }
	    repaint();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		Tile tile = (Tile) this.getComponentAt(new Point(e.getX(), e.getY()));

        if (currUnit != null) {
            if (currUnit.getSide() == 0 && playerTurn)
                return;
            if (currUnit.getSide() == 1 && !playerTurn)
                return;

            List<Tile> legalMoves = currUnit.getLegalMoves(this);
            List<Tile> legalAttacks = currUnit.getLegalAttacks(this);
            
            for(int i = 0;i<10;i++) {
            	for(int j = 0; j<5;j++) {
            		board[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            	}
            }

            if (legalMoves.contains(tile)) {
            	tile.setDisplay(true);
                currUnit.move(tile);
                currUnit = null;
                pVanish();
                actions++;
            }
            else if(legalAttacks.contains(tile)) {
            	fight(currUnit.getPosition(),tile);
            	if(tile.getOccupyingUnit().getHealth()<=0) {
            		if(currUnit.getSide()==1) {
            			eUnits.remove(tile.getOccupyingUnit());
            			tile.removeUnit();
            			if(eUnits.isEmpty()) endGame(true);
            		}
            		else {
            			pUnits.remove(tile.getOccupyingUnit());
            			tile.removeUnit();
            			if(pUnits.isEmpty()) endGame(false);
            		}
            	}
            	currUnit.getPosition().setDisplay(true);
            	currUnit = null;
                pVanish();
        		actions++;
            }
             else {
            	currUnit.getPosition().setDisplay(true);
                pVanish();
            	currUnit = null;
            }
            if(actions>=maxActions) {
            	playerTurn = !playerTurn;
            	actions = 0;
            	if(multiplayer == false) {
            	if(playerTurn == true) gameWindow.setTurn("Twoja tura");
            	else {
            		gameWindow.setTurn("Tura przeciwnika");
                	aiTurn();
            	}
            	}
            	else {
                	if(playerTurn == true) gameWindow.setTurn("Tura 1 gracza");
                	else gameWindow.setTurn("Tura 2 gracza");
            	}
            	
            }
        }

        	

        repaint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		currX = e.getX();
	    currY = e.getY();
	    
	    Tile tile = (Tile) this.getComponentAt(new Point(e.getX(), e.getY()));
	    if(tile.isOccupied()) {
	    	if(tile.getOccupyingUnit().getSide()==0) eShow(tile.getOccupyingUnit());
	    	if(tile.getOccupyingUnit().getSide()==1) pShow(tile.getOccupyingUnit());
	    }
	    else {
	    	eVanish();
	    	pVanish();
	    }
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}



	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}
