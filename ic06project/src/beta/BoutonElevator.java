package beta;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class BoutonElevator extends BoutonPressoir {
	
	protected int poids;
	
	public BoutonElevator(int _x, int _y, int _w, int _h, Body b, int p){
		super(_x, _y, _w, _h, b, p);
	}

	public void activate(){
		System.out.println("fonction activate");
		this.setActivated(true);
		relatedBody.applyImpulse(new Vec2(0, 400), relatedBody.getWorldCenter());
		this.setImage("blur1test.png");
	}

	public void desactivate(){
		this.setActivated(false);
		relatedBody.applyImpulse(new Vec2(0, -200), relatedBody.getWorldCenter());
		this.setImage("blur20test.png");
	}
}
