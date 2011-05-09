package alpha;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

public class Level1 extends Level {
	public Level1(GameplayState state, LevelSave model){	
		super(state,model);
		
		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(0,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH-10,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,0,Global.GAMEPLAYWIDTH,10);
		
		
		// second wall 
		/*
		ArrayList<Vec2> temp = new ArrayList<Vec2>();
		temp.add(new Vec2(-100/2,-75/2));
		temp.add(new Vec2(+100/2,-75/2));
		temp.add(new Vec2(-100/2,+75/2));
		createObstacleWithPoints(10,Global.GAMEPLAYHEIGHT-35,80,25,temp);
		*/
		createObstacle(10,Global.GAMEPLAYHEIGHT-85,100,75);
		createObstacle(310,Global.GAMEPLAYHEIGHT-85,100,75);
		createObstacle(410,Global.GAMEPLAYHEIGHT-65,80,55);
		createObstacle(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-35,80,25);
		
		//Plateform
		createObstacle(300,200,100,10);
		
		
	
		// Place a Obstacle
		//createObstacle(300,Global.GAMEPLAYHEIGHT,100,10);
		
		
		// Place sources
		Source s = createSource(115,CH1_INIT_Y-10,180,70);
		s.setImage("pool_bird.png");
		s.power = 2;
		
		Source s2 = createSource(500,Global.GAMEPLAYHEIGHT-60,20,20);
		s2.setImage("pool_bird.png");
		s2.power = 1;
		
		
		// Place the first character
		this.character1 = addCharacter(20,90);		
		this.character2 = addCharacter(Global.GAMEPLAYWIDTH-50,90);
	}
}
