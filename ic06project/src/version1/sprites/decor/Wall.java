package version1.sprites.decor;

import version1.sprites.Sprite;


/**
 * The name of this class pretty much talks for itself. It is a block used as a wall.
 * A wall is used to put the limits to the game. 
 *
 */
public class Wall extends Sprite{
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically implicit.)
     */
	public Wall(){
		super();
		setImage("wall2.jpg");

	}
	public Wall(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("wall2.jpg");
	}
}
