package gold;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level5 extends Level {
	public Level5(GameplayState state, LevelSave model){	
		super(state,model);
		this.levelID = 5; // Do not forget to update that !!
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
		
		//plateforme en haut à gauche
		createGround(0,100,100,10);	
		//plateforme en haut à droite
		createGround(200,100,Global.GAMEPLAYWIDTH-200,10);	
		
		//Pouvoir du dédoublement
		Source dedouble = createSource(0,40,60,60,Power.DOUBLE);
		
		PlateformeMissile platmiss=new PlateformeMissile(0,110,80,10);
		ArrayList<Vec2> shape = new ArrayList<Vec2>();
		shape.add(new Vec2(-25,-5));
		shape.add(new Vec2(-25,5));
		shape.add(new Vec2(25,5));
		shape.add(new Vec2(25,-5));
		shape.add(new Vec2(0,0));
		
		sprites.add(platmiss);
		myState.addGround(platmiss);
		Body maplat = state.getBodyForUserData(platmiss);
		ArrayList<Body> miss=new ArrayList<Body>() ;
		miss.add(maplat);
		createBoutonBombarde(Global.GAMEPLAYWIDTH-100,85,30,15,miss);
		createBoutonDeplace(0,Global.GAMEPLAYHEIGHT-115,30,15,miss,"left");
		createBoutonDeplace(Global.GAMEPLAYWIDTH-30,Global.GAMEPLAYHEIGHT-115,30,15,miss,"right");
		BoutonCharge bouton=new BoutonCharge(Global.GAMEPLAYWIDTH-30,85,30,15,miss);
		sprites.add(bouton);
		myState.addBoutonPressoir(bouton);
		
		// bloc en bas à gauche
		createGround(0,Global.GAMEPLAYHEIGHT-100,50,100);		
		// bloc en bas à droite
		createGround(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-100,50,100);
		//petit bloc en bas à gauche
		createGround(50,Global.GAMEPLAYHEIGHT-50,50,50);	
		//petit bloc en bas à droite
		createGround(Global.GAMEPLAYWIDTH-100,Global.GAMEPLAYHEIGHT-50,50,50);
		
		
		Ground2 g1 = new Ground2(120,Global.GAMEPLAYHEIGHT-180,100,10);
		sprites.add(g1);
		myState.addGround(g1);
		Ground2 g2 = new Ground2(280,Global.GAMEPLAYHEIGHT-220,100,10);
		sprites.add(g2);
		myState.addGround(g2);
		
		
		
		
		
		// Place the first character
		this.character1 = addCharacterWithPoints(550,0,0.75f);		
		this.character2 = addCharacterWithPoints(550,0,0.75f);
		

		Witch boss = new Witch(550,450,50,50);
		sprites.add(boss);
		myState.addMonster(boss, 1.0f);	
		boss.setBorns(new Vec2(0,450), new Vec2(800,450));
		boss.setSpeed(new Vec2(-10,0));
		
		this.setLevelForAllSprites();
		
		
		//indications
		dedouble.setIndication(420, 120, "Appuyez sur A ou sur Enter pour creer votre \ndouble a vos cotes pendant 10 secondes.\nAttention une fois utilise vous n'avez plus\n le pouvoir !");
		
		// TODO: indications
	}
}