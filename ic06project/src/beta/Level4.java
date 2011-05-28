package beta;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;

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
		
		// Sols
		createGround(0,90,220,25); // 1
		createGround(280,90,200,20); // 2
		createGround(480,50,20,60); // 3
		createGround(500,50,120,10); // 4
		createGround(680,50,120,10); // 5
		createGround(380,110,20,60); // 6
		createGround(380,160,140,40); // 7
		createGround(565,110,235,10); // 8
		createGround(700,170,100,20); // 9
		createGround(500,170,150,30); // 10
		createGround(320,190,60,10); // 11
		createGround(255,180,65,20); // 12
		createGround(255,200,25,130); // 13
		createGround(280,250,60,30); // 14
		createGround(340,260,400,10); // 15
		createGround(420,200,230,10); // 16
		createGround(280,330,100,10); // 17
		createGround(540,320,260,20); // 18
		createGround(430,370,80,20); // 19
		createGround(340,390,320,20); // 20
		createGround(740,390,60,20); // 21
		createGround(80,165,100,35); // 22
		createGround(0,220,40,65); // 23
		createGround(40,250,160,20); // 24
		createGround(0,340,150,20); // 25
		createGround(195,330,85,30); // 26
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
		o2.setImage("sol-pourri-v56.png");
		o2.image = o2.image.getSubImage(o2.x,250,o2.w,o2.h);
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(myState.getBodyForUserData(o2));
		createLevier(120,135,30,30,b2);

		Obstacle o3 = createObstacle(540,270,40,50);
		o3.setImage("arbre-droit.png");
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(myState.getBodyForUserData(o3));
		createLevier(120,460,30,30,b3);
		
		// Place destructibles
		Destructible d1 = createDestructible(42,360,76,70);
		Destructible d2 = createDestructible(60,430,60,60);
		d2.addDestructible(d1);
		Destructible d3 = createDestructible(670,340,60,30);
		Destructible d4 = createDestructible(640,370,120,20);
		d4.addDestructible(d3);
		
		// Place l'eau
		SourceMortelle s = createSourceMortelle(320,185,60,5);
		s.setAnimation("waves.png", 300, 300);
		s.setFilter(Color.green);
		
		// Place sources
		Source sLight = createSource(340,90-42,60,60,Power.LIGHT);
		sLight.setLightSize(100);
		Source sInvisible = createSource(290,290,40,40,Power.INVISIBLE);
		Source sDestructor = createSource(0,298,60,60,Power.DESTRUCTOR);
		
		// Place Sortie
		createExit(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-56,32,46);
		
		// Place bonus
		createBonus(10,195,25,25);
		createBonus(10,465,25,25);
		createBonus(290,225,25,25);
		
		// Place les monstres
		Monster m1 = createMonster(565,74,30,30);
		m1.setImage("6241879_s.jpg");
		m1.setSpeed(new Vec2(-10,+1.0f));
		m1.setBorns(new Vec2(565,74-1), new Vec2(800-m1.w-1,74+4));
		
		Monster m2 = createMonster(520,134,30,30);
		m2.setImage("6241879_s.jpg");
		m2.setSpeed(new Vec2(10,+1.0f));
		m2.setBorns(new Vec2(m2.x,133), new Vec2(800-m2.w-1,138));

		Monster m3 = createMonster(340,220,30,30);
		m3.setImage("6241879_s.jpg");
		m3.setSpeed(new Vec2(10,0));
		m3.setBorns(new Vec2(m3.x,0), new Vec2(740-m3.w-1,0));
		
		Monster m = createMonster(Global.GAMEPLAYWIDTH-120,Global.GAMEPLAYHEIGHT-70,50,60);
		//m.setSpeed(new Vec2(-8,0));
		//m.setBorns(new Vec2(m.x-300,m.y), new Vec2(m.x,m.y));
		
		// Place des torches
		
		// place entree
		Torch torch0 = new Torch(10,44);
		this.backgroundSprites.add(torch0);
		Torch torch1 = new Torch(280,210);
		this.backgroundSprites.add(torch1);		
		Torch torch2 = new Torch(Global.GAMEPLAYWIDTH-70,Global.GAMEPLAYHEIGHT-70);
		torch2.setLightSize(110);
		this.backgroundSprites.add(torch2);
		
		// Place the first character
		this.character1 = addCharacterWithPoints(10,50,0.7f);		
		this.character2 = addCharacterWithPoints(10,50,0.7f);	
		this.character1.setLightSize(this.character1.h);
		this.character2.setLightSize(this.character2.h);

		this.setLevelForAllSprites();
		InGameIndication indication0 = new InGameIndication(150, 100, 200, 150, 
				"Il fait sombre!\n Trouve de la lumiere \npour mieux voir \nautour de toi!");
		createIndicationSprite(this.character1.x+this.character1.w+20,this.character1.y, 40,40, indication0);
		sInvisible.setIndication(sInvisible.x,sInvisible.y,250,150,"Pouvoir d'invisibilite! \nLes monstres ne devraient \nplus te voir!");
		sDestructor.setIndication(sInvisible.x,sInvisible.y,250,150,"Pouvoir de destruction! \nTu peux detruire \ncertains obstacles!");
	}
}
