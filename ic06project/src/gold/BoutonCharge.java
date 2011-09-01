package gold;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

public class BoutonCharge extends Button{

	public BoutonCharge(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h, b, 1);
	}
	
	public void activate(){
		this.setActivated(true);		
		for (Body bx : relatedBody) {
			if(((PlateformeMissile)(bx.getUserData())).getMissile()==null) {
				((PlateformeMissile)(bx.getUserData())).recharge();
			}
		}
		this.updateImage();
	}

	public void desactivate(){
		this.setActivated(false);
		this.updateImage();
	}
	
}
