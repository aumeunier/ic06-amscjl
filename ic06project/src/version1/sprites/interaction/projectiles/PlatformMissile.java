package version1.sprites.interaction.projectiles;

import org.newdawn.slick.Color;

import version1.sprites.interaction.Destructible;
import version1.sprites.interaction.MissilePlatform;

/**
 * A PlatformMissile is an object which can be thrown by a <code>MissilePlatform</code> 
 *
 */
public class PlatformMissile extends Destructible{
	/** The platform which shoots the missile */
	protected MissilePlatform relatedPlatform;
	
	public PlatformMissile(int _x, int _y, int _w, int _h, MissilePlatform p) {
		super( _x,  _y, _w,  _h);
		relatedPlatform = p;
		this.setFilter(new Color(185, 120, 120, 255));
	}
	
	public MissilePlatform getPlatform(){
		return relatedPlatform;
	}

}
