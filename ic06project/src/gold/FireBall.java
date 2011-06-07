package gold;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class FireBall extends Sprite {
	protected float x, y, w, h;
	private Sprite owner;
	private int powerPerHit = 1;
	private int nbHits = 0;
	private int maxNbHits = 1;
	private float speedValue = 0.0001f;
	protected Vec2 speed = new Vec2(0.0f,0.0f);
	protected Vec2 target = new Vec2(0.0f,0.0f);
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
	
	public void setSpeed(Vec2 _speed){
		this.speed = _speed;
	}
	public Vec2 getSpeed(){
		return speed;
	}
	public int getPower(){
		return powerPerHit;
	}
	public void setMaxNbHits(int nb){
		this.maxNbHits = nb;
	}
	public boolean doesItCharacter(Character other){
		return !(other.equals(owner));
	}
	
	public boolean hurtCharacter(Character other){
		if((nbHits < maxNbHits) && this.doesItCharacter(other)){
			other.loseHealth(powerPerHit);	
			nbHits = 0;
			return true;
		}
		return false;
	}
	
	public Vec2 getNextPosition(int delta){
		deltaCumulated+=delta;
		if(deltaCumulated >= DELTA_CUMULATED_MAX){
			deltaCumulated-=DELTA_CUMULATED_MAX;
			
			x+=((float)(speed.x))*speedValue;
			y+=((float)(speed.y))*speedValue;
		}
		return new Vec2(x,y);
	}

	public float getAngle(){
		if(x==0){
			return 90;
		}
		return (float) Math.cos(-y/x);
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
