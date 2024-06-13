package projektv4;

public class Knight extends Unit {
	
	int speed = 2;
	int damage = 3;
	int range = 1;
	int health = 10;
	int number = 1;
	String icon;
	String name = "rycerz";


	public Knight(int side, Tile initialTile, String img_file) {
		super(side, initialTile, img_file);
		super.speed = speed;
		super.damage = damage;
		super.range = range;
		super.health = health;
		super.number = number;
		if(super.getSide()==1)super.icon = "pKnightIcon.png";
		else super.icon = "eKnightIcon.png";
		super.name = name;
	}
}
