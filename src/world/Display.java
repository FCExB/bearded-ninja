package world;

import javax.swing.JFrame;

public class Display extends JFrame {
	
	public Display(){
		Board board = new Board(100);
		
		add(board);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(360, 310);
        setLocationRelativeTo(null);
        setTitle("Tiles");
        setVisible(true);
        
        new Thread(board).start();
	}
	
}
