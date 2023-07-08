package src.game.entities;


import src.game.screen.GameWindow;
import src.game.utils.Resource;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Obstacle extends Enemy {

    public static final float Y_LAND = GameWindow.realHeight;

    private int posX;
    private final int width;
    private final int height;


    private final Dino dino;

    private final BufferedImage image;

    public Obstacle(Dino dino, int posX, int variation) {
        this.posX = posX;

        image = Resource.getResourceImage("res/images/"+dino.theme+"/cactus"+variation+".png");

        width = image.getWidth() - 10;
        height = image.getHeight() - 10;


        this.dino = dino;

    }

    public void update() {
        //Reduces the x-Position of the Obstacle
        posX -= dino.getSpeedX();

    }

    public void draw(Graphics g) {

        g.drawImage(image, posX, (int) (Y_LAND - image.getHeight()), null);


        Rectangle bound = getBound();
        g.setColor(Color.RED);
        //Leave it out for playable game
        g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    @Override
    public Rectangle getBound() {

        Rectangle rectBound = new Rectangle();
        //Draws a rectangle
        rectBound.x = posX + (image.getWidth() - width)/2;
        rectBound.y = (int) (Y_LAND - image.getHeight() + (image.getHeight() - height)/2);
        rectBound.width = width;
        rectBound.height = height;
        return rectBound;

    }


    @Override
    public boolean isOutOfScreen() {
        return posX < -image.getWidth();
    }

}
