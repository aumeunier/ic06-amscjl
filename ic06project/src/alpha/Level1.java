package alpha;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level1 extends Level {
	public Level1(GameplayState state, LevelSave model){	
		super(state,model);
		this.setBackgroundImage("blur15.jpg");
		
		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(0,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH-10,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,0,Global.GAMEPLAYWIDTH,10);
		
		
		// Flower
		ArrayList<Vec2> temp = new ArrayList<Vec2>();
		temp.add(new Vec2(-100,50));
		temp.add(new Vec2(+100,50));
		temp.add(new Vec2(-100,+75));
		Obstacle o = createObstacleWithPoints(600,200,200,150,temp);
		o.setImage("fleur9_mur.png");
		
		createWall(10,Global.GAMEPLAYHEIGHT-85,70,75);
		createWall(310,Global.GAMEPLAYHEIGHT-85,100,75);
		createWall(410,Global.GAMEPLAYHEIGHT-65,80,55);
		createWall(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-35,80,25);
		
		//Plateform
		Wall o1 = createWall(10,75,(int)(148/1.3),(int)(42/1.3));
		o1.setImage("fleur2.png");
		Wall o2 = createWall(75,200,(int)(148/1.3),(int)(42/1.3));
		o2.setImage("fleur2.png");
		
		//la cage
		Wall o4 = createWall(300,10,10,75);
		Wall o5 = createWall(300,85,70,10);
		Wall o6 = createWall(430,85,75,10);
		Wall o7 = createWall(495,10,10,75);
		
		//l'arrivée
		Wall o8 = createWall(Global.GAMEPLAYWIDTH-160,75,150,10);

		// Place un mur pour séparer les deux personnages
		Obstacle oMur = createObstacle(Global.GAMEPLAYWIDTH-250,Global.GAMEPLAYHEIGHT-120,40,110);
		
		// Place un mur pour empecher l'acces au levier
		Obstacle oMurLevier = new Obstacle(Global.GAMEPLAYWIDTH-130,85,20,135);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		
		// Créé un bouton pressoir qui ouvre ce mur
		BoutonPressoir bouton = createBoutonPressoir(85,185,30,15,oMurLevierBody);
		
		// Place un mur devant la sortie
		Obstacle oMurSortie = new Obstacle(Global.GAMEPLAYWIDTH-160,10,20,65);
		sprites.add(oMurSortie);
		Body oMurSortieBody = myState.addObstacle(oMurSortie);
		
		// Créé un levier qui ouvre ce mur
		Levier levier = createLevier(Global.GAMEPLAYWIDTH-70,210,30,15,oMurSortieBody);
		
		
		// Place sources
		SourceMortelle s = createSourceMortelle(80,Global.GAMEPLAYHEIGHT-60,230,50);
		s.setImage("blur9.jpg");
		createSource(500,Global.GAMEPLAYHEIGHT-20,20,20,Power.FLYING);
		createSource(Global.GAMEPLAYWIDTH-180,Global.GAMEPLAYHEIGHT-30,20,20,Power.INTANGIBLE);
		
		// Place the first character
		this.character1 = addCharacter(440,Global.GAMEPLAYHEIGHT-90);		
		this.character2 = addCharacter(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-90);
	}
}
