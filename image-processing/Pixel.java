import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class Pixel {

    private int[] pixel;

    Pixel(int[] c) {
        pixel = new int[c.length];
        for (int i = 0; i < c.length; i++) {
            pixel[i] = c[i];
        }
    }

    public int[] getPixel() {
        return pixel;
    }

    /* for Negative */
    public void makeNegative(){
        for (int i = 0; i < pixel.length; i++){
            pixel[i] = 255 - pixel[i];
        }
    }

    /* for Grayscale-ing */
    public int getAverage(){
        int sum = 0;
        for (int i = 0; i < pixel.length; i++){
            sum += pixel[i];
        }
        int avg = sum / pixel.length;
        return avg;
    }

    public void makeGrayscale(){
        int avg = getAverage();
        for (int i = 0; i < pixel.length; i++){
            pixel[i] = avg;
        }
    }
}
