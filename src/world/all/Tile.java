package world.all;

import java.awt.Image;


public class Tile {
	private Image defaultImage;
	private Image image;
	
	private boolean visible;
	
	public Tile(Image defaultImage){
		this.defaultImage = defaultImage;
		this.image = defaultImage;
	}
	
	public Image getImage(){
		return image;
	}
	
	public void setImage(Image newImage){
		image = newImage;
	}

	public void resetImage(){
		image = defaultImage;
	}
	
	public void setVisible(boolean visible){
		this.visible = visible;
	}
	
	public boolean isVisible(){
		return visible;
	}
}
