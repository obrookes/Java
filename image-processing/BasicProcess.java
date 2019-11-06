import java.io.*;
import java.awt.*;
import java.awt.image.*;

public class BasicProcess {

  public Picture flipHorizontal(Picture picture, int width, int height){
    Pixel pixel1, pixel2;
    for (int x = 0; x < width/2; x++) {
      for (int y = 0; y < height; y++) {
          pixel1 = picture.getPixel(x, y);
          pixel2 = picture.getPixel(width-x-1, y);
          picture.setPixel(x, y, pixel2);
          picture.setPixel(width-x-1, y, pixel1);
      }
    }
    return picture;
  }

  public Picture flipVertical(Picture picture, int width, int height){
    Pixel pixel1, pixel2;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height / 2; y++) {
          pixel1 = picture.getPixel(x, y);
          pixel2 = picture.getPixel(x, height-y-1);
          picture.setPixel(x, y, pixel2);
          picture.setPixel(x, height-y-1, pixel1);
      }
    }
    return picture;
  }

  public Picture makeNegative(Picture picture, int width, int height){
    Pixel pixel;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
          pixel = picture.getPixel(x, y);
          pixel.makeNegative();
          picture.setPixel(x, y, pixel);
      }
    }
    return picture;
  }

  public Picture makeGrayscale(Picture picture, int width, int height){
    Pixel pixel;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        pixel = picture.getPixel(x, y);
        pixel.makeGrayscale();
        picture.setPixel(x, y, pixel);
      }
    }
    return picture;
  }


}
