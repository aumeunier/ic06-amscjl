package gold;

public class Missile extends Destructible{
	
	PlateformeMissile relatedPlateforme;
	
	public Missile(int _x, int _y, int _w, int _h, PlateformeMissile p) {
		super( _x,  _y, _w,  _h);
		relatedPlateforme = p;
	}
	
	public PlateformeMissile getRelated(){
		return relatedPlateforme;
	}
	

}
