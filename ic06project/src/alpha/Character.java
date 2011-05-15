package alpha;


public class Character extends Sprite{
	private Power power;
	public boolean isGoingRight;
	public boolean isGoingLeft;
	public boolean isFalling;
	private boolean isDead;
	private boolean avoidDoubleChangeFlag;
	public Character(){
		super();
		x=0;
		y=0;
		w=50;
		h=50;
		isGoingRight=false;
		isGoingLeft=false;
		isFalling=false;
		isDead = false;
		avoidDoubleChangeFlag=true;
		power = Power.NONE;
		setImage("normal.png");
	}
	public Character(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		isGoingRight=false;
		isGoingLeft=false;
		isFalling=false;
		isDead = false;
		avoidDoubleChangeFlag=true;
		power = Power.NONE;
	}
	public void changeImage(){
		if(isIntangible()){
			setImage("invisible.png");
		}
		else if(isFlying()){
			setImage("flying.png");	
		}
		else {
			setImage("normal.png");			
		}
	}
	
	public boolean isFlying(){
		return power==Power.FLYING;
	}
	public boolean isIntangible(){
		return power==Power.INTANGIBLE;
	}
	public boolean isDead(){
		return isDead;
	}
	public void setDead(boolean _dead){
		isDead = _dead;
	}
	public Power getPower(){
		return power;
	}

	public void setPower(Power _power){
		power = _power;
		changeImage();
	}
	public boolean getAvoidDoubleFlag(){
		return avoidDoubleChangeFlag;
	}
	public void changeAvoidDoubleFlagState(){
		avoidDoubleChangeFlag=!avoidDoubleChangeFlag;
	}
}
