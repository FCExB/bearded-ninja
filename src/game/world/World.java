package game.world;

import game.Camera;
import game.entities.Creature;
import game.entities.Entity;
import game.entities.Player;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class World {
	private final int tileSize = 16;

	private final Image tileOne;
	private final Image tileTwo;

	private final Tile[][] tiles;

	private int width, height;
	
	private List<Entity> entities; 
	
	private Player player;

	public World(int width, int height,List<Entity> entities ) throws SlickException {
		tileOne = new Image("bin/data/grass.png");
		tileTwo = new Image("bin/data/rock.png");

		this.width = width;
		this.height = height;
		this.entities = entities;

		tiles = new Tile[width][height];

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if (Math.random() < 0.2) {
					tiles[x][y] = new Tile(tileTwo);
				} else {
					tiles[x][y] = new Tile(tileOne);
				}
				
				if (x == 0 || y == 0 || x == tiles.length-1 || y == tiles[x].length-1){
					tiles[x][y] = new Fence();
				}
			}
		}
	}
	
	public void addPLayer(Player player){
		this.player = player;
	}

	public Tile getTile(int x, int y) {
		try {
			return tiles[x][y];
		} catch (Exception e) {
			return null;
		}
	}
	
	public int getTileSize(){
		return tileSize;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Vector3f getPlayerLocation(){
		return player.getPosition();
	}

	public void render(Camera camera) {
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				int tileX = x * tileSize;
				int tileY = y * tileSize;

				if (camera.inView(new Vector3f(tileX, tileY, 0))) {
					tiles[x][y].getImage().draw(tileX - camera.getX(),
							tileY - camera.getY());
				}
			}
		}

	}

	public void addFence(int x, int y) {
		try {
			tiles[x][y] = new Fence();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}

	public boolean positionClear(Vector3f pos, Entity entity) {

		for (float x = pos.x; x <= pos.x + entity.getWidth(); x += tileSize) {
			for (float y = pos.y; y <= pos.y + entity.getHeight(); y += tileSize) {
				if (!tiles[Math.round(x / tileSize)][Math.round(y / tileSize)].walkable()) {
					return false;
				}
				
				for(Entity e : entities){
					if(e != entity && e.collides(new Vector3f(x,y,0))){
						return false;
					}
				}
				
				if(player != entity && player.collides(new Vector3f(x,y,0))){
					return false;
				}

			}
		}

		return true;
	}

	public void addCreature(Vector3f pos) {
		try {
			Creature newCreature = new Creature(pos,this);
			
			if(positionClear(pos,newCreature)){
				entities.add(newCreature);
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
}
