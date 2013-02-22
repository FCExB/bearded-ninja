package game;

import java.util.LinkedList;
import java.util.List;

import game.entities.Entity;
import game.entities.Player;
import game.world.World;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;


public class TheWild extends BasicGame {

	Player player;
	Camera camera;
	World world;
	List<Entity> entities;
	
	public TheWild() throws SlickException{
		super("The Wild");
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		entities = new LinkedList();
		
		world = new World(100, 100, entities);
		player = new Player(50*world.getTileSize(),50*world.getTileSize(),world);
		
		world.addPLayer(player);
		
		camera = new Camera(50,50,0, 0, 600, 500);
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		player.update(delta, container);
		camera.update(container, player);
		
		for (Entity e : entities){
			e.update(delta, container);
		}
	}
	
	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		world.render(camera);
		
		for(Entity e : entities){
			e.render(camera);
		}
		
		
		player.render(camera);
	}
}
