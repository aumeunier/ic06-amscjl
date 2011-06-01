package gold;

import java.util.ArrayList;

public class Destructible extends Wall {
	private ArrayList<Destructible> destructibles;
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
