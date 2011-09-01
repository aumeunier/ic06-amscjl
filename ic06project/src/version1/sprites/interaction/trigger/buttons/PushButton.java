package version1.sprites.interaction.trigger.buttons;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import version1.sprites.Sprite;
import version1.sprites.alive.Creature;
import version1.sprites.interaction.trigger.Trigger;

/**
 * A Button is used to interact with various elements in a level.
 * A Button is considered activated when a Character is stepping on it.
 * Different buttons have different effects: switch open/closed an Obstacle, charge a missile, 
 * need an heavy man to be activated.
 * A standard push button hide and display the elements it is related to
 *
 */
public class PushButton extends Trigger {
	/** Counts the number of Buttons in use */
	static private int buttonCount=0; 
	/** id of this instance of Button */
	private int buttonID;
	
	/** Bodies which state are modified by the Button */
	protected ArrayList<Body> relatedBodies; 
	/** How much weight is required to active the Button */
	protected int weight; 
	/** Whether the button is still active after the Character leaves it */
	protected boolean stayActive = false; 
	
	/**
	 * Constructor for a Button
	 * @param bodies The bodies related to that Button
	 * @param w The weight needed to press that Button
	 */
	public PushButton(int _x, int _y, int _w, int _h, ArrayList<Body> bodies, int weight){
		super(_x, _y, _w, _h);
		this.relatedBodies=bodies;
		this.weight=weight;
		this.buttonID = ++buttonCount;
		this.updateImage();
	}
	
	// Getters
	/** @return the weight needed to push the button */
	public int getWeight(){
		return weight;
	}
	public int getButtonID(){
		return buttonID;
	}
	
	// Setters
	/** @param active if true, the button will stay pushed after the player moves away */
	public void setStayActive(boolean active){
		stayActive=active;
	}

	
	/** Check if we need to change the state of the Button */
	public void check(){
		/* Activate the button if:
			- the required weight is 1 and at least one character is on it
			- the required weight is 2 and both players are on it or one fat player is on it 
			- the required weight is 3 and both players are on it and at least one player is fat
			- the required weight is 4 and both players are fat and on the button
		*/
		Creature character1 = level.getFirstCharacter();
		Creature character2 = level.getSecondCharacter();
		if((weight==1 && (character1.getBouton()==buttonID || character2.getBouton()==buttonID))
				|| (weight==2 && (character1.getBouton()==buttonID && character2.getBouton()==buttonID)
						|| (character1.getBouton()==buttonID && character1.isFat())
						|| (character2.getBouton()==buttonID && character2.isFat()))
				|| (weight==3 && (character1.getBouton()==buttonID && character2.getBouton()==buttonID)
						&& (character1.isFat() || character2.isFat()))
				|| (weight==4 && (character1.getBouton()==buttonID && character2.getBouton()==buttonID)
						&& (character1.isFat() && character2.isFat()))
			){
			enable();			
		}	
		/* Desactivate the button if it is not set to stay pushed when the players leave the button */
		else if(!stayActive){
			disable();			
		}		
	}
	
	/** Activate the Button to hide all the bodies its related to */
	public void enable(){
		this.setEnabled(true);
		for (Body bx : relatedBodies) {
			((Sprite)(bx.getUserData())).setHidden(true);
		}
		this.updateImage();
	}
	
	/** Desactivate the Button to display all the bodies its related to */
	public void disable(){
		this.setEnabled(false);
		for (Body bx : relatedBodies) {
			((Sprite)(bx.getUserData())).setHidden(false);
		}
		this.updateImage();
	}
	
	/** Update the image of the button depending on its state and its weight */
	protected void updateImage(){
		if(this.isEnabled()){
			switch(weight){
			case 1:this.setImage("Boutonactif1.png");break;
			case 2:this.setImage("Boutonactif2.png");break;
			case 3:this.setImage("Boutonactif3.png");break;
			}					
		}
		else {
			switch(weight){
			case 1:this.setImage("Bouton1.png");break;
			case 2:this.setImage("Bouton2.png");break;
			case 3:this.setImage("Bouton3.png");break;
			}			
		}
	}

}
