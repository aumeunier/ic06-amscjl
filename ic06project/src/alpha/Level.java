package alpha;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

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
	
	public Level(GameplayState state, LevelSave model){
		this.myState = state;
		this.sprites = new ArrayList<Sprite>();
		this.levelModel = model;
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
	
	protected Source createSource(int x, int y, int w, int h){
		
		// Create a wall object
		Source source = new Source(x,y,w,h);
		
		// Add it to the list of sprites of this level
		sprites.add(source);
		
		// Create the source body
		myState.addSource(source);
		
		return source;
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
	
	public void render(){
		character1.draw();
		character2.draw();
		for(int i = 0 ; i < sprites.size() ; ++i){
			sprites.get(i).draw();
		}
	}
}
