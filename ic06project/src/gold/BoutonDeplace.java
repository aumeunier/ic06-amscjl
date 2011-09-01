package gold;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

public class BoutonDeplace extends Button {
	
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
		this.updateImage();
	}

	public void desactivate(){
		this.setActivated(false);
		this.updateImage();
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
