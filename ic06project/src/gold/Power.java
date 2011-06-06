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
	DOUBLE,
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
			result = "powerFat.png";
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
			result = "powerInvocation.png";
			break;
		case ABSORBE:
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
