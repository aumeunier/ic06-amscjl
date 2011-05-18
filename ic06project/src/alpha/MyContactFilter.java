package alpha;

import org.jbox2d.collision.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactFilter;

public class MyContactFilter implements ContactFilter {

	@Override
	public boolean shouldCollide(Shape shape1, Shape shape2) {
		Body b1 = shape1.getBody();
		Body b2 = shape2.getBody();
		
		/* Collisions concernant le groundsensor */
		// Premier cas b1 est le groundsensor
		if(shape1.getUserData()==GameplayState.GROUND_SENSOR_NAME){
			Sprite s2 = (Sprite)b2.getUserData();
			if(s2.getClass().equals(BoutonPressoir.class)){
				((BoutonPressoir)s2).activate();					
			}
		}
		// Deuxieme cas b2 est le groundsensor
		else if(shape2.getUserData()==GameplayState.GROUND_SENSOR_NAME){
			Sprite s1 = (Sprite)b1.getUserData();
			if(s1.getClass().equals(BoutonPressoir.class)){
				((BoutonPressoir)s1).activate();					
			}				
		}
		
		/* Collisions concernant le personnage */
		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			// Premier cas s1 est ...
			if(s1.getClass().equals(Character.class)){
				if(((Character)s1).isIntangible()
						&& (s2.getClass().equals(Obstacle.class))){
					return false;
				}
				else if(s2.getClass().equals(SourceMortelle.class)){
					((Character)(s1)).setDead(true);
				}
				else if(s2.getClass().equals(Exit.class)&&shape1.getUserData()!=GameplayState.GROUND_SENSOR_NAME){
					((Exit)s2).ajoutCollision();
				}
				else if(s2.getClass().equals(Source.class)){
					Source source = (Source)s2;
					Character character = (Character)s1;
					if(source.power == Power.INTANGIBLE){
						character.setPower(Power.INTANGIBLE);
					}
					else if(source.power == Power.FLYING){
						character.setPower(Power.FLYING);
					}
					return false;
				}
				else if(s2.getClass().equals(Character.class)){
					return false;
				}
				else if(s2.getClass().equals(Obstacle.class) && ((Obstacle)s2).isHidden()){
					return false;
				}
				else if(s2.getClass().equals(Levier.class)){
					((Levier)s2).activate();
					return false;
				}
				else if(s2.getClass().equals(Bonus.class)){
					((Bonus)s2).obtained();
				}
			}
			// Deuxieme cas s2 est ...
			else if(s2.getClass().equals(Character.class)){
				if(((Character)s2).isIntangible()
						&& (s1.getClass().equals(Obstacle.class))){
					return false;
				}
				else if(s1.getClass().equals(SourceMortelle.class)){
					((Character)(s2)).setDead(true);					
				}
				else if(s1.getClass().equals(Exit.class)&&shape2.getUserData()!=GameplayState.GROUND_SENSOR_NAME){
					((Exit)s1).ajoutCollision();
				}
				else if(s1.getClass().equals(Source.class)){
					Source source = (Source)s1;
					Character character = (Character)s2;
					if(source.power == Power.INTANGIBLE){
						character.setPower(Power.INTANGIBLE);
					}
					else if(source.power == Power.FLYING){
						character.setPower(Power.FLYING);
					}
					return false;
				}
				else if(s1.getClass().equals(Obstacle.class) && ((Obstacle)s1).isHidden()){
					return false;
				}
				else if(s1.getClass().equals(Levier.class)){
					((Levier)s1).activate();
					return false;
				}
				else if(s1.getClass().equals(Bonus.class)){
					((Bonus)s1).obtained();
				}
			}
			
		}
		return true;
	}

}
