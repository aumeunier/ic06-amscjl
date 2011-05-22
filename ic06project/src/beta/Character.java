package beta;

import java.util.HashSet;

import org.newdawn.slick.Color;


public class Character extends Sprite{
	private Power power = Power.NONE;
	public boolean isGoingRight = false;
	public boolean isGoingLeft = false;
	public boolean isFalling = false;
	private boolean isDead = false;
	private boolean isAtExit = false;
	private boolean isTransported = false;
	public int X_transported = 0;
	public int Y_transported = 0;
	private boolean avoidDoubleChangeFlag = true;
	private final static int FEE_PNG_W = 348;
	private final static int FEE_PNG_H = 260;
	private final static int CHAR_W = (int) (FEE_PNG_W/4);
	private final static int CHAR_H = (int) (FEE_PNG_H/4);
	private final static float CHAR_W_BODY_RATIO = 0.5f;
	private final static float CHAR_H_BODY_RATIO = 1.0f;
	
	public Character(){
		super(0,0,CHAR_W,CHAR_H);
		setAnimation("fee2.png",FEE_PNG_W,FEE_PNG_H);
	}
	public Character(int _x, int _y){
		super(_x,_y,CHAR_W,CHAR_H);
		setAnimation("fee2.png",FEE_PNG_W,FEE_PNG_H);
	}
	public Character(int _x, int _y, float ratio){
		super(_x,_y,(int)(CHAR_W*ratio),(int)(CHAR_H*ratio));
		setAnimation("fee2.png",FEE_PNG_W,FEE_PNG_H);
	}
	public void changePower(){
		if(isIntangible()){
			setImage("invisible.png");
			this.setFilter(new Color(129,244,254,100));
		}
		else if(isFlying()){
			setImage("flying.png");	
			this.setFilter(new Color(251,185,54,255));
		}
		else {
			setAnimation("fee2.png",FEE_PNG_W,FEE_PNG_H);
			this.setFilter(Color.white);
		}
	}
	
	private void changeDirection(){
		if(isGoingRight){
			setAnimation("feeRight.png",FEE_PNG_W,FEE_PNG_H);
		}
		else if(isGoingLeft){
			setAnimation("feeLeft.png",FEE_PNG_W,FEE_PNG_H);
		}
		else {
			setAnimation("fee2.png",FEE_PNG_W,FEE_PNG_H);
		}
	}
	
	public void goLeft(){
		if(!isGoingLeft){			
			isGoingRight = false;
			isGoingLeft = true;
			changeDirection();
		}
	}
	
	public void goRight(){
		if(!isGoingRight){			
			isGoingRight = true;
			isGoingLeft = false;
			changeDirection();
		}
	}
	
	public void straight(){
		if(isGoingRight || isGoingLeft){			
			isGoingRight = false;
			isGoingLeft = false;
			changeDirection();
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
	public boolean isAtExit(){
		return isAtExit;
	}
	public void setAtExit(boolean b){
		isAtExit = b;
	}
	public boolean isTransported(){
		return isTransported;
	}
	public void setTransported(boolean b, int x, int y){
		isTransported = b;
		X_transported=x;
		Y_transported=y;
	}
	public int getCharWidth(){
		return this.w;
	}
	public int getCharHeight(){
		return this.h;
	}
	public int getCharBodyWidth(){
		return (int) (this.w*CHAR_W_BODY_RATIO);		
	}
	public int getCharBodyHeight(){
		return (int) (this.h*CHAR_H_BODY_RATIO);		
	}
	
	public void setPower(Power _power){
		power = _power;
		changePower();
	}
	public boolean getAvoidDoubleFlag(){
		return avoidDoubleChangeFlag;
	}
	public void changeAvoidDoubleFlagState(){
		avoidDoubleChangeFlag=!avoidDoubleChangeFlag;
	}
}
