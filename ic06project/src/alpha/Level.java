package alpha;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
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
	protected Character character1;
	protected Character character2;
	protected LevelSave levelModel;
	protected Image backgroundImage;
	
	public Level(GameplayState state, LevelSave model){
		this.myState = state;
		this.sprites = new ArrayList<Sprite>();
		this.levelModel = model;
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
		
		// Add it to the list of sprites of the level
		sprites.add(wall);
		
		// Create the wall body
		myState.addWall(wall);
		
		return wall;
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

	protected Source createSource(int x, int y, int w, int h, Power _power){
		
		// Create a source object
		Source source = new Source(x,y,w,h,_power);
		
		// Add it to the list of sprites of this level
		sprites.add(source);
		
		// Create the source body
		myState.addSource(source);
		
		return source;
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
	
	protected Character addCharacter(int x, int y){
		// Create a new character and add it to the panel
		Character ch = new Character();
		ch.x = x;
		ch.y = y;
		
		// Create the body definition
		myState.addCharacter(ch);

		return ch;
	}

	public Character getFirstCharacter(){
		return this.character1;
	}
	public Character getSecondCharacter(){
		return this.character2;
	}
	
	public void render(Graphics g){
		backgroundImage.draw(0,0,Global.GAMEPLAYWIDTH,Global.GAMEPLAYHEIGHT);
		character1.draw(g);
		character2.draw(g);
		for(int i = 0 ; i < sprites.size() ; ++i){
			Sprite s = sprites.get(i);
			if(!s.getClass().equals(Obstacle.class) || !((Obstacle)s).isHidden()){
				sprites.get(i).draw(g);
			}
		}
	}
}
