package version1.ui;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import version1.Game;
import version1.Global;
import version1.states.GameplayState;

/**
 * The UILevelCompleted is displayed when the players finish the level (arrive at the exit).
 * The background is darkened and a small box with a few congratulation words and three possibilities:
 * 		Restart, Next level, Main menu.
 *
 */
public class UILevelCompleted implements UIInterface {
	
	// General Interface
	final static int WIN_X = 200;
	final static int WIN_Y = 100;
	final static int WIN_W = (Global.WINDOW_WIDTH/2-WIN_X)*2;
	final static int WIN_H = (int) ((Global.WINDOW_HEIGHT/2.5-WIN_Y/2)*2);
	final static int BUTTON_SPACE = 20;
	final static int BUTTON_W = (int) ((WIN_W-3*BUTTON_SPACE)/2);
	final static int BUTTON_H = 25;
	final static int BUTTON_X = WIN_X+BUTTON_SPACE;
	final static int BUTTON_Y = WIN_Y+WIN_H-2*BUTTON_SPACE-2*BUTTON_H;

	protected float backgroundTransparency;
	protected float menuBackgroundTransparency;	
	protected Image menuBackgroundImage;
	
	private Display display;	
	
	public UILevelCompleted(GameContainer gc){

		this.backgroundTransparency = 0.7f;
		this.menuBackgroundTransparency = 0.15f;
		this.display = new Display(gc);
		
		// Background image
		this.menuBackgroundImage = Global.getImage("9191582_s.jpg");	
		
		// Text
		Image textImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE).getScaledCopy(2*BUTTON_W+BUTTON_SPACE, BUTTON_H);
		Label winLabel = new Label(textImage,"Niveau termine! Felicitations!");
		winLabel.setBounds(BUTTON_X,WIN_Y+WIN_H/3,2*BUTTON_W+BUTTON_SPACE,BUTTON_H);
		winLabel.pack();
		this.display.add(winLabel);		
		winLabel.setImage(null);
		
		// Restart button 
		Image buttonImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE).getScaledCopy(BUTTON_W, BUTTON_H);
		Label restartLabel = new Label(buttonImage,"Rejouer");
		restartLabel.setBounds(BUTTON_X,BUTTON_Y,BUTTON_W,BUTTON_H);
		restartLabel.pack();
		this.display.add(restartLabel);	

		// Next button
		Label nextLabel = new Label(buttonImage,"Niveau suivant");
		nextLabel.setBounds(BUTTON_X+BUTTON_W+BUTTON_SPACE,BUTTON_Y,BUTTON_W,BUTTON_H);
		nextLabel.pack();
		this.display.add(nextLabel);

		// Main menu button 
		buttonImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE).getScaledCopy(BUTTON_W*2+BUTTON_SPACE, BUTTON_H);
		Label backToMenuLabel = new Label(buttonImage,"Menu principal");
		backToMenuLabel.setBounds(BUTTON_X,BUTTON_Y+BUTTON_H+BUTTON_SPACE,2*BUTTON_W+BUTTON_SPACE,BUTTON_H);
		backToMenuLabel.pack();
		this.display.add(backToMenuLabel);
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// Background screen is darkened
		g.setColor(new Color(0.0f,0.0f,0.0f,backgroundTransparency));
		g.fillRect(0,0,Global.WINDOW_WIDTH,Global.WINDOW_HEIGHT);
		
		// Menu
		this.menuBackgroundImage.draw(WIN_X,WIN_Y,WIN_W,WIN_H);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(WIN_X,WIN_Y,WIN_W,WIN_H);
		
		// Buttons
		display.render(gc, g);
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount,
			GameplayState state) {
		int result = -1;
		// Click on the restart button
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);
			result = Game.SHOULD_RESTART;
		}
		
		// Click on the next button
		else if((x >= BUTTON_X+BUTTON_W+BUTTON_SPACE  && x <= (BUTTON_X+BUTTON_W+BUTTON_SPACE + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);	
			result = Game.GO_TO_NEXT_LEVEL;
		}		
		
		// Click on the main menu button
		if((x >= BUTTON_X && x <= (BUTTON_X+BUTTON_W+BUTTON_SPACE + BUTTON_W)) 
				&&	(y >= (BUTTON_Y+BUTTON_H+BUTTON_SPACE) && y <= (BUTTON_Y+BUTTON_H+BUTTON_SPACE+ BUTTON_H))){
			state.setPaused(false);
			result = Game.MAINMENU_STATE;
		}
		return result;
	}

}
