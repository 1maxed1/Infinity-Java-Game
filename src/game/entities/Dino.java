package src.game.entities;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import src.game.graphics.Land;
import src.game.screen.GameWindow;
import src.game.utils.Animation;
import src.game.utils.Resource;
public class Dino {

    //Before it was this GameWindow.SCREEN_HEIGHT - 75
    public float LAND_POSY = Math.round(GameWindow.realHeight-45+GameWindow.difference);
    public static final float GRAVITY = 0.5f;

    private static final int NORMAL_RUN = 0;
    private static final int JUMPING = 1;
    private static final int DOWN_RUN = 2;
    private static final int DEATH = 3;

    private float posY;
    private final float posX;
    private float speedX;
    private float speedY;

    public int score = 0;

    private int state = NORMAL_RUN;

    //origin dino
    private final Animation normalRunAnim;
    private final BufferedImage jumping;
    private final Animation downRunAnim;
    private final BufferedImage deathImage;

    //lava dino
    private final Animation lavanormalRunAnim;
    private final BufferedImage lavajumping;
    private final Animation lavadownRunAnim;
    private final BufferedImage lavadeathImage;

    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;
    private static final long SCORE_UPDATE_INTERVAL = 200; // 200second
    private long lastScoreUpdateTime = 0;

    public int highScore = 0;

    public String theme;

    public Dino(String theme) {
        this.theme = theme;
        posX = 50;


        posY = LAND_POSY;
        //One can certainly find a cleaner way to do this, but it works

        /*
          These are the animations for the origin dino
          */
        normalRunAnim = new Animation(90);
        normalRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character2.png"));
        normalRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character1.png"));
        jumping = Resource.getResourceImage("res/images/origin/main-character3.png");

        //
        downRunAnim = new Animation(90);
        downRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character5.png"));
        downRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character6.png"));

        //DeathImage
        deathImage = Resource.getResourceImage("res/images/origin/main-character4.png");

        /*These are the animations for the lava dino
        * */
        lavanormalRunAnim = new Animation(90);
        lavanormalRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character6.png"));
        lavanormalRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character1.png"));

        lavajumping = Resource.getResourceImage("res/images/lava/main-character3.png");

        //
        lavadownRunAnim = new Animation(90);
        lavadownRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character4.png"));
        lavadownRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character5.png"));

        //DeathImage
        lavadeathImage = Resource.getResourceImage("res/images/lava/main-character2.png");

        //Note the animations for the space dino are the origin dino cause Elias didnt design any different ones

        try {
            //Sounds are loaded
            jumpSound =  Applet.newAudioClip(new URL("file","","res/sounds/origin/jump.wav"));
            deadSound =  Applet.newAudioClip(new URL("file","","res/sounds/origin/dead.wav"));
            scoreUpSound =  Applet.newAudioClip(new URL("file","","res/sounds/origin/scoreup.wav"));

        } //Catches if the url provided is wrong
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public float getSpeedX() {
        return speedX;
    }
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setHighScoreAfterDeath(){
        if(highScore < score){
            highScore = score;
        }
    }

    public void draw(Graphics g) {
        switch(state) {
            case NORMAL_RUN:
                //Only possible because no maincharacter pics for space
                if(Objects.equals(theme, "origin")||Objects.equals(theme, "space" )){
                    g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
                } else if(Objects.equals(theme, "lava")){
                    g.drawImage(lavanormalRunAnim.getFrame(), (int) posX, (int) posY, null);
                }
                break;
            case JUMPING:
                //Only possible because no maincharacter pics for space

                if(Objects.equals(theme, "origin")||Objects.equals(theme, "space" )){
                    g.drawImage(jumping, (int) posX, (int) posY, null);
                } else if(Objects.equals(theme, "lava")){
                    g.drawImage(lavajumping, (int) posX, (int) posY, null);
                }
                break;
            case DOWN_RUN:
                //Only possible because no maincharacter pics for space
                if(Objects.equals(theme, "origin")||Objects.equals(theme, "space" )){
                    g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
                } else if(Objects.equals(theme, "lava")){
                    g.drawImage(lavadownRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
                }
                break;
            case DEATH:
                //Only possible because no maincharacter pics for space
                if(Objects.equals(theme, "origin")||Objects.equals(theme, "space" )){
                    g.drawImage(deathImage, (int) posX, (int) posY, null);
                } else if(Objects.equals(theme, "lava")){
                    g.drawImage(lavadeathImage, (int) posX, (int) posY, null);
                }
                break;
        }
      Rectangle bound = getBound();
      g.setColor(Color.RED);
        //Leave it out for playable game
      g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    public void update() {
        //Only possible because no maincharacter pics for space
        if(Objects.equals(theme, "origin" )||Objects.equals(theme, "space" )){
            normalRunAnim.updateFrame();
            downRunAnim.updateFrame();
        } else if(Objects.equals(theme, "lava")){
            lavanormalRunAnim.updateFrame();
            lavadownRunAnim.updateFrame();
        }

        if(posY > LAND_POSY) {
            //Lets the dino run when on landY position

            if(state != DOWN_RUN) {
                state = NORMAL_RUN;
            }
        } else {
            speedY += GRAVITY;
            posY += speedY;
        }
        // Update score based on time passed
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastScoreUpdateTime >= SCORE_UPDATE_INTERVAL) {
            lastScoreUpdateTime = currentTime;
            scoreUp();
        }
    }

    public void jump() {
        if(state == JUMPING) {

            if (posY >= LAND_POSY) {
                if (jumpSound != null) {
                    jumpSound.play();
                }
                speedY = -7.5f;
                posY += speedY;
                state = JUMPING;

            }

        }

    }

    public void down(boolean isDown) {
        //Jump can be interrrupted
        if(state == JUMPING) {

            //Places the dino on the land
            resetYPos();
        }
        //Either the state of normalRunning or downRunning --> either way same effect
        if(isDown) {
            state = DOWN_RUN;
        } else {
            state = NORMAL_RUN;
        }
    }

    public void setRunning(Boolean isRunning){
        if(isRunning){
            state = NORMAL_RUN;

        }
    }

   public void setJumping(Boolean isJumping){
        if(isJumping){
            state = JUMPING;
            jump();
        }

    }

    public void setDownRun(Boolean isDownRunning){
        if(isDownRunning){
            state = DOWN_RUN;
            down(true);
        } else {
            setRunning(true);
        }
    }

    public Rectangle getBound() {
        Rectangle rectBound = new Rectangle();
        if(state == DOWN_RUN) {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) posY + 20;
            //Only possible because no maincharacter pics for space
            if(Objects.equals(theme, "origin")||Objects.equals(theme, "space" )){
                rectBound.width = downRunAnim.getFrame().getWidth() - 10;
                rectBound.height = downRunAnim.getFrame().getHeight();

            } else if(Objects.equals(theme, "lava")){
                rectBound.width = lavadownRunAnim.getFrame().getWidth() - 10;
                rectBound.height = lavadownRunAnim.getFrame().getHeight();
            }

        } else {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) posY;
            //Only possible because no maincharacter pics for space
            if(Objects.equals(theme, "origin")||Objects.equals(theme, "space" )){
                rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
                rectBound.height = normalRunAnim.getFrame().getHeight();

            } else if(Objects.equals(theme, "lava")){
                rectBound.width = lavadownRunAnim.getFrame().getWidth() - 10;
                rectBound.height = lavadownRunAnim.getFrame().getHeight();
            }
        }
        return rectBound;
    }

    public void dead(boolean isDeath) {
        if(isDeath) {
            setHighScoreAfterDeath();
            state = DEATH;
            //Sets the theme back to normal
            theme = "origin";
        }
    }

    public void resetYPos() {
        posY = LAND_POSY;
    }

    public void playDeadSound() {
        deadSound.play();
    }

    public void scoreUp() {

        //For each new Frame this methode is called

        score ++;
        if(score % 50 == 0) {
            scoreUpSound.play();
            //Call the newTheme function
            setNewTheme();


        }
    }

    public void setScore(int goal){
        score = goal;
    }

    public void setNewTheme(){
        Random rand = new Random();
        int nextTheme = rand.nextInt(2);

        switch (theme){
            case "origin":


                if(nextTheme == 1){
                    theme = "space";
                }else{
                    theme = "lava";
                };

                break;
            case "lava":
                if(nextTheme == 1){
                    theme = "space";
                }else{
                    theme = "origin";
                };

                break;


            case "space":
                if(nextTheme == 1){
                    theme = "origin";
                }else{
                    theme = "lava";
                };
            break;
        }
        System.out.println(theme);

    }



}
