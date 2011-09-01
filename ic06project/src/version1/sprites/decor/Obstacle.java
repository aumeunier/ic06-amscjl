package version1.sprites.decor;

import version1.sprites.Sprite;

/**
 * The name of this class pretty much talks for itself. 
 * An obstacle can be passed through by a character with the intangible power.
 *
 */
public class Obstacle extends Sprite{
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically implicit.)
     */
	public Obstacle(){
		setImage("arbre-droit.png");
		isHidden = false;
	}
	public Obstacle(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("arbre-droit.png");
		isHidden = false;
	}

}
