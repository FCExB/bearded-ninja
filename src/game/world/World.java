package game.world;

import game.Camera;
import game.entities.Entity;
import game.entities.moving.Creature;
import game.entities.moving.Player;
import game.entities.stationary.Fence;
import game.entities.stationary.Flower;
import game.entities.stationary.Tree;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import util.Assets;
import util.Attributes;

public class World {
	private final int tileSize = 16;

	private int brightness = 255;

	private final Tile[][] tiles;

	private final int width, height;

	private final List<Entity> entities;

	private final List<Entity> toAdd = new LinkedList<Entity>();
	private final List<Entity> toRemove = new LinkedList<Entity>();

	private Player player;

	public World(int width, int height, List<Entity> entities)
			throws SlickException {

		this.width = width;
		this.height = height;
		this.entities = entities;

		tiles = new Tile[width][height];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(Assets.TILE_ONE);

				Vector3f tileVector = new Vector3f(x * tileSize + tileSize / 2,
						0, y * tileSize + tileSize);

				if (x == 0 || y == 0 || x == tiles.length - 1
						|| y == tiles[x].length - 1) {
					entities.add(new Fence(tileVector, this));

					tiles[x][y] = new Tile(Assets.MINECRAFT.getSubImage(7 * 16,
							0, 16, 16));
				} else if (Math.random() < 0.025) {
					entities.add(new Flower(tileVector, this));
				} else if (Math.random() < 0.005) {
					entities.add(new Tree(tileVector, this));
				} else if (Math.random() < 0.0025) {
					entities.add(new Creature(new Attributes(), tileVector,
							this));
				}
			}
		}
	}

	public void addPLayer(Player player) {
		this.player = player;
	}

	public Tile getTile(int x, int y) {
		try {
			return tiles[x][y];
		} catch (Exception e) {
			return null;
		}
	}

	public int getTileSize() {
		return tileSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Vector3f getPlayerLocation() {
		return player.getPosition();
	}

	public Color getFilter() {
		return new Color(255, 255, 255, 255);
		// return new Color(brightness, brightness, brightness);
	}

	public void update(int deltaT) {
		if (brightness > 0) {
			brightness -= Math.round(deltaT / 8);
		} else {
			brightness = 255;
		}
	}

	public void render(Camera camera) {
		float zScaler = camera.zScaler();

		for (int x = 0; x < tiles.length; x++) {
			for (int z = 0; z < tiles[x].length; z++) {
				int tileX = x * tileSize;
				int tileZ = z * tileSize;

				if (camera.inRenderView(new Vector3f(tileX, 0, tileZ))) {

					Tile currentTile = tiles[x][z];

					// Draw floor tile
					int xLocation = (tileX - camera.getX()) + 500;
					float yLocation = (tileZ - camera.getY()) * zScaler + 300;
					float xScale = 1;
					float yScale = zScaler;

					currentTile.getImage().draw(xLocation, yLocation,
							tileSize * xScale, tileSize * yScale, getFilter());
				}
			}
		}

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

						if (player != entity && player.collides(vertex)) {
							if (entities.contains(entity)) {
								player.hitBy(entity);
							}
							return false;
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
			return true;
		}

		return false;
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
		toRemove.clear();
	}
}
