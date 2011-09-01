package version1.sprites.interaction.trigger.buttons;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import version1.sprites.interaction.MissilePlatform;

/**
 * A PushButtonShoot is used to shoot a <code>MissilePlatform</code> missiles.
 * The platform has to be charged otherwise nothing will happen.
 *
 */
public class PushButtonShoot extends PushButton {
	/** Whether the platform should shoot the missile at the next clock tick */
	protected boolean shouldShoot;
	
	public PushButtonShoot(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h, b, 1);
		shouldShoot=false;
	}

	public boolean isShouldShoot(){
		return shouldShoot;
	}
	/**
	 * Shoot the missile (wakes the body up) and set the platform as not charged
	 */
	public void shoot(){
		shouldShoot=false;
		for(Body platform: this.relatedBodies){
			MissilePlatform theplatform = (MissilePlatform)(platform.getUserData());
			if(theplatform.getMissile() != null){
				theplatform.getMissile().wakeUp();
				theplatform.setMissile(null);					
			}		
		}
	}	

	@Override
	public void enable(){
		this.setEnabled(true);	
		for (Body bx : relatedBodies) {
			if(((MissilePlatform)(bx.getUserData())).getMissile()!=null){
				shouldShoot=true;
			}
		}
		this.updateImage();
	}

	@Override
	public void disable(){
		this.setEnabled(false);
		this.updateImage();
	}
	
}
