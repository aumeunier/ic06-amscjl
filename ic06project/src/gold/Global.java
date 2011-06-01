package gold;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Global {
	public final static int WINDOW_WIDTH = 800;
	public final static int WINDOW_HEIGHT = 600;
	public final static int GAMEPLAYWIDTH = 800;
	public final static int GAMEPLAYHEIGHT = 500;
	public final static String PATH_SAVES = "./saves/";
	public final static String PATH_RESSOURCES = "./ressources/";
	public final static String PATH_IMAGES_RESSOURCES = PATH_RESSOURCES+"images/";
	public final static String PATH_SPRITES_RESSOURCES = PATH_RESSOURCES+"sprites/";
	public final static String PATH_SOUNDS_RESSOURCES = PATH_RESSOURCES+"sounds/";
	public final static String PATH_MUSICS_RESSOURCES = PATH_RESSOURCES+"musics/";
	public final static String BUTTON_STANDARD_IMAGE = "blur11.jpg";
	public final static String DEFAULT_UIGAMEPLAY_BACKGROUND_IMAGE = "06_wood_artshare_ru.jpg";
	public final static String DEFAULT_LIGHT_IMAGE = "test.png";
	public static String CURRENT_GAME_FILENAME = null;
	
	public static Vec2 getWorldCoordinates(float box2dX, float box2dY){
		return new Vec2(box2dX,box2dY);
	}
	public static Vec2 getBox2DCoordinates(int worldX, int worldY){
		return new Vec2(worldX,Global.GAMEPLAYHEIGHT-worldY);
	}

	public static Image setImage(String filename){
		try {
			return new Image(Global.PATH_IMAGES_RESSOURCES+filename);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
}
