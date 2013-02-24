package game.entities;

import game.world.World;

import org.lwjgl.util.vector.Vector3f;

import util.Assets;

public class Rock extends Entity {

	public Rock(boolean hasShadow, Vector3f position, World world) {
		super(Assets.ROCK, hasShadow, 16, position, world);
	}

	@Override
	public void hitBy(Entity entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean collides(Vector3f pos) {
		return false;
	}

}
