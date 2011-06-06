package gold;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class HelpState extends BasicGameState implements MouseListener {
	static final int BACK_X = 25;
	static final int BACK_Y = 50;
	private int stateID;
	private int previousID = -1;
	int selection;
	Image backgroundImage;

	@Override
	public int getID() {
		return stateID;
	}

	public HelpState(int id){
		super();
		stateID = id;	
	}
	
	public void setPreviousState(int id){
		previousID = id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+"help.png").getScaledCopy(800, 600);
		selection = -1;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setColor(Color.black);
		backgroundImage.draw(0,0,800,600);
		arg2.drawString("Retour", BACK_X, BACK_Y);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(selection != -1){
			arg1.enterState(selection);
		}
		selection = -1;
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= BACK_X && x <= (BACK_X + 50)) 
				&&	(y >= BACK_Y && y <= (BACK_Y + 25))){
			selection = previousID;
		}
	}
}
