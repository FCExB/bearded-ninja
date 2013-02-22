package game.entities;

import game.world.Fence;
import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import util.SpriteSheet;

public class Player extends Entity {

	private final float acceleration = 2f;

	public Player(int x, int y, World world) throws SlickException {
		super(new SpriteSheet(new Image("bin/data/Test2.png"), 50, 50),
				new Vector3f(x, y, 0), world);

	}

	@Override
	Vector3f acceleration(int deltaT, GameContainer gc) {
		Input input = gc.getInput();

		int x = 0, y = 0;

		if (input.isKeyDown(Input.KEY_UP)) {

			y--;
		}

		if (input.isKeyDown(Input.KEY_DOWN)) {

			y++;
		}

		if (input.isKeyDown(Input.KEY_LEFT)) {

			x--;
		}

		if (input.isKeyDown(Input.KEY_RIGHT)) {

			x++;
		}

		Vector3f change = new Vector3f(x, y, 0);
		
		if (change.length() == 0){
			return change;
		}
		
		change.normalise().scale(acceleration * (deltaT / 1000f));

		return change;
	}

	@Override
	protected void act(int deltaT, GameContainer gc){
		Input input = gc.getInput();
		
		if (input.isKeyDown(Input.KEY_F)){
			
			world.addFence(Math.round((position.x + 65) / world.getTileSize()), Math
					.round((position.y + 30) / world.getTileSize()));
		} 
		
		if (input.isKeyDown(Input.KEY_C)){
			world.addCreature(new Vector3f(position.x+55,position.getY()+30,0));
		}
	}
}
