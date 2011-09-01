package version1.sprites.interaction.trigger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.dynamics.Body;

import version1.sprites.Sprite;
import version1.sprites.interaction.sources.Source;


/**
 * A LeverCombination takes part of a list of LeverCombination which can enable or disable elements.
 * It is necessary to have a specific combination to have an effect on some elements.
 * There can be multiple combinations and thus different effects on different bodies.
 *
 */
public class LeverCombination extends Trigger{
	/** To avoid double enabling with box2d contacts, we use a timer. While it is running, this Lever can't be enabled */ 
	private Timer timer;
	/** We can have several possible combinations with different effects */
	private ArrayList<ArrayList<Boolean> > possibleCombinations;
	/** Each LeverCombination is related to other LeverCombination*/
	private ArrayList<LeverCombination> levers;
	/** The elements the LeverCombination can change the state (hidden / not hidden) of */
	private ArrayList<ArrayList<Body> > relatedBodies;

	public LeverCombination(int _x, int _y, int _w, int _h, ArrayList<ArrayList<Body> > b, ArrayList<ArrayList<Boolean> > test){
		super(_x, _y, _w, _h);		
		this.possibleCombinations = test;
		this.relatedBodies = b;
		this.levers = null;
		this.setImage("levier-1.png");
		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				endOfTimer();
			}			
		});
	}
	public void SetLeverList(ArrayList<LeverCombination> levers){
		this.levers = levers;
	}

	public void enable() {
		if(timer.isRunning()){
			return;
		}
		
		enabled=!enabled;
		
		// For show every related body
		for(int n=0;n<relatedBodies.size();n++)
		{ 			
			ArrayList<Body> tempBodies = relatedBodies.get(n);
			for (Body bx : tempBodies) {			
				if((Sprite)(bx.getUserData()) instanceof Source) {
						((Sprite)(bx.getUserData())).setHidden(true);
				}
				else {
					((Sprite)(bx.getUserData())).setHidden(false);
				}
			}
		}
		
		// For every possible combination we search for the current combination
		for(int n=0;n<possibleCombinations.size();n++)
		{ 			
			ArrayList<Boolean> tempCombi=possibleCombinations.get(n);
			Boolean testCombi=true;
			
			// Is it the good one ?
			for(int i=0;i<levers.size();i++){
				if((levers.get(i).isEnabled() && !tempCombi.get(i))||(!levers.get(i).isEnabled() && tempCombi.get(i))){
					testCombi=false;
				}
			}
				
			// If it is the good combination we hide the related bodies
			if(testCombi) {
				ArrayList<Body> tempBodies = relatedBodies.get(n);
				for (Body bx : tempBodies) {
					if((Sprite)(bx.getUserData()) instanceof Source) {
						((Sprite)(bx.getUserData())).setHidden(false);
					}
					else {
					((Sprite)(bx.getUserData())).setHidden(true);
					}
				}
				break;
			}
				
		}
		if (enabled) { this.setImage("levier-2.png"); }
		else { this.setImage("levier-1.png"); }
		timer.start();
	}
	public void disable(){};
	public void endOfTimer(){
		timer.stop();
	}
}
