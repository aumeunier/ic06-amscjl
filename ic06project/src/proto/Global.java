package proto;


import org.jbox2d.common.Vec2;

public class Global {
	public final static int GAMEWIDTH = 800;
	public final static int GAMEHEIGHT = 575;
	
	public static Vec2 getWorldCoordinates(float box2dX, float box2dY){
		return new Vec2(box2dX,box2dY);
	}
	public static Vec2 getBox2DCoordinates(int worldX, int worldY){
		return new Vec2(worldX,Global.GAMEHEIGHT-worldY);
	}
}
