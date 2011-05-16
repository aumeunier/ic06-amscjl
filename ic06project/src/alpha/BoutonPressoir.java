package alpha;

import org.jbox2d.dynamics.Body;

public class BoutonPressoir extends Declencheur {
	
	public BoutonPressoir(int _x, int _y, int _w, int _h, Body b){
		super(_x, _y, _w, _h, b);
		this.setImage("blur20test.png");
	}

	@Override
	public void activate() {
		this.setActivated(true);
		((Obstacle)(relatedBody.getUserData())).setHidden(true);
		this.setImage("blur1test.png");
	}

	@Override
	public void desactivate() {
		this.setActivated(false);
		((Obstacle)(relatedBody.getUserData())).setHidden(false);
		this.setImage("blur20test.gif");
	}
}
