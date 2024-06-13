package projektv4;

public class Assasin extends Unit {

	int speed = 4;
	int damage = 4;
	int range = 1;
	int health = 4;
	int number = 5;
	String icon;
	String name = "zabójca";
		
	public Assasin(int side, Tile initialTile, String img_file) {
		super(side, initialTile, img_file);
		super.speed = speed;
		super.damage = damage;
		super.range = range;
		super.health = health;
		super.number = number;
		if(super.getSide()==1)super.icon = "pAssasinIcon.png";
		else super.icon = "eAssasinIcon.png";
		super.name = name;
	}
}
