package gold;

public class Transporter extends Sprite {
	public int new_x ;
	public int new_y ;
	public Transporter(int x,int y){
		super();
		new_x = x;
		new_y = y;
		setImage("portail.png");
	}
	public Transporter(int _x, int _y, int _w, int _h, int nx, int ny){
		super(_x,_y,_w,_h);
		new_x = nx;
		new_y = ny;
		setImage("portail.png");
	}
}
