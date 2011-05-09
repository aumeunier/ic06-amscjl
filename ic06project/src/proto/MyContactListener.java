package proto;


import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactListener;
import org.jbox2d.dynamics.contacts.ContactPoint;
import org.jbox2d.dynamics.contacts.ContactResult;

public class MyContactListener implements ContactListener {

	@Override
	public void add(ContactPoint point) {
		// TODO Auto-generated method stub
		Body b1 = point.shape1.getBody();
		Body b2 = point.shape2.getBody();
		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			if(s1.getClass().equals(Character.class)){
				if (((Character)s1).isFalling)
					((Character)s1).isFalling=false ;
				if(((Character)s1).canGoThroughObstacles
						&& (s2.getClass().equals(Obstacle.class))){
					System.out.println("Add");
					this.remove(point);
				}
			}
			if(s2.getClass().equals(Character.class)){
				if(((Character)s2).canGoThroughObstacles
						&& (s1.getClass().equals(Obstacle.class))){
					System.out.println("Add");
					this.remove(point);
				}
			}
		}
	}

	@Override
	public void persist(ContactPoint point) {
		// TODO Auto-generated method stub
		Body b1 = point.shape1.getBody();
		Body b2 = point.shape2.getBody();

		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			if(s1.getClass().equals(Character.class)){
				if(((Character)s1).canGoThroughObstacles
						&& (s2.getClass().equals(Obstacle.class))){
					System.out.println("Persist");
					this.remove(point);
				}
			}
			if(s2.getClass().equals(Character.class)){
				if(((Character)s2).canGoThroughObstacles
						&& (s1.getClass().equals(Obstacle.class))){
					System.out.println("Persist");
					this.remove(point);
				}
			}
		}
	}

	@Override
	public void remove(ContactPoint point) {
		// TODO Auto-generated method stub-
		Body b1 = point.shape1.getBody();
		Body b2 = point.shape2.getBody();

		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			if(s1.getClass().equals(Character.class)){
				if(((Character)s1).canGoThroughObstacles
						&& (s2.getClass().equals(Obstacle.class))){
					System.out.println("Remove");
				}
			}
			if(s2.getClass().equals(Character.class)){
				if(((Character)s2).canGoThroughObstacles
						&& (s1.getClass().equals(Obstacle.class))){
					System.out.println("Remove");
				}
			}
		}
	}

	@Override
	public void result(ContactResult point) {
		// TODO Auto-generated method stub
		Body b1 = point.shape1.getBody();
		Body b2 = point.shape2.getBody();

		if(b1.getUserData()!=null && b2.getUserData()!=null){
			Sprite s1 = (Sprite)b1.getUserData();
			Sprite s2 = (Sprite)b2.getUserData();
			if(s1.getClass().equals(Character.class)){
				if(((Character)s1).canGoThroughObstacles
						&& (s2.getClass().equals(Obstacle.class))){
					System.out.println("Result");
				}
			}
			if(s2.getClass().equals(Character.class)){
				if(((Character)s2).canGoThroughObstacles
						&& (s1.getClass().equals(Obstacle.class))){
					System.out.println("Result");
				}
			}
		}
	}

}
