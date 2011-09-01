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
 * The UIPause is displayed when players click on the Menu button at the bottom of the screen 
 * in the <code>GameplayState</code>. It is displayed on top of the gameplay state (the game is paused).
 * It is possible to go back to the game, to the main menu, to the help screen or to start/stop the music.
 *
 */
public class UIPause implements UIInterface {
	
	// General interface
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
	final static int BUTTON_Y_INIT = 50+BUTTON_SPACE;
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
		this.menuBackgroundImage = Global.getImage("menupause.png");
		
		int i = 0;

		// Back to game button
		Image buttonImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE).getScaledCopy(BUTTON_W, BUTTON_H);
		Label backToGameLabel = new Label(buttonImage,"Retour au jeu");
		backToGameLabel.setBounds(BUTTON_X,BUTTON_Y_INIT+(i++)*(BUTTON_H+BUTTON_SPACE),BUTTON_W,BUTTON_H);
		backToGameLabel.pack();
		this.display.add(backToGameLabel);
		
		// Back to menu button  
		Label helpLabel = new Label(buttonImage,"Aide");
		helpLabel.setBounds(BUTTON_X,BUTTON_Y_INIT+(i++)*(BUTTON_H+BUTTON_SPACE),BUTTON_W,BUTTON_H);
		helpLabel.pack();
		this.display.add(helpLabel);
		
		// Restart level button  
		Label musicLabel = new Label(buttonImage,"Lire/Stop musique");
		musicLabel.setBounds(BUTTON_X,BUTTON_Y_INIT+(i++)*(BUTTON_H+BUTTON_SPACE),BUTTON_W,BUTTON_H);
		musicLabel.pack();
		this.display.add(musicLabel);
		
		// Restart level button  
		Label restartLabel = new Label(buttonImage,"Recommencer le niveau");
		restartLabel.setBounds(BUTTON_X,BUTTON_Y_INIT+(i++)*(BUTTON_H+BUTTON_SPACE),BUTTON_W,BUTTON_H);
		restartLabel.pack();
		this.display.add(restartLabel);
		
		// Back to menu button  
		Label backToMenuLabel = new Label(buttonImage,"Retour au menu principal");
		backToMenuLabel.setBounds(BUTTON_X,BUTTON_Y_INIT+(i++)*(BUTTON_H+BUTTON_SPACE),BUTTON_W,BUTTON_H);
		backToMenuLabel.pack();
		this.display.add(backToMenuLabel);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		// Background is darkened 
		g.setColor(new Color(0.0f,0.0f,0.0f,backgroundTransparency));
		g.fillRect(0,0,Global.WINDOW_WIDTH,Global.WINDOW_HEIGHT);
		
		// Menu
		this.menuBackgroundImage.draw(BACKGROUND_X,BACKGROUND_Y,BACKGROUND_W,BACKGROUND_H);
		g.setColor(new Color(0.0f,0.0f,0.0f,menuBackgroundTransparency));
		g.fillRect(BACKGROUND_X,BACKGROUND_Y,BACKGROUND_W,BACKGROUND_H);
		
		// Buttons
		display.render(gc, g);
	}

	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		int result = -1;
		// Return to the game
		if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT && y <= (BUTTON_Y_INIT + BUTTON_H))){
			state.setPaused(false);				
		}
		
		// Help
		else if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT+BUTTON_H+BUTTON_SPACE  
						&& y <= (BUTTON_Y_INIT+BUTTON_H+BUTTON_SPACE + BUTTON_H))){
			state.setPaused(false);
			result = Game.HELP_STATE;
		}
		
		// Start/Stop music
		else if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT+2*BUTTON_H+2*BUTTON_SPACE  
						&& y <= (BUTTON_Y_INIT+2*BUTTON_H+2*BUTTON_SPACE + BUTTON_H))){
			state.setPaused(false);	
			result = Game.STOP_PLAY_MUSIC;
		}
		
		// Restart 
		else if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT+3*BUTTON_H+3*BUTTON_SPACE  
						&& y <= (BUTTON_Y_INIT+3*BUTTON_H+3*BUTTON_SPACE + BUTTON_H))){
			state.setPaused(false);	
			result = Game.SHOULD_RESTART;
		}
		
		// Return to the main menu
		else if((x >= BUTTON_X && x <= (BUTTON_X + BUTTON_W)) 
				&&	(y >= BUTTON_Y_INIT+4*BUTTON_H+4*BUTTON_SPACE  
						&& y <= (BUTTON_Y_INIT+4*BUTTON_H+4*BUTTON_SPACE + BUTTON_H))){
			state.setPaused(false);	
			result = Game.MAINMENU_STATE;
		}
		
		return result;
	}
}
