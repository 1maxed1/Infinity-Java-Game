package src.game.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.JPanel;

import src.game.entities.Bird;
import src.game.entities.Dino;
import src.game.graphics.Clouds;
import src.game.graphics.Land;
import src.game.utils.Resource;


public class GameScreen extends JPanel implements Runnable, KeyListener {
    private static final int START_GAME_STATE = 0;
    private static final int GAME_PLAYING_STATE = 1;
    private static final int GAME_OVER_STATE = 2;

    private final Land land;
    private final Dino dino;

    private Bird bird;

    private final GameLoop gameLoop;
    private final Clouds clouds;
    private boolean isKeyPressed;
    private int gameState = START_GAME_STATE;
    private final BufferedImage replayButtonImage;
    private final BufferedImage gameOverButtonImage;

    private final float rH;

    private float dif;

    private final float rW;

    public GameScreen() {
        //Start theme definded with this parameter

        dino = new Dino("origin");
        land = new Land(GameWindow.SCREEN_WIDTH, dino);


        //Determines how fast the dino is and thus how fast the object get rendered
        dino.setSpeedX(5);
        //Sets the replayButton and the gameOverbutton (not clickable)
        replayButtonImage = Resource.getResourceImage("res/images/replay_button.png");
        gameOverButtonImage = Resource.getResourceImage("res/images/gameover_text.png");
        //Starts the gameLoop
        gameLoop = new GameLoop(dino);

        rH = GameWindow.realHeight;
        rW = GameWindow.realWidth;
        dif = GameWindow.difference;
        //Starts painting the clouds
        clouds = new Clouds(GameWindow.SCREEN_WIDTH, dino);
    }

    public void startGame() {
        Thread thread = new Thread(this);

        thread.start();
    }

    public void gameUpdate() {
        if (gameState == GAME_PLAYING_STATE) {
            gameLoop.update();
            clouds.update();
            land.update();
            dino.update();


            if (gameLoop.isCollision()) {
                dino.playDeadSound();
                gameState = GAME_OVER_STATE;
                dino.dead(true);
            }
        }
    }

    //Update
    public void paint(Graphics g) {
        //add a themechange when a certain time is over
        if(Objects.equals(dino.theme , "origin")){
            g.setColor(Color.decode("#f7f7f7"));


        } else if(Objects.equals(dino.theme , "lava")){
            g.setColor(Color.BLACK);
        }else if(Objects.equals(dino.theme , "space")){
            g.setColor(Color.BLACK);
        }
        g.fillRect(0, 0, getWidth(), getHeight());


        switch (gameState) {
            case START_GAME_STATE:


                land.draw(g);
                dino.draw(g);


                break;
            case GAME_PLAYING_STATE:


                //Furthest away from the front gets rendered first
                clouds.draw(g);
                land.draw(g);
                gameLoop.draw(g);
                dino.draw(g);


                if(dino.highScore > 0){
                    //Draws the current high score as a String
                    g.drawString("HI " + dino.highScore, 500, (int) (0.1*rH));
                }

                //Draws the current run score as a String but only if highscore is not null
                //Red because clearly visible on backGround fillings of all themes
                g.drawString(String.valueOf(dino.score), (int) (0.8*rW), (int) (0.1*rH));





            case GAME_OVER_STATE:

                if (gameState == GAME_OVER_STATE) {
                    //Furthest away from the front
                    clouds.draw(g);

                    land.draw(g);
                    dino.draw(g);

                    g.drawString("You scored: " + dino.score, (int) (rW*0.4), (int) (0.1*rH));



                    g.drawImage(gameOverButtonImage, (int) ((GameWindow.windowWidth)*0.5-gameOverButtonImage.getWidth()/1.75), (int) (GameWindow.ORIGIN_SCREEN_HEIGHT*0.20), Color.RED, null);
                    g.drawImage(replayButtonImage, (int) ((GameWindow.windowWidth)*0.5-replayButtonImage.getWidth()), (int) (GameWindow.ORIGIN_SCREEN_HEIGHT*0.40), null);

                }
                break;
        }
    }

    @Override
    public void run() {
        // Add controls for regulating the speed of the game proportional to the highscore
        int fps = 60;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = System.nanoTime();
        long elapsed;

        int msSleep;
        int nanoSleep;

        try {
            do {
                gameUpdate();
                // Repaint the current component
                repaint();
                // endProcessGame = System.nanoTime();
                elapsed = (lastTime + msPerFrame - System.nanoTime());

                msSleep = (int) (elapsed / 1000000);
                nanoSleep = (int) (elapsed % 1000000);

                if (msSleep > 0) {
                    Thread.sleep(msSleep, nanoSleep);
                }
                lastTime = System.nanoTime();
            } while (true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //

    public void keyPressed(KeyEvent e) {
        if (!isKeyPressed) {
            isKeyPressed = true;
            switch (gameState) {
                case START_GAME_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
                        gameState = GAME_PLAYING_STATE;
                    }
                    break;
                case GAME_PLAYING_STATE:
                    // Added controls to the dino

                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        System.out.println("Space pressed");
                        dino.setJumping(true);


                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        System.out.println("Downkey pressed");
                        dino.setDownRun(true);

                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        System.out.println("UpKey pressed");
                        dino.setJumping(true);



                    }
                    break;
                case GAME_OVER_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
                        gameState = GAME_PLAYING_STATE;
                        //Calls the function to start a new game
                        resetGame();
                    }
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        isKeyPressed = false;
        if (gameState == GAME_PLAYING_STATE) {
            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                System.out.println("DownKey released");
                dino.setDownRun(false);


            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                System.out.println("UpKey released");
                dino.setJumping(false);

            } else if(e.getKeyCode() == KeyEvent.VK_SPACE){
                System.out.println("Space bar released");
                dino.setJumping(false);
            }
        }
    }



    @Override
    public void keyTyped(KeyEvent e) {
        //Does nothing
    }

    private void resetGame() {
        gameLoop.reset();
        dino.dead(false);
        dino.setScore(0);

    }
}
