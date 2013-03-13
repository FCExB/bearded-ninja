package game.world;

import game.Camera;
import game.entities.Entity;
import game.entities.LightEmitting;
import game.entities.moving.Creature;
import game.entities.moving.Player;
import game.entities.stationary.Flower;
import game.entities.stationary.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import util.Assets;
import util.Attributes;

public class World {
	private static final int INITIAL_WIDTH = 1;
	private static final int INITIAL_HEIGHT = 1;

	private final int tileSize = 16;

	private float brightness = 2;

	private final SortedMap<Point, Tile> tiles;

	private final List<Entity> entities;
	private final List<LightEmitting> lights = new ArrayList<LightEmitting>();

	private final List<Entity> toAdd = new LinkedList<Entity>();
	private final List<Entity> toRemove = new LinkedList<Entity>();

	private Player player;

	public World(int width, int height, List<Entity> entities,
			SortedMap<Point, Tile> tiles, Camera camera) throws SlickException {

		this.entities = entities;
		this.tiles = tiles;

		for (int x = 0; x < INITIAL_WIDTH; x++) {
			for (int z = 0; z < INITIAL_HEIGHT; z++) {
				addTile(x, z);
				addTile(-x, z);
				addTile(-x, -z);
				addTile(x, -z);
			}
		}

		return;
	}

	private void addTile(int x, int z) {
		addTile(new Point(x, z));
	}

	public void addTile(Point point) {

		if (tiles.containsKey(point)) {
			return;
		}

		Vector3f tileVector = new Vector3f(point.x * tileSize + tileSize / 2,
				0, point.z * tileSize + tileSize);

		if (Math.random() < 0.025) {
			entities.add(new Flower(tileVector, this));
		} else if (Math.random() < 0.005) {
			entities.add(new Tree(tileVector, this));
		} else if (Math.random() < 0.0025) {
			entities.add(new Creature(new Attributes(), tileVector, this));
		}

		Tile tile = new Tile(Assets.TILE_ONE);
		tiles.put(point, tile);
	}

	public int getTileSize() {
		return tileSize;
	}

	public Vector3f getPlayerLocation() {
		return player.getPosition();
	}

	public Color getGlobalFilter() {
		float temp = Math.abs(1 - brightness);
		return new Color(temp, temp, temp);
	}

	public void update(int deltaT) {

		if (brightness > 0) {
			brightness -= deltaT / 10000f;
		} else {
			brightness = 2;
		}
	}

	public void render(Camera camera) {
		float zScaler = camera.zScaler();

		Color globalFilter = getGlobalFilter();

		for (Map.Entry<Point, Tile> entry : tiles.entrySet()) {

			int tileX = entry.getKey().x * tileSize;
			int tileZ = entry.getKey().z * tileSize;

			if (camera.inRenderView(new Vector3f(tileX, 0, tileZ))) {

				Tile currentTile = entry.getValue();

				// Draw floor tile
				int xLocation = (tileX - camera.getX()) + 500;
				float yLocation = (tileZ - camera.getY()) * zScaler + 300;
				float xScale = 1;
				float yScale = zScaler;

				Color tileFilter = brightnessAtLocation(new Vector3f(tileX, 0,
						tileZ));

				tileFilter.add(globalFilter);

				currentTile.getImage().draw(xLocation, yLocation,
						tileSize * xScale, tileSize * yScale, tileFilter);
			}
		}
	}

	public Point getContainedPoint(int i, int j) {
		return new Point(i / getTileSize(), j / getTileSize());
	}

	public boolean positionClear(Vector3f pos, Entity entity) {
		if (entity.isSolid()) {

			for (float x = 0f; x <= 1; x++) {
				for (float y = 0f; y <= 1; y++) {
					for (float z = 0f; z <= 1; z++) {

						Vector3f vertex = new Vector3f(
								pos.x - entity.getWidth() / 2 + x
										* entity.getWidth(), pos.y + y
										* entity.getHeight(), pos.z
										- entity.getDepth() / 2 + z
										* entity.getDepth());

						for (Entity e : entities) {
							if (e != entity && e.collides(vertex)) {
								if (entities.contains(entity)) {
									e.hitBy(entity);
								}
								return false;
							}
						}
					}
				}
			}

		}

		return true;
	}

	public boolean addEntity(Entity entity, Vector3f pos) {

		if (positionClear(pos, entity)) {
			toAdd.add(entity);
			if (entity instanceof LightEmitting) {
				LightEmitting e = (LightEmitting) entity;
				lights.add(e);
			}
			return true;
		}

		return false;
	}

	public boolean addPlayer(Player player, Vector3f pos) {
		this.player = player;
		return addEntity(player, pos);
	}

	public void removeEntity(Entity entity) {
		toRemove.add(entity);
	}

	public void addAllEntities() {
		entities.addAll(toAdd);
		toAdd.clear();
	}

	public void removeAllEntities() {
		entities.removeAll(toRemove);
		lights.removeAll(toRemove);
		toRemove.clear();
	}

	public Color brightnessAtLocation(Vector3f pos) {
		Color result = new Color(0);

		for (LightEmitting light : lights) {
			Color lightEffect = light.filterAt(pos);

			result.r = Math.max(result.r, lightEffect.r);
			result.g = Math.max(result.g, lightEffect.g);
			result.b = Math.max(result.b, lightEffect.b);
		}

		return result;
	}
}
