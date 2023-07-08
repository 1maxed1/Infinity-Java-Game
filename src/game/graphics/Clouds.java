package game.graphics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import game.screen.GameWindow;
import game.entities.Dino;
import game.utils.Resource;

/**
 *File: Clouds.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The Clouds class manages and draws clouds in the game.
 */
public class Clouds {
    private final List<ImageCloud> listCloud;
    private final BufferedImage cloud;
    private final BufferedImage lavacloud;
    private final BufferedImage spacecloud;
    private final Dino dino;

    /**
     * Constructs a Clouds object with the specified width and Dino.
     *
     * @param width the width of the clouds
     * @param dino  the Dino object
     */
    public Clouds(int width, Dino dino) {
        this.dino = dino;

        // Load the cloud images based on the Dino's theme
        cloud = Resource.getResourceImage("res/images/origin/cloud.png");
        lavacloud = Resource.getResourceImage("res/images/lava/cloud.png");
        spacecloud = Resource.getResourceImage("res/images/space/cloud.png");

        // Create a list to store the cloud images
        listCloud = new ArrayList<>();

        // Add initial cloud images with different positions to the list
        ImageCloud imageCloud = new ImageCloud();
        imageCloud.posX = 0;
        imageCloud.posY = 30;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 150;
        imageCloud.posY = 40;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 300;
        imageCloud.posY = 50;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 450;
        imageCloud.posY = 20;
        listCloud.add(imageCloud);

        imageCloud = new ImageCloud();
        imageCloud.posX = 600;
        imageCloud.posY = 60;
        listCloud.add(imageCloud);
    }

    /**
     * Updates the positions of the cloud images based on the Dino's speed.
     */
    public void update() {
        Iterator<ImageCloud> itr = listCloud.iterator();
        ImageCloud firstElement = itr.next();
        float updateSpeed = 2; // Controls the speed of cloud movement
        firstElement.posX -= dino.getSpeedX() / updateSpeed;

        while (itr.hasNext()) {
            ImageCloud element = itr.next();
            element.posX -= dino.getSpeedX() / updateSpeed;
        }

        if (firstElement.posX < -cloud.getWidth()) {
            // Move the first cloud image to the end of the list
            listCloud.remove(firstElement);
            firstElement.posX = GameWindow.SCREEN_WIDTH;
            listCloud.add(firstElement);
        }
    }

    /**
     * Draws the cloud images on the screen based on the Dino's theme.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g) {
        for (ImageCloud imgLand : listCloud) {
            if (Objects.equals(dino.theme, "origin")) {
                g.drawImage(cloud, (int) imgLand.posX, imgLand.posY, null);
            } else if (Objects.equals(dino.theme, "lava")) {
                g.drawImage(lavacloud, (int) imgLand.posX, imgLand.posY, null);
            } else if (Objects.equals(dino.theme, "space")) {
                g.drawImage(spacecloud, (int) imgLand.posX, imgLand.posY, null);
            }
        }
    }

    /**
     * The ImageCloud class represents a cloud image with its position.
     */
    private static class ImageCloud {
        float posX;
        int posY;
    }
}
