package alpha;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIGameplay implements UIInterface {
	final static int MENU_X = Global.WINDOW_WIDTH/2-50;
	final static int MENU_Y = Global.WINDOW_HEIGHT-20;
	final static int MENU_W = 100;
	final static int MENU_H = 15;
	final static int BUBBLE_X = 10;
	final static int BUBBLE_Y = 0;
	final static int BUBBLE_W = Global.WINDOW_HEIGHT - Global.GAMEPLAYHEIGHT - 40;
	final static int BUBBLE_H = Global.WINDOW_HEIGHT - Global.GAMEPLAYHEIGHT - 40;
	protected Image backgroundImage;
	protected Image leftBubbleBackgroundImage;
	protected Image leftPlayerBackgroundImage;
	protected Image leftPlayerImage;
	protected Image rightBubbleBackgroundImage;
	protected Image rightPlayerBackgroundImage;
	protected Image rightPlayerImage;
	protected Image menuBackgroundImage;
	
	private Display display;
	protected Label levelLabel;
	protected Label unlockableKeysLabel;
	protected Label unlockedKeysLabel;
	
	protected int x, y, w, h;
	protected int nbUnlockableKeys, nbUnlockedKeys;
	
	public UIGameplay(GameContainer gc) {
		backgroundImage = Global.setImage("06_wood_artshare_ru.jpg");
		leftBubbleBackgroundImage = Global.setImage("bubble.jpg");
		rightBubbleBackgroundImage = Global.setImage("bubble.jpg");
		menuBackgroundImage = Global.setImage("blur11.jpg");
		this.x = 0; this.y = Global.GAMEPLAYHEIGHT;
		this.w = Global.WINDOW_WIDTH; this.h = Global.WINDOW_HEIGHT-Global.GAMEPLAYHEIGHT;
		
		this.display = new Display(gc);
		
		// Labels
		Image labelImage = Global.setImage("blur11.jpg");
		Image playerImage = labelImage.getScaledCopy(BUBBLE_W,25);
		Label firstPlayerLabel = new Label(playerImage,Save.getInstance().getFirstPlayerName());
		firstPlayerLabel.setForeground(Color.black);
		firstPlayerLabel.setBounds(BUBBLE_X,y+BUBBLE_H,BUBBLE_W,25);
		firstPlayerLabel.pack();
		this.display.add(firstPlayerLabel);
		firstPlayerLabel.setImage(null);

		Label secondPlayerLabel = new Label(playerImage,Save.getInstance().getSecondPlayerName());
		secondPlayerLabel.setForeground(Color.black);
		secondPlayerLabel.setBounds(this.w-(BUBBLE_X+BUBBLE_W+1),y+BUBBLE_H,BUBBLE_W,25);
		secondPlayerLabel.pack();
		this.display.add(secondPlayerLabel);
		secondPlayerLabel.setImage(null);

		Image levelLabelImage = labelImage.getScaledCopy(MENU_X-(BUBBLE_X+BUBBLE_W+10), MENU_H);
		levelLabel = new Label(levelLabelImage,null);
		levelLabel.setBounds(BUBBLE_X+BUBBLE_W, Global.WINDOW_HEIGHT-MENU_H, MENU_X-(BUBBLE_X+BUBBLE_W+10), MENU_H);
		levelLabel.setForeground(Color.black);
		levelLabel.setHorizontalAlignment(Label.RIGHT_ALIGNMENT);
		levelLabel.updateAppearance();
		levelLabel.pack();
		this.display.add(levelLabel);

		Image keysTotalLabelImage = labelImage.getScaledCopy(MENU_W/2, MENU_H);
		Label allKeysLabel = new Label(keysTotalLabelImage,"Nombre total de clees: "+
				Save.getInstance().getTotalNumberOfUnlockedKeys()+"/"+Save.getInstance().getTotalNumberOfKeys());
		allKeysLabel.setBounds(MENU_X+MENU_W+10, Global.WINDOW_HEIGHT-MENU_H, MENU_X-(BUBBLE_X+BUBBLE_W+10), MENU_H);
		allKeysLabel.setForeground(Color.black);
		allKeysLabel.setHorizontalAlignment(Label.LEFT_ALIGNMENT);
		allKeysLabel.updateAppearance();
		allKeysLabel.pack();
		this.display.add(allKeysLabel);
		allKeysLabel.setImage(null);

		Image keyLabelImage = labelImage.getScaledCopy(MENU_W/2, 25);
		unlockedKeysLabel = new Label(keyLabelImage,null);
		unlockedKeysLabel.setBounds(MENU_X-(MENU_W/2+10), Global.GAMEPLAYHEIGHT+40, MENU_W/2, MENU_H);
		unlockedKeysLabel.setForeground(Color.black);
		unlockedKeysLabel.setHorizontalAlignment(Label.RIGHT_ALIGNMENT);
		unlockedKeysLabel.updateAppearance();
		unlockedKeysLabel.pack();
		this.display.add(unlockedKeysLabel);

		unlockableKeysLabel = new Label(keyLabelImage,null);
		unlockableKeysLabel.setBounds(MENU_X+(MENU_W+10), Global.GAMEPLAYHEIGHT+40, MENU_W/2, MENU_H);
		unlockableKeysLabel.setForeground(Color.black);
		unlockableKeysLabel.setHorizontalAlignment(Label.LEFT_ALIGNMENT);
		unlockableKeysLabel.updateAppearance();
		unlockableKeysLabel.pack();
		this.display.add(unlockableKeysLabel);
		
		// Buttons
		Image menuImage = labelImage.getScaledCopy(MENU_W, MENU_H);
		Label menuLabel = new Label(menuImage,"Menu"); //TODO: button
		menuLabel.setBounds(MENU_X,MENU_Y,MENU_W,MENU_H);
		menuLabel.pack();
		this.display.add(menuLabel);
		
	}
	public void setLevelInformation(String _levelName, int _nbUnlockableKeys, int _nbUnlockedKeys){
		levelLabel.setText(_levelName);
		levelLabel.setImage(null);
		unlockedKeysLabel.setText("Unlocked keys:"+_nbUnlockedKeys);
		unlockedKeysLabel.setImage(null);
		unlockableKeysLabel.setText("Unlockable keys:"+_nbUnlockableKeys);
		unlockableKeysLabel.setImage(null);
	}
	public void setTempLevelInformation(String powerPlayer1, String powerPlayer2, int _nbUnlockedKeys){
		unlockedKeysLabel.setText("Unlocked keys:"+_nbUnlockedKeys);
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) {
		backgroundImage.draw(x,y,w,h);
		
		// Left bubble
		leftBubbleBackgroundImage.draw(BUBBLE_X,y,BUBBLE_W,BUBBLE_H);
		
		// Right bubble
		rightBubbleBackgroundImage.draw(w-(BUBBLE_W+BUBBLE_X),y,BUBBLE_W,BUBBLE_H);
		
		// Labels and buttons
		this.display.render(gc, g);
	}	
	
	@Override
	public int mouseClicked(int button, int x, int y, int clickCount, GameplayState state) {
		if((x >= MENU_X && x <= (MENU_X + MENU_W)) 
				&&	(y >= MENU_Y && y <= (MENU_Y + MENU_H))){
			state.setPaused(true);			
		}	
		
		return -1;
	}
}
