package beta;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIPause implements UIInterface {
	
	// Interface generale
	final static int BACKGROUND_X = 0;
	final static int BACKGROUND_Y = 0;
	final static int BACKGROUND_W = 800;
	final static int BACKGROUND_H = 600;
	final static int PAUSE_X = 200;
	final static int PAUSE_Y = 100;
	final static int PAUSE_W = (Global.WINDOW_WIDTH/2-PAUSE_X)*2;
	final static int PAUSE_H = (Global.WINDOW_HEIGHT/2-PAUSE_Y)*2;
	final static int BUTTON_SPACE = 20;
	final static int BUTTON_X = (int) (PAUSE_X+PAUSE_W*0.2);
	final static int BUTTON_Y_INIT = PAUSE_Y+BUTTON_SPACE;
	final static int BUTTON_W = (int) (PAUSE_W*0.6);
	final static int BUTTON_H = 25;

	protected float backgroundTransparency;
	protected float menuBackgroundTransparency;	
	protected Image menuBackgroundImage;
	
	private Display display;	
		
	public UIPause(GameContainer gc){
		this.backgroundTransparency = 0.7f;
		this.menuBackgroundTransparency = 0.3f;
		this.display = new Display(gc);
		
		// Background image
		this.menuBackgroundImage = Global.setImage("menupause.png");

		// Back to game button //TODO: Button
		Image buttonImage = Global.setImage("blur11.jpg").getScaledCopy(BUTTON_W, BUTTON_H);
		Label backToGameLabel = new Label(buttonImage,"Retour au jeu");
		backToGameLabel.setBounds(BUTTON_X,BUTTON_Y_INIT,BUTTON_W,BUTTON_H);
		backToGameLabel.pack();
		this.display.add(backToGameLabel);
		
		// Back to menu button //TODO: Button
		Label backToMenuLabel = new Label(buttonImage,"Retour au menu principal");
		backToMenuLabel.setBounds(BUTTON_X,BUTTON_Y_INIT+BUTTON_H+BUTTON_SPACE,BUTTON_W,BUTTON_H);
		backToMenuLabel.pack();
		this.display.add(backToMenuLabel);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// Fond d'ecran plus sombre
		g.setColor(new Color(0.0f,0.0f,0.0f,backgroundTransparency));
		g.fillRect(0,0,Global.WINDOW_WIDTH,Global.WINDOW_HEIGHT);
		// Menu
		this.menuBackgroundImage.draw(BACKGROUND_X,BACKGROUND_Y,BACKGROUND_W,BACKGROUND_H);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(BACKGROUND_X,BACKGROUND_Y,BACKGROUND_W,BACKGROUND_H);
		// Boutons
		display.render(gc, g);
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		int result = -1;
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT && y <= (BUTTON_Y_INIT + BUTTON_H))){
			state.setPaused(false);				
		}
		else if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT+BUTTON_H+BUTTON_SPACE  
						&& y <= (BUTTON_Y_INIT+BUTTON_H+BUTTON_SPACE + BUTTON_H))){
			state.setPaused(false);	
			result = Game.MAINMENU_STATE;
		}
		
		return result;
	}
}
