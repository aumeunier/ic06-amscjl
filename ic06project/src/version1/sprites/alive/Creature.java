package version1.sprites.alive;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Color;

import version1.sprites.Power;
import version1.sprites.Sprite;

/**
 * A creature is either a player or a monster. This class contains classic variables for a character such as health 
 * and power. It also declares method for actions and movements.
 *
 */
public class Creature extends Sprite{
	private final static int FEE_PNG_W = 348;
	private final static int FEE_PNG_H = 260;
	private final static int CHAR_W = (int) (FEE_PNG_W/4);
	private final static int CHAR_H = (int) (FEE_PNG_H/4);
	private final static float CHAR_W_BODY_RATIO = 0.5f;
	private final static float CHAR_H_BODY_RATIO = 1.0f;
	
	// Booleans on the movement state of the Character
	// TODO: use bytes
	public boolean isGoingRight = false;
	public boolean isGoingLeft = false;
	public boolean isFalling = false;
	public boolean isColliding = false;
	public boolean isSlipping = false;
	private boolean isAtExit = false;
	private boolean isTransported = false;
	protected boolean isSwimming=false;
	private boolean shouldJumpAfterRebound = false;
	
	/** Health of the creature */
	protected int health = 1;
	/** True if the creature is dead */
	private boolean isDead = false;
	/** This timer is user for self-destruction of the creature. Used for clones for example. */
	private Timer timer; 
	/** The power the creature currently have. */
	private Power power = Power.NONE;
	/** Boolean indicating the power has started the destruction operation. */
	private boolean destructionPowerActivated = false;

	/** Strength put on a push button by the player */
	private int boutonpressoir = 0; //TODO:
	// Teleportation stuff TODO rename/remove
	public int X_transported = 0;
	public int Y_transported = 0;

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically implicit.)
     */
	public Creature(){
		super(0,0,CHAR_W,CHAR_H);
		setAnimation("fee-de-face.png",CHAR_W,CHAR_H);
	}
	public Creature(int _x, int _y,int _w, int _h){
		super(_x,_y,_w,_h);
	}
	/**
	 * Constructor to create a creature smaller than usually
	 * @param _x Top left abscissa
	 * @param _y Top left ordinate
	 * @param ratio Size ratio: 1.0 is normal, <1.0 is smaller, >1.0 is bigger
	 */
	public Creature(int _x, int _y, float ratio){
		super(_x,_y,(int)(CHAR_W*ratio),(int)(CHAR_H*ratio));
		setAnimation("fee-de-face.png",CHAR_W,CHAR_H);
	}

	
	// Health management
	public void setHealth(int _health){
		this.health = _health;
	}
	public int getHealth(){
		return health;
	}
	public void loseHealth(int nb){
		this.health -= nb;
		if(this.health <= 0){
			this.setDead(true);
			this.setShouldBeDestroy();
		}
	}
	
	
	// Power management
	/**
	 * Apply the power change: image modification, filter modification, etc.
	 */
	public void changePower(){
		this.setFilter(Color.white);
		if(isIntangible()){
			this.setFilter(new Color(129,244,254,100));
		}
		else if(isFlying()){
			setAnimation("fee-de-face-pouvoir-1.png",CHAR_W,CHAR_H);
		}
		else if(isFire()){
			this.setFilter(new Color(255,51,51,255));
		}
		else if(canSwim()){
			this.setFilter(new Color(102,255,255,255));
		}
		else if(isRebond()){
			this.setFilter(new Color(51,255,51,255));
		}
		else if(isFat()){
			this.setFilter(Color.white);
			setAnimation("grosse-vache.png",CHAR_W, CHAR_H);
		}
		else if(isLight()){
			this.setLightSize((int) (3*this.h));
		}
		else if(canTeleport()){
			this.setFilter(new Color(255,153,255,255));
		}
		else if(absorbe()){
			this.setFilter(new Color(102,102,102,255));
		}
		else if(isInvisible()){
			this.setFilter(new Color(150,150,150,100));
		}
		else if(dedouble()){
			this.setFilter(new Color(44,210,44,255));
		}
		else {
			setAnimation("fee-de-face.png",CHAR_W,CHAR_H);
			this.setFilter(Color.white);
		}
	}
	
	/** Self destruction after 10s (used for double) */
	public void initTimer(){
		timer = new Timer(10000, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				endOfTimer();
			}			
		});
		timer.start();
	}
	/** Self-destruction */
	public void endOfTimer(){
		shouldBeDestroy = true;
		timer.stop();
	}

	// Directions
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
	/** Change the image depending on the direction of the character */
	private void changeDirection(){
		if(isGoingLeft && isFat()){
			setAnimation("grosse-vache-incoming.png",CHAR_W,CHAR_H);
		}
		else if(isGoingRight && isFat()){
			setAnimation("grosse-vache-incoming-droite.png",CHAR_W,CHAR_H);
		}
		else if(isGoingRight && !isFlying()){
			setAnimation("fee-marche-droite.png",CHAR_W,CHAR_H);
		}
		else if(isGoingLeft && !isFlying()){
			setAnimation("fee-marche-gauche.png",CHAR_W,CHAR_H);
		}
		else if(isGoingRight && isFlying()){
			setAnimation("fee-vole-droite-pouvoir.png",CHAR_W,CHAR_H);
		}
		else if(isGoingLeft && isFlying()){
			setAnimation("fee-vole-gauche-pouvoir.png",CHAR_W,CHAR_H);
		}
		else if(isFat()){
			setAnimation("grosse-vache.png",CHAR_W,CHAR_H);
		}
		else if(!isFlying()){
			setAnimation("fee-de-face.png",CHAR_W,CHAR_H);
		}
		else if(isFlying()){
			setAnimation("fee-de-face-pouvoir-1.png",CHAR_W,CHAR_H);
		}
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////// GETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	public float getCharWidth(){
		return this.w;
	}
	public float getCharHeight(){
		return this.h;
	}
	public int getCharBodyWidth(){
		return (int) (this.w*CHAR_W_BODY_RATIO);		
	}
	public int getCharBodyHeight(){
		return (int) (this.h*CHAR_H_BODY_RATIO);		
	}
	public int getBouton(){
		return boutonpressoir;
	}

	/////////////////////////////////////////////////////////
	///////////////////////// POWERS ////////////////////////
	/////////////////////////////////////////////////////////
	public boolean isFlying(){
		return power==Power.FLYING;
	}
	public boolean isIntangible(){
		return power==Power.INTANGIBLE;
	}
	public boolean isFat(){
		return power==Power.FAT;
	}
	public boolean isPetit(){
		return power==Power.SMALL;
	}
	public boolean isFire(){
		return power==Power.FIRE;
	}
	public boolean isRebond(){
		return power==Power.REBOND;
	}
	public boolean canSwim(){
		return power==Power.SWIM;
	}
	public boolean isInvisible(){
		return power==Power.INVISIBLE;
	}
	public boolean isDead(){
		return isDead;
	}
	public boolean isLight(){
		return power==Power.LIGHT;
	}
	public boolean isDestructor(){
		return power==Power.DESTRUCTOR;
	}
	public boolean canDestruct(){
		return power==Power.DESTRUCTOR && destructionPowerActivated;
	}
	public boolean canTeleport(){
		return power==Power.TELEPORTATION;
	}
	public boolean absorbe(){
		return power==Power.ABSORB;
	}
	public boolean dedouble(){
		return power==Power.DOUBLE;
	}
	public Power getPower(){
		return power;
	}
	public boolean isSwimming(){
		return isSwimming;
	}
	public boolean isAtExit(){
		return isAtExit;
	}
	public boolean isTransported(){
		return isTransported;
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////// SETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	public void setDead(boolean _dead){
		isDead = _dead;
	}
	public void setAtExit(boolean b){
		isAtExit = b;
	}
	public void setTransported(boolean b, Vec2 destination){
		isTransported = b;
		X_transported = (int) destination.x;
		Y_transported = (int) destination.y;
	}
	public void setTransported(boolean b, int x, int y){
		isTransported = b;
		X_transported=x;
		Y_transported=y;
	}
	public void setPower(Power _power){
		if((_power==Power.SMALL && power!=Power.SMALL) ||(power==Power.SMALL && _power!=Power.SMALL)){
			shouldChangeSize=true;
		}
		else if(((_power==Power.REBOND) && (power!=Power.REBOND))){
			shouldRebond=true;
		}
		else if(((_power==Power.SWIM) && (power!=Power.SWIM))){
			isSwimming=true;
		}
		
		power = _power;
		changePower();
	}
	public void setJumpAfterRebound(boolean flag){
		this.shouldJumpAfterRebound = flag;
	}
	public void setIsSwimming(boolean flag){
		this.isSwimming = flag;
	}
	public boolean shouldJumpAfterRebound(){
		return shouldJumpAfterRebound;
	}
	public void setBouton(int n){
		boutonpressoir=n;
	}
	 
}
