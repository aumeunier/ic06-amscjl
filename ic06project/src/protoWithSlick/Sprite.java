package protoWithSlick;

import java.awt.Color;
import java.awt.Graphics;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite {
	protected int x;
	protected int y;
	protected int w;
	protected int h;
	protected Image image = null;
	
	public Sprite(){
		this.x=0;
		this.y=0;
		this.w=40;
		this.h=40;
		setImage("plane.png");
	}	
	public Sprite(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
		setImage("plane.png");
	}
	
	public void setCoordinatesFromBody(Body b){
		this.x = (int)b.getPosition().x-this.w/2;
		this.y = Global.GAMEHEIGHT-(int)b.getPosition().y-this.h/2;
	}
	
	protected void setImage(String filename){
		try {
			this.image = new Image("./ressources/"+filename);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void draw(){
		image.draw(x, y, w, h);
	}
}
