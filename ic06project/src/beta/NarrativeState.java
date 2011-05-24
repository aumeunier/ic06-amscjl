package beta;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class NarrativeState extends BasicGameState implements MouseListener {
	private static NarrativeState instance;
	static final int MAP_X = 100;
	static final int MAP_Y = 150;
	static final int MAP_W = 600;
	static final int MAP_H = 300;
	static final int CONTINUE_X = 340;
	static final int CONTINUE_Y = MAP_Y+MAP_H+20;
	static final int CONTINUE_W = 120;
	static final int CONTINUE_H = 30;
	private int stateID;
	private int selection;
	Image backgroundImage;
	Image textBackgroundImage;
	mdes.slick.sui.TextField textFieldSui;
	TextField textField;
	Display display;
	String textToDisplay = "";

	@Override
	public int getID() {
		return this.stateID;
	}
	
	public static NarrativeState getInstance(){
		if(instance == null){
			instance = new NarrativeState(Game.NARRATIVE_STATE);
		}
		return instance;
	}
	private NarrativeState(int id){
		super();
		stateID = id;
		selection = -1;
	}

	public void setBackgroundImage(String imageFilename){
		backgroundImage = Global.setImage(imageFilename);
	}
	public void setTextBackgroundImage(String imageFilename){
		textBackgroundImage = Global.setImage(imageFilename);
	}
	public void setText(String text){
		if(textField != null){
			this.textField.setText(text);
		}
		else {
			this.textFieldSui.setText(text);
		}
	}
	public void ChooseLevel(int i){
		switch(i){
		case 1:
			textToDisplay = "Pas de description pour ce niveau 1! :(";
			break;
		case 2:
			textToDisplay = "Pas de description pour ce niveau 2! :(";
			break;
		case 3:
			textToDisplay = "Pas de description pour ce niveau 3! :(";
			break;
		case 4 :
			textToDisplay = "Pas de description pour ce niveau 4! :(";
			break;
		default:
			textToDisplay = "Pas de description pour ce niveau! :(";
			break;
		}
		if(textField!=null){
			this.textField.setText(textToDisplay);
		}
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		backgroundImage = Global.setImage("papyrus_page.png");
		textBackgroundImage = Global.setImage("scroll_background_page_horizontal.png");
		
		display = new Display(container);
		
		textField = new TextField(container, container.getDefaultFont(), MAP_X+50, MAP_Y+50, MAP_W-100, MAP_H-100);
		textField.setBackgroundColor(new Color(0.0f,0.0f,0.0f,0.0f));
		textField.setBorderColor(new Color(0.0f,0.0f,0.0f,0.0f));
		textField.setTextColor(Color.black);
		textField.setAcceptingInput(false);
		textField.setText(textToDisplay);
		
		Image labelImage = Global.setImage("blur11.jpg");
		Image continueImage = labelImage.getScaledCopy(CONTINUE_W, CONTINUE_H);
		Label continueLabel = new Label(continueImage,"Continue"); //TODO: button
		continueLabel.setBounds(CONTINUE_X,CONTINUE_Y,CONTINUE_W,CONTINUE_H);
		continueLabel.pack();
		this.display.add(continueLabel);
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g)
			throws SlickException {
		g.setColor(Color.black);

		// Top part
		backgroundImage.draw(0,0,800,600);
		textBackgroundImage.draw(MAP_X,MAP_Y,MAP_W,MAP_H);
		if(textField != null){
			textField.render(container, g);
		}
		
		display.render(container, g);
	}

	@Override
	public void update(GameContainer container, StateBasedGame game, int delta)
			throws SlickException {
		if(this.selection != -1){
			game.enterState(selection);
			selection = -1;
		}
	}

	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= CONTINUE_X && x <= (CONTINUE_X + CONTINUE_W)) 
				&&	(y >= CONTINUE_Y && y <= (CONTINUE_Y + CONTINUE_H))){
			this.selection = Game.GAMEPLAY_STATE;			
		}			
	}
}
