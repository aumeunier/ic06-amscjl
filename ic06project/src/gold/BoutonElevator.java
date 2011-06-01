package gold;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class BoutonElevator extends BoutonPressoir {
	
	protected int puissance;
	
	public BoutonElevator(int _x, int _y, int _w, int _h, ArrayList<Body> b, int p, int puiss){
		super(_x, _y, _w, _h, b, p);
		puissance=puiss;
	}

	public void activate(){
		this.setActivated(true);		
		for (Body bx : relatedBody) {
			
			bx.applyImpulse(new Vec2(0, puissance), bx.getWorldCenter());
		}
		if(poids==1)
			this.setImage("Boutonactif1.png");
		else if(poids==2)
			this.setImage("Boutonactif2.png");
		else if(poids==3)
			this.setImage("Boutonactif3.png");
	}

	public void desactivate(){
		this.setActivated(false);
		for (Body bx : relatedBody) {
			
			bx.applyImpulse(new Vec2(0, -200), bx.getWorldCenter());
		}
		
		if(poids==1)
			this.setImage("Bouton1.png");
		else if(poids==2)
			this.setImage("Bouton2.png");
		else if(poids==3)
			this.setImage("Bouton3.png");
	}
}
