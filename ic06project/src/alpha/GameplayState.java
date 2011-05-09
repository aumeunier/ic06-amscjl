package alpha;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.MassData;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameplayState extends BasicGameState implements MouseListener{
	final static int SPEED_X = 20;
	final static int SPEED_JUMP = 400;
	final static int MENU_X = Global.WINDOW_WIDTH/2-40;
	final static int MENU_Y = Global.WINDOW_HEIGHT-20;
	final static int MENU_W = 85;
	final static int MENU_H = 15;
	private int stateID;
	private int selection;
	private Level currentLevel;
	private World world;
	private Body ch1_body;
	private Body ch2_body;
	private ArrayList<Body> spriteBodies;
	protected GameplayUI ui;

	public GameplayState(int id){
		super();
		this.stateID = id;
		this.ui = new GameplayUI();
	}

	public void ChooseLevel(int levelIndex){
		LevelSave save = Save.getInstance().levelSaveForLevelID(levelIndex);
		this.ui.setLevelInformation(save.getName(), save.getUnlockableKeys(), save.getUnlockedKeys());
		this.cleanAllBodies();
		switch(levelIndex){
		case 1:
			this.currentLevel = new Level1(this,save);
			break;

		default:
			this.currentLevel = null;
			break;
		}
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		spriteBodies = new ArrayList<Body>();
		this.createWorld();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Input i = container.getInput();
		i.clearKeyPressedRecord();
		this.selection = -1;
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		Input i = container.getInput();
		i.clearKeyPressedRecord();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		if(this.selection != -1){
			sbg.enterState(selection);			
		}
		if(this.currentLevel == null){
			return;
		}
		Input input = gc.getInput();
		Character char1 = this.currentLevel.getFirstCharacter();
		Character char2 = this.currentLevel.getSecondCharacter();
		boolean char1CanMove = char1.isFlying() || !char1.isFalling;
		boolean char2CanMove = char2.isFlying() || !char2.isFalling;

		if((input.isKeyPressed(Input.KEY_Z)) && char1CanMove){
			ch1_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch1_body.getWorldCenter());	
			char1.isFalling = true;
		}
		else if((input.isKeyDown(Input.KEY_Q)) && char1CanMove){
			ch1_body.m_linearVelocity.x = -SPEED_X;			
			char1.isGoingRight = false;
			char1.isGoingLeft = true;
		}
		else if((input.isKeyDown(Input.KEY_D)) && char1CanMove){
			ch1_body.m_linearVelocity.x = SPEED_X;			
			char1.isGoingRight = true;
			char1.isGoingLeft = false;
		}
		else if(!char1.isFalling){
			ch1_body.m_linearVelocity.x = 0;			
			char1.isGoingRight = false;
			char1.isGoingLeft = false;			
		}
		char1.setCoordinatesFromBody(ch1_body);

		if((input.isKeyPressed(Input.KEY_UP)) && char2CanMove){
			ch2_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch2_body.getWorldCenter());	
			char2.isFalling = true;
		}
		else if((input.isKeyDown(Input.KEY_LEFT)) && char2CanMove){
			ch2_body.m_linearVelocity.x = -SPEED_X;			
			char2.isGoingRight = false;
			char2.isGoingLeft = true;
		}
		else if((input.isKeyDown(Input.KEY_RIGHT)) && char1CanMove){
			ch2_body.m_linearVelocity.x = SPEED_X;			
			char2.isGoingRight = true;
			char2.isGoingLeft = false;
		}
		else if(!char2.isFalling){
			ch2_body.m_linearVelocity.x = 0;			
			char2.isGoingRight = false;
			char2.isGoingLeft = false;			
		}
		char2.setCoordinatesFromBody(ch2_body);	

		// Simulation sur 0.1s dans notre box2D world
		world.step((float)delta/100, 100);

		// Update the screen and the model values
		for(int i = 0 ; i < spriteBodies.size() ; ++i){
			Body tempBody = spriteBodies.get(i);
			Sprite theSprite = (Sprite)tempBody.getUserData();
			theSprite.setCoordinatesFromBody(tempBody);
		}		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {
		ui.render(g);
		if(currentLevel !=null){
			currentLevel.render();
		}
	}

	private void createWorld(){
		Vec2 v = new Vec2(0.0f,-10.0f);
		AABB aabb = new AABB(new Vec2(0.0f,0.0f), new Vec2((float)Global.GAMEPLAYWIDTH,(float)Global.GAMEPLAYHEIGHT));
		world = new World(aabb,v,false);
		world.setContactListener(new MyContactListener());
		world.setContactFilter(new MyContactFilter());
	}
	
	private void cleanAllBodies(){
		for(Body b:spriteBodies){
			world.destroyBody(b);
		}
		spriteBodies.clear();
	}

	public void addWall(Wall wallData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = wallData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(wallData.x, wallData.y);
		bodyDef.position = new Vec2(b2dcoord.x+wallData.w/2,b2dcoord.y-wallData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 5.0f;

		sd.setAsBox(wallData.w/2,wallData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
	}

	public void addObstacle(Obstacle obstacleData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = obstacleData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(obstacleData.x, obstacleData.y);
		bodyDef.position = new Vec2(b2dcoord.x+obstacleData.w/2,b2dcoord.y-obstacleData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 5.0f;

		sd.setAsBox(obstacleData.w/2,obstacleData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
	}
	public void addObstacleWithPoints(Obstacle obstacleData, ArrayList<Vec2> list){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = obstacleData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(obstacleData.x, obstacleData.y);
		bodyDef.position = new Vec2(b2dcoord.x+obstacleData.w/2,b2dcoord.y-obstacleData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 5.0f;

		for(Vec2 v: list){
			sd.addVertex(v);			
		}
		//sd.setAsBox(obstacleData.w/2,obstacleData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
	}

	public void addSource(Source sourceData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = sourceData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(sourceData.x, sourceData.y);
		bodyDef.position = new Vec2(b2dcoord.x+sourceData.w/2,b2dcoord.y-sourceData.h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 5.0f;

		sd.setAsBox(sourceData.w/2,sourceData.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
	}

	public void addCharacter(Character characterData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = characterData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(characterData.x, characterData.y);
		bodyDef.position = new Vec2(b2dcoord.x+characterData.w/2, b2dcoord.y-characterData.h/2);
		MassData md = new MassData();
		md.mass = 10.0f;
		bodyDef.massData = md;

		PolygonDef sd = new PolygonDef();
		sd.density = 5.0f;
		sd.friction = 0.0f;
		sd.setAsBox(25, 25);

		// Ajoute un Sensor pour savoir si le character est au sol
		PolygonDef groundSensor = new PolygonDef();
		groundSensor.isSensor=true;
		groundSensor.userData="groundsensor";
		groundSensor.setAsBox((float)(characterData.w/2.0*0.9), 2, new Vec2(0,-characterData.h/2), 0);
		if(ch1_body == null){
			ch1_body = world.createBody(bodyDef);
			ch1_body.createShape(sd);
			ch1_body.createShape(groundSensor);
			ch1_body.wakeUp();
		}
		else {
			ch2_body = world.createBody(bodyDef);
			ch2_body.createShape(sd);
			ch2_body.createShape(groundSensor);
			ch2_body.wakeUp();
		}
	}


	public void mouseMoved(int oldx, int oldy, int newX, int newY){
		// do nothing
	}
 
	public void mouseClicked(int button, int x, int y, int clickCount){
		if((x >= MENU_X && x <= (MENU_X + MENU_W)) 
				&&	(y >= MENU_Y && y <= (MENU_Y + MENU_H))){
			selection = Game.MAINMENU_STATE;
		}
	}
}
