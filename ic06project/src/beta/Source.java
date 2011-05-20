package beta;

public class Source extends Sprite {
	Power power;
	public Source(Power _power){
		super();
		power = _power;
		setImage(power.imageForPower());
	}
	public Source(int _x, int _y, int _w, int _h, Power _power){
		super(_x,_y,_w,_h);
		power = _power;
		setImage(power.imageForPower());
	}
}
