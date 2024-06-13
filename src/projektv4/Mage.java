package projektv4;

public class Mage extends Unit {
	
	int speed = 1;
	int damage = 3;
	int range = 3;
	int health = 6;
	int number = 4;
	String icon;
	String name = "mag";

	public Mage(int side, Tile initialTile, String img_file) {
		super(side, initialTile, img_file);
		super.speed = speed;
		super.damage = damage;
		super.range = range;
		super.health = health;
		super.number = number;
		if(super.getSide()==1)super.icon = "pMageIcon.png";
		else super.icon = "eMageIcon.png";
		super.name = name;
	}

}
