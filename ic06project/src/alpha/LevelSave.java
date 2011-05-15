package alpha;

public class LevelSave {
	int levelID;
	int unlockableKeys;
	int unlockedKeys;
	boolean isUnlocked;
	boolean isFinished;
	String levelName;
	String musicFilename;
	int[] areaOnMap;
	/**
	 * Default constructor
	 */
	public LevelSave(int id, String name, String music, int unlockable, int[] area){
		super();
		levelID = id;
		unlockableKeys = unlockable;
		levelName = name;
		musicFilename = music;
		areaOnMap = area;
		isUnlocked = false;
		isFinished = false;
	}
	/**
	 * Method used to load the players data on a particular level
	 * @param unlocked
	 * @param levelUnlocked
	 * @param levelFinished
	 */
	public void setSavedLevelData(int unlocked, boolean levelUnlocked, boolean levelFinished){
		if(unlockableKeys < unlocked){
			unlocked = unlockableKeys;
		}
		unlockedKeys = unlocked;
		isUnlocked = levelUnlocked;
		isFinished = levelFinished;
	}
	public String getName(){
		return levelName;
	}
	public String getMusicName(){
		return musicFilename;
	}
	public int getUnlockableKeys(){
		return unlockableKeys;
	}
	public int getUnlockedKeys(){
		return unlockedKeys;
	}
	public boolean isFinished(){
		return isFinished;
	}
	public boolean isUnlocked(){
		return isUnlocked;
	}
	public int getID(){
		return levelID;
	}
	public int[] getAreaOnMap(){
		return areaOnMap;
	}
	@Override
	public String toString(){
		return "ID:"+levelID+" Name:"+levelName+"\n"
			+"Unlocked:"+((isUnlocked)?"Yes":"No")+" Finished:"+((isFinished)?"Yes":"No")+"\n"
			+"Keys:"+unlockableKeys+" Unlocked:"+unlockedKeys+"\n"
			+"AreaOnMap:"+areaOnMap[0]+","+areaOnMap[1]+","+areaOnMap[2]+","+areaOnMap[3];
	}
	public String toJson(String prefix){
		String result=prefix+"{\n";
		result+=prefix+"\t"+"\"id\":"+levelID+",\n";
		result+=prefix+"\t"+"\"keys\":"+unlockedKeys+",\n";
		result+=prefix+"\t"+"\"unlocked\":"+((isUnlocked)?"true":"false")+",\n";
		result+=prefix+"\t"+"\"finished\":"+((isFinished)?"true":"false")+"\n";
		result+=prefix+"}";
		return result;
	}
}