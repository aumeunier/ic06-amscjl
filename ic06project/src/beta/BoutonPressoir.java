package beta;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;

public class BoutonPressoir extends Declencheur {
	protected ArrayList<Body> relatedBody;
	
	protected int poids;
	static private int cpt=0;
	private int num;
	protected boolean resteactive=false;
	
	public BoutonPressoir(int _x, int _y, int _w, int _h, ArrayList<Body> b, int p){
		super(_x, _y, _w, _h);
		relatedBody=b;
		cpt++;
		num=cpt;
		poids=p;
		if(poids==1)
			this.setImage("Bouton1.png");
		else if(poids==2)
			this.setImage("Bouton2.png");
		else if(poids==3)
			this.setImage("Bouton3.png");
		}
	
	public int getPoids(){
		return poids;
	}
	
	public int getNum(){
		return num;
	}
	
	public void setResteActive(boolean active){
		resteactive=active;
	}

	public void check(){
		if((level.character1.getBouton()==num || level.character2.getBouton()==num)&& poids==1)
			activate();
		else if(((level.character1.getBouton()==num && level.character2.getBouton()==num)||(level.character1.getBouton()==num && level.character1.isFat())||(level.character2.getBouton()==num && level.character2.isFat()))&&poids==2)
			activate();
		else if((level.character1.getBouton()==num && level.character2.getBouton()==num && (level.character1.isFat() || level.character2.isFat()))&&poids==3)
			activate();
		else if((level.character1.getBouton()==num && level.character2.getBouton()==num && level.character1.isFat() && level.character2.isFat())&&poids==4)
			activate();			
		else if(!resteactive)
			desactivate();
		
	}
	
	public void activate(){
		this.setActivated(true);
		for (Body bx : relatedBody) {
			((Sprite)(bx.getUserData())).Hidden(!((Sprite)(bx.getUserData())).isHidden());
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
			((Sprite)(bx.getUserData())).Hidden(false);
		}
		if(poids==1){
			this.setImage("Bouton1.png");
		}
		else if(poids==2)
			this.setImage("Bouton2.png");
		else if(poids==3)
			this.setImage("Bouton3.png");
		
	}
}
