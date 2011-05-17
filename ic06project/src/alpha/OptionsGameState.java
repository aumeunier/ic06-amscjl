package alpha;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class OptionsGameState extends BasicGameState implements MouseListener {
	static final int BACK_X = 75;
	static final int BACK_Y = 50;
	private int stateID;
	int selection;
	int selectedSave;
	Image backgroundImage;
	
	public OptionsGameState(int id){
		super();
		stateID = id;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+"7711466_s.jpg");
		selection = -1;		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		backgroundImage.draw(0,0,800,600);
		arg2.drawString("Retour", BACK_X, BACK_Y);
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(selection == Game.MAINMENU_STATE){
			arg1.enterState(Game.MAINMENU_STATE);
		}
		selection = -1;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return stateID;
	}


	public void mouseMoved(int oldx, int oldy, int newX, int newY){
		
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= BACK_X && x <= (BACK_X + 50)) 
				&&	(y >= BACK_Y && y <= (BACK_Y + 25))){
			selection = Game.MAINMENU_STATE;
		}
	}
}
