package alpha;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class UIGameplay implements UIInterface {
	protected Image backgroundImage;
	protected Image leftBubbleBackgroundImage;
	protected Image leftPlayerBackgroundImage;
	protected Image leftPlayerImage;
	protected Image rightBubbleBackgroundImage;
	protected Image rightPlayerBackgroundImage;
	protected Image rightPlayerImage;
	protected Image menuBackgroundImage;
	protected int x, y, w, h;
	protected String levelName;
	protected int nbUnlockableKeys, nbUnlockedKeys;
	
	public UIGameplay() {
		backgroundImage = Global.setImage("06_wood_artshare_ru.jpg");
		leftBubbleBackgroundImage = Global.setImage("bubble.jpg");
		rightBubbleBackgroundImage = Global.setImage("bubble.jpg");
		menuBackgroundImage = Global.setImage("blur11.jpg");
		this.x = 0; this.y = Global.GAMEPLAYHEIGHT;
		this.w = Global.WINDOW_WIDTH; this.h = Global.WINDOW_HEIGHT-Global.GAMEPLAYHEIGHT;
	}
	public void setLevelInformation(String _levelName, int _nbUnlockableKeys, int _nbUnlockedKeys){
		this.levelName = _levelName;
		this.nbUnlockableKeys = _nbUnlockableKeys;
		this.nbUnlockedKeys = _nbUnlockedKeys;
	}
	
	@Override
	public void render(Graphics g) {
		backgroundImage.draw(x,y,w,h);
		int ratio = leftBubbleBackgroundImage.getWidth()/leftBubbleBackgroundImage.getHeight();
		int bubbleH = h-20;
		int bubbleXoffset = 10;
		
		// Left part
		leftBubbleBackgroundImage.draw(bubbleXoffset,y,ratio*bubbleH,bubbleH);
		g.setColor(Color.blue);
		g.drawString(Save.getInstance().getFirstPlayerName(), bubbleXoffset, y+bubbleH);

		// Middle part
		menuBackgroundImage.draw(GameplayState.MENU_X,GameplayState.MENU_Y,GameplayState.MENU_W,GameplayState.MENU_H);
		g.setColor(Color.black);
		g.drawString(""+nbUnlockedKeys, w/2-20-20, y+20);
		g.drawString("Menu", w/2-18, y+h-20);
		g.drawString(""+nbUnlockableKeys, w/2+20+20, y+20);
		g.drawString(levelName, 100, y+h-20);
		g.drawString("Clés:"+Save.getInstance().getTotalNumberOfUnlockedKeys()+"/"+Save.getInstance().getTotalNumberOfKeys(), 500, y+h-20);
		
		// Right part
		rightBubbleBackgroundImage.draw(w-ratio*bubbleH-bubbleXoffset,y,ratio*bubbleH,bubbleH);
		g.setColor(Color.green);
		g.drawString(Save.getInstance().getSecondPlayerName(), w-ratio*bubbleH-bubbleXoffset, y+bubbleH);	
	}	
}
