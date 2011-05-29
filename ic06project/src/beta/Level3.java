package beta;

import java.util.ArrayList;

import org.jbox2d.collision.MassData;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level3 extends Level {
	public Level3(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 3; // Do not forget to update that !!
		this.setBackgroundImage("5759438_s.jpg");
		Ground.setim(Ground.IM2);

		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-2,Global.GAMEPLAYWIDTH,2);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		//Création des grounds
			createGround(0,380,100,120); // Sol 1
			Sprite w1 = new Sprite(0,380,100,50);
			w1.setImage("snow.png");
			w1.image = w1.image.getSubImage(0,17,100,50);
			sprites.add(w1);
			
			createGround(150,380,600,50); // Sol 2
			Sprite w2 = new Sprite(150,380,600,50);
			w2.setImage("snow.png");
			w2.image = w2.image.getSubImage(0,17,550,50);
			sprites.add(w2);
			
			createGround(550,430,200,70); // Sol 4
			createGround(750,480,50,20); // Sol 5
		
		//Création de la source FEU
		Source sourceFeu = createSource(500,450,49,42,Power.FIRE);	
		InGameIndication indicationFeu = new InGameIndication(150, 150, 200, 150, 
	    "Tu as maintenant le\npouvoir du feu, \nà quoi cela peut-il \nbien servir ?");  
		createIndicationFromSprite(sourceFeu,indicationFeu);
		
		//Création des petites plateformes
		createGround(220,310,60,5); // plateforme levier 1
		createGround(330,310,60,5); // plateforme levier 2
		createGround(440,310,60,5); // plateforme levier 3
		createGround(0,310,50,5); // plateforme source Gros
		
	
		
		//Création des leviers et de ce qu'ils déclenchent
		
		Source sourceG = createSource(0,265,40,40,Power.FAT); 		//Pouvoir du Gros
		InGameIndication indicationGros = new InGameIndication(150, 150, 200, 150, 
	    "Tu as maintenant le\n pouvoir d'être plus\n lourd !");  
		createIndicationFromSprite(sourceG,indicationGros);
		Body sourceGBody = state.getBodyForUserData(sourceG); 			//Body du pouvoir du Gros
		Wall wall = createWall(540,250,10,130);							//Mur plateforme sautante
		Body wallBody = state.getBodyForUserData(wall);					//Body du Mur
		Wall wall2 = createWall(450,430,10,68);							//Mur pouvoir du feu
		Body wallBody2 = state.getBodyForUserData(wall2);				//Body du Mur
		

		
			//Création des combinaisons
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
			
		//Création des listes de combi
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
		

		
		LevierCombi levier1=createLevierCombi(240,280,30,30,listB,listT);
		LevierCombi levier2=createLevierCombi(350,280,30,30,listB,listT);
		LevierCombi levier3=createLevierCombi(460,280,30,30,listB,listT);
		ArrayList<LevierCombi> L = new ArrayList<LevierCombi>();
		L.add(levier1);
		L.add(levier2);
		L.add(levier3);
		
		levier1.SetListeLeviers(L);		//levier déclencheur du mur
		levier2.SetListeLeviers(L);		//levier déclencheur du mur
		levier3.SetListeLeviers(L);		//levier déclencheur du pouvoir
		
		levier1.activate();
		levier1.activate();
			
		Ground solMouvant = createGround(570,373,50,7); 		// plateforme mouvante
		Body bodyMouvant = state.getBodyForUserData(solMouvant);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(bodyMouvant);
		MassData md = new MassData();
		md.mass = 100.0f;
		bodyMouvant.setMass(md);
		BoutonElevator B = createBoutonElevator(660, 362, 30, 18, b2, 2, 5000);	//bouton Elevator pour la plateforme
		createGround(565,375,5,5);//cale1
		createGround(620,375,5,5);//cale2
		
		
		
		
		createGround(0,240,550,10); // plateforme centrale
		Sprite w3 = new Sprite(0,240,550,50);
		w3.setImage("snow.png");
		w3.image = w3.image.getSubImage(0,17,550,50);
		sprites.add(w3);
		
		Source sTp = createSource(0,200,60,60,Power.TELEPORTATION);	//pouvoir de teleportation
		
		//escalier
		createGround(60,175,60,5);
		createGround(0,125,60,5);	
		// plateforme en haut
		//createGround(45,70,50,5); 
		
		//construction de la pente la plus haute
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
		sol4.setSlippery(true, "right");
		sol4.setImage("snow.png");
		sol4.image = sol4.image.getSubImage(0,10,550,50);
		
		//plateforme intermédiaire
		Ground inter = createGround(570,160,230,5); 
		inter.addPointToShape(570,157);
		inter.addPointToShape(570,172);
		inter.addPointToShape(800,169);
		inter.addPointToShape(800,154);
		inter.setSlippery(true, "left");
		inter.setImage("snow.png");
		inter.image = sol4.image.getSubImage(0,0,550,50);
		
		
		//creation bonus
		createBonus(770,127,30,30); 
		createBonus(760,450,30,30);
		
		/*//construction de la deuxieme pente ne marche pas
		ArrayList<Vec2> sol4Points2 = new ArrayList<Vec2>();
		sol4Points2.add(new Vec2(200,30));
		sol4Points2.add(new Vec2(200,15));
		sol4Points2.add(new Vec2(-200,-30));
		sol4Points2.add(new Vec2(-200,-15));
		
		Ground sol42 = createGroundWithPoints(450,160,200,20,sol4Points2); // Sol 3
		sol42.addPointToShape(450,165);
		sol42.addPointToShape(450,180);
		sol42.addPointToShape(650,175);
		sol42.addPointToShape(650,160);
		//Ground sol42 = createGround(400,160,400,10);
		sol42.setSlippery(true, "left");*/
		
		
		
		createGround(680,105,120,5); // plateforme arrivée
		Wall arriveeWall = createWall(Global.GAMEPLAYWIDTH-60,40,5,65);
		Body arriveeBody = state.getBodyForUserData(arriveeWall);
		ArrayList<Body> b4 = new ArrayList<Body>();
		b4.add(arriveeBody);
		BoutonPressoir B2 = createBoutonPressoir(680, 87, 30, 18, b4, 3);
		B2.setResteActive(true);
		createExit(Global.GAMEPLAYWIDTH-45,40,45,65);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,330,0.75f);		
		this.character2 = addCharacterWithPoints(200,190,0.75f);	
		
		this.setLevelForAllSprites();
		sTp.setIndication(250,200,
				200, 200, "Appuyez sur A\n ou sur Enter pour \nteleporter votre \nami a vos cotes");
		
		//TODO: indications
	}
}
