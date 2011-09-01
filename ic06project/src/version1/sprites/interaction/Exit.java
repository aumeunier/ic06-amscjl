package version1.sprites.interaction;

import version1.Global;
import version1.sprites.Sprite;


/**
 * An Exit is what players should aim for. Both players need to be at the exit to finish the level
 *
 */
public class Exit extends Sprite {
	
	public Exit(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		this.image = Global.getImage("porte.png");
	}
}
