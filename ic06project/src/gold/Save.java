package gold;

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
public class Save {
	static final String LEVELS_DEFINITION = Global.PATH_RESSOURCES+"levels.json";
	static final int MAPITEM_W = 25;
	static final int MAPITEM_H = 25;
	String saveFilename;
	String player1name;
	String player2name;
	ArrayList<LevelSave> levels;
	int totalNumberOfKeys;
	int totalNumberOfUnlockedKeys;
	
    public static Save getInstance() {
        if (null == instance) { // Premier appel
            instance = new Save();
        }
        return instance;
    }
    private static Save instance;
	private Save(){
		super();
		levels = new ArrayList<LevelSave>();
		totalNumberOfKeys = 0;
		totalNumberOfUnlockedKeys =0;
		saveFilename = null;
		player1name = null;
		player2name = null;
		loadLevelDefinitions();
	}
	
	/////// *** Concerning the loading and saving *** ///////
	private JsonNode loadJson(String jsonFilepath) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper(); 
		return mapper.readValue(new File(jsonFilepath), JsonNode.class);
	}
	/**
	 * Will load the levels' information: number of levels, names, points on the map
	 * Uses the LEVELS_DEFINITION file (JSON) to load data.
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
				int tempKeys = lvl.path("numberOfKeys").getIntValue();
				Iterator<JsonNode> it = lvl.path("areaOnMap").getElements();
				int[] tempArea = new int[4];
				int i = 0;
				while(it.hasNext()){
					tempArea[i] = it.next().getIntValue();
					i++;
				}
				LevelSave newLevel = new LevelSave(tempId, tempName, musicFilename, tempKeys, tempArea);
				levels.add(newLevel);
				totalNumberOfKeys+=tempKeys;
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Load a save file
	 * @param filename The name of the save
	 */
	public void loadSave(String filename){
		saveFilename = filename;
		totalNumberOfUnlockedKeys = 0;
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
				int tempKeys = lvl.path("keys").getIntValue();
				LevelSave level = getLevelWithID(tempId);
				if(level != null){
					level.setSavedLevelDataFromSave(tempKeys, tempUnlocked, tempFinished);
					totalNumberOfUnlockedKeys+=level.getUnlockedKeys();
				}
			}			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Save the current game
	 */
	public void save(){
		totalNumberOfUnlockedKeys = 0;
		for(LevelSave lvlSave: levels){
			totalNumberOfUnlockedKeys+=lvlSave.getUnlockedKeys();
		}
	    File file = new File(saveFilename);
	    try {
		    BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(this.toJson());
		    output.close(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
	
	/////// *** Getters *** ///////
	public LevelSave getLevelWithID(int id){
		for(LevelSave level: levels){
			if(level.getID() == id){
				return level;
			}
		}
		return null;
	}
	// 00 == Non disponible, non fini ; 01 == disponible, non fini ; 
	// 11 == disponible, fini
	// 111 == disponible, fini, tous les fruits
	public int getFinishedStateForLevelID(int index){
		LevelSave lvl = this.getLevelWithID(index);
		byte b1 = (byte)((lvl.isUnlocked)?1:0);
		byte b2 = (byte)((lvl.isFinished)?1:0);
		byte b3 = (byte)((((lvl.getUnlockedKeys()-lvl.getUnlockableKeys()) >= 0))?1:0);
		System.out.println((((b3<< 1) << 1) + (b2 << 1) + b1));
		return ((b3<< 1) << 1) + (b2 << 1) + b1;
	}
	public int[] getAllIds(){
		int[] temp = new int[levels.size()];
		int i = 0;
		for(LevelSave lvl: levels){
			temp[i] = lvl.getID();
			i++;
		}
		return temp;
	}
	public int getTotalNumberOfKeys(){
		return totalNumberOfKeys;
	}
	public int getTotalNumberOfUnlockedKeys(){
		return totalNumberOfUnlockedKeys;
	}	
	public int getUnlockableKeysForLevelID(int id){	
		LevelSave level = getLevelWithID(id);
		if(level != null){
			return level.getUnlockableKeys();
		}
		return 0;
	}
	public int getUnlockedKeysForLevelID(int id){
		LevelSave level = getLevelWithID(id);
		if(level != null){
			return level.getUnlockedKeys();
		}
		return 0;
	}
	public String getFirstPlayerName() {
		return player1name;
	}
	public String getSecondPlayerName() {
		return player2name;
	}
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
	
	/////// *** Concerning the map *** ///////
	private boolean isPointOnLevel(int x, int y, LevelSave level){
		int[] coordinates = mapPointForLevel(level);
		if(coordinates == null){
			return false;
		}
		return ((x >= coordinates[0] && x <= coordinates[0]+coordinates[2])
				&& (y >= coordinates[1] && y <= coordinates[1]+coordinates[3]));
	}
	private int[] mapPointForLevel(LevelSave level){
		return level.getAreaOnMap();		
	}
	public int levelIdForPoint(int x, int y){
		for(LevelSave level: levels){
			if(isPointOnLevel(x,y,level)){
				return level.getID();
			}
		}
		return -1;
	}
	public int[] mapPointForLevelID(int id){
		LevelSave level = getLevelWithID(id);
		return mapPointForLevel(level);
	}
	public LevelSave levelSaveForLevelID(int id){
		return getLevelWithID(id);
	}
	public boolean hasUnlockedLevelWithID(int id){
		LevelSave level = this.getLevelWithID(id);
		return (levels.contains(level) && level.isUnlocked());
	}
	public boolean hasSaveLoaded(){
		return (this.saveFilename!=null);
	}
	public int getNumberOfLoadedLevels(){
		return levels.size();
	}
}
