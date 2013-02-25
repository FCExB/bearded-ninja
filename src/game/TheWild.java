package game;

import game.entities.Entity;
import game.entities.moving.Creature;
import game.entities.moving.Player;
import game.world.World;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import util.Assets;
import util.Attributes;

public class TheWild extends BasicGame {

	private static final int WORLD_WIDTH = 50;
	private static final int WORLD_HEIGHT = 50;

	Player player;
	Camera camera;
	World world;
	List<Entity> entities;
	Assets assets;

	public TheWild() throws SlickException {
		super("The Wild");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		assets = new Assets();

		entities = new LinkedList<Entity>();

		world = new World(WORLD_WIDTH, WORLD_HEIGHT, entities);
		player = new Player((WORLD_WIDTH / 2) * world.getTileSize(),
				(WORLD_HEIGHT / 2) * world.getTileSize(), world);

		world.addPLayer(player);

		camera = new Camera(400, 300, 832, 632);

		entities.add(player);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {

		int x = Mouse.getX();
		int z = 600 - Mouse.getY();
		x = x + camera.getX() - 400;
		z = Math.round((z - 300) / camera.zScaler() + camera.getY());

		if (Mouse.isButtonDown(0)) {

			player.shootAt(new Vector3f(x, 0, z));

		} else if (Mouse.isButtonDown(1)) {
			Vector3f mousePoint = new Vector3f(x, 0, z);

			world.addEntity(new Creature(Assets.CREATURE, new Attributes(),
					mousePoint, world), mousePoint);
		}

		for (Entity e : entities) {
			e.update(delta, container);
		}

		camera.update(container, player, delta);

		world.removeAllEntities();
		world.addAllEntities();

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		world.render(camera);

		Collections.sort(entities);

		for (Entity e : entities) {
			e.render(camera, g);
		}
	}
}
