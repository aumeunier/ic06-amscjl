package beta;

import org.jbox2d.collision.Shape;
import org.jbox2d.common.Vec2;
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
			if(s2 instanceof BoutonPressoir){
				((Character)b1.getUserData()).setBouton(((BoutonPressoir)s2).getNum());
				System.out.println(((BoutonPressoir)s2).getNum());
				((BoutonPressoir)s2).check();
			}

		}
		// Deuxieme cas b2 est le groundsensor
		else if(shape2.getUserData()==GameplayState.GROUND_SENSOR_NAME){
			Sprite s1 = (Sprite)b1.getUserData();
			if(s1 instanceof BoutonPressoir){
				((Character)b2.getUserData()).setBouton(((BoutonPressoir)s1).getNum());
				System.out.println(((BoutonPressoir)s1).getNum());
				((BoutonPressoir)s1).check();
			}			
		}

		/* Collisions concernant le personnage */
		else {
			if(b1.getUserData()!=null && b2.getUserData()!=null){
				Sprite s1 = (Sprite)b1.getUserData();
				Sprite s2 = (Sprite)b2.getUserData();
				// Premier cas s1 est ...
				if(s1.getClass().equals(Character.class)){
					if(((Character)s1).isIntangible()
							&& (s2.getClass().equals(Obstacle.class))){
						return false;
					}
					else if(s2.isHidden()){
						return false;
					}
					else if((s2.getClass().equals(SourceMortelle.class))&&(((Character)(s1)).getPower()!= Power.NAGE)){
						((Character)(s1)).setDead(true);
						return false;
					}
					else if((s2.getClass().equals(SourceMortelle.class)) && (((Character)(s2)).getPower()== Power.NAGE)){
						((Character)(s2)).isFalling=false;
						return false;
					}
					else if(s2.getClass().equals(Source.class)){
						Source source = (Source)s2;
						Character character = (Character)s1;
						character.setPower(source.power);
						return false;
					}
					else if(s2.getClass().equals(Transporter.class)){
						((Character)s1).setTransported(true,((Transporter)s2).new_x,((Transporter)s2).new_y);
						return true;
					}
					else if(s2.getClass().equals(Character.class)){
						if(((Character)s1).absorbe())
							((Character)s1).setPower(((Character)s2).getPower());
						else if(((Character)s2).absorbe())
							((Character)s2).setPower(((Character)s1).getPower());
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
					else if(s1.isHidden()){
						return false;
					}
					else if((s1.getClass().equals(SourceMortelle.class)) && (((Character)(s2)).getPower()!= Power.NAGE)){
						((Character)(s2)).setDead(true);	
						return false;
					}
					else if((s1.getClass().equals(SourceMortelle.class)) && (((Character)(s2)).getPower()== Power.NAGE)){
						((Character)(s2)).isFalling=false;		
						return false;
					}
					else if(s1.getClass().equals(Source.class)){
						Source source = (Source)s1;
						Character character = (Character)s2;
						character.setPower(source.power);
						return false;
					}
					else if(s1.getClass().equals(Transporter.class)){
						((Character)s2).setTransported(true,((Transporter)s1).new_x,((Transporter)s1).new_y);
						return true;
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
		}
		return true;
	}

}
