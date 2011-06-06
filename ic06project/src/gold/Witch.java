package gold;

public class Witch extends Monster {
	private final static int FIREBALL_RATE = 3000;
	private int fireballPreparation = 0;

	public Witch(int _x, int _y, int _w, int _h) {
		super(_x, _y, _w, _h);
		this.staticMonster = false;
		this.setHealth(5);
	}

	@Override
	public void step(int delta){
		// Movements
		super.step(delta);
		
		// Prepare a fireball
		fireballPreparation+=delta;
		
		// Cast a fireball if possible
		if(fireballPreparation > FIREBALL_RATE){
			fireballPreparation-=FIREBALL_RATE;
		}
		
	}
}
