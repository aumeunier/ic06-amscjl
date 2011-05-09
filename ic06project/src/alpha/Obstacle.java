package alpha;


public class Obstacle extends Sprite{
	
	public Obstacle(){
		setImage("bricks.jpg");
	}
	public Obstacle(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("bricks.jpg");
	}
}
