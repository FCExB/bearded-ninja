package util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Assets {

	public static Image TILE_ONE;
	public static Image TILE_TWO;
	public static Image FENCE;

	public static Image CREATURE;

	public static Image PLAYER;

	public Assets() throws SlickException {
		TILE_ONE = new Image("bin/data/grass.png");
		TILE_TWO = new Image("bin/data/rock.png");

		CREATURE = new Image("bin/data/creature.png");

		PLAYER = new Image("bin/data/overlap test.png");

		FENCE = new Image("bin/data/fence.png");
	}
}
