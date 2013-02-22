package game;

import org.newdawn.slick.Image;

public class SpriteSheet extends org.newdawn.slick.SpriteSheet {

	private int spriteWidth, spriteHeight;
	
	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public SpriteSheet(Image image, int tw, int th) {
		super(image, tw, th);
		spriteWidth = tw;
		spriteHeight = th;
	}
	
	public int getNumberOfSpritesWide(){
		return this.width/spriteWidth;
	}

}
