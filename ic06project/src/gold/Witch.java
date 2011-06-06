package gold;

public class Witch extends Monster {

	public Witch(int _x, int _y, int _w, int _h) {
		super(_x, _y, _w, _h);
		this.staticMonster = false;
		this.setHealth(5);
	}

}
