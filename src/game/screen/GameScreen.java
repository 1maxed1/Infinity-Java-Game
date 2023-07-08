package game.screen;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Objects;
import javax.swing.JPanel;

import game.entities.Bird;
import game.entities.Dino;
import game.graphics.Clouds;
import game.graphics.Land;
import game.utils.Resource;

/**
 *File: GameScreen.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The GameScreen class represents the game screen and handles rendering and user input for the game.
 */
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

    /**
     * Constructs a GameScreen object.
     */
    public GameScreen() {
        dino = new Dino("origin");
        land = new Land(GameWindow.SCREEN_WIDTH, dino);
        dino.setSpeedX(5);
        replayButtonImage = Resource.getResourceImage("res/images/replay_button.png");
        gameOverButtonImage = Resource.getResourceImage("res/images/gameover_text.png");
        gameLoop = new GameLoop(dino);
        rH = GameWindow.realHeight;
        rW = GameWindow.realWidth;
        dif = GameWindow.difference;
        clouds = new Clouds(GameWindow.SCREEN_WIDTH, dino);
    }

    /**
     * Starts the game by creating and starting a new thread.
     */
    public void startGame() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * Updates the game state.
     */
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

    /**
     * Renders the game objects on the screen.
     *
     * @param g the Graphics object
     */
    public void paint(Graphics g) {
        if (Objects.equals(dino.theme, "origin")) {
            g.setColor(Color.decode("#f7f7f7"));
        } else if (Objects.equals(dino.theme, "lava")) {
            g.setColor(Color.BLACK);
        } else if (Objects.equals(dino.theme, "space")) {
            g.setColor(Color.BLACK);
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        switch (gameState) {
            case START_GAME_STATE:
                land.draw(g);
                dino.draw(g);
                break;
            case GAME_PLAYING_STATE:
                clouds.draw(g);
                land.draw(g);
                gameLoop.draw(g);
                dino.draw(g);
                if (dino.highScore > 0) {
                    g.drawString("HI " + dino.highScore, 500, (int) (0.1 * rH));
                }
                g.drawString(String.valueOf(dino.score), (int) (0.8 * rW), (int) (0.1 * rH));
            case GAME_OVER_STATE:
                if (gameState == GAME_OVER_STATE) {
                    clouds.draw(g);
                    land.draw(g);
                    dino.draw(g);
                    g.drawString("You scored: " + dino.score, (int) (rW * 0.4), (int) (0.1 * rH));
                    g.drawImage(gameOverButtonImage, (int) ((GameWindow.windowWidth) * 0.5 - gameOverButtonImage.getWidth() / 1.75), (int) (GameWindow.ORIGIN_SCREEN_HEIGHT * 0.20), Color.RED, null);
                    g.drawImage(replayButtonImage, (int) ((GameWindow.windowWidth) * 0.5 - replayButtonImage.getWidth()), (int) (GameWindow.ORIGIN_SCREEN_HEIGHT * 0.40), null);
                }
                break;
        }
    }

    @Override
    public void run() {
        int fps = 60;
        long msPerFrame = 1000 * 1000000 / fps;
        long lastTime = System.nanoTime();
        long elapsed;
        int msSleep;
        int nanoSleep;
        try {
            do {
                gameUpdate();
                repaint();
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

    @Override
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
                    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                        dino.setJumping(true);
                    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        dino.setDownRun(true);
                    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        dino.setJumping(true);
                    }
                    break;
                case GAME_OVER_STATE:
                    if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_UP) {
                        gameState = GAME_PLAYING_STATE;
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
                dino.setDownRun(false);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                dino.setJumping(false);
            } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                dino.setJumping(false);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Do nothing
    }

    private void resetGame() {
        gameLoop.reset();
        dino.dead(false);
        dino.setScore(0);
    }
}


