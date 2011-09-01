package version1.sprites.interaction;

import org.jbox2d.dynamics.Body;

import version1.sprites.decor.Ground;

/**
 * A missile platform is a special platform which can charge and release a missile. It can also be moved.
 *
 */
public class MissilePlatform extends Ground{
	private Body missile;
	private boolean shouldBeRecharged;
	
	public MissilePlatform(int _x, int _y, int _w, int _h){
		super(_x,_y,_w, _h);
		missile = null;
		shouldBeRecharged = false;
	}
	
	public void setMissile(Body b){
		missile=b;
		shouldBeRecharged=false;
	}	
	public Body getMissile(){
		return missile;
	}
	
	/** Called by the main engine when the platform should be recharged */
	public void recharge(){
		shouldBeRecharged=true;
	}
	/** Called when a player interacts with the platform's charger */
	public boolean shouldRecharge(){
		return shouldBeRecharged;
	}
	
}
