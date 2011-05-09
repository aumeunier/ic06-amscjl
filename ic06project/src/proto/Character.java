package proto;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

public class Character extends Sprite{
	public boolean isGoingRight;
	public boolean isGoingLeft;
	public boolean isFalling;
	public boolean canGoThroughObstacles;
	public Character(){
		super();
		x=0;
		y=0;
		w=50;
		h=50;
		isGoingRight=false;
		isGoingLeft=false;
		isFalling=false;
		canGoThroughObstacles=false;
	}

	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRoundRect(x, y, w, h, 20, 20);
	}
}
