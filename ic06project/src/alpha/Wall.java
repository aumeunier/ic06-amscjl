package alpha;


public class Wall extends Sprite{
	public Wall(){
		super();
		setImage("wall.jpg");
	}
	public Wall(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("wall.jpg");
	}
}
