package gold;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class FireBall extends Sprite {
	private Sprite owner;
	private int powerPerHit = 1;
	private int nbHits = 0;
	private int maxNbHits = 1;
	protected Vec2 speed = new Vec2(0,0);
	
	public FireBall(int x, int y, Vec2 _speed, Sprite _owner){
		super(x,y,10,10);
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
