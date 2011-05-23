package beta;

import java.util.ArrayList;

import org.jbox2d.collision.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level3 extends Level {
	public Level3(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 3; // Do not forget to update that !!
		this.setBackgroundImage("8298638_s.jpg");

		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-2,Global.GAMEPLAYWIDTH,2);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		//Création des grounds
			createGround(0,380,100,120); // Sol 1
			Sprite w1 = new Sprite(0,380,100,50);
			w1.setImage("newherbe2.png");
			w1.image = w1.image.getSubImage(0,17,100,50);
			sprites.add(w1);
			
			createGround(150,380,600,50); // Sol 2
			Sprite w2 = new Sprite(150,380,600,50);
			w2.setImage("newherbe2.png");
			w2.image = w2.image.getSubImage(0,17,550,50);
			sprites.add(w2);
			
			createGround(550,430,200,70); // Sol 4
			createGround(750,480,50,20); // Sol 5
		
		//Création de la source FEU
		createSource(500,450,40,40,Power.FIRE);	
		
		
		//Création des petites plateformes
		createGround(200,310,50,4); // plateforme levier 1
		createGround(300,310,50,4); // plateforme levier 2
		createGround(400,310,50,4); // plateforme levier 3
		createGround(500,310,50,4); // plateforme source Gros
	
		
		//Création des leviers et de ce qu'ils déclenchent
		
		Source sourceG = createSource(505,265,40,40,Power.FAT); 		//Pouvoir du Gros
		Body sourceGBody = state.getBodyForUserData(sourceG); 			//Body du pouvoir du Gros
		Wall wall = createWall(540,250,10,130);							//Mur plateforme sautante
		Body wallBody = state.getBodyForUserData(wall);					//Body du Mur
		Wall wall2 = createWall(450,430,10,68);	//Mur pouvoir du feu
		Body wallBody2 = state.getBodyForUserData(wall2);				//Body du Mur
		
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(wallBody);
		createLevier(310,280,30,30,b3);							//levier déclencheur du mur
		ArrayList<Body> b5 = new ArrayList<Body>();
		b5.add(wallBody2);
		createLevier(410,280,30,30,b5);							//levier déclencheur du mur
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(sourceGBody);
		Levier levier1 = createLevier(210,280,30,30,b1);		//levier déclencheur du pouvoir
		levier1.activate();
		
		Ground solMouvant = createGround(570,373,50,7); 		// plateforme mouvante
		Body bodyMouvant = state.getBodyForUserData(solMouvant);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(bodyMouvant);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyMouvant.setMass(md);
		BoutonElevator B = createBoutonElevator(680, 362, 30, 18, b2, 2, 5000);	//bouton Elevator pour la plateforme
		System.out.println(B.getNum());
		createGround(565,375,5,5);//cale1
		createGround(620,375,5,5);//cale2
		
		
		
		
		createGround(0,240,550,10); // plateforme centrale
		
		createSource(0,200,40,40,Power.TELEPORTATION);	//pouvoir de teleportation
		
		//escalier
		createGround(80,170,40,5);
		createGround(0,115,40,5);	
		createGround(50,50,50,5); // plateforme en haut
		
		//construction de la pente la plus haute
		ArrayList<Vec2> sol4Points = new ArrayList<Vec2>();
		sol4Points.add(new Vec2(-215,15));
		sol4Points.add(new Vec2(-215,10));
		sol4Points.add(new Vec2(215,-15));
		sol4Points.add(new Vec2(215,-10));
		Ground sol4 = createGroundWithPoints(120,60,430,30,sol4Points); // Sol 3
		sol4.addPointToShape(120,60);
		sol4.addPointToShape(120,65);
		sol4.addPointToShape(550,90);
		sol4.addPointToShape(550,85);
		sol4.setSlippery(true, "right");
		
		//plateforme intermédiaire
		Ground inter = createGround(570,160,230,5); 
		inter.addPointToShape(570,157);
		inter.addPointToShape(570,162);
		inter.addPointToShape(800,159);
		inter.addPointToShape(800,154);
		inter.setSlippery(true, "left");
		
		
		//plateforme bonus
		
		createBonus(770,127,30,30); 
		
		/*//construction de la deuxieme pente ne marche pas
		ArrayList<Vec2> sol4Points2 = new ArrayList<Vec2>();
		sol4Points2.add(new Vec2(200,30));
		sol4Points2.add(new Vec2(200,15));
		sol4Points2.add(new Vec2(-200,-30));
		sol4Points2.add(new Vec2(-200,-15));
		
		Ground sol42 = createGroundWithPoints(450,160,200,20,sol4Points2); // Sol 3
		sol42.addPointToShape(450,165);
		sol42.addPointToShape(450,180);
		sol42.addPointToShape(650,175);
		sol42.addPointToShape(650,160);
		//Ground sol42 = createGround(400,160,400,10);
		sol42.setSlippery(true, "left");*/
		
		
		
		createGround(680,105,120,5); // plateforme arrivée
		Wall arriveeWall = createWall(Global.GAMEPLAYWIDTH-60,40,5,65);
		Body arriveeBody = state.getBodyForUserData(arriveeWall);
		ArrayList<Body> b4 = new ArrayList<Body>();
		b4.add(arriveeBody);
		BoutonPressoir B2 = createBoutonPressoir(680, 87, 30, 18, b4, 3);
		B2.setResteActive(true);
		createExit(Global.GAMEPLAYWIDTH-45,40,45,65);
		
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,330,0.75f);		
		this.character2 = addCharacterWithPoints(50,330,0.75f);	
		
		this.setLevelForAllSprites();
	}
}
