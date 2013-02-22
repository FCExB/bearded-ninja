package game.entities;

import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class Creature extends Entity {
	
	private final float acceleration = 1f;
	
	public Creature (Vector3f position, World world) throws SlickException{
		super(new Image("bin/data/Creature.png"), position, world);
	}

	@Override
	Vector3f acceleration(int deltaT, GameContainer gc) {
		
		Vector3f result = Vector3f.sub(world.getPlayerLocation(), position, null);
		
		result.normalise().scale(acceleration * (deltaT / 1000f));
		
		return result;
	}
	
	
}
