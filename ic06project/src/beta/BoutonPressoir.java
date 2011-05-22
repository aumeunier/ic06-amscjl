package beta;

import org.jbox2d.dynamics.Body;

public class BoutonPressoir extends Declencheur {
	
	protected int poids;
	
	public BoutonPressoir(int _x, int _y, int _w, int _h, Body b, int p){
		super(_x, _y, _w, _h, b);
		poids=p;
		if (p==1)
			this.setImage("blur20test.png");
		else if (p==2)
			this.setImage("blur20test.png");
	}
	
	public int getPoids(){
		return poids;
	}

	public void activate(){
		this.setActivated(true);
		((Sprite)(relatedBody.getUserData())).Hidden(true);
		this.setImage("blur1test.png");
	}

	public void desactivate(){
		this.setActivated(false);
		((Sprite)(relatedBody.getUserData())).Hidden(false);
		this.setImage("blur20test.png");
	}
}
