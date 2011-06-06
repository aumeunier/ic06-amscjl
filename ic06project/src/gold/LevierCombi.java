package gold;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import org.jbox2d.dynamics.Body;

public class LevierCombi extends Declencheur{
	private Timer timer;
	private ArrayList<ArrayList<Boolean> > combi;
	private ArrayList<LevierCombi> leviers;
	private ArrayList<ArrayList<Body> > relatedBody;

	public LevierCombi(int _x, int _y, int _w, int _h, ArrayList<ArrayList<Body> > b, ArrayList<ArrayList<Boolean> > test){
		super(_x, _y, _w, _h);		
		combi=test;
		relatedBody=b;
		leviers=null;
		this.setImage("levier-1.png");
		timer = new Timer(1000, new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				endOfTimer();
			}			
		});
	}
	
	public void SetListeLeviers(ArrayList<LevierCombi> lesLeviers){
		leviers=lesLeviers;
	}

	public void endOfTimer(){
		timer.stop();
	}
	
	
	public void activate() 
	{
		if(timer.isRunning()){
			return;
		}
		
		isActivated=!isActivated;
		
		//pour chaque combi possible, réafficher tous les bodies(ou caher les sources)
		for(int n=0;n<relatedBody.size();n++)
		{ 			
			ArrayList<Body> tempBodies = relatedBody.get(n);
			for (Body bx : tempBodies) 
			{			
				if((Sprite)(bx.getUserData()) instanceof Source) 	//doit etre caché
						((Sprite)(bx.getUserData())).Hidden(true);
				else													//doit etre affiché
					((Sprite)(bx.getUserData())).Hidden(false);
			}
		}
		
		//pour chaque combi possible, rechercher la bonne
		for(int n=0;n<combi.size();n++)
		{ 			
			ArrayList<Boolean> tempCombi=combi.get(n);
			Boolean testCombi=true;
			
			//je parcours les deux listes pour voir si la combi est bonne
			for(int i=0;i<leviers.size();i++){
				//si la combi est mauvaise
				if((leviers.get(i).isActivated && !tempCombi.get(i))||(!leviers.get(i).isActivated && tempCombi.get(i))){
					testCombi=false;
				}
			}
				
			//si la combi est bonne, on met les bodies qui lui sont reliés en caché
			if(testCombi)
			{
				ArrayList<Body> tempBodies = relatedBody.get(n);
				for (Body bx : tempBodies) {
					if((Sprite)(bx.getUserData()) instanceof Source) 	//doit etre affiché
						((Sprite)(bx.getUserData())).Hidden(false);
					else													//doit etre caché
					((Sprite)(bx.getUserData())).Hidden(true);
				}
				break;
			}
				
		}
		if (isActivated)
			this.setImage("levier-2.png");	
		else
			this.setImage("levier-1.png");
		timer.start();
	}
	
	public void desactivate(){};
	
}
