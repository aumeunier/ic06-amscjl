package gold;

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
