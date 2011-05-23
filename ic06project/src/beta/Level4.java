package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level4 extends Level {

	public Level4(GameplayState state, LevelSave model) {
		super(state,model);
		this.levelID = 4;
		//this.inTheDarkness = true;
		this.setBackgroundImage("1770925_s.jpg");
		
		// Place a ground wall
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		// Left wall
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		// Right wall
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		// Celling
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// place entree
		Sprite entree = new Sprite(10,44,32,46);
		entree.setImage("porte.png");
		entree.setLightSize(100);
		this.backgroundSprites.add(entree);
		
		// Sols
		createGround(0,90,200,25); // 1
		createGround(280,90,200,20); // 2
		createGround(480,50,20,60); // 3
		createGround(500,50,120,10); // 4
		createGround(680,50,120,10); // 5
		createGround(380,110,20,60); // 6
		createGround(380,160,140,40); // 7
		createGround(565,110,235,10); // 8
		createGround(720,170,80,20); // 9
		createGround(520,170,160,30); // 10
		createGround(320,190,60,10); // 11
		createGround(260,180,60,20); // 12
		createGround(260,200,20,140); // 13
		createGround(280,250,60,30); // 14
		createGround(340,260,400,10); // 15
		createGround(420,200,260,10); // 16
		createGround(280,330,100,10); // 17
		createGround(540,320,260,20); // 18
		createGround(420,370,40,20); // 19
		createGround(280,390,380,20); // 20
		createGround(700,390,100,20); // 21
		createGround(80,165,100,35); // 22
		createGround(0,220,40,80); // 23
		createGround(40,250,160,10); // 24
		createGround(0,340,150,20); // 25
		createGround(200,340,60,20); // 26
		//createGround(0,340,150,10); // 27
		createGround(0,360,40,70); // 28
		createGround(160,115,40,50); // 30
		
		// Place obstacles
		Obstacle o1 = createObstacle(200,250,60,20);
		o1.setImage("arbre-horizontal.png");
		Ground o2 = createGround(40,260,60,80); // 02
		
		// Place l'eau
		SourceMortelle s = createSourceMortelle(320,170,60,20);
		s.setAnimation("waves.png", 300, 300);
		
		// Place sources
		Source sLight = createSource(340,90-42,49,42,Power.LIGHT);
		sLight.setLightSize(100);
		
		//place Sortie
		//createExit(Global.GAMEPLAYWIDTH-65,Global.GAMEPLAYHEIGHT-65-10,32,46);
		
		// Place bonus
		//createBonus(50,Global.GAMEPLAYHEIGHT-110,25,25);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,40,0.7f);		
		this.character2 = addCharacterWithPoints(10,40,0.7f);	
		this.character1.setLightSize(this.character1.h);
		this.character2.setLightSize(this.character2.h);

		this.setLevelForAllSprites();
	}

}
