package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level2 extends Level {
	public Level2(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 2; // Do not forget to update that !!
		this.setBackgroundImage("6362779_s.jpg");
		this.backgroundImage = this.backgroundImage.getSubImage(0, 0, 
				this.backgroundImage.getWidth(), this.backgroundImage.getHeight()*Global.GAMEPLAYHEIGHT/Global.WINDOW_HEIGHT);
		
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
		createWall(100,190,410,10); // bois au dessus pour �viter le bug
		createWall(530,190,300,10); // bois au dessus pour �viter le bug


		createSource(500,Global.GAMEPLAYHEIGHT-42,49,42,Power.FLYING);
		//createSource(0,Global.GAMEPLAYHEIGHT-42,49,42,Power.FLYING);
		createTransporter(790,420,10,42,300,280);
		createTransporter(300,280,10,42,760,285);
		createTransporter(790,270,10,42,105,400);
		createTransporter(105,400,10,42,750,440);
		createTransporter(790,340,10,42,600,440);


		Exit exit = createExit(600,400,25,25);
		exit.setImage("porte.png");


		//addIndication(200,100,"Tu peux maintenant voler!");
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(400,400,0.75f);		
		this.character2 = addCharacterWithPoints(400,400,0.75f);	
		/*Body b = state.getBodyForUserData(this.character1);
		state.modifyBodySize(b, 0.5f, 0.5f);
		Body b2 = state.getBodyForUserData(this.character2);
		state.modifyBodySize(b2, 0.5f, 0.5f);
		*/
	}
}
