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
	static final boolean optionsActivated = false;
	static final int NEWGAME_X = 50;
	static final int NEWGAME_Y = 50;
	static final int NEWGAME_W = 200;
	static final int NEWGAME_H = 40;
	static final int LOADGAME_X = 50;
	static final int LOADGAME_Y = 120;
	static final int LOADGAME_W = 200;
	static final int LOADGAME_H = 40;
	static final int OPTIONS_X = 50;
	static final int OPTIONS_Y = 190;
	static final int OPTIONS_W = 200;
	static final int OPTIONS_H = 40;
	static final int TITLE_X = 450;
	static final int TITLE_Y = 100;
	static final int TITLE_W = 200;
	static final int TITLE_H = 40;
	static final int MAP_X = 0;
	static final int MAP_Y = 300;
	static final int MAP_W = 0;
	static final int MAP_H = 300;
	private int stateID;
	int selection;
	int levelSelection;
	Image backgroundImage;
	//Image topBackgroundImage;
	//Image newGameImage;
	//Image loadGameImage;
	//Image optionsImage;
	//Image titleImage;
	Image mapImage;
	Image mapLvlImage;	
	Display display;
	Label newGameLabel;
	Label loadGameLabel;
	Label optionsLabel;
	Label titleLabel;
	
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
		newGameLabel = new Label();
		/*
		topBackgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+"9170733_s.jpg");
		newGameImage = new Image(Global.PATH_IMAGES_RESSOURCES+"menunewgame.png");
		loadGameImage = new Image(Global.PATH_IMAGES_RESSOURCES+"menunewgame.png");
		optionsImage = new Image(Global.PATH_IMAGES_RESSOURCES+"menunewgame.png");
		titleImage = new Image(Global.PATH_IMAGES_RESSOURCES+"menunewgame.png");
		*/
		mapImage = new Image(Global.PATH_IMAGES_RESSOURCES+"scroll_background_page_horizontal.png");
		mapLvlImage = new Image(Global.PATH_IMAGES_RESSOURCES+"map.jpeg");
		
		// Labels
		display = new Display(gc);
		Image labelImage = Global.setImage("blur11.jpg");
		Image playerImage = labelImage.getScaledCopy(NEWGAME_W,NEWGAME_H);
		newGameLabel = new Label(playerImage,"New Game");
		newGameLabel.setForeground(Color.black);
		newGameLabel.setBounds(NEWGAME_X,NEWGAME_Y,NEWGAME_W,NEWGAME_H);
		newGameLabel.pack();
		this.display.add(newGameLabel);
		newGameLabel.setImage(null);

		loadGameLabel = new Label(playerImage,"Load Game");
		loadGameLabel.setForeground(Color.black);
		loadGameLabel.setBounds(LOADGAME_X,LOADGAME_Y,LOADGAME_W,LOADGAME_H);
		loadGameLabel.pack();
		this.display.add(loadGameLabel);
		loadGameLabel.setImage(null);
		
		if(optionsActivated){
			optionsLabel = new Label(playerImage,"Options");
			optionsLabel.setForeground(Color.black);
			optionsLabel.setBounds(OPTIONS_X,OPTIONS_Y,OPTIONS_W,OPTIONS_H);
			optionsLabel.pack();
			this.display.add(optionsLabel);
			optionsLabel.setImage(null);
		}

		titleLabel = new Label(playerImage,"Pixie Powa!");
		titleLabel.setForeground(Color.black);
		titleLabel.setBounds(TITLE_X,TITLE_Y,TITLE_W,TITLE_H);
		titleLabel.pack();
		this.display.add(titleLabel);
		titleLabel.setImage(null);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.setColor(Color.black);
		backgroundImage.draw(-backgroundImage.getWidth()/8,-backgroundImage.getHeight()/8,300,800);
		/*
		topBackgroundImage.draw(0,-topBackgroundImage.getHeight(),
				topBackgroundImage.getWidth()*2,topBackgroundImage.getHeight()*2);
		newGameImage.draw(NEWGAME_X,NEWGAME_Y);
		loadGameImage.draw(LOADGAME_X,LOADGAME_Y);
		if(optionsActivated){
			optionsImage.draw(OPTIONS_X,OPTIONS_Y);
		}
		titleImage.draw(TITLE_X,TITLE_Y);
		*/
		mapImage.draw(MAP_X,MAP_Y,800-2*MAP_X,600-MAP_Y);
		Save s = Save.getInstance();
		int[] ids = s.getAllIds();
		for(int i = 0 ; i < ids.length ; i++){
			int[] mapLvl = s.mapPointForLevelID(ids[i]);
			if(mapLvl!=null){
				mapLvlImage.draw(MAP_X+mapLvl[0],MAP_Y+mapLvl[1],mapLvl[2],mapLvl[3]);
			}
		}
		
		display.render(gc, g);
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
 
	@SuppressWarnings("unused")
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= NEWGAME_X && x <= (NEWGAME_X + newGameLabel.getWidth())) 
				&&	(y >= NEWGAME_Y && y <= (NEWGAME_Y + newGameLabel.getHeight()))){
			selection = Game.NEWGAME_STATE;
		}
		else if((x >= LOADGAME_X && x <= (LOADGAME_X + loadGameLabel.getWidth())) 
				&&	(y >= LOADGAME_Y && y <= (LOADGAME_Y + loadGameLabel.getHeight()))){
			selection = Game.LOADGAME_STATE;
		}
		else if(optionsActivated && (x >= OPTIONS_X && x <= (OPTIONS_X + optionsLabel.getWidth())) 
				&&	(y >= OPTIONS_Y && y <= (OPTIONS_Y + optionsLabel.getHeight()))){
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
