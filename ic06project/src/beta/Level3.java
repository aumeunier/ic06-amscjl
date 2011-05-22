package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level3 extends Level {
	public Level3(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 3; // Do not forget to update that !!
		this.setBackgroundImage("8298638_s.jpg");
		this.backgroundImage = this.backgroundImage.getSubImage(0, 0, 
				this.backgroundImage.getWidth(), this.backgroundImage.getHeight()*Global.GAMEPLAYHEIGHT/Global.WINDOW_HEIGHT);
		
		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-2,Global.GAMEPLAYWIDTH,2);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		createGround(0,400,200,100); // Sol 1
		createGround(250,400,500,50); // Sol 2
		createGround(550,450,200,50); // Sol 4
		createGround(750,480,50,20); // Sol 5
		
		createGround(270,325,50,2); // plateforme levier 1
		createGround(330,310,50,2); // plateforme levier 2
		createGround(390,325,50,2); // plateforme levier 3
		createGround(450,310,50,2); // plateforme source
	
		
		Source sourceG = createSource(455,270,40,40,Power.FAT);
		createSource(500,450,40,40,Power.FIRE);
		Body sourceGBody = state.getBodyForUserData(sourceG);
		Levier levier1 = createLevier(260,295,30,30,sourceGBody);
		levier1.activate();
		
		Ground solMouvant = createGround(570,395,50,5); // plateforme mouvante
		Body bodyMouvant = state.getBodyForUserData(solMouvant);
		createBoutonElevator(680, 382, 30, 18, bodyMouvant, 2);
		
		createGround(0,240,550,10); // plateforme 
		
		createGround(650,50,150,10); // plateforme arrivée
		

		//addIndication(200,100,"Tu peux maintenant voler!");
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(11,350,0.75f);		
		this.character2 = addCharacterWithPoints(50,350,0.75f);	
	}
}
