package gold;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.MassData;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.collision.PolygonShape;
import org.jbox2d.collision.Shape;
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
	public final static float LOW_BODY_CHARACTER_RATIO = 1.0f/8.0f;
	final static int SPEED_X = 20;
	final static int SPEED_JUMP = 400;
	public static final String GROUND_SENSOR_NAME = "groundsensor";
	private int stateID;
	private int selection;
	private Level currentLevel;
	private LevelState currentLevelState;
	private World world;
	private Body ch1_body;
	private Body ch2_body;
	private Body exit;
	private ArrayList<Body> spriteBodies;
	private ArrayList<Body> monsterBodies;
	private UIGameplay uiGameplay;
	private UIDeath uiDeath;
	private UIPause uiPause;
	private UILevelCompleted uiWin;
	private boolean isPaused;
	private boolean isFinished;
	private boolean startAgain;
	private boolean alwaysStartAgain;
	private boolean levelJustCreated;
	private boolean stop_play_music;
	private boolean music_changed;

	public GameplayState(int id){
		super();
		this.stateID = id;
		this.isPaused = false;
		this.startAgain = false;
		this.alwaysStartAgain = false;
		this.isFinished = false;
		this.levelJustCreated = true;
		this.stop_play_music = false;
		this.currentLevelState = new LevelState();
	}

	public void ChooseLevel(int levelIndex){
		LevelSave save = Save.getInstance().levelSaveForLevelID(levelIndex);
		this.currentLevelState = new LevelState();
		this.uiGameplay.setLevelInformation(save.getName(), 0, save.getUnlockableKeys(), levelIndex);
		this.cleanAllBodies();
		this.isFinished = false;
		this.uiGameplay.onEnter();
		switch(levelIndex){
		case 1:
			this.currentLevel = new Level1(this,save);
			break;
			
		case 2:
			this.currentLevel = new Level2(this,save);
			break;
			
		case 3:
			this.currentLevel = new Level3(this,save);
			break;
			
		case 4:
			this.currentLevel = new Level4(this,save);
			break;
			

		default:
			this.currentLevel = null;
			break;
		}
		levelJustCreated = true;
	}

	@Override
	public int getID() {
		return stateID;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		spriteBodies = new ArrayList<Body>();
		monsterBodies = new ArrayList<Body>();
		this.uiGameplay = new UIGameplay(gc);
		this.uiPause = new UIPause(gc);
		this.uiDeath = new UIDeath(gc);
		this.uiWin = new UILevelCompleted(gc);
		this.createWorld();
	}

	@Override
	public void enter(GameContainer container, StateBasedGame game) {
		Input i = container.getInput();
		i.clearKeyPressedRecord();
		i.consumeEvent();
		this.selection = -1;
		this.isPaused = false;
		this.startAgain = false;
		this.alwaysStartAgain = false;
		this.uiGameplay.onEnter();
		if(ch1_body!=null){
			this.ch1_body.setLinearVelocity(new Vec2(0,0));
		}
		if(ch2_body!=null){
			this.ch2_body.setLinearVelocity(new Vec2(0,0));
		}
	}

	@Override
	public void leave(GameContainer container, StateBasedGame game) {
		Input i = container.getInput();
		i.clearKeyPressedRecord();
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {
		
		// Si un changement d'etat a ete demande, l'effectuer
		if(this.selection != -1){
			if(selection == Game.NARRATIVE_STATE){
				((NarrativeState)(sbg.getState(selection))).ChooseLevel(this.currentLevel.getLevelID());				
			}
			sbg.enterState(selection);	
		}
		
		// Change la musique quand le niveau change 
		if(this.music_changed){
			((Game)sbg).changeGameplayMusic();
			this.music_changed = false;
		}
		if(this.stop_play_music){
			Game game = (Game)sbg;
			if(game.isMusicPlaying()){
				game.stopMusic();
			}
			else{
				game.playMusic();
			}
			this.stop_play_music = false;
		}
		
		// Si aucun niveau n'est charge ou que le niveau est fini, aucun interet a faire des calculs
		if(this.currentLevel == null){
			return;
		}
		else if(this.levelJustCreated){
			this.levelJustCreated = false;
			this.currentLevel.setDisplay(gc);
		}
		
		if(this.isFinished){
			LevelSave lvlSave = Save.getInstance().levelSaveForLevelID(this.currentLevel.getLevelID());
			lvlSave.setSavedLevelData(currentLevel.nbBonus, true, true);
			LevelSave nextLvlSave = Save.getInstance().levelSaveForLevelID(this.currentLevel.getLevelID()+1);
			nextLvlSave.setSavedLevelData(0, true, false);
			Save.getInstance().save();
			return;			
		}
		
		// Si les joueurs etaient morts et veulent recommencer le niveau
		if(this.arePlayersDead() && (this.startAgain || this.alwaysStartAgain)){
			this.ChooseLevel(this.currentLevel.getLevelID());
			this.startAgain = false;
			return;
		}
		
		Character char1 = this.currentLevel.getFirstCharacter();
		Character char2 = this.currentLevel.getSecondCharacter();
		Input input = gc.getInput();
		
		// Si un des personnages est mort ou que le menu a ete demande, ne pas faire tourner les calculs
		if(this.arePlayersDead() || this.isPaused){	
			input.consumeEvent();
			return;
		}
		
		
		
		/*else if(s1 instanceof Exit && s2 instanceof Character){*/
		if (!((Character)this.ch1_body.getUserData()).rectCollideWithOther((Sprite)(exit.getUserData()))){
			((Character)this.ch1_body.getUserData()).setAtExit(false);
		}
		if (!((Character)this.ch2_body.getUserData()).rectCollideWithOther((Sprite)(exit.getUserData()))){
			((Character)this.ch2_body.getUserData()).setAtExit(false);
		}
	/*}
	else if(s1 instanceof Character && s2 instanceof Exit){
		if (!s1.rectCollideWithOther(s2)){
			((Character)s1).setAtExit(false);
		}
	}*/
		
		if(((Character)this.ch1_body.getUserData()).isAtExit() && ((Character)this.ch2_body.getUserData()).isAtExit()){
			this.isFinished = true;
			return;
		}
		
		if(((Character)this.ch1_body.getUserData()).isTransported() ){
			Vec2 b2dcoord = Global.getBox2DCoordinates(((Character)ch1_body.getUserData()).X_transported, ((Character)ch1_body.getUserData()).Y_transported);
			Vec2 position = new Vec2(b2dcoord.x+((Character)ch1_body.getUserData()).w/2, b2dcoord.y-((Character)ch1_body.getUserData()).h/2);
			ch1_body.setXForm(position,0);
			((Character)ch1_body.getUserData()).setTransported(false,0,0);
			return;
		}
		
		if(((Character)this.ch2_body.getUserData()).isTransported() ){
			Vec2 b2dcoord = Global.getBox2DCoordinates(((Character)ch2_body.getUserData()).X_transported, ((Character)ch2_body.getUserData()).Y_transported);
			Vec2 position = new Vec2(b2dcoord.x+((Character)ch2_body.getUserData()).w/2, b2dcoord.y-((Character)ch2_body.getUserData()).h/2);
			ch2_body.setXForm(position,0);
			((Character)ch2_body.getUserData()).setTransported(false,0,0);
			return;
		}
		
		// Sinon effectuer les traitements d'inputs et l'update du world / des sprites
		boolean char1CanJump = (char1.isFlying() || !char1.isFalling) && !char1.isSlipping;
		boolean char2CanJump = (char2.isFlying() || !char2.isFalling) && !char2.isSlipping;
		boolean char1CanMove = (char1CanJump || !char1.isColliding) && !char1.isSlipping;
		boolean char2CanMove = (char2CanJump || !char2.isColliding) && !char2.isSlipping;
		

		if(char1CanJump){
			if(char1.isFlying()){
				if(input.isKeyDown(Input.KEY_Z)){
					ch1_body.m_linearVelocity.y = +SPEED_X;
					char1.isFalling = true;			
				}
			}
			else if(input.isKeyPressed(Input.KEY_Z)){
				ch1_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch1_body.getWorldCenter());	
				char1.isFalling = true;
			}
			else if(char1.shouldJumpAfterRebound()){
				ch1_body.applyImpulse(new Vec2(0, SPEED_JUMP/3), ch1_body.getWorldCenter());	
				char1.setJumpAfterRebound(false);
			}
		}
		else if(!char1.isSlipping && char1.isRebond()){
			if(input.isKeyDown(Input.KEY_Z)){
				char1.setJumpAfterRebound(true);		
			}
		}
		if(((input.isKeyDown(Input.KEY_Q)) && char1CanMove)||(char1.isGoingLeft&&char1.isSlipping)){
			ch1_body.m_linearVelocity.x = -SPEED_X;			
			char1.goLeft();
		}
		else if(((input.isKeyDown(Input.KEY_D)) && char1CanMove)||(char1.isGoingRight&&char1.isSlipping)){
			ch1_body.m_linearVelocity.x = SPEED_X;			
			char1.goRight();
		}
		else if((input.isKeyPressed(Input.KEY_A)) && char1.canTeleport()){
			char2.setTransported(true, char1.x, char1.y);
		}
		else if(!char1.isFalling){
			ch1_body.m_linearVelocity.x = 0;			
			char1.straight();
		}

		if(char2CanJump){
			if(char2.isFlying()){
				if(input.isKeyDown(Input.KEY_UP)){
					ch2_body.m_linearVelocity.y = +SPEED_X;
					char2.isFalling = true;			
				}
			}
			else if(input.isKeyPressed(Input.KEY_UP)){
				ch2_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch2_body.getWorldCenter());	
				char2.isFalling = true;
			}
			else if(char2.shouldJumpAfterRebound()){
				ch2_body.applyImpulse(new Vec2(0, SPEED_JUMP/3), ch2_body.getWorldCenter());	
				char2.setJumpAfterRebound(false);
			}
		}
		else if(!char2.isSlipping && char2.isRebond()){
			if(input.isKeyDown(Input.KEY_Z)){
				char2.setJumpAfterRebound(true);		
			}
		}
		if(((input.isKeyDown(Input.KEY_LEFT)) && char2CanMove)||(char2.isGoingLeft&&char2.isSlipping)){
			ch2_body.m_linearVelocity.x = -SPEED_X;			
			char2.goLeft();
		}
		else if(((input.isKeyDown(Input.KEY_RIGHT)) && char2CanMove)||(char2.isGoingRight&&char2.isSlipping)){
			ch2_body.m_linearVelocity.x = SPEED_X;			
			char2.goRight();
		}
		else if((input.isKeyPressed(Input.KEY_ENTER)) && char2.canTeleport()){
			char1.setTransported(true, char2.x, char2.y);
		}
		else if(!char2.isFalling){
			ch2_body.m_linearVelocity.x = 0;			
			char2.straight();
		}
		
		if((char1.isPetit())&&(char1.shouldChangeSize)){
			modifyBodySize(getBodyForUserData(char1),(float)0.5,(float)0.5);
			char1.shouldChangeSize=false;
		}
		if((char2.isPetit())&&(char2.shouldChangeSize)){
			modifyBodySize(getBodyForUserData(char2),(float)0.5,(float)0.5);
			char2.shouldChangeSize=false;
		}
		if((!char1.isPetit())&&(char1.shouldChangeSize)){
			modifyBodySize(getBodyForUserData(char1),(float)2.0,(float)2.0);
			char1.shouldChangeSize=false;
		}
		if((!char2.isPetit())&&(char2.shouldChangeSize)){
			modifyBodySize(getBodyForUserData(char2),(float)2.0,(float)2.0);
			char2.shouldChangeSize=false;
		}
		if((char1.isRebond())&&(char1.shouldRebond)){
			modifyBodyRebond(getBodyForUserData(char1),(float)0.9);
			char1.shouldRebond=false;
		}
		if((char2.isRebond())&&(char2.shouldRebond)){
			modifyBodyRebond(getBodyForUserData(char2),(float)0.9);
			char2.shouldRebond=false;
		}
		if((char1.isNage())&& char1.shouldNage){
			modifyBodyRebond(getBodyForUserData(char1),(float)0.0);
			char1.shouldNage=false;
		}
		if((char2.isNage())&& char2.shouldNage){
			modifyBodyRebond(getBodyForUserData(char2),(float)0.0);
			char2.shouldNage=false;
		}
		
		
		char1.setCoordinatesFromBody(ch1_body);
		char2.setCoordinatesFromBody(ch2_body);	
		
		// Update the monster's speed
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for(int i = 0 ; i < monsterBodies.size() ; ++i){
			Body b = monsterBodies.get(i);
			Monster m = (Monster)b.getUserData();
			b.setLinearVelocity(m.getSpeed());	
			((Monster)b.getUserData()).step();
			m.setCoordinatesFromBody(b);
			if (m.getShouldBeDestroy()){
				currentLevel.sprites.remove(m);
				tempList.add(i);
			}
		}
		for(int i = 0 ; i < tempList.size() ; ++i){
			Body tempBody = monsterBodies.get((tempList.get(i))-i);
			this.monsterBodies.remove(tempBody);
			this.world.destroyBody(tempBody);
			currentLevel.nbBonus++; // Every monster killed gives a fruit
			this.currentLevelState.setNbKeysUnlocked(currentLevel.nbBonus);
		}
		tempList.clear();
		
		// Simulation sur 0.1s dans notre box2D world
		world.step((float)delta/100, 100);

		// Update the screen and the model values
		for(int i = 0 ; i < spriteBodies.size() ; ++i){
			Body tempBody = spriteBodies.get(i);
			Sprite theSprite = (Sprite)tempBody.getUserData();
			if (theSprite.getShouldBeDestroy()){
				if(theSprite instanceof Bonus && ((Bonus)theSprite).isObtained()){
					currentLevel.nbBonus++;
					this.currentLevelState.setNbKeysUnlocked(currentLevel.nbBonus);
				}
				currentLevel.sprites.remove(theSprite);
				tempList.add(i);
			}
			else {
				if(!(theSprite instanceof Levier)&&!(theSprite instanceof LevierCombi))
					theSprite.setCoordinatesFromBody(tempBody);
			}
		}	
		for(int i = 0 ; i < tempList.size() ; ++i){
			Body tempBody = spriteBodies.get((tempList.get(i))-i);
			this.spriteBodies.remove(tempBody);
			this.world.destroyBody(tempBody);
		}		
		this.currentLevelState.setPlayer1Power(((Character)ch1_body.getUserData()).getPower());
		this.currentLevelState.setPlayer2Power(((Character)ch2_body.getUserData()).getPower());
		this.uiGameplay.setTempLevelInformation(currentLevelState.getPlayer1Power(),
				currentLevelState.getPlayer2Power(), 
				currentLevel.nbBonus);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {		
		uiGameplay.render(gc, g);
		if(currentLevel !=null){
			currentLevel.render(gc, sbg, g);
			
			// Si un des personnages est mort, afficher le menu de mort
			if(this.currentLevel.getFirstCharacter().isDead() || this.currentLevel.getSecondCharacter().isDead()){	
				this.uiDeath.render(gc, g);
			}
		}

		// Si le niveau est complete, afficher un ecran de victoire
		if(this.isFinished){
			this.uiWin.render(gc, g);
		}
		// Si le menu de pause a ete demande, l'afficher
		else if(this.isPaused){	
			this.uiPause.render(gc, g);
		}
	}

	private void createWorld(){
		Vec2 v = new Vec2(0.0f,-10.0f);
		AABB aabb = new AABB(new Vec2(-10.0f,-10.0f), new Vec2((float)Global.GAMEPLAYWIDTH+10,(float)Global.GAMEPLAYHEIGHT+10));
		world = new World(aabb,v,false);
		world.setContactListener(new MyContactListener());
		world.setContactFilter(new MyContactFilter());
	}
	
	private void cleanAllBodies(){
		for(Body b:spriteBodies){
			world.destroyBody(b);
		}
		spriteBodies.clear();
		for(Body b:monsterBodies){
			world.destroyBody(b);
		}
		monsterBodies.clear();
		if(null!=ch1_body){
			world.destroyBody(ch1_body);
			ch1_body=null;
		}
		if(null!=ch2_body){
			world.destroyBody(ch2_body);
			ch2_body=null;
		}
	}

	public Body addWall(Wall wallData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = wallData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(wallData.x, wallData.y);
		bodyDef.position = new Vec2(b2dcoord.x+wallData.w/2,b2dcoord.y-wallData.h/2);
		Body newBody = world.createBody(bodyDef);
		
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 0.5f;
		sd.setAsBox(wallData.w/2,wallData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addGround(Ground groundData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = groundData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(groundData.x, groundData.y);
		bodyDef.position = new Vec2(b2dcoord.x+groundData.w/2,b2dcoord.y-groundData.h/2);
		Body newBody = world.createBody(bodyDef);
		
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 0.5f;
		sd.setAsBox(groundData.w/2,groundData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addGroundWithPoints(Ground groundData, ArrayList<Vec2> list){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = groundData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(groundData.x, groundData.y);
		bodyDef.position = new Vec2(b2dcoord.x+groundData.w/2,b2dcoord.y-groundData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 0.5f;

		for(Vec2 v: list){
			sd.addVertex(v);			
		}
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addWallWithPoints(Wall wallData, ArrayList<Vec2> list){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = wallData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(wallData.x, wallData.y);
		bodyDef.position = new Vec2(b2dcoord.x+wallData.w/2,b2dcoord.y-wallData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 0.5f;

		for(Vec2 v: list){
			sd.addVertex(v);			
		}
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}

	public Body addObstacle(Obstacle obstacleData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = obstacleData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(obstacleData.x, obstacleData.y);
		bodyDef.position = new Vec2(b2dcoord.x+obstacleData.w/2,b2dcoord.y-obstacleData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 0.5f;

		sd.setAsBox(obstacleData.w/2,obstacleData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addDestructible(Destructible destructibleData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = destructibleData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(destructibleData.x, destructibleData.y);
		bodyDef.position = new Vec2(b2dcoord.x+destructibleData.w/2,b2dcoord.y-destructibleData.h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 0.5f;

		sd.setAsBox(destructibleData.w/2,destructibleData.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addObstacleWithPoints(Obstacle obstacleData, ArrayList<Vec2> list){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = obstacleData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(obstacleData.x, obstacleData.y);
		bodyDef.position = new Vec2(b2dcoord.x+obstacleData.w/2,b2dcoord.y-obstacleData.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 0.5f;

		for(Vec2 v: list){
			sd.addVertex(v);			
		}
		//sd.setAsBox(obstacleData.w/2,obstacleData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addExit(Exit exitData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = exitData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(exitData.x, exitData.y);
		bodyDef.position = new Vec2(b2dcoord.x+exitData.w/2,b2dcoord.y-exitData.h/2);
		Body newBody = world.createBody(bodyDef);
		
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 1.0f;
		sd.setAsBox(exitData.w/2,exitData.h/2);
		sd.isSensor=true;
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		exit=newBody;
		newBody.putToSleep();
		return newBody;
	}
	
	public Body addBonus(Bonus bonusData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = bonusData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(bonusData.x, bonusData.y);
		bodyDef.position = new Vec2(b2dcoord.x+bonusData.w/2,b2dcoord.y-bonusData.h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 1.0f;
		sd.setAsBox(bonusData.w/2,bonusData.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		return newBody;
	}

	public Body addSource(Source sourceData){
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
		sd.friction = 1.0f;

		sd.setAsBox(sourceData.w/2,sourceData.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);		
		return newBody;
	}
	public Body addTransporter(Transporter transporterData){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = transporterData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(transporterData.x, transporterData.y);
		bodyDef.position = new Vec2(b2dcoord.x+transporterData.w/2,b2dcoord.y-transporterData.h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 1.0f;

		sd.setAsBox(transporterData.w/2,transporterData.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);		
		return newBody;
	}
	
	public Body addBoutonPressoir(BoutonPressoir bouton){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = bouton;
		Vec2 b2dcoord = Global.getBox2DCoordinates(bouton.x, bouton.y);
		bodyDef.position = new Vec2(b2dcoord.x+bouton.w/2,b2dcoord.y-bouton.h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 100.0f;
		sd.friction = 1.0f;		 

		sd.setAsBox(bouton.w/2,bouton.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addLevier(Levier levier){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = levier;
		Vec2 b2dcoord = Global.getBox2DCoordinates(levier.x+2, levier.y-5);
		bodyDef.position = new Vec2(b2dcoord.x+(levier.w-4)/2,b2dcoord.y-(levier.h-5)/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 1.0f;

		sd.setAsBox((levier.w-4)/2,(levier.h-5)/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addLevier(LevierCombi levier){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = levier;
		Vec2 b2dcoord = Global.getBox2DCoordinates(levier.x+2, levier.y-5);
		bodyDef.position = new Vec2(b2dcoord.x+(levier.w-4)/2,b2dcoord.y-(levier.h-5)/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 1.0f;

		sd.setAsBox((levier.w-4)/2,(levier.h-5)/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		return newBody;
	}
	
	public Body addIndicationSprite(IndicationSprite indic){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = indic;
		Vec2 b2dcoord = Global.getBox2DCoordinates(indic.x, indic.y);
		bodyDef.position = new Vec2(b2dcoord.x+indic.w/2,b2dcoord.y-indic.h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 1.0f;

		sd.setAsBox(indic.w/2,indic.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		newBody.putToSleep();
		return newBody;
	}
	
	public Body addMonster(Monster monsterData, float coef){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = monsterData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(monsterData.x, monsterData.y);
		bodyDef.position = new Vec2(b2dcoord.x+monsterData.w/2,b2dcoord.y-monsterData.h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 1.0f;
		sd.setAsBox(monsterData.w/2*coef,monsterData.h/2*coef);
		newBody.createShape(sd);
		monsterBodies.add(newBody);
		return newBody;
	}

	public Body addCharacter(Character characterData, ArrayList<Vec2> list){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = characterData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(characterData.x, characterData.y);
		bodyDef.position = new Vec2(b2dcoord.x+characterData.w/2, b2dcoord.y-characterData.h/2);
		MassData md = new MassData();
		md.mass = 10.0f;
		bodyDef.massData = md;

		PolygonDef sd = new PolygonDef();
		sd.density = 5.0f;
		sd.friction = 0.1f;
		sd.restitution = 0.0f; 
		if(list!=null){
			for(Vec2 v: list){
				sd.addVertex(v);				
			}
		}
		else {
			sd.setAsBox(characterData.getCharBodyWidth()/2, characterData.getCharBodyHeight()/2);
		}
		
		// Ajoute un Sensor pour savoir si le character est au sol
		PolygonDef groundSensor = new PolygonDef();
		groundSensor.isSensor=true;
		groundSensor.userData=GROUND_SENSOR_NAME;
		if(list!=null){
			groundSensor.setAsBox((float)((characterData.getCharBodyWidth()*LOW_BODY_CHARACTER_RATIO)*1.0),
					2, new Vec2(0,-characterData.getCharBodyHeight()/2), 0);
		}
		else {
			groundSensor.setAsBox((float)((characterData.getCharBodyWidth()/2.0)*1.0), 2, 
					new Vec2(0,-characterData.getCharBodyHeight()/2), 0);
		}
		if(ch1_body == null){
			ch1_body = world.createBody(bodyDef);
			ch1_body.createShape(sd);
			ch1_body.createShape(groundSensor);
			ch1_body.wakeUp();
			return ch1_body;
		}
		else {
			ch2_body = world.createBody(bodyDef);
			ch2_body.createShape(sd);
			ch2_body.createShape(groundSensor);
			ch2_body.wakeUp();
			return ch2_body;
		}
	}
	public Body modifyBodyRebond(Body body, float R)
	{
		if(!(body.getUserData() instanceof Sprite)){
			return null;
		}
		
		Sprite userData = (Sprite)body.getUserData();
		//int newW = (int) (userData.w*w);
		//int newH = (int) (userData.h*h);
		//userData.x = userData.x - (newW - userData.w)/2;
		//userData.y = userData.y - (newH - userData.h); //nb : je ne pense pas qu'il faille diviser par 2(sinon grandit dans le sol)
		//userData.w = newW;
		//userData.h = newH;
		
		Vec2 b2dcoord = Global.getBox2DCoordinates(((Sprite)userData).x, ((Sprite)userData).y);
		Vec2 b2position = new Vec2(b2dcoord.x+((Sprite)userData).w/2, b2dcoord.y-((Sprite)userData).h/2);
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = userData;
		bodyDef.position = b2position;
		MassData md = new MassData();
		md.mass = body.getMass();
		bodyDef.massData = md;
		
		Body newBody = world.createBody(bodyDef);		
		Shape shape = body.getShapeList();
		do {
			PolygonDef sd = new PolygonDef();
			sd.density = shape.m_density;
			sd.friction = shape.getFriction();
			sd.restitution = R; 
			sd.userData = shape.getUserData();
			sd.isSensor = shape.isSensor();
			for(Vec2 v: ((PolygonShape)shape).getVertices()){
				Vec2 newV = new Vec2(v.x,v.y);
				sd.addVertex(newV);
			}		
			newBody.createShape(sd);			
			shape = shape.getNext();
		} while(shape!=null);
		
		if(ch1_body.equals(body)){
			ch1_body = newBody;
		}
		else if(ch2_body.equals(body)){
			ch2_body = newBody;
		}
		else {
			for(Body b: spriteBodies){
				if(b.equals(body)){
					b = newBody;
				}
			}
		}
		
		world.destroyBody(body);
		body = null;
		
		return newBody;
	}
	public Body modifyBodySize(Body body, float h, float w)//nb : rajout d'un parametre pour pouvoir grossir uniquement
	{
		if(!(body.getUserData() instanceof Sprite)){
			return null;
		}
		
		Sprite userData = (Sprite)body.getUserData();
		int newW = (int) (userData.w*w);
		int newH = (int) (userData.h*h);
		userData.x = userData.x - (newW - userData.w)/2;
		userData.y = userData.y - (newH - userData.h); //nb : je ne pense pas qu'il faille diviser par 2(sinon grandit dans le sol)
		userData.w = newW;
		userData.h = newH;
		
		Vec2 b2dcoord = Global.getBox2DCoordinates(((Sprite)userData).x, ((Sprite)userData).y);
		Vec2 b2position = new Vec2(b2dcoord.x+((Sprite)userData).w/2, b2dcoord.y-((Sprite)userData).h/2);
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = userData;
		bodyDef.position = b2position;
		MassData md = new MassData();
		md.mass = body.getMass();
		bodyDef.massData = md;
		
		Body newBody = world.createBody(bodyDef);		
		Shape shape = body.getShapeList();
			
		while(shape!=null){
			PolygonDef sd = new PolygonDef();
			sd.density = shape.m_density;
			sd.friction = shape.getFriction();
			//sd.restitution = shape.getRestitution(); 
			sd.userData = shape.getUserData();
			sd.isSensor = shape.isSensor();
			for(Vec2 v: ((PolygonShape)shape).getVertices()){
				Vec2 newV = new Vec2(v.x*w,v.y*h);
				sd.addVertex(newV);
			}	
			newBody.createShape(sd);			
			shape = shape.getNext();
		}
		
		if(ch1_body.equals(body)){
			ch1_body = newBody;
		}
		else if(ch2_body.equals(body)){
			ch2_body = newBody;
		}
		else {
			for(Body b: spriteBodies){
				if(b.equals(body)){
					b = newBody;
				}
			}
		}
		
		world.destroyBody(body);
		body = null;
		
		return newBody;
	}
	public Body getBodyForUserData(Object userData){
		if(userData == null){
			return null;
		}
		if(ch1_body!=null && ch1_body.getUserData().equals(userData)){
			return ch1_body;
		}
		else if(ch2_body!=null && ch2_body.getUserData().equals(userData)){
			return ch2_body;
		}
		else if (spriteBodies!=null){
			for(Body b: spriteBodies){
				if(b.getUserData().equals(userData)){
					return b;
				}
			}
		}		
		return null;
	}
	
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount){
		if(this.isFinished){
			Save.getInstance().levelSaveForLevelID(this.currentLevel.getLevelID()).
				setSavedLevelData(this.currentLevelState.getNbKeys(), true, true);
			Save.getInstance().save();
			selection = this.uiWin.mouseClicked(button, x, y, clickCount, this);
			if(selection == Game.GO_TO_NEXT_LEVEL){
				this.ChooseLevel(this.currentLevel.getLevelID()+1);
				this.music_changed = true;
				selection = Game.NARRATIVE_STATE;	
			}		
		}
		else if(this.isPaused){
			selection = this.uiPause.mouseClicked(button, x, y, clickCount, this);
			if(selection == Game.STOP_PLAY_MUSIC){
				this.stop_play_music = true;
				selection = -1;
			}		
		}
		else if(this.currentLevel!=null && this.arePlayersDead()){
			selection = this.uiDeath.mouseClicked(button, x, y, clickCount, this);
		}
		else if(y >= Global.GAMEPLAYHEIGHT){
			selection = this.uiGameplay.mouseClicked(button, x, y, clickCount, this);
		}
		if(selection == Game.SHOULD_RESTART && this.currentLevel != null){
			this.ChooseLevel(this.currentLevel.getLevelID());
			selection = -1;
		}
	}
	public void setPaused(boolean _paused){
		this.isPaused = _paused;
	}
	public boolean arePlayersDead(){
		return (this.currentLevel.getFirstCharacter().isDead() || this.currentLevel.getSecondCharacter().isDead());
	}
	public LevelSave getCurrentLevelModel(){
		if(this.currentLevel == null){
			return null;
		}
		return this.currentLevel.levelModel;
	}
}
