package protoWithSlick;

import java.util.ArrayList;
import org.jbox2d.collision.AABB;
import org.jbox2d.collision.MassData;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class MainPanel extends BasicGame {
	final static int CH1_INIT_X = 50;
	final static int CH1_INIT_Y = Global.GAMEHEIGHT-50;
	final static int SPEED_X = 20;
	final static int SPEED_JUMP = 400;
	private Character ch;
	private World world;
	private Body ch_body;
	private ArrayList<Sprite> sprites;
	private ArrayList<Body> spriteBodies;

	/* ATTENTION 
	 * Systeme de coordonnees de box2D est different de celui de Java Swing
	Dans Swing:
	- X vont de gauche à droite et Y du haut vers le bas
	- pour positionner un objet il faut donner son coin supérieur gauche
	- pour definir la taille de l'objet il faut donner sa largeur et sa hauteur
	
	Dans Box2D:
	- X vont de gauche à droit et Y du bas vers le haut
	- pour positionner un objet il faut donner son centre
	- pour definir la taille de l'objet il faut donner sa demi-largeur et sa demi-hauteur	
	
	Deux méthodes dans Global sont disponibles pour changer de systeme de coordonnées
	Pour la position et la taille il n'y a pas encore de methode definies
	En revanche, a l'interieur de la classe Sprite, il y a une methode pour changer la position d'un sprite a partir d'un body
	*/
	public MainPanel() {
		super("My Slick Game");
	}
	private void createLevel(){
		// Place a ground wall
		createWall(0,Global.GAMEHEIGHT-10,Global.GAMEWIDTH,10);
		// Left wall
		createWall(0,0,10,Global.GAMEHEIGHT);
		// Right wall
		createWall(Global.GAMEWIDTH-10,0,10,Global.GAMEHEIGHT);
		// Celling
		createWall(0,0,Global.GAMEWIDTH,10);
		
		// Place an obstacle
		createObstacle(300,CH1_INIT_Y-50,50,100);
		
		// Place a source
		createSource(200,CH1_INIT_Y-50,50,50);
		
		// Place a second source
		Source s = createSource(400,CH1_INIT_Y-50,50,50);
		s.setImage("pool_bird.png");
		s.power = 2;
	}
	private void createWorld(){
		// Create the b2world
		Vec2 v = new Vec2(0.0f,-10.0f);
		AABB aabb = new AABB(new Vec2(0.0f,0.0f), new Vec2((float)Global.GAMEWIDTH,(float)Global.GAMEHEIGHT));
		world = new World(aabb,v,false);
		world.setContactListener(new MyContactListener());
		world.setContactFilter(new MyContactFilter());
	}
	// Parameters: (x,y) position du mur, w largeur, h longueur (dans Java Swing repere)
	private Wall createWall(int x, int y, int w, int h){
		
		// Create a wall object
		Wall wall = new Wall();
		wall.x = x;
		wall.y = y;
		wall.w = w;
		wall.h = h;
		sprites.add(wall);
		
		// Create the wall body
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = wall;
		Vec2 b2dcoord = Global.getBox2DCoordinates(wall.x, wall.y);
		bodyDef.position = new Vec2(b2dcoord.x+w/2,b2dcoord.y-h/2);
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 5.0f;
		
		// Demande la DEMI largeur et la DEMI hauteur
		sd.setAsBox(wall.w/2,wall.h/2);
		newBody.createShape(sd);
		newBody.putToSleep();
		spriteBodies.add(newBody);

		return wall;
	}
	// Parameters: (x,y) position du mur, w largeur, h longueur (dans Java Swing repere)
	private Obstacle createObstacle(int x, int y, int w, int h){
		
		// Create a wall object
		Obstacle obstacle = new Obstacle();
		obstacle.x = x;
		obstacle.y = y;
		obstacle.w = w;
		obstacle.h = h;
		sprites.add(obstacle);
		
		// Create the wall body
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = obstacle;
		Vec2 b2dcoord = Global.getBox2DCoordinates(obstacle.x, obstacle.y);
		bodyDef.position = new Vec2(b2dcoord.x+w/2,b2dcoord.y-h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 5.0f;
		
		// Demande la DEMI largeur et la DEMI hauteur
		sd.setAsBox(obstacle.w/2,obstacle.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		
		return obstacle;
	}
	private Source createSource(int x, int y, int w, int h){
		
		// Create a wall object
		Source source = new Source();
		source.x = x;
		source.y = y;
		source.w = w;
		source.h = h;
		sprites.add(source);
		
		// Create the wall body
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = source;
		Vec2 b2dcoord = Global.getBox2DCoordinates(source.x, source.y);
		bodyDef.position = new Vec2(b2dcoord.x+w/2,b2dcoord.y-h/2);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyDef.massData = md;
		Body newBody = world.createBody(bodyDef);
		PolygonDef sd = new PolygonDef();		
		sd.density = 5.0f;
		sd.friction = 5.0f;
		
		// Demande la DEMI largeur et la DEMI hauteur
		sd.setAsBox(source.w/2,source.h/2);
		newBody.createShape(sd);
		spriteBodies.add(newBody);
		
		return source;
	}
	private void addCharacter(){
		// Create a new character and add it to the panel
		ch = new Character();
		ch.x = CH1_INIT_X;
		ch.y = CH1_INIT_Y;
		
		// Create the body definition
		BodyDef bodyDef = new BodyDef();
		bodyDef.userData = ch; // link the body to the model
		Vec2 b2dcoord = Global.getBox2DCoordinates(ch.x, ch.y);
		bodyDef.position = new Vec2(b2dcoord.x+ch.w/2, b2dcoord.y-ch.h/2);
		MassData md = new MassData();
		md.mass = 10.0f;
		bodyDef.massData = md;
		ch_body = world.createBody(bodyDef);
		
		PolygonDef sd = new PolygonDef();
		sd.density = 5.0f;
		sd.friction = 0.0f;
		sd.setAsBox(25, 25);
		ch_body.createShape(sd);
		
		// Ajoute un Sensor pour savoir si le character est au sol
		PolygonDef groundSensor = new PolygonDef();
		groundSensor.isSensor=true;
		groundSensor.userData="groundsensor";
		groundSensor.setAsBox((float)(ch.w/2.0*0.9), 2, new Vec2(0,-ch.h/2), 0);
		System.out.println("Character body : x="+(b2dcoord.x+ch.w/2)+" y="+(b2dcoord.y-ch.h/2));
		System.out.println("Sensor body : x="+(b2dcoord.x)+" y="+(b2dcoord.y-ch.h));
		ch_body.createShape(groundSensor);
		ch_body.wakeUp();
		
	}

    public void update(GameContainer gc, int delta) 
			throws SlickException {  
    	boolean canMove = ch.isFlying() || !ch.isFalling;
    	Input input = gc.getInput();
    	if(input.isKeyPressed(Input.KEY_I)){
    		ch.setIntangible(!ch.isIntangible());
			ch.changeImage();
			if(ch.isIntangible()){
				System.out.println("intangible");
			}
			else{
				System.out.println("normal");
			}
		}
		if(input.isKeyPressed(Input.KEY_UP) && canMove){
			ch_body.applyImpulse(new Vec2(0, SPEED_JUMP), ch_body.getWorldCenter());
			ch.isGoingRight = true;
			ch.isGoingLeft = false;
			ch.isFalling = true;
			System.out.println("Jump!");		

		}
		else if(input.isKeyPressed(Input.KEY_RIGHT) && canMove){
			ch_body.m_linearVelocity.x = SPEED_X;
			ch.isGoingRight = true;
			ch.isGoingLeft = false;
		}
		else if(input.isKeyPressed(Input.KEY_LEFT) && canMove){
			ch_body.m_linearVelocity.x = -SPEED_X;
			ch.isGoingRight = false;
			ch.isGoingLeft = true;
		}
		if((!input.isKeyDown(Input.KEY_RIGHT) && canMove)
				&&(!input.isKeyDown(Input.KEY_LEFT) && !ch.isFalling)){
			ch_body.m_linearVelocity.x = 0;
			ch.isGoingRight = false;
			ch.isGoingLeft = false;
			
		}
		
		// Simulation sur 0.1s dans notre box2D world
		world.step((float)delta/100, 100);

		// Update the screen and the model values
		ch.setCoordinatesFromBody(ch_body);
		for(int i = 0 ; i < spriteBodies.size() ; ++i){
			Body tempBody = spriteBodies.get(i);
			Sprite theSprite = (Sprite)tempBody.getUserData();
			theSprite.setCoordinatesFromBody(tempBody);
		}
	}
	@Override
	public void render(GameContainer arg0, org.newdawn.slick.Graphics arg1)
			throws SlickException {
		ch.draw();
		for(int i = 0 ; i < sprites.size() ; ++i){
			sprites.get(i).draw();
		}
	}
	@Override
	public void init(GameContainer arg0) throws SlickException {
		sprites = new ArrayList<Sprite>();
		spriteBodies = new ArrayList<Body>();
		
		this.createWorld();
		this.createLevel();
		this.addCharacter();
	}
}
