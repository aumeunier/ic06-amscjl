package beta;

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
	protected boolean shouldBeDestroy=false;
	protected boolean shouldChangeSize=false;
	protected Color colorFilter = Color.white;
	protected boolean isHidden=false;
	
	public Sprite(){
		this.x=0;
		this.y=0;
		this.w=40;
		this.h=40;
	}	
	public Sprite(int _x, int _y){
		this.x = _x;
		this.y = _y;
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
			SpriteSheet ss = new SpriteSheet(Global.PATH_SPRITES_RESSOURCES+filename,tw,th);
			this.animation = new Animation(ss,100);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setTexture(String filename){
		// TODO:
		//this.image.setTexture(new Texture());
	}
	protected void setFilter(Color _colorFilter){
		this.colorFilter = _colorFilter;
	}
	public void addPointToShape(int x, int y){
		if(shape==null){
			shape = new Polygon();
		}
		shape.addPoint(x, y);
	}
	
	public void draw(Graphics g){
		if(animation!=null){
			animation.draw(x, y, w, h, colorFilter);
		}
		else if(shape!=null){
			g.texture(shape, image, true);
		}
		else if(image!=null){
			image.draw(x, y, w, h);
		}
	}
	
	public boolean getShouldBeDestroy(){
		return shouldBeDestroy;
	}
	
	public void setShouldBeDestroy(){
		 shouldBeDestroy=true;
	}
	
	public boolean isHidden(){
		 return isHidden;
	}
	
	public void Hidden(boolean hideOrNot){
		 isHidden=hideOrNot;
	}
	
	public boolean rectCollideWithOther(Sprite other){
		if((((this.x >= other.x) && (this.x <= (other.x+other.w))) 
				|| (((this.x + this.w) >= other.x) && ((this.x + this.w) <= (other.x+other.w))))
			&& (((this.y >= other.y) && (this.y <= (other.y+other.h))) 
					|| (((this.y + this.h) >= other.y) && ((this.y + this.h) <= (other.y+other.h))))){
			return true;
		}
		else{
			return false;
		}
	}

}
