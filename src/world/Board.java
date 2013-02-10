package world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import world.all.Creature;
import world.all.Player;
import world.all.World;

public class Board extends JPanel implements Runnable {
	private World world;
	private Player player;
	
	
	Image thingy;
	int delay;
	
    public Board(int delay) {
        this.delay = delay;
        
        world = new World(100,100);
        player = new Player(50,50,20,16,world);
        
        initKeyBindings();
        
    }
    
    private void initKeyBindings(){
    	InputMap inputMap = getInputMap( );
    	ActionMap actionMap = getActionMap();
    	
    	inputMap.put(KeyStroke.getKeyStroke( "D" ), "moveRight");
    	actionMap.put("moveRight", player);
    	
    	inputMap.put(KeyStroke.getKeyStroke( "A" ), "moveLeft");
    	actionMap.put("moveLeft", player);
    	
    	inputMap.put(KeyStroke.getKeyStroke( "W" ), "moveUp");
    	actionMap.put("moveUp", player);
    	
    	inputMap.put(KeyStroke.getKeyStroke( "S" ), "moveDown");
    	actionMap.put("moveDown", player);
    	
    	inputMap.put(KeyStroke.getKeyStroke( "SPACE" ), "moveDown");
    	actionMap.put("space", player);
    }

    public void paint(Graphics g) {
    	world.update();
    	player.drawView(g, 10, 10);
    }
    
    @Override
	public void addNotify() {
    	super.addNotify();
    	Thread refresher = new Thread(this);
    	refresher.start();
    }
	
	@Override
	public void run()
	{
		while(true)
		{
			long start = System.nanoTime();
			repaint();
			long end = System.nanoTime();
			try {
				Thread.sleep(delay-((start-end)/10000000));
			} catch (InterruptedException e) {
			}
		}
	}
}
