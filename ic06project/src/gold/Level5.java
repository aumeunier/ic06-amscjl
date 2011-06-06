package gold;

import org.jbox2d.common.Vec2;

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
		
		
		
		// bloc en bas à gauche
		createGround(0,Global.GAMEPLAYHEIGHT-100,50,100);		
		// bloc en bas à droite
		createGround(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-100,50,100);
		//petit bloc en bas à gauche
		createGround(50,Global.GAMEPLAYHEIGHT-50,50,50);	
		//petit bloc en bas à droite
		createGround(Global.GAMEPLAYWIDTH-100,Global.GAMEPLAYHEIGHT-50,50,50);
		
		
		createGround(Global.GAMEPLAYWIDTH/2,Global.GAMEPLAYHEIGHT-50,50,50);
		
		
		
		
		
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
		dedouble.setIndication(200, 150, "Appuyez sur A\n ou sur Enter pour \ncreer votre \ndouble a vos cotes");
		
		// TODO: indications
	}
}