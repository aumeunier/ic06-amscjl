package beta;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

public class LevierCombi extends Declencheur{
	
	private ArrayList<ArrayList<Boolean> > combi;
	private ArrayList<LevierCombi> leviers;
	private ArrayList<ArrayList<Body> > relatedBody;

	public LevierCombi(int _x, int _y, int _w, int _h, ArrayList<ArrayList<Body> > b, ArrayList<ArrayList<Boolean> > test){
		super(_x, _y, _w, _h);		
		combi=test;
		relatedBody=b;
		leviers=null;
		this.setImage("levier-1.png");
	}
	
	public void SetListeLeviers(ArrayList<LevierCombi> lesLeviers){
		leviers=lesLeviers;
	}
	
	
	
	public void activate() 
	{
		isActivated=!isActivated;
		System.out.println("fontion activate de levierCombi");
		
		//pour chaque combi possible, réafficher tous les bodies
		for(int n=0;n<relatedBody.size();n++)
		{ 			
			ArrayList<Body> tempBodies = relatedBody.get(n);
			for (Body bx : tempBodies) 
			{			
				((Sprite)(bx.getUserData())).Hidden(false);
			}
		}
		
		//pour chaque combi possible, rechercher la bonne
		for(int n=0;n<leviers.size();n++)
		{ 			
			ArrayList<Boolean> tempCombi=combi.get(n);
			Boolean testCombi=true;
			
			//je parcours les deux listes pour voir si la combi est bonne
			for(int i=0;i<leviers.size();i++){
				//si la combi est mauvaise
				if((leviers.get(i).isActivated && !tempCombi.get(i))||(!leviers.get(i).isActivated && tempCombi.get(i))){
					testCombi=false;
					System.out.println(leviers.get(i).isActivated );
					System.out.println(tempCombi.get(i));
				}
			}
				
			//si la combi est bonne, on met les bodies qui lui sont reliés en caché
			if(testCombi)
			{
				ArrayList<Body> tempBodies = relatedBody.get(n);
				for (Body bx : tempBodies) {			
					((Sprite)(bx.getUserData())).Hidden(true);
				}
				break;
			}
				
		}
		if (isActivated)
			this.setImage("levier-2.png");	
		else
			this.setImage("levier-1.png");
	}
	
	public void desactivate(){};
	
}
