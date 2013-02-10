package world.all;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import util.Position;
import util.Util;

public class World {
	private static final String ONE = "grass.png";
	private static final String TWO = "rock.png";
	private final Image tileOne = Util.loadImage("grass.png");
	private final Image tileTwo = Util.loadImage("rock.png");
	final Image creatueOne = Util.loadImage("creature.png");

	private final Tile[][] tiles;
	
	private List<Creature> creatures = new ArrayList<Creature>();

	private int width, height;

	public World(int width, int height) {
		this.width = width;
		this.height = height;

		tiles = new Tile[width][height];
		
		

		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				if(Math.random() < 0.3){
				tiles[x][y] = new Tile(tileTwo);
				} else {
					tiles[x][y] = new Tile(tileOne);
				}
			}
		}
	}
	
	public void addCreature(Creature creature){
		creatures.add(creature);
	}

	public Tile getTile(int x, int y) {
		try {
			return tiles[x][y];
		} catch(Exception e){
			return null;
		}
	}
	
	public Tile getTile(Position pos){
		return getTile(pos.getX(), pos.getY());
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public EdgeCase inWorld(Position p){
		if(p.getX() < 0){
			return EdgeCase.LEFT;
		} else if(p.getX() >= width){
			return EdgeCase.RIGHT;
		} else if(p.getY() < 0){
			return EdgeCase.TOP;
		} else if(p.getY() >= height){
			return EdgeCase.BOTTOM;
		}
		
		return null;
	}
	
	public void update(){
		for(Creature c : creatures){
			c.update();
		}
	}
}
