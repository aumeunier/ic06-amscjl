package gold;

import org.jbox2d.common.Vec2;

public class Witch extends Monster {
	private final static boolean ISDEBUG = false;
	private final static int FIREBALL_RATE_SLOW = 5000;
	private final static int FIREBALL_RATE_NORMAL = 3000;
	private final static int FIREBALL_RATE_FAST = 1000;
	private int fireballPreparation = 0;
	private int currentPreparationTime = FIREBALL_RATE_SLOW;

	public Witch(int _x, int _y, int _w, int _h) {
		super(_x, _y, _w, _h);
		this.staticMonster = false;
		this.setHealth(5);
		this.setImage("dragon.png");
	}
	
	@Override
	public void loseHealth(int nb){
		super.loseHealth(nb);
		switch(this.health){
		case 4:
			currentPreparationTime = FIREBALL_RATE_NORMAL;
			break;
		case 3:
			currentPreparationTime = FIREBALL_RATE_SLOW;
			break;
		case 2:
			currentPreparationTime = FIREBALL_RATE_NORMAL;
			break;
		case 1:
			currentPreparationTime = FIREBALL_RATE_FAST;
			break;
		default:
			currentPreparationTime = FIREBALL_RATE_NORMAL;
			break;
		}
	}

	@Override
	public void step(int delta){
		// Movements
		super.step(delta);
		
		// Prepare a fireball
		fireballPreparation+=delta;
		
		// Cast a fireball if possible
		if(fireballPreparation > currentPreparationTime){
			fireballPreparation=0;
			Vec2 target = target();			
			if(!ISDEBUG){
				this.level.addFireball(x,y,target,this);
			}
		}
		
	}
	
	// Retourne la position de la cible
	public Vec2 target(){
		Character c1 = this.level.character1;
		Character c2 = this.level.character2;
		
		Vec2 c1pos = new Vec2(c1.x+c1.w/2,c1.y+c1.h/2);
		Vec2 c2pos = new Vec2(c2.x+c1.w/2,c2.y+c2.h/2);
		Vec2 pos = new Vec2(x+w/2,y+h/2);
		float c1l = c1pos.sub(pos).length();
		float c2l = c2pos.sub(pos).length();
		Vec2 targetPos = (c1l > c2l)?c2pos:c1pos;
		float targetL = (c1l > c2l)?c1l:c2l;
		
		for(Sprite s:this.level.sprites){
			if(s instanceof Character && !s.equals(this)){
				Vec2 spos = new Vec2(s.x+s.w/2,s.y+s.h/2);
				if(spos.sub(pos).length() < targetL){
					targetL = spos.sub(pos).length();
					targetPos = spos;
				}
			}
		}
		
		targetPos.subLocal(pos).normalize();
		targetPos.mulLocal(10000);
		return targetPos;
	}
}
