package alpha;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UILevelCompleted implements UIInterface {
	
	// Interface generale
	final static int WIN_X = 200;
	final static int WIN_Y = 100;
	final static int WIN_W = (Global.WINDOW_WIDTH/2-WIN_X)*2;
	final static int WIN_H = (Global.WINDOW_HEIGHT/3-WIN_Y/2)*2;
	final static int BUTTON_SPACE = 20;
	final static int BUTTON_W = (int) ((WIN_W-3*BUTTON_SPACE)/2);
	final static int BUTTON_H = 25;
	final static int BUTTON_X = WIN_X+BUTTON_SPACE;
	final static int BUTTON_Y = WIN_Y+WIN_H-BUTTON_SPACE-BUTTON_H;

	protected float backgroundTransparency;
	protected float menuBackgroundTransparency;	
	protected Image menuBackgroundImage;
	
	private Display display;	
	
	public UILevelCompleted(GameContainer gc){

		this.backgroundTransparency = 0.7f;
		this.menuBackgroundTransparency = 0.15f;
		this.display = new Display(gc);
		
		// Background image
		this.menuBackgroundImage = Global.setImage("9191582_s.jpg");	
		
		// Texte
		Image textImage = Global.setImage("blur11.jpg").getScaledCopy(2*BUTTON_W+BUTTON_SPACE, BUTTON_H);
		Label winLabel = new Label(textImage,"Niveau termine! Felicitations!");
		winLabel.setBounds(BUTTON_X,WIN_Y+WIN_H/3,2*BUTTON_W+BUTTON_SPACE,BUTTON_H);
		winLabel.pack();
		this.display.add(winLabel);		
		winLabel.setImage(null);

		// Restart button //TODO: Button
		Image buttonImage = Global.setImage("blur11.jpg").getScaledCopy(BUTTON_W, BUTTON_H);
		Label nextLabel = new Label(buttonImage,"Niveau suivant");
		nextLabel.setBounds(BUTTON_X,BUTTON_Y,BUTTON_W,BUTTON_H);
		nextLabel.pack();
		this.display.add(nextLabel);
		
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
		this.menuBackgroundImage.draw(WIN_X,WIN_Y,WIN_W,WIN_H);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(WIN_X,WIN_Y,WIN_W,WIN_H);
		// Boutons
		display.render(gc, g);
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount,
			GameplayState state) {
		int result = -1;
		// Click on the next button
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);
			result = Game.GO_TO_NEXT_LEVEL;
		}
		// Click on the main menu button
		else if((x >= BUTTON_X+BUTTON_W+BUTTON_SPACE  && x <= (BUTTON_X+BUTTON_W+BUTTON_SPACE + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);	
			result = Game.MAINMENU_STATE;
		}		
		return result;
	}

}
