package version1.sprites;

import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

import version1.Global;
import version1.data.levels.Level;
import version1.sprites.decor.InGameIndication;

/**
 * A Sprite represents an element from the scene. 
 * It holds basic properties like the Image, the rectangle, the shape and flags.
 * Classes which inherits from Sprite can be the Characters, interactive elements, decor elements, etc.
 */
public class Sprite {
	/** x, top left point of the bounding box of this element */
	protected int x; 
	/** y, top left point of the bounding box of this element */
	protected int y; 
	/** width of the bounding box of this element */
	protected int w; 
	/** heigh of the bounding box of this element */
	protected int h; 
	/** image to load on the element */
	protected Image image = null; 
	/** animation to load on the element */
	protected Animation animation = null;
	/** shape of the element (if not rectangular) */
	protected Shape shape = null; 
	/** whether the element should be destroyed */
	protected boolean shouldBeDestroy=false; 
	/** whether the element should be resized */
	protected boolean shouldChangeSize=false; 
	/** whether the element should rebond */
	protected boolean shouldRebond=false; 
	/** Filter to apply on the object */
	protected Color colorFilter = Color.white;
	/** whether the element should be hidden on the scene */
	protected boolean isHidden=false; 
	 /** whether the element shines in the darkness */
	protected boolean lightInDarkness=false;
	/** size of the light generated by the element in the darkness */
	protected int lightSize=50; 
	/** image to use as a filter for light */
	protected Image lightImage=Global.getImage(Global.DEFAULT_LIGHT_IMAGE); 
	/** level in which the sprite is encapsulated */
	protected Level level; 
	 /** the indication to display when passing over the sprite */
	protected InGameIndication indication= null;

	
	/////////////////////////////////////////////////////////
	////////////////////// CONSTRUCTOR //////////////////////
	/////////////////////////////////////////////////////////
    /**
     * Sole constructor.  (For invocation by subclass constructors, typically implicit.)
     */
	public Sprite(){
		this.x=0;
		this.y=0;
		this.w=40;
		this.h=40;
	}	
	/**
	 * Constructor to use in general. Create a sprite with a bouding box.
	 * @param _x The abscissa of the top left point
	 * @param _y The ordinate of the top left point
	 * @param _w The width of the object
	 * @param _h The height of the object
	 */
	public Sprite(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
	}

	
	/////////////////////////////////////////////////////////
	//////////////////////// SETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	/** Set the abscissa of the top left point */
	public void setX(int x){
		this.x = x;
	}	
	/** Set the ordinate of the top left point */
	public void setY(int y){
		this.y = y;
	}
	/** Set the width */
	public void setW(int w){
		this.w = w;
	}	
	/** Set the height */
	public void setH(int h){
		this.h = h;
	}	
	/** Set the Level object (the owned) in which this object is contained */
	public void setLevelContainer(Level lvl){
		this.level = lvl;
	}
	/** Update the abscissa and ordinate of the Sprite from a Box2D Body */
	public void setCoordinatesFromBody(Body b){
		this.x = (int)b.getPosition().x-this.w/2;
		this.y = Global.GAMEPLAYHEIGHT-(int)b.getPosition().y-this.h/2;
	}
	/** 
	 * Change the image to use for this object. The image is used to display the object on the level.
	 * @param filename The name of the image (with its extension but not its path)
	 */
	public void setImage(String filename){
		try {
			if (filename==null) 
				this.image=null;
			else 
				this.image = new Image(Global.PATH_IMAGES_RESSOURCES+filename);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Only take a small portion of the original image to display the object
	 */
	public void setCroppedImage(int x, int y, int w, int h){
		this.image = image.getSubImage(x, y, w, h);
	}
	/**
	 * Load and use a sprite sheet to display the image.
	 * @param filename Name of the spritesheet (with its extension but not its path)
	 * @param tw Width of a single image
	 * @param th Height of a single image
	 */
	public void setAnimation(String filename, int tw, int th){
		try {
			SpriteSheet ss = new SpriteSheet(Global.PATH_SPRITES_RESSOURCES+filename,tw,th);
			this.animation = new Animation(ss,100);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public void setTexture(String filename){
		// TODO: 
		//this.image.setTexture(new Texture());
	}
	/**
	 * Load and use a color filter when displaying the image. It affects the colors' rendering
	 * @param _colorFilter The Color to use as a filter. A white color doesn't change anything.
	 */
	public void setFilter(Color _colorFilter){
		this.colorFilter = _colorFilter;
	}
	/**
	 * Use a complex polygon for the shape of this object. Add a point to that polygon.
	 */
	public void addPointToShape(int x, int y){
		if(shape==null){
			shape = new Polygon();
		}
		if(shape instanceof Polygon){
			((Polygon)shape).addPoint(x, y);			
		}
	}
	/**
	 * Use a circle as shape of the object. Keep the same x/y/w/h. Circle will be centered on the center of the box.
	 */
	public void setCircleShapeO(float radius){
		shape = new Circle(x+w/2,y+h/2,radius);
	}
	/**
	 * The sprite should be destroyed soon. Call this method when you want to remove the object from the screen 
	 * at the next iteration of the game loop.
	 */
	public void setShouldBeDestroy(){
		shouldBeDestroy=true;
	}
	/**
	 * Add an indication to this sprite. When a player collides with this sprite, the indication is displayed at the 
	 * bottom of the screen.
	 * @param w Width of the message
	 * @param h Heigh of the message
	 * @param msg Text to display
	 */
	public void setIndication(int w, int h, String msg){
		indication = new InGameIndication(w,h,msg);
		if(this.level != null){
			this.level.addIndication(indication);
		}
	}
	/**
	 * Add an indication to this sprite. 
	 * @param indic The indication to use and display when a collision with a player is detected.
	 */
	public void setIndication(InGameIndication indic){
		indication=indic;
	}
	/**
	 * Change the hidden property of the object. An hidden object isn't displayed but is still on the scene.
	 * @param hideOrNot Yes if you want to hide the object
	 */
	public void setHidden(boolean flag){
		isHidden=flag;
	}
	/**
	 * Change the size of the light generated by an object. This is useful in a level that is in the darkness.
	 * The light generated will be a circle centered on the center of this sprite.
	 * @param _lightSize The radius, in pixels
	 */
	public void setLightSize(int _lightSize){
		this.lightInDarkness = true;
		this.lightSize = _lightSize;
		this.lightImage = Global.getImage(Global.DEFAULT_LIGHT_IMAGE).getScaledCopy(lightSize, lightSize);
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////// GETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	public int X(){
		return x;
	}	
	public int Y(){
		return y;
	}	
	public int W(){
		return w;
	}	
	public int H(){
		return h;
	}
	public Image getImage(){
		return image;
	}
	public InGameIndication getIndication(){
		return indication;
	}
	public boolean getShouldBeDestroy(){
		return shouldBeDestroy;
	}
	public boolean isHidden(){
		return isHidden;
	}
	public boolean isLightInDarkness(){
		return lightInDarkness;
	}
	public boolean getShouldBeDestroyed(){
		return shouldBeDestroy;
	}
	public boolean getShouldChangeSize(){
		return shouldChangeSize;
	}
	public boolean getShouldRebond(){
		return shouldRebond;
	}
	public void setShouldBeDestroyed(boolean flag){
		shouldBeDestroy = flag;
	}
	public void setShouldChangeSize(boolean flag){
		shouldChangeSize = flag;
	}
	public void setShouldRebond(boolean flag){
		shouldRebond = flag;
	}


	/////////////////////////////////////////////////////////
	/////////////////////// COLLISIONS //////////////////////
	///////////////////////////////////////////////////////// 
	/** 
	 *	Check for a collision between two Sprite
	 *  @return whether the two Sprite are colliding
	*/
	public boolean rectCollideWithOther(Sprite other){
		if((((this.x >= other.x) && (this.x <= (other.x+other.w))) 
				|| (((this.x + this.w) >= other.x) && ((this.x + this.w) <= (other.x+other.w))))
				&& (((this.y >= other.y) && (this.y <= (other.y+other.h))) 
						|| (((this.y + this.h) >= other.y) && ((this.y + this.h) <= (other.y+other.h))))){
			return true;
		}
		else{
			return false;
		}
	}
	/** Activate the indication, which will be displayed at the bottom of the screen*/
	public void activateIndication(){
		if(indication!=null){
			indication.activate();
		}
	}


	/////////////////////////////////////////////////////////
	//////////////////////// DRAWING ////////////////////////
	/////////////////////////////////////////////////////////
	/**
	 * Standard drawing for this engine
	 */
	public void draw(GameContainer container, StateBasedGame game, Graphics g){
		if(animation!=null){
			animation.draw(x, y, w, h, colorFilter);
		}
		else if(shape!=null){
			g.texture(shape, image, true);
		}
		else if(image!=null){
			image.draw(x, y, w, h);
		}
	}
	/** Method called when we are in alpha mode */
	public void drawLight(Graphics g, boolean alphaMode){
		lightImage.draw(x+w/2-lightSize/2, y+w/2-lightSize/2, new Color(0.0f,0.0f,0.0f,0.5f));
	}

}
