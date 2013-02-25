package game.entities;

import game.Camera;
import game.entities.moving.Bullet;
import game.entities.stationary.Explosion;
import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import util.Assets;
import util.SpriteSheet;

public abstract class Entity implements Comparable<Entity> {

	protected Vector3f position;

	private final SpriteSheet animation;

	private int animationFrame;
	private int time;
	protected boolean animating = false;

	protected final int width;
	protected final int height;
	protected final int depth;

	private final boolean hasShadow;

	protected final World world;

	public Entity(SpriteSheet ss, boolean hasShadow, int depth,
			Vector3f position, World world) {
		this.position = position;
		this.world = world;
		animation = ss;
		width = ss.getSpriteWidth();
		height = ss.getSpriteHeight();
		this.depth = depth;
		this.hasShadow = hasShadow;
	}

	public Entity(Image image, boolean hasShadow, int depth, Vector3f position,
			World world) {
		this(new SpriteSheet(image, image.getWidth(), image.getHeight()),
				hasShadow, depth, position, world);
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

	protected int getAnimationFrame() {
		return animationFrame;
	}

	public Vector3f getPosition() {
		return new Vector3f(position);
	}

	public void render(Camera camera, Graphics g) {
		if (camera.inView(position)) {

			float zScaler = camera.zScaler();
			float otherScaler = camera.otherScaler();

			if (hasShadow) {
				int x = Math.round(position.getX() - camera.getX()) + 400
						- width / 2;
				int y = Math.round((position.getZ() - camera.getY()) * zScaler
						+ 300 - (depth * zScaler) / 2);
				float xScale = 1;
				float yScale = zScaler;

				if (position.y > 0) {
					xScale *= Math.abs(1 / (1 + position.y));
					zScaler *= Math.abs((1 / (1 + position.y)));
				}

				Assets.SHADOW.draw(x, y, width * xScale, depth * yScale);
			}

			Image image = animation.getSubImage(animationFrame, 0);

			int x = Math.round(position.getX() - camera.getX()) + 400 - width
					/ 2;
			int y = Math.round((position.getZ() - camera.getY()) * zScaler
					+ 300 - height * otherScaler - position.y * otherScaler);
			float xScale = 1;
			float yScale = otherScaler;

			image.draw(x, y, width * xScale, height * yScale);
		}
	}

	public void update(int deltaT, GameContainer gc) {
		act(deltaT, gc);

		time += deltaT;
		if (time >= 50 && animating) {
			animationFrame = (animationFrame + 1)
					% animation.getNumberOfSpritesWide();
			time = 0;
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

	public void hitBy(Entity entity) {
		if (entity instanceof Bullet) {
			world.removeEntity(entity);

			world.addEntity(new Explosion(entity.position, world),
					entity.position);
		}
	}

	@Override
	public int compareTo(Entity that) {
		return new Float(position.z).compareTo(that.position.z);
	}

}
