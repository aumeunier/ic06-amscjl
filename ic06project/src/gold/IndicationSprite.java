package gold;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

public class IndicationSprite extends Sprite {

	public IndicationSprite(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
	}
	public IndicationSprite(Sprite s){
		this.x = s.x;
		this.y = s.y;
		this.w = s.w;
		this.h = s.h;
	}
	
	@Override
	public void draw(GameContainer container, StateBasedGame game, Graphics g){
		if(indication!=null){
			indication.render(container, game, g);
		}
	}
}
