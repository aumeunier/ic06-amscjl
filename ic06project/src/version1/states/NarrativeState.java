package version1.states;

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

import version1.Game;
import version1.Global;


/**
 * The NarrativeState is a text which displays descriptions of the levels before they start.
 * The aim is to place the game in a context which makes sense and looks like a story.
 * The player can read it, whether or not, but he has to press a Button to start the level.
 *
 */
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
	int chosenId = 1;

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
	
	/** 
	 * When a level is chosen, we prepare the text for the narrative state
	 * 
	 * @param i The unique identifier of the level
	 */
	public void ChooseLevel(int i){
		chosenId = i;
		switch(i){
		case 1:
			textToDisplay = "Il �tait une fois, un peuple de f�es. Elles vivaient paisiblement\n" +
					"depuis plusieurs milliers d'ann�es, dans une contr�e lointaine \n" +
					"� l'Ouest du royaume Bros. Leur village se situait dans la vall�e \n" +
					"de �Eel Ti Jut�...  Les f�es se sont toujours tenus � l'�cart du \n" +
					"royaume de la sorci�re (la sorci�re �Ulna Rei�) situ�e dans les \n" +
					"contr�es Est. La sorci�re, elle, r�ve depuis toujours de capturer\n" +
					"le peuple des f�es pour leur arracher leurs ailes qui poss�dent \n" +
					"des  particularit�s  surprenantes. Les f�es peuvent effectivement,\n" +
					"gr�ce � leurs ailes, absorber des pouvoirs dissimul�s dans des \n" +
					"zones magiques (que les f�es arrivent � d�tecter par une aura \n" +
					"�toil�e). Comme chaque 1er jour de l'�t� les deux plus jeunes \n" +
					"f�es de la tribu ont la difficile t�che de r�cup�rer un fruit  \n" +
					"tr�s rare dont seules les f�es connaissent l'existence : \n" +
					" ce fruit qu'elles nomment �Hapsten� est utilis� pour la\n" +
					" c�r�monie  annuelle f��rique et permet, si le nombre de fruit \n" +
					" r�colt� est suffisant, de pouvoir �tre une source de pouvoir \n" +
					" gigantesque ! Ce que ne savent pas nos deux f�es c'est que cette\n" +
					" ann�e la r�colte sera d'une importance cruciale pour la survie de\n" +
					"  la tribu ! Et c'est donc ici que commence l'Histoire de nos 2 \n" +
					"   SUPER FAIRY BROS. !";		
			break;
		case 2:
			textToDisplay = "       Apr�s avoir parcouru les plaines des contr�es Ouest de Bros \n" +
							"              nos deux f�es ont aper�u leur village en feu !\n\n " +
							"		Apr�s une visite des lieux,  \n" +
							"elles ont retrouv� le village d�sert et � moiti� en ruine. \n " +
							"Elles trouv�rent un message mena�ant \n" +
							"accroch� � l�entr�e du village : \n\n" +
							"           � J�ai fait prisonni�re toutes  vos soeurs, \n" +
							"                 j�ai r�cup�r� leurs ailes \n" +
							"                       et elles n�auront plus jamais aucun pouvoir !\n " +
							"                               Bient�t votre tour viendra� \n" +
							"           La Sorci�re �Ulna Rei� � \n\n" +
							"Nos deux f�es ont donc d�cid� d�aller sauver les f�es prisonni�res\n " +
							"de la sorci�re : pour se faire elles vont r�unir le plus possible\n" +
							" de leur fruit magique �Hapsten� afin de fabriquer une source de \n" +
							" pouvoir tr�s puissante et de pouvoir,  peut-�tre,\n " +
							"sauver leurs s�urs et leurs m�res. \n";
			
			
			break;
		case 3:
			textToDisplay = "\n\n\n Nos deux f�es doivent maintenant  traverser la vall�e � Izapov�\n\n" +
					" afin de rejoindre les  grottes  �d� ArgennK �, n�oubliez pas de \n\n" +
					"ramasser autant d��Hapsten� que possible. \n";			break;
		case 4 :
			textToDisplay = "\n\n\nToujours en allant vers l�Est nos deux f�es traversent les grottes \n\n" +
					"qui n�abritent pas toujours de doux compagnons� \n\n" +
					"Juste derri�re ces grottes nous trouveront la Sorci�re�";	break;
		case 5:
			
			textToDisplay = "\n\n\nNos deux h�ros ont r�ussi � atteindre le rep�re de la sorci�re!\n\n" +
					"Une dure bataille les attend maintenant. Si elles parviennent�\n\n" +
					"� se d�barasser de la sorci�re, elles pourront lib�rer leurs amis!!";
			break;
		case 6:
			textToDisplay = "\n\n\nNos deux vaillantes f�es ont atteint leur objectif:\n\n" +
					"elles ont sauv� leurs amies et la sorci�re devrait les laisser tranquilles\n\n" +
					"pour un moment. Cependant, quelques f�es sont encore empoisonn�es,\n\n" +
					"Il faut r�cup�rer tous les fruits Hapsten!!";
			break;
		case 7:
			textToDisplay = "\n\n\nLa sorci�re a �t� vaincue et les f�es sont donc libres.\n\n" +
					"De plus, � l'aide des fruits Hapsten, toutes les f�es ont �t� gu�ries!\n\n" +
					"Bravo!";
			break;
		default:
			textToDisplay = "Pas de description pour ce niveau! :(";
			break;
		}
		if(textField!=null){
			this.textField.setText(textToDisplay);
		}
	}

	public void setBackgroundImage(String imageFilename){
		backgroundImage = Global.getImage(imageFilename);
	}
	public void setTextBackgroundImage(String imageFilename){
		textBackgroundImage = Global.getImage(imageFilename);
	}
	public void setText(String text){
		if(textField != null){
			this.textField.setText(text);
		}
		else {
			this.textFieldSui.setText(text);
		}
	}
	
	@Override
	public void init(GameContainer container, StateBasedGame game)
			throws SlickException {
		backgroundImage = Global.getImage("image_narration.png");
		textBackgroundImage = Global.getImage("scroll_background_page_horizontal.png");
		
		display = new Display(container);
		
		textField = new TextField(container, container.getDefaultFont(), MAP_X+50, MAP_Y+50, MAP_W-100, MAP_H-100);
		textField.setBackgroundColor(new Color(0.0f,0.0f,0.0f,0.0f));
		textField.setBorderColor(new Color(0.0f,0.0f,0.0f,0.0f));
		textField.setTextColor(Color.black);
		textField.setAcceptingInput(false);
		textField.setText(textToDisplay);
		
		Image labelImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE);
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
			if(chosenId > 5){
				this.selection = Game.MAINMENU_STATE;
			}
		}			
	}
}
