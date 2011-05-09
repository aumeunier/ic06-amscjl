package protoWithSlick;

import org.jbox2d.collision.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

public class MyContactListener implements ContactListener {

	@Override
	public void add(ContactPoint point) {
		if (point.shape1.getUserData() == "groundsensor"){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)){
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		if (point.shape2.getUserData()=="groundsensor"){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)){
				((Character) point.shape2.getBody().getUserData()).isFalling = false;
			}
		}
	}

	@Override
	public void persist(ContactPoint point) {
		if (point.shape1.getUserData() == "groundsensor"){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)){
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		if (point.shape2.getUserData()=="groundsensor"){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)){
				((Character) point.shape2.getBody().getUserData()).isFalling = false;
			}
		}
	}

	@Override
	public void remove(ContactPoint point) {
		if (point.shape1.getUserData() == "groundsensor"){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)){
				((Character) point.shape1.getBody().getUserData()).isFalling = true;
			}
		}
		if (point.shape2.getUserData()=="groundsensor"){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)){
				((Character) point.shape2.getBody().getUserData()).isFalling = true;
			}
		}
	}

	@Override
	public void result(ContactResult point) {
	}

}
