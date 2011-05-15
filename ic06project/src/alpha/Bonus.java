package alpha;

public class Bonus extends Sprite {
	private boolean obtained = false;
	
	public Bonus(int _x, int _y, int _w, int _h){
		this.x = _x;
		this.y = _y;
		this.w = _w;
		this.h = _h;
	}
	
	public void obtained(){
		this.obtained = true;
		this.setShouldBeDestroy();
	}
	public boolean isObtained(){
		return obtained;
	}
	
}
