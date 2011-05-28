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
			result = "powerGros.png";
			break;
		case PETIT:
			result = "powerPetit.png";
			break;
		case REBOND:
			result = "powerRebond.png";
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
			result = "powerEmpty.png";
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
