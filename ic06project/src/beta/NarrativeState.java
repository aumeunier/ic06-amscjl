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
			textToDisplay = "Il �tait une fois, un peuple de f�es...\n Elles vivaient paisiblement depuis plusieurs milliers \n d'ann�es, dans une contr�e lointaine � l'Ouest du royaume \n Bros. Leur village se situait dans la \n vall�e de �Eel Ti Jut�... \n Les f�es se sont toujours tenus � l'�cart du\n royaume de la sorci�re (la \n sorci�re �Ulna Rei�) situ�e dans les contr�es Est.\n La sorci�re, elle, r�ve depuis toujours de \n capturer le peuple des f�es pour leur arracher \n leurs ailes qui poss�dent des particularit�s \n  surprenantes.\n Les f�es peuvent effectivement gr�ce \n � leurs ailes absorber des pouvoirs \n dissimul�s dans des zones magiques (que les\n f�es arrivent � d�tecter par une aura �toil�). \n Comme chaque 1er jour de l'�t� les deux plus\n jeunes f�es de la tribu ont la difficile t�che de \n r�cup�rer un fruit tr�s rare dont seules les f�es\n connaissent l'existence : ce fruit qu'elles \n nomment �Hapsten�. \n Ce fruit est utilis� pour la c�r�monie \n annuelle f��rique et permet, si le \n nombre de fruit r�colt� est suffisant, de \n pouvoir �tre une source de pouvoir gigantesque ! Ce \n que ne savent pas nos deux f�es c'est\n que cette ann�e la r�colte sera d'une importance \n capitale pour la survie de la tribu ! \n Et c'est donc ici que commence l'Histoire de nos 2 Super \n Fairy Bros !";
			break;
		case 2:
			textToDisplay = "Apr�s avoir parcouru les plaines des contr�es\n ouest de Bros nos deux f�es ont aper�u leur village\n en feu ! \n Apr�s une visite des lieux,  elles ont retrouv�\n le village d�sert et � moiti� en ruine. \n Elles trouv�rent un message mena�ant\n accroch� � l�entr�e du village : � J�ai fait prisonni�re toutes \n vos soeurs, j�ai r�cup�r� leurs ailes et elles \n n�auront plus jamais aucun pouvoir ! Bient�t votre \n tour viendra� \n La Sorci�re �Ulna Rei� � \n Nos deux f�es ont donc d�cid� d�aller \n sauver les f�es prisonni�res de la sorci�re : pour se faire \n elles vont r�unir le plus possible de leur fruit\n magique �Hapsten� afin de fabriquer une source de \n pouvoir tr�s puissante et de pouvoir, \n peut-�tre, sauver leurs s�urs et leurs m�res. \n";
			break;
		case 3:
			textToDisplay = "Nos deux f�es doivent maintenant \n traverser le mont � Izapov� afin de rejoindre les \n  grottes  �d� ArgennK �, n�oubliez pas \n de ramasser autant d��Hapsten� que possible. \n";
			break;
		case 4 :
			textToDisplay = "Toujours en allant vers l�Est nos deux f�es traversent\n les grottes qui n�abritent pas toujours de doux \n compagnons� Juste derri�re ces grottes nous trouveront la Sorci�re�";
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
