package beta;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

public class Levier extends Declencheur {

	public Levier(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h, b);
		this.setImage("levier-1.png");
	}

	@Override
	public void activate() {
		isActivated=!isActivated;
		for (Body bx : relatedBody) {
			
			if(isActivated)
			{
				((Sprite)(bx.getUserData())).Hidden(!((Sprite)(bx.getUserData())).isHidden());
				this.setImage("levier-2.png");	
			}
			else{
				((Sprite)(bx.getUserData())).Hidden(!((Sprite)(bx.getUserData())).isHidden());
				this.setImage("levier-1.png");
			}
			 
		}
		
	}
	public void desactivate(){
	}

}
