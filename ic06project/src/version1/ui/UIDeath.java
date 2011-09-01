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
 * The UIDeath is displayed when a player dies (from a monster, from a dive in the water)
 *
 */
public class UIDeath implements UIInterface {
	
	// General interface
	final static int DEATH_W = (int) (401*1.5);//(Global.WINDOW_WIDTH/2-DEATH_X)*2;
	final static int DEATH_H = (int) (283*1.5);//(Global.WINDOW_HEIGHT/2-DEATH_Y)*2;
	final static int DEATH_X = (800-DEATH_W)/2;
	final static int DEATH_Y = (600-DEATH_H)/3;
	final static int BUTTON_SPACE = 60;
	final static int BUTTON_W = (int) ((DEATH_W-3*BUTTON_SPACE)/2);
	final static int BUTTON_H = 25;
	final static int BUTTON_X = DEATH_X+BUTTON_SPACE;
	final static int BUTTON_Y = (int) (DEATH_Y+DEATH_H*0.7);

	protected float backgroundTransparency;
	protected float menuBackgroundTransparency;	
	protected Image menuBackgroundImage;
	
	private Display display;	

	public UIDeath(GameContainer gc){
		this.backgroundTransparency = 0.7f;
		this.menuBackgroundTransparency = 0.3f;
		this.display = new Display(gc);
		
		// Background image
		this.menuBackgroundImage = Global.getImage("1770925_s.jpg");		
		
		// 'You lost' label
		Image gameOverImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE).getScaledCopy(DEATH_W-2*BUTTON_SPACE, BUTTON_H);
		Label gameOver = new Label(gameOverImage,"Vous etes morts!\n");
		gameOver.setBounds(BUTTON_X,DEATH_Y+DEATH_H/3-BUTTON_H,DEATH_W-2*BUTTON_SPACE,BUTTON_H);
		gameOver.setForeground(Color.black);
		gameOver.pack();
		this.display.add(gameOver);
		gameOver.setImage(null);

		// Restart button
		Image buttonImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE).getScaledCopy(BUTTON_W, BUTTON_H);
		Label restartLabel = new Label(buttonImage,"Recommencer");
		restartLabel.setBounds(BUTTON_X,BUTTON_Y,BUTTON_W,BUTTON_H);
		restartLabel.pack();
		this.display.add(restartLabel);
		
		// Back button
		Label backToMenuLabel = new Label(buttonImage,"Menu principal");
		backToMenuLabel.setBounds(BUTTON_X+BUTTON_W+BUTTON_SPACE,BUTTON_Y,BUTTON_W,BUTTON_H);
		backToMenuLabel.pack();
		this.display.add(backToMenuLabel);	
	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		// The background is darkened 
		g.setColor(new Color(0.0f,0.0f,0.0f,backgroundTransparency));
		g.fillRect(0,0,Global.WINDOW_WIDTH,Global.WINDOW_HEIGHT);
		
		// Menu
		this.menuBackgroundImage.draw(DEATH_X,DEATH_Y,DEATH_W,DEATH_H);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(DEATH_X,DEATH_Y,DEATH_W,DEATH_H);
		
		// Buttons
		display.render(gc, g);		
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		int result = -1;
		// Click on the restart button
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y && y <= (BUTTON_Y + BUTTON_H))){
			state.setPaused(false);
			result = Game.SHOULD_RESTART;
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
