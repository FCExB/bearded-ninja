package world.all;

import java.awt.Image;


public class Tile {
	Image defaultImage;
	Image image;
	
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
}
