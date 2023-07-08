package src.game.entities;

import src.game.screen.GameWindow;
import src.game.utils.Animation;
import src.game.utils.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;



public class Bird extends Enemy{

    private int posX;

    private final Dino dino;
    private float rH = GameWindow.realHeight;

    private float dif = GameWindow.difference;

    //Array with the different heights of the birds

    private static final int[] BirdHeightsY = {Math.round(GameWindow.realHeight-30+GameWindow.difference),  Math.round(GameWindow.realHeight-40+GameWindow.difference), Math.round(GameWindow.realHeight-20+GameWindow.difference)};

    private final int randomY;

    private final Animation anim;
    private final Animation lavaanim;
    private final Animation spaceanim;



    public Bird(Dino dino, int posX){
        this.posX = posX;

        this.dino = dino;

        anim  = new Animation(90);
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
    @Override
    public void update() {
        //Plays the animation for different themes;
        if(Objects.equals(dino.theme, "lava")){
            lavaanim.updateFrame();
        } else if(Objects.equals(dino.theme, "origin")){
            anim.updateFrame();
        }
        else if(Objects.equals(dino.theme, "space")){
            spaceanim.updateFrame();
        }
        //Reduce the posY on the screen as dino "moves"
        posX -= dino.getSpeedX();
    }



    @Override
    public void draw(Graphics g) {
        if(Objects.equals(dino.theme, "origin")){
            g.drawImage(anim.getFrame(), posX, randomY- anim.getFrame().getHeight(), null);
        } else if(Objects.equals(dino.theme, "lava")){
            g.drawImage(lavaanim.getFrame(), posX, randomY- anim.getFrame().getHeight(), null);
        }
        else if(Objects.equals(dino.theme, "space")){
            g.drawImage(spaceanim.getFrame(), posX, randomY- spaceanim.getFrame().getHeight(), null);
        }

        Rectangle bound = getBound();
        g.setColor(Color.RED);
        //Leave it out for playable game
        g.drawRect(bound.x, bound.y, bound.width, bound.height);

    }

    @Override
    public Rectangle getBound() {
        //Basically same as in the obstacle
        Rectangle rectBound = new Rectangle();
        //Draws a rectangle
        //Right now the collisionbox is too big to fit the dino under and over it--> fix

        if(Objects.equals(dino.theme, "origin")){
            rectBound.x = posX;
            rectBound.y = (int) (randomY- anim.getFrame().getHeight()/1.3);
            
            rectBound.width = anim.getFrame().getWidth();
            rectBound.height = anim.getFrame().getHeight()/2;
        } else if(Objects.equals(dino.theme, "lava")){
            rectBound.x = (int) (posX+ 0.1*lavaanim.getFrame().getWidth());
            //Birdsheight = 48px
            rectBound.y = (int) (randomY- 1.5*lavaanim.getFrame().getHeight());
            
            rectBound.width = (int) (0.6*lavaanim.getFrame().getWidth());
            rectBound.height = (int) (0.6*lavaanim.getFrame().getHeight());
        }
        else if(Objects.equals(dino.theme, "space")){
            rectBound.x = (int) (posX+ 0.1*lavaanim.getFrame().getWidth());
            rectBound.y = (int) (randomY- 1.5*lavaanim.getFrame().getHeight());
            
            rectBound.width = (int) (0.6*lavaanim.getFrame().getWidth());
            rectBound.height = (int) (0.6*lavaanim.getFrame().getHeight());
        }

        return rectBound;
    }

    @Override
    public boolean isOutOfScreen() {
        //Same as the obstacle
        return posX < -anim.getFrame().getWidth();
    }

    public int getY(){
        // Creating an instance of the Random class
        Random random = new Random();

        // Generating a random index within the valid range of the array
        int randomIndex = random.nextInt(BirdHeightsY.length);

        // Getting the random value from the array using the random index
        return BirdHeightsY[randomIndex];
    }
}
