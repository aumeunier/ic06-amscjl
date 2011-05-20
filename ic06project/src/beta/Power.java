package beta;

public enum Power {
	NONE,
	DEATHLY,
	INTANGIBLE,
	FLYING,;
	
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
		}
		return result;
	}

}
