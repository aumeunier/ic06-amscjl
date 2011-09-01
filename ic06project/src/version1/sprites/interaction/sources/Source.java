package version1.sprites.interaction.sources;

import version1.sprites.Power;
import version1.sprites.Sprite;


/**
 * A Source is an object full of <code>Power</code> which can transmit its power to any character who touches it.
 *
 */
public class Source extends Sprite {
	/** The power related to that source and which can be given to players touching this Source */
	protected Power power;
	public Source(Power _power){
		super();
		power = _power;
		setImage(power.imageForPower());
	}
	public Source(int _x, int _y, int _w, int _h, Power _power){
		super(_x,_y,_w,_h);
		power = _power;
		setImage(power.imageForPower());
	}
	public Power getPower(){
		return power;
	}
}
