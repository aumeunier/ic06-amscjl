package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level2 extends Level {
	public Level2(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 2; // Do not forget to update that !!
		this.setBackgroundImage("6362779_s.jpg");

		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		
		
		createGround(100,440,200,50); // Sol 3
		createGround(100,390,10,50); // Sol 2
		createGround(100,200,200,190); // Sol 1/4
		createGround(290,390,10,50); // Sol 32
		createGround(300,200,200,75); // Sol 5
		createGround(300,325,200,110); // Sol 6
		createGround(500,200,10,150); // Sol 7
		createGround(500,350,10,85); // Sol 7 bis
		createGround(530,200,10,150); // Sol 8
		createGround(530,350,10,85); // Sol 8 bis
		createGround(570,425,250,10); // Sol 11
		createGround(630,390,190,35); // Sol 10
		createGround(540,320,90,70); // Sol 12
		createGround(540,200,90,70); // Sol 13
		createGround(730,200,90,50); // Sol 15
		createGround(730,320,90,10); // Sol 15
		createGround(0,400,40,10); // Sol 20 petite plateforme
		Wall w1 = createWall(100,190,410,50); // bois au dessus pour éviter le bug
		w1.setImage("newherbe2.png");
		w1.image = w1.image.getSubImage(0,15,410,50);
		Wall w2 = createWall(530,190,300,50); // bois au dessus pour éviter le bug
		w2.setImage("newherbe2.png");
		w2.image = w2.image.getSubImage(0,15,300,50);

		//source
		createSource(750,150,49,42,Power.PETIT);
		createSource(750,20,49,42,Power.REBOND);
		createSource(300,150,49,42,Power.NAGE);
		
		
		//transporteur
		createTransporter(790,420,10,42,300,280);
		createTransporter(300,280,10,42,760,285);
		createTransporter(790,270,10,42,120,400);
		createTransporter(120,400,5,42,750,440);
		createTransporter(790,340,10,42,600,440);
		createTransporter(545,250,10,42,10,330);


		Exit exit = createExit(600,400,25,25);


		//addIndication(200,100,"Tu peux maintenant voler!");
		
		//mur levier x
		Obstacle oMurLevier = new Obstacle(700,0,10,60);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(oMurLevierBody);
		
		Obstacle oMurLevier2 = new Obstacle(180,0,10,60);
		oMurLevier2.Hidden(true);
		sprites.add(oMurLevier2);
		Body oMurLevierBody2 = myState.addObstacle(oMurLevier2);
		b1.add(oMurLevierBody2);
		
		//mur levier y
		Obstacle oMurLevier3 = new Obstacle(180,0,10,60);
		sprites.add(oMurLevier3);
		Body oMurLevierBody3 = myState.addObstacle(oMurLevier3);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(oMurLevierBody3);
		
		//levier qui ouvre le mur
		createLevier(460,295,30,30,b1); //levier x
		createLevier(250,410,30,30,b2); //levier y
		//createBoutonPressoir(40,450,30,30,b2); //levier z
		
		//place l'eau
		SourceMortelle s = createSourceMortelle(0,Global.GAMEPLAYHEIGHT-60,50,50);
		s.setAnimation("waves.png", 300, 300);
		SourceMortelle s2 = createSourceMortelle(50,Global.GAMEPLAYHEIGHT-60,50,50);
		s2.setAnimation("waves2.png", 300, 300);
		
		
		//plateforme
		Wall w3 = createWall(0,60,200,20); 
		w3.setImage("newherbe2.png");
		w3.image = w3.image.getSubImage(0,15,410,50);
		
		Wall w4 = createWall(600,60,200,20); 
		w4.setImage("newherbe2.png");
		w4.image = w4.image.getSubImage(0,15,410,50);
		
		Wall w5 = createWall(380,100,40,10); 
		w5.setImage("newherbe2.png");
		w5.image = w5.image.getSubImage(0,15,410,50);
		
		createObstacle(420,10,10,90);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(600,0,0.75f);		
		this.character2 = addCharacterWithPoints(550,450,0.75f);	
		/*Body b = state.getBodyForUserData(this.character1);
		state.modifyBodySize(b, 0.5f, 0.5f);
		Body b2 = state.getBodyForUserData(this.character2);
		state.modifyBodySize(b2, 0.5f, 0.5f);
		*/
		
		this.setLevelForAllSprites();
	}
}
