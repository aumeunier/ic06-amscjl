package beta;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Ground extends Sprite{
	protected boolean slippery=false;
	protected String sens=null;
	
	public Ground(){
		super();
		setImage("new-sol.png");

	}
	public Ground(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("new-sol.png");
	}
	
	@Override
	protected void setImage(String filename){
		try {
			this.image = new Image(Global.PATH_IMAGES_RESSOURCES+filename);
			this.image = this.image.getSubImage(x,250,w,h);
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
}
