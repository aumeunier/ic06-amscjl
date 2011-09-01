package version1;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import version1.data.LevelSave;
import version1.data.Save;
import version1.states.CreditsState;
import version1.states.GameplayState;
import version1.states.HelpState;
import version1.states.LoadGameState;
import version1.states.MainMenuGameStateNew;
import version1.states.NarrativeState;
import version1.states.NewGameState;

/**
 * This is the starting class of the game. It inherits from the StateBasedGame of Slick.
 * Basically, it's a container of states for the game. A state can be a menu, a game part, a credits page, etc.
 * It also implements the MusicListener from Slick to have a centralized gestion of the musics in the game. 
 * Only one music can be played at once.
 * 
 * 	Current @version 1.0.0 
 *
 *	Libraries used in the last version:
 *	- ibxm
 *	- jackson-all-1.8.0
 *	- jbox2D-2.0.1-full
 *	- jogg-0.0.7
 *	- jorbis-0.0.15
 *	- lwjgl
 *	- slick
 *	- sui
 */
public class Game extends StateBasedGame implements MusicListener {
	// Unique identifiers for the states
	/** The main menu */
	public static final int MAINMENU_STATE = 0; 
	/** The game part itself */
	public static final int GAMEPLAY_STATE = 1; 
	/** The menu to start a new game */
	public static final int NEWGAME_STATE = 2; 
	/** The menu to load a saved game  */
	public static final int LOADGAME_STATE = 3; 
	/** Options menu - Not Implemented */
	public static final int OPTIONSGAME_STATE = 4; 
	/** A screen to display narrative information */
	public static final int NARRATIVE_STATE = 5; 
	/** An help screen with information about keys and gameplay elements */
	public static final int HELP_STATE = 6;
	/** The credits scren */
	public static final int CREDITS_STATE = 7; 
	/** Indicate a change of level */
	public static final int GO_TO_NEXT_LEVEL = -100; 
	/** Indicate the need to restart the current level */
	public static final int SHOULD_RESTART = -200; 
	/** Indicate that we should stop the music */
	public static final int STOP_PLAY_MUSIC = -300;

	// Various global settings
	private static boolean displayFullScreen = false;
	private static final boolean displayFPS = false;
	private final static String MENUS_MUSIC = "duckett_-_Pequennas_Alas_(PC_TA_mix).ogg";
	
	/** The music instance to use for any music played in the game  */
	public Music music; 
	
	/**
	 * Default constructor.
	 * @param name The name to put in the top bar
	 */
	public Game(String name) {
		super(name);
	}

	@Override
	/**
	 * Called at the launching. Create the states.
	 * @param gc The GameContainer used (internal)
	 * @throws SlickException 
	 */
	public void initStatesList(GameContainer gc) throws SlickException {		
		// Initialize the states
		//addState(new MainMenuGameState(MAINMENU_STATE)); // old menu
		addState(new MainMenuGameStateNew(MAINMENU_STATE));
		addState(new GameplayState(GAMEPLAY_STATE));
		addState(new NewGameState(NEWGAME_STATE));
		addState(new LoadGameState(LOADGAME_STATE));
		//addState(new OptionsGameState(OPTIONSGAME_STATE));
		addState(new HelpState(HELP_STATE));
		addState(new CreditsState(CREDITS_STATE));
		addState(NarrativeState.getInstance());
		
		// Initialize the music
		changeMusic("108316_Corsica_S_welcome_to_our_fairy_world.ogg");
		playMusic();
		this.music.addListener(this);
	}

	/**
	 * @param args no arguments used
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		// Create the container of the application
		AppGameContainer app = new AppGameContainer(new Game("Super Fairy Bros"));
		Save.getInstance().loadSave("./saves/cheat_cheater.save"); //FIXME: remove
	 
		// Application properties
		app.setDisplayMode(800, 600, displayFullScreen);
		app.setShowFPS(displayFPS);
		//app.setSmoothDeltas(true);

		// Start the application
		app.start();
	}
	
	/**
	 * Change the background music of the game
	 * @param filename The name of the music to use from now
	 */
	public void changeMusic(String filename){
		try {
			this.music = new Music(Global.PATH_MUSICS_RESSOURCES+filename,true);
		} catch (SlickException e) {
			e.printStackTrace();
		}				
	}
	/**
	 * @return Whether the music is playing
	 */
	public boolean isMusicPlaying(){
		return this.music.playing();
	}
	/** Start the music */
	public void playMusic(){
		this.music.play();
	}
	/** Start the music and loop it */
	public void loopMusic(){
		this.music.loop();
	}
	/** Stop the music */
	public void stopMusic(){
		this.music.stop();
	}
	/** Change the music using the current level's music */
	public void changeGameplayMusic(){
		LevelSave s = ((GameplayState)this.getState(GAMEPLAY_STATE)).getCurrentLevelModel();
		if(s!=null){
			changeMusic(s.getMusicName());	
			loopMusic();	
		}			
	}
	
	@Override
	/**
	 * Called when we load a new state (when we go from a menu to another menu for example)
	 * @param id The id of the new state (declared at the top of this file)
	 */
	public void enterState(int id){
		super.enterState(id);
		int currentID = this.getCurrentStateID();
		if(id==MAINMENU_STATE && currentID!=NEWGAME_STATE && currentID!=LOADGAME_STATE && currentID!=OPTIONSGAME_STATE){
			changeMusic(MENUS_MUSIC);
			loopMusic();
		}
		else if(id==NARRATIVE_STATE){
			changeGameplayMusic();
		}
	}

	@Override
	/**
	 * Called when the music stops. Overriden to load a music after the initial music ("Welcome to our ...")
	 * @param arg0 The music that just ended
	 */
	public void musicEnded(Music arg0) {
		int currentID = this.getCurrentStateID();
		if(currentID==MAINMENU_STATE || currentID==NEWGAME_STATE 
				|| currentID==LOADGAME_STATE || currentID==OPTIONSGAME_STATE){
			changeMusic(MENUS_MUSIC);
			loopMusic();
		}
		else if(currentID==GAMEPLAY_STATE){
			changeMusic(((GameplayState)this.getCurrentState()).getCurrentLevelModel().getMusicName());	
			loopMusic();		
		}
	}

	@Override
	/**
	 * Called when the music has changed
	 * @param arg0 the previous music (swapped from)
	 * @param arg1 the new music (swapped for)
	 */
	public void musicSwapped(Music arg0, Music arg1) {
		// Do nothing - required by the MusicListener interface
	}
}
