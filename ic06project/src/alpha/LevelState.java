package alpha;

public class LevelState {
	private Power player1power;
	private Power player2power;
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
	public String getPlayer1Power(){
		return player1power.toString();
	}
	public String getPlayer2Power(){
		return player2power.toString();
	}
	public int getNbKeys(){
		return nbKeysUnlocked;
	}
}
