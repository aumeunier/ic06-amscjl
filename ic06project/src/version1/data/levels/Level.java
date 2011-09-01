package version1.data.levels;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import version1.Global;
import version1.data.LevelSave;
import version1.sprites.Power;
import version1.sprites.Sprite;
import version1.sprites.alive.Creature;
import version1.sprites.alive.monsters.Monster;
import version1.sprites.decor.Ground;
import version1.sprites.decor.InGameIndication;
import version1.sprites.decor.IndicationSprite;
import version1.sprites.decor.Obstacle;
import version1.sprites.decor.Wall;
import version1.sprites.interaction.Bonus;
import version1.sprites.interaction.Destructible;
import version1.sprites.interaction.Exit;
import version1.sprites.interaction.MissilePlatform;
import version1.sprites.interaction.Transporter;
import version1.sprites.interaction.projectiles.FireBall;
import version1.sprites.interaction.projectiles.PlatformMissile;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.sources.DeathlySource;
import version1.sprites.interaction.trigger.Lever;
import version1.sprites.interaction.trigger.LeverCombination;
import version1.sprites.interaction.trigger.buttons.PushButtonShoot;
import version1.sprites.interaction.trigger.buttons.PushButtonMove;
import version1.sprites.interaction.trigger.buttons.PushButtonElevator;
import version1.sprites.interaction.trigger.buttons.PushButton;
import version1.states.GameplayState;

public class Level {	
	protected int levelID;
	/** The state that displays the level */
	protected GameplayState myState;
	/** The first player object */
	protected Creature character1;
	/** The second player object */
	protected Creature character2;
	/** The list of sprites (decor, buttons...) */
	protected ArrayList<Sprite> sprites;
	/** The list of sprites to display behind every other sprite */
	protected ArrayList<Sprite> backgroundSprites;
	/** The background image used */
	protected Image backgroundImage;
	/** The list of exit objects for this level */
	protected ArrayList<Exit> listeExit;
	/** The list of indications in this level */
	protected ArrayList<InGameIndication> indications;
	/** The model of this level */
	protected LevelSave levelModel;
	/** The number of bonus which have been collected so far */
	protected int nbBonus=0;
	/** Whether the level is plunged into the dark */
	protected boolean inTheDarkness=false;
	/** The image used for the light effet */
	protected Image lightImage = Global.getImage(Global.DEFAULT_LIGHT_IMAGE);

	
	/////////////////////////////////////////////////////////
	///////////////////////// INIT //////////////////////////
	/////////////////////////////////////////////////////////
	/**
	 * Standard constructor
	 * @param state The state owner of the level
	 * @param model The model of this level
	 */
	public Level(GameplayState state, LevelSave model){
		this.myState = state;
		this.sprites = new ArrayList<Sprite>();
		this.backgroundSprites = new ArrayList<Sprite>();
		this.listeExit = new ArrayList<Exit>();
		this.indications = new ArrayList<InGameIndication>();
		this.levelModel = model;
	}
	/** Finish the initialisation of a level */
	protected void finishInitialisation(){
		setLevelForAllSprites();
	}
	/** Claim ownership on all the sprites */
	protected void setLevelForAllSprites(){
		character1.setLevelContainer(this);
		character2.setLevelContainer(this);
		for(Sprite s: sprites){
			s.setLevelContainer(this);
		}
		for(Sprite s: backgroundSprites){
			s.setLevelContainer(this);
		}
		for(Sprite s: listeExit){
			s.setLevelContainer(this);
		}
	}

	/////////////////////////////////////////////////////////
	//////////////////////// GETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	/** @return the first player object */
	public Creature getFirstCharacter(){
		return this.character1;
	}
	/** @return the second player object */
	public Creature getSecondCharacter(){
		return this.character2;
	}
	/** @return the LevelSave instance related to that level */
	public LevelSave getLevelModel(){
		return this.levelModel;
	}
	/** @return the list of sprites contained in this level */
	public ArrayList<Sprite> getSprites(){
		return this.sprites;
	}
	/** @return the number of collected bonus */
	public int getNbBonus(){
		return this.nbBonus;
	}
	/** A bonus has been collected. Increment the counter. */
	public void incrementNbBonus(){
		this.nbBonus++;
	}
	/** @return the id of the level */
	public int getLevelID(){
		return this.levelID;
	}
	/** @return the exit's list */
	public ArrayList<Exit> getExit(){
		return this.listeExit;
	}

	/////////////////////////////////////////////////////////
	//////////////////////// SETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	protected void setBackgroundImage(String filename){
		try {
			this.backgroundImage = new Image(Global.PATH_IMAGES_RESSOURCES+filename).
				getScaledCopy(Global.WINDOW_WIDTH, Global.WINDOW_HEIGHT);
			this.backgroundImage = this.backgroundImage.getSubImage(0, 0, this.backgroundImage.getWidth(), 
						this.backgroundImage.getHeight()*Global.GAMEPLAYHEIGHT/Global.WINDOW_HEIGHT);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	/////////////////////////////////////////////////////////
	////////////////// LEVEL CONSTRUCTION ///////////////////
	/////////////////////////////////////////////////////////	
	/**
	 * Remove a sprite from the list of sprites
	 * @param s Sprite to remove
	 */
	public void removeSpriteFromList(Sprite s){
		this.sprites.remove(s);
	}
	
	/** Create and return a Wall object, add it to the sprites' list and create a box2d body linked to this object. */
	protected Wall createWall(int x, int y, int w, int h){
		// Create a wall object
		Wall wall = new Wall(x,y,w,h);
		// Add it to the list of sprites of the level
		sprites.add(wall);
		// Create the wall body
		myState.addGroundOrWall(wall);
		return wall;
	}
	/** Create a Wall object and a Box2D body related to it. The form of the body is given by the plist of points. */
	protected Wall createWallWithPoints(int x, int y, int w, int h, ArrayList<Vec2> points){
		Wall wall = new Wall(x,y,w,h);
		sprites.add(wall);
		myState.addGroundOrWallWithPoints(wall, points);
		return wall;
	}
	protected Ground createGround(int x, int y, int w, int h){
		Ground ground = new Ground(x,y,w,h);
		sprites.add(ground);
		myState.addGroundOrWall(ground);
		return ground;
	}
	protected Ground createGroundWithPoints(int x, int y, int w, int h, ArrayList<Vec2> points){
		Ground ground = new Ground(x,y,w,h);
		sprites.add(ground);
		myState.addGroundOrWallWithPoints(ground, points);
		return ground;
	}	
	protected Obstacle createObstacle(int x, int y, int w, int h){
		Obstacle obstacle = new Obstacle(x,y,w,h);
		sprites.add(obstacle);
		myState.addObstacle(obstacle);
		return obstacle;
	}
	protected Obstacle createObstacleWithPoints(int x, int y, int w, int h, ArrayList<Vec2> points){
		Obstacle obstacle = new Obstacle(x,y,w,h);
		sprites.add(obstacle);
		myState.addObstacleWithPoints(obstacle, points);
		return obstacle;
	}
	protected Destructible createDestructible(int x, int y, int w, int h){
		Destructible destructible = new Destructible(x,y,w,h);
		sprites.add(destructible);
		myState.addDestructible(destructible);
		return destructible;
	}
	public PlatformMissile createMissile(int x, int y, int w, int h, MissilePlatform p){
		PlatformMissile missile = new PlatformMissile(x,y,w,h,p);
		sprites.add(missile);
		myState.addMissile(missile);
		return missile;
	}
	protected Exit createExit(int x, int y, int w, int h){
		Exit exit = new Exit(x,y,w,h);
		listeExit.add(exit);
		myState.addExit(exit);
		return exit;
	}
	protected Source createSource(int x, int y, int w, int h, Power power){
		Source source = new Source(x,y,w,h,power);
		backgroundSprites.add(source);
		myState.addSource(source);		
		return source;
	}
	protected Transporter createTransporter(int x, int y, int w, int h, int nx, int ny){
		Transporter transporter = new Transporter(x,y,w,h,nx,ny);
		sprites.add(transporter);
		myState.addTransporter(transporter);
		return transporter;
	}
	protected DeathlySource createDeathlySource(int x, int y, int w, int h){
		DeathlySource source = new DeathlySource(x,y,w,h);
		sprites.add(source);
		myState.addSource(source);		
		return source;
	}
	/** @param b The bodies linked to the button */
	protected PushButton createPushButton(int x, int y, int w, int h, ArrayList<Body> b){
		PushButton bouton = new PushButton(x,y,w,h,b,1);
		sprites.add(bouton);
		myState.addPushButton(bouton);
		return bouton;
	}
	/**
	 * @param b The bodies linked to the button
	 * @param weight The minimum weight needed to push the button
	 */
	protected PushButton createPushButton(int x, int y, int w, int h, ArrayList<Body> b, int weight){
		PushButton bouton = new PushButton(x,y,w,h,b,weight);
		sprites.add(bouton);
		myState.addPushButton(bouton);
		return bouton;
	}	
	protected PushButtonShoot createBoutonBombarde(int x, int y, int w, int h, ArrayList<Body> b){
		PushButtonShoot bouton = new PushButtonShoot(x,y,w,h,b);
		sprites.add(bouton);
		myState.addPushButton(bouton);
		return bouton;
	}
	protected PushButtonMove createBoutonDeplace(int x, int y, int w, int h, ArrayList<Body> b, String s){
		PushButtonMove bouton = new PushButtonMove(x,y,w,h,b,s);
		sprites.add(bouton);
		myState.addPushButton(bouton);
		return bouton;
	}
	/** @param pu Power of the button */
	protected PushButtonElevator createBoutonElevator(int x, int y, int w, int h, ArrayList<Body> b, int weight, int pu){
		PushButtonElevator bouton = new PushButtonElevator(x,y,w,h,b,weight,pu);
		sprites.add(bouton);
		myState.addPushButton(bouton);
		return bouton;
	}
	protected Lever createLevier(int x, int y, int w, int h, ArrayList<Body> b){
		Lever levier = new Lever(x,y,w,h,b);
		sprites.add(levier);
		myState.addLever(levier);
		return levier;
	}
	protected LeverCombination createLevierCombi(int x, int y, int w, int h, ArrayList<ArrayList<Body> > b, ArrayList<ArrayList<Boolean> > test){
		LeverCombination levier = new LeverCombination(x,y,w,h,b,test);
		sprites.add(levier);
		myState.addLever(levier);
		return levier;
	}
	protected Bonus createBonus(int x, int y, int w, int h){
		Bonus bonus = new Bonus(x,y,w,h);
		sprites.add(bonus);
		myState.addBonus(bonus);
		return bonus;
	}
	/**
	 * Construct and InGameIndication with the given text, width and height. 
	 * Add the indication to the list of indications.
	 */
	public void addIndication(int w, int h, String text){
		InGameIndication indication = new InGameIndication(w,h,text);
		indications.add(indication);
	}
	/** Add the existing indication to the list of indications. */
	public void addIndication(InGameIndication indication){
		indications.add(indication);
	}
	/** Create a sprite just to detect when to display the given indication. */
	protected IndicationSprite createIndicationSprite(int x, int y, int w, int h, InGameIndication indication){
		IndicationSprite indic = new IndicationSprite(x,y,w,h);
		myState.addIndicationSprite(indic);
		indic.setIndication(indication);
		this.indications.add(indication);
		return indic;		
	}
	/** Create an indication from a sprite (same position) */
	protected IndicationSprite createIndicationFromSprite(Sprite s, InGameIndication indication){
		IndicationSprite indic = new IndicationSprite(s);
		myState.addIndicationSprite(indic);
		indic.setIndication(indication);
		this.indications.add(indication);
		return indic;		
	}
	protected Monster createMonster(int x, int y, int w, int h, float coef){
		Monster monster = new Monster(x,y,w,h);
		sprites.add(monster);
		myState.addMonster(monster, coef);
		return monster;
	}
	protected Creature addCharacter(int x, int y, float ratio){
		// Create a new character and add it to the panel
		Creature ch = new Creature(x,y,ratio);
		// Create the body definition
		myState.addCharacter(ch,null);
		return ch;
	}
	public Creature addCharacterWithPoints(int x, int y, float ratio){
		// Create a new character and add it to the panel
		Creature ch = new Creature(x,y,ratio);
		
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
	public FireBall addFireball(int x, int y, Vec2 speed, Sprite owner){
		FireBall f = new FireBall(x,y,speed,owner);
		sprites.add(f);
		myState.addFireBall(f);
		return f;
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////// DRAWING ////////////////////////
	/////////////////////////////////////////////////////////
	/** Called by the game engine to draw all the elements contained in the level */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		g.setAntiAlias(true);		
		// If the map is plunged in the darkness
		if(this.inTheDarkness){
			g.clearAlphaMap();	        
			g.setDrawMode(Graphics.MODE_ALPHA_MAP);
			for(int i = 0 ; i < backgroundSprites.size() ; ++i){
				Sprite s = backgroundSprites.get(i);
				if(s.isLightInDarkness() && !s.isHidden()){
					s.drawLight(g, true);				
				}
			}
			if(character1.isLightInDarkness()){
				character1.drawLight(g, true);	
			}
			if(character2.isLightInDarkness()){
				character2.drawLight(g, true);				
			}
			for(int i = 0 ; i < sprites.size() ; ++i){
				Sprite s = sprites.get(i);
				if(s.isLightInDarkness() && !s.isHidden()){
					s.drawLight(g, true);				
				}
			}	
			// Alpha blend drawing
	        g.setDrawMode(Graphics.MODE_ALPHA_BLEND); 
			backgroundImage.draw();
			for(int i = 0 ; i < backgroundSprites.size() ; ++i){
				Sprite s = backgroundSprites.get(i);
				if(!s.isLightInDarkness() && !s.isHidden()){
					s.draw(gc, sbg, g);				
				}
				else {
			        g.setDrawMode(Graphics.MODE_NORMAL); 
					s.draw(gc, sbg, g);			
			        g.setDrawMode(Graphics.MODE_ALPHA_BLEND); 
				}
			}
			if(!character1.isLightInDarkness()){
				character1.draw(gc, sbg, g);	
			}
			if(!character2.isLightInDarkness()){
				character2.draw(gc, sbg, g);				
			}
			for(int i = 0 ; i < sprites.size() ; ++i){
				Sprite s = sprites.get(i);
				if(!s.isLightInDarkness() && !s.isHidden()){
					s.draw(gc, sbg, g);				
				}
			}
	        // Normal drawing
	        g.setDrawMode(Graphics.MODE_NORMAL);
			for(int i = 0 ; i < listeExit.size() ; ++i){
				listeExit.get(i).draw(gc, sbg, g);
			}
			if(character1.isLightInDarkness()){
				character1.draw(gc, sbg, g);	
			}
			if(character2.isLightInDarkness()){
				character2.draw(gc, sbg, g);		
			}
			for(int i = 0 ; i < sprites.size() ; ++i){
				Sprite s = sprites.get(i);
				if(s.isLightInDarkness() && !s.isHidden()){
					s.draw(gc, sbg, g);				
				}
			}
			for(InGameIndication indication: indications){
				indication.renderAlpha(gc, sbg, g);
			}   
		}
		// If no darkness we draw as usual
		else {
			backgroundImage.draw();
			for(int i = 0 ; i < backgroundSprites.size() ; ++i){

				Sprite s = backgroundSprites.get(i);
				if(!s.isHidden()){
					s.draw(gc, sbg, g);
				}
			}
			for(int i = 0 ; i < listeExit.size() ; ++i){
				listeExit.get(i).draw(gc, sbg, g);
			}
			character1.draw(gc, sbg, g);
			character2.draw(gc, sbg, g);
			for(int i = 0 ; i < sprites.size() ; ++i){
				Sprite s = sprites.get(i);
				if(!s.isHidden()){
					s.draw(gc, sbg, g);
				}
			}
			for(InGameIndication indication: indications){
				indication.render(gc, sbg, g);
			}				
		}
		g.setAntiAlias(false);
	}
}
