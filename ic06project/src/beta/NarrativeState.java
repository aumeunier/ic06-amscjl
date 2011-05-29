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
			textToDisplay = "Il était une fois, un peuple de fées. Elles vivaient paisiblement\n depuis plusieurs milliers  d'années, dans une contrée lointaine \n à l'Ouest du royaume Bros. Leur village se situait dans la vallée \n de ‘Eel Ti Jut’...  Les fées se sont toujours tenus à l'écart du \n royaume de la sorcière (la sorcière ‘Ulna Rei’) située dans les \n contrées Est. La sorcière, elle, rêve depuis toujours de capturer\n le peuple des fées pour leur arracher leurs ailes qui possèdent \n des  particularités  surprenantes. Les fées peuvent effectivement\n grâce à leurs ailes absorber des pouvoirs dissimulés dans des \n zones magiques (que les fées arrivent à détecter par une aura \n étoilé). Comme chaque 1er jour de l'été les deux plus jeunes fées\n de la tribu ont la difficile tâche de récupérer un fruit très rare dont\n seules les fées connaissent l'existence : ce fruit qu'elles nomment \n ‘Hapsten’. Ce fruit est utilisé pour la cérémonie  annuelle féérique\n et permet, si le  nombre de fruit récolté est suffisant, de pouvoir\n être une source de pouvoir gigantesque ! Ce que ne savent pas \n nos deux fées c'est que cette année la récolte sera d'une\n importance capitale pour la survie de la tribu ! Et c'est donc ici que \n commence l'Histoire de nos 2 Super  Fairy Bros !";		
			break;
		case 2:
			textToDisplay = "Après avoir parcouru les plaines des contrées ouest de Bros nos \n " +
							"deux fées ont aperçu leur village en feu ! Après une visite des \n " +
							"lieux,  elles ont retrouvé le village désert et à moitié en ruine. \n " +
							"Elles trouvèrent un message menaçant accroché à l’entrée du \n " +
							"village : \n\n\n« J’ai fait prisonnière toutes  vos soeurs, j’ai récupéré " +
							"leurs ailes et elles n’auront plus jamais aucun pouvoir !\n " +
							"Bientôt votre tour viendra… La Sorcière ‘Ulna Rei’ » \n \n\n" +
							"Nos deux fées ont donc décidé d’aller sauver les fées prisonnières " +
							"de la sorcière : pour se faire elles vont réunir le plus possible" +
							" de leur fruit magique ‘Hapsten’ afin de fabriquer une source de " +
							" pouvoir très puissante et de pouvoir,  peut-être, " +
							"sauver leurs sœurs et leurs mères. \n";
			
			
			break;
		case 3:
			textToDisplay = "\n\n\n Nos deux fées doivent maintenant  traverser le mont ‘ Izapov’\n\n" +
					" afin de rejoindre les  grottes  ‘d’ ArgennK ‘, n’oubliez pas de \n\n" +
					"ramasser autant d’’Hapsten’ que possible. \n";			break;
		case 4 :
			textToDisplay = "\n\n\nToujours en allant vers l’Est nos deux fées traversent les grottes \n\n" +
					"qui n’abritent pas toujours de doux compagnons… \n\n" +
					"Juste derrière ces grottes nous trouveront la Sorcière…";	break;
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
