package projektv4;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Unit {
	private final int side;
	private Tile currentTile;
	protected BufferedImage img;
	
	protected int speed;
	protected int damage;
	protected int range;
	protected int health;
	protected int number;
	protected String name;
	protected String icon;
	
	public Unit(int side, Tile initialTile, String img_file) {
        this.side = side;
        this.currentTile = initialTile;
        img = getImage(img_file);
	}
	
	public BufferedImage getImage(String img_file) {
		BufferedImage img = null;
		try {
            
              img = ImageIO.read(getClass().getResourceAsStream(img_file));
            
          } catch (Exception e) {
            System.out.println("File not found: " + e.getMessage());
          }
		return img;
	}

	 public boolean move(Tile fin) {
	        Unit occup = fin.getOccupyingUnit();
	        
	        if (occup != null) {
	            if (occup.getSide() == this.side) return false;
	            else fin.capture(this);
	        }
	        
	        currentTile.removeUnit();
	        this.currentTile = fin;
	        currentTile.put(this);
	        return true;
	    }
	 

	    
	    public Tile getPosition() {
	        return currentTile;
	    }
	    
	    public void setPosition(Tile tile) {
	        this.currentTile = tile;
	    }
	    
	    public int getSide() {
	        return side;
	    }
	    
	    public Image getImage() {
	        return img;
	    }
	    
	    public String getIcon() {
	    	return icon;
	    }
	    
	    public int getSpeed() {
	    	return speed;
	    }
	    
	    public int getDamage() {
	    	return damage;
	    }
	    
	    public int getRange() {
	    	return range;
	    }
	    
	    public int getHealth() {
	    	return health;
	    }
	    
	    public void setHealth(int n) {
	    	this.health = n;
	    }
	    
	    public int getNumber() {
	    	return number;
	    }
	    
	    public String getName() {
	    	return name;
	    }
	    
	    public void draw(Graphics g) {
	        int x = currentTile.getX();
	        int y = currentTile.getY();
	        
	        g.drawImage(this.img, x, y, null);
	    }
	    
	    public List<Tile> getLegalMoves(Board b) {
	        LinkedList<Tile> legalMoves = new LinkedList<Tile>();
	        Tile[][] board = b.getTileArray();
	        
	        int x = this.getPosition().getXNum();
	        int y = this.getPosition().getYNum();

	        for(int i = speed;i>-(speed+1);i--) {
	        	for(int j = speed;j>-(speed+1);j--) {
	        		if (Math.abs(i)+Math.abs(j)<=speed) {
	        		if (i != 0 || j != 0) {
	        		try {
	        			if(board[x + j][y + i].isOccupied()==false) legalMoves.add(board[x + j][y + i]);
	        		}
	        		catch (ArrayIndexOutOfBoundsException e){
	        			continue;
	        		}
	        		}
	        		}
	        	}
	        }
	        
	        return legalMoves;
	    }
	    public List<Tile> getLegalAttacks(Board b) {
	        LinkedList<Tile> legalAttacks = new LinkedList<Tile>();
	        Tile[][] board = b.getTileArray();
	        
	        int x = this.getPosition().getXNum();
	        int y = this.getPosition().getYNum();

	        for(int i = range;i>-(range+1);i--) {
	        	for(int j = range;j>-(range+1);j--) {
	        		if (Math.abs(i)+Math.abs(j)<=range) {
	        		if (i != 0 || j != 0) {
	        		try {
	        			if(board[x + j][y + i].isOccupied()==true&&board[x + j][y + i].getOccupyingUnit().side!=board[x][y].getOccupyingUnit().side) legalAttacks.add(board[x + j][y + i]);
	        		}
	        		catch (ArrayIndexOutOfBoundsException e){
	        			continue;
	        		}
	        		}
	        		}
	        	}
	        }
	        
	        return legalAttacks;
	    }

	    
}
