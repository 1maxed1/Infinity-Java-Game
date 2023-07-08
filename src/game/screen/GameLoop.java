package src.game.screen;//Library imports
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Package Imports

import src.game.entities.Bird;
import src.game.entities.Dino;
import src.game.entities.Obstacle;
import src.game.utils.Resource;
import src.game.entities.Enemy;

public class GameLoop {

    private BufferedImage cactus1;
    private BufferedImage cactus2;
    private Random rand;

    private List<Enemy> enemies;
    private Dino dino;

    private Bird bird;


    //Spawn point is the side furthest to the right
    private float spawnPoint = GameWindow.windowWidth;

    public GameLoop(Dino dino) {
        rand = new Random();
        this.dino = dino;



        enemies = new ArrayList<Enemy>();


        enemies.add(createEnemy());
    }

    public void update() {
        for(Enemy e : enemies) {
            e.update();
        }
        //Gets the enemy that was first added
        Enemy enemy = enemies.get(0);
        if(enemy.isOutOfScreen()) {
            //Purges the current enemy
            enemies.clear();
            //Creates a new enemy
            enemies.add(createEnemy());
        }
    }

    public void draw(Graphics g) {
        for(Enemy e : enemies) {
            e.draw(g);
        }
    }

    private Enemy createEnemy() {
        // Creates one of three possible enemies
        //Either cactus
        int type = rand.nextInt(3);
        if(type == 0) {
            System.out.println("Added an obstacle!");
            return new Obstacle(dino, (int) spawnPoint,  1);

        } else if(type == 1) {
            System.out.println("Added an  obstacle 2!");
            return new Obstacle(dino, (int) spawnPoint, 2);
        }
        else {
            System.out.println("Added a bird!");
            return new Bird(dino, (int) spawnPoint);

        }
    }

    public boolean isCollision() {
        for(Enemy e : enemies) {
            //checks for a collision as their boxes would intersect
            if (dino.getBound().intersects(e.getBound())) {
                return true;
            }
        }
        return false;
    }

    public void reset() {
        enemies.clear();
        //Needs to add another enemy else the thread would stop
        enemies.add(createEnemy());
    }

}
