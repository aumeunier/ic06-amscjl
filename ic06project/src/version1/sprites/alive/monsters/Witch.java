package version1.sprites.alive.monsters;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;

import version1.sprites.Sprite;
import version1.sprites.alive.Creature;

/**
 * A Witch is a special <code>Monster</code>, it is the boss !
 * The Witch can fire fireball at different speed rates.
 *
 */
public class Witch extends Monster {
	// Rates, in ms
	private final static int FIREBALL_RATE_SLOW = 4000;
	private final static int FIREBALL_RATE_NORMAL = 2800;
	private final static int FIREBALL_RATE_FAST = 1000;
	
	/** 
	 * It takes time to prepare a fireball. Using this variable we can add preparation time at every step,
	 * this ensuring we won't 'lose' time. For example, if we are at 4999 and we add 7, we start again at 6.
	 */
	private int fireballPreparation = 0;
	private int currentPreparationTime = FIREBALL_RATE_SLOW;

	public Witch(int _x, int _y, int _w, int _h) {
		super(_x, _y, _w, _h);
		this.staticMonster = false;
		this.setHealth(5);
		this.setImage("dragon.png");
	}
	
	@Override
	/**
	 * The Witch has a different shooting speed depending on its health
	 */
	public void loseHealth(int nb){
		int goingLeft = (this.isGoingLeft())?-1:+1;
		super.loseHealth(nb);
		switch(this.health){
		case 4:
			currentPreparationTime = FIREBALL_RATE_FAST;
			this.setSpeed(new Vec2(goingLeft*5,0));
			setFilter(Color.gray);
			break;
		case 3:
			currentPreparationTime = FIREBALL_RATE_SLOW;
			this.setSpeed(new Vec2(goingLeft*20,0));
			setFilter(Color.yellow);
			break;
		case 2:
			currentPreparationTime = FIREBALL_RATE_NORMAL;
			this.setSpeed(new Vec2(goingLeft*10,0));
			setFilter(Color.orange);			
			break;
		case 1:
			currentPreparationTime = FIREBALL_RATE_FAST;
			this.setSpeed(new Vec2(goingLeft*20,0));
			setFilter(Color.red);
			break;
		default:
			currentPreparationTime = FIREBALL_RATE_NORMAL;
			this.setSpeed(new Vec2(goingLeft*10,0));
			break;
		}
	}

	@Override
	/**
	 * We had preparation time at every time step. If the preparation time is large enough, we cast a fireball.
	 */
	public void step(int delta){
		// Movements
		super.step(delta);
		
		// Prepare a fireball
		fireballPreparation+=delta;
		
		// Cast a fireball if possible
		if(fireballPreparation > currentPreparationTime){
			fireballPreparation=0;
			Vec2 target = target();			
			this.level.addFireball(x,y,target,this);
		}
		
	}
	
	/**
	 * Search for the closest target.
	 * @return The position of the closest target. This will be the aiming point.
	 */
	public Vec2 target(){
		Creature c1 = this.level.getFirstCharacter();
		Creature c2 = this.level.getSecondCharacter();
		
		Vec2 c1pos = new Vec2(c1.X()+c1.W()/2,c1.Y()+c1.H()/2);
		Vec2 c2pos = new Vec2(c2.X()+c1.W()/2,c2.Y()+c2.H()/2);
		Vec2 pos = new Vec2(x+w/2,y+h/2);
		float c1l = c1pos.sub(pos).length();
		float c2l = c2pos.sub(pos).length();
		Vec2 targetPos = (c1l > c2l)?c2pos:c1pos;
		float targetL = (c1l > c2l)?c1l:c2l;
		
		for(Sprite s:this.level.getSprites()){
			if(s instanceof Creature && !s.equals(this)){
				Vec2 spos = new Vec2(s.X()+s.W()/2,s.Y()+s.H()/2);
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
