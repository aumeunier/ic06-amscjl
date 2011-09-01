package version1.sprites.alive.monsters;

import org.jbox2d.common.Vec2;
import version1.sprites.alive.Creature;

/**
 * A Monster is a <code>Creature</code> who is controlled by the computer. 
 * It can move by itself. The movement is pretty simple as of now: from a starting point to an end point.
 *
 */
public class Monster extends Creature {
	/** Two dimensional speed */
	protected Vec2 speed = new Vec2(0,0);
	/** Starting point of the movement */
	protected Vec2 start;
	/** End point of the movement */
	protected Vec2 end;
	/** If yes, the monster doesn't move */
	protected boolean staticMonster;
	
	public Monster(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		this.setImage("Behemoth.png");
		staticMonster = true;
	}	
	
	/** At every step the monster move. It will change direction if it has arrived to the starting or end point. */
	public void step(int delta){
		if(staticMonster || start == null || end == null){
			return;
		}
		if(x < start.x && speed.x < 0){
			inverseXspeed();
		}
		else if(x > end.x && speed.x > 0){
			inverseXspeed();
		}
		if(y < start.y && speed.y > 0){
			inverseYspeed();
		}
		else if(y > end.y && speed.y < 0){
			inverseYspeed();
		}
	}
	public void inverseXspeed(){
		speed.x = - speed.x;
	}
	public void inverseYspeed(){
		speed.y = - speed.y;
	}
	
	
	/////////////////////////////////////////////////////////
	//////////////////////// GETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	public Vec2 getSpeed(){
		return speed;
	}
	public boolean isGoingLeft(){
		return speed.x < 0;
	}
	public boolean isGoingRight(){
		return speed.x > 0;
	}
	public boolean isGoingDown(){
		return speed.y < 0;
	}
	public boolean isGoingUp(){
		return speed.y > 0;
	}
	
	
	/////////////////////////////////////////////////////////
	//////////////////////// SETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	public void setStatic(){
		staticMonster = true;
	}
	public void setBorns(Vec2 _start, Vec2 _end){
		staticMonster = false;
		start = _start;
		end = _end;
	}
	public void setSpeed(Vec2 v){
		speed = v;
	}
	
}
