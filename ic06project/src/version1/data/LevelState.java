package version1.data;

import version1.sprites.Power;

public class LevelState {
	/** Current power of the first player */
	private Power player1power;
	/** Current power of the second player */
	private Power player2power;
	/** Number of keys unlocked in the level being played right now */
	private int nbKeysUnlocked;
	
	public LevelState(){
		player1power = Power.NONE;
		player2power = Power.NONE;
		nbKeysUnlocked = 0;
	}
	public void setPlayer1Power(Power power){
		player1power = power;
	}
	public void setPlayer2Power(Power power){
		player2power = power;
	}
	public void setNbKeysUnlocked(int nb){
		nbKeysUnlocked = nb;
	}
	public Power getPlayer1Power(){
		return player1power;
	}
	public Power getPlayer2Power(){
		return player2power;
	}
	public int getNbKeys(){
		return nbKeysUnlocked;
	}
}
