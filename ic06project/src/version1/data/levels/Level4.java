package version1.data.levels;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.newdawn.slick.Color;

import version1.Global;
import version1.data.LevelSave;
import version1.sprites.Power;
import version1.sprites.alive.monsters.Monster;
import version1.sprites.decor.Ground;
import version1.sprites.decor.InGameIndication;
import version1.sprites.decor.Obstacle;
import version1.sprites.decor.Torch;
import version1.sprites.interaction.Destructible;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.sources.DeathlySource;
import version1.states.GameplayState;

/**
 * This level introduces new gameplay mechanics: darkness and monsters. 
 * The players can't see the whole level because it is plunged in darkness, they can only see close to them.
 * However, a new power let them shine a little more than by default. That power does not disappear contrary to the others
 * if they get a new power.
 * Also, monsters have appeared and will kill the player if he touches them (same as the water). 
 * However, the players can get the invisible power to go through the monsters without getting killed.
 * Finally, a new type of blocks has appeared: destructible blocks, which can be destroyed witht the Destructor power.
 * 
 * Spoiler: one player needs to go on the left side, another on the right side in order to finish the level (switches).
 * It can be hard to get all the bonuses (requires to use the destructor and the invisible power). 
 * The last bonus is obtained by killing the boss...
 *
 */
public class Level4 extends Level {
	public Level4(GameplayState state, LevelSave model) {
		// Initialize the level 
		super(state,model);
		this.levelID = 4;
		this.inTheDarkness = true;
		this.setBackgroundImage("1770925_s.jpg");
		Ground.setim(Ground.IM1);
		
		// Place the walls around the level
		createWall(0,Global.GAMEPLAYHEIGHT-10,Global.GAMEPLAYWIDTH,10);		
		createWall(-10,0,10,Global.GAMEPLAYHEIGHT);
		createWall(Global.GAMEPLAYWIDTH,0,10,Global.GAMEPLAYHEIGHT);
		createWall(0,-10,Global.GAMEPLAYWIDTH,10);
		
		// Grounds (numbers correspond to the map)
		createGround(0,90,220,25); // 1
		createGround(280,90,200,20); // 2
		createGround(480,50,20,60); // 3
		createGround(500,50,120,10); // 4
		createGround(680,50,120,10); // 5
		createGround(380,110,20,60); // 6
		createGround(380,160,140,40); // 7
		createGround(565,110,235,10); // 8
		createGround(700,170,100,20); // 9
		createGround(520,170,130,30); // 10
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
		o2.setCroppedImage((int)o2.X(),250,(int)o2.W(),(int)o2.H());
		ArrayList<Body> b2 = new ArrayList<Body>();
		b2.add(myState.getBodyForUserData(o2));
		createLevier(120,135,30,30,b2);

		Obstacle o3 = createObstacle(540,270,40,50);
		o3.setImage("arbre-droit.png");
		ArrayList<Body> b3 = new ArrayList<Body>();
		b3.add(myState.getBodyForUserData(o3));
		createLevier(120,460,30,30,b3);
		
		// Place Obstacles that can be destructed
		Destructible d1 = createDestructible(42,360,76,70);
		Destructible d2 = createDestructible(60,430,60,60);
		d2.addDestructible(d1);
		Destructible d3 = createDestructible(670,340,60,30);
		Destructible d4 = createDestructible(640,370,120,20);
		d4.addDestructible(d3);
		
		// Place water
		DeathlySource s = createDeathlySource(320,185,60,5);
		s.setAnimation("waves.png", 300, 300);
		s.setFilter(Color.green);
		
		// Place sources
		Source sLight = createSource(340,90-42,60,60,Power.LIGHT);
		sLight.setLightSize(100);
		Source sInvisible = createSource(290,290,40,40,Power.INVISIBLE);
		Source sDestructor = createSource(0,298,60,60,Power.DESTRUCTOR);
		
		// Place the exit
		createExit(Global.GAMEPLAYWIDTH-50,Global.GAMEPLAYHEIGHT-56,32,46);
		
		// Place bonuses
		createBonus(10,195,25,25);
		createBonus(10,465,25,25);
		createBonus(290,225,25,25);
		
		// Place monsters
		Monster m1 = createMonster(565,74,30,30,0.2f);
		m1.setImage("6241879_s.jpg");
		m1.setSpeed(new Vec2(-10,+1.0f));
		m1.setBorns(new Vec2(565,74-1), new Vec2(800-m1.W()-1,74+4));
		
		Monster m2 = createMonster(520,134,30,30,0.2f);
		m2.setImage("6241879_s.jpg");
		m2.setSpeed(new Vec2(10,+1.0f));
		m2.setBorns(new Vec2(m2.X(),133), new Vec2(800-m2.W()-1,138));

		Monster m3 = createMonster(340,220,30,30,1.0f);
		m3.setImage("6241879_s.jpg");
		m3.setSpeed(new Vec2(10,0));
		m3.setBorns(new Vec2(m3.X(),0), new Vec2(740-m3.W()-1,0));
		
		createMonster(Global.GAMEPLAYWIDTH-120,Global.GAMEPLAYHEIGHT-70,50,60,1.0f);
		
		// Place light sources
		Torch torch0 = new Torch(10,44);
		this.backgroundSprites.add(torch0);
		Torch torch1 = new Torch(280,210);
		this.backgroundSprites.add(torch1);		
		Torch torch2 = new Torch(Global.GAMEPLAYWIDTH-70,Global.GAMEPLAYHEIGHT-70);
		torch2.setLightSize(110);
		this.backgroundSprites.add(torch2);
		
		// Place the characters and change their size
		this.character1 = addCharacterWithPoints(10,50,0.7f);		
		this.character2 = addCharacterWithPoints(10,50,0.7f);	
		this.character1.setLightSize((int) this.character1.H());
		this.character2.setLightSize((int) this.character2.H());

		// Place in game indications
		this.setLevelForAllSprites();
		InGameIndication indication0 = new InGameIndication(300, 100, 
				"Il fait sombre! Trouve de la lumiere \npour mieux voir autour de toi!");
		createIndicationSprite((int) (this.character1.X()+this.character1.W()+20),(int)this.character1.Y(), 40,40, indication0);
		sLight.setIndication(300,100,"Tu as trouve de la lumiere! \nLa lumiere reste si tu prends \nun autre pouvoir!");
		sInvisible.setIndication(300,100,"Pouvoir d'invisibilite! \nLes monstres ne devraient plus te voir!");
		sDestructor.setIndication(300,100,"Pouvoir de destruction! \nTu peux detruire certains obstacles!");
	}
}
