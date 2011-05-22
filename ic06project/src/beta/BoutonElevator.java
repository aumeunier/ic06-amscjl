package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class BoutonElevator extends BoutonPressoir {
	
	protected int poids;
	
	public BoutonElevator(int _x, int _y, int _w, int _h, ArrayList<Body> b, int p){
		super(_x, _y, _w, _h, b, p);
	}

	public void activate(){
		this.setActivated(true);		
		for (Body bx : relatedBody) {
			
			bx.applyImpulse(new Vec2(0, 6300), bx.getWorldCenter());
		}
		this.setImage("blur1test.png");
	}

	public void desactivate(){
		this.setActivated(false);
		for (Body bx : relatedBody) {
			
			bx.applyImpulse(new Vec2(0, -200), bx.getWorldCenter());
		}
		
		this.setImage("blur20test.png");
	}
}
