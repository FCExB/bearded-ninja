package game.entities;

import game.world.World;

import org.lwjgl.util.vector.Vector3f;

import util.Assets;

public class Tree extends Entity {

	public Tree(boolean hasShadow, Vector3f position, World world) {
		super(Assets.TREE, hasShadow, 25, position, world);
	}

	@Override
	public void hitBy(Entity entity) {

	}
}
