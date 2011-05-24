package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

public class Level4 extends Level {

	public Level4(GameplayState state, LevelSave model) {
		super(state,model);
		this.levelID = 4;
		this.inTheDarkness = true;
		this.setBackgroundImage("1770925_s.jpg");
		Ground.setim(Ground.IM1);
		
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
		createGround(260,200,20,130); // 13
		createGround(280,250,60,30); // 14
		createGround(340,260,400,10); // 15
		createGround(420,200,260,10); // 16
		createGround(280,330,100,10); // 17
		createGround(540,320,260,20); // 18
		createGround(430,370,80,20); // 19
		createGround(340,390,320,20); // 20
		createGround(740,390,60,20); // 21
		createGround(80,165,100,35); // 22
		createGround(0,220,40,80); // 23
		createGround(40,250,160,20); // 24
		createGround(0,340,150,20); // 25
		createGround(200,330,80,30); // 26
		createGround(120,410,110,20); // 27
		createGround(0,360,40,70); // 28
		createGround(280,460,80,30); // 29
		createGround(160,115,40,50); // 30
		
		// Place obstacles
		Obstacle o1 = createObstacle(200,250,60,20);
		o1.setImage("arbre-horizontal.png");
		ArrayList<Body> b1 = new ArrayList<Body>();
		b1.add(myState.getBodyForUserData(o1));
		createLevier(420,130,30,30,b1);

		Obstacle o2 = createObstacle(40,270,60,70);
		//createGround(40,250,160,20); // 24
		o2.setImage("new-sol.png");
		o2.image = o2.image.getSubImage(o2.x,250,o2.w,o2.h);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(myState.getBodyForUserData(o2));
		createLevier(120,135,30,30,b2);

		Obstacle o3 = createObstacle(540,270,40,50);
		o3.setImage("arbre-droit.png");
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(myState.getBodyForUserData(o3));
		createLevier(120,460,30,30,b3);
		
		// Place l'eau
		SourceMortelle s = createSourceMortelle(320,170,60,20);
		s.setAnimation("waves.png", 300, 300);
		
		// Place sources
		Source sLight = createSource(340,90-42,49,42,Power.LIGHT);
		sLight.setLightSize(100);
		createSource(290,290,49,42,Power.INVISIBLE);
		
		//place Sortie
		createExit(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-56,32,46);
		
		// Place bonus
		createBonus(10,195,25,25);
		createBonus(10,465,25,25);
		createBonus(290,225,25,25);
		
		// Place des torches
		Torch torch1 = new Torch(280,210);
		this.backgroundSprites.add(torch1);		
		Torch torch2 = new Torch(Global.GAMEPLAYWIDTH-70,Global.GAMEPLAYHEIGHT-70);
		torch2.setLightSize(110);
		this.backgroundSprites.add(torch2);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,40,0.7f);		
		this.character2 = addCharacterWithPoints(10,40,0.7f);	
		this.character1.setLightSize(this.character1.h);
		this.character2.setLightSize(this.character2.h);

		this.setLevelForAllSprites();
	}

}
