package alpha;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIPause implements UIInterface {
	
	// Interface generale
	final static int PAUSE_X = 200;
	final static int PAUSE_Y = 100;
	final static int PAUSE_WIDTH = (Global.WINDOW_WIDTH/2-PAUSE_X)*2;
	final static int PAUSE_HEIGHT = (Global.WINDOW_HEIGHT/2-PAUSE_Y)*2;
	final static int BUTTON_SPACE = 20;
	final static int BUTTON_X = (int) (PAUSE_X+PAUSE_WIDTH*0.2);
	final static int BUTTON_Y_INIT = PAUSE_Y+BUTTON_SPACE;
	final static int BUTTON_W = (int) (PAUSE_WIDTH*0.6);
	final static int BUTTON_H = 25;

	protected float backgroundTransparency;
	protected float menuBackgroundTransparency;	
	protected Image menuBackgroundImage;
	
	private Display display;	
	protected Button backButton;
	
	public UIPause(GameContainer gc){
		this.backgroundTransparency = 0.7f;
		this.menuBackgroundTransparency = 0.3f;
		this.display = new Display(gc);
		
		// Background image
		this.menuBackgroundImage = Global.setImage("06_wood_artshare_ru.jpg");
		
		// Back button //TODO: Button
		Image buttonImage = Global.setImage("blur11.jpg").getScaledCopy(BUTTON_W, BUTTON_H);
		Label backLabel = new Label(buttonImage,"Retour au jeu");
		backLabel.setBounds(BUTTON_X,BUTTON_Y_INIT,BUTTON_W,BUTTON_H);
		backLabel.pack();
		this.display.add(backLabel);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// Fond d'ecran plus sombre
		g.setColor(new Color(0.0f,0.0f,0.0f,backgroundTransparency));
		g.fillRect(0,0,Global.WINDOW_WIDTH,Global.WINDOW_HEIGHT);
		// Menu
		this.menuBackgroundImage.draw(PAUSE_X,PAUSE_Y,PAUSE_WIDTH,PAUSE_HEIGHT);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(PAUSE_X,PAUSE_Y,PAUSE_WIDTH,PAUSE_HEIGHT);
		// Boutons
		display.render(gc, g);
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT && y <= (BUTTON_Y_INIT + BUTTON_H))){
			state.setPaused(false);				
		}
		
		return -1;
	}
}
