package gold;

import org.jbox2d.dynamics.Body;

public class PlateformeMissile extends Ground{

	int xStop;
	boolean shouldMove;
	Body monmissile;
	boolean shouldBeRecharged;
	
	public PlateformeMissile(int _x, int _y, int _w, int _h){
		super(_x,_y,_w, _h);
		xStop=_x;
		shouldMove=false;
		monmissile = null;
		shouldBeRecharged = false;
	}
	
	/*public void bouge(String s){
		if(s=="left")
		{
			xStop=x-10;
			if(xStop<0){
				xStop=0;
			}
		}
		else if (s=="right")
		{
			xStop=x+10;
			if(xStop>800){
				xStop=800;
			}
		}
		shouldMove=true;
	}*/
	
	/*public boolean shouldMove(){
		return shouldMove;
	}
	public int xMove(){
		return xStop;
	}*/
	
	
	public void setMissile(Body b){
		if(b==null){
		}
		monmissile=b;
		shouldBeRecharged=false;
	}
	
	public Body getMissile(){
		return monmissile;
	}
	
	public void recharge(){
		shouldBeRecharged=true;
	}
	
	public boolean shouldRecharge(){
		return shouldBeRecharged;
	}
	
}
