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
		createGround(200,380,550,50); // Sol 2
		createGround(550,430,200,70); // Sol 4
		createGround(750,480,50,20); // Sol 5
		
		createGround(50,70,50,10); // plateforme en haut
		
		//construction de la pente la plus haute
		ArrayList<Vec2> sol4Points = new ArrayList<Vec2>();
		sol4Points.add(new Vec2(-250,25));
		sol4Points.add(new Vec2(-250,20));
		sol4Points.add(new Vec2(250,-25));
		sol4Points.add(new Vec2(250,-20));
		Ground sol4 = createGroundWithPoints(100,70,500,50,sol4Points); // Sol 3
		sol4.addPointToShape(100,70);
		sol4.addPointToShape(100,75);
		sol4.addPointToShape(600,120);
		sol4.addPointToShape(600,115);
		sol4.setSlippery(true);
		
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
		
		Ground solMouvant = createGround(570,370,50,7); 		// plateforme mouvante
		Body bodyMouvant = state.getBodyForUserData(solMouvant);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(bodyMouvant);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyMouvant.setMass(md);
		createBoutonElevator(680, 355, 30, 18, b2, 2, 5000);	//bouton Elevator pour la plateforme
		createGround(565,375,5,5);//cale1
		createGround(620,375,5,5);//cale2
		
		
		createGround(0,240,550,10); // plateforme centrale
		
		createGround(650,100,150,10); // plateforme arrivée
		

		//addIndication(200,100,"Tu peux maintenant voler!");
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(60,50,0.75f);		
		this.character2 = addCharacterWithPoints(50,350,0.75f);	
	}
}
