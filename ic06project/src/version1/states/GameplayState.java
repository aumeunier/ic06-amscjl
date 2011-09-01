package version1.states;

import java.util.ArrayList;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.CircleDef;
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

import version1.Game;
import version1.Global;
import version1.data.LevelSave;
import version1.data.LevelState;
import version1.data.Save;
import version1.data.levels.Level;
import version1.data.levels.Level1;
import version1.data.levels.Level2;
import version1.data.levels.Level3;
import version1.data.levels.Level4;
import version1.data.levels.Level5;
import version1.engine.MyContactFilter;
import version1.engine.MyContactListener;
import version1.sprites.Power;
import version1.sprites.Sprite;
import version1.sprites.alive.Creature;
import version1.sprites.alive.monsters.Monster;
import version1.sprites.alive.monsters.Witch;
import version1.sprites.decor.IndicationSprite;
import version1.sprites.decor.Obstacle;
import version1.sprites.interaction.Bonus;
import version1.sprites.interaction.Destructible;
import version1.sprites.interaction.Exit;
import version1.sprites.interaction.MissilePlatform;
import version1.sprites.interaction.Transporter;
import version1.sprites.interaction.projectiles.FireBall;
import version1.sprites.interaction.projectiles.PlatformMissile;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.trigger.Lever;
import version1.sprites.interaction.trigger.LeverCombination;
import version1.sprites.interaction.trigger.buttons.PushButton;
import version1.sprites.interaction.trigger.buttons.PushButtonMove;
import version1.sprites.interaction.trigger.buttons.PushButtonShoot;
import version1.ui.UIDeath;
import version1.ui.UIGameplay;
import version1.ui.UILevelCompleted;
import version1.ui.UIPause;


/**
 * The GameplayState is the core of the application. It controls the game part. 
 * This is the engine of the game: main loop, drawing, mouse listener, keyboard listener, etc.
 * It controls specifically two things: the Box2D world (all the bodies) and the <code>Level</code> being played.
 * The level controls all the <code>Sprite</code>, such that our bodies and sprites are not owned by a single class
 * and are not mixed up.
 * Additionnaly, most of the changes (UI) need to be done in the main loop, that is why we have to check most of the
 * elements in the game if they need to be changed.
 *
 */
public class GameplayState extends BasicGameState implements MouseListener{
	public final static float LOW_BODY_CHARACTER_RATIO = 1.0f/8.0f;
	private final static int SPEED_X = 20;
	private final static int SPEED_JUMP = 400;
	public static final String GROUND_SENSOR_NAME = "groundsensor";
	private int stateID;
	private int selection;
	/** The level currently being player */
	private Level currentLevel;
	/** The current state of the level (player's powers and bonus gathered) */
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
	/** Whether we should restart the level */
	private boolean startAgain;
	/** Whether the player wants to automatically restart the level when he dies - not possible to change right now */
	private boolean alwaysStartAgain;//TODO: not implemented
	private boolean stop_play_music;
	private boolean music_changed;
	private boolean endOfGame = false;

	
	/////////////////////////////////////////////////////////
	/////////////////////// LIFECYCLE ///////////////////////
	/////////////////////////////////////////////////////////
	public GameplayState(int id){
		super();
		this.stateID = id;
		this.isPaused = false;
		this.startAgain = false;
		this.alwaysStartAgain = false;
		this.isFinished = false;
		this.stop_play_music = false;
		this.currentLevelState = new LevelState();
	}
	@Override
	/** Initialize the UIs and several variables */
	public void init(GameContainer gc, StateBasedGame sbg)
	throws SlickException {
		this.spriteBodies = new ArrayList<Body>();
		this.monsterBodies = new ArrayList<Body>();
		this.uiGameplay = new UIGameplay(gc);
		this.uiPause = new UIPause(gc);
		this.uiDeath = new UIDeath(gc);
		this.uiWin = new UILevelCompleted(gc);
		this.createWorld();
	}
	@Override
	/** Clear various inputs when going back to the game */
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

	
	/////////////////////////////////////////////////////////
	//////////////////// GETTERS/SETTERS ////////////////////
	/////////////////////////////////////////////////////////
	@Override
	public int getID() {
		return stateID;
	}
	public LevelSave getCurrentLevelModel(){
		if(this.currentLevel == null){
			return null;
		}
		return this.currentLevel.getLevelModel();
	}
	public void setPaused(boolean _paused){
		this.isPaused = _paused;
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////// HELPERS ////////////////////////
	/////////////////////////////////////////////////////////
	/** Load the level and prepare the game before it actually astarts */
	public void ChooseLevel(int levelIndex){
		LevelSave save = Save.getInstance().levelSaveForLevelID(levelIndex);
		this.currentLevelState = new LevelState();
		this.cleanAllBodies();
		this.isFinished = false;
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

		case 5:
			this.currentLevel = new Level5(this, save);
			break;
		default:
			this.currentLevel = null;
			break;
		}
		if(this.currentLevel !=null){
			this.uiGameplay.setLevelInformation(save.getName(), 0, save.getUnlockableKeys(), levelIndex);
			this.uiGameplay.onEnter();
		}
	}
	/** @return whether at least one player is dead */
	public boolean arePlayersDead(){
		return (this.currentLevel.getFirstCharacter().isDead() || this.currentLevel.getSecondCharacter().isDead());
	}

	
	/////////////////////////////////////////////////////////
	//////////////////////// INPUTS /////////////////////////
	/////////////////////////////////////////////////////////
	@Override
	public void mouseClicked(int button, int x, int y, int clickCount){
		// If the level is over...
		if(this.isFinished){
			// We save the result
			Save.getInstance().levelSaveForLevelID(this.currentLevel.getLevelID()).
			setSavedLevelData(this.currentLevelState.getNbKeys(), true, true);
			Save.getInstance().save();
			selection = this.uiWin.mouseClicked(button, x, y, clickCount, this);
			
			// If the player requested to go to the next level, we change the level
			if(selection == Game.GO_TO_NEXT_LEVEL){
				int id = this.currentLevel.getLevelID();
				
				// If it was the last level, we display an ending screen depending on the number of collected bonuses
				if(id == 5){
					if(Save.getInstance().getTotalNumberOfUnlockedKeys()
							- Save.getInstance().getTotalNumberOfKeys() >= 0){
						id = 7;
					}
					else {
						id = 6;
					}
					endOfGame = true;
				}
				// Otherwise we just go to the next level
				else {
					id++;
				}
				this.ChooseLevel(id);
				this.music_changed = true;
				selection = Game.NARRATIVE_STATE;	
			}		
		}
		
		// If the pause menu was displayed we get the action
		else if(this.isPaused){
			// It can be a state change (help, menu, restart, etc.) ...
			selection = this.uiPause.mouseClicked(button, x, y, clickCount, this);
			// ... or stop/start the music
			if(selection == Game.STOP_PLAY_MUSIC){
				this.stop_play_music = true;
				selection = -1;
			}		
		}
		
		// If the death menu is displayed, get the action (restart, menu)
		else if(this.currentLevel!=null && this.arePlayersDead()){
			selection = this.uiDeath.mouseClicked(button, x, y, clickCount, this);
		}
		
		// That part is reserved to the UI, get the action (restart)
		else if(y >= Global.GAMEPLAYHEIGHT){
			selection = this.uiGameplay.mouseClicked(button, x, y, clickCount, this);
		}
		
		// If we should restart, then restart
		if(selection == Game.SHOULD_RESTART && this.currentLevel != null){
			this.ChooseLevel(this.currentLevel.getLevelID());
			selection = -1;
		}
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////  GAME LOOP  //////////////////////
	/////////////////////////////////////////////////////////
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
	throws SlickException {

		// If we want to change the state of the game (go to another screen), we do it
		if(this.selection != -1){
			if(selection == Game.NARRATIVE_STATE){
				int id = 1;
				if(endOfGame){
					if(Save.getInstance().getTotalNumberOfUnlockedKeys()
							- Save.getInstance().getTotalNumberOfKeys() >= 0){
						id=7;
					}
					else {
						id=6;
					}
				}
				else {
					id = this.currentLevel.getLevelID();
				}
				((NarrativeState)(sbg.getState(selection))).ChooseLevel(id);				
			}
			else if(selection == Game.HELP_STATE){
				((HelpState)(sbg.getState(selection))).setPreviousState(Game.GAMEPLAY_STATE);
			}
			sbg.enterState(selection);	
		}

		// Change the music if needed (the music is different for every game)
		if(this.music_changed){
			((Game)sbg).changeGameplayMusic();
			this.music_changed = false;
		}
		
		// Stop the music if asked (from the menu)
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

		// If no level is loaded, not necessary to go further
		if(this.currentLevel == null){
			return;
		}

		// If the level is finished we save - not necessary to go further either
		if(this.isFinished){
			LevelSave lvlSave = Save.getInstance().levelSaveForLevelID(this.currentLevel.getLevelID());
			lvlSave.setSavedLevelData(currentLevel.getNbBonus(), true, true);
			if(lvlSave.getID() < 5){
				LevelSave nextLvlSave = Save.getInstance().levelSaveForLevelID(this.currentLevel.getLevelID()+1);
				nextLvlSave.setSavedLevelData(0, true, false);
			}
			Save.getInstance().save();
			return;			
		}

		// If the players are dead they can restart the game
		if(this.arePlayersDead() && (this.startAgain || this.alwaysStartAgain)){
			this.ChooseLevel(this.currentLevel.getLevelID());
			this.startAgain = false;
			return;
		}

		Creature char1 = this.currentLevel.getFirstCharacter();
		Creature char2 = this.currentLevel.getSecondCharacter();
		Input input = gc.getInput();

		// If the players are dead or the menu is displayed, we pause the game
		if(this.arePlayersDead() || this.isPaused){	
			input.consumeEvent();
			return;
		}

		// Check whether Players are at the Exit
		// TODO: debug - we still have times where both players are at exit and the exit is not validated
		if(exit!=null) {
			if (!((Creature)this.ch1_body.getUserData()).rectCollideWithOther((Sprite)(exit.getUserData()))){
				((Creature)this.ch1_body.getUserData()).setAtExit(false);
			}
			else {
				((Creature)this.ch1_body.getUserData()).setAtExit(true);
			}
			if (!((Creature)this.ch2_body.getUserData()).rectCollideWithOther((Sprite)(exit.getUserData()))){
				((Creature)this.ch2_body.getUserData()).setAtExit(false);
			}
			else {
				((Creature)this.ch2_body.getUserData()).setAtExit(true);
			}			
			if(((Creature)this.ch1_body.getUserData()).isAtExit() && ((Creature)this.ch2_body.getUserData()).isAtExit()){
				this.isFinished = true;
				return;
			}
		}

		// If the players need to be teleported, we do so
		boolean aPlayerTransported = false;
		if(((Creature)this.ch1_body.getUserData()).isTransported() ){
			Vec2 b2dcoord = Global.getBox2DCoordinates(((Creature)ch1_body.getUserData()).X_transported, ((Creature)ch1_body.getUserData()).Y_transported);
			Vec2 position = new Vec2(b2dcoord.x+((Creature)ch1_body.getUserData()).W()/2, b2dcoord.y-((Creature)ch1_body.getUserData()).H()/2);
			ch1_body.setXForm(position,0);
			((Creature)ch1_body.getUserData()).setTransported(false,0,0);
			aPlayerTransported = true;
		}
		if(((Creature)this.ch2_body.getUserData()).isTransported() ){
			Vec2 b2dcoord = Global.getBox2DCoordinates(((Creature)ch2_body.getUserData()).X_transported, ((Creature)ch2_body.getUserData()).Y_transported);
			Vec2 position = new Vec2(b2dcoord.x+((Creature)ch2_body.getUserData()).W()/2, b2dcoord.y-((Creature)ch2_body.getUserData()).H()/2);
			ch2_body.setXForm(position,0);
			((Creature)ch2_body.getUserData()).setTransported(false,0,0);
			aPlayerTransported = true;
		}
		if(aPlayerTransported){ return; }

		// Otherwise, take care of the inputs
		boolean char1CanJump = (char1.isFlying() || !char1.isFalling) && !char1.isSlipping;
		boolean char2CanJump = (char2.isFlying() || !char2.isFalling) && !char2.isSlipping;
		boolean char1CanMove = (char1CanJump || !char1.isColliding) && !char1.isSlipping;
		boolean char2CanMove = (char2CanJump || !char2.isColliding) && !char2.isSlipping;

		// Player 1 movements
		if(char1CanJump){
			if(char1.isFlying()){
				if(input.isKeyDown(Input.KEY_Z)){ // Fly
					ch1_body.m_linearVelocity.y = +SPEED_X;
					char1.isFalling = true;			
				}
			}
			else if(char1.shouldJumpAfterRebound()){ // Rebound
				ch1_body.applyImpulse(new Vec2(0, 2*SPEED_JUMP), ch1_body.getWorldCenter());	
				char1.setJumpAfterRebound(false);
				char1.isFalling = true;
			}
			else if(input.isKeyPressed(Input.KEY_Z)){ // Jump
				ch1_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch1_body.getWorldCenter());	
				char1.isFalling = true;
			}
		}
		else if(!char1.isSlipping && char1.isRebond()){ 
			if(input.isKeyPressed(Input.KEY_Z)){ // Prepare rebound
				char1.setJumpAfterRebound(true);		
			}
		}
		if(((input.isKeyDown(Input.KEY_Q)) && char1CanMove)||(char1.isGoingLeft&&char1.isSlipping)){ // Left
			ch1_body.m_linearVelocity.x = -SPEED_X;			
			char1.goLeft();
		}
		else if(((input.isKeyDown(Input.KEY_D)) && char1CanMove)||(char1.isGoingRight&&char1.isSlipping)){ // Right
			ch1_body.m_linearVelocity.x = SPEED_X;			
			char1.goRight();
		}
		else if(input.isKeyPressed(Input.KEY_A)){ // Action
			if(char1.canTeleport()){
				char2.setTransported(true, char1.X(), char1.Y());
			}
			else if(char1.dedouble()){
				Creature ch = currentLevel.addCharacterWithPoints(char1.X(), char1.Y(), 0.75f);
				currentLevel.getSprites().add(ch);
				ch.initTimer();
				char1.setPower(Power.NONE);
			}
		}
		else if(!char1.isFalling){ // Standing
			ch1_body.m_linearVelocity.x = 0;			
			char1.straight();
		}
		
		// Player 2 movements
		if(char2CanJump){
			if(char2.isFlying()){
				if(input.isKeyDown(Input.KEY_UP)){ // Fly
					ch2_body.m_linearVelocity.y = +SPEED_X;
					char2.isFalling = true;			
				}
			}
			else if(char2.shouldJumpAfterRebound()){ // Rebound
				ch2_body.applyImpulse(new Vec2(0, SPEED_JUMP/3), ch2_body.getWorldCenter());	
				char2.setJumpAfterRebound(false);
			}
			else if(input.isKeyPressed(Input.KEY_UP)){ // Jump
				ch2_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch2_body.getWorldCenter());	
				char2.isFalling = true;
			}
		}
		else if(!char2.isSlipping && char2.isRebond()){ 
			if(input.isKeyDown(Input.KEY_UP)){ // Prepare rebound
				char2.setJumpAfterRebound(true);		
			}
		}
		if(((input.isKeyDown(Input.KEY_LEFT)) && char2CanMove)||(char2.isGoingLeft&&char2.isSlipping)){ // Left
			ch2_body.m_linearVelocity.x = -SPEED_X;			
			char2.goLeft();
		}
		else if(((input.isKeyDown(Input.KEY_RIGHT)) && char2CanMove)||(char2.isGoingRight&&char2.isSlipping)){ // Right
			ch2_body.m_linearVelocity.x = SPEED_X;			
			char2.goRight();
		}
		else if(input.isKeyPressed(Input.KEY_ENTER)){ // Action
			if(char2.canTeleport())
				char1.setTransported(true, char2.X(), char2.Y());
			else if(char2.dedouble()){
				Creature ch = currentLevel.addCharacterWithPoints(char2.X(), char2.Y(), 0.75f);
				currentLevel.getSprites().add(ch);
				ch.initTimer();
				char2.setPower(Power.NONE);
			}

		}
		else if(!char2.isFalling){ // Standing
			ch2_body.m_linearVelocity.x = 0;			
			char2.straight();
		}

		// Remove the unhandled events
		input.clearKeyPressedRecord();

		// Body modifications following a new power
		if((char1.isPetit())&&(char1.getShouldChangeSize())){
			modifyBodySize(getBodyForUserData(char1),(float)0.5,(float)0.5);
			char1.setShouldChangeSize(false);
		}
		if((char2.isPetit())&&(char2.getShouldChangeSize())){
			System.out.println("char 2 petit + change");
			modifyBodySize(getBodyForUserData(char2),(float)0.5,(float)0.5);
			char2.setShouldChangeSize(false);
		}
		if((!char1.isPetit())&&(char1.getShouldChangeSize())){
			modifyBodySize(getBodyForUserData(char1),(float)2.0,(float)2.0);
			char1.setShouldChangeSize(false);
		}
		if((!char2.isPetit())&&(char2.getShouldChangeSize())){
			modifyBodySize(getBodyForUserData(char2),(float)2.0,(float)2.0);
			char2.setShouldChangeSize(false);
		}
		if((char1.isRebond())&&(char1.getShouldRebond())){
			modifyBodyRebond(getBodyForUserData(char1),(float)0.9);
			char1.setShouldRebond(false);
		}
		if((char2.isRebond())&&(char2.getShouldRebond())){
			modifyBodyRebond(getBodyForUserData(char2),(float)0.9);
			char2.setShouldRebond(false);
		}
		if((char1.canSwim())&& char1.isSwimming()){
			modifyBodyRebond(getBodyForUserData(char1),(float)0.0);
			char1.setIsSwimming(false);
		}
		if((char2.canSwim())&& char2.isSwimming()){
			modifyBodyRebond(getBodyForUserData(char2),(float)0.0);
			char2.setIsSwimming(false);
		}

		// Update the position of the characters
		char1.setCoordinatesFromBody(ch1_body);
		char2.setCoordinatesFromBody(ch2_body);	

		// Update the monster's speed
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		for(int i = 0 ; i < monsterBodies.size() ; ++i){
			Body b = monsterBodies.get(i);
			Monster m = (Monster)b.getUserData();
			b.setLinearVelocity(m.getSpeed());	
			((Monster)b.getUserData()).step(delta);
			m.setCoordinatesFromBody(b);
			if (m.getShouldBeDestroy()){
				currentLevel.getSprites().remove(m);
				tempList.add(i);
				if(m instanceof Witch){
					this.isFinished = true;
				}
			}
		}
		
		// Remove the killed monsters
		for(int i = 0 ; i < tempList.size() ; ++i){
			Body tempBody = monsterBodies.get((tempList.get(i))-i);
			this.monsterBodies.remove(tempBody);
			this.world.destroyBody(tempBody);
			currentLevel.incrementNbBonus(); // Every monster killed gives a fruit
			this.currentLevelState.setNbKeysUnlocked(currentLevel.getNbBonus());
		}
		tempList.clear();

		// 0.1s change in our Box2D world
		world.step((float)delta/100, 100);

		// Update the screen and the model values
		for(int i = 0 ; i < spriteBodies.size() ; ++i){
			Body tempBody = spriteBodies.get(i);
			Sprite theSprite = (Sprite)tempBody.getUserData();
			if (theSprite.getShouldBeDestroy()){
				if(theSprite instanceof Bonus && ((Bonus)theSprite).isObtained()){
					currentLevel.incrementNbBonus();
					this.currentLevelState.setNbKeysUnlocked(currentLevel.getNbBonus());
				}
				currentLevel.getSprites().remove(theSprite);
				tempList.add(i);
			}
			else {
				if(!(theSprite instanceof Lever)&&!(theSprite instanceof LeverCombination))
					theSprite.setCoordinatesFromBody(tempBody);
			}
			
			// Missiles
			// If a shoot button has been pushed, we shoot the missiles
			if(theSprite instanceof PushButtonShoot && ((PushButtonShoot)theSprite).isShouldShoot()) {
				((PushButtonShoot)theSprite).shoot();
			}
			// If a move button has been pushed, we move the platforms
			if(theSprite instanceof PushButtonMove && ((PushButtonMove)theSprite).isEnabled()){
				((PushButtonMove)theSprite).move();
			}
			// If a charge button has been pushed, we charge the platform
			if(theSprite instanceof MissilePlatform && ((MissilePlatform)theSprite).shouldRecharge()){
				PlatformMissile missile = currentLevel.createMissile(((MissilePlatform)theSprite).X()+(((MissilePlatform)theSprite).W()-50)/2,((MissilePlatform)theSprite).Y()+((MissilePlatform)theSprite).H(),50,50,((MissilePlatform)theSprite));
				Body monmissile = getBodyForUserData(missile);
				((MissilePlatform)theSprite).setMissile(monmissile);
				missile.setDeadly(true);
			}
			
			// Update the projectiles position and remove them if they are out of the screen
			if(theSprite instanceof FireBall) {
				Vec2 slickcoord = ((FireBall)theSprite).getNextPosition(delta);
				Vec2 b2dcoord = Global.getBox2DCoordinates((int)slickcoord.x,(int)slickcoord.y);
				Vec2 position = new Vec2(b2dcoord.x+theSprite.W()/2, b2dcoord.y-theSprite.H()/2);
				if(slickcoord.x < 0 || slickcoord.x > Global.GAMEPLAYWIDTH
						|| slickcoord.y < 0 || slickcoord.y > Global.GAMEPLAYHEIGHT){
					theSprite.setShouldBeDestroy();
				}
				else {
					tempBody.setXForm(position, 0);					
				}
			}
		}	
		
		// Actually remove the sprites
		for(int i = 0 ; i < tempList.size() ; ++i){
			Body tempBody = spriteBodies.get((tempList.get(i))-i);
			this.spriteBodies.remove(tempBody);
			this.world.destroyBody(tempBody);
		}		
		
		// Update the state of the level
		this.currentLevelState.setPlayer1Power(((Creature)ch1_body.getUserData()).getPower());
		this.currentLevelState.setPlayer2Power(((Creature)ch2_body.getUserData()).getPower());
		
		// Update the UI at the bottom of the screen
		this.uiGameplay.setTempLevelInformation(currentLevelState.getPlayer1Power(),
				currentLevelState.getPlayer2Power(), 
				currentLevel.getNbBonus());
	}


	
	/////////////////////////////////////////////////////////
	//////////////////////// BODIES /////////////////////////
	/////////////////////////////////////////////////////////
	/** Create the Box2D environment with some standard gravity */
	private void createWorld(){
		Vec2 v = new Vec2(0.0f,-10.0f);
		AABB aabb = new AABB(new Vec2(-10.0f,-10.0f), new Vec2((float)Global.GAMEPLAYWIDTH+10,(float)Global.GAMEPLAYHEIGHT+10));
		world = new World(aabb,v,false);
		world.setContactListener(new MyContactListener());
		world.setContactFilter(new MyContactFilter());
	}
	/** Remove all the bodies from the world */
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
	/**
	 * Search in the current level's bodies for a body which has the given object has userData
	 * @param userData The data we are trying to find in the user data's values of the bodies of the current world
	 * @return The body related to that userData
	 */
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
	
	/**
	 * Helper method to avoid repeating code.
	 * Create a new body which userData and massData values are given as parameters 
	 */ 
	public Body addBodyWithSprite(Sprite userData, MassData md){
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = userData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(userData.X(), userData.Y());
		bodyDef.position = new Vec2(b2dcoord.x+userData.W()/2,b2dcoord.y-userData.H()/2);
		if(md != null){ bodyDef.massData = md; }
		return world.createBody(bodyDef);
	}
	/**
	 * Helper method to avoid repeating code.
	 * Create a PolygonDef and add a shape to the given body with that definition.
	 * Requires the body to have a Sprite userData value.
	 */
	public void createPolygonDefForBody(Body b, float density, float friction){
		if(!(b.getUserData() instanceof Sprite)){
			return;
		}
		PolygonDef sd = new PolygonDef();		
		sd.density = density;
		sd.friction = friction;
		Sprite userData = (Sprite)b.getUserData();
		sd.setAsBox(userData.W()/2,userData.H()/2);
		b.createShape(sd);
	}
	/** 
	 * Create a body linked to a <code>Wall</code> or <code>Ground</code> object. 
	 * The form of that body is a standard rectangular form matching the object. 
	 */
	public Body addGroundOrWall(Sprite userData){
		// Create the body
		Body newBody = this.addBodyWithSprite(userData, null);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 5.0f, 0.5f);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	/** 
	 * Create a body linked to a <code>Wall</code> or <code>Ground</code> object.
	 * The form is given by a list of points in anticlockwise order.
	 */
	public Body addGroundOrWallWithPoints(Sprite userData, ArrayList<Vec2> list){
		// Create the body
		Body newBody = this.addBodyWithSprite(userData, null);
		
		// Create the shape
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 0.5f;
		for(Vec2 v: list){
			sd.addVertex(v);			
		}
		newBody.createShape(sd);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	/** An obstacle is created the same way than Walls and Grounds but with a higher density */
	public Body addObstacle(Obstacle obstacleData){
		// Create the body
		Body newBody = this.addBodyWithSprite(obstacleData, null);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 5000.0f, 0.5f);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addDestructible(Destructible destructibleData){
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(destructibleData, md);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 5000.0f, 0.5f);
		
		// Finish the creation
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addObstacleWithPoints(Obstacle obstacleData, ArrayList<Vec2> list){//
		// Create the body
		Body newBody = this.addBodyWithSprite(obstacleData, null);
		
		// Create a shape for that body
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 0.5f;
		for(Vec2 v: list){
			sd.addVertex(v);			
		}
		newBody.createShape(sd);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		newBody.putToSleep();
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addExit(Exit exitData){
		// Create the body
		Body newBody = this.addBodyWithSprite(exitData, null);

		// Create the shape
		PolygonDef sd = new PolygonDef();		//
		sd.density = 5000.0f;
		sd.friction = 1.0f;
		sd.setAsBox(exitData.W()/2,exitData.H()/2);
		sd.isSensor=true; // ?
		newBody.createShape(sd);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		spriteBodies.add(newBody);
		exit=newBody;
		newBody.putToSleep();
		return newBody;
	}
	public Body addBonus(Bonus bonusData){
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(bonusData, md);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 5000.0f, 1.0f);
		
		// Finish the creation
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addSource(Source sourceData){
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(sourceData, md);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 5.0f, 0.5f);
		
		// Finish the creation
		spriteBodies.add(newBody);		
		return newBody;
	}
	public Body addTransporter(Transporter transporterData){
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(transporterData, md);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 5.0f, 1.0f);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		newBody.putToSleep();
		spriteBodies.add(newBody);		
		return newBody;
	}
	public Body addPushButton(PushButton button){
		// Create the body
		Body newBody = this.addBodyWithSprite(button, null);

		// Create a shape for that body
		this.createPolygonDefForBody(newBody, 100.0f, 1.0f);
		
		// Finish the creation
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addLever(Sprite lever){
		// We create it manually because we want the body to be smaller than the whole box represented by the sprite
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = lever;
		Vec2 b2dcoord = Global.getBox2DCoordinates(lever.X()+2, lever.Y()-5);
		bodyDef.position = new Vec2(b2dcoord.x+(lever.W()-4)/2,b2dcoord.y-(lever.H()-5)/2);
		Body newBody = world.createBody(bodyDef);
		
		// Create a shape for that body
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 1.0f;
		sd.setAsBox((lever.W()-4)/2,(lever.H()-5)/2);
		
		// Finish the creation
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		return newBody;
	}
	public Body addIndicationSprite(IndicationSprite indic){
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(indic, md);

		// Create a shape for that body
		PolygonDef sd = new PolygonDef();
		sd.density = 5000.0f;
		sd.friction = 1.0f;
		sd.setAsBox(indic.W()/2,indic.H()/2);
		sd.isSensor=true;
		newBody.createShape(sd);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		spriteBodies.add(newBody);
		newBody.putToSleep();
		return newBody;
	}
	public Body addMonster(Monster monsterData, float coef){
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(monsterData, md);

		// Create a shape for that body
		PolygonDef sd = new PolygonDef();		
		sd.density = 5000.0f;
		sd.friction = 1.0f;
		sd.setAsBox(monsterData.W()/2*coef,monsterData.H()/2*coef);
		newBody.createShape(sd);
		
		// Finish the creation
		monsterBodies.add(newBody);
		return newBody;
	}
	public Body addMissile(PlatformMissile destructibleData){ //TODO: box ?
		// Create the body
		MassData md = new MassData();
		md.mass = 100.0f;
		Body newBody = this.addBodyWithSprite(destructibleData, md);
		
		
		// Create a shape for that body
		CircleDef sd = new CircleDef();		
		sd.density = 5000.0f;
		sd.friction = 0.5f;
		sd.radius=destructibleData.W()/2;
		newBody.createShape(sd);
		
		// Finish the creation and put to sleep the object to avoid unnecessary computing
		spriteBodies.add(newBody);
		newBody.putToSleep(); //FIXME: careful
		return newBody;
	}
	/** A fireball body is a simple body with a single small circle shape*/
	public Body addFireBall(FireBall fireballData){
		// Create the body
		Body newBody = this.addBodyWithSprite(fireballData, null);
		
		// Create a circle shape for that body
		CircleDef sd = new CircleDef();
		sd.radius=fireballData.W()/8; //FIXME: problem with collisions 
		newBody.createShape(sd);
		
		// Finish the creation
		spriteBodies.add(newBody);
		return newBody;
	}
	/**
	 * The creation of a character's body is a little more complex. It is indeed composed of two shapes: 
	 * the main shape (the whole body of the character) and a sensor at his feet to search for ground collisions. 
	 * @param characterData The character object (<code>Sprite</code>)
	 * @param list The list of points in unclockwised order
	 * @return The character's freshly created body
	 */
	public Body addCharacter(Creature characterData, ArrayList<Vec2> list){
		// Creation of the body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = characterData;
		Vec2 b2dcoord = Global.getBox2DCoordinates(characterData.X(), characterData.Y());
		bodyDef.position = new Vec2(b2dcoord.x+characterData.W()/2, b2dcoord.y-characterData.H()/2);
		MassData md = new MassData();
		md.mass = 10.0f;
		bodyDef.massData = md;

		// Creation of the main body shape
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

		// Creation of the ground sensor
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
		
		// Update references
		if(ch1_body == null){ // First character creation
			ch1_body = world.createBody(bodyDef);
			ch1_body.createShape(sd);
			ch1_body.createShape(groundSensor);
			ch1_body.wakeUp();
			return ch1_body;
		}
		else if(ch2_body==null){ // Second character creation 
			ch2_body = world.createBody(bodyDef);
			ch2_body.createShape(sd);
			ch2_body.createShape(groundSensor);
			ch2_body.wakeUp();
			return ch2_body;
		}
		else{ // Additional character creation (doubles)
			Body perso = world.createBody(bodyDef);
			perso.createShape(sd);
			perso.createShape(groundSensor);
			perso.wakeUp();
			spriteBodies.add(perso);
			return perso;
		}
	}
	
	/**
	 * When we want to make a body rebound, we need to change a few values of the body.
	 * However, this is only possible by creating a new body.
	 * Note that it will only have an effect on bodies with a Sprite for user data value.
	 * 
	 * @param body The body we want to change the property of
	 * @param R The rebound ratio (restitution)
	 * @return The new body
	 */
	public Body modifyBodyRebond(Body body, float R) {
		// We only want bodies linked to Sprites
		if(!(body.getUserData() instanceof Sprite)){
			return null;
		}

		// We create a new body definition
		Sprite userData = (Sprite)body.getUserData();
		Vec2 b2dcoord = Global.getBox2DCoordinates(((Sprite)userData).X(), ((Sprite)userData).Y());
		Vec2 b2position = new Vec2(b2dcoord.x+((Sprite)userData).W()/2, b2dcoord.y-((Sprite)userData).H()/2);
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = userData;
		bodyDef.position = b2position;
		MassData md = new MassData();
		md.mass = body.getMass();
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);		
		
		// We recreate every shape of the body but change the restitution
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

		// We update the references to that body
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

		// We destroy the old one
		world.destroyBody(body);
		body = null;

		// And return the new one
		return newBody;
	}
	/**
	 * Modification of the size of an already existing body. These bodies should have a <code>Sprite</code> object 
	 * as user data.
	 * @param body The body to modify
	 * @param h The height ratio (0.5 to half the size, for example)
	 * @param w The width ratio
	 * @return the new body
	 */
	public Body modifyBodySize(Body body, float h, float w)	{
		// We only treat Sprite bodies
		if(!(body.getUserData() instanceof Sprite)){
			return null;
		}

		// We change the width and height of the model object
		Sprite userData = (Sprite)body.getUserData();
		int newW = (int) (userData.W()*w);
		int newH = (int) (userData.H()*h);
		userData.setX(userData.X() - (newW - userData.W())/2);
		userData.setY(userData.Y() - (newH - userData.H())); 
		userData.setW(newW);
		userData.setH(newH);

		// We create a new body definition with the new size (and actualize the coordinates to match the size)
		Vec2 b2dcoord = Global.getBox2DCoordinates(((Sprite)userData).X(), ((Sprite)userData).Y());
		Vec2 b2position = new Vec2(b2dcoord.x+((Sprite)userData).W()/2, b2dcoord.y-((Sprite)userData).H()/2);
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = userData;
		bodyDef.position = b2position;
		MassData md = new MassData();
		md.mass = body.getMass();
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);		
		Shape shape = body.getShapeList();

		// We restore all the other properties of its shapes
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

		// We actualize the body
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
		
		// We destroy the old one
		world.destroyBody(body);
		body = null;

		// Return the new body
		return newBody;
	}
	
	

	/////////////////////////////////////////////////////////
	//////////////////////// DRAWING ////////////////////////
	/////////////////////////////////////////////////////////
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
	throws SlickException {		
		// First render the UI at the bottom of the screen
		uiGameplay.render(gc, g);
		
		// Then, render the level
		if(currentLevel !=null){
			currentLevel.render(gc, sbg, g);

			// If one of the characters is dead, display the death screen
			if(this.currentLevel.getFirstCharacter().isDead() || this.currentLevel.getSecondCharacter().isDead()){	
				this.uiDeath.render(gc, g);
			}
		}
	
		// If the level has been finished, display the end of level screen
		if(this.isFinished){
			this.uiWin.render(gc, g);
		}
		
		// If the players asked for the menu, the menu is displayed
		else if(this.isPaused){	
			this.uiPause.render(gc, g);
		}
	}
	
}
