import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class ImageCompare {

    /* if the two images being compared are different in size make them equal */
    public BufferedImage resizeImage(BufferedImage image1, BufferedImage image2){
        int newHeight = image2.getHeight();
        int newWidth = image2.getWidth();
        Image temp = image1.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = newImage.createGraphics();
        graphics.drawImage(temp, 0, 0, null);
        graphics.dispose();
        return newImage;
    }

    public double calculateDifference(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();
        int width2 = image2.getWidth();
        int height2 = image2.getHeight();
        if (width != width2 || height != height2) {
            image1 = resizeImage(image1, image2);
        }
        int difference = 0;
        for (int y = 0; y < height2; y++) {
            for (int x = 0; x < width2; x++) {
                difference += differenceInPixels(image1.getRGB(x, y), image2.getRGB(x, y));
            }
        }
        int maxDifference = 3 * 255 * width * height;
        return 100.0 * difference / maxDifference;
    }

    public int differenceInPixels(int rgb1, int rgb2) {
      /* get individual RGB values from each param... */
        int r1 = (rgb1 >> 16) & 0xff;
        int g1 = (rgb1 >>  8) & 0xff;
        int b1 = (rgb1 >> 0)  & 0xff;
        int r2 = (rgb2 >> 16) & 0xff;
        int g2 = (rgb2 >>  8) & 0xff;
        int b2 = (rgb2 >> 0)  & 0xff;
        int difference = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
        return difference;
    }
}
