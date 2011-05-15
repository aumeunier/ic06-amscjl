package alpha;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level1 extends Level {
	public Level1(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 1;
		this.setBackgroundImage("blur15.jpg");
		
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
		
		Wall sol1 = createWall(0,Global.GAMEPLAYHEIGHT-85,80,75); // Sol 1
		Wall sol2 = createWall(310,Global.GAMEPLAYHEIGHT-85,100,75); // Sol 2 (apres l'eau)
		//*
		ArrayList<Vec2> sol3Points = new ArrayList<Vec2>();
		sol3Points.add(new Vec2(-40,-27));
		sol3Points.add(new Vec2(40,-27));
		sol3Points.add(new Vec2(-40,27));
		Wall sol3 = createWallWithPoints(410,Global.GAMEPLAYHEIGHT-65,80,55,sol3Points); // Sol 3
		/*/
		Wall sol3 = createWall(410,Global.GAMEPLAYHEIGHT-65,80,55); // Sol 3
		//*/
		sol3.addPointToShape(410,Global.GAMEPLAYHEIGHT-10);
		sol3.addPointToShape(410+80,Global.GAMEPLAYHEIGHT-10);
		sol3.addPointToShape(410,Global.GAMEPLAYHEIGHT-65);
		Wall sol4 = createWall(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-35,80,25); // Sol 4 (en dessous perso 2)
		
		//Plateform
		Wall o1 = createWall(10,75,(int)(148/1.3),(int)(42/1.3));
		o1.setImage("fleur2.png");
		Wall o2 = createWall(75,200,(int)(148/1.3),(int)(42/1.3));
		o2.setImage("fleur2.png");
		
		//la cage
		Wall o4 = createWall(300,0,10,85);
		Wall o5 = createWall(300,85,70,10);
		Wall o6 = createWall(430,85,75,10);
		Wall o7 = createWall(495,0,10,85);
		
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
		Obstacle oMurSortie = new Obstacle(Global.GAMEPLAYWIDTH-160,0,20,75);
		sprites.add(oMurSortie);
		Body oMurSortieBody = myState.addObstacle(oMurSortie);
		
		// Créé un levier qui ouvre ce mur
		Levier levier = createLevier(Global.GAMEPLAYWIDTH-70,210,30,15,oMurSortieBody);
		
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
		createSource(500,Global.GAMEPLAYHEIGHT-20,20,20,Power.FLYING);
		createSource(Global.GAMEPLAYWIDTH-180,Global.GAMEPLAYHEIGHT-30,20,20,Power.INTANGIBLE);
		
		//place SOrtie
		Exit exit = createExit(755,10,45,65);
		exit.setImage("porte.png");
		
		//place bonus
		Bonus bonus1 = createBonus(50,Global.GAMEPLAYHEIGHT-150,25,25);
		bonus1.setImage("cerise_rouge.png");
		
		// Place the first character
		this.character1 = addCharacter(440,Global.GAMEPLAYHEIGHT-Character.CHAR_H-40);		
		this.character2 = addCharacter(Global.GAMEPLAYWIDTH-Character.CHAR_W-10,
				Global.GAMEPLAYHEIGHT-Character.CHAR_H-40);
	}
}
