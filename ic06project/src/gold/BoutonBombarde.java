package gold;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

public class BoutonBombarde extends Button {
	
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
			if( (  (PlateformeMissile)(bx.getUserData())).getMissile()!=null){
				shouldBombard=true;
				aBombarde=bx;
			}
		}
		this.updateImage();
	}

	public void desactivate(){
		this.setActivated(false);
		this.updateImage();
	}
	
	public void bombarde(){
		shouldBombard=false;
		((PlateformeMissile)(aBombarde.getUserData())).setMissile(null);
		aBombarde=null;
	}
	
	public boolean shouldBombarde(){
		return shouldBombard;
	}
	
	public Body bombardement(){
		return aBombarde;
	}
}
