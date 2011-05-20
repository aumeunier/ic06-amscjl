package beta;


public class Ground extends Sprite{
	public Ground(){
		super();
		setImage("sol3.png");

	}
	public Ground(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("sol3.png");
		this.image = this.image.getSubImage(_x,0,_w,_h);
	}
}
