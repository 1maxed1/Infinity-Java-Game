package game.entities;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;

import game.graphics.Land;
import game.screen.GameWindow;
import game.utils.Animation;
import game.utils.Resource;

/**
 *File: Dino.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The Dino class represents the main character in the game.
 * It controls the Dino's movement, animations, and interactions.
 */
public class Dino {

    public float LAND_POSY = Math.round(GameWindow.realHeight - 45 + GameWindow.difference);
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

    private final Animation normalRunAnim;
    private final BufferedImage jumping;
    private final Animation downRunAnim;
    private final BufferedImage deathImage;

    private final Animation lavanormalRunAnim;
    private final BufferedImage lavajumping;
    private final Animation lavadownRunAnim;
    private final BufferedImage lavadeathImage;

    private AudioClip jumpSound;
    private AudioClip deadSound;
    private AudioClip scoreUpSound;
    private static final long SCORE_UPDATE_INTERVAL = 200; // 200 milliseconds
    private long lastScoreUpdateTime = 0;

    public int highScore = 0;

    public String theme;

    /**
     * Constructs a Dino object with the specified theme.
     *
     * @param theme the theme of the Dino (origin, lava, or space)
     */
    public Dino(String theme) {
        this.theme = theme;
        posX = 50;
        posY = LAND_POSY;

        // Initialize animations and images for the origin theme
        normalRunAnim = new Animation(90);
        normalRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character2.png"));
        normalRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character1.png"));
        jumping = Resource.getResourceImage("res/images/origin/main-character3.png");
        downRunAnim = new Animation(90);
        downRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character5.png"));
        downRunAnim.addFrame(Resource.getResourceImage("res/images/origin/main-character6.png"));
        deathImage = Resource.getResourceImage("res/images/origin/main-character4.png");

        // Initialize animations and images for the lava theme
        lavanormalRunAnim = new Animation(90);
        lavanormalRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character6.png"));
        lavanormalRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character1.png"));
        lavajumping = Resource.getResourceImage("res/images/lava/main-character3.png");
        lavadownRunAnim = new Animation(90);
        lavadownRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character4.png"));
        lavadownRunAnim.addFrame(Resource.getResourceImage("res/images/lava/main-character5.png"));
        lavadeathImage = Resource.getResourceImage("res/images/lava/main-character2.png");

        try {
            // Load audio clips for sound effects
            jumpSound = Applet.newAudioClip(new URL("file", "", "res/sounds/origin/jump.wav"));
            deadSound = Applet.newAudioClip(new URL("file", "", "res/sounds/origin/dead.wav"));
            scoreUpSound = Applet.newAudioClip(new URL("file", "", "res/sounds/origin/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the horizontal speed of the Dino.
     *
     * @return the speed in the X direction
     */
    public float getSpeedX() {
        return speedX;
    }

    /**
     * Sets the horizontal speed of the Dino.
     *
     * @param speedX the speed in the X direction
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /**
     * Sets the high score to the current score if it's higher.
     */
    public void setHighScoreAfterDeath() {
        if (highScore < score) {
            highScore = score;
        }
    }

    /**
     * Draws the Dino on the screen based on its current state and theme.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g) {
        switch (state) {
            case NORMAL_RUN:
                if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
                    g.drawImage(normalRunAnim.getFrame(), (int) posX, (int) posY, null);
                } else if (Objects.equals(theme, "lava")) {
                    g.drawImage(lavanormalRunAnim.getFrame(), (int) posX, (int) posY, null);
                }
                break;
            case JUMPING:
                if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
                    g.drawImage(jumping, (int) posX, (int) posY, null);
                } else if (Objects.equals(theme, "lava")) {
                    g.drawImage(lavajumping, (int) posX, (int) posY, null);
                }
                break;
            case DOWN_RUN:
                if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
                    g.drawImage(downRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
                } else if (Objects.equals(theme, "lava")) {
                    g.drawImage(lavadownRunAnim.getFrame(), (int) posX, (int) (posY + 20), null);
                }
                break;
            case DEATH:
                if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
                    g.drawImage(deathImage, (int) posX, (int) posY, null);
                } else if (Objects.equals(theme, "lava")) {
                    g.drawImage(lavadeathImage, (int) posX, (int) posY, null);
                }
                break;
        }
        Rectangle bound = getBound();
        g.setColor(Color.RED);
        g.drawRect(bound.x, bound.y, bound.width, bound.height);
    }

    /**
     * Updates the state of the Dino and its animations.
     */
    public void update() {
        if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
            normalRunAnim.updateFrame();
            downRunAnim.updateFrame();
        } else if (Objects.equals(theme, "lava")) {
            lavanormalRunAnim.updateFrame();
            lavadownRunAnim.updateFrame();
        }

        if (posY > LAND_POSY) {
            if (state != DOWN_RUN) {
                state = NORMAL_RUN;
            }
        } else {
            speedY += GRAVITY;
            posY += speedY;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastScoreUpdateTime >= SCORE_UPDATE_INTERVAL) {
            lastScoreUpdateTime = currentTime;
            scoreUp();
        }
    }

    /**
     * Makes the Dino jump.
     */
    public void jump() {
        if (state == JUMPING) {
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

    /**
     * Makes the Dino crouch or stand up.
     *
     * @param isDown true to crouch, false to stand up
     */
    public void down(boolean isDown) {
        if (state == JUMPING) {
            resetYPos();
        }
        if (isDown) {
            state = DOWN_RUN;
        } else {
            state = NORMAL_RUN;
        }
    }

    /**
     * Sets the Dino to the running state.
     *
     * @param isRunning true to set the Dino to running state, false otherwise
     */
    public void setRunning(Boolean isRunning) {
        if (isRunning) {
            state = NORMAL_RUN;
        }
    }

    /**
     * Sets the Dino to the jumping state and makes it jump.
     *
     * @param isJumping true to set the Dino to jumping state and make it jump, false otherwise
     */
    public void setJumping(Boolean isJumping) {
        if (isJumping) {
            state = JUMPING;
            jump();
        }
    }

    /**
     * Sets the Dino to the down run state or normal run state.
     *
     * @param isDownRunning true to set the Dino to down run state, false to set it to normal run state
     */
    public void setDownRun(Boolean isDownRunning) {
        if (isDownRunning) {
            state = DOWN_RUN;
            down(true);
        } else {
            setRunning(true);
        }
    }

    /**
     * Gets the bounding rectangle of the Dino.
     *
     * @return the bounding rectangle
     */
    public Rectangle getBound() {
        Rectangle rectBound = new Rectangle();
        if (state == DOWN_RUN) {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) posY + 20;
            if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
                rectBound.width = downRunAnim.getFrame().getWidth() - 10;
                rectBound.height = downRunAnim.getFrame().getHeight();
            } else if (Objects.equals(theme, "lava")) {
                rectBound.width = lavadownRunAnim.getFrame().getWidth() - 10;
                rectBound.height = lavadownRunAnim.getFrame().getHeight();
            }
        } else {
            rectBound.x = (int) posX + 5;
            rectBound.y = (int) posY;
            if (Objects.equals(theme, "origin") || Objects.equals(theme, "space")) {
                rectBound.width = normalRunAnim.getFrame().getWidth() - 10;
                rectBound.height = normalRunAnim.getFrame().getHeight();
            } else if (Objects.equals(theme, "lava")) {
                rectBound.width = lavadownRunAnim.getFrame().getWidth() - 10;
                rectBound.height = lavadownRunAnim.getFrame().getHeight();
            }
        }
        return rectBound;
    }

    /**
     * Sets the Dino to the death state.
     *
     * @param isDeath true to set the Dino to death state, false otherwise
     */
    public void dead(boolean isDeath) {
        if (isDeath) {
            setHighScoreAfterDeath();
            state = DEATH;
            theme = "origin";
        }
    }

    /**
     * Resets the Y position of the Dino to the land position.
     */
    public void resetYPos() {
        posY = LAND_POSY;
    }

    /**
     * Plays the sound effect for Dino's death.
     */
    public void playDeadSound() {
        deadSound.play();
    }

    /**
     * Increases the score and plays a sound effect if the score is a multiple of 50.
     */
    public void scoreUp() {
        score++;
        if (score % 50 == 0) {
            scoreUpSound.play();
            setNewTheme();
        }
    }

    /**
     * Sets the score to the specified value.
     *
     * @param goal the score value to set
     */
    public void setScore(int goal) {
        score = goal;
    }

    /**
     * Sets a new theme for the Dino randomly.
     */
    public void setNewTheme() {
        Random rand = new Random();
        int nextTheme = rand.nextInt(2);

        switch (theme) {
            case "origin":
                if (nextTheme == 1) {
                    theme = "space";
                } else {
                    theme = "lava";
                }
                break;
            case "lava":
                if (nextTheme == 1) {
                    theme = "space";
                } else {
                    theme = "origin";
                }
                break;
            case "space":
                if (nextTheme == 1) {
                    theme = "origin";
                } else {
                    theme = "lava";
                }
                break;
        }
        System.out.println(theme);
    }
}
