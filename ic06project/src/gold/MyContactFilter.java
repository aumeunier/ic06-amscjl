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
				&& ((b1d.getClass().equals(Destructible.class) && !b2d.getClass().equals(Character.class))
						|| (b2d.getClass().equals(Destructible.class) && !b1d.getClass().equals(Character.class)))){
			if((b1d.getClass().equals(Monster.class) && b2d.getClass().equals(Destructible.class))){
				if(((Destructible)b2d).isDeadly()){
					((Monster)b1d).setDead(true);
					((Monster)b1d).shouldBeDestroy=true;	
				}
			}
			else if((b2d.getClass().equals(Monster.class) && b1d.getClass().equals(Destructible.class))){
				if(((Destructible)b1d).isDeadly()){
					((Monster)b2d).setDead(true);
					((Monster)b2d).shouldBeDestroy=true;
				}				
			}
			else if((b1d.getClass().equals(Wall.class) && b2d.getClass().equals(Destructible.class))){
				((Destructible)b2d).setDeadly(false);
			}
			else if((b2d.getClass().equals(Wall.class) && b1d.getClass().equals(Destructible.class))){
				((Destructible)b1d).setDeadly(false);				
			}
		}

		/* Collisions concernant le personnage */
		else {
			if(b1d!=null && b2d!=null){
				Sprite s1 = (Sprite)b1d;
				Sprite s2 = (Sprite)b2d;
				// Premier cas s1 est ...
				
				if(s1.getClass().equals(Character.class)){
					if(s2.isHidden()){
						return false;
					}
					else if(s2 instanceof Sprite && s2.getIndication()!=null ){
						System.out.println(s2.getClass());
						s2.activateIndication();
					}
					if(((Character)s1).isIntangible()
							&& (s2.getClass().equals(Obstacle.class))){
						return false;
					}
					else if((s2.getClass().equals(Monster.class))&&(((Character)(s1)).getPower() != Power.INVISIBLE)){
						((Character)(s1)).setDead(true);
						return false;
					}
					else if((s2.getClass().equals(Monster.class))&&(((Character)(s1)).getPower() == Power.INVISIBLE)){
						return false;
					}
					else if((s2.getClass().equals(SourceMortelle.class))&&(((Character)(s1)).getPower()!= Power.NAGE)){
						((Character)(s1)).setDead(true);
						return false;
					}
					else if((s2.getClass().equals(SourceMortelle.class)) && (((Character)(s1)).getPower()== Power.NAGE)){
						((Character)(s1)).isFalling=false;
						return false;
					}
					else if((s2.getClass().equals(Destructible.class)) && (((Destructible)s2).isDeadly())){
						((Character)s1).setDead(true);
						return false;
					}
					else if((s2.getClass().equals(Destructible.class)) && (((Character)(s1)).getPower()== Power.DESTRUCTOR)){
						s2.shouldBeDestroy=true;
						((Destructible)s2).setDestructiblesDeadly();
						((Character)(s1)).isFalling=false;
						return false;
					}
					else if(s2.getClass().equals(IndicationSprite.class)){
						((IndicationSprite)s2).activateIndication();
						s2.setShouldBeDestroy();
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
					else if(s2.getClass().equals(LevierCombi.class)){
						((LevierCombi)s2).activate();
						return false;
					}
					else if(s2.getClass().equals(Bonus.class)){
						((Bonus)s2).obtained();

						
					}
				}
				// Deuxieme cas s2 est ...
				else if(s2.getClass().equals(Character.class)){
					if(s1.isHidden()){
						return false;
					}
					else if(s1 instanceof Sprite && s1.getIndication()!=null){
						System.out.println(s1.getClass());
						s1.activateIndication();
					}
					if(((Character)s2).isIntangible()
							&& (s1.getClass().equals(Obstacle.class))){
						return false;
					}
					else if((s1.getClass().equals(Monster.class))&&(((Character)(s2)).getPower() != Power.INVISIBLE)){
						((Character)(s2)).setDead(true);
						return false;
					}
					else if((s1.getClass().equals(Monster.class))&&(((Character)(s2)).getPower() == Power.INVISIBLE)){
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
					else if((s1.getClass().equals(Destructible.class)) && (((Destructible)s1).isDeadly())){
						((Character)s2).setDead(true);
						return false;
					}
					else if((s1.getClass().equals(Destructible.class)) && (((Character)(s2)).getPower()== Power.DESTRUCTOR)){
						s1.shouldBeDestroy=true;
						((Destructible)s1).setDestructiblesDeadly();
						((Character)(s2)).isFalling=false;
						return false;
					}
					else if(s1.getClass().equals(IndicationSprite.class)){
						((IndicationSprite)s1).activateIndication();
						s1.setShouldBeDestroy();
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
					else if(s1.getClass().equals(LevierCombi.class)){
						((LevierCombi)s1).activate();
						return false;
					}
					else if(s1.getClass().equals(Bonus.class)){
						((Bonus)s1).obtained();

						
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

