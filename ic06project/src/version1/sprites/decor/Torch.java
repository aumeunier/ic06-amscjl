package version1.sprites.decor;

import version1.sprites.Sprite;

/**
 * A torch is a sprite that generates some light in a dark environment.
 *
 */
public class Torch extends Sprite {

	public Torch(int _x, int _y){
		this.x = _x;
		this.y = _y;
		this.w = 30;
		this.h = 30;
		this.setLightSize(80);
		this.setImage("torche.png");
	}
}
