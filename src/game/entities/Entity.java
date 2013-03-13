package game.entities;

import game.Camera;
import game.entities.moving.Bullet;
import game.entities.moving.Player;
import game.entities.stationary.Explosion;
import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
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
	private final boolean solid;

	protected final World world;

	public Entity(SpriteSheet ss, boolean hasShadow, boolean solid, int depth,
			Vector3f position, World world) {
		this.position = position;
		this.world = world;
		animation = ss;
		width = ss.getSpriteWidth();
		height = ss.getSpriteHeight();
		this.depth = depth;
		this.hasShadow = hasShadow;
		this.solid = solid;
	}

	public Entity(Image image, boolean hasShadow, boolean solid, int depth,
			Vector3f position, World world) {
		this(new SpriteSheet(image, image.getWidth(), image.getHeight()),
				hasShadow, solid, depth, position, world);
	}

	public boolean isSolid() {
		return solid;
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
		if (camera.inRenderView(position)) {

			float zScaler = camera.zScaler();
			float otherScaler = camera.otherScaler();

			if (hasShadow) {
				int x = Math.round(position.getX() - camera.getX()) + 500
						- width / 2;

				int y = Math.round((position.getZ() - camera.getY()) * zScaler
						+ 300 - (depth * zScaler) / 2);

				float xScale = 1;
				float yScale = zScaler;

				// if (position.y > 0) {
				// x += Math.abs((width / 2) * (1 - 1 / (1 + position.y) * 2));
				// y += Math.abs(((depth * zScaler) / 2)
				// * (1 - 1 / (1 + position.y)) * 2);
				//
				// xScale *= Math.abs(1 / position.y) * 2;
				// zScaler *= Math.abs(1 / position.y) * 2;
				// }

				Assets.SHADOW.draw(x, y, width * xScale, depth * yScale);
			}

			Image image = animation.getSubImage(animationFrame, 0);

			int x = Math.round(position.getX() - camera.getX()) + 500 - width
					/ 2;
			int y = Math.round((position.getZ() - camera.getY()) * zScaler
					+ 300 - height * otherScaler - position.y * otherScaler);
			float xScale = 1;
			float yScale = otherScaler;

			Color filter = world.brightnessAtLocation(position);
			
            Color lightEffect = world.getGlobalFilter();
			
			filter.r = Math.max(filter.r, lightEffect.r);
			filter.g = Math.max(filter.g, lightEffect.g);
			filter.b = Math.max(filter.b, lightEffect.b);

			image.draw(x, y, width * xScale, height * yScale, filter);
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
		if (solid) {
			int halfWidth = width / 2;
			int halfDepth = depth / 2;

			return pos.x >= position.x - halfWidth
					&& pos.x <= position.x + halfWidth
					&& pos.z >= position.z - halfDepth
					&& pos.z <= position.z + halfDepth && pos.y >= position.y
					&& pos.y <= position.y + height;
		}

		return false;
	}

	public void hitBy(Entity entity) {
		if (entity instanceof Bullet) {
			Vector3f explosionLocation = new Vector3f(entity.position.x,
					entity.position.y, position.z);

			world.removeEntity(entity);

			world.addEntity(new Explosion(explosionLocation, world),
					explosionLocation);
		} else if (entity instanceof Player) {
			Player player = (Player) entity;
			player.jumping = false;
		}
	}

	@Override
	public int compareTo(Entity that) {
		return new Float(position.z).compareTo(that.position.z);
	}

}
