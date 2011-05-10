package alpha;

import org.jbox2d.dynamics.Body;

public class Levier extends Declencheur {

	public Levier(int _x, int _y, int _w, int _h, Body b){
		super(_x, _y, _w, _h, b);
		this.setImage("blur20.jpg");
	}

	@Override
	public void activate() {
		this.setActivated(true);
		((Obstacle)(relatedBody.getUserData())).setHidden(true);
		this.setImage("blur1.jpg");
	}

	@Override
	public void desactivate() {
		this.setActivated(false);
		((Obstacle)(relatedBody.getUserData())).setHidden(false);
		this.setImage("blur20.jpg");
	}
}
