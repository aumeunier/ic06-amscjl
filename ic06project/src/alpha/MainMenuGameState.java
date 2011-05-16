package alpha;


import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class MainMenuGameState extends BasicGameState implements MouseListener {
	static final boolean optionsActivated = true;
	static final float SCALE_DOWN_W = 300.0f/500.0f;
	static final int NEWGAME_X = 50;
	static final int NEWGAME_Y = 50;
	static final int LOADGAME_X = 50;
	static final int LOADGAME_Y = 120;
	static final int OPTIONS_X = 50;
	static final int OPTIONS_Y = 190;
	static final int TITLE_X = 450;
	static final int TITLE_Y = 100;
	static final int MAP_X = 0;
	static final int MAP_Y = 300;
	static final int MAP_W = 0;
	static final int MAP_H = 300;
	private int stateID;
	int selection;
	int levelSelection;
	Image backgroundImage;
	//Image topBackgroundImage;
	Image newGameImage;
	Image loadGameImage;
	Image optionsImage;
	Image titleImage;
	Image mapImage;
	Image mapLvlImage;	
	
	public MainMenuGameState(int id){
		super();
		stateID = id;
		selection = -1;
		levelSelection = -1;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		// Images
		backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+"papyrus_page.png");
		backgroundImage.rotate(90);
		mapImage = new Image(Global.PATH_IMAGES_RESSOURCES+"scroll_background_page_horizontal.png");
		mapLvlImage = new Image(Global.PATH_IMAGES_RESSOURCES+"map.jpeg");
		
		// Labels
		newGameImage = Global.setImage("main_menu_nouvelle_partie.png");
		newGameImage = newGameImage.getScaledCopy((int) (newGameImage.getWidth()*SCALE_DOWN_W),newGameImage.getHeight());

		loadGameImage = Global.setImage("main_menu_charger_partie.png");
		loadGameImage = loadGameImage.getScaledCopy((int) (loadGameImage.getWidth()*SCALE_DOWN_W),loadGameImage.getHeight());
		
		if(optionsActivated){
			optionsImage = Global.setImage("main_menu_options.png");
			optionsImage = optionsImage.getScaledCopy((int) (optionsImage.getWidth()*SCALE_DOWN_W),optionsImage.getHeight());
		}

		//titleImage = Global.setImage("main_menu_options.png");
		//titleImage = titleImage.getScaledCopy((int) (titleImage.getWidth()*SCALE_DOWN_W),titleImage.getHeight());
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		
		// Top part
		backgroundImage.draw(-backgroundImage.getWidth()/8,-backgroundImage.getHeight()/8,300,800);
		//titleImage.draw(TITLE_X,TITLE_Y);
		newGameImage.draw(NEWGAME_X,NEWGAME_Y);
		loadGameImage.draw(LOADGAME_X,LOADGAME_Y);
		optionsImage.draw(OPTIONS_X,OPTIONS_Y);
		
		// Map part
		mapImage.draw(MAP_X,MAP_Y,800-2*MAP_X,600-MAP_Y);
		Save s = Save.getInstance();
		int[] ids = s.getAllIds();
		for(int i = 0 ; i < ids.length ; i++){
			int[] mapLvl = s.mapPointForLevelID(ids[i]);
			if(mapLvl!=null){
				mapLvlImage.draw(MAP_X+mapLvl[0],MAP_Y+mapLvl[1],mapLvl[2],mapLvl[3]);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		if(selection > -1){
			if(selection == Game.GAMEPLAY_STATE){
				((GameplayState)(sbg.getState(Game.GAMEPLAY_STATE))).ChooseLevel(levelSelection);	
			}
			sbg.enterState(selection);				
		}
		selection = -1;
	}

	@Override
	public int getID() {
		return stateID;
	}
	
	public void mouseMoved(int oldx, int oldy, int newX, int newY){
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= NEWGAME_X && x <= (NEWGAME_X + newGameImage.getWidth())) 
				&&	(y >= NEWGAME_Y && y <= (NEWGAME_Y + newGameImage.getHeight()))){
			selection = Game.NEWGAME_STATE;
		}
		else if((x >= LOADGAME_X && x <= (LOADGAME_X + loadGameImage.getWidth())) 
				&&	(y >= LOADGAME_Y && y <= (LOADGAME_Y + loadGameImage.getHeight()))){
			selection = Game.LOADGAME_STATE;
		}
		else if(optionsActivated && (x >= OPTIONS_X && x <= (OPTIONS_X + optionsImage.getWidth())) 
				&&	(y >= OPTIONS_Y && y <= (OPTIONS_Y + optionsImage.getHeight()))){
			selection = Game.OPTIONSGAME_STATE;
		}
		else {
			int id = Save.getInstance().levelIdForPoint(x-MAP_X, y-MAP_Y);
			if(id > -1){
				selection = Game.GAMEPLAY_STATE;
				levelSelection = id;
			}
		}
	}
}
