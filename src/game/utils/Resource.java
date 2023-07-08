package game.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *File: Resources.java
 *@version : 1.0
 *@author  1maxed1 (Max)*/
public class Resource {

    public static BufferedImage getResourceImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));  // Read the image file from the specified path
        } catch (IOException e) {
            e.printStackTrace();  // Print the stack trace if an error occurs
        }
        return img;  // Return the loaded image
    }
}
