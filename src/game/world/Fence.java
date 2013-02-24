package game.world;

import util.Assets;

public class Fence extends Tile {

	public Fence() {
		super(Assets.FENCE);
	}

	@Override
	public boolean walkable() {
		return false;
	}
}
