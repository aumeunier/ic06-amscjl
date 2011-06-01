package gold;

import java.io.File;
import java.util.Arrays;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class NewGameState extends BasicGameState implements MouseListener {
	static final int NAMETEXT1_X = 100;
	static final int NAMETEXT1_Y = 100;
	static final int NAMEFIELD1_X = 100;
	static final int NAMEFIELD1_Y = 120;
	static final int NAMETEXT2_X = 100;
	static final int NAMETEXT2_Y = 180;
	static final int NAMEFIELD2_X = 100;
	static final int NAMEFIELD2_Y = 200;
	static final int NAMEFIELD_W = 300;
	static final int NAMEFIELD_H = 20;
	static final int OK_X = 100;
	static final int OK_Y = 250;
	static final int ERROR_X = 100;
	static final int ERROR_Y = 220;
	static final int BACK_X = 75;
	static final int BACK_Y = 50;
	private int stateID;
	int selection;
	TextField name1textField;
	TextField name2textField;
	Image backgroundImage;
	boolean hasError;
	String errorString;
	
	public NewGameState(int id){
		super();
		stateID = id;	
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		container.getInput();
	}
	
	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		name1textField.setFocus(false);
		name2textField.setFocus(false);
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		name1textField = new TextField(arg0, arg0.getDefaultFont(), NAMEFIELD1_X, NAMEFIELD1_Y, 
				NAMEFIELD_W, NAMEFIELD_H);
		name1textField.setConsumeEvents(true);
		name2textField = new TextField(arg0, arg0.getDefaultFont(), NAMEFIELD2_X, NAMEFIELD2_Y, 
				NAMEFIELD_W, NAMEFIELD_H);
		backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+"7711466_s.jpg");
		selection = -1;
		hasError = false;
		errorString = null;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setColor(Color.black);
		backgroundImage.draw(0,0,800,600);
		arg2.drawString("Retour", BACK_X, BACK_Y);
		arg2.drawString("Quel est le nom du premier joueur ?", NAMETEXT1_X, NAMETEXT1_Y);
		name1textField.render(arg0, arg2);
		arg2.drawString("Quel est le nom du deuxime joueur ?", NAMETEXT2_X, NAMETEXT2_Y);
		arg2.drawString("Valider", OK_X, OK_Y);
		name2textField.render(arg0, arg2);		
		if(hasError){
			arg0.getDefaultFont().drawString(ERROR_X, ERROR_Y, errorString, new Color(255,32,25));	
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(selection == Game.MAINMENU_STATE){
			arg1.enterState(Game.MAINMENU_STATE);
		}
		if(selection == Game.GAMEPLAY_STATE){
			((GameplayState)(arg1.getState(Game.GAMEPLAY_STATE))).ChooseLevel(1);
			((NarrativeState)(arg1.getState(Game.NARRATIVE_STATE))).ChooseLevel(1);
			arg1.enterState(Game.NARRATIVE_STATE);
			Global.CURRENT_GAME_FILENAME = name1textField.getText()+"_"+name2textField.getText();
		}
		selection = -1;
	}

	public void mouseMoved(int oldx, int oldy, int newX, int newY){
		// do nothing
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= OK_X && x <= (OK_X + 75)) 
				&&	(y >= OK_Y && y <= (OK_Y + 25))){
			String name1 = name1textField.getText();
			String name2= name2textField.getText();
			if(name1.length() > 0 && name2.length() > 0){
				if(isNameAvailable(name1+"_"+name2)){
					selection = Game.GAMEPLAY_STATE;
					hasError = false;	
					Save.getInstance().createSave(name1,name2);
				}
				else {
					selection = -1;
					hasError = true;	
					errorString = "Names already taken, load your game!";
				}
			}
			else {
				hasError = true;
				errorString = "You must specify a name for each player !";
			}
		}
		else if((x >= BACK_X && x <= (BACK_X + 50)) 
				&&	(y >= BACK_Y && y <= (BACK_Y + 25))){
			selection = Game.MAINMENU_STATE;
		}
		else if((x >= NAMETEXT1_X && x <= (NAMETEXT1_X + 50)) 
				&&	(y >= NAMETEXT1_Y && y <= (NAMETEXT1_Y + 25))){
			name1textField.setFocus(true);
			name2textField.setFocus(false);
		}
		else if((x >= NAMETEXT2_X && x <= (NAMETEXT2_X + 50)) 
				&&	(y >= NAMETEXT2_Y && y <= (NAMETEXT2_Y + 25))){
			name1textField.setFocus(false);
			name2textField.setFocus(true);
		}
	}
	
	private boolean isNameAvailable(String name){
		String stringPath = "./saves";		
		File path = new File(stringPath);
	    File files[]; 
	    files = path.listFiles();
	    Arrays.sort(files);
	    for (int i = 0, n = files.length; i < n; i++) {
	    	String fileName = files[i].toString();
	    	if(fileName.equals("./saves/"+name+".save")){
	    		return false;
	    	}
	    }		
		return true;
	}
}
