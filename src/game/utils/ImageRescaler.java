package src.game.utils;

import java.awt.Graphics2D;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageRescaler {
    public static void main(String[] args) {
        String inputFilePath = "path/to/input/image.png";
        int percentage = 20; // Increase width and height by 20%

        try {
            // Read the input image file
            BufferedImage originalImage = ImageIO.read(new File(inputFilePath));

            // Calculate the new width and height based on the percentage increase
            int newWidth = (int) (originalImage.getWidth() * (1 + (percentage / 100.0)));
            int newHeight = (int) (originalImage.getHeight() * (1 + (percentage / 100.0)));

            // Create a new image with the calculated dimensions
            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

            // Perform the resizing operation
            Graphics2D g2d = resizedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
            g2d.dispose();

            // Display the rescaled image
            displayImage(resizedImage);

            System.out.println("Image rescaled successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void displayImage(BufferedImage image) {
        // You can implement your own logic here to display the image using a GUI library
        // For simplicity, this example prints the image dimensions to the console
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println("Rescaled Image Dimensions: " + width + " x " + height);
    }
}
