package world;

import java.awt.Color;

import javax.swing.JFrame;

public class Display extends JFrame {
	
	public Display(){
		Board board = new Board(10);
		
		add(board);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 310);
        setLocationRelativeTo(null);
        setTitle("Tiles");
        setVisible(true);
        
        this.setBackground(new Color(255,255,255));
        
        new Thread(board).start();
	}
	
}
