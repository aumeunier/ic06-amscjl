package gold;

public class Missile extends Destructible{
	int xStop;
	boolean shouldMove;
	
	public Missile(int _x, int _y, int _w, int _h) {
		super( _x,  _y, _w,  _h);
		xStop=0;
		shouldMove=false;
	}
	public void bouge(String s){
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
	}
	
	public boolean shouldMove(){
		return shouldMove;
	}
	public int xMove(){
		return xStop;
	}

}
