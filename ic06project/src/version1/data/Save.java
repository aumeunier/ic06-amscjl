package version1.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import version1.Global;
/**
 * The Save class is a singletion which stores information about the current game and levels.
 * When you create a new game or load an existing game, the information is updated.
 *
 */
public class Save {
	/** The path to the levels' definition */
	static final String LEVELS_DEFINITION = Global.PATH_RESSOURCES+"levels.json";
	/** The current save store name */
	String saveFilename;
	/** The name of the first player for this game */
	String player1name;
	/** The name of the second player for this game */
	String player2name;
	/** The list of levels available to this game */
	ArrayList<LevelSave> levels;
	/** The number of bonus unlockable in this game */
	int totalNumberOfBonus;
	/** The number of bonus unlocked in this game */
	int totalNumberOfUnlockedBonus;
	
	// Singleton
    public static Save getInstance() {
        if (null == instance) { // First call
            instance = new Save();
        }
        return instance;
    }
    private static Save instance;
	private Save(){
		super();
		levels = new ArrayList<LevelSave>();
		totalNumberOfBonus = 0;
		totalNumberOfUnlockedBonus =0;
		saveFilename = null;
		player1name = null;
		player2name = null;
		loadLevelDefinitions();
	}
	
	/////////////////////////////////////////////////////////
	///////////////////// LOAD AND SAVE /////////////////////
	/////////////////////////////////////////////////////////
	private JsonNode loadJson(String jsonFilepath) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper(); 
		return mapper.readValue(new File(jsonFilepath), JsonNode.class);
	}
	/**
	 * Will load the levels' information: number of levels, names, points on the map
	 * Uses the LEVELS_DEFINITION file (JSON) to load data for each level
	 */
	private void loadLevelDefinitions(){
		try {
			JsonNode rootNode = loadJson(LEVELS_DEFINITION);
			JsonNode levelsNode = rootNode.path("levels");
			Iterator<JsonNode> itr = levelsNode.getElements();
			while(itr.hasNext()){
				JsonNode lvl = itr.next(); 
				int tempId = lvl.path("id").getIntValue();
				String tempName = lvl.path("levelName").getValueAsText();
				String musicFilename = lvl.path("music").getValueAsText();
				int tempBonus = lvl.path("numberOfBonus").getIntValue();
				Iterator<JsonNode> it = lvl.path("areaOnMap").getElements();
				int[] tempArea = new int[4];
				int i = 0;
				while(it.hasNext()){
					tempArea[i] = it.next().getIntValue();
					i++;
				}
				LevelSave newLevel = new LevelSave(tempId, tempName, musicFilename, tempBonus, tempArea);
				levels.add(newLevel);
				totalNumberOfBonus+=tempBonus;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Load a save file and update the current names and bonus information
	 * @param filename The name of the save
	 */
	public void loadSave(String filename){
		saveFilename = filename;
		totalNumberOfUnlockedBonus = 0;
		try {
			JsonNode rootNode = loadJson(filename);
			player1name = rootNode.path("name1").getValueAsText();
			player2name = rootNode.path("name2").getValueAsText();
			JsonNode levelsNode = rootNode.path("levels");
			Iterator<JsonNode> itr = levelsNode.getElements();
			while(itr.hasNext()){
				JsonNode lvl = itr.next(); 
				int tempId = lvl.path("id").getIntValue();
				boolean tempUnlocked = lvl.path("unlocked").getBooleanValue();
				boolean tempFinished = lvl.path("finished").getBooleanValue();
				int tempBonus = lvl.path("bonus").getIntValue();
				LevelSave level = getLevelWithID(tempId);
				if(level != null){
					level.setSavedLevelDataFromSave(tempBonus, tempUnlocked, tempFinished);
					totalNumberOfUnlockedBonus+=level.getUnlockedBonus();
				}
			}			
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Save the current game
	 */
	public void save(){
		totalNumberOfUnlockedBonus = 0;
		for(LevelSave lvlSave: levels){
			totalNumberOfUnlockedBonus+=lvlSave.getUnlockedBonus();
		}
	    File file = new File(saveFilename);
	    try {
		    BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(this.toJson());
		    output.close(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Create a new save file
	 * @param name1 The name of the first player
	 * @param name2 The name of the second player
	 */
	public void createSave(String name1, String name2){
		this.player1name = name1;
		this.player2name = name2;
		this.saveFilename = Global.PATH_SAVES+name1+"_"+name2+".save";
		getLevelWithID(1).setSavedLevelDataFromSave(0, true, false);
		getLevelWithID(2).setSavedLevelDataFromSave(0, false, false);
		getLevelWithID(3).setSavedLevelDataFromSave(0, false, false);
		getLevelWithID(4).setSavedLevelDataFromSave(0, false, false);
		getLevelWithID(5).setSavedLevelDataFromSave(0, false, false);
		save();
	}
	
	/////////////////////////////////////////////////////////
	//////////////////////// GETTERS ////////////////////////
	/////////////////////////////////////////////////////////
	/**
	 * @return the LevelSave related to a level id
	 */
	public LevelSave getLevelWithID(int id){
		for(LevelSave level: levels){
			if(level.getID() == id){
				return level;
			}
		}
		return null;
	}
	/**
	 * Get the state of the level for this game using three digits:
	 * 	- first digit: 0=level not available, 1=level available (is playable)
	 * 	- second digit: 0=level not finished, 1=finished (went to the exit at least once)
	 * 	- third digit: 0=not all fruits unlocked, 1=unlocked all fruits
	 * @param index the id of the level concerned
	 * @return the state of the level as specified above
	 */
	public int getFinishedStateForLevelID(int index){
		LevelSave lvl = this.getLevelWithID(index);
		byte b1 = (byte)((lvl.isUnlocked())?1:0);
		byte b2 = (byte)((lvl.isFinished())?1:0);
		byte b3 = (byte)((((lvl.getUnlockedBonus()-lvl.getUnlockableBonus()) >= 0))?1:0);
		return ((b3<< 1) << 1) + (b2 << 1) + b1;
	}
	/** @return the ids of all the levels */
	public int[] getAllIds(){
		int[] temp = new int[levels.size()];
		int i = 0;
		for(LevelSave lvl: levels){
			temp[i] = lvl.getID();
			i++;
		}
		return temp;
	}
	/** @return the number of bonus unlockable in this game */
	public int getTotalNumberOfBonus(){
		return totalNumberOfBonus;
	}
	/** @return the number of bonus unlocked in this game */
	public int getTotalNumberOfUnlockedBonus(){
		return totalNumberOfUnlockedBonus;
	}	
	/** 
	 * @param id the level's id
	 * @return the number of bonus unlockable in the given level
	 */
	public int getUnlockableBonusForLevelID(int id){	
		LevelSave level = getLevelWithID(id);
		if(level != null){
			return level.getUnlockableBonus();
		}
		return 0;
	}
	/**
	 * @param id the level's id
	 * @return the number of bonus already unlocked in the given level
	 */
	public int getUnlockedBonusForLevelID(int id){
		LevelSave level = getLevelWithID(id);
		if(level != null){
			return level.getUnlockedBonus();
		}
		return 0;
	}
	/** @return the name of the first player in the current game */
	public String getFirstPlayerName() {
		return player1name;
	}
	/** @return the name of the second player in the current game */
	public String getSecondPlayerName() {
		return player2name;
	}
	/** @return the LevelSave instance related to a given level */
	public LevelSave levelSaveForLevelID(int id){
		return getLevelWithID(id);
	}
	/** @return whether the given level has been unlocked in this game */
	public boolean hasUnlockedLevelWithID(int id){
		LevelSave level = this.getLevelWithID(id);
		return (levels.contains(level) && level.isUnlocked());
	}
	/** @return whether any game has been loaded */
	public boolean hasSaveLoaded(){
		return (this.saveFilename!=null);
	}
	/** @return the number of possible levels */
	public int getNumberOfLoadedLevels(){
		return levels.size();
	}

	/////////////////////////////////////////////////////////
	////////////////////////// MAP //////////////////////////
	/////////////////////////////////////////////////////////
	/** 
	 * A level is represented by a square on the map, is the point (x,y) on this square ?
	 * @return whether the given point is in the square of the given level
	 */
	private boolean isPointOnLevel(int x, int y, LevelSave level){
		int[] coordinates = mapPointForLevel(level);
		if(coordinates == null){
			return false;
		}
		return ((x >= coordinates[0] && x <= coordinates[0]+coordinates[2])
				&& (y >= coordinates[1] && y <= coordinates[1]+coordinates[3]));
	}
	/** @return the area of the level's square on the map */
	private int[] mapPointForLevel(LevelSave level){
		return level.getAreaOnMap();	
	}
	/** @return the id of level related to the given point on the map */
	public int levelIdForPoint(int x, int y){
		for(LevelSave level: levels){
			if(isPointOnLevel(x,y,level)){
				return level.getID();
			}
		}
		return -1;
	}
	/** @return the area on the map for the given level id */
	public int[] mapPointForLevelID(int id){
		LevelSave level = getLevelWithID(id);
		return mapPointForLevel(level);
	}
	

	/////////////////////////////////////////////////////////
	//////////////////////// STRING /////////////////////////
	/////////////////////////////////////////////////////////
	@Override
	public String toString(){
		String result = "Save:"+saveFilename+"\n"+"Player1:"+player1name+" Player2:"+player2name+"\n\n";
		for(LevelSave level: levels){
			result+=level.toString()+"\n\n";
		}
		return result;
	}
	private String toJson(){
		String result = "{\n";
		result+="\t \"name1\":\""+player1name+"\",\n";
		result+="\t \"name2\":\""+player2name+"\",\n";
		result+="\t \"levels\":["+"\n";
		for(int i = 0 ; i < levels.size() ; ++i){
			LevelSave lvl = levels.get(i);
			result+=lvl.toJson("\t\t");
			if(i<levels.size()-1){
				result+=",";
			}
			result+="\n";
		}
		result+="\t ]\n";
		result+="}";
		return result;
	}
}
