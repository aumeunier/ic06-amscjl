package version1.sprites.interaction.trigger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.dynamics.Body;

import version1.sprites.Sprite;

public class Lever extends Trigger {
	/** To avoid double enabling with box2d contacts, we use a timer. While it is running, this Lever can't be enabled */ 
	private Timer timer;
	/** The elements the Lever can change the state (hidden / not hidden) of */
	protected ArrayList<Body> relatedBodies;

	public Lever(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h);
		relatedBodies=b;
		this.setImage("levier-1.png");
		timer = new Timer(500, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				endOfTimer();
			}			
		});
	}

	public void enable() { 
		// The Lever has been enabled a short while ago, we don't want to enable it again
		if(timer.isRunning()){
			return;
		}
		enabled=!enabled;
		
		// Modifies the state of each of the elements this Lever can interact with 
		for (Body bx : relatedBodies) {
			if(enabled)	{
				((Sprite)(bx.getUserData())).setHidden(!((Sprite)(bx.getUserData())).isHidden());
				this.setImage("levier-2.png");	
			}
			else {
				((Sprite)(bx.getUserData())).setHidden(!((Sprite)(bx.getUserData())).isHidden());
				this.setImage("levier-1.png");
			}			 
		}		
		timer.start();
	}
	public void disable(){}
	public void endOfTimer(){
		timer.stop();
	}
}
