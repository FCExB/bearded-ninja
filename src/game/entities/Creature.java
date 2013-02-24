package game.entities;

import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import util.Assets;

public class Creature extends MovingEntity {

	private final float acceleration = 10f;

	private int timeSinceBaby, timeSinceFood;

	private final Attributes attributes;

	public Creature(Image image, Attributes attributes, Vector3f position,
			World world) {
		super(image, position, world);
		this.attributes = attributes;
	}

	@Override
	Vector3f acceleration(int deltaT, GameContainer gc) {

		Vector3f result = new Vector3f(
				world.getPlayerLocation().x - position.x, 0,
				world.getPlayerLocation().z - position.z);

		result.normalise().scale(acceleration * (deltaT / 1000f));

		return result;
	}

	@Override
	protected void act(int deltaT, GameContainer gc) {
		timeSinceBaby += deltaT;
		timeSinceFood += deltaT;
	}

	@Override
	public void hitBy(Entity entity) {
		if (entity instanceof Creature) {
			Creature that = (Creature) entity;

			if (that.timeSinceFood >= attributes.getAttribute(Attribute.FOOD)) {

				if (Math.random() > 0.5) {
					world.removeEntity(this);
				}
				that.timeSinceFood = 0;

			} else if (timeSinceBaby >= attributes.getAttribute(Attribute.SEX)) {

				Vector3f babyPosition = Vector3f.sub(that.position,
						this.position, null);
				babyPosition.scale(2.5f);
				Vector3f.add(this.position, babyPosition, babyPosition);

				if (world.addEntity(
						new Creature(Assets.CREATURE, Attributes.breed(
								this.attributes, that.attributes),
								babyPosition, world), babyPosition)) {
					this.timeSinceBaby = 0;
					that.timeSinceBaby = 0;
				}
			}
		}
	}
}
