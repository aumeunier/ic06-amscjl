package gold;

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
	public static void setim(String s){
		im=s;
	}
}
