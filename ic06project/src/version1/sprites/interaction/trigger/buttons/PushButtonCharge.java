package version1.sprites.interaction.trigger.buttons;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import version1.sprites.interaction.MissilePlatform;

/**
 * A PushButtonCharge is used to recharge <code>MissilePlatform</code> missiles.
 *
 */
public class PushButtonCharge extends PushButton{

	public PushButtonCharge(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h, b, 1);
	}
	
	@Override
	public void enable(){
		this.setEnabled(true);		
		for (Body bx : relatedBodies) {
			if(((MissilePlatform)(bx.getUserData())).getMissile()==null) {
				((MissilePlatform)(bx.getUserData())).recharge();
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
