package version1.engine;

import org.jbox2d.collision.Shape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.ContactFilter;

import beta.Global;

import version1.sprites.Sprite;
import version1.sprites.alive.Creature;
import version1.sprites.alive.monsters.Monster;
import version1.sprites.alive.monsters.Witch;
import version1.sprites.decor.Ground;
import version1.sprites.decor.GroundBackground;
import version1.sprites.decor.IndicationSprite;
import version1.sprites.decor.Obstacle;
import version1.sprites.decor.Wall;
import version1.sprites.interaction.Bonus;
import version1.sprites.interaction.Destructible;
import version1.sprites.interaction.Transporter;
import version1.sprites.interaction.projectiles.FireBall;
import version1.sprites.interaction.projectiles.PlatformMissile;
import version1.sprites.interaction.sources.Source;
import version1.sprites.interaction.sources.DeathlySource;
import version1.sprites.interaction.trigger.Lever;
import version1.sprites.interaction.trigger.LeverCombination;
import version1.sprites.interaction.trigger.buttons.PushButton;
import version1.states.GameplayState;

/**
 * This class inherits from the <code>ContactFilter</code> class of the JBox2D library.
 * We use it to get a notification when a new collision is detected and either perform operations or filter the
 * collisions. 
 *
 */
public class MyContactFilter implements ContactFilter {

	@Override
	public boolean shouldCollide(Shape shape1, Shape shape2) {
		Body b1 = shape1.getBody();
		Body b2 = shape2.getBody();

		//// Ground sensor collisions - used to detect if the character stand on a button
		if(shape1.getUserData()==GameplayState.GROUND_SENSOR_NAME){
			Sprite s2 = (Sprite)b2.getUserData();
			if(s2 instanceof PushButton){
				((Creature)b1.getUserData()).setBouton(((PushButton)s2).getButtonID());
				((PushButton)s2).check();
			}
		}
		else if(shape2.getUserData()==GameplayState.GROUND_SENSOR_NAME){
			Sprite s1 = (Sprite)b1.getUserData();
			if(s1 instanceof PushButton){
				((Creature)b2.getUserData()).setBouton(((PushButton)s1).getButtonID());
				((PushButton)s1).check();
			}		
		}

		Object b1d = b1.getUserData();
		Object b2d = b2.getUserData();
		
		//// Collisions with Destructible objects
		if((b1d!=null && b2d!=null)&& (b1d instanceof Sprite && b2d instanceof Sprite)
			&& (((b1d instanceof Destructible) && !b2d.getClass().equals(Creature.class))
				|| ((b2d instanceof Destructible) && !b1d.getClass().equals(Creature.class)))){
			
			Destructible d = (Destructible) ((b1d instanceof Destructible)?b1d:b2d);
			Sprite other = (Sprite) ((b1d instanceof Destructible)?b2d:b1d);
			
			// A monster loses health if a Destructible falls on it
			if(other instanceof Monster){
				if(d.isDeadly()){
					((Monster)other).loseHealth(1);	
				}				
				return false;
			}
			
			// A Missile (subclass of Destructible) object is destroyed if it falls onto the ground
			else if((other.getClass().equals(Wall.class)||other.getClass().equals(Ground.class)) && d.getClass().equals(PlatformMissile.class)){
				d.setShouldBeDestroy();
			}
			
			// A Destructible does not hurt anymore when it stand on the ground (only if falling)
			else if(other.getClass().equals(Wall.class)){
				d.setDeadly(false);
				return true;
			}
			
			// A Destructible falls through background platforms and transporters (doesn't stop it)
			else if(other.getClass().equals(GroundBackground.class) 
					|| other.getClass().equals(Transporter.class)){
				return false;
			}
		}

		//// Collisions between Sprites
		if(b1d!=null && b2d!=null && (b1d instanceof Sprite && b2d instanceof Sprite)){
			Sprite s1 = (Sprite)b1d;
			Sprite s2 = (Sprite)b2d;

			//// Collisions with a Creature
			if(s1.getClass().equals(Creature.class) || s2.getClass().equals(Creature.class)){
				Creature character = null;
				Sprite other = null;
				if(s1.getClass().equals(Creature.class)){
					character = (Creature)s1;
					other = s2;
				}
				else {
					character = (Creature)s2;
					other = s1;						
				}					

				// An hidden sprite doesn't stop the player
				if(other.isHidden()){
					return false;
				}
				// If the sprite holds an indication, it pops it at the bottom of the screen
				else if(other.getIndication()!=null ){
					other.activateIndication();
				}
				
				/// Power related collisions
				// If a character is intangible, it can go through obstacles
				if(character.isIntangible()
						&& (other.getClass().equals(Obstacle.class))){
					return false;
				}
				// If the character is not invisible, it is killed by monsters
				else if(other instanceof Monster && !character.isInvisible()){
					character.setDead(true);
					return false;
				}
				else if(other instanceof Monster && character.isInvisible()){
					return false;
				}
				// If the character can't swim, it dies in the water
				else if((other.getClass().equals(DeathlySource.class)) && !character.canSwim()){
					character.setDead(true);
					return false;
				}
				else if((other.getClass().equals(DeathlySource.class)) && character.canSwim()){
					character.isFalling=false;
					return false;
				}
				// A character gets killed by Destructible objects falling on him
				else if((other.getClass().equals(Destructible.class)) && ((Destructible)other).isDeadly()){
					character.setDead(true);
					return false;
				}
				else if((other.getClass().equals(Destructible.class)) && character.isDestructor()){
					other.setShouldBeDestroyed(true);
					((Destructible)other).setDestructiblesDeadly();
					character.isFalling=false;
					return false;
				}
				// Activate the indication of an indication sprite then remove it
				else if(other.getClass().equals(IndicationSprite.class)){
					((IndicationSprite)other).activateIndication();
					other.setShouldBeDestroy();
					return false;
				}
				// If a source is encountered, the player gets the power
				else if(other.getClass().equals(Source.class)){
					Source source = (Source)other;
					character.setPower(source.getPower());
					return false;
				}
				// If a player walks into a transporter, it is teleported to the transporter destination
				else if(other.getClass().equals(Transporter.class)){
					character.setTransported(true,((Transporter)other).getTeleportationPosition());
					return true;
				}
				// If the players touch each other and one of them have the absorb power, it absorbs the other's power
				else if(other.getClass().equals(Creature.class)){
					if(character.absorbe()){
						character.setPower(((Creature)other).getPower());
					}
					else if(((Creature)other).absorbe()){
						((Creature)other).setPower(character.getPower());
					}
					return false;
				}
				// If a player goes on a switch, he activates it
				else if(other.getClass().equals(Lever.class)){
					((Lever)other).enable();
					return false;
				}
				else if(other.getClass().equals(LeverCombination.class)){
					((LeverCombination)other).enable();
					return false;
				}
				// If a player walks on a bonus, he gets it
				else if(other.getClass().equals(Bonus.class)){
					((Bonus)other).obtained();
					return false;						
				}
				// If a player is hit by a fireball, he loses health
				else if(other.getClass().equals(FireBall.class)){
					((FireBall)other).setShouldBeDestroy();
					if(other.X() > 0 && other.Y() > 0 
							&& other.X() < Global.GAMEPLAYWIDTH && other.Y() < Global.GAMEPLAYWIDTH) {
						character.loseHealth(((FireBall)other).getPower());
					}
					return false;
				}
				// We don't want the player to push a missile
				else if(other.getClass().equals(PlatformMissile.class)){
					return false;
				}
			}

			//// Other collisions with a Creature
			else {
				// Display indication sprites
				if((s1.getClass().equals(IndicationSprite.class))
						|| (s2.getClass().equals(IndicationSprite.class))){
					return false;
				}
				// Fireballs hit the first player it encounters and then are removed
				else if((s1.getClass().equals(FireBall.class))
						|| (s2.getClass().equals(FireBall.class))){
					FireBall fb = (FireBall) ((s1.getClass().equals(FireBall.class))?s1:s2);
					Sprite other = ((s1.getClass().equals(FireBall.class))?s2:s1);
					if(other instanceof Creature){
						if(fb.hurtCharacter((Creature)other)){
							fb.setShouldBeDestroy();
						}
					}
					return false;
				}
				// The Witch boss goes back and force between ground objects
				if(s1 instanceof Witch && s2 instanceof Ground){
					((Witch)s1).inverseXspeed();
					return false;
				}
				else if(s2 instanceof Witch && s1 instanceof Ground){
					((Witch)s2).inverseXspeed();
					return false;
				}
				// Monsters don't go through teleporters
				if(s1 instanceof Monster && s2 instanceof Transporter){
					return false;
				}
				else if(s2 instanceof Monster && s1 instanceof Transporter){
					return false;
				}
			}
		}
		return true;
	}
}

