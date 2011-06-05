package gold;

import mdes.slick.sui.Display;
import mdes.slick.sui.Label;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIGameplay implements UIInterface {
	final static int MENU_X = Global.WINDOW_WIDTH/2-50;
	final static int MENU_Y = Global.GAMEPLAYHEIGHT+80;
	final static int RESTART_Y = Global.GAMEPLAYHEIGHT+45;
	final static int MENU_W = 100;
	final static int MENU_H = 15;
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
	private Label unlockableKeysLabel;
	private Label unlockedKeysLabel;
	private Label allKeysLabel;
	
	private int x, y, w, h;
	private int nbUnlockedKeys;
	private int idLevel;
	private Power p1power = Power.NONE, p2power = Power.NONE;
	
	public UIGameplay(GameContainer gc) {
		backgroundImage = Global.setImage(Global.DEFAULT_UIGAMEPLAY_BACKGROUND_IMAGE);
		leftPlayerImage = Global.setImage(p1power.imageForPower());
		rightPlayerImage = Global.setImage(p2power.imageForPower());
		this.x = 0; this.y = Global.GAMEPLAYHEIGHT;
		this.w = Global.WINDOW_WIDTH; this.h = Global.WINDOW_HEIGHT-Global.GAMEPLAYHEIGHT;
		
		this.display = new Display(gc);
		
		// Labels (utilisation d'une image pour dimensionner)
		Image labelImage = Global.setImage(Global.BUTTON_STANDARD_IMAGE);
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

		Image keysTotalLabelImage = labelImage.getScaledCopy(MENU_W/2, MENU_H);
		allKeysLabel = new Label(keysTotalLabelImage,"Nombre total de fruits Hapsten: "+
				Save.getInstance().getTotalNumberOfUnlockedKeys()+"/"+Save.getInstance().getTotalNumberOfKeys());
		allKeysLabel.setBounds(MENU_X+MENU_W+10, MENU_Y, MENU_X-(POWER_X+POWER_W+10), MENU_H);
		allKeysLabel.setForeground(Color.black);
		allKeysLabel.setHorizontalAlignment(Label.LEFT_ALIGNMENT);
		allKeysLabel.updateAppearance();
		allKeysLabel.pack();
		this.display.add(allKeysLabel);
		allKeysLabel.setImage(null);

		Image keyLabelImage = labelImage.getScaledCopy(MENU_W/2, 25);
		unlockedKeysLabel = new Label(keyLabelImage,null);
		unlockedKeysLabel.setBounds(MENU_X-(MENU_W/2+10), RESTART_Y-5, MENU_W/2, MENU_H);
		unlockedKeysLabel.setForeground(Color.black);
		unlockedKeysLabel.setHorizontalAlignment(Label.RIGHT_ALIGNMENT);
		unlockedKeysLabel.updateAppearance();
		unlockedKeysLabel.pack();
		this.display.add(unlockedKeysLabel);

		unlockableKeysLabel = new Label(keyLabelImage,null);
		unlockableKeysLabel.setBounds(MENU_X+(MENU_W+10), RESTART_Y-5, MENU_W/2, MENU_H);
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
		Label restartLabel = new Label(menuImage,"Recommencer"); //TODO: button
		restartLabel.setBounds(MENU_X,RESTART_Y,MENU_W,MENU_H);
		restartLabel.pack();
		this.display.add(restartLabel);
	}
	public void onEnter(){
		allKeysLabel.setText("Nombre total de fruits Hapsten: "+
				Save.getInstance().getTotalNumberOfUnlockedKeys()+"/"+Save.getInstance().getTotalNumberOfKeys());
		firstPlayerLabel.setText(Save.getInstance().getFirstPlayerName());
		secondPlayerLabel.setText(Save.getInstance().getSecondPlayerName());
		leftPlayerImage = Global.setImage(p1power.imageForPower());
		rightPlayerImage = Global.setImage(p2power.imageForPower());;
	}
	public void setLevelInformation(String _levelName, int _nbUnlockedKeys, int _nbUnlockableKeys, int id){
		nbUnlockedKeys = _nbUnlockedKeys;
		if(Save.getInstance().getLevelWithID(id)!=null){
			nbUnlockedKeys = Save.getInstance().getLevelWithID(id).getUnlockedKeys();
		}
		idLevel=id;
		levelLabel.setText(_levelName);
		levelLabel.setImage(null);
		unlockedKeysLabel.setText("Unlocked fruits:"+_nbUnlockedKeys);
		unlockedKeysLabel.setImage(null);
		unlockableKeysLabel.setText("Unlockable fruits:"+_nbUnlockableKeys);
		unlockableKeysLabel.setImage(null);
		nbUnlockedKeys = _nbUnlockedKeys;
		if(Save.getInstance().getLevelWithID(id)!=null){
			nbUnlockedKeys = Save.getInstance().getLevelWithID(id).getUnlockedKeys();
		}
		idLevel=id;
	}
	
	public void setTempLevelInformation(Power powerPlayer1, Power powerPlayer2, int _nbUnlockedKeys){
		if(_nbUnlockedKeys>nbUnlockedKeys)
			nbUnlockedKeys=_nbUnlockedKeys;
		unlockedKeysLabel.setText("Unlocked fruits:"+(_nbUnlockedKeys));
		allKeysLabel.setText("Nombre total de fruits Hapsten: "+	(Save.getInstance().getTotalNumberOfUnlockedKeys()+nbUnlockedKeys-Save.getInstance().getLevelWithID(idLevel).getUnlockedKeys())
				+"/"+Save.getInstance().getTotalNumberOfKeys());
		if(powerPlayer1.compareTo(p1power)!=0){
			this.p1power = powerPlayer1;
			leftPlayerImage = Global.setImage(p1power.imageForPower());
		}
		if(powerPlayer2.compareTo(p2power)!=0){
			this.p2power = powerPlayer2;
			rightPlayerImage = Global.setImage(p2power.imageForPower());
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
