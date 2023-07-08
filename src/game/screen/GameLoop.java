package game.screen;

// Library imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Package imports
import game.entities.Bird;
import game.entities.Dino;
import game.entities.Obstacle;
import game.entities.Enemy;
import game.utils.Resource;
import game.screen.GameWindow;

/**
 *File: GameLoop.java
 *@version : 1.0
 *@author  1maxed1 (Max)
 * The GameLoop class manages the gameplay loop of the game.
 */
public class GameLoop {
    private BufferedImage cactus1;
    private BufferedImage cactus2;
    private Random rand;
    private List<Enemy> enemies;
    private Dino dino;
    private Bird bird;
    private float spawnPoint = GameWindow.windowWidth;

    /**
     * Constructs a GameLoop object with the specified Dino.
     *
     * @param dino the Dino object
     */
    public GameLoop(Dino dino) {
        rand = new Random();
        this.dino = dino;
        enemies = new ArrayList<Enemy>();
        enemies.add(createEnemy());
    }

    /**
     * Updates the game state.
     */
    public void update() {
        for (Enemy e : enemies) {
            e.update();
        }
        Enemy enemy = enemies.get(0);
        if (enemy.isOutOfScreen()) {
            enemies.clear();
            enemies.add(createEnemy());
        }
    }

    /**
     * Draws the game objects on the screen.
     *
     * @param g the Graphics object
     */
    public void draw(Graphics g) {
        for (Enemy e : enemies) {
            e.draw(g);
        }
    }

    /**
     * Creates a new enemy based on a random type.
     *
     * @return the created Enemy object
     */
    private Enemy createEnemy() {
        int type = rand.nextInt(3);
        if (type == 0) {
            System.out.println("Added an obstacle!");
            return new Obstacle(dino, (int) spawnPoint, 1);
        } else if (type == 1) {
            System.out.println("Added an obstacle 2!");
            return new Obstacle(dino, (int) spawnPoint, 2);
        } else {
            System.out.println("Added a bird!");
            return new Bird(dino, (int) spawnPoint);
        }
    }

    /**
     * Checks if there is a collision between the Dino and any enemy.
     *
     * @return true if there is a collision, false otherwise
     */
    public boolean isCollision() {
        for (Enemy e : enemies) {
            if (dino.getBound().intersects(e.getBound())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Resets the game by clearing the enemies list and adding a new enemy.
     */
    public void reset() {
        enemies.clear();
        enemies.add(createEnemy());
    }
}
