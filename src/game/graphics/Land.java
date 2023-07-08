package game.graphics;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import game.entities.Dino;
import game.screen.GameWindow;
import game.utils.Resource;

/**
 *File: Land.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The Land class manages and draws land images in the game.
 */
public class Land {
    public static float posY;
    private final List<ImageLand> listLand;
    private final BufferedImage land1;
    private final BufferedImage land2;
    private final BufferedImage lavaland1;
    private final BufferedImage lavaland2;
    private final BufferedImage spaceland1;
    private final BufferedImage spaceland2;
    private int width;
    private final Dino dino;

    /**
     * Constructs a Land object with the specified width and Dino.
     *
     * @param width the width of the land
     * @param dino  the Dino object
     */
    public Land(int width, Dino dino) {
        this.dino = dino;
        this.width = width;

        // Load the land images based on the Dino's theme
        land1 = Resource.getResourceImage("res/images/origin/land1.png");
        land2 = Resource.getResourceImage("res/images/origin/land2.png");
        lavaland1 = Resource.getResourceImage("res/images/lava/land1.png");
        lavaland2 = Resource.getResourceImage("res/images/lava/land2.png");
        spaceland1 = Resource.getResourceImage("res/images/space/land1.png");
        spaceland2 = Resource.getResourceImage("res/images/space/land2.png");

        // Calculate the initial Y position of the land based on the Dino's theme
        float rH = GameWindow.realHeight;
        float dif = GameWindow.difference;
        posY = rH - land1.getHeight() + dif;

        // Calculate the number of land images needed to fill the width of the screen
        int numberOfImageLand = width / land1.getWidth() + 2;

        // Create a list to store the land images
        listLand = new ArrayList<>();

        // Add land images with different positions and set their images
        for (int i = 0; i < numberOfImageLand; i++) {
            ImageLand imageLand = new ImageLand();
            imageLand.posX = i * land1.getWidth();
            setImageLand(imageLand);
            listLand.add(imageLand);
        }
    }

    /**
     * Updates the positions of the land images based on the Dino's speed.
     */
    public void update() {
        Iterator<ImageLand> itr = listLand.iterator();
        ImageLand firstElement = itr.next();
        firstElement.posX -= dino.getSpeedX();

        float previousPosX = firstElement.posX;
        while (itr.hasNext()) {
            ImageLand element = itr.next();
            element.posX = previousPosX + land1.getWidth();
            previousPosX = element.posX;
        }

        if (firstElement.posX < -land1.getWidth()) {
            // Move the first land image to the end of the list
            listLand.remove(firstElement);
            firstElement.posX = previousPosX + land1.getWidth();
            setImageLand(firstElement);
            listLand.add(firstElement);
        }
    }

    /**
     * Sets the image of the given land image based on the Dino's theme.
     *
     * @param imgLand the ImageLand object
     */
    private void setImageLand(ImageLand imgLand) {
        int typeLand = getTypeOfLand();
        if (Objects.equals(dino.theme, "origin")) {
            if (typeLand == 1) {
                imgLand.image = land1;
            } else {
                imgLand.image = land2;
            }
        } else if (Objects.equals(dino.theme, "lava")) {
            if (typeLand == 1) {
                imgLand.image = lavaland1;
            } else {
                imgLand.image = lavaland2;
            }
        } else if (Objects.equals(dino.theme, "space")) {
            if (typeLand == 1) {
                imgLand.image = spaceland1;
            } else {
                imgLand.image = spaceland2;
            }
        }
    }

    /**
     * Draws the land images on the screen.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g) {
        for (ImageLand imgLand : listLand) {
            g.drawImage(imgLand.image, (int) imgLand.posX, (int) posY, null);
        }
    }

    /**
     * Generates a random type of land (1 or 2).
     *
     * @return the type of land
     */
    private int getTypeOfLand() {
        Random rand = new Random();
        return rand.nextInt(2) + 1;
    }

    /**
     * The ImageLand class represents a land image with its position and image.
     */
    private static class ImageLand {
        float posX;
        BufferedImage image;
    }
}
