package proto;


import java.awt.Color;
import java.awt.Graphics;

public class Obstacle extends Sprite{

	public void draw(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(x, y, w, h);
	}
}
