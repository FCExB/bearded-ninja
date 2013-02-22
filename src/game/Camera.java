package game;

import game.entities.Player;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Camera {
	
	private int xOffset,yOffset, x,y, viewWidth, viewHeight;
	
	public Camera(int topLeftX, int topLeftY, int x, int y, int viewWidth, int viewHeight) {
		this.x = x;
		this.y = y;
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.xOffset = topLeftX;
		this.yOffset = topLeftY;
	}

	public int getX() {
		return x - xOffset;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y - yOffset;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getViewWidth() {
		return viewWidth;
	}

	public void setViewWidth(int viewWidth) {
		this.viewWidth = viewWidth;
	}

	public int getViewHeight() {
		return viewHeight;
	}

	public void setViewHeight(int viewHeight) {
		this.viewHeight = viewHeight;
	}

	public boolean inView(Vector3f pos) {
		return pos.x >= x && pos.x < x+viewWidth &&
			   pos.y >= y && pos.y < y+ viewHeight;
	}

	public void update(GameContainer gc, Player player) {
		x = Math.round(player.getPosition().x)-viewWidth/2;
		y = Math.round(player.getPosition().y)-viewHeight/2;
		
	}
}
