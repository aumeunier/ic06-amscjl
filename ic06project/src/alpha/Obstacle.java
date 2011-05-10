package alpha;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;


public class Obstacle extends Sprite{
	private boolean isHidden;
	public Obstacle(){
		setImage("bricks.jpg");
		isHidden = false;
	}
	public Obstacle(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("bricks.jpg");
		isHidden = false;
	}
	public boolean isHidden(){
		return isHidden;
	}
	public void setHidden(boolean shouldBeHidden){
		isHidden = shouldBeHidden;
	}
}
