package alpha;

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
			result = "pool_hat.png";
			break;
		case FLYING:
			result = "pool_bird.png";
			break;
		}
		return result;
	}

}
