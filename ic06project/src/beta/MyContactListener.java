package beta;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

public class MyContactListener implements ContactListener {
	private ArrayList<ContactPoint> contactPoints = new ArrayList<ContactPoint>();

	@Override
	public void add(ContactPoint point) {
		this.contactPoints.add(point);
		if (point.shape1.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		else if (point.shape2.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape2.getBody().getUserData()).isFalling = false;
			}
		}
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();

			if(s1 instanceof Exit && s2 instanceof Character){
				((Character)s2).setAtExit(true);
			}
			else if(s1 instanceof Character && s2 instanceof Exit){
				((Character)s1).setAtExit(true);
			}
		}
	}

	@Override
	public void persist(ContactPoint point) {
		if (point.shape1.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		else if (point.shape2.getUserData() == GameplayState.GROUND_SENSOR_NAME){
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

			// Character not on BouttonPressoir anymore
			if(s1 instanceof BoutonPressoir && s2 instanceof Character){
				boolean noOneOnButton = true;
				for(ContactPoint c: contactPoints){
					Body c1 = c.shape1.getBody();
					Body c2 = c.shape2.getBody();
					if(c1!= null && c2!=null){
						Sprite temp1 = (Sprite)c1.getUserData();
						Sprite temp2 = (Sprite)c2.getUserData();
						if(((temp1 instanceof BoutonPressoir && temp2 instanceof Character)
								&& (temp1!=s1 ||temp2!=s2))
							|| ((temp2 instanceof BoutonPressoir && temp1 instanceof Character)
								&& (temp2!=s1 ||temp1!=s2))){
							//System.out.println(temp1.toString()+temp2.toString());
							noOneOnButton = false;
						}
					}			
				}
				if(noOneOnButton){
					((BoutonPressoir)s1).desactivate();
				}		
			}
			else if(s1 instanceof Character && s2 instanceof BoutonPressoir){
				boolean noOneOnButton = true;
				for(ContactPoint c: contactPoints){
					Body c1 = c.shape1.getBody();
					Body c2 = c.shape2.getBody();
					if(c1!= null && c2!=null){
						Sprite temp1 = (Sprite)c1.getUserData();
						Sprite temp2 = (Sprite)c2.getUserData();
						if(((temp1 instanceof BoutonPressoir && temp2 instanceof Character)
								&& (temp1!=s2 || temp2!=s1))
							|| ((temp2 instanceof BoutonPressoir && temp1 instanceof Character)
								&& (temp1!=s1 || temp2!=s2))){
							noOneOnButton = false;
						}
					}	
				}
				if(noOneOnButton){
					((BoutonPressoir)s2).desactivate();
				}		
			}

			// Character not in Exit body anymore
			else if(s1 instanceof Exit && s2 instanceof Character){
				if (!s1.rectCollideWithOther(s2)){
					((Character)s2).setAtExit(false);
				}
			}
			else if(s1 instanceof Character && s2 instanceof Exit){
				if (!s1.rectCollideWithOther(s2)){
					((Character)s1).setAtExit(false);
				}
			}
		}
		int indexOfPoint = -1;
		for(int i = 0 ; i < contactPoints.size() ; ++i){
			ContactPoint p = contactPoints.get(i);
			if((point.shape1.equals(p.shape1) && point.shape2.equals(p.shape2))
					|| (point.shape2.equals(p.shape1) && point.shape1.equals(p.shape2))){
				indexOfPoint = i;		
			}
		}
		if(indexOfPoint > -1){
			contactPoints.remove(indexOfPoint);
		}
	}

	@Override
	public void result(ContactResult point) { }
}
