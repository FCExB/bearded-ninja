package game.world;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Fence extends Tile {

	public Fence() throws SlickException {
		super(new Image("bin/data/fence.png"));
		
	}
	
	@Override
	public boolean walkable(){
		return false;
	}
}
