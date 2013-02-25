package game.world;

import game.Camera;
import game.entities.Entity;
import game.entities.moving.Player;
import game.entities.stationary.Rock;
import game.entities.stationary.Tree;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.SlickException;

import util.Assets;

public class World {
	private final int tileSize = 16;

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

				if (x == 0 || y == 0 || x == tiles.length - 1
						|| y == tiles[x].length - 1) {
					tiles[x][y] = new Fence();
				} else if (Math.random() < 0.2) {
					entities.add(new Rock(false, new Vector3f(x * tileSize
							+ tileSize / 2, 0, y * tileSize + tileSize / 2),
							this));
				} else if (Math.random() < 0.01) {
					entities.add(new Tree(true, new Vector3f(x * tileSize
							+ tileSize / 2, 0, y * tileSize + tileSize / 2),
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

	public void render(Camera camera) {
		float zScaler = camera.zScaler();

		for (int x = 0; x < tiles.length; x++) {
			for (int z = 0; z < tiles[x].length; z++) {
				int tileX = x * tileSize;
				int tileZ = z * tileSize;

				if (camera.inView(new Vector3f(tileX, 0, tileZ))) {

					Tile currentTile = tiles[x][z];

					// Draw floor tile
					int xLocation = (tileX - camera.getX()) + 400;
					float yLocation = (tileZ - camera.getY()) * zScaler + 300;
					float xScale = 1;
					float yScale = zScaler;

					currentTile.getImage().draw(xLocation, yLocation,
							tileSize * xScale, tileSize * yScale);
				}
			}
		}

	}

	public void addFence(int x, int y) {
		tiles[x][y] = new Fence();
	}

	private boolean indexInBounds(int xIndex, int yIndex) {
		return xIndex >= 0 && xIndex < tiles.length && yIndex >= 0
				&& yIndex < tiles[xIndex].length;
	}

	public boolean positionClear(Vector3f pos, Entity entity) {

		for (float x = pos.x - entity.getWidth() / 2; x <= pos.x
				+ entity.getWidth() / 2; x += tileSize) {
			for (float z = pos.z - entity.getDepth() / 2; z <= pos.z
					+ entity.getDepth() / 2; z += tileSize) {

				int xIndex = Math.round(x / tileSize);
				int zIndex = Math.round(z / tileSize);

				if (indexInBounds(xIndex, zIndex)
						&& !tiles[xIndex][zIndex].walkable() && pos.y < 17) {
					return false;
				}

				for (float y = pos.y - entity.getHeight() / 2; y <= pos.y
						+ entity.getHeight() / 2; y += tileSize) {

					for (Entity e : entities) {
						if (e != entity && e.collides(new Vector3f(x, y, z))) {
							if (entities.contains(entity)) {
								e.hitBy(entity);
							}
							return false;
						}
					}

					if (player != entity
							&& player.collides(new Vector3f(x, y, z))) {
						if (entities.contains(entity)) {
							player.hitBy(entity);
						}
						return false;
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
