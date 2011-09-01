package version1.data.levels;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import version1.Global;
import version1.data.LevelSave;
import version1.sprites.Power;
import version1.sprites.decor.Ground;
import version1.sprites.decor.InGameIndication;
import version1.sprites.decor.Obstacle;
import version1.sprites.decor.Wall;
import version1.sprites.interaction.Bonus;
import version1.sprites.interaction.Exit;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.sources.DeathlySource;
import version1.sprites.interaction.trigger.Lever;
import version1.states.GameplayState;

/**
 * This level serves as an introduction to the game. 
 * Several indications pop to indicate how to move, collect things, what sources are for and what the aim is.
 * Solution is to get the intangible power and go through the wall, then have a player press the push button
 * while another activates the switch on the right. Then both players can head to the exit (and gather bonuses).
 *
 */
public class Level1 extends Level {
	public Level1(GameplayState state, LevelSave model){	
		// Initialize the level
		super(state,model);
		this.levelID = 1;
		
		// Set the background and ground type images
		this.setBackgroundImage("6362779_s.jpg");
		Ground.setim(Ground.IM2);

		// Place the walls around the level
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// Place platforms (flowers)
		ArrayList<Vec2> temp = new ArrayList<Vec2>();
		temp.add(new Vec2(-100,50));
		temp.add(new Vec2(+100,50));
		temp.add(new Vec2(-100,+75));
		Obstacle o = createObstacleWithPoints(600,200,200,150,temp);
		o.setImage("fleur9_mur.png");
		Wall o1 = createWall(10,75,(int)(148/1.3),(int)(42/1.3)); // flower 1
		o1.setImage("fleur2.png");
		Wall o2 = createWall(75,250,(int)(148/1.3),(int)(42/1.3)); // flower 2
		o2.setImage("fleur2.png");
		createWall(Global.GAMEPLAYWIDTH-160,75,150,10); // top right
		
		// Place the ground
		createGround(0,Global.GAMEPLAYHEIGHT-85,80,75); // Sol 1
		createGround(310,Global.GAMEPLAYHEIGHT-85,100,75); // Sol 2 (after water)
		createGround(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-45,80,35); // Sol 4 (right)
		ArrayList<Vec2> sol3Points = new ArrayList<Vec2>();
		sol3Points.add(new Vec2(-40,-27));
		sol3Points.add(new Vec2(40,-27));
		sol3Points.add(new Vec2(-40,27));
		Wall sol3 = createWallWithPoints(410,Global.GAMEPLAYHEIGHT-65,80,55,sol3Points); // Sol 3
		sol3.addPointToShape(410,Global.GAMEPLAYHEIGHT-10);
		sol3.addPointToShape(410+80,Global.GAMEPLAYHEIGHT-10);
		sol3.addPointToShape(410,Global.GAMEPLAYHEIGHT-65);
		
		// Place grass
		Wall w1 = createWall(0,Global.GAMEPLAYHEIGHT-90,80,50);
		w1.setImage("newherbe2.png");
		w1.setCroppedImage(0,15,80,50);
		Wall w2 = createWall(310,Global.GAMEPLAYHEIGHT-90,100,50);
		w2.setImage("newherbe2.png");
		w2.setCroppedImage(0,15,100,50);
		Wall w3 = createWall(Global.GAMEPLAYWIDTH-80,Global.GAMEPLAYHEIGHT-50,80,50); 
		w3.setImage("newherbe2.png");
		w3.setCroppedImage(0,15,80,50);
		
		// Box top middle
		createWall(300,0,10,85);
		createWall(300,85,70,25);
		createWall(430,85,75,25);
		createWall(495,0,10,85);
		
		// Wood wall
		Obstacle o5 =createObstacle(Global.GAMEPLAYWIDTH-200, Global.GAMEPLAYHEIGHT-220, 35, 210);
		o5.setCroppedImage(25,10,50,300);

		// Wall before the exit
		Exit exit = createExit(Global.GAMEPLAYWIDTH-65,10,45,65);
		Obstacle oMurSortie = new Obstacle(Global.GAMEPLAYWIDTH-160,0,20,75);
		oMurSortie.setImage("arbre-droit.png");
		oMurSortie.setCroppedImage(25,10,50,325);
		sprites.add(oMurSortie);
		Body oMurSortieBody = myState.addObstacle(oMurSortie);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(oMurSortieBody);
		Lever l1 = createLevier(Global.GAMEPLAYWIDTH-40,195,30,30,b2); // the switch open the wall
		
		// Wall before the switch
		Obstacle oMurLevier = new Obstacle(Global.GAMEPLAYWIDTH-130,85,30,135);
		oMurLevier.setCroppedImage(25,10,50,325);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(oMurLevierBody);
		createPushButton(85,235,30,18,b1); // Push button which opens that wall
		
		// Water
		DeathlySource s = createDeathlySource(80,Global.GAMEPLAYHEIGHT-60,50,50);
		s.setAnimation("waves.png", 300, 300);
		DeathlySource s2 = createDeathlySource(130,Global.GAMEPLAYHEIGHT-60,50,50);
		s2.setAnimation("waves2.png", 300, 300);
		DeathlySource s3 = createDeathlySource(180,Global.GAMEPLAYHEIGHT-60,50,50);
		s3.setAnimation("waves.png", 300, 300);
		DeathlySource s4 = createDeathlySource(230,Global.GAMEPLAYHEIGHT-60,50,50);
		s4.setAnimation("waves2.png", 300, 300);
		DeathlySource s5 = createDeathlySource(280,Global.GAMEPLAYHEIGHT-60,50,50);
		s5.setAnimation("waves.png", 300, 300);
		
		// Sources
		Source s1 = createSource(500,Global.GAMEPLAYHEIGHT-42,49,42,Power.FLYING);
		createSource(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-87,49,42,Power.INTANGIBLE);
		Source testSprite = this.createSource(330+34,Global.GAMEPLAYHEIGHT-35-76,
				5, 5, Power.NONE); // indication
		testSprite.setHidden(true);
		
		// Bonus
		Bonus b3 = createBonus(50,Global.GAMEPLAYHEIGHT-110,25,25);
		createBonus(20,20,25,25);
		createBonus(450,20,25,25);
		
		// Place the characters
		this.character1 = addCharacterWithPoints(330,Global.GAMEPLAYHEIGHT-65-76,1.0f);		
		this.character2 = addCharacterWithPoints(Global.GAMEPLAYWIDTH-87-80,
				Global.GAMEPLAYHEIGHT-65-10,1.0f);		

		// Place indications
		this.setLevelForAllSprites();
		testSprite.setIndication(300, 100, "Appuyez sur Q Z D ou \nsur les flèches\n pour vous déplacer");
		l1.setIndication(300, 100,"Ceci est un levier, il peut \nouvrir une ou plusieurs portes, \nil a 2 positions");
		s1.setIndication(300, 100,"Ceci est une source, elle apporte \nun pouvoir particulier. \nRegardez les dessins!");
		InGameIndication indication3 = new InGameIndication(300, 100, 
		"Ceci est un fruit, ramasse les \ntous pour sauver tes amis!");		
		createIndicationFromSprite(b3,indication3);
		exit.setIndication(350, 100, 
		"Ceci est une sortie! \nLes deux fées doivent être à la \nporte pour finir le niveau ");	
	}
}
