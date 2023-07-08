package src.game.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import src.game.screen.GameWindow;
import src.game.entities.Dino;
import src.game.utils.Resource;
import src.game.screen.GameScreen;

public class Clouds {
    private final List<ImageCloud> listCloud;
    private final BufferedImage cloud;
    private final BufferedImage lavacloud;
    private final BufferedImage spacecloud;

    private final Dino dino;

    public Clouds(int width, Dino dino) {
        this.dino = dino;

        cloud = Resource.getResourceImage("res/images/origin/cloud.png");
        lavacloud = Resource.getResourceImage("res/images/lava/cloud.png");
        spacecloud = Resource.getResourceImage("res/images/space/cloud.png");
        listCloud = new ArrayList<ImageCloud>();

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

    public void update(){
        Iterator<ImageCloud> itr = listCloud.iterator();
        ImageCloud firstElement = itr.next();
        //How many clouds per sec
        //Higher --> Less
        float updateSpeed = 2;
        firstElement.posX -= dino.getSpeedX()/updateSpeed;
        while(itr.hasNext()) {
            ImageCloud element = itr.next();
            element.posX -= dino.getSpeedX()/updateSpeed;
        }
        if(firstElement.posX < -cloud.getWidth()) {
            listCloud.remove(firstElement);
            firstElement.posX = GameWindow.SCREEN_WIDTH;
            listCloud.add(firstElement);
        }
    }

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

        //Creates a new class only for params previosly defined
    private static class ImageCloud {
            float posX;
            int posY;
        }
    }
