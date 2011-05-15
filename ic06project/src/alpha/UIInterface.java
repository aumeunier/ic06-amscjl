package alpha;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface UIInterface {
	abstract void render(GameContainer gc, Graphics g);
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state);
}
