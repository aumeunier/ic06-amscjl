package beta;

public enum Power {
	NONE,
	DEATHLY,
	INTANGIBLE,
	FLYING,
	FAT,
	FIRE,
	REBOND,
	PETIT;
	
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
		}
		return result;
	}

}
