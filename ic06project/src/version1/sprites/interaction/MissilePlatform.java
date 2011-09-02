package version1.sprites.interaction;

import org.jbox2d.dynamics.Body;

import version1.Global;
import version1.sprites.decor.Ground;

/**
 * A missile platform is a special platform which can charge and release a missile. It can also be moved.
 *
 */
public class MissilePlatform extends Ground{
	private Body missile;
	private boolean shouldBeRecharged;
	protected int minX = 0, maxX = Global.GAMEPLAYWIDTH, minY = 0, maxY = Global.GAMEPLAYHEIGHT;

	public MissilePlatform(int _x, int _y, int _w, int _h){
		super(_x,_y,_w, _h);
		missile = null;
		shouldBeRecharged = false;
	}

	public int getMinX(){ return minX; }
	public int getMaxX(){ return maxX; }
	public int getMinY(){ return minY; }
	public int getMaxY(){ return maxY; }
	public void setMinX(int min) { minX = min; }
	public void setMaxX(int max) { maxX = max; }
	public void setMinY(int min) { minY = min; }
	public void setMaxY(int max) { maxY = max; }
	
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
