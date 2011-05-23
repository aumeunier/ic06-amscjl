package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level4 extends Level {

	public Level4(GameplayState state, LevelSave model) {
		super(state,model);
		this.levelID = 4;
		//this.inTheDarkness = true;
		this.setBackgroundImage("6362779_s.jpg");
		
		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// place entree
		Sprite entree = new Sprite(10,44,32,46);
		entree.setImage("porte.png");
		entree.setLightSize(100);
		this.backgroundSprites.add(entree);
		
		// Sols
		createGround(0,90,200,20); 
		createGround(280,90,200,20);
		createGround(260,170,80,20);// 
		createGround(260,200,20,70);//
		createGround(380,110,20,80); 
		createGround(480,70,20,40); 
		createGround(480,50,160,20); 
		createGround(680,50,120,20); 
		createGround(550,120,250,20); 
		createGround(400,170,120,20); 
		createGround(500,190,180,20);
		createGround(720,190,80,20); 
		createGround(280,260,60,20); 
		createGround(260,280,480,20); 
		
		/*
		
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
		
		// Créé un bouton pressoir qui ouvre ce mur
		//createBoutonPressoir(85,185,30,18,oMurLevierBody);
		
		// Place un mur devant la sortie
		Obstacle oMurSortie = new Obstacle(Global.GAMEPLAYWIDTH-160,0,20,75);
		sprites.add(oMurSortie);
		Body oMurSortieBody = myState.addObstacle(oMurSortie);
		
		// Créé un levier qui ouvre ce mur
		//createLevier(Global.GAMEPLAYWIDTH-70,195,30,30,oMurSortieBody);
		
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
		*/
		
		//place Sortie
		createExit(Global.GAMEPLAYWIDTH-65,Global.GAMEPLAYHEIGHT-65-10,32,46);
		
		// Place bonus
		//createBonus(50,Global.GAMEPLAYHEIGHT-110,25,25);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,10,0.7f);		
		this.character2 = addCharacterWithPoints(Global.GAMEPLAYWIDTH-87-80,
				Global.GAMEPLAYHEIGHT-65-10,0.7f);	
		this.character1.setLightSize(this.character1.h*2);
		this.character2.setLightSize(this.character2.h*2);
	}

}
