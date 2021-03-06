package gold;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level1 extends Level {
	public Level1(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 1; // Do not forget to update that !!
		this.setBackgroundImage("6362779_s.jpg");
		Ground.setim(Ground.IM2);

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
		
		
		//herbe li�s aux sols 1 2 et 3
		Wall w1 = createWall(0,Global.GAMEPLAYHEIGHT-90,80,50); // bois au dessus pour �viter le bug
		w1.setImage("newherbe2.png");
		w1.image = w1.image.getSubImage(0,15,80,50);
		Wall w2 = createWall(310,Global.GAMEPLAYHEIGHT-90,100,50); // bois au dessus pour �viter le bug
		w2.setImage("newherbe2.png");
		w2.image = w2.image.getSubImage(0,15,100,50);
		Wall w3 = createWall(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-50,80,50); // bois au dessus pour �viter le bug
		w3.setImage("newherbe2.png");
		w3.image = w2.image.getSubImage(0,15,80,50);
		
		//Plateform
		Wall o1 = createWall(10,75,(int)(148/1.3),(int)(42/1.3));
		o1.setImage("fleur2.png");
		Wall o2 = createWall(75,250,(int)(148/1.3),(int)(42/1.3));
		o2.setImage("fleur2.png");
		
		//la cage
		createWall(300,0,10,85);
		createWall(300,85,70,25);
		createWall(430,85,75,25);
		createWall(495,0,10,85);
		
		
		//l'arriv�e
		createWall(Global.GAMEPLAYWIDTH-160,75,150,10);

		// Place un mur pour s�parer les deux personnages
		//createObstacle(Global.GAMEPLAYWIDTH-200,Global.GAMEPLAYHEIGHT-220,40,210);
		
		Obstacle o5 =createObstacle(Global.GAMEPLAYWIDTH-200, Global.GAMEPLAYHEIGHT-220, 35, 210);
		o5.image = o5.image.getSubImage(25,10,50,300);
		
		// Place un mur pour empecher l'acces au levier
		Obstacle oMurLevier = new Obstacle(Global.GAMEPLAYWIDTH-130,85,30,135);
		oMurLevier.image = oMurLevier.image.getSubImage(25,10,50,325);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(oMurLevierBody);
		
		// Cr�� un bouton pressoir qui ouvre ce mur
		createBoutonPressoir(85,235,30,18,b1);
		
		// Place un mur devant la sortie
		Obstacle oMurSortie = new Obstacle(Global.GAMEPLAYWIDTH-160,0,20,75);
		oMurSortie.setImage("arbre-droit.png");
		oMurSortie.image = oMurSortie.image.getSubImage(25,10,50,325);
		sprites.add(oMurSortie);
		Body oMurSortieBody = myState.addObstacle(oMurSortie);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(oMurSortieBody);
		
		// Cr�� un levier qui ouvre ce mur
		Levier l1 = createLevier(Global.GAMEPLAYWIDTH-40,195,30,30,b2);
		
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
		Source s1 = createSource(500,Global.GAMEPLAYHEIGHT-42,49,42,Power.FLYING);
		createSource(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-87,49,42,Power.INTANGIBLE);
		
		//place Sortie
		Exit exit = createExit(Global.GAMEPLAYWIDTH-65,10,45,65);
		
		//place bonus
		Bonus b3 = createBonus(50,Global.GAMEPLAYHEIGHT-110,25,25);
		createBonus(20,20,25,25);
		createBonus(450,20,25,25);
		
		Source testSprite = this.createSource(330+34,Global.GAMEPLAYHEIGHT-35-76,
				5, 5, Power.NONE);
		testSprite.isHidden = true;
		
		// Place the characters
		this.character1 = addCharacterWithPoints(330,Global.GAMEPLAYHEIGHT-65-76,1.0f);		
		this.character2 = addCharacterWithPoints(Global.GAMEPLAYWIDTH-87-80,
				Global.GAMEPLAYHEIGHT-65-10,1.0f);		
		
		this.setLevelForAllSprites();
		
		// indication
		testSprite.setIndication(300, 100, "Appuyez sur Q Z D ou \nsur les fl�ches\n pour vous d�placer");
		l1.setIndication(300, 100,"Ceci est un levier, il peut \nouvrir une ou plusieurs portes, \nil a 2 positions");
		s1.setIndication(300, 100,"Ceci est une source, elle apporte \nun pouvoir particulier. \nRegardez les dessins!");
		InGameIndication indication3 = new InGameIndication(300, 100, 
		"Ceci est un fruit, ramasse les \ntous pour sauver tes amis!");		
		createIndicationFromSprite(b3,indication3);
		InGameIndication indication4 = new InGameIndication(350, 100, 
		"Ceci est une sortie! \nLes deux f�es doivent �tre � la \nporte pour finir le niveau ");		
		createIndicationSprite((int)(exit.x - 60), (int)(exit.y+30), 20,20, indication4);
	}
}
