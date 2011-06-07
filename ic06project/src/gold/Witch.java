package gold;

import org.jbox2d.common.Vec2;

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
			Vec2 target = target();			
			FireBall f = this.level.addFireball(x,y,target,this);
		}
		
	}
	
	// Retourne la position de la cible
	public Vec2 target(){
		Character c1 = this.level.character1;
		Character c2 = this.level.character2;
		Vec2 c1pos = new Vec2(c1.x+c1.w/2,c1.y+c1.h/2);
		Vec2 c2pos = new Vec2(c2.x+c1.w/2,c2.y+c2.h/2);
		Vec2 pos = new Vec2(x,y);
		Vec2 targetPos = (c1pos.sub(pos).length() > c2pos.sub(pos).length())?c2pos:c1pos;
		targetPos.subLocal(pos).normalize();
		targetPos.mulLocal(10000);
		return targetPos;
	}
}
