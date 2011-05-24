package beta;

import org.newdawn.slick.Color;

import mdes.slick.sui.Label;

public class InGameIndication extends Label {
	private String text;
	private final static String DEFAULT_IMAGE = "scroll_large.png";
	
	public InGameIndication(int x, int y, String _text){
		super(_text);
		text=_text;
		this.setForeground(Color.black);
		this.setImage(Global.setImage(DEFAULT_IMAGE).getScaledCopy((int)(this.getTextWidth()*1.4), (int)(this.getTextHeight()+40)));
		this.setBounds(x, y, (float)(this.getTextWidth()*1.4), (float) (this.getTextHeight()+40));
		this.updateAppearance();
		this.setToolTipText(_text);
		this.pack();
	}
	protected String getTexte(){
		return text;
	}
}
