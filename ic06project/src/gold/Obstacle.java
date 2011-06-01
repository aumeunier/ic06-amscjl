package gold;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;


public class Obstacle extends Sprite{
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
