package alpha;

import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

public class MyContactListener implements ContactListener {

	@Override
	public void add(ContactPoint point) {
		if (point.shape1.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		if (point.shape2.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape2.getBody().getUserData()).isFalling = false;
			}
		}
	}

	@Override
	public void persist(ContactPoint point) {
		if (point.shape1.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)|| (s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		if (point.shape2.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape2.getBody().getUserData()).isFalling = false;
			}
		}
	}

	@Override
	public void remove(ContactPoint point) {
		Object o1 = point.shape1.getUserData();
		Object o2 = point.shape2.getUserData();
		if (o1 == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape1.getBody().getUserData()).isFalling = true;
			}
		}
		else if (o2 == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape2.getBody().getUserData()).isFalling = true;
			}
		}
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();
			if(s1 instanceof BoutonPressoir && s2 instanceof Character){
				((BoutonPressoir)s1).desactivate();
			}
			else if(s1 instanceof Character && s2 instanceof BoutonPressoir){
				((BoutonPressoir)s2).desactivate();
			}
			
			else if(s1 instanceof Exit && s2 instanceof Character){
				((Exit)s1).supprCollision();
			}
			else if(s1 instanceof Character && s2 instanceof Exit){
				((Exit)s2).supprCollision();
			}

		}
	}

	@Override
	public void result(ContactResult point) {
	}

}
