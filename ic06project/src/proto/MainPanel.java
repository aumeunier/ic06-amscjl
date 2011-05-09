package proto;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.jbox2d.collision.AABB;
import org.jbox2d.collision.MassData;
import org.jbox2d.collision.PolygonDef;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class MainPanel extends JPanel {
	final static int CH1_INIT_X = 50;
	final static int CH1_INIT_Y = Global.GAMEHEIGHT-50;
	private Character ch;
	private World world;
	private Timer timer;
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
		setPreferredSize(new Dimension(Global.GAMEWIDTH, Global.GAMEHEIGHT));
		this.setSize(new Dimension(Global.GAMEWIDTH, Global.GAMEHEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);

		sprites = new ArrayList<Sprite>();
		spriteBodies = new ArrayList<Body>();
		
		this.createWorld();
		this.createLevel();
		this.addCharacter();
		
		// Add keyboard events
		// Create an anonymous class (new KeyAdapter() { declarations habituelles de class})
		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP  && !ch.isFalling){
					ch_body.setLinearVelocity(new Vec2(0,20));
					ch.isFalling = true;
					
					
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN){
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !ch.isFalling){
					ch_body.setLinearVelocity(new Vec2(20,0));
					ch.isGoingRight = true;
					ch.isGoingLeft = false;
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT && !ch.isFalling){
					ch_body.setLinearVelocity(new Vec2(-20,0));
					ch.isGoingRight = false;
					ch.isGoingLeft = true;
				}
			
			}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_UP){
					
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN){
					
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT && !ch.isFalling){
					ch_body.setLinearVelocity(new Vec2(0,0));
					ch.isGoingRight = false;
					
				}
				else if (e.getKeyCode() == KeyEvent.VK_LEFT && !ch.isFalling){
					ch_body.setLinearVelocity(new Vec2(0,0));
					ch.isGoingLeft = false;
					
				}
				else if (e.getKeyCode() == KeyEvent.VK_I){
					ch.canGoThroughObstacles = !ch.canGoThroughObstacles;
				}
			}
		});
		
		timer = new Timer(20, new TimerAction());
		timer.start();
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
		createObstacle(200,CH1_INIT_Y-50,50,100);
	}
	private void createWorld(){
		// Create the b2world
		Vec2 v = new Vec2(0.0f,-10.0f);
		AABB aabb = new AABB(new Vec2(0.0f,0.0f), new Vec2((float)Global.GAMEWIDTH,(float)Global.GAMEHEIGHT));
		world = new World(aabb,v,false);
		//world.setContactListener(new MyContactListener());
		world.setContactFilter(new MyContactFilter());
	}
	// Parameters: (x,y) position du mur, w largeur, h longueur (dans Java Swing repere)
	private void createWall(int x, int y, int w, int h){
		
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
		spriteBodies.add(newBody);
	}
	// Parameters: (x,y) position du mur, w largeur, h longueur (dans Java Swing repere)
	private void createObstacle(int x, int y, int w, int h){
		
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
	}
	private void addCharacter(){
		// Create a new character and add it to the panel
		ch = new Character();
		ch.x = CH1_INIT_X;
		ch.y = CH1_INIT_Y;
		this.add(ch);
		
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
	}

	class TimerAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			// Simulation sur 0.1s dans notre box2D world
			world.step(0.1f, 100);

			// Update the screen and the model values
			ch.setCoordinatesFromBody(ch_body);
			for(int i = 0 ; i < spriteBodies.size() ; ++i){
				Body tempBody = spriteBodies.get(i);
				Sprite theSprite = (Sprite)tempBody.getUserData();
				theSprite.setCoordinatesFromBody(tempBody);
			}
			if(ch.y <= Global.GAMEHEIGHT && ch.y >= Global.GAMEHEIGHT-100){
				ch.isFalling = false;
			}
			
			// Print all the components
			repaint();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ch.draw(g);
		for(int i = 0 ; i < sprites.size() ; ++i){
			sprites.get(i).draw(g);
		}
	}
}
