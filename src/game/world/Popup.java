package game.world;

import org.newdawn.slick.Image;

public class Popup extends Tile {

	Image verticalImage;

	public Popup(Image baseImage, Image verticalImage) {
		super(baseImage);
		this.verticalImage = verticalImage;
	}

	public Image getVerticalImage() {
		return verticalImage;
	}

}
