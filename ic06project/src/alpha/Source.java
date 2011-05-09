package alpha;


public class Source extends Sprite {
	int power;
	public Source(){
		super();
		power = 1;
		setImage("pool_hat.png");
	}
	public Source(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		power = 1;
		setImage("pool_hat.png");
	}

}
