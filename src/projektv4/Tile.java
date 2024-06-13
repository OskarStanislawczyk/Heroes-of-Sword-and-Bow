package projektv4;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Tile extends JComponent {

	private Board b;
    
    private Unit occupyingUnit;
    private boolean displayUnit;
    
    private int xNum;
    private int yNum;
    
	public Tile(Board b, int xNum, int yNum) {
		this.b = b;
        this.displayUnit = true;
        this.xNum = xNum;
        this.yNum = yNum;
        
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
	}
	public Unit getOccupyingUnit() {
        return occupyingUnit;
    }
	
    public boolean isOccupied() {
        return (this.occupyingUnit != null);
    }
    
    public int getXNum() {
        return xNum;
    }
    
    public int getYNum() {
        return yNum;
    }
    
    public void setDisplay(boolean v) {
        this.displayUnit = v;
    }
    
    public void put(Unit u) {
        this.occupyingUnit = u;
        u.setPosition(this);
    }
    
    public void removeUnit() {
        this.occupyingUnit = null;
    }
    
    public void capture(Unit u) {
        Unit k = getOccupyingUnit();
        if (k.getSide() == 0) b.pUnits.remove(k);
        if (k.getSide() == 1) b.eUnits.remove(k);
        this.occupyingUnit = u;
    }
    
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);     
        if(occupyingUnit != null && displayUnit) {
        	occupyingUnit.draw(g);
        }
    }
    
}
