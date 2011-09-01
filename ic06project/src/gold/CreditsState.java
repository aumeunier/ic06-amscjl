package gold;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class CreditsState extends BasicGameState implements MouseListener {
	static final int BACK_X = 25;
	static final int BACK_Y = 20;
	static final int SAVE_OFFSET_X = 100;
	static final int SAVE_OFFSET_Y = 50;
	static final int SAVE_SPACE_Y = 10;
	static final int SAVE_TEXT_Y = 25;
	private int stateID;
	int selection;
	Image backgroundImage;

	@Override
	public int getID() {
		return stateID;
	}
	
	public CreditsState(int id){
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
		arg2.setColor(Color.white);
		//backgroundImage.draw(0,0,800,600);
		arg2.drawString("Retour", BACK_X, BACK_Y);
		int startX = SAVE_OFFSET_X, startY = SAVE_OFFSET_Y;
		arg2.setColor(Color.yellow);
		arg2.drawString("Conception et réalisation:", startX, startY);
		arg2.setColor(Color.white);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Stéphane Chebli", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Juliette Lemaître", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Aurélien Meunier", startX, startY);
		startY+=2*SAVE_SPACE_Y+SAVE_TEXT_Y;

		arg2.setColor(Color.yellow);
		arg2.drawString("Graphismes:", startX, startY);
		arg2.setColor(Color.white);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Clément Gougeon", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("wpclipart.com", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("123rf.com", startX, startY);
		startY+=2*SAVE_SPACE_Y+SAVE_TEXT_Y;

		arg2.setColor(Color.yellow);
		arg2.drawString("Musiques:", startX, startY);
		arg2.setColor(Color.white);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Dance of light pixies by enameth (dig.ccmixter.org)", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Pequennas allas by duckett (dig.ccmixter.org)", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Between me and myself by gmz (dig.ccmixter.org)", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Never heard a rhyme like that before by scottaltham (dig.ccmixter.org)", startX, startY);
		startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
		arg2.drawString("Feeling dark (behind the mask) by 7OOP3D (dig.ccmixter.org)", startX, startY);
		startY+=2*SAVE_SPACE_Y+SAVE_TEXT_Y;
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(selection == Game.MAINMENU_STATE){
			arg1.enterState(Game.MAINMENU_STATE);
		}
		selection = -1;
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
