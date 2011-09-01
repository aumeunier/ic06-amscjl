package version1.sprites.interaction.projectiles;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import version1.sprites.Sprite;
import version1.sprites.alive.Creature;


/**
 * A Fireball is a projectile shot by monsters. It can hit other creatures.
 *
 */
public class FireBall extends Sprite {
	protected float x, y, w, h;
	/** The creature who shot the fireball */
	private Sprite owner;
	/** The strength of the fireball */
	private int powerPerHit = 1;
	/** Number of hits it has already done */
	private int nbHits = 0;
	/** Number of maximum creatures it can hit */
	private int maxNbHits = 1;
	/** Speed per tick */
	private float speedValue = 0.0001f;
	/** Direction of the fireball */
	protected Vec2 speed = new Vec2(0.0f,0.0f);
	/** Target point of the fireball */
	protected Vec2 target = new Vec2(0.0f,0.0f);
	/** Fireball moves only every few ticks, in order to take decimals into account */
	private int deltaCumulated = 0;
	private final int DELTA_CUMULATED_MAX = 5;
	
	public FireBall(float x, float y, Vec2 _speed, Sprite _owner){
		this.x = x;
		this.y = y;
		this.w = 10;
		this.h = 10;
		this.owner = _owner;
		this.speed = _speed;
		this.setImage("fireball.png");
	}
	
	/**
	 * The shooter of the fireball can't be hurt by its own fireball.
	 * @param other The creature who's been hit by the fireball
	 * @return Whether the creature is actually hit (hurt) by the fireball
	 */
	public boolean canHitCharacter(Creature other){
		return !(other.equals(owner));
	}
	/**
	 * Called when a creature has been hit by this fireball. Doesn't necessarily have to actually hurt him.
	 * @param other The creature who's been hit
	 * @return whether it has been hit
	 */
	public boolean hurtCharacter(Creature other){
		if((nbHits < maxNbHits) && this.canHitCharacter(other)){
			other.loseHealth(powerPerHit);	
			nbHits = 0;
			return true;
		}
		return false;
	}
	
	/**
	 * @param delta Duration between previous tick and current tick
	 * @return The next position of this fireball
	 */
	public Vec2 getNextPosition(int delta){
		deltaCumulated+=delta;
		if(deltaCumulated >= DELTA_CUMULATED_MAX){
			deltaCumulated-=DELTA_CUMULATED_MAX;
			
			x+=((float)(speed.x))*speedValue;
			y+=((float)(speed.y))*speedValue;
		}
		return new Vec2(x,y);
	}
	public Vec2 getSpeed(){
		return speed;
	}
	public int getPower(){
		return powerPerHit;
	}
	public float getAngle(){
		if(x==0){
			return 90;
		}
		return (float) Math.cos(-y/x);
	}
	
	public void setSpeed(Vec2 _speed){
		this.speed = _speed;
	}
	public void setMaxNbHits(int nb){
		this.maxNbHits = nb;
	}
	
	
	@Override
	public void draw(GameContainer container, StateBasedGame game, Graphics g){
		if(animation!=null){
			animation.draw(x, y, w, h, colorFilter);
		}
		else if(shape!=null){
			g.texture(shape, image, true);
		}
		else if(image!=null){
			image.draw(x, y, w, h);
		}
	}
}
