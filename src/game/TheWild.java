package game;

import game.entities.Attributes;
import game.entities.Creature;
import game.entities.Entity;
import game.entities.Player;
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
		int y = 600 - Mouse.getY();

		if (Mouse.isButtonDown(0)) {

			x = (x + camera.getX()) / world.getTileSize();
			y = (y + camera.getY()) / world.getTileSize();

			world.addFence(x, y);
		} else if (Mouse.isButtonDown(1)) {
			Vector3f newPos = new Vector3f(x + camera.getX(),
					y + camera.getY(), 0);

			world.addEntity(new Creature(Assets.CREATURE, new Attributes(),
					newPos, world), newPos);
		}

		for (Entity e : entities) {
			e.update(delta, container);
		}

		camera.update(container, player);

		world.removeAllEntities();
		world.addAllEntities();

	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		world.render(camera);

		Collections.sort(entities);

		for (Entity e : entities) {
			e.render(camera);
		}
	}
}
