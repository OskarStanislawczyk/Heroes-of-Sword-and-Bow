package projektv4;

public class Horseman extends Unit {
	
	int speed = 3;
	int damage = 3;
	int range = 1;
	int health = 8;
	int number = 3;
	String icon;
	String name = "jeździec";

	public Horseman(int side, Tile initialTile, String img_file) {
		super(side, initialTile, img_file);
		super.speed = speed;
		super.damage = damage;
		super.range = range;
		super.health = health;
		super.number = number;
		if(super.getSide()==1)super.icon = "pHorsemanIcon.png";
		else super.icon = "eHorsemanIcon.png";
		super.name = name;
	}
}
