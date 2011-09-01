package version1.sprites.interaction;

import java.util.ArrayList;

import version1.sprites.decor.Wall;

/**
 * A Destructible is a Wall which can be destructed by a player with a Destructor power.
 * A Destructible can fall and kill a creature.
 *
 */
public class Destructible extends Wall {
	/** Destructibles can be placed over other destructibles. These will fall if the this object is destroyed  */
	private ArrayList<Destructible> destructibles;
	/** A Destructible is deadly when it is falling */
	private boolean isDeadly = false;
	public Destructible(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("wall2.jpg");
		destructibles = new ArrayList<Destructible>();
	}
	public void addDestructible(Destructible d){
		destructibles.add(d);
	}
	public void setDestructiblesDeadly(){
		for(Destructible d:destructibles){
			if(d!=null){
				d.setDeadly(true);
			}
		}
	}
	public void setDeadly(boolean _deadly){
		isDeadly=_deadly;
	}
	public boolean isDeadly(){
		return isDeadly;
	}
}
