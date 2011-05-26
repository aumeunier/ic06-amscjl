package beta;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

public class InGameIndication {
	private String text;
	private final static String DEFAULT_IMAGE = "scroll_large.png";
	private Image backgroundImage;
	private TextField textField;
	private int x,y,w,h;

	public InGameIndication(int _x, int _y, int _w, int _h, String _text){
		backgroundImage = Global.setImage(DEFAULT_IMAGE);
		text=_text;
		x=_x;y=_y;w=_w;h=_h;
	}
	protected String getTexte(){
		return text;
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {		
		if(textField == null){
			textField = new TextField(container, container.getDefaultFont(),x,y,w,h);
			textField.setBackgroundColor(new Color(0.0f,0.0f,0.0f,0.0f));
			textField.setBorderColor(new Color(0.0f,0.0f,0.0f,0.0f));
			textField.setTextColor(Color.black);
			textField.setAcceptingInput(false);
			textField.setText(text);
		}

		backgroundImage.draw((int)(x-w*0.25),(int)(y-h*0.5),(int)(w*1.5),(int)(h*1.5));
		if(textField != null){
			textField.render(container, g);
		}
	}
}
