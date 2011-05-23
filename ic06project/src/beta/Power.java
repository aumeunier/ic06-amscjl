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
	TELEPORTATION;
	
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
			result = "fat.png";
			break;
		case PETIT:
			result = "fat.png";
			break;
		case REBOND:
			result = "fat.png";
			break;
		case FIRE:
			result = "powerFire.png";
			break;
		case NAGE:
			result = "fat.png";
			break;
		case LIGHT:
			result = "fat.png";
			break;
		case TELEPORTATION:
			result = "fat.png";
			break;
		case ABSORBE:
			result = "fat.png";
			break;
		}
		return result;
	}

}
