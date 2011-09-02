package version1.ui;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import version1.Game;
import version1.Global;
import version1.data.Save;
import version1.sprites.Power;
import version1.states.GameplayState;

/**
 * The UIGameplay is displayed during the game, at the bottom of the screen.
 * It displays some information to the players such as their current power, their name, the obtained bonuses,
 * the number of possible bonuses, the name of the level, etc. 
 * The button for the menu is also displayed there. A Restart button has been added after the first user feedbacks.
 *
 */
public class UIGameplay implements UIInterface {
	final static int MENU_X = Global.WINDOW_WIDTH/2-50;
	final static int MENU_Y = Global.GAMEPLAYHEIGHT+60;
	final static int RESTART_Y = Global.GAMEPLAYHEIGHT+20;
	final static int MENU_W = 100;
	final static int MENU_H = 30;
	final static int POWER_X = 10;
	final static int POWER_Y = 0;
	final static int POWER_W = Global.WINDOW_HEIGHT - Global.GAMEPLAYHEIGHT - 40;
	final static int POWER_H = Global.WINDOW_HEIGHT - Global.GAMEPLAYHEIGHT - 40;
	private Image backgroundImage;
	private Image leftPlayerImage;
	private Image rightPlayerImage;
	
	private Display display;
	private Label firstPlayerLabel;
	private Label secondPlayerLabel;
	private Label levelLabel;
	private Label unlockableBonusLabel;
	private Label unlockedBonusLabel;
	private Label allBonusLabel;
	
	private int x, y, w, h;
	private int nbUnlockedBonus;
	private int idLevel;
	private Power p1power = Power.NONE, p2power = Power.NONE;
	
	public UIGameplay(GameContainer gc) {
		backgroundImage = Global.getImage(Global.DEFAULT_UIGAMEPLAY_BACKGROUND_IMAGE);
		leftPlayerImage = Global.getImage(p1power.imageForPower());
		rightPlayerImage = Global.getImage(p2power.imageForPower());
		this.x = 0; this.y = Global.GAMEPLAYHEIGHT;
		this.w = Global.WINDOW_WIDTH; this.h = Global.WINDOW_HEIGHT-Global.GAMEPLAYHEIGHT;
		
		this.display = new Display(gc);

		Image labelImage = Global.getImage(Global.BUTTON_STANDARD_IMAGE);
		Image playerImage = labelImage.getScaledCopy(POWER_W,25);
		
		firstPlayerLabel = new Label(playerImage,Save.getInstance().getFirstPlayerName());
		firstPlayerLabel.setForeground(Color.black);
		firstPlayerLabel.setBounds(POWER_X,y+POWER_H,POWER_W,25);
		firstPlayerLabel.pack();
		this.display.add(firstPlayerLabel);
		firstPlayerLabel.setImage(null);

		secondPlayerLabel = new Label(playerImage,Save.getInstance().getSecondPlayerName());
		secondPlayerLabel.setForeground(Color.black);
		secondPlayerLabel.setBounds(this.w-(POWER_X+POWER_W+1),y+POWER_H,POWER_W,25);
		secondPlayerLabel.pack();
		this.display.add(secondPlayerLabel);
		secondPlayerLabel.setImage(null);

		Image levelLabelImage = labelImage.getScaledCopy(MENU_X-(POWER_X+POWER_W+10), MENU_H);
		levelLabel = new Label(levelLabelImage,null);
		levelLabel.setBounds(POWER_X+POWER_W, MENU_Y, MENU_X-(POWER_X+POWER_W+10), MENU_H);
		levelLabel.setForeground(Color.black);
		levelLabel.setHorizontalAlignment(Label.RIGHT_ALIGNMENT);
		levelLabel.updateAppearance();
		levelLabel.pack();
		this.display.add(levelLabel);

		Image bonusTotalLabelImage = labelImage.getScaledCopy(MENU_W/2, MENU_H);
		allBonusLabel = new Label(bonusTotalLabelImage,"Nombre total de fruits Hapsten: "+
				Save.getInstance().getTotalNumberOfUnlockedBonus()+"/"+Save.getInstance().getTotalNumberOfBonus());
		allBonusLabel.setBounds(MENU_X+MENU_W+10, MENU_Y, MENU_X-(POWER_X+POWER_W+10), MENU_H);
		allBonusLabel.setForeground(Color.black);
		allBonusLabel.setHorizontalAlignment(Label.LEFT_ALIGNMENT);
		allBonusLabel.updateAppearance();
		allBonusLabel.pack();
		this.display.add(allBonusLabel);
		allBonusLabel.setImage(null);

		Image keyLabelImage = labelImage.getScaledCopy(MENU_W/2, 25);
		unlockedBonusLabel = new Label(keyLabelImage,null);
		unlockedBonusLabel.setBounds(MENU_X-(MENU_W/2+10), RESTART_Y-5, MENU_W/2, MENU_H);
		unlockedBonusLabel.setForeground(Color.black);
		unlockedBonusLabel.setHorizontalAlignment(Label.RIGHT_ALIGNMENT);
		unlockedBonusLabel.updateAppearance();
		unlockedBonusLabel.pack();
		this.display.add(unlockedBonusLabel);

		unlockableBonusLabel = new Label(keyLabelImage,null);
		unlockableBonusLabel.setBounds(MENU_X+(MENU_W+10), RESTART_Y-5, MENU_W/2, MENU_H);
		unlockableBonusLabel.setForeground(Color.black);
		unlockableBonusLabel.setHorizontalAlignment(Label.LEFT_ALIGNMENT);
		unlockableBonusLabel.updateAppearance();
		unlockableBonusLabel.pack();
		this.display.add(unlockableBonusLabel);
		
		// Buttons
		Image menuImage = labelImage.getScaledCopy(MENU_W, MENU_H);
		Label menuLabel = new Label(menuImage,"Menu");
		menuLabel.setBounds(MENU_X,MENU_Y,MENU_W,MENU_H);
		menuLabel.pack();
		this.display.add(menuLabel);
		
		Label restartLabel = new Label(menuImage,"Recommencer");
		restartLabel.setBounds(MENU_X,RESTART_Y,MENU_W,MENU_H);
		restartLabel.pack();
		this.display.add(restartLabel);
	}
	public void onEnter(){
		allBonusLabel.setText("Nombre total de fruits Hapsten: "+
				Save.getInstance().getTotalNumberOfUnlockedBonus()+"/"+Save.getInstance().getTotalNumberOfBonus());
		firstPlayerLabel.setText(Save.getInstance().getFirstPlayerName());
		secondPlayerLabel.setText(Save.getInstance().getSecondPlayerName());
		leftPlayerImage = Global.getImage(p1power.imageForPower());
		rightPlayerImage = Global.getImage(p2power.imageForPower());;
	}
	public void setLevelInformation(String _levelName, int _nbUnlockedBonus, int _nbUnlockableBonus, int id){
		nbUnlockedBonus = _nbUnlockedBonus;
		if(Save.getInstance().getLevelWithID(id)!=null){
			nbUnlockedBonus = Save.getInstance().getLevelWithID(id).getUnlockedBonus();
		}
		idLevel=id;
		levelLabel.setText(_levelName);
		levelLabel.setImage(null);
		unlockedBonusLabel.setText("Fruits trouves:"+_nbUnlockedBonus);
		unlockedBonusLabel.setImage(null);
		unlockableBonusLabel.setText("Fruits a trouver:"+_nbUnlockableBonus);
		unlockableBonusLabel.setImage(null);
		nbUnlockedBonus = _nbUnlockedBonus;
		if(Save.getInstance().getLevelWithID(id)!=null){
			nbUnlockedBonus = Save.getInstance().getLevelWithID(id).getUnlockedBonus();
		}
		idLevel=id;
	}
	
	public void setTempLevelInformation(Power powerPlayer1, Power powerPlayer2, int _nbUnlockedBonus){
		if(_nbUnlockedBonus>nbUnlockedBonus)
			nbUnlockedBonus = _nbUnlockedBonus;
		unlockedBonusLabel.setText("Fruits trouves:"+(_nbUnlockedBonus));
		allBonusLabel.setText("Nombre total de fruits Hapsten: "+	(Save.getInstance().getTotalNumberOfUnlockedBonus()
				+nbUnlockedBonus-Save.getInstance().getLevelWithID(idLevel).getUnlockedBonus())
				+"/"+Save.getInstance().getTotalNumberOfBonus());
		if(powerPlayer1.compareTo(p1power)!=0){
			this.p1power = powerPlayer1;
			leftPlayerImage = Global.getImage(p1power.imageForPower());
		}
		if(powerPlayer2.compareTo(p2power)!=0){
			this.p2power = powerPlayer2;
			rightPlayerImage = Global.getImage(p2power.imageForPower());
		}
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		backgroundImage.draw(x,y,w,h);
		
		// Left bubble
		if(leftPlayerImage != null){
			leftPlayerImage.draw(x+POWER_X,y+POWER_Y,POWER_W,POWER_H);
		}
		
		// Right bubble
		if(rightPlayerImage != null){
			rightPlayerImage.draw(x+w-(POWER_X+POWER_W),y+POWER_Y,POWER_W,POWER_H);			
		}
		
		// Labels and buttons
		this.display.render(gc, g);
	}	
	
	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		int selection = -1;
		if((x >= MENU_X && x <= (MENU_X + MENU_W)) 
				&&	(y >= MENU_Y && y <= (MENU_Y + MENU_H))){
			state.setPaused(true);			
		}	
		else if((x >= MENU_X && x <= (MENU_X + MENU_W)) 
				&&	(y >= RESTART_Y && y <= (RESTART_Y + MENU_H))){
			selection = Game.SHOULD_RESTART;
		}	
		
		return selection;
	}
}
