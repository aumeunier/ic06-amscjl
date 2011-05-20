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
			((Obstacle)(relatedBody.getUserData())).setHidden(true);
			this.setImage("levier-2.png");	
		}
		else{
			((Obstacle)(relatedBody.getUserData())).setHidden(false);
			this.setImage("levier-1.png");
		}
	}

	@Override
	public void desactivate() {
		this.setActivated(false);
		((Obstacle)(relatedBody.getUserData())).setHidden(false);
		this.setImage("blur20.jpg");
	}
}
