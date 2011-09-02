package version1.sprites.interaction.trigger.buttons;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import version1.Global;
import version1.sprites.Sprite;
import version1.sprites.interaction.MissilePlatform;

/**
 * A PushButtonMove can move elements when pushed
 *
 */
public class PushButtonMove extends PushButton {
	/** The direction of the movement applied to the body */
	protected String direction;
	/** Used to slow tha pace down */
	protected boolean dirtyMove;

	public PushButtonMove(int _x, int _y, int _w, int _h, ArrayList<Body> b, String s){
		super(_x, _y, _w, _h, b, 1);
		direction = s;
		dirtyMove = false;
	}
	
	public String getDirection(){
		return direction;
	}
	public boolean getShouldMove(){
		dirtyMove=!dirtyMove;
		return dirtyMove;
	}
	public void move(){
		if(getShouldMove()) {
			Vec2 b2dcoord;
			int newX, newY;
			for(Body relatedBody: relatedBodies){
				MissilePlatform plateforme=(MissilePlatform)(relatedBody.getUserData());
				//Vec2 b2dcoord = Global.getBox2DCoordinates(((PlateformeMissile)theSprite).xMove(), ((PlateformeMissile)theSprite).Y());
				if(getDirection()=="left") {
					newX = plateforme.X()-1;
					if(newX<plateforme.getMinX()) {newX = plateforme.getMinX();}
				}
				else if(getDirection()=="right"){
					newX=plateforme.X()+1;
					newY = plateforme.Y();
					//if(newX>800-plateforme.W()) {newX=800-plateforme.W();}
					if(newX>plateforme.getMaxX()) {newX = plateforme.getMaxX();}
				}
				else {
					newX = plateforme.X();
				}
				newY=plateforme.Y();
				b2dcoord = Global.getBox2DCoordinates(newX, newY);
				Vec2 position = new Vec2(b2dcoord.x+plateforme.W()/2, b2dcoord.y-plateforme.H()/2);
				relatedBody.setXForm(position,0);
				if(plateforme.getMissile()!=null) {
					Vec2 pos2 = new Vec2(b2dcoord.x+plateforme.W()/2, b2dcoord.y-plateforme.H() -((Sprite)(plateforme.getMissile().getUserData())).H()/2);
					plateforme.getMissile().setXForm(pos2, 0);
				}
			}
		}
	}
	
	@Override
	public void enable(){
		this.setEnabled(true);	
		this.updateImage();
	}
	@Override
	public void disable(){
		this.setEnabled(false);
		this.updateImage();
	}
}
