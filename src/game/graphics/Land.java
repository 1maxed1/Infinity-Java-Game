package src.game.graphics;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import javafx.stage.Screen;

import src.game.entities.Dino;
import src.game.screen.GameWindow;
import src.game.utils.Resource;



public class Land {

    //Before it was this GameWindow.SCREEN_HEIGHT-60
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

    public Land(int width, Dino dino) {
        this.dino = dino;
        this.width = width;
        //Add different lands for different themes
        land1 = Resource.getResourceImage("res/images/origin/land1.png");
        land2 = Resource.getResourceImage("res/images/origin/land2.png");

        lavaland1 = Resource.getResourceImage("res/images/lava/land1.png");
        lavaland2 = Resource.getResourceImage("res/images/lava/land2.png");

        spaceland1 = Resource.getResourceImage("res/images/space/land1.png");
        spaceland2 = Resource.getResourceImage("res/images/space/land2.png");

        //Its rH-imageHeight
        float rH = GameWindow.realHeight;
        float dif = GameWindow.difference;
        posY = rH -land1.getHeight()+(dif);

        //Width of the screen
        int numberOfImageLand = width / land1.getWidth() + 2;

        listLand = new ArrayList<>();
        for(int i = 0; i < numberOfImageLand; i++) {
            ImageLand imageLand = new ImageLand();
            imageLand.posX = i * land1.getWidth();
            setImageLand(imageLand);
            listLand.add(imageLand);
        }
    }



    public void update(){
        Iterator<ImageLand> itr = listLand.iterator();
        ImageLand firstElement = itr.next();
        firstElement.posX -= dino.getSpeedX();

        float previousPosX = firstElement.posX;
        if(Objects.equals(dino.theme, "origin")){
            while(itr.hasNext()) {
                ImageLand element = itr.next();
                element.posX = previousPosX + land1.getWidth();

                previousPosX = element.posX;
            }
            if(firstElement.posX < -land1.getWidth()) {
                listLand.remove(firstElement);
                firstElement.posX = previousPosX + land1.getWidth();
                setImageLand(firstElement);
                listLand.add(firstElement);
            }
        } else if(Objects.equals(dino.theme, "lava")){
            while(itr.hasNext()) {
                ImageLand element = itr.next();
                element.posX = previousPosX + lavaland1.getWidth();

                previousPosX = element.posX;
            }
            if(firstElement.posX < -land1.getWidth()) {
                listLand.remove(firstElement);
                firstElement.posX = previousPosX + lavaland1.getWidth();
                setImageLand(firstElement);
                listLand.add(firstElement);
            }
        }
        else if(Objects.equals(dino.theme, "space")){
            while(itr.hasNext()) {
                ImageLand element = itr.next();
                element.posX = previousPosX + spaceland1.getWidth();

                previousPosX = element.posX;
            }
            if(firstElement.posX < -land1.getWidth()) {
                listLand.remove(firstElement);
                firstElement.posX = previousPosX + spaceland1.getWidth();
                setImageLand(firstElement);
                listLand.add(firstElement);
            }
        }
    }

    private void setImageLand(ImageLand imgLand) {
        int typeLand = getTypeOfLand();
        if(Objects.equals(dino.theme, "origin")){
            if(typeLand == 1) {
                imgLand.image = land1;
            } else {
                imgLand.image = land2;
            }
        } else if(Objects.equals(dino.theme, "lava")){
            if(typeLand == 1) {
                imgLand.image = lavaland1;
            } else {
                imgLand.image = lavaland2;
            }
        }
        else if(Objects.equals(dino.theme, "space")){
            if(typeLand == 1) {
                imgLand.image = spaceland1;
            } else {
                imgLand.image = spaceland2;
            }
        }
    }




    public void draw(Graphics g) {
        for(ImageLand imgLand : listLand) {

            g.drawImage(imgLand.image, (int) imgLand.posX, (int) posY, null);
        }
    }

    private int getTypeOfLand() {
        Random rand = new Random();
        return rand.nextInt(2) + 1;
    }




    private static class ImageLand {
        float posX;
        BufferedImage image;
    }

}
