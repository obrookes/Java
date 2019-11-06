import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EdgeDetector {

    private static final double[][] H_FILTER = {{1, 1, 1},{0, 0, 0},{-1, -1, -1}};
    private static final int MAX_RGB = 255;
    private static final int KERNEL_WIDTH = 3;
    private static final int KERNEL_HEIGHT = 3;

    /* Convolutions */

    public static double convolvePixel(double[][] image, int x, int y, double[][] kernel){
        double outputImage = 0;
        for (int i = 0; i < KERNEL_WIDTH; i++) {
            for (int j = 0; j < KERNEL_HEIGHT; j++) {
                outputImage = outputImage + (image[x + i][y + j] * kernel[i][j]);
            }
        }
        return outputImage;
    }

    public static double[][] convolveAndPad(double[][] image, int width, int height, double[][] kernel){
        int newWidth = width - KERNEL_WIDTH;
        int newHeight = height - KERNEL_HEIGHT;
        int margin = 1;


        double[][] convolvedImage = new double[newWidth][newHeight];
        for (int i = 0; i < newWidth; i++) {
            for (int j = 0; j < newHeight; j++) {
                convolvedImage[i][j] = convolvePixel(image, i, j, kernel);
            }
        }
        double resizedConvImage [][] = new double[width][height];
        for (int j = 0; j < newHeight; ++j) {
            for (int i = 0; i < newWidth; ++i) {
                resizedConvImage[i + margin][j + margin] = convolvedImage[i][j];
            }
        }
        return resizedConvImage;
        }

    public double[][] convolveImage(double[][] image, int width, int height, double[][] kernel) {
        double[][] convolvedImage = convolveAndPad(image, width, height, kernel);
        return convolvedImage;
    }

    /* Edge detection */

    public double[][][] vectoriseImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        double[][][] image = new double[3][height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(bufferedImage.getRGB(j, i));
                image[0][i][j] = color.getRed();
                image[1][i][j] = color.getGreen();
                image[2][i][j] = color.getBlue();
            }
        }
        return image;
    }

    public double[][] convolve(int width, int height, double[][][] image) {
        double[][] redConvolution = convolveImage(image[0], height, width, H_FILTER);
        double[][] greenConvolution = convolveImage(image[1], height, width, H_FILTER);
        double[][] blueConvolution = convolveImage(image[2], height, width, H_FILTER);
        double[][] lastConvolution = new double[redConvolution.length][redConvolution[0].length];
        for (int i = 0; i < redConvolution.length; i++) {
            for (int j = 0; j < redConvolution[i].length; j++) {
                lastConvolution[i][j] = redConvolution[i][j] + greenConvolution[i][j] + blueConvolution[i][j];
            }
        }
        return lastConvolution;
    }

    public BufferedImage createImage(BufferedImage image, double[][] convolvedImage){
        image = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < convolvedImage.length; i++) {
            for (int j = 0; j < convolvedImage[i].length; j++){
                int rgb = restrictRange(convolvedImage[i][j]);
                Color color = new Color(rgb, rgb, rgb);
                image.setRGB(j, i, color.getRGB());
            }
        }
        return image;
    }

    public BufferedImage detectEdges(BufferedImage bufferedImage){
        double[][][] image = vectoriseImage(bufferedImage);
        double[][] convolvedImage = convolve(bufferedImage.getWidth(), bufferedImage.getHeight(), image);
        return createImage(bufferedImage, convolvedImage);
    }

    public int restrictRange(double rgbValue) {
        if (rgbValue < 0) {
            rgbValue = -rgbValue;
        }
        if (rgbValue > MAX_RGB) {
            return MAX_RGB;
        } else {
            return (int) rgbValue;
        }
    }
}
