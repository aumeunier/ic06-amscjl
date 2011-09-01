package version1.data;

public class LevelSave {
	/** Unique identifier for this level */
	private int levelID;
	/** Number of unlockable keys in this level */
	private int unlockableKeys;
	/** Number of already unlocked keys*/
	private int unlockedKeys;
	/** Whether the level has been unlocked */
	private boolean isUnlocked;
	/** Whether the level has been finished at least once (went through exit) */
	private boolean isFinished;
	/** Title of the level */
	private String levelName;
	/** Music to play */
	private String musicFilename;
	/** Area is determined by a list of points used to display the level on the map in the main title menu */
	private int[] areaOnMap;
	/**
	 * Default constructor
	 */
	public LevelSave(int id, String name, String music, int unlockable, int[] area){
		super();
		levelID = id;
		unlockedKeys = 0;
		unlockableKeys = unlockable;
		levelName = name;
		musicFilename = music;
		areaOnMap = area;
		isUnlocked = false;
		isFinished = false;
	}
	/**
	 * Method used to save the players data on a particular level
	 * @param unlocked
	 * @param levelUnlocked
	 * @param levelFinished
	 */
	public void setSavedLevelData(int unlocked, boolean levelUnlocked, boolean levelFinished){
		unlockedKeys = (unlockedKeys >= unlocked)?unlockedKeys:unlocked;
		isUnlocked = isUnlocked || levelUnlocked;
		isFinished = isFinished || levelFinished;
	}
	/**
	 * Method used to load the players data on a particular level
	 * @param unlocked
	 * @param levelUnlocked
	 * @param levelFinished
	 */
	public void setSavedLevelDataFromSave(int unlocked, boolean levelUnlocked, boolean levelFinished){
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
	
	
	/////////////////////////////////////////////////////////
	//////////////////////// STRING /////////////////////////
	/////////////////////////////////////////////////////////
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