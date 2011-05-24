package beta;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Ground extends Sprite{
	protected boolean slippery=false;
	protected String sens=null;
	protected static String im="sol-pourri-v56.png";
	protected static final String IM1="sol-pourri-v56.png";
	protected static final String IM2="sol-un-peu-mieux-v2.png";
	
	public Ground(){
		super();
		setImage(im);

	}
	public Ground(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage(im);
	}
	
	@Override
	protected void setImage(String filename){
		try {
			this.image = new Image(Global.PATH_IMAGES_RESSOURCES+filename);
			this.image = this.image.getSubImage(x,y,w,h);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSlippery(boolean glisse, String cote){
		slippery=glisse;
		sens = cote;
	}
	
	public void setSlippery(boolean glisse){
		slippery=glisse;
	}
	
	public boolean getSlippery(){
		return slippery;
	}
	public static void setim(String s){
		im=s;
	}
}
