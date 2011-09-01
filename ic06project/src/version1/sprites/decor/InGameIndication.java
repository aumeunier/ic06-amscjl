package version1.sprites.decor;

import java.util.Timer;
import java.util.TimerTask;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import version1.Global;


/**
 * An InGameIndication is used to display some text during the game in order to guide players.
 * It is displayer on a scroll background and at the bottom of th screen such as not to hide the game area.
 * It is enabled by collision between a player and the bounding box of the indication.
 * It is desabled after some time.
 *
 */
public class InGameIndication {
	private final static String DEFAULT_IMAGE = "scroll_large.png";
	/** Duration of the indication display */
	private final static int INDICATION_DURATION = 5000;
	private String text;
	private Image backgroundImage;
	/** Textfield used to display the text of the indication in most cases (not in dark environment). */
	private TextField textField;
	/** Textfield used for dark levels. The alpha mode indeed disabled the possibility to use usual textfields */
	private mdes.slick.sui.TextField suiTextField;
	private int x,y,w,h;
	/** An indication can only be activated once */
	private boolean activatedBefore = false;
	/** Whether the indication is currently activated */
	private boolean activated = false;
	/** Timer used to disable the indication after some time */
	protected Timer timer = null;

	public InGameIndication(int _w, int _h, String _text){
		backgroundImage = Global.getImage(DEFAULT_IMAGE);
		text=_text;
		x=Global.WINDOW_WIDTH/2-_w/2;
		y=Global.WINDOW_HEIGHT-_h*2/3;
		w=_w;h=_h;
		suiTextField = new mdes.slick.sui.TextField(_text);
		suiTextField.setBounds(x, y, w, 60);
	}
	protected String getText(){
		return text;
	}
	
	public void desactivate(){
		activated = false;
	}
	/**
	 * A InGameIndication can be activated and thus displayed. It will be removed after 5s.
	 */
	public void activate(){
		if(!activatedBefore){
			activated = true;
			activatedBefore = true;
			if(this.timer == null){
				this.timer = new Timer("indication");
				timer.schedule(new TimerTask(){
					@Override
					public void run() {
						desactivate();
					}			
				}, INDICATION_DURATION);
			}
			else {
				this.timer.cancel();
				this.timer = new Timer("indication");
				timer.schedule(new TimerTask(){
					@Override
					public void run() {
						desactivate();
					}			
				}, INDICATION_DURATION);
			}
		}
	}

	public void render(GameContainer container, StateBasedGame game, Graphics g) {		
		if(!activated){
			return;
		}
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
	public void renderAlpha(GameContainer container, StateBasedGame game, Graphics g) {	
		if(!activated){
			return;
		}
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
			suiTextField.render(container,g);
		}
	}
}
