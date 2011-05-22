package beta;

import org.jbox2d.dynamics.Body;

public class Levier extends Declencheur {

	public Levier(int _x, int _y, int _w, int _h, Body b){
		super(_x, _y, _w, _h, b);
		this.setImage("levier-1.png");
	}

	@Override
	public void activate() {
		isActivated=!isActivated;
		if(isActivated)
		{
			((Sprite)(relatedBody.getUserData())).Hidden(true);
			this.setImage("levier-2.png");	
		}
		else{
			((Sprite)(relatedBody.getUserData())).Hidden(false);
			this.setImage("levier-1.png");
		}
	}
	public void desactivate(){
	}

}
