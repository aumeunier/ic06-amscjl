package version1.data.levels;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import version1.Global;
import version1.data.LevelSave;
import version1.sprites.Power;
import version1.sprites.alive.monsters.Witch;
import version1.sprites.decor.Ground;
import version1.sprites.decor.GroundBackground;
import version1.sprites.interaction.MissilePlatform;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.trigger.buttons.PushButtonCharge;
import version1.states.GameplayState;

/**
 * This level is the last level of the game. The aim is to kill the boss by shooting at him using a platform.
 * This level is very hard and requires very good coordination between the players.
 * One player needs to be at the top to charge and shoot with the platform while the other player needs to 
 * move the platform left and right using the push buttons at the button.
 * The boss can kill the players in two different ways: touching them or fire fireballs at the closest player.
 * A new power can be used to dodge the fireballs, it creates a double of himself for a short while and can get one hit.
 * Note that this power disappears when used.
 *
 */
public class Level5 extends Level {
	public Level5(GameplayState state, LevelSave model){	
		// Initialize the level
		super(state,model);
		this.levelID = 5;
		this.setBackgroundImage("58a8ac8efbea1e681975348d60b40915.jpg");
		Ground.setim(Ground.IM3);

		// Place the walls around the level
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// Top platforms
		createGround(0,100,100,10);
		createGround(200,100,Global.GAMEPLAYWIDTH-300,10);
		createGround(690,110,10,50);
		createGround(700,150,100,10);	
		
		// Platform that can be moved and can fire missiles
		MissilePlatform platmiss=new MissilePlatform(0,110,80,10);
		ArrayList<Vec2> shape = new ArrayList<Vec2>();
		shape.add(new Vec2(-25,-5));
		shape.add(new Vec2(-25,5));
		shape.add(new Vec2(25,5));
		shape.add(new Vec2(25,-5));
		shape.add(new Vec2(0,0));
		
		// Blocks on the ground
		createGround(0,Global.GAMEPLAYHEIGHT-100,100,100);		
		createGround(Global.GAMEPLAYWIDTH-100,Global.GAMEPLAYHEIGHT-100,100,100);
		createGround(100,Global.GAMEPLAYHEIGHT-50,50,50);	
		createGround(Global.GAMEPLAYWIDTH-150,Global.GAMEPLAYHEIGHT-50,50,50);
		
		// Platforms
		GroundBackground g1 = new GroundBackground(120,Global.GAMEPLAYHEIGHT-170,80,10);
		sprites.add(g1);
		myState.addGroundOrWall(g1);
		GroundBackground g2 = new GroundBackground(280,Global.GAMEPLAYHEIGHT-230,80,10);
		sprites.add(g2);
		myState.addGroundOrWall(g2);
		GroundBackground g3 = new GroundBackground(420,Global.GAMEPLAYHEIGHT-155,100,10);
		sprites.add(g3);
		myState.addGroundOrWall(g3);
		GroundBackground g4 = new GroundBackground(580,Global.GAMEPLAYHEIGHT-140,80,10);
		sprites.add(g4);
		myState.addGroundOrWall(g4);
		GroundBackground g5 = new GroundBackground(440,Global.GAMEPLAYHEIGHT-280,80,10);
		sprites.add(g5);
		myState.addGroundOrWall(g5);
		GroundBackground g6 = new GroundBackground(590,Global.GAMEPLAYHEIGHT-260,80,10);
		sprites.add(g6);
		myState.addGroundOrWall(g6);
		GroundBackground g7 = new GroundBackground(0,Global.GAMEPLAYHEIGHT-240,50,10);
		sprites.add(g7);
		myState.addGroundOrWall(g7);
		
		// Create push buttons related to the platform
		sprites.add(platmiss);
		myState.addGroundOrWall(platmiss);
		Body maplat = state.getBodyForUserData(platmiss);
		ArrayList<Body> miss=new ArrayList<Body>() ;
		miss.add(maplat);
		createBoutonBombarde(10,85,30,15,miss); // Button to fire the missile
		createBoutonDeplace(0,Global.GAMEPLAYHEIGHT-115,30,15,miss,"left"); // Move left
		createBoutonDeplace(Global.GAMEPLAYWIDTH-30,Global.GAMEPLAYHEIGHT-115,30,15,miss,"right"); // Move right
		PushButtonCharge bouton=new PushButtonCharge(Global.GAMEPLAYWIDTH-100+30,150-15,30,15,miss); // Charge a missile
		sprites.add(bouton);
		myState.addPushButton(bouton);
		
		// Place sources
		Source dedouble = createSource(0,Global.GAMEPLAYHEIGHT-300,60,60,Power.DOUBLE);		
		
		// Place characters
		this.character1 = addCharacterWithPoints(550,0,0.75f);		
		this.character2 = addCharacterWithPoints(550,0,0.75f);
		
		// Monster
		Witch boss = new Witch(550,450,80,80);
		sprites.add(boss);
		myState.addMonster(boss, 1.0f);	
		boss.setBorns(new Vec2(0,450), new Vec2(800,450));
		boss.setSpeed(new Vec2(-10,0));
		
		// Place indications
		this.setLevelForAllSprites();
		dedouble.setIndication(420, 120, "Appuyez sur A ou sur Enter pour creer votre \ndouble a vos cotes pendant 10 secondes.\nAttention une fois utilise vous n'avez plus\n le pouvoir !");
	}
}