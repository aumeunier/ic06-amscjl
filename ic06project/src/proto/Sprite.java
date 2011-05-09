package proto;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import org.jbox2d.dynamics.Body;

public class Sprite extends Component{
	public int x;
	public int y;
	public int w;
	public int h;
	
	public Sprite(){
		
	}
	
	public void setCoordinatesFromBody(Body b){
		this.x = (int)b.getPosition().x-this.w/2;
		this.y = Global.GAMEHEIGHT-(int)b.getPosition().y-this.h/2;
		
	}
	
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRoundRect(x, y, w, h, 20, 20);
	}
}
