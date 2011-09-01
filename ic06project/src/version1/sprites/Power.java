package version1.sprites;

/**
 * A Power represents a capacity to do something special.
 * A Power can be obtained when going through sources. Each source being related to one specific Power.
 * @author aurelien
 *
 */
public enum Power {
	/** Default at the beginning of the level. A character can move around and jump. */
	NONE,
	/** Touching a sources that is deathly will kill you. */
	DEATHLY,
	/** The intangible power allows players to go through the obstacles (but not through walls) */
	INTANGIBLE,
	/** The flying power allows players to fly using the top arrow (instead of a simple jump) */
	FLYING,
	/** The fat power allows players to higher their weight and be able to push hard push buttons */
	FAT,
	/** The fire power allows players to melt the ice on surfaces */
	FIRE,
	/** The rebond power allows players to jump repetitively and higher */
	REBOND,
	/** The swim power allows players to not die when entering the water */
	SWIM,
	/** The small power decreases the size of the player, who can go through small openings */
	SMALL,
	/** The light power will cast some light around the player. Doesn't disappear if another power is taken. */
	LIGHT,
	/** The absorb power allows a player to get the power of the other player if they collide. Both will have it */
	ABSORB,
	/** The teleportation power allows a player to teleport the other player next to himself. */
	TELEPORTATION,
	/** The destructor power allows players to destroy specific obstacles (Destructible) */
	DESTRUCTOR,
	/** The double power allows players to create a double of themselves which can't move. Remove the power on use. */
	DOUBLE,
	/** The invisible power allows players to go through monsters without dying*/
	INVISIBLE;
	
	/**
	 * @return The filename of the image to use for each power, including the file type.
	 * That image is used for both the source image on the level and the players' power image at the bottom.
	 */
	public String imageForPower() {
		String result = "";
		switch(this){
		case NONE:
			result = "powerEmpty.png";
			break;
		case DEATHLY:
			result = "powerEmpty.png";
			break;
		case INTANGIBLE:
			result = "powerMur.png";
			break;
		case FLYING:
			result = "powerFlying.png";
			break;
		case FAT:
			result = "powerFat.png";
			break;
		case SMALL:
			result = "powerPetit.png";
			break;
		case REBOND:
			result = "powerRebond.png";
			break;
		case FIRE:
			result = "powerFire.png";
			break;
		case SWIM:
			result = "powerNage.png";
			break;
		case LIGHT:
			result = "powerLumiere.png";
			break;
		case TELEPORTATION:
			result = "powerInvocation.png";
			break;
		case ABSORB:
			result = "powerSteal.png";
			break;
		case DESTRUCTOR:
			result = "powerDestructor.png";
			break;
		case DOUBLE:
			result = "powerDouble.png";
			break;
		case INVISIBLE:
			result = "powerInvisible.png";
			break;
		default:
			result = "powerEmpty.png";
			break;
		}
		return result;
	}

}
