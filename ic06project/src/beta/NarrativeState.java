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
	static final int MAP_X = 50;
	static final int MAP_Y = 25;
	static final int MAP_W = 700;
	static final int MAP_H = 500;
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
			textToDisplay = "Il �tait une fois, un peuple de f�es. Elles vivaient paisiblement\n depuis plusieurs milliers  d'ann�es, dans une contr�e lointaine \n � l'Ouest du royaume Bros. Leur village se situait dans la vall�e \n de �Eel Ti Jut�...  Les f�es se sont toujours tenus � l'�cart du \n royaume de la sorci�re (la sorci�re �Ulna Rei�) situ�e dans les \n contr�es Est. La sorci�re, elle, r�ve depuis toujours de capturer\n le peuple des f�es pour leur arracher leurs ailes qui poss�dent \n des  particularit�s  surprenantes. Les f�es peuvent effectivement\n gr�ce � leurs ailes absorber des pouvoirs dissimul�s dans des \n zones magiques (que les f�es arrivent � d�tecter par une aura \n �toil�). Comme chaque 1er jour de l'�t� les deux plus jeunes f�es\n de la tribu ont la difficile t�che de r�cup�rer un fruit tr�s rare dont\n seules les f�es connaissent l'existence : ce fruit qu'elles nomment \n �Hapsten�. Ce fruit est utilis� pour la c�r�monie  annuelle f��rique\n et permet, si le  nombre de fruit r�colt� est suffisant, de pouvoir\n �tre une source de pouvoir gigantesque ! Ce que ne savent pas \n nos deux f�es c'est que cette ann�e la r�colte sera d'une\n importance capitale pour la survie de la tribu ! Et c'est donc ici que \n commence l'Histoire de nos 2 Super  Fairy Bros !";		
			break;
		case 2:
			textToDisplay = "Apr�s avoir parcouru les plaines des contr�es ouest de Bros nos \n " +
							"deux f�es ont aper�u leur village en feu ! Apr�s une visite des \n " +
							"lieux,  elles ont retrouv� le village d�sert et � moiti� en ruine. \n " +
							"Elles trouv�rent un message mena�ant accroch� � l�entr�e du \n " +
							"village : \n\n\n� J�ai fait prisonni�re toutes  vos soeurs, j�ai r�cup�r� " +
							"leurs ailes et elles n�auront plus jamais aucun pouvoir !\n " +
							"Bient�t votre tour viendra� La Sorci�re �Ulna Rei� � \n \n\n" +
							"Nos deux f�es ont donc d�cid� d�aller sauver les f�es prisonni�res " +
							"de la sorci�re : pour se faire elles vont r�unir le plus possible" +
							" de leur fruit magique �Hapsten� afin de fabriquer une source de " +
							" pouvoir tr�s puissante et de pouvoir,  peut-�tre, " +
							"sauver leurs s�urs et leurs m�res. \n";
			
			
			break;
		case 3:
			textToDisplay = "\n\n\n Nos deux f�es doivent maintenant  traverser le mont � Izapov�\n\n" +
					" afin de rejoindre les  grottes  �d� ArgennK �, n�oubliez pas de \n\n" +
					"ramasser autant d��Hapsten� que possible. \n";			break;
		case 4 :
			textToDisplay = "\n\n\nToujours en allant vers l�Est nos deux f�es traversent les grottes \n\n" +
					"qui n�abritent pas toujours de doux compagnons� \n\n" +
					"Juste derri�re ces grottes nous trouveront la Sorci�re�";	break;
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
		
		Image labelImage = Global.setImage(Global.BUTTON_STANDARD_IMAGE);
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
