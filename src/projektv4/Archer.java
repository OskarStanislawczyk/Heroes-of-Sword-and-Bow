package projektv4;

public class Archer extends Unit {
	
	int speed = 1;
	int damage = 2;
	int range = 4;
	int health = 4;
	int number = 2;
	String icon;
	String name = "łucznik";

	public Archer(int side, Tile initialTile, String img_file) {
		super(side, initialTile, img_file);
		super.speed = speed;
		super.damage = damage;
		super.range = range;
		super.health = health;
		super.number = number;
		if(super.getSide()==1)super.icon = "pArcherIcon.png";
		else super.icon = "eArcherIcon.png";
		super.name = name;
	}
}
