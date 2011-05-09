package proto;


import org.jbox2d.collision.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactFilter;

public class MyContactFilter implements ContactFilter {

	@Override
	public boolean shouldCollide(Shape shape1, Shape shape2) {
		// TODO Auto-generated method stub
		Body b1 = shape1.getBody();
		Body b2 = shape2.getBody();
		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			if(s1.getClass().equals(Character.class)){
				if(((Character)s1).canGoThroughObstacles
						&& (s2.getClass().equals(Obstacle.class))){
					System.out.println("NO COLLISION MWAHAHAH");
					return false;
				}
			}
			if(s2.getClass().equals(Character.class)){
				if(((Character)s2).canGoThroughObstacles
						&& (s1.getClass().equals(Obstacle.class))){
					System.out.println("NO COLLISION MWAHAHAH");
					return false;
				}
			}
		}
		return true;
	}

}
