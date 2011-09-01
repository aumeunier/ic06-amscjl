package version1.sprites.interaction.trigger.buttons;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

/**
 * A PushButtonElevator will apply an impulse to a platform when pushed
 *
 */
public class PushButtonElevator extends PushButton {
	/** How powerful the impulse is */
	protected int strength;
	
	public PushButtonElevator(int _x, int _y, int _w, int _h, ArrayList<Body> b, int p, int strength){
		super(_x, _y, _w, _h, b, p);
		this.strength = strength;
	}

	@Override
	public void enable(){
		this.setEnabled(true);		
		for (Body bx : relatedBodies) {
			bx.applyImpulse(new Vec2(0, strength), bx.getWorldCenter());
		}
		this.updateImage();
	}

	@Override
	public void disable(){
		this.setEnabled(false);
		for (Body bx : relatedBodies) {
			bx.applyImpulse(new Vec2(0, -200), bx.getWorldCenter());
		}
		this.updateImage();
	}
}
