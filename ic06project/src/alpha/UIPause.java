package alpha;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIPause implements UIInterface {
	private final int PAUSE_HEIGHT = 300;
	protected Image menuBackgroundImage;
	
	public UIPause(){
		this.menuBackgroundImage = Global.setImage("wall2.jpg");
	}
	
	@Override
	public void render(Graphics g) {
		//this.menuBackgroundImage.draw((Global.WINDOW_HEIGHT-PAUSE_HEIGHT)/2);
		
	}

}
