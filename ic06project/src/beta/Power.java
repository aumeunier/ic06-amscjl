package beta;

public enum Power {
	NONE,
	DEATHLY,
	INTANGIBLE,
	FLYING,
	FAT,
	FIRE,
	REBOND,
	NAGE,
	PETIT,
	LIGHT,
	ABSORBE,
	TELEPORTATION,
	DESTRUCTOR,
	INVISIBLE;
	
	public String imageForPower() {
		String result = "";
		switch(this){
		case NONE:
			result = "star.jpg";
			break;
		case DEATHLY:
			result = "star.jpg";
			break;
		case INTANGIBLE:
			result = "powerMur.png";
			break;
		case FLYING:
			result = "powerFlying.png";
			break;
		case FAT:
			result = "gros.png";
			break;
		case PETIT:
			result = "petit.png";
			break;
		case REBOND:
			result = "fat.png";
			break;
		case FIRE:
			result = "powerFire.png";
			break;
		case NAGE:
			result = "nage.png";
			break;
		case LIGHT:
			result = "lumiere.png";
			break;
		case TELEPORTATION:
			result = "teleportation.png";
			break;
		case ABSORBE:
			result = "fat.png";
			break;
		case DESTRUCTOR:
			result = "musclor.png";
			break;
		case INVISIBLE:
			result = "invisible2.png";
			break;
		default:
			result = "star.jpg";
			break;
		}
		return result;
	}

}
