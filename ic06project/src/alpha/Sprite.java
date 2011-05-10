package alpha;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

public class Sprite {
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Image image = null;
	protected Animation animation = null;
	protected Polygon shape = null;
	
	public Sprite(){
		this.x=0;
		this.y=0;
		this.w=40;
		this.h=40;
	}	
	public Sprite(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
	}
	
	public void setCoordinatesFromBody(Body b){
		this.x = (int)b.getPosition().x-this.w/2;
		this.y = Global.GAMEPLAYHEIGHT-(int)b.getPosition().y-this.h/2;
	}
	
	protected void setImage(String filename){
		try {
			this.image = new Image(Global.PATH_IMAGES_RESSOURCES+filename);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	protected void setAnimation(String filename, int tw, int th){
		try {
			SpriteSheet ss = new SpriteSheet(Global.PATH_IMAGES_RESSOURCES+filename,tw,th);
			this.animation = new Animation(ss,1000);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setTexture(String filename){
		//this.image.setTexture(new Texture());
	}
	public void addPointToShape(int x, int y){
		if(shape==null){
			shape = new Polygon();
		}
		shape.addPoint(x, y);
	}
	
	public void draw(Graphics g){
		if(animation!=null){
			animation.draw(x, y, w, h);
		}
		else if(shape!=null){
			g.setColor(Color.red);
			g.fill(shape);
		}
		else if(image!=null){
			image.draw(x, y, w, h);
		}
	}
}
