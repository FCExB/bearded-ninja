package game;

import game.entities.Player;

import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;

public class Camera {

	private int x;
	private int y;
	private int viewWidth;
	private int viewHeight;
	private float angle;

	public Camera(int x, int y, int viewWidth, int viewHeight) {
		this.x = x;
		this.y = y;
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.angle = 45;
	}

	public float zScaler() {
		return (float) Math.sin(angle * (Math.PI / 180));
	}

	public float otherScaler() {
		return (float) Math.cos(angle * (Math.PI) / 180);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
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

		int halfWidth = viewWidth / 2;
		float halfHeight = (viewHeight / zScaler()) / 2;

		return pos.x >= x - halfWidth && pos.x < x + halfWidth
				&& pos.z >= y - halfHeight && pos.z < y + halfHeight;
	}

	public void update(GameContainer gc, Player player, int deltaT) {

		float rotateSpeed = 0.06f;

		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_K) && angle < 90) {
			angle += rotateSpeed * deltaT;
		} else if (input.isKeyDown(Input.KEY_M) && angle > 2) {
			angle -= rotateSpeed * deltaT;
		}

		x = Math.round(player.getPosition().x);
		y = Math.round(player.getPosition().z);
	}
}
