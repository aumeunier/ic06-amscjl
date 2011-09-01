package version1.ui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import version1.states.GameplayState;

/**
 * An UI must have two methods which are called by the states when they need to render the screen or 
 * react to mouse clicks
 *
 */
public interface UIInterface {
	abstract void render(GameContainer gc, Graphics g);
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state);
}
