package gold;

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
				if(s instanceof Ground && ((Ground)s).getSlippery()){
					if(((Character) point.shape1.getBody().getUserData()).getPower()==Power.FIRE)
						((Ground)s).setSlippery(false);
					else{
						((Character) point.shape1.getBody().getUserData()).isSlipping = true;
						if(((Ground)s).sens=="left")
							((Character) point.shape1.getBody().getUserData()).goLeft();
						else
							((Character) point.shape1.getBody().getUserData()).goRight();
					}
				}
				else
					((Character) point.shape1.getBody().getUserData()).isSlipping = false;
				((Character) point.shape1.getBody().getUserData()).isFalling = false;
			}
		}
		else if (point.shape2.getUserData() == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				if(s instanceof Ground && ((Ground)s).getSlippery()){
					if(((Character) point.shape2.getBody().getUserData()).getPower()==Power.FIRE)
						((Ground)s).setSlippery(false);
					else{
						((Character) point.shape2.getBody().getUserData()).isSlipping = true;
						if(((Ground)s).sens=="left")
							((Character) point.shape2.getBody().getUserData()).goLeft();
						else
							((Character) point.shape2.getBody().getUserData()).goRight();
					}
				}
				else
					((Character) point.shape2.getBody().getUserData()).isSlipping = false;
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
			else if((s1 instanceof Wall || s1 instanceof Obstacle || s1 instanceof Ground
					|| s1 instanceof BoutonPressoir || s1 instanceof BoutonElevator)
					&& s2 instanceof Character){
				((Character)s2).isColliding = true;
			}
			else if(s1 instanceof Character && 
					(s2 instanceof Wall || s2 instanceof Obstacle || s2 instanceof Ground
					|| s2 instanceof BoutonPressoir || s2 instanceof BoutonElevator)){
				((Character)s1).isColliding = true;
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
		//*
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();

			if((s1 instanceof Wall || s1 instanceof Obstacle || s1 instanceof Ground) && s2 instanceof Character){
				((Character)s2).isColliding = true;
			}
			else if(s1 instanceof Character && (s2 instanceof Wall || s2 instanceof Obstacle || s2 instanceof Ground)){
				((Character)s1).isColliding = true;
			}
		}
		//*/
	}

	@Override
	public void remove(ContactPoint point) {
		Object o1 = point.shape1.getUserData();
		Object o2 = point.shape2.getUserData();
		if (o1 == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape2.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				if((s instanceof Ground)&& (((Ground)s).sens!=null) && ((Character)point.shape1.getBody().getUserData()).isFire())
					((Ground)s).setSlippery(true);
				((Character) point.shape1.getBody().getUserData()).isFalling = true;
				((Character) point.shape1.getBody().getUserData()).isColliding = false;
			}	
		}
		else if (o2 == GameplayState.GROUND_SENSOR_NAME){
			Sprite s = (Sprite)point.shape1.getBody().getUserData();
			if((s instanceof Wall) || (s instanceof Obstacle)||(s instanceof Ground)|| (s instanceof Declencheur)){
				if((s instanceof Ground)&& (((Ground)s).sens!=null)&&((Character)point.shape2.getBody().getUserData()).isFire())
					((Ground)s).setSlippery(true);
				((Character) point.shape2.getBody().getUserData()).isFalling = true;
				((Character) point.shape2.getBody().getUserData()).isColliding = false;
			}
		}
		else {
			Sprite s1 = (Sprite)point.shape1.getBody().getUserData();
			Sprite s2 = (Sprite)point.shape2.getBody().getUserData();

			// Character not on BouttonPressoir anymore
			if(s1 instanceof BoutonPressoir && s2 instanceof Character){
				((Character)s2).setBouton(0);
				((BoutonPressoir)s1).check();			
			}
			else if(s1 instanceof Character && s2 instanceof BoutonPressoir){
				((Character)s1).setBouton(0);
				((BoutonPressoir)s2).check();		
			}

			// Character not in Exit body anymore
			/*else if(s1 instanceof Exit && s2 instanceof Character){
				if (!s1.rectCollideWithOther(s2)){
					((Character)s2).setAtExit(false);
				}
			}
			else if(s1 instanceof Character && s2 instanceof Exit){
				if (!s1.rectCollideWithOther(s2)){
					((Character)s1).setAtExit(false);
				}
			}*/

			/*
			// Character collides with ground or wall or obstacle
			//else {
				// FIXME: parfois, si personnage en contact avec deux obstacles,
				// les deux obstacles se bloquent mutuellement
				if((s1 instanceof Wall || s1 instanceof Obstacle || s1 instanceof Ground
						|| s1 instanceof Declencheur)
						&& (s2.getClass().equals(Character.class)) ){
					boolean noHardCollision = true;
					for(ContactPoint c: contactPoints){
						Body c1 = c.shape1.getBody();
						Body c2 = c.shape2.getBody();
						if(c1!= null && c2!=null){
							Sprite temp1 = (Sprite)c1.getUserData();
							Sprite temp2 = (Sprite)c2.getUserData();
							if((((temp1 instanceof Wall || temp1 instanceof Obstacle || temp1 instanceof Ground 
									|| temp1 instanceof Declencheur)
									&& temp2.getClass().equals(Character.class))
									&& (temp1!=s1 && temp2==s2))
								|| ((temp2 instanceof Wall || temp2 instanceof Obstacle || temp2 instanceof Ground
										|| temp2 instanceof Declencheur)
										&& temp1.getClass().equals(Character.class))
										&& (temp2!=s1 && temp1==s2)){
								noHardCollision = false;
							}
						}	
					}
					if(noHardCollision){
						((Character)s2).isColliding = false;
					}		
				}
				else if((s1.getClass().equals(Character.class)) 
						&& (s2 instanceof Wall || s2 instanceof Obstacle || s2 instanceof Ground 
								|| s2 instanceof Declencheur)){
					boolean noHardCollision = true;
					for(ContactPoint c: contactPoints){
						Body c1 = c.shape1.getBody();
						Body c2 = c.shape2.getBody();
						if(c1!= null && c2!=null){
							Sprite temp1 = (Sprite)c1.getUserData();
							Sprite temp2 = (Sprite)c2.getUserData();
							if((((temp1 instanceof Wall || temp1 instanceof Obstacle || temp1 instanceof Ground
									|| temp1 instanceof Declencheur)
									&& temp2.getClass().equals(Character.class))
									&& (temp1!=s2 && temp2==s1))
								|| ((temp2 instanceof Wall || temp2 instanceof Obstacle || temp2 instanceof Ground
										|| temp2 instanceof Declencheur)
										&& temp1.getClass().equals(Character.class))
										&& (temp2!=s2 && temp1==s1)){
								noHardCollision = false;
							}
						}	
					}	
					if(noHardCollision){
						((Character)s1).isColliding = false;
					}					
				}
			}	
		 */		
		}
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
