package gold;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

public class BoutonCharge extends BoutonPressoir{

	public BoutonCharge(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h, b, 1);
	}
	
	public void activate(){
		this.setActivated(true);
		
		for (Body bx : relatedBody) {
			if(((PlateformeMissile)(bx.getUserData())).getMissile()==null)
			{
				((PlateformeMissile)(bx.getUserData())).recharge();
			}
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
	
}
