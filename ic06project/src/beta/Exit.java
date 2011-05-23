package beta;


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
		this.image = Global.setImage("porte.png");
	}
	
	public boolean isReady(){
		return isReady;
	}
	public int getCpt(){
		return cpt;
	}
}
