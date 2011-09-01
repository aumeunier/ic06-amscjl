package version1.sprites.decor;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import version1.Global;
import version1.sprites.Sprite;

/**
 * The name of this class pretty much talks for itself. It is a block used for the ground in a level.
 * We usually use one single big image of which we take small blocks such that several adjacent blocks 
 * look adjacent. Also, a ground can be slippery such that if a character walks on it, it slips in a direction.
 *
 */
public class Ground extends Sprite{
	/** If the ground is slippery, the character won't be able to move freely */
	protected boolean slippery=false;
	/** If the ground is slippery, it can't be melted but then come back */
	protected boolean originalySlippery = false;
	/** If the ground is slippery it has a direction */
	protected boolean slipsLeft=false;
	protected static String im="sol-pourri-v56.png";
	public static final String IM1="sol-pourri-v56.png";
	public static final String IM2="sol-un-peu-mieux-v2.png";
	public static final String IM3="sol-niveau5.png";

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically implicit.)
     */
	public Ground(){
		super();
		setImage(im);

	}
	public Ground(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage(im);
	}
	
	@Override
	public void setImage(String filename){
		try {
			this.image = new Image(Global.PATH_IMAGES_RESSOURCES+filename);
			this.image = this.image.getSubImage((int)x,(int)y,(int)w,(int)h);
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	public static void setim(String s){
		im=s;
	}
	
	/**
	 * Set the ground as slippery.
	 * @param isSlippery Is the ground slippery ?
	 * @param isLeft Is the ground sliding to the left ?
	 */
	public void setSlippery(boolean isSlippery, boolean isLeft){
		slippery=isSlippery;
		slipsLeft = isLeft;
	}
	public void setOriginalySlippery(boolean isSlippery){
		originalySlippery = isSlippery;
	}
	/**
	 * Update the image of the ground if it gets slyppery (or if a slippery ground is melted by a player's fire power)
	 */
	public void setSlippery(boolean isSlippery){
		slippery=isSlippery;
		if(slippery){
			setImage("snow.png");
			shape=null;
			if(x==80){
				addPointToShape(80,70);
				addPointToShape(80,90);
				addPointToShape(550,105);
				addPointToShape(550,85);
				image = image.getSubImage(0,10,550,50);
			}
			else{
				addPointToShape(570,157);
				addPointToShape(570,172);
				addPointToShape(800,169);
				addPointToShape(800,154);
				image = image.getSubImage(0,10,230,70);
			}
		}
		else{
			setImage(IM2);
			shape=null;
			if(x==80){
				addPointToShape(80,70);
				addPointToShape(80,75);
				addPointToShape(550,90);
				addPointToShape(550,85);
				image = image.getSubImage(0,10,550,5);
			}
			else{
				addPointToShape(570,157);
				addPointToShape(570,162);
				addPointToShape(800,159);
				addPointToShape(800,154);
				image = image.getSubImage(0,10,230,5);
			}
		}
	}
	
	public boolean getSlippery(){
		return slippery;
	}
	public boolean getOriginalySlippery(){
		return originalySlippery;
	}
	
	/**
	 * @return whether the ground slips to the left
	 */
	public boolean getDirectionIsLeft(){
		return slipsLeft;
	}
}
