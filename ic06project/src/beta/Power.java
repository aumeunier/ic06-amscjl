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
			result = "wall.png";
			break;
		case DEATHLY:
			result = "blur11.jpg";
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
			result = "fat.png";
			break;
		case TELEPORTATION:
			result = "teleportation.png";
			break;
		case ABSORBE:
			result = "fat.png";
			break;
		case DESTRUCTOR:
			result = "fat.png";
			break;
		case INVISIBLE:
			result = "fat.png";
			break;
		default:
			result = "fat.png";
			break;
		}
		return result;
	}

}
