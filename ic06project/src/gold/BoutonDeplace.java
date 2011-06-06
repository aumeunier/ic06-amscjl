package gold;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class BoutonDeplace extends BoutonPressoir {
	
	String sens ;
	Body theBody;
	boolean deplace;
	
	public BoutonDeplace(int _x, int _y, int _w, int _h, ArrayList<Body> b, String s){
		super(_x, _y, _w, _h, b, 1);
		for (Body bx : relatedBody) {
			theBody=bx;
		}
		sens = s;
		deplace=false;
	}

	public void activate(){
		this.setActivated(true);	
		/*for (Body bx : relatedBody) {
				((PlateformeMissile)bx.getUserData()).bouge(sens);
		}*/
		
		if(poids==1)
			this.setImage("Boutonactif1.png");
		else if(poids==2)
			this.setImage("Boutonactif2.png");
		else if(poids==3)
			this.setImage("Boutonactif3.png");
	}

	public void desactivate(){
		this.setActivated(false);
		
		if(poids==1)
			this.setImage("Bouton1.png");
		else if(poids==2)
			this.setImage("Bouton2.png");
		else if(poids==3)
			this.setImage("Bouton3.png");
	}
	
	public String getsens(){
		return sens;
	}
	public Body getTheRelatedBody(){
		return theBody;
	}
	public boolean getDeplace(){
		deplace=!deplace;
		return deplace;
	}
}
