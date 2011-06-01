package gold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.dynamics.Body;

public class Levier extends Declencheur {
	private Timer timer;
	protected ArrayList<Body> relatedBody;

	public Levier(int _x, int _y, int _w, int _h, ArrayList<Body> b){
		super(_x, _y, _w, _h);
		relatedBody=b;
		this.setImage("levier-1.png");
		timer = new Timer(500, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				endOfTimer();
			}			
		});
	}

	@Override
	public void activate() {
		if(timer.isRunning()){
			return;
		}
		isActivated=!isActivated;
		for (Body bx : relatedBody) {
			
			if(isActivated)
			{
				((Sprite)(bx.getUserData())).Hidden(!((Sprite)(bx.getUserData())).isHidden());
				this.setImage("levier-2.png");	
			}
			else{
				((Sprite)(bx.getUserData())).Hidden(!((Sprite)(bx.getUserData())).isHidden());
				this.setImage("levier-1.png");
			}			 
		}		
		timer.start();
	}
	public void desactivate(){
		
	}
	public void endOfTimer(){
		System.out.println("test4");
		timer.stop();
	}
}
