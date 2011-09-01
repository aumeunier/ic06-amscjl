package version1.sprites.interaction;

import org.jbox2d.common.Vec2;

import version1.sprites.Sprite;

/**
 * A transporter is used to teleport a player from a point to another.
 * The transporter is located at a point which is the origin, when the player collides with it, he is teleported
 * to the destination. A transporter is one way.
 *
 */
public class Transporter extends Sprite {
	private Vec2 destination;
	public Transporter(int x,int y){
		super();
		destination = new Vec2(x,y);
		setImage("portail.png");
	}
	public Transporter(int _x, int _y, int _w, int _h, int nx, int ny){
		super(_x,_y,_w,_h);
		destination = new Vec2(nx,ny);
		setImage("portail.png");
	}
	public Vec2 getTeleportationPosition(){
		return destination;
	}
}
