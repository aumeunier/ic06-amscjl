package gold;

import java.util.ArrayList;
import org.jbox2d.dynamics.Body;

public class Level3 extends Level {
	public Level3(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 3; // Do not forget to update that !!
		this.setBackgroundImage("6362779_s.jpg");
		Ground.setim(Ground.IM1);

		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		
		
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
		Wall w1 = createWall(100,190,410,50); // bois au dessus pour �viter le bug
		w1.setImage("newherbe2.png");
		w1.image = w1.image.getSubImage(0,15,410,50);
		Wall w2 = createWall(530,190,300,50); // bois au dessus pour �viter le bug
		w2.setImage("newherbe2.png");
		w2.image = w2.image.getSubImage(0,15,300,50);

		//source
		createSource(750,150,60,60,Power.PETIT);
		Source rebond =createSource(750,20,60,60,Power.REBOND);
		Source nage =createSource(10,10,60,60,Power.NAGE);
		Source absorbe =createSource(350,450,60,60,Power.ABSORBE);
		
		createExit(590,395,40,30);
		
		//bonus
		createBonus(10,300,25,25);
		createBonus(650,215,25,25);
		
		//mur levier x
		Obstacle oMurLevier = new Obstacle(700,0,10,60);
		sprites.add(oMurLevier);
		Body oMurLevierBody = myState.addObstacle(oMurLevier);
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(oMurLevierBody);
		
		Obstacle oMurLevier2 = new Obstacle(140,0,10,60);
		oMurLevier2.setHidden(true);
		sprites.add(oMurLevier2);
		Body oMurLevierBody2 = myState.addObstacle(oMurLevier2);
		b1.add(oMurLevierBody2);
		
		//mur levier y
		Obstacle oMurLevier3 = new Obstacle(140,0,10,60);
		sprites.add(oMurLevier3);
		Body oMurLevierBody3 = myState.addObstacle(oMurLevier3);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(oMurLevierBody3);
		
		//mur levier z
		Obstacle oMurLevier4 = new Obstacle(500,435,10,55);
		sprites.add(oMurLevier4);
		Body oMurLevierBody4 = myState.addObstacle(oMurLevier4);
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(oMurLevierBody4);
		
		//levier qui ouvre le mur
		Levier l1 = createLevier(460,295,30,30,b1); //levier x
		createLevier(250,380,30,30,b2); //levier y
		
		
		//place l'eau
		createSourceMortelle(25,Global.GAMEPLAYHEIGHT-20,175,10);
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
		
		createBoutonPressoir(5,480,20,20,b3); //levier z
		
		//transporteur
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
		
		
		//plateforme
		Wall w3 = createWall(0,60,150,20); 
		w3.setImage("newherbe2.png");
		w3.image = w3.image.getSubImage(0,15,410,50);
		
		Wall w4 = createWall(600,60,200,20); 
		w4.setImage("newherbe2.png");
		w4.image = w4.image.getSubImage(0,15,410,50);
		
		Wall w5 = createWall(310,105,100,10); 
		w5.setImage("newherbe2.png");
		w5.image = w5.image.getSubImage(0,15,410,50);		
		//createObstacle(420,10,10,90);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(600,0,0.75f);
		//Body b = state.getBodyForUserData(this.character1);
		//state.modifyBodySize(b, 0.8f, 0.8f);		
		this.character2 = addCharacterWithPoints(550,450,0.75f);	
		//character1.setPower(Power.PETIT);
		//character2.setPower(Power.PETIT);
		/*Body b = state.getBodyForUserData(this.character1);
		state.modifyBodySize(b, 0.5f, 0.5f);
		Body b2 = state.getBodyForUserData(this.character2);
		state.modifyBodySize(b2, 0.5f, 0.5f);
		*/
		
		this.setLevelForAllSprites();
		
		InGameIndication indicationRebond = new InGameIndication(250, 100, 
	    "Rebondir peut �tre utile,\n prochaine �tape : NAGER !!");  
		createIndicationFromSprite(rebond,indicationRebond);
		
		InGameIndication indicationNage = new InGameIndication(350, 100, 
	    "Nage et cherche le bouton !\n Apr�s �a fais toi petite... !!");  
		createIndicationFromSprite(nage,indicationNage);
		
		InGameIndication indicationAbsorbe = new InGameIndication(350, 100, 
	    "Tu peux maintenant absorber le pouvoir \nde ton amie simplement en la touchant!");  
		createIndicationFromSprite(absorbe,indicationAbsorbe);
		
		InGameIndication indicationt3 = new InGameIndication(450, 100, 
	    "Ceci est un t�l�porteur, touche le et tu seras \nt�l�port�, Mais attention certains \nt�l�porteurs sont cach�s!!");  
		createIndicationFromSprite(t3,indicationt3);
		
		InGameIndication indicationl1 = new InGameIndication(450, 100, 
	    "Attention, il y a deux leviers, \n il faut trouver les bonnes combinaisons !");  
		createIndicationFromSprite(l1,indicationl1);
		// TODO: indications
	}
}