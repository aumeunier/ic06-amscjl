package gold;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame implements MusicListener {
	public static final int MAINMENU_STATE = 0;
	public static final int GAMEPLAY_STATE = 1;
	public static final int NEWGAME_STATE = 2;
	public static final int LOADGAME_STATE = 3;
	public static final int OPTIONSGAME_STATE = 4;
	public static final int NARRATIVE_STATE = 5;
	public static final int GO_TO_NEXT_LEVEL = -100;
	public static final int SHOULD_RESTART = -200;
	public static final int STOP_PLAY_MUSIC = -300;
	
	private final static String MENUS_MUSIC = "duckett_-_Pequennas_Alas_(PC_TA_mix).ogg";

	public static boolean displayFullScreen = false;
	static final boolean displayFPS = false;
	static final boolean optionsActivated = false;
	
	public Music music;
	
	public Game(String name) {
		super(name);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {		
		// Initialize the states
		//addState(new MainMenuGameState(MAINMENU_STATE));
		addState(new MainMenuGameStateNew(MAINMENU_STATE));
		addState(new GameplayState(GAMEPLAY_STATE));
		addState(new NewGameState(NEWGAME_STATE));
		addState(new LoadGameState(LOADGAME_STATE));
		addState(new OptionsGameState(OPTIONSGAME_STATE));
		addState(NarrativeState.getInstance());
		
		// Initialize the music
		changeMusic("108316_Corsica_S_welcome_to_our_fairy_world.ogg");
		playMusic();
		this.music.addListener(this);
	}

	/**
	 * @param args
	 * @throws SlickException 
	 */
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Game("Super Fairy Bros"));
		Save.getInstance().loadSave("./saves/cheat_cheater.save"); //TODO: automatically load last one ?
	 
		// Application properties
		app.setDisplayMode(800, 600, displayFullScreen);
		app.setShowFPS(displayFPS);
		//app.setSmoothDeltas(true);

		app.start();
	}
	
	public void changeMusic(String filename){
		try {
			this.music = new Music(Global.PATH_MUSICS_RESSOURCES+filename,true);
		} catch (SlickException e) {
			e.printStackTrace();
		}				
	}
	public boolean isMusicPlaying(){
		return this.music.playing();
	}
	public void playMusic(){
		this.music.play();
	}
	public void loopMusic(){
		this.music.loop();
	}
	public void stopMusic(){
		this.music.stop();
	}
	public void changeGameplayMusic(){
		LevelSave s = ((GameplayState)this.getState(GAMEPLAY_STATE)).getCurrentLevelModel();
		if(s!=null){
			changeMusic(s.getMusicName());	
			loopMusic();	
		}			
	}
	
	@Override
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
	public void musicSwapped(Music arg0, Music arg1) {
		// do nothing
	}
}
