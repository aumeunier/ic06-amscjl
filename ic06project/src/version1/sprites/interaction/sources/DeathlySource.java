package version1.sprites.interaction.sources;

import version1.sprites.Power;

/**
 * A DeathlySource is a source which will kill any creature touching it. It can be used for water for example.
 *
 */
public class DeathlySource extends Source {
	public DeathlySource(){
		super(Power.DEATHLY);
	}
	public DeathlySource(int _x, int _y, int _w, int _h){
		super(_x,_y,_w,_h,Power.DEATHLY);
	}
}
