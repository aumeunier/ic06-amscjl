package gold;


public abstract class Declencheur extends Sprite {
	protected boolean isActivated;
	
	public Declencheur(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		isActivated=false;
	}
	
	public boolean isActivated(){
		return isActivated;
	}
	public void setActivated(boolean _activated){
		isActivated = _activated;
	}
	abstract public void activate();
	abstract public void desactivate();
}
