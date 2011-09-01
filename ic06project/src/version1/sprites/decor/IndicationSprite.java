package version1.sprites.decor;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import version1.sprites.Sprite;

/**
 * An indication sprite is a sprite whose sole purpose is to have a position and an <code>InGameIndication</code>.
 * Players who will go at that position will pop the indication up.
 *
 */
public class IndicationSprite extends Sprite {
	/**
	 * Create an indication sprite from a sprite. The position will be the same
	 */
	public IndicationSprite(Sprite s){
		this.x = s.X();
		this.y = s.Y();
		this.w = s.W();
		this.h = s.H();
	}
	public IndicationSprite(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
	}
	
	@Override
	public void draw(GameContainer container, StateBasedGame game, Graphics g){
		if(indication!=null){
			indication.render(container, game, g);
		}
	}
}
