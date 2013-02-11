package world.all;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;


import util.Position;
import util.Util;

public class Player extends AbstractAction {
	private Position position;

	private int viewWidth, viewHeight;

	Image image;

	private World world;

	public Player(int x, int y, int viewWidth, int viewHeight, World world) {

		position = new Position(x, y);
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		this.world = world;

		image = Util.loadImage("thingy.png");
	}

	public void drawView(Graphics g, int xOffset, int yOffset) {
		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i < viewWidth; i++) {
			for (int j = 0; j < viewHeight; j++) {
				int worldX = i + (position.getX() - viewWidth/2); 
				int worldY = j + (position.getY() - viewHeight/2); 
				
				Tile tile = world.getTile(worldX, worldY);

				if (!tile.equals(null)) {
					g2d.drawImage(tile.getImage(), i * 16 + xOffset, j * 16
							+ yOffset, null);
				}
				
				if (worldX == position.getX() && worldY == position.getY()) {
					g2d.drawImage(image, i * 16 + xOffset, j * 16 + yOffset,
							null);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		int x = position.getX();
		int y = position.getY();

		String command = e.getActionCommand();

		if (command.equals("d")) {
			// if (world.inWorld(new Position(x + 1, y)).equals(null)) {
			position = new Position(x + 1, y);
			// }
		} else if (command.equals("a")) {
			// if (world.inWorld(new Position(x - 1, y)).equals(null)) {
			position = new Position(x - 1, y);
			// }
		} else if (command.equals("w")) {
			// if (world.inWorld(new Position(x, y - 1)).equals(null)) {
			position = new Position(x, y - 1);
			// }
		} else if (command.equals("s")) {
			// if (world.inWorld(new Position(x, y + 1)).equals(null)) {
			position = new Position(x, y + 1);
			// }
		} else if (command.equals(" ")) {
			world.addCreature(new Creature(x, y, world.creatueOne, world));
		}

	}

}
