package game.entities;

import game.Camera;
import game.world.World;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;

import util.SpriteSheet;


public abstract class Entity {
	protected Vector3f position;
	private Vector3f velocity = new Vector3f(0, 0, 0);

	private final SpriteSheet movingAnimation;

	private int animationFrame;
	private int time;
	private int width, height;

	protected final World world;

	public Entity(SpriteSheet ss, Vector3f position, World world) {
		this.position = position;
		this.world = world;
		movingAnimation = ss;
		width = ss.getSpriteWidth();
		height = ss.getSpriteHeight();
	}

	public Entity(Image image, Vector3f position, World world) {
		this.position = position;
		this.world = world;
		movingAnimation = new SpriteSheet(image, image.getWidth(),
				image.getHeight());
		width = image.getWidth();
		height = image.getHeight();
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWidth(){
		return width;
	}
	
	public Vector3f getPosition(){
		return new Vector3f(position);
	}

	public void render(Camera camera) {
		if (camera.inView(position)) {

			Image image = movingAnimation.getSubImage(animationFrame, 0);

			image.draw(Math.round(position.getX() - camera.getX()), Math.round(position.getY()
					- camera.getY()));
		}
	}

	abstract Vector3f acceleration(int deltaT, GameContainer gc);

	public void update(int deltaT, GameContainer gc) {
		act(deltaT, gc);

		time += deltaT;
		if (time >= 90 && velocity.length() >= 0.025) {
			animationFrame = (animationFrame + 1)
					% movingAnimation.getNumberOfSpritesWide();
			time = 0;
		}
		
		Vector3f.add(velocity, acceleration(deltaT, gc), velocity);
		applyFriction(deltaT);

		Vector3f newPosition = Vector3f.add(position, velocity, null);
		
		if(world.positionClear(newPosition,this)){
			position = newPosition;
		} else {
			velocity = new Vector3f();
		}
	}
	
	private void applyFriction(int deltaT) {
		float friction = 0.99f;

		velocity.scale(friction);
	}

	protected void act(int deltaT, GameContainer gc) {
	}
	
	public boolean collides(Vector3f pos){
		return pos.x >= position.x && pos.x <= position.x + width
				&& pos.y >= position.y && pos.y <= position.y + height;
	}
	

}
