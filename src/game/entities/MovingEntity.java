package game.entities;

import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import util.SpriteSheet;

public abstract class MovingEntity extends Entity {

	private Vector3f velocity = new Vector3f(0, 0, 0);

	public MovingEntity(SpriteSheet ss, Vector3f position, World world) {
		super(ss, true, 16, position, world);
	}

	public MovingEntity(Image image, Vector3f position, World world) {
		super(image, true, 16, position, world);
	}

	abstract Vector3f acceleration(int deltaT, GameContainer gc);

	@Override
	public void update(int deltaT, GameContainer gc) {
		act(deltaT, gc);

		Vector3f.add(velocity, acceleration(deltaT, gc), velocity);

		applyWorldForces(deltaT);

		Vector3f newPosition = Vector3f.add(position, velocity, null);

		if (world.positionClear(newPosition, this)) {
			position = newPosition;
		} else {
			velocity = new Vector3f();
		}
	}

	private void applyWorldForces(int deltaT) {
		if (position.y <= 0) {
			position.y = 0;
			float friction = 0.9f;
			velocity.scale(friction);
		} else {
			float gravity = -0.01f;
			Vector3f.add(velocity, new Vector3f(0, gravity * deltaT, 0),
					velocity);
		}
	}

}
