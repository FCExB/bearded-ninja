package game.entities;

import game.Camera;
import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import util.SpriteSheet;

public abstract class Entity implements Comparable<Entity> {
	protected Vector3f position;
	private Vector3f velocity = new Vector3f(0, 0, 0);

	private final SpriteSheet movingAnimation;

	private int animationFrame;
	private int time;
	private final int width, height, depth;

	protected final World world;

	public Entity(SpriteSheet ss, Vector3f position, World world) {
		this.position = position;
		this.world = world;
		movingAnimation = ss;
		width = ss.getSpriteWidth();
		height = ss.getSpriteHeight();

		depth = 16;
	}

	public Entity(Image image, Vector3f position, World world) {
		this.position = position;
		this.world = world;
		movingAnimation = new SpriteSheet(image, image.getWidth(),
				image.getHeight());
		width = image.getWidth();
		height = image.getHeight();

		depth = 16;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getDepth() {
		return depth;
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void render(Camera camera) {
		if (camera.inView(position)) {

			float zScaler = camera.zScaler();
			float otherScaler = camera.otherScaler();

			Image image = movingAnimation.getSubImage(animationFrame, 0);

			int x = Math.round(position.getX() - camera.getX()) + 400 - width
					/ 2;
			int y = Math.round((position.getZ() - camera.getY()) * zScaler
					+ 300 - height * otherScaler - position.y * otherScaler);
			float xScale = 1;
			float yScale = otherScaler;

			image.draw(x, y, width * xScale, height * yScale);
		}
	}

	abstract Vector3f acceleration(int deltaT, GameContainer gc);

	public void update(int deltaT, GameContainer gc) {
		act(deltaT, gc);

		time += deltaT;
		if (time >= 90 && velocity.length() >= 0.05) {
			animationFrame = (animationFrame + 1)
					% movingAnimation.getNumberOfSpritesWide();
			time = 0;
		}

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

	protected void act(int deltaT, GameContainer gc) {
	}

	public boolean collides(Vector3f pos) {
		int halfWidth = width / 2;
		int halfDepth = depth / 2;

		return pos.x >= position.x - halfWidth
				&& pos.x <= position.x + halfWidth
				&& pos.z >= position.z - halfDepth
				&& pos.z <= position.z + halfDepth && pos.y >= position.y
				&& pos.y <= position.y + height;
	}

	abstract public void hitBy(Entity entity);

	@Override
	public int compareTo(Entity that) {
		return new Float(position.z).compareTo(that.position.z);
	}

}
