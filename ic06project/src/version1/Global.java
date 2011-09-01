package version1;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Global {
	/** Default width for our game window */
	public final static int WINDOW_WIDTH = 800;
	/** Default height for our game window */
	public final static int WINDOW_HEIGHT = 600;
	/** Width of the area used by the level */
	public final static int GAMEPLAYWIDTH = 800;
	/** Heigth of the area used by the level, remaining used to display information to the players */
	public final static int GAMEPLAYHEIGHT = 500;
	
	// Paths to ressources
	public final static String PATH_SAVES = "./saves/";
	public final static String PATH_RESSOURCES = "./ressources/";
	public final static String PATH_IMAGES_RESSOURCES = PATH_RESSOURCES+"images/";
	public final static String PATH_SPRITES_RESSOURCES = PATH_RESSOURCES+"sprites/";
	public final static String PATH_SOUNDS_RESSOURCES = PATH_RESSOURCES+"sounds/";
	public final static String PATH_MUSICS_RESSOURCES = PATH_RESSOURCES+"musics/";
	
	// Default images
	public final static String BUTTON_STANDARD_IMAGE = "blur11.jpg";
	public final static String DEFAULT_UIGAMEPLAY_BACKGROUND_IMAGE = "image_menu2.png";
	public final static String DEFAULT_LIGHT_IMAGE = "test.png";
	
	/** Loaded game name (used to store data when saved) */
	public static String CURRENT_GAME_FILENAME = null;
	
	/**
	 * Switch from world coordinates to box2d coordinates
	 * @param worldX x value in world coordinates, box2dx = worldX
	 * @param worldY y value in world coordinates, box2dy = H - worldY
	 * @return a vector of 2 coordinates in the box2D reference
	 */
	public static Vec2 getBox2DCoordinates(int worldX, int worldY){
		return new Vec2(worldX,Global.GAMEPLAYHEIGHT-worldY);
	}
	public static Vec2 getBox2DCoordinates(float worldX, float worldY){
		return new Vec2(worldX,Global.GAMEPLAYHEIGHT-worldY);
	}

	/**
	 * Create an Image (Slick2d) from a filename. Image is searched in the ressources folder. Catch exceptions.
	 * @param filename the full name of the image
	 * @return the Image
	 */
	public static Image getImage(String filename){
		try {
			return new Image(Global.PATH_IMAGES_RESSOURCES+filename);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return null;
	}
}
