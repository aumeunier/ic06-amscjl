package proto;


import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class Wall extends Sprite{
	public Wall(){
		super();
		x=0;
		y=0;
		w=400;
		h=10;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, w, h);
	}
}
