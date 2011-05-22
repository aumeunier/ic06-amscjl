package beta;

import java.util.ArrayList;

import mdes.slick.sui.Display;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Level {	
	protected final static int CH1_INIT_X = 50;
	protected final static int CH1_INIT_Y = Global.GAMEPLAYHEIGHT-50;
	protected final static int CH2_INIT_X = Global.GAMEPLAYWIDTH-100;
	protected final static int CH2_INIT_Y = Global.GAMEPLAYHEIGHT-50;
	protected GameplayState myState;
	protected ArrayList<Sprite> sprites;
	protected ArrayList<Exit> listeExit;
	protected ArrayList<InGameIndication> indications;
	protected Character character1;
	protected Character character2;
	protected LevelSave levelModel;
	protected Image backgroundImage;
	protected int levelID;
	protected int nbBonus=0;
	private Display display;
	
	public Level(GameplayState state, LevelSave model){
		this.myState = state;
		this.sprites = new ArrayList<Sprite>();
		this.listeExit = new ArrayList<Exit>();
		this.indications = new ArrayList<InGameIndication>();
		this.levelModel = model;
	}
	
	public void setDisplay(GameContainer gc){
		this.display = new Display(gc);
	}
	
	protected void addIndication(int x, int y, String text){
		InGameIndication indication = new InGameIndication(x,y,text);
		indications.add(indication);
	}

	protected void setBackgroundImage(String filename){
		try {
			this.backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+filename);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected Wall createWall(int x, int y, int w, int h){
		
		// Create a wall object
		Wall wall = new Wall(x,y,w,h);
		//Sprite S2 = new Sprite(x,y-25,w,50);
		//S2.setImage("herbe3.png");
		
		
		// Add it to the list of sprites of the level
		sprites.add(wall);
		//sprites.add(S2);
		
		// Create the wall body
		myState.addWall(wall);
		
		return wall;
	}
	
	protected Ground createGround(int x, int y, int w, int h){
		
		// Create a wall object
		Ground ground = new Ground(x,y,w,h);
		//Sprite S2 = new Sprite(x,y-25,w,50);
		//S2.setImage("herbe3.png");
		
		
		// Add it to the list of sprites of the level
		sprites.add(ground);
		//sprites.add(S2);
		
		// Create the wall body
		myState.addGround(ground);
		
		return ground;
	}
	
	protected Wall createWallWithPoints(int x, int y, int w, int h, ArrayList<Vec2> point){
		
		// Create a wall object
		Wall wall = new Wall(x,y,w,h);
		
		// Add it to the list of sprites of the level
		sprites.add(wall);
		
		// Create the wall body
		myState.addWallWithPoints(wall, point);
		
		return wall;
	}

	protected Obstacle createObstacle(int x, int y, int w, int h){
		
		// Create a wall object
		Obstacle obstacle = new Obstacle(x,y,w,h);

		// Add it to the list of sprites of the level
		sprites.add(obstacle);
		
		// Create the obstacle body
		myState.addObstacle(obstacle);
		
		return obstacle;
	}
	protected Obstacle createObstacleWithPoints(int x, int y, int w, int h, ArrayList<Vec2> point){
		
		// Create a wall object
		Obstacle obstacle = new Obstacle(x,y,w,h);

		// Add it to the list of sprites of the level
		sprites.add(obstacle);
		
		// Create the obstacle body
		myState.addObstacleWithPoints(obstacle, point);
		
		return obstacle;
	}

	protected Exit createExit(int x, int y, int w, int h){
		
		// Create a wall object
		Exit exit = new Exit(x,y,w,h);

		// Add it to the list of sprites of the level
		//sprites.add(exit);
		listeExit.add(exit);
		
		// Create the obstacle body
		myState.addExit(exit);
		
		return exit;
	}
	
	protected Source createSource(int x, int y, int w, int h, Power _power){
		
		// Create a source object
		Source source = new Source(x,y,w,h,_power);
		
		// Add it to the list of sprites of this level
		sprites.add(source);
		
		// Create the source body
		myState.addSource(source);
		
		return source;
	}
	protected Transporter createTransporter(int x, int y, int w, int h, int nx, int ny){
		
		// Create a source object
		Transporter transporter = new Transporter(x,y,w,h,nx,ny);
		
		// Add it to the list of sprites of this level
		sprites.add(transporter);
		
		// Create the source body
		myState.addTransporter(transporter);
		
		return transporter;
	}
	protected SourceMortelle createSourceMortelle(int x, int y, int w, int h){
		
		// Create a source mortelle object
		SourceMortelle source = new SourceMortelle(x,y,w,h);
		
		// Add it to the list of sprites of this level
		sprites.add(source);
		
		// Create the source body
		myState.addSource(source);
		
		return source;
	}
	protected BoutonPressoir createBoutonPressoir(int x, int y, int w, int h, Body b){
		
		// Create a boutonpressoir object
		BoutonPressoir bouton = new BoutonPressoir(x,y,w,h,b);
		
		// Add it to the list of sprites of this level
		sprites.add(bouton);
		
		// Create the source body
		myState.addBoutonPressoir(bouton);
		
		return bouton;
	}
	protected Levier createLevier(int x, int y, int w, int h, Body b){
		
		// Create a levier object
		Levier levier = new Levier(x,y,w,h,b);
		
		// Add it to the list of sprites of this level
		sprites.add(levier);
		
		// Create the source body
		myState.addLevier(levier);
		
		return levier;
	}
	
	protected Bonus createBonus(int x, int y, int w, int h){
		
		// Create a bonus object
		Bonus bonus = new Bonus(x,y,w,h);
		
		// Add it to the list of sprites of this level
		sprites.add(bonus);
		
		// Create the source body
		myState.addBonus(bonus);
		
		return bonus;
	}

	protected Character addCharacter(int x, int y, float ratio){
		// Create a new character and add it to the panel
		Character ch = new Character(x,y,ratio);
		
		// Create the body definition
		myState.addCharacter(ch,null);

		return ch;
	}
	protected Character addCharacterWithPoints(int x, int y, float ratio){
		// Create a new character and add it to the panel
		Character ch = new Character(x,y,ratio);
		
		// Create the body definition
		ArrayList<Vec2> tempArray = new ArrayList<Vec2>();
		Vec2 v = new Vec2(-ch.getCharBodyWidth()/2,ch.getCharBodyHeight()/2);	
		tempArray.add(v);
		v = new Vec2(-ch.getCharBodyWidth()*GameplayState.LOW_BODY_CHARACTER_RATIO,-ch.getCharBodyHeight()/2);
		tempArray.add(v);
		v = new Vec2(ch.getCharBodyWidth()*GameplayState.LOW_BODY_CHARACTER_RATIO,-ch.getCharBodyHeight()/2);
		tempArray.add(v);
		v = new Vec2(ch.getCharBodyWidth()/2,ch.getCharBodyHeight()/2);
		tempArray.add(v);
		
		
		myState.addCharacter(ch,tempArray);

		return ch;
	}

	public Character getFirstCharacter(){
		return this.character1;
	}
	public Character getSecondCharacter(){
		return this.character2;
	}
	public int getLevelID(){
		return this.levelID;
	}
	public ArrayList<Exit> getExit(){
		return this.listeExit;
	}
	public void removeSpriteFromList(Sprite s){
		this.sprites.remove(s);
	}
	
	public void render(GameContainer gc, Graphics g){
		backgroundImage.draw(0,0,Global.GAMEPLAYWIDTH,Global.GAMEPLAYHEIGHT);
		for(int i = 0 ; i < listeExit.size() ; ++i){
			listeExit.get(i).draw(g);
			}
		character1.draw(g);
		character2.draw(g);
		for(int i = 0 ; i < sprites.size() ; ++i){
			Sprite s = sprites.get(i);
			if(!s.getClass().equals(Obstacle.class) || !((Obstacle)s).isHidden()){
				s.draw(g);
			}
		}
		for(InGameIndication indication: indications){
			indication.render(gc, g);
		}
	}
}