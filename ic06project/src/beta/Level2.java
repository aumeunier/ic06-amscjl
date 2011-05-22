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





		//addIndication(200,100,"Tu peux maintenant voler!");
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(400,400,0.75f);		
		this.character2 = addCharacterWithPoints(11,400,0.75f);	
	}
}
