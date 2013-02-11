package world.all;

import java.awt.Image;

import util.Position;

public class Creature {
	private Position pos;
	private Image image;
	private final World world;
	
	public Creature (Position position,Image image, World world){
		pos = position;
		this.world = world;
		this.image = image;
	}
	
	public Creature (int x, int y,Image image, World world){
		pos = new Position(x,y);
		this.world = world;
		this.image = image;
	}
	
	public void update(){
		if(Math.random() > 0.95){
			world.getTile(pos).resetImage();
			int randX = (int) Math.round((Math.random() * 2 - 1));
			int randY = (int) Math.round(Math.random() * 2 - 1);
			pos = new Position(pos.getX()+randX, pos.getY()+randY);
			world.getTile(pos).setImage(image);
		}
	}
}
