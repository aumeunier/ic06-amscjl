package beta;

import org.jbox2d.dynamics.Body;

public class BoutonPressoir extends Declencheur {
	
	public BoutonPressoir(int _x, int _y, int _w, int _h, Body b){
		super(_x, _y, _w, _h, b);
		this.setImage("blur20test.png");
	}

	public void activate(){
		this.setActivated(true);
		((Sprite)(relatedBody.getUserData())).Hidden(true);
		this.setImage("blur1test.png");
	}

	public void desactivate(){
		this.setActivated(false);
		((Sprite)(relatedBody.getUserData())).Hidden(false);
		this.setImage("blur20test.png");
	}
}
