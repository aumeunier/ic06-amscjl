package alpha;

import org.jbox2d.dynamics.Body;

public class Exit extends Sprite {
	protected boolean isReady;
	int cpt;
	
	public Exit(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		cpt=0;
		isReady=false;
	}
	
	public boolean isReady(){
		return isReady;
	}
	public int getCpt(){
		return cpt;
	}
	public void ajoutCollision(){
		cpt ++;
		if(cpt==2)
		{
			isReady=true;
		}
	}
	public void supprCollision(){
		if(cpt==2)
		{
			isReady=false;
		}
		cpt--;
	}
}
