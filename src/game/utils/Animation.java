package game.utils;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * File: Animation.java
 *@version : 1.0
 *@author  1maxed1 (Max)*/
public class Animation {
    private List<BufferedImage> list;  // List of frames in the animation
    private long deltaTime;  // Time duration between frames
    private int currentFrame = 0;  // Index of the current frame
    private long previousTime;  // Timestamp of the previous frame

    public Animation(int deltaTime) {
        this.deltaTime = deltaTime;
        list = new ArrayList<BufferedImage>();
        previousTime = 0;
    }

    public void updateFrame() {
        // Check if enough time has passed to switch to the next frame
        if (System.currentTimeMillis() - previousTime >= deltaTime) {
            currentFrame++;  // Move to the next frame
            if (currentFrame >= list.size()) {
                currentFrame = 0;  // Wrap around to the first frame if reached the end
            }
            previousTime = System.currentTimeMillis();  // Update the previous frame timestamp
        }
    }

    public void addFrame(BufferedImage image) {
        list.add(image);  // Add a frame to the animation
    }

    public BufferedImage getFrame() {
        return list.get(currentFrame);  // Get the current frame
    }
}
