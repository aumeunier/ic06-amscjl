package gold;

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


public class MainMenuGameStateNew extends BasicGameState implements
		MouseListener {
	static final boolean optionsActivated = false;
	private static final int ERROR_X = 100;
	private static final int ERROR_Y = 350;
	private static final int ERROR_W = 150;
	private static final int ERROR_H = 40;
	private static final int NEWGAME_X = 270;
	private static final int NEWGAME_Y = 396;
	private static final int NEWGAME_W = 350;
	private static final int NEWGAME_H = 50;
	private static final int LOADGAME_X = NEWGAME_X;
	private static final int LOADGAME_Y = NEWGAME_Y+NEWGAME_H+3;
	private static final int LOADGAME_W = NEWGAME_W;
	private static final int LOADGAME_H = NEWGAME_H;
	private static final int MAP_X = 500;
	private static final int MAP_Y = 200;
	private static final int MAP_BUTTON_W = 70;
	private static final int MAP_BUTTON_H = 70;
	private static final int MAP_BUTTON_W_SPACE = 10;
	private static final int MAP_BUTTON_H_SPACE = 10;	
	private final static String level1imageDefault = "accueil/1-rouge.png";
	private final static String level2imageDefault = "accueil/2-rouge.png";
	private final static String level3imageDefault = "accueil/3-rouge.png";
	private final static String level4imageDefault = "accueil/4-rouge.png";
	private final static String level5imageDefault = "accueil/5-rouge.png";
	private final static String level1imageUnlocked = "accueil/1-jaune.png";
	private final static String level2imageUnlocked = "accueil/2-jaune.png";
	private final static String level3imageUnlocked = "accueil/3-jaune.png";
	private final static String level4imageUnlocked = "accueil/4-jaune.png";
	private final static String level5imageUnlocked = "accueil/5-jaune.png";
	private final static String level1imageFinished = "accueil/1.png";
	private final static String level2imageFinished = "accueil/2.png";
	private final static String level3imageFinished = "accueil/3.png";
	private final static String level4imageFinished = "accueil/4.png";
	private final static String level5imageFinished = "accueil/5.png";
	private int stateID;
	private int selection;
	int levelSelection;
	Image backgroundImage;
	Image level1image;
	Image level2image;
	Image level3image;
	Image level4image;
	Image level5image;
	Display display;
	Label mapErrorLabel;	

	public MainMenuGameStateNew(int id){
		super();
		stateID = id;
		selection = -1;
		levelSelection = -1;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		// Images
		backgroundImage = Global.setImage("accueil/fond.png");
		level1image = Global.setImage(level1imageDefault);
		level2image = Global.setImage(level2imageDefault);
		level3image = Global.setImage(level3imageDefault);
		level4image = Global.setImage(level4imageDefault);
		level5image = Global.setImage(level5imageDefault);
		
		display = new Display(gc);
		Image labelImage = Global.setImage(Global.BUTTON_STANDARD_IMAGE);
		Image playerImage = labelImage.getScaledCopy(150,25);
		mapErrorLabel = new Label(playerImage,"");
		mapErrorLabel.setForeground(Color.red);
		mapErrorLabel.setBounds(800-ERROR_X-ERROR_W,ERROR_Y,ERROR_W,ERROR_H);
		mapErrorLabel.pack();
		this.display.add(mapErrorLabel);
		mapErrorLabel.setImage(null);
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Save s = Save.getInstance();
		if(s.hasSaveLoaded()){
			int[] ids = s.getAllIds();
			for(int i = 0 ; i < ids.length ; i++){
				int[] mapLvl = s.mapPointForLevelID(ids[i]);
				switch(i){
				case 1:
					switch(s.getFinishedStateForLevelID(i)){
					case 0:
						level1image = Global.setImage(level1imageDefault);
						break;
					case 1:
						level1image = Global.setImage(level1imageUnlocked);
						break;
					case 3:
						level1image = Global.setImage(level1imageFinished);
						break;
					default:
						level1image = Global.setImage(level1imageDefault);
						break;
					}
					break;
				case 2:
					switch(s.getFinishedStateForLevelID(i)){
					case 0:
						level2image = Global.setImage(level2imageDefault);
						break;
					case 1:
						level2image = Global.setImage(level2imageUnlocked);
						break;
					case 3:
						level2image = Global.setImage(level2imageFinished);
						break;
					default:
						level2image = Global.setImage(level2imageDefault);
						break;
					}
					break;
				case 3:
					switch(s.getFinishedStateForLevelID(i)){
					case 0:
						level3image = Global.setImage(level3imageDefault);
						break;
					case 1:
						level3image = Global.setImage(level3imageUnlocked);
						break;
					case 3:
						level3image = Global.setImage(level3imageFinished);
						break;
					default:
						level3image = Global.setImage(level3imageDefault);
						break;
					}
					break;
				case 4:
					switch(s.getFinishedStateForLevelID(i)){
					case 0:
						level4image = Global.setImage(level4imageDefault);
						break;
					case 1:
						level4image = Global.setImage(level4imageUnlocked);
						break;
					case 3:
						level4image = Global.setImage(level4imageFinished);
						break;
					default:
						level4image = Global.setImage(level4imageDefault);
						break;
					}
					break;
				case 5:
					switch(s.getFinishedStateForLevelID(i)){
					case 0:
						level5image = Global.setImage(level5imageDefault);
						break;
					case 1:
						level5image = Global.setImage(level5imageUnlocked);
						break;
					case 3:
						level5image = Global.setImage(level5imageFinished);
						break;
					default:
						level5image = Global.setImage(level5imageDefault);
						break;
					}
					break;
				}
			}
		}		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
		g.setColor(Color.black);

		// Draw the background
		backgroundImage.draw();

		// Draw the level images
		int x = MAP_X;
		int y = MAP_Y;
		level1image.draw(x,y,MAP_BUTTON_W,MAP_BUTTON_H);
		x+=MAP_BUTTON_W+MAP_BUTTON_W_SPACE;
		level2image.draw(x,y,MAP_BUTTON_W,MAP_BUTTON_H);
		x+=MAP_BUTTON_W+MAP_BUTTON_W_SPACE;
		level3image.draw(x,y,MAP_BUTTON_W,MAP_BUTTON_H);
		y+=MAP_BUTTON_H+MAP_BUTTON_H_SPACE;
		x=MAP_X;
		level4image.draw(x,y,MAP_BUTTON_W,MAP_BUTTON_H);
		x+=MAP_BUTTON_W+MAP_BUTTON_W_SPACE;
		//level5image.draw(x,y,MAP_BUTTON_W,MAP_BUTTON_H);
		
		display.render(gc, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		if(selection > -1){
			if(selection == Game.GAMEPLAY_STATE){
				((GameplayState)(sbg.getState(Game.GAMEPLAY_STATE))).ChooseLevel(levelSelection);
				selection = Game.NARRATIVE_STATE;
				((NarrativeState)(sbg.getState(Game.NARRATIVE_STATE))).ChooseLevel(levelSelection);
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
		int maxId = Save.getInstance().getNumberOfLoadedLevels();
		if((x >= NEWGAME_X && x <= (NEWGAME_X + NEWGAME_W)) 
				&&	(y >= NEWGAME_Y && y <= (NEWGAME_Y + NEWGAME_H))){
			selection = Game.NEWGAME_STATE;
		}
		else if((x >= LOADGAME_X && x <= (LOADGAME_X + LOADGAME_W)) 
				&&	(y >= LOADGAME_Y && y <= (LOADGAME_Y + LOADGAME_H))){
			selection = Game.LOADGAME_STATE;
		}
		else if((x >= MAP_X && x <= (MAP_X+3*(MAP_BUTTON_W+MAP_BUTTON_W_SPACE)))
				&& (y >= MAP_Y && y <= (MAP_Y+(maxId/3+((maxId%3>0)?1:0))*(MAP_BUTTON_H+MAP_BUTTON_H_SPACE))) ){
			int xIndex = (x-MAP_X)/(MAP_BUTTON_W + MAP_BUTTON_W_SPACE);
			int yIndex = (y-MAP_Y)/(MAP_BUTTON_H + MAP_BUTTON_H_SPACE);
			int id = yIndex*3+xIndex+1;
			if(id > -1 && id <= maxId && Save.getInstance().hasSaveLoaded()){
				if(!Save.getInstance().hasUnlockedLevelWithID(id)){
					mapErrorLabel.setText("Niveau pas encore debloque!");
				}
				else {
					selection = Game.GAMEPLAY_STATE;
					levelSelection = id;
					mapErrorLabel.setText("");
				}
			}
			else if(id > maxId){
				mapErrorLabel.setText("Niveau pas encore developpe !");				
			}
		}
		else {
			mapErrorLabel.setText("");			
		}
	}
	

}
