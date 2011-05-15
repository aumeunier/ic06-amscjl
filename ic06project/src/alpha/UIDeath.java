package alpha;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIDeath implements UIInterface {
	public final static int SHOULD_RESTART = -100;
	
	// Interface generale
	final static int DEATH_X = 200;
	final static int DEATH_Y = 100;
	final static int DEATH_W = (Global.WINDOW_WIDTH/2-DEATH_X)*2;
	final static int DEATH_H = (Global.WINDOW_HEIGHT/2-DEATH_Y)*2;
	final static int BUTTON_SPACE = 20;
	final static int BUTTON_W = (int) ((DEATH_W-3*BUTTON_SPACE)/2);
	final static int BUTTON_H = 25;
	final static int BUTTON_X = DEATH_X+BUTTON_SPACE;
	final static int BUTTON_Y = DEATH_Y+DEATH_H-BUTTON_SPACE-BUTTON_H;

	protected float backgroundTransparency;
	protected float menuBackgroundTransparency;	
	protected Image menuBackgroundImage;
	
	private Display display;	

	public UIDeath(GameContainer gc){
		this.backgroundTransparency = 0.7f;
		this.menuBackgroundTransparency = 0.3f;
		this.display = new Display(gc);
		
		// Background image
		this.menuBackgroundImage = Global.setImage("blur20.jpg");	

		// Restart button //TODO: Button
		Image buttonImage = Global.setImage("blur11.jpg").getScaledCopy(BUTTON_W, BUTTON_H);
		Label restartLabel = new Label(buttonImage,"Recommencer");
		restartLabel.setBounds(BUTTON_X,BUTTON_Y,BUTTON_W,BUTTON_H);
		restartLabel.pack();
		this.display.add(restartLabel);
		
		// Back button //TODO: Button
		Label backToMenuLabel = new Label(buttonImage,"Menu principal");
		backToMenuLabel.setBounds(BUTTON_X+BUTTON_W+BUTTON_SPACE,BUTTON_Y,BUTTON_W,BUTTON_H);
		backToMenuLabel.pack();
		this.display.add(backToMenuLabel);	
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// Fond d'ecran plus sombre
		g.setColor(new Color(0.0f,0.0f,0.0f,backgroundTransparency));
		g.fillRect(0,0,Global.WINDOW_WIDTH,Global.WINDOW_HEIGHT);
		// Menu
		this.menuBackgroundImage.draw(DEATH_X,DEATH_Y,DEATH_W,DEATH_H);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(DEATH_X,DEATH_Y,DEATH_W,DEATH_H);
		// Boutons
		display.render(gc, g);		
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		int result = -1;
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);
			result = SHOULD_RESTART;
		}
		else if((x >= BUTTON_X+BUTTON_W+BUTTON_SPACE  && x <= (BUTTON_X+BUTTON_W+BUTTON_SPACE + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);	
			result = Game.MAINMENU_STATE;
		}		
		return result;
	}
}
