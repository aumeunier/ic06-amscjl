package beta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class LoadGameState extends BasicGameState implements MouseListener {
	static final int BACK_X = 75;
	static final int BACK_Y = 50;
	static final int SAVE_OFFSET_X = 100;
	static final int SAVE_OFFSET_Y = 100;
	static final int SAVE_SPACE_Y = 25;
	static final int SAVE_TEXT_Y = 25;
	private int stateID;
	int selection;
	int selectedSave;
	Image backgroundImage;
	ArrayList<String> saves;

	@Override
	public int getID() {
		return stateID;
	}
	
	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		getSaves();
	}

	public LoadGameState(int id){
		super();
		stateID = id;	
	}

	private ArrayList<String> getSaves(){
		this.saves.clear();
		String stringPath = Global.PATH_SAVES;	
		File path = new File(stringPath);
	    File files[]; 
	    files = path.listFiles();
	    Arrays.sort(files);
	    for (int i = 0, n = files.length; i < n; i++) {
	    	String fileName = files[i].toString();
	    	if(fileName.endsWith(".save")){
	    		saves.add(fileName);
	    	}
	    }		
		return saves;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+"7711466_s.jpg");
		saves = new ArrayList<String>();
		getSaves();
		selection = -1;
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		arg2.setColor(Color.black);
		backgroundImage.draw(0,0,800,600);
		arg2.drawString("Retour", BACK_X, BACK_Y);
		int startX = SAVE_OFFSET_X, startY = SAVE_OFFSET_Y;
		for(String save: saves){
			if(save.contains("/")){
				String saveName = save.substring(save.lastIndexOf('/')+1, save.lastIndexOf(".save"));
				arg2.drawString(saveName, startX, startY);
				startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
			}			
			else if(save.contains("\\")){
				String saveName = save.substring(save.lastIndexOf('\\')+1, save.lastIndexOf(".save"));
				arg2.drawString(saveName, startX, startY);
				startY+=SAVE_SPACE_Y+SAVE_TEXT_Y;
			}			
		}
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2)
			throws SlickException {
		if(selection == Game.MAINMENU_STATE){
			arg1.enterState(Game.MAINMENU_STATE);
		}
		selection = -1;
	}
	

	public void mouseMoved(int oldx, int oldy, int newX, int newY){
		
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= BACK_X && x <= (BACK_X + 50)) 
				&&	(y >= BACK_Y && y <= (BACK_Y + 25))){
			selection = Game.MAINMENU_STATE;
		}
		else if((x >= SAVE_OFFSET_X && x <= (SAVE_OFFSET_X + 100))) {
			float temp = (float)(y-SAVE_OFFSET_Y)%(float)(SAVE_SPACE_Y+SAVE_TEXT_Y);
			if(temp >= 0 && temp <= SAVE_TEXT_Y){
				Save.getInstance().loadSave(saves.get((y-SAVE_OFFSET_Y)/(SAVE_SPACE_Y+SAVE_TEXT_Y)));
				selection = Game.MAINMENU_STATE;
			}
		}
	}
}
