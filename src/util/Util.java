package util;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Util {
	private static final String path = "C:/Users/Finlay/Documents/GitHub/bearded-ninja/bin/data/";
	
	
	public static Image loadImage(String file){
		try {
			return ImageIO.read(new File(path + file));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
