package gold;

import java.util.ArrayList;

import org.jbox2d.collision.MassData;
import org.jbox2d.dynamics.Body;

public class BoutonBombarde extends BoutonPressoir {
	
	boolean shouldBombard;
	Body aBombarde;
	
	public BoutonBombarde(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h, b, 1);
		shouldBombard=false;
		aBombarde=null;
	}

	public void activate(){
		this.setActivated(true);		
		for (Body bx : relatedBody) {
			shouldBombard=true;
			aBombarde=bx;
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
		
		if(poids==1)
			this.setImage("Bouton1.png");
		else if(poids==2)
			this.setImage("Bouton2.png");
		else if(poids==3)
			this.setImage("Bouton3.png");
	}
	
	public void bombarde(){
		shouldBombard=false;
	}
	
	public boolean shouldBombarde(){
		return shouldBombard;
	}
	
	public Body bombardement(){
		return aBombarde;
	}
}
