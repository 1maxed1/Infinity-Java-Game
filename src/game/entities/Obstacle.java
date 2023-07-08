package game.entities;

import game.screen.GameWindow;
import game.utils.Resource;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

/**
 *File: Obstacle.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The Obstacle class represents an obstacle entity in the game.
 * It extends the abstract class Enemy and provides implementation for its methods.
 */
public class Obstacle extends Enemy {

    public static final float Y_LAND = GameWindow.realHeight;

    private int posX;
    private final int width;
    private final int height;

    private final Dino dino;

    private final BufferedImage image;

    /**
     * Constructs an Obstacle object with the specified Dino, X position, and variation.
     *
     * @param dino      the Dino object
     * @param posX      the X position of the obstacle
     * @param variation the variation of the obstacle
     */
    public Obstacle(Dino dino, int posX, int variation) {
        this.posX = posX;

        // Load the image based on the Dino's theme and variation
        image = Resource.getResourceImage("res/images/" + dino.theme + "/cactus" + variation + ".png");

        width = image.getWidth() - 10;
        height = image.getHeight() - 10;

        this.dino = dino;
    }

    /**
     * Updates the state of the obstacle by reducing its X position based on the Dino's speed.
     */
    public void update() {
        posX -= dino.getSpeedX();
    }

    /**
     * Draws the obstacle on the screen.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g) {
        g.drawImage(image, posX, (int) (Y_LAND - image.getHeight()), null);

        Rectangle bound = getBound();
        g.setColor(Color.RED);
        //Leave it out for playable game
        g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    /**
     * Gets the bounding rectangle of the obstacle.
     *
     * @return the bounding rectangle
     */
    @Override
    public Rectangle getBound() {
        Rectangle rectBound = new Rectangle();
        rectBound.x = posX + (image.getWidth() - width) / 2;
        rectBound.y = (int) (Y_LAND - image.getHeight() + (image.getHeight() - height) / 2);
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;
    }

    /**
     * Checks if the obstacle is out of the screen.
     *
     * @return true if the obstacle is out of the screen, false otherwise
     */
    @Override
    public boolean isOutOfScreen() {
        return posX < -image.getWidth();
    }
}
