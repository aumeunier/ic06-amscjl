package version1.sprites.interaction.trigger;

import version1.sprites.Sprite;

/**
 * A Trigger is an object with which a player can interact with. A trigger usually has two states: enabled or disabled.
 *
 */
public abstract class Trigger extends Sprite {
	/** The current state of this trigger */
	protected boolean enabled;
	
	public Trigger(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		enabled=false;
	}
	
	public boolean isEnabled(){
		return enabled;
	}
	public void setEnabled(boolean _enabled){
		enabled = _enabled;
	}
	abstract public void enable();
	abstract public void disable();
}
