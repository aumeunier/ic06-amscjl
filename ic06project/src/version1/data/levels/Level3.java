package version1.data.levels;

import java.util.ArrayList;

import org.jbox2d.dynamics.Body;

import version1.Global;
import version1.data.LevelSave;
import version1.sprites.Power;
import version1.sprites.Sprite;
import version1.sprites.decor.Ground;
import version1.sprites.decor.InGameIndication;
import version1.sprites.decor.Obstacle;
import version1.sprites.decor.Wall;
import version1.sprites.interaction.Transporter;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.trigger.Lever;
import version1.states.GameplayState;

/**
 * This level's main concern is to disturb the player. 
 * There are many teleporters and you don't go back to the first place when you use it back.
 * Three new powers are introduced: bounce, swim and absorb. 
 * The last one make the player get his friend power if he touches him.
 * 
 * Note that it can be quite hard to spot the push button at the bottom left corner of the level and that there is 
 * a secret teleporter that is not visible. Also, the bounce power isn't easy to control.
 * 
 * After user tests we moved that level from second to third and we added several indications to help the players.
 *
 */
public class Level3 extends Level {
	public Level3(GameplayState state, LevelSave model){	
		// Initialize the level
		super(state,model);
		this.levelID = 3;
		
		// Set the background and ground type images
		this.setBackgroundImage("6362779_s.jpg");
		Ground.setim(Ground.IM1);

		// Place the walls around the level
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// Place ground - numbers reference the map we drawn
		createGround(200,410,100,80); // Sol 3
		createGround(100,410,100,20);
		createGround(100,360,10,50); // Sol 2 petit a gauche
		createGround(100,200,200,160); // Sol 1/4 gros en haut
		createGround(290,360,10,50); // Sol 32 petit a droite
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
		createGround(730,200,90,70); // Sol 15
		createGround(730,320,90,10); // Sol 15
		createGround(630,260,100,10);
		createGround(0,350,40,10); // Sol 20 petite plateforme

		// Animated Water
		createDeathlySource(25,Global.GAMEPLAYHEIGHT-20,175,10);
		Sprite s6 = new Sprite(0,Global.GAMEPLAYHEIGHT-60,50,50);
		sprites.add(s6);
		s6.setAnimation("waves.png", 300, 300);
		Sprite s7 = new Sprite(50,Global.GAMEPLAYHEIGHT-60,50,50);
		sprites.add(s7);
		s7.setAnimation("waves2.png", 300, 300);
		Sprite s8 = new Sprite(100,Global.GAMEPLAYHEIGHT-60,50,50);
		sprites.add(s8);
		s8.setAnimation("waves.png", 300, 300);
		Sprite s9 = new Sprite(150,Global.GAMEPLAYHEIGHT-60,50,50);
		sprites.add(s9);
		s9.setAnimation("waves2.png", 300, 300);
		
		// Place wood wall
		Wall w1 = createWall(100,190,410,50); 
		w1.setImage("newherbe2.png");
		w1.setCroppedImage(0,15,410,50);
		Wall w2 = createWall(530,190,300,50); 
		w2.setImage("newherbe2.png");
		w2.setCroppedImage(0,15,300,50);
		
		// Doors
		Obstacle oMurLevier = new Obstacle(700,0,10,60);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(oMurLevierBody);
		
		Obstacle oMurLevier2 = new Obstacle(140,0,10,70);
		oMurLevier2.setHidden(true);
		sprites.add(oMurLevier2);
		Body oMurLevierBody2 = myState.addObstacle(oMurLevier2);
		b1.add(oMurLevierBody2);
		
		Obstacle oMurLevier3 = new Obstacle(140,0,10,70);
		sprites.add(oMurLevier3);
		Body oMurLevierBody3 = myState.addObstacle(oMurLevier3);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(oMurLevierBody3);
		
		Obstacle oMurLevier4 = new Obstacle(500,435,10,55);
		sprites.add(oMurLevier4);
		Body oMurLevierBody4 = myState.addObstacle(oMurLevier4);
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(oMurLevierBody4);
		
		// Switches
		Lever l1 = createLevier(460,295,30,30,b1); 
		createLevier(250,380,30,30,b2); 
		createPushButton(5,480,20,20,b3); 
		
		// Teleporters
		Transporter t3 = createTransporter(790,450,10,42,300,280);
		createTransporter(300,285,10,42,760,285);
		createTransporter(790,280,10,42,120,370);
		createTransporter(130,370,10,42,750,440);
		createTransporter(790,350,10,42,600,440);
		createTransporter(545,280,10,42,10,250);
		createTransporter(180,460,10,42,600,0);
		Transporter t1 = createTransporter(300,450,10,42,630,210);
		Transporter t2 = createTransporter(720,215,10,42,350,450);
		t1.setImage(null);
		t2.setImage(null);
		
		
		// Place platforms
		Wall w3 = createWall(0,70,150,20);
		w3.setImage("newherbe2.png");
		w3.setCroppedImage(0,15,410,50);
		
		Wall w4 = createWall(600,60,200,20); 
		w4.setImage("newherbe2.png");
		w4.setCroppedImage(0,15,410,50);
		
		Wall w5 = createWall(310,105,100,10); 
		w5.setImage("newherbe2.png");
		w5.setCroppedImage(0,15,410,50);	
		
		// Place exit
		createExit(590,395,40,30);

		// Place sources
		createSource(750,150,60,60,Power.SMALL);
		Source rebond =createSource(750,20,60,60,Power.REBOND);
		Source nage =createSource(10,10,60,60,Power.SWIM);
		Source absorbe =createSource(350,450,60,60,Power.ABSORB);
		
		// Place bonuses
		createBonus(10,300,25,25);
		createBonus(650,215,25,25);
		
		// Place the characters
		this.character1 = addCharacterWithPoints(600,0,0.75f);
		this.character2 = addCharacterWithPoints(550,450,0.75f);
		
		// Place indications
		this.setLevelForAllSprites();
		InGameIndication indicationRebond = new InGameIndication(250, 100, 
	    "Rebondir peut être utile,\n prochaine étape : NAGER !!");  
		createIndicationFromSprite(rebond,indicationRebond);
		InGameIndication indicationNage = new InGameIndication(350, 100, 
	    "Nage et cherche le bouton !\n Après ça fais toi petite... !!");  
		createIndicationFromSprite(nage,indicationNage);
		InGameIndication indicationAbsorbe = new InGameIndication(350, 100, 
	    "Tu peux maintenant absorber le pouvoir \nde ton amie simplement en la touchant!");  
		createIndicationFromSprite(absorbe,indicationAbsorbe);
		InGameIndication indicationt3 = new InGameIndication(450, 100, 
	    "Ceci est un téléporteur, touche le et tu seras \ntéléporté, Mais attention certains \ntéléporteurs sont cachés!!");  
		createIndicationFromSprite(t3,indicationt3);
		InGameIndication indicationl1 = new InGameIndication(450, 100, 
	    "Attention, il y a deux leviers, \n il faut trouver les bonnes combinaisons !");  
		createIndicationFromSprite(l1,indicationl1);
	}
}