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
		
		createGround(0,380,150,120); // Sol 1
		Wall w1 = createWall(0,380,150,50);
		w1.setImage("newherbe2.png");
		w1.image = w1.image.getSubImage(0,17,150,50);
		
		createGround(200,380,550,50); // Sol 2
		Wall w2 = createWall(200,380,550,50);
		w2.setImage("newherbe2.png");
		w2.image = w2.image.getSubImage(0,17,550,50);
		
		createGround(550,430,200,70); // Sol 4
		createGround(750,480,50,20); // Sol 5
		
		
		createGround(270,310,50,4); // plateforme levier 1
		createGround(330,310,50,4); // plateforme levier 2
		createGround(390,310,50,4); // plateforme levier 3
		createGround(450,310,50,4); // plateforme source
	
		
		Source sourceG = createSource(455,265,40,40,Power.FAT); //Pouvoir du Gros
		createSource(500,450,40,40,Power.FIRE);					//Pouvoir du feu
		Body sourceGBody = state.getBodyForUserData(sourceG);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(sourceGBody);
		Levier levier1 = createLevier(280,280,30,30,b1);		//levier déclencheur du pouvoir
		levier1.activate();
		
		
		Wall wall = createWall(540,250,10,130);
		Body wallBody = state.getBodyForUserData(wall);
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(wallBody);
		createLevier(340,280,30,30,b3);	
		
		
		Ground solMouvant = createGround(570,373,50,7); 		// plateforme mouvante
		Body bodyMouvant = state.getBodyForUserData(solMouvant);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(bodyMouvant);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyMouvant.setMass(md);
		createBoutonElevator(680, 362, 30, 18, b2, 2, 5000);	//bouton Elevator pour la plateforme
		createGround(565,375,5,5);//cale1
		createGround(620,375,5,5);//cale2
		
		
		createGround(0,240,550,10); // plateforme centrale
		
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
		
		createGround(680,70,120,5); // plateforme arrivée
		
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,330,0.75f);		
		this.character2 = addCharacterWithPoints(50,330,0.75f);	
	}
}
