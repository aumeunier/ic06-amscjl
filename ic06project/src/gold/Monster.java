package gold;

import org.jbox2d.common.Vec2;

public class Monster extends Character {
	protected Vec2 speed = new Vec2(0,0);
	protected Vec2 start;
	protected Vec2 end;
	protected boolean staticMonster;
	
	public Monster(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		this.setImage("Behemoth.png");
		staticMonster = true;
	}	

	public void step(){
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
	public void setStatic(){
		staticMonster = true;
	}
	public void setBorns(Vec2 _start, Vec2 _end){
		staticMonster = false;
		start = _start;
		end = _end;
	}
	public Vec2 getSpeed(){
		return speed;
	}
	public void setSpeed(Vec2 v){
		speed = v;
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
}
