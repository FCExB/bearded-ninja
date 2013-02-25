package util;

import org.newdawn.slick.Image;

public class SpriteSheet extends org.newdawn.slick.SpriteSheet {

	private final int spriteWidth, spriteHeight;

	public int getSpriteWidth() {
		return spriteWidth;
	}

	public int getSpriteHeight() {
		return spriteHeight;
	}

	public SpriteSheet(Image image, int spriteWidth, int spriteHeight) {
		super(image, spriteWidth, spriteHeight);
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
	}

	public int getNumberOfSpritesWide() {
		return this.width / spriteWidth;
	}

}
