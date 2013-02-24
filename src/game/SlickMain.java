package game;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class SlickMain {

	/**
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {
		TheWild game = new TheWild();

		AppGameContainer container = new AppGameContainer(game);

		container.setDisplayMode(800, 600, false);

		container.start();

	}

}
