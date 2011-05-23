package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level1 extends Level {
	public Level1(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 1; // Do not forget to update that !!
		this.setBackgroundImage("6362779_s.jpg");

		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// Flowers
		ArrayList<Vec2> temp = new ArrayList<Vec2>();
		temp.add(new Vec2(-100,50));
		temp.add(new Vec2(+100,50));
		temp.add(new Vec2(-100,+75));
		Obstacle o = createObstacleWithPoints(600,200,200,150,temp);
		o.setImage("fleur9_mur.png");
		
		// Sols
		createGround(0,Global.GAMEPLAYHEIGHT-85,80,75); // Sol 1
		createGround(310,Global.GAMEPLAYHEIGHT-85,100,75); // Sol 2 (apres l'eau)
		createGround(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-45,80,35); // Sol 4 (en dessous perso 2)
		//*
		ArrayList<Vec2> sol3Points = new ArrayList<Vec2>();
		sol3Points.add(new Vec2(-40,-27));
		sol3Points.add(new Vec2(40,-27));
		sol3Points.add(new Vec2(-40,27));
		Wall sol3 = createWallWithPoints(410,Global.GAMEPLAYHEIGHT-65,80,55,sol3Points); // Sol 3
		sol3.addPointToShape(410,Global.GAMEPLAYHEIGHT-10);
		sol3.addPointToShape(410+80,Global.GAMEPLAYHEIGHT-10);
		sol3.addPointToShape(410,Global.GAMEPLAYHEIGHT-65);
		/*/
		//Wall sol3 = createWall(410,Global.GAMEPLAYHEIGHT-65,80,55);
		//*/
		
		
		//Plateform
		Wall o1 = createWall(10,75,(int)(148/1.3),(int)(42/1.3));
		o1.setImage("fleur2.png");
		Wall o2 = createWall(75,200,(int)(148/1.3),(int)(42/1.3));
		o2.setImage("fleur2.png");
		
		//la cage
		createWall(300,0,10,85);
		createWall(300,85,70,10);
		createWall(430,85,75,10);
		createWall(495,0,10,85);
		
		//l'arrivée
		createWall(Global.GAMEPLAYWIDTH-160,75,150,10);

		// Place un mur pour séparer les deux personnages
		createObstacle(Global.GAMEPLAYWIDTH-200,Global.GAMEPLAYHEIGHT-220,40,210);
		
		// Place un mur pour empecher l'acces au levier
		Obstacle oMurLevier = new Obstacle(Global.GAMEPLAYWIDTH-130,85,20,135);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(oMurLevierBody);
		
		// Créé un bouton pressoir qui ouvre ce mur
		createBoutonPressoir(85,185,30,18,b1);
		
		// Place un mur devant la sortie
		Obstacle oMurSortie = new Obstacle(Global.GAMEPLAYWIDTH-160,0,20,75);
		sprites.add(oMurSortie);
		Body oMurSortieBody = myState.addObstacle(oMurSortie);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(oMurSortieBody);
		
		// Créé un levier qui ouvre ce mur
		createLevier(Global.GAMEPLAYWIDTH-70,195,30,30,b2);
		
		// Place l'eau
		SourceMortelle s = createSourceMortelle(80,Global.GAMEPLAYHEIGHT-60,50,50);
		s.setAnimation("waves.png", 300, 300);
		SourceMortelle s2 = createSourceMortelle(130,Global.GAMEPLAYHEIGHT-60,50,50);
		s2.setAnimation("waves2.png", 300, 300);
		SourceMortelle s3 = createSourceMortelle(180,Global.GAMEPLAYHEIGHT-60,50,50);
		s3.setAnimation("waves.png", 300, 300);
		SourceMortelle s4 = createSourceMortelle(230,Global.GAMEPLAYHEIGHT-60,50,50);
		s4.setAnimation("waves2.png", 300, 300);
		SourceMortelle s5 = createSourceMortelle(280,Global.GAMEPLAYHEIGHT-60,50,50);
		s5.setAnimation("waves.png", 300, 300);
		
		// Place sources
		createSource(500,Global.GAMEPLAYHEIGHT-42,49,42,Power.FLYING);
		createSource(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-87,49,42,Power.INTANGIBLE);
		
		//place SOrtie
		Exit exit = createExit(Global.GAMEPLAYWIDTH-65,10,45,65);
		
		//place bonus
		Bonus bonus1 = createBonus(50,Global.GAMEPLAYHEIGHT-110,25,25);
		
		// Place the first character
		//*
		this.character1 = addCharacterWithPoints(330,Global.GAMEPLAYHEIGHT-65-76,1.0f);		
		this.character2 = addCharacterWithPoints(Global.GAMEPLAYWIDTH-87-80,
				Global.GAMEPLAYHEIGHT-65-10,1.0f);		
	}
}
