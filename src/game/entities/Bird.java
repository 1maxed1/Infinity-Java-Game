package game.entities;

import game.screen.GameWindow;
import game.utils.Animation;
import game.utils.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

/**
 *File: Bird.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The Bird class represents a type of enemy called Bird in a game.
 * It extends the Enemy class.
 */
public class Bird extends Enemy {

    private int posX;
    private final Dino dino;
    private float rH = GameWindow.realHeight;
    private float dif = GameWindow.difference;
    private static final int[] BirdHeightsY = {Math.round(GameWindow.realHeight - 20 + GameWindow.difference),
            Math.round(GameWindow.realHeight - 10 + GameWindow.difference), Math.round(GameWindow.realHeight - 30 + GameWindow.difference)};
    private final int randomY;
    private final Animation anim;
    private final Animation lavaanim;
    private final Animation spaceanim;

    /**
     * Constructs a Bird object with the given Dino and X position.
     *
     * @param dino  the Dino object
     * @param posX  the X position of the Bird
     */
    public Bird(Dino dino, int posX) {
        this.posX = posX;
        this.dino = dino;
        anim = new Animation(90);
        anim.addFrame(Resource.getResourceImage("res/images/origin/birdUp.png"));
        anim.addFrame(Resource.getResourceImage("res/images/origin/birdDown.png"));
        lavaanim = new Animation(90);
        lavaanim.addFrame(Resource.getResourceImage("res/images/lava/birdUp.png"));
        lavaanim.addFrame(Resource.getResourceImage("res/images/lava/birdDown.png"));
        spaceanim = new Animation(90);
        spaceanim.addFrame(Resource.getResourceImage("res/images/space/birdUp.png"));
        spaceanim.addFrame(Resource.getResourceImage("res/images/space/birdDown.png"));
        randomY = getY();
    }

    /**
     * Updates the Bird's animation and position based on the Dino's theme and speed.
     */
    @Override
    public void update() {
        if (Objects.equals(dino.theme, "lava")) {
            lavaanim.updateFrame();
        } else if (Objects.equals(dino.theme, "origin")) {
            anim.updateFrame();
        } else if (Objects.equals(dino.theme, "space")) {
            spaceanim.updateFrame();
        }
        posX -= dino.getSpeedX();
    }

    /**
     * Draws the Bird on the screen based on the Dino's theme.
     *
     * @param g  the Graphics object
     */
    @Override
    public void draw(Graphics g) {
        if (Objects.equals(dino.theme, "origin")) {
            g.drawImage(anim.getFrame(), posX, randomY - anim.getFrame().getHeight(), null);
        } else if (Objects.equals(dino.theme, "lava")) {
            g.drawImage(lavaanim.getFrame(), posX, randomY - anim.getFrame().getHeight(), null);
        } else if (Objects.equals(dino.theme, "space")) {
            g.drawImage(spaceanim.getFrame(), posX, randomY - spaceanim.getFrame().getHeight(), null);
        }
        Rectangle bound = getBound();
        g.setColor(Color.RED);
        g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    /**
     * Returns the collision boundary of the Bird based on the Dino's theme.
     *
     * @return the collision boundary as a Rectangle object
     */
    @Override
    public Rectangle getBound() {
        Rectangle rectBound = new Rectangle();
        if (Objects.equals(dino.theme, "origin")) {
            rectBound.x = posX;
            rectBound.y = (int) (randomY - anim.getFrame().getHeight() / 1.3);
            rectBound.width = anim.getFrame().getWidth();
            rectBound.height = anim.getFrame().getHeight() / 2;
        } else if (Objects.equals(dino.theme, "lava")) {
            rectBound.x = (int) (posX + 0.1 * lavaanim.getFrame().getWidth());
            rectBound.y = (int) (randomY - 1.5 * lavaanim.getFrame().getHeight());
            rectBound.width = (int) (0.6 * lavaanim.getFrame().getWidth());
            rectBound.height = (int) (0.6 * lavaanim.getFrame().getHeight());
        } else if (Objects.equals(dino.theme, "space")) {
            rectBound.x = (int) (posX + 0.1 * lavaanim.getFrame().getWidth());
            rectBound.y = (int) (randomY - 1.5 * lavaanim.getFrame().getHeight());
            rectBound.width = (int) (0.6 * lavaanim.getFrame().getWidth());
            rectBound.height = (int) (0.6 * lavaanim.getFrame().getHeight());
        }
        return rectBound;
    }

    /**
     * Checks if the Bird is out of the screen.
     *
     * @return true if the Bird is out of the screen, false otherwise
     */
    @Override
    public boolean isOutOfScreen() {
        return posX < -anim.getFrame().getWidth();
    }

    /**
     * Generates a random Y coordinate for the Bird from a predefined array of heights.
     *
     * @return the random Y coordinate
     */
    public int getY() {
        Random random = new Random();
        int randomIndex = random.nextInt(BirdHeightsY.length);
        return BirdHeightsY[randomIndex];
    }
}
