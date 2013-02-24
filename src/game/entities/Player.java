package game.entities;

import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import util.Assets;

public class Player extends Entity {

	private final float acceleration = 30f;

	public Player(int x, int z, World world) throws SlickException {
		// super(new SpriteSheet(Assets.PLAYER, 50, 50), new Vector3f(x, 0, z),
		// world);

		super(Assets.PLAYER, new Vector3f(x, 0, z), world);

	}

	@Override
	Vector3f acceleration(int deltaT, GameContainer gc) {
		Input input = gc.getInput();

		int x = 0, z = 0, y = 0;

		if (position.y <= 0 && input.isKeyDown(Input.KEY_W)) {

			z--;
		}

		if (position.y <= 0 && input.isKeyDown(Input.KEY_S)) {

			z++;
		}

		if (position.y <= 0 && input.isKeyDown(Input.KEY_A)) {

			x--;
		}

		if (position.y <= 0 && input.isKeyDown(Input.KEY_D)) {

			x++;
		}

		if (position.y <= 0 && input.isKeyDown(Input.KEY_SPACE)) {
			y++;
		}

		Vector3f change = new Vector3f(x, y, z);

		if (change.length() == 0) {
			return change;
		}

		change.normalise().scale(acceleration * (deltaT / 1000f));

		if (position.y <= 0 && input.isKeyDown(Input.KEY_SPACE)) {
			change.y += 4;
		}

		return change;
	}

	@Override
	protected void act(int deltaT, GameContainer gc) {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_F)) {

			world.addFence(Math.round((position.x + 65) / world.getTileSize()),
					Math.round((position.y + 30) / world.getTileSize()));
		}

		if (input.isKeyDown(Input.KEY_C)) {
			Vector3f newPos = new Vector3f(position.x + getWidth(), 0,
					position.getZ() + getDepth());

			world.addEntity(new Creature(Assets.CREATURE, new Attributes(),
					newPos, world), newPos);
		}
	}

	@Override
	public void hitBy(Entity entity) {
		if (entity instanceof Creature) {
			if (entity.getPosition().y < position.y) {
				world.removeEntity(entity);
			}
		}

	}
}
