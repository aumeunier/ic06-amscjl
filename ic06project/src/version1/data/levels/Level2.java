package version1.data.levels;

import java.util.ArrayList;

import org.jbox2d.collision.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import version1.Global;
import version1.data.LevelSave;
import version1.sprites.Power;
import version1.sprites.Sprite;
import version1.sprites.decor.Ground;
import version1.sprites.decor.Wall;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.trigger.LeverCombination;
import version1.sprites.interaction.trigger.buttons.PushButton;
import version1.states.GameplayState;

/**
 * This level was initially the third level but after user tests seemed easier so we moved it to the second level.
 * It introduces other gameplay elements and new powers: 
 * three switches lead to different results depending on their state, 
 * push buttons can need more than one person to be pressed,
 * a teleport power can teleport your friend next to you,
 * a fat power can make you activate hard buttons,
 * a fire power can remove the snow on an icy platform while the player is here, removing the sliding effect.
 *
 */
public class Level2 extends Level {
	public Level2(GameplayState state, LevelSave model){	
		// Initialize the level
		super(state,model);
		this.levelID = 2;

		// Set the background and ground type images
		this.setBackgroundImage("5759438_s.jpg");
		Ground.setim(Ground.IM2);

		// Place sources
		Source sourceFeu = createSource(500,450,49,42,Power.FIRE);
		Source sourceG = createSource(0,265,40,40,Power.FAT);
		sourceG.setHidden(true);
		Source sTp = createSource(0,200,60,60,Power.TELEPORTATION);

		// Place the walls around the level
		createWall(0,Global.GAMEPLAYHEIGHT-2,Global.GAMEPLAYWIDTH,2);		
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);

		// Place grounds
		createGround(0,380,100,120); // Ground 1
		Sprite w1 = new Sprite(0,380,100,50);
		w1.setImage("snow.png");
		w1.setCroppedImage(0,17,100,50);
		sprites.add(w1);
		createGround(150,380,600,50); // Ground 2
		Sprite w2 = new Sprite(150,380,600,50);
		w2.setImage("snow.png");
		w2.setCroppedImage(0,17,100,50);
		sprites.add(w2);
		createGround(550,430,200,70); // Ground 4
		createGround(750,480,50,20); // Ground 5
		
		// Place platforms 
		createGround(220,310,50,5); // plateforme levier 1
		createGround(330,310,50,5); // plateforme levier 2
		createGround(440,310,50,5); // plateforme levier 3
		createGround(0,310,40,5); // plateforme source Gros
		createGround(0,240,550,10); // plateforme centrale
		Sprite w3 = new Sprite(0,240,550,50);
		w3.setImage("snow.png");
		w3.setCroppedImage(0,17,550,50);
		sprites.add(w3);

		// Stairs
		createGround(60,175,60,5);
		createGround(0,125,60,5);	

		// Iced platform top
		ArrayList<Vec2> sol4Points = new ArrayList<Vec2>();
		sol4Points.add(new Vec2(-235,10));
		sol4Points.add(new Vec2(-235,5));
		sol4Points.add(new Vec2(235,-10));
		sol4Points.add(new Vec2(235,-5));
		Ground sol4 = createGroundWithPoints(80,70,470,20,sol4Points); // Sol 3
		sol4.addPointToShape(80,70);
		sol4.addPointToShape(80,90);
		sol4.addPointToShape(550,105);
		sol4.addPointToShape(550,85);
		sol4.setSlippery(true, false);
		sol4.setOriginalySlippery(true);
		sol4.setImage("snow.png");
		sol4.setCroppedImage(0,10,550,50);

		// Ice platform right
		Ground inter = createGround(570,160,230,5); 
		inter.addPointToShape(570,157);
		inter.addPointToShape(570,172);
		inter.addPointToShape(800,169);
		inter.addPointToShape(800,154);
		inter.setSlippery(true, true);
		inter.setOriginalySlippery(true);
		inter.setImage("snow.png");
		inter.setCroppedImage(0,10,230,70);

		// Exit platform
		createGround(680,105,120,5); 
		Wall arriveeWall = createWall(Global.GAMEPLAYWIDTH-60,40,5,65);
		Body arriveeBody = state.getBodyForUserData(arriveeWall);
		ArrayList<Body> b4 = new ArrayList<Body>();
		b4.add(arriveeBody);
		PushButton B2 = createPushButton(680, 87, 30, 18, b4, 3);
		B2.setStayActive(true);
		createExit(Global.GAMEPLAYWIDTH-45,40,45,65);

		// Switch creation
		Body sourceGBody = state.getBodyForUserData(sourceG);
		Wall wall = createWall(540,250,10,130);
		Body wallBody = state.getBodyForUserData(wall);
		Wall wall2 = createWall(450,430,10,68);
		Body wallBody2 = state.getBodyForUserData(wall2);

		// Create different possibilities
		//1ere
		ArrayList<Body> listeB1= new ArrayList<Body>();
		listeB1.add(sourceGBody);
		ArrayList<Boolean> listeT1= new ArrayList<Boolean>();
		listeT1.add(true);
		listeT1.add(false);
		listeT1.add(false);
		//2eme
		ArrayList<Body> listeB2= new ArrayList<Body>();
		listeB2.add(wallBody);
		ArrayList<Boolean> listeT2= new ArrayList<Boolean>();
		listeT2.add(true);
		listeT2.add(true);
		listeT2.add(false);
		//3eme
		ArrayList<Body> listeB3= new ArrayList<Body>();
		listeB3.add(wallBody2);
		ArrayList<Boolean> listeT3= new ArrayList<Boolean>();
		listeT3.add(true);
		listeT3.add(true);
		listeT3.add(true);
		//4eme
		ArrayList<Body> listeB4= new ArrayList<Body>();
		listeB4.add(wallBody);
		listeB4.add(wallBody2);
		ArrayList<Boolean> listeT4= new ArrayList<Boolean>();
		listeT4.add(true);
		listeT4.add(false);
		listeT4.add(true);

		//Lists creation
		ArrayList<ArrayList<Body> > listB = new ArrayList<ArrayList<Body> >();
		listB.add(listeB1);
		listB.add(listeB2);
		listB.add(listeB3);
		listB.add(listeB4);
		ArrayList<ArrayList<Boolean> > listT = new ArrayList<ArrayList<Boolean> >();
		listT.add(listeT1);
		listT.add(listeT2);
		listT.add(listeT3);
		listT.add(listeT4);

		//Switch creation
		LeverCombination levier1=createLevierCombi(235,280,30,30,listB,listT);
		LeverCombination levier2=createLevierCombi(345,280,30,30,listB,listT);
		LeverCombination levier3=createLevierCombi(455,280,30,30,listB,listT);
		ArrayList<LeverCombination> L = new ArrayList<LeverCombination>();
		L.add(levier1);
		L.add(levier2);
		L.add(levier3);

		levier1.SetLeverList(L);//wall
		levier2.SetLeverList(L);//wall
		levier3.SetLeverList(L);//fat power

		// Elevator
		Ground solMouvant = createGround(570,373,50,7);
		Body bodyMouvant = state.getBodyForUserData(solMouvant);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(bodyMouvant);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyMouvant.setMass(md);
		createBoutonElevator(660, 362, 30, 18, b2, 2, 5000);// Switch of the elevator
		createGround(565,375,5,5);//cale1
		createGround(620,375,5,5);//cale2
		
		// Place bonuses
		createBonus(770,127,30,30); 
		createBonus(760,450,30,30);

		// Place the characters
		this.character1 = addCharacterWithPoints(0,330,0.75f);		
		this.character2 = addCharacterWithPoints(30,330,0.75f);	

		// Place indications
		this.setLevelForAllSprites();
		sourceG.setIndication(300, 100, "Tu as maintenant le pouvoir \nd'etre plus lourd !");
		sourceFeu.setIndication(300, 100,"Tu as maintenant le pouvoir \ndu feu, a quoi cela peut-il \nbien servir ?");
		sTp.setIndication(300, 100,"Appuyez sur A ou sur Entree\n pour teleporter votre \nami a vos cotes");
	}
}

