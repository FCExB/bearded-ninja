package game.entities.moving;

import game.TheWild;
import game.entities.Entity;
import game.entities.LightEmitting;
import game.entities.MovingEntity;
import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import util.Assets;
import util.Attributes;

public class Player extends MovingEntity implements LightEmitting {

	private final StateBasedGame game;
	private final float acceleration = 30f;

	private final int reloadTime = 100;
	private int time;
	private int health = 100;

	public boolean jumping, torchOn = false;

	public Player(int x, int z, World world, StateBasedGame game)
			throws SlickException {
		// super(new SpriteSheet(Assets.PLAYER, 50, 50), new Vector3f(x, 0, z),
		// world);

		super(Assets.PLAYER, new Vector3f(x, 0, z), new Vector3f(0, 0, 0),
				world);

		this.game = game;
	}

	@Override
	protected Vector3f acceleration(int deltaT, GameContainer gc) {
		Input input = gc.getInput();

		int x = 0, z = 0, y = 0;

		if (!jumping && input.isKeyDown(Input.KEY_W)) {

			z--;
		}

		if (!jumping && input.isKeyDown(Input.KEY_S)) {

			z++;
		}

		if (!jumping && input.isKeyDown(Input.KEY_A)) {

			x--;
		}

		if (!jumping && input.isKeyDown(Input.KEY_D)) {

			x++;
		}

		if (!jumping && input.isKeyPressed(Input.KEY_SPACE)) {
			y++;
			jumping = true;
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
		if (health <= 0) {
			game.enterState(TheWild.TITLE_STATE);
		}

		time += deltaT;

		if (getVelocitySize() > 0.08) {
			animating = true;
		} else {
			animating = false;
		}

		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_C)) {
			Vector3f newPos = new Vector3f(position.x + getWidth(), 0,
					position.getZ() + getDepth());

			world.addEntity(new Creature(new Attributes(), newPos, world),
					newPos);
		}

		if (input.isKeyPressed(Input.KEY_T)) {
			if (torchOn) {
				torchOn = false;
			} else {
				torchOn = true;
			}
		}

		if (position.y <= 0) {
			jumping = false;
		}
	}

	public void shootAt(Vector3f aim) {
		if (time >= reloadTime) {

			time = 0;

			Vector3f launchSite = new Vector3f(position.x, height + 15,
					position.z);

			Vector3f velocity = Vector3f.sub(aim, position, null);
			velocity.scale(0.037f);

			Bullet bullet = new Bullet(launchSite, velocity, world);

			world.addEntity(bullet, launchSite);
		}
	}

	@Override
	public void hitBy(Entity entity) {
		super.hitBy(entity);

		if (entity instanceof Creature) {
			Creature c = (Creature) entity;

			health -= c.getStrength();
		}
	}

	@Override
	public Color filterAt(Vector3f pos) {
		if (torchOn) {
			Vector3f difference = Vector3f.sub(pos, position, null);

			float ratio = 0.2f / difference.length();

			return new Color(255 * ratio, 255 * ratio, 255 * ratio);
		}
		
		return new Color(0,0,0);
	}
}
