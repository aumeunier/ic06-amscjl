package gold;

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
			result = "grossir.png";
			break;
		case PETIT:
			result = "powerPetit.png";
			break;
		case REBOND:
			result = "rebondir.png";
			break;
		case FIRE:
			result = "powerFire.png";
			break;
		case NAGE:
			result = "powerNage.png";
			break;
		case LIGHT:
			result = "powerLumiere.png";
			break;
		case TELEPORTATION:
			result = "powerTeleportation.png";
			break;
		case ABSORBE:
			result = "voler-pouvoir.png";
			break;
		case DESTRUCTOR:
			result = "powerDestructor.png";
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
