package protoWithSlick;

import org.jbox2d.collision.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactFilter;

public class MyContactFilter implements ContactFilter {

	@Override
	public boolean shouldCollide(Shape shape1, Shape shape2) {
		Body b1 = shape1.getBody();
		Body b2 = shape2.getBody();
		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			if(s1.getClass().equals(Character.class)){
				if(((Character)s1).isIntangible()
						&& (s2.getClass().equals(Obstacle.class))){
					return false;
				}
				else if(s2.getClass().equals(Source.class)){
					Source source = (Source)s2;
					Character character = (Character)s1;
					if(source.power == 1){
						if(character.test){
							character.setIntangible(!character.isIntangible());
							character.changeImage();
							character.test = false;
						}
						else {
							character.test = true;
						}
					}
					else if(source.power == 2){
						character.setFlying(true);
						character.changeImage();
					}
					return false;
				}
			}
			if(s2.getClass().equals(Character.class)){
				if(((Character)s2).isIntangible()
						&& (s1.getClass().equals(Obstacle.class))){
					return false;
				}
				else if(s1.getClass().equals(Source.class)){
					Source source = (Source)s1;
					Character character = (Character)s2;
					if(source.power == 1){
						if(character.test){
							character.setIntangible(!character.isIntangible());
							character.changeImage();
							character.test = false;
						}
						else{
							character.test = true;
						}
					}
					else if(source.power == 2){
						character.setFlying(true);
						character.changeImage();
					}
					return false;
				}
			}
		}
		return true;
	}

}
