package version1.states;

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

import version1.Game;
import version1.Global;
import version1.data.Save;

/**
 * @deprecated
 * @author aurelien
 *
 */
public class MainMenuGameState extends BasicGameState implements MouseListener {
	static final boolean optionsActivated = false;
	static final float SCALE_DOWN_W = 300.0f/500.0f;
	static final int NEWGAME_X = 50;
	static final int NEWGAME_Y = 75;
	static final int LOADGAME_X = 50;
	static final int LOADGAME_Y = 145;
	static final int OPTIONS_X = 50;
	static final int OPTIONS_Y = 215;
	static final int TITLE_X = 250;
	static final int TITLE_Y = 0;
	static final int MAP_X = 0;
	static final int MAP_Y = 300;
	static final int ERROR_X = 150;
	static final int ERROR_Y = 350;
	static final int ERROR_W = 150;
	static final int ERROR_H = 40;
	private int stateID;
	private int selection;
	int levelSelection;
	Image backgroundImage;
	Image newGameImage;
	Image loadGameImage;
	Image optionsImage;
	Image titleImage;
	Image mapImage;
	Image mapLvlDefaultImage;	
	Image mapLvlUnlockedImage;	
	Image mapLvlUnlockedFinishedImage;	
	Image mapLvlFinishedImage;
	Display display;
	Label mapErrorLabel;	

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
		backgroundImage = Global.getImage("papyrus_page.png");
		backgroundImage.rotate(90);
		mapImage = Global.getImage("scroll_background_page_horizontal.png");
		mapLvlDefaultImage = Global.getImage("bloque_niveau.png");
		mapLvlUnlockedImage = Global.getImage("debut_niveau.png");
		mapLvlUnlockedFinishedImage = Global.getImage("fin_niveau.png");
		mapLvlFinishedImage = Global.getImage("fin_niveau.png");

		// Labels
		newGameImage = Global.getImage("main_menu_nouvelle_partie.png");
		newGameImage = newGameImage.getScaledCopy((int) (newGameImage.getWidth()*SCALE_DOWN_W),newGameImage.getHeight());

		loadGameImage = Global.getImage("main_menu_charger_partie.png");
		loadGameImage = loadGameImage.getScaledCopy((int) (loadGameImage.getWidth()*SCALE_DOWN_W),loadGameImage.getHeight());

		if(optionsActivated){
			optionsImage = Global.getImage("main_menu_options.png");
			optionsImage = optionsImage.getScaledCopy((int) (optionsImage.getWidth()*SCALE_DOWN_W),optionsImage.getHeight());
		}

		titleImage = Global.getImage("main_menu_title.png");
		titleImage = titleImage.getScaledCopy((int) (titleImage.getWidth()*SCALE_DOWN_W*1.5),titleImage.getHeight());

		display = new Display(gc);
		Image labelImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE);
		Image playerImage = labelImage.getScaledCopy(150,25);
		mapErrorLabel = new Label(playerImage,"");
		mapErrorLabel.setForeground(Color.red);
		mapErrorLabel.setBounds(800-ERROR_X-ERROR_W,ERROR_Y,ERROR_W,ERROR_H);
		mapErrorLabel.pack();
		this.display.add(mapErrorLabel);
		mapErrorLabel.setImage(null);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
		g.setColor(Color.black);

		// Top part
		backgroundImage.draw(-backgroundImage.getWidth()/8,-backgroundImage.getHeight()/8,300,800);
		titleImage.draw(TITLE_X,TITLE_Y);
		newGameImage.draw(NEWGAME_X,NEWGAME_Y);
		loadGameImage.draw(LOADGAME_X,LOADGAME_Y);
		if(MainMenuGameState.optionsActivated){
			optionsImage.draw(OPTIONS_X,OPTIONS_Y);
		}

		// Map part
		mapImage.draw(MAP_X,MAP_Y,800-2*MAP_X,600-MAP_Y);
		Save s = Save.getInstance();
		if(s.hasSaveLoaded()){
			int[] ids = s.getAllIds();
			for(int i = 0 ; i < ids.length ; i++){
				int[] mapLvl = s.mapPointForLevelID(ids[i]);
				if(mapLvl!=null){
					switch(s.getFinishedStateForLevelID(ids[i])){
					case 0:
						mapLvlDefaultImage.draw(MAP_X+mapLvl[0],MAP_Y+mapLvl[1],mapLvl[2],mapLvl[3]);
						break;
					case 1:
						mapLvlUnlockedImage.draw(MAP_X+mapLvl[0],MAP_Y+mapLvl[1],mapLvl[2],mapLvl[3]);
						break;
					case 2:
						mapLvlFinishedImage.draw(MAP_X+mapLvl[0],MAP_Y+mapLvl[1],mapLvl[2],mapLvl[3]);
						break;
					case 3:
						mapLvlUnlockedFinishedImage.draw(MAP_X+mapLvl[0],MAP_Y+mapLvl[1],mapLvl[2],mapLvl[3]);
						break;
					default:
						break;
					}
				}
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
			if(id > -1 && Save.getInstance().hasSaveLoaded()){
				if(!Save.getInstance().hasUnlockedLevelWithID(id)){
					mapErrorLabel.setText("Niveau pas encore debloque!");
				}
				else {
					selection = Game.GAMEPLAY_STATE;
					levelSelection = id;
					mapErrorLabel.setText("");
				}
			}
		}
	}
}
