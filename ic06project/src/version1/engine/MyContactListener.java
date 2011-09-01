package version1.engine;

import java.util.ArrayList;

import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

import version1.sprites.Sprite;
import version1.sprites.alive.Creature;
import version1.sprites.decor.Ground;
import version1.sprites.decor.Obstacle;
import version1.sprites.decor.Wall;
import version1.sprites.interaction.Exit;
import version1.sprites.interaction.trigger.Trigger;
import version1.sprites.interaction.trigger.buttons.PushButtonElevator;
import version1.sprites.interaction.trigger.buttons.PushButton;
import version1.states.GameplayState;

/**
 * This class inherits from the <code>ContactListener</code> class of the JBox2D library.
 * We use it to get notifications from current collisions. 
 * Notifications include: addition of a point, removal of a point, continous checking
 *
 */
public class MyContactListener implements ContactListener {
	private ArrayList<ContactPoint> contactPoints = new ArrayList<ContactPoint>();

	@Override
	public void add(ContactPoint point) {
		this.contactPoints.add(point);
		// Get contact information about the ground sensor: is it on a slippery ground ?
		Object data1 = point.shape1.getUserData();
		Object data2 = point.shape2.getUserData();
		if(data1 == GameplayState.GROUND_SENSOR_NAME
				|| data2 == GameplayState.GROUND_SENSOR_NAME){
			Sprite sprite = null;
			Creature player = null;
			if(data1 == GameplayState.GROUND_SENSOR_NAME){
				player = (Creature)point.shape1.getBody().getUserData();
				sprite = (Sprite)point.shape2.getBody().getUserData();
			}
			else if(data2 == GameplayState.GROUND_SENSOR_NAME){
				player = (Creature)point.shape2.getBody().getUserData();
				sprite= (Sprite)point.shape1.getBody().getUserData();
			}
			if((sprite instanceof Wall) || (sprite instanceof Obstacle)
					||(sprite instanceof Ground)|| (sprite instanceof Trigger)){
				if(sprite instanceof Ground && ((Ground)sprite).getSlippery()){
					// If a player is on a slippery ground but has the fire power, it removes the slippery effect
					if(((Creature)player).isFire()) {
						((Ground)sprite).setSlippery(false);						
					}
					// Otherwise, it slides in the given direction
					else{
						player.isSlipping = true;
						if(((Ground)sprite).getDirectionIsLeft()){
							player.goLeft();
						}
						else{
							player.goRight();
						}
					}
				}
				// If the player is on the ground but it's not slippery, it doesn't slide
				else {
					player.isSlipping = false;
				}
				// If the player is on the ground, it is not falling
				player.isFalling = false;
			}
		}
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();

			// Is it at the exit ?
			if(s1 instanceof Exit && s2 instanceof Creature){
				((Creature)s2).setAtExit(true);
			}
			else if(s1 instanceof Creature && s2 instanceof Exit){
				((Creature)s1).setAtExit(true);
			}
			// Is it colliding with an element ?
			else if((s1 instanceof Wall || s1 instanceof Obstacle || s1 instanceof Ground
					|| s1 instanceof PushButton || s1 instanceof PushButtonElevator)
					&& s2 instanceof Creature){
				((Creature)s2).isColliding = true;
			}
			else if(s1 instanceof Creature && 
					(s2 instanceof Wall || s2 instanceof Obstacle || s2 instanceof Ground
					|| s2 instanceof PushButton || s2 instanceof PushButtonElevator)){
				((Creature)s1).isColliding = true;
			}
		}
	}

	@Override
	public void persist(ContactPoint point) {
		// Collision with the ground
		if (point.shape1.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Trigger)){
				((Creature) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		else if (point.shape2.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Trigger)){
				((Creature) point.shape2.getBody().getUserData()).isFalling = false;
			}
		}
		// Collision with obstacles
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();

			if((s1 instanceof Wall || s1 instanceof Obstacle || s1 instanceof Ground) && s2 instanceof Creature){
				((Creature)s2).isColliding = true;
			}
			else if(s1 instanceof Creature && (s2 instanceof Wall || s2 instanceof Obstacle || s2 instanceof Ground)){
				((Creature)s1).isColliding = true;
			}
		}
	}

	@Override
	public void remove(ContactPoint point) {
		Object o1 = point.shape1.getUserData();
		Object o2 = point.shape2.getUserData();
		// Contact with the ground or obstacles
		// The character is probably not falling nor colliding anymore (if still, will be changed in persists)
		if (o1 == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Trigger)){
				if((s instanceof Ground)&& (((Ground)s).getOriginalySlippery()) 
						&& ((Creature)point.shape1.getBody().getUserData()).isFire()) {
					((Ground)s).setSlippery(true);
				}
				((Creature) point.shape1.getBody().getUserData()).isFalling = true;
				((Creature) point.shape1.getBody().getUserData()).isColliding = false;
			}	
		}
		else if (o2 == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Trigger)){
				if((s instanceof Ground)&& (((Ground)s).getOriginalySlippery())
						&& ((Creature)point.shape2.getBody().getUserData()).isFire()) {
					((Ground)s).setSlippery(true);
				}
				((Creature) point.shape2.getBody().getUserData()).isFalling = true;
				((Creature) point.shape2.getBody().getUserData()).isColliding = false;
			}
		}
		// Creature not on Push Button anymore
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();

			if(s1 instanceof PushButton && s2 instanceof Creature){
				((Creature)s2).setBouton(0);
				((PushButton)s1).check();			
			}
			else if(s1 instanceof Creature && s2 instanceof PushButton){
				((Creature)s1).setBouton(0);
				((PushButton)s2).check();		
			}
		}
		// Remove the contact point from our own list
		for(int i = contactPoints.size() - 1; i >= 0; --i){
			ContactPoint p = contactPoints.get(i);
			if((point.shape1.equals(p.shape1) && point.shape2.equals(p.shape2))
					|| (point.shape2.equals(p.shape1) && point.shape1.equals(p.shape2))){
				contactPoints.remove(i);
			}	
		}
	}

	@Override
	public void result(ContactResult point) { }
}
