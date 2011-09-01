package version1.sprites.interaction;

import version1.Global;
import version1.sprites.Sprite;

/**
 * A Bonus is an element we can collect.
 * If all the bonuses are collected in the game, the
 */
public class Bonus extends Sprite {
	private boolean obtained = false; /** True if the bonus has already been collected */
	
	/** Constructor to use. Position the bonus and set the default image (a red cherry) */
	public Bonus(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.image = Global.getImage("cerise_rouge.png");
	}
	
	/** The bonus has been obtained so we should remove it from the scene */
	public void obtained(){
		this.obtained = true;
		this.setShouldBeDestroy();
	}
	/** @return Whether the bonus has been obtained yet */
	public boolean isObtained(){
		return obtained;
	}	
	
}