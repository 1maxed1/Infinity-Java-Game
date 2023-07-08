package game.entities;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *File: Enemy.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The abstract class Enemy represents an enemy entity in the game.
 * It provides abstract methods for updating, drawing, getting the bounding rectangle,
 * and checking if the enemy is out of the screen.
 */
public abstract class Enemy {

    /**
     * Updates the state of the enemy.
     */
    public abstract void update();

    /**
     * Draws the enemy on the screen.
     *
     * @param g the Graphics object
     */
    public abstract void draw(Graphics g);

    /**
     * Gets the bounding rectangle of the enemy.
     *
     * @return the bounding rectangle
     */
    public abstract Rectangle getBound();

    /**
     * Checks if the enemy is out of the screen.
     *
     * @return true if the enemy is out of the screen, false otherwise
     */
    public abstract boolean isOutOfScreen();
}
