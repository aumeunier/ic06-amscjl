package protoWithSlick;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Character extends Sprite{
	public boolean isGoingRight;
	public boolean isGoingLeft;
	public boolean isFalling;
	private boolean intangible;
	private boolean flying;
	public boolean test;
	public Character(){
		super();
		x=0;
		y=0;
		w=50;
		h=50;
		isGoingRight=false;
		isGoingLeft=false;
		isFalling=false;
		intangible=false;
		flying = false;
		test = true;
		setImage("normal.png");
	}
	public Character(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
	}
	public void changeImage(){
		if(intangible){
			setImage("invisible.png");
		}
		else if(flying){
			setImage("flying.png");	
		}
		else {
			setImage("normal.png");			
		}
	}
	
	public void setFlying(boolean flag){
		flying = flag;
		if(flying){
			intangible = false;			
		}
	}
	public void setIntangible(boolean flag){
		intangible = flag;
		if(intangible){
			flying = false;
		}
	}
	public boolean isFlying(){
		return flying;
	}
	public boolean isIntangible(){
		return intangible;
	}
}
