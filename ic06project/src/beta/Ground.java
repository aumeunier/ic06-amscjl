package beta;


public class Ground extends Sprite{
	protected boolean slippery=false;
	protected String sens=null;
	
	public Ground(){
		super();
		setImage("new-sol.png");

	}
	public Ground(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h);
		setImage("new-sol.png");
		this.image = this.image.getSubImage(_x,250,_w,_h);
	}
	
	public void setSlippery(boolean glisse, String cote){
		System.out.println("steSlippery"+glisse);
		slippery=glisse;
		sens = cote;
	}
	
	public void setSlippery(boolean glisse){
		System.out.println("steSlippery"+glisse);
		slippery=glisse;
	}
	
	public boolean getSlippery(){
		return slippery;
	}
}
