package gold;

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
			if(s2 instanceof BoutonPressoir){
				((Character)b1.getUserData()).setBouton(((BoutonPressoir)s2).getNum());
				((BoutonPressoir)s2).check();
			}
		}
		// Deuxieme cas b2 est le groundsensor
		else if(shape2.getUserData()==GameplayState.GROUND_SENSOR_NAME){
			Sprite s1 = (Sprite)b1.getUserData();
			if(s1 instanceof BoutonPressoir){
				((Character)b2.getUserData()).setBouton(((BoutonPressoir)s1).getNum());
				((BoutonPressoir)s1).check();
			}		
		}
		Object b1d = b1.getUserData();
		Object b2d = b2.getUserData();
		if((b1d!=null && b2d!=null)
				&& (b1d instanceof Sprite && b2d instanceof Sprite)
				&& (((b1d instanceof Destructible) && !b2d.getClass().equals(Character.class))
						|| ((b2d instanceof Destructible) && !b1d.getClass().equals(Character.class)))){
			Destructible d = (Destructible) ((b1d instanceof Destructible)?b1d:b2d);
			Sprite other = (Sprite) ((b1d instanceof Destructible)?b2d:b1d);
			if(other instanceof Monster){
				if(d.isDeadly()){
					((Monster)other).loseHealth(1);	
				}				
			}
			else if(other.getClass().equals(Wall.class)){
				d.setDeadly(false);
			}
		}

		else {
			if(b1d!=null && b2d!=null){
				Sprite s1 = (Sprite)b1d;
				Sprite s2 = (Sprite)b2d;

				/* Collisions concernant les personnage */	
				if(s1.getClass().equals(Character.class) || s2.getClass().equals(Character.class)){
					Character character = null;
					Sprite other = null;
					if(s1.getClass().equals(Character.class)){
						character = (Character)s1;
						other = s2;
					}
					else {
						character = (Character)s2;
						other = s1;						
					}					
					
					if(other.isHidden()){
						return false;
					}
					else if(other.getIndication()!=null ){
						other.activateIndication();
					}
					if(character.isIntangible()
							&& (other.getClass().equals(Obstacle.class))){
						return false;
					}
					else if((other.getClass().equals(Monster.class))&& (character.getPower() != Power.INVISIBLE)){
						character.setDead(true);
						return false;
					}
					else if((other.getClass().equals(Monster.class))&& (character.getPower() == Power.INVISIBLE)){
						return false;
					}
					else if((other.getClass().equals(SourceMortelle.class))&& (character.getPower()!= Power.NAGE)){
						character.setDead(true);
						return false;
					}
					else if((other.getClass().equals(SourceMortelle.class)) && (character.getPower()== Power.NAGE)){
						character.isFalling=false;
						return false;
					}
					else if((other.getClass().equals(Destructible.class)) && (((Destructible)other).isDeadly())){
						character.setDead(true);
						return false;
					}
					else if((other.getClass().equals(Destructible.class)) && (character.getPower()== Power.DESTRUCTOR)){
						other.shouldBeDestroy=true;
						((Destructible)other).setDestructiblesDeadly();
						character.isFalling=false;
						return false;
					}
					else if(other.getClass().equals(IndicationSprite.class)){
						((IndicationSprite)other).activateIndication();
						other.setShouldBeDestroy();
						return false;
					}
					else if(other.getClass().equals(Source.class)){
						Source source = (Source)other;
						character.setPower(source.power);
						return false;
					}
					else if(other.getClass().equals(Transporter.class)){
						character.setTransported(true,((Transporter)other).new_x,((Transporter)other).new_y);
						return true;
					}
					else if(other.getClass().equals(Character.class)){
						if(character.absorbe())
							character.setPower(character.getPower());
						else if(character.absorbe())
							character.setPower(character.getPower());
						return false;
					}
					else if(other.getClass().equals(Levier.class)){
						((Levier)other).activate();
						return false;
					}
					else if(other.getClass().equals(LevierCombi.class)){
						((LevierCombi)other).activate();
						return false;
					}
					else if(other.getClass().equals(Bonus.class)){
						((Bonus)other).obtained();
						return false;						
					}
				}
				
				// Les IndicationSprite ne collide pas
				else {
					if(s1.getClass().equals(IndicationSprite.class)){
						return false;
					}
					else if(s2.getClass().equals(IndicationSprite.class)){
						return false;
					}
					if(s1 instanceof Witch && s2 instanceof Ground){
						((Witch)s1).inverseXspeed();
						return false;
					}
					else if(s2 instanceof Witch && s1 instanceof Ground){
						((Witch)s2).inverseXspeed();
						return false;
					}
				}
			}
		}
		return true;
	}

}

