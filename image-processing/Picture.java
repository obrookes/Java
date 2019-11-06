
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Picture {

    private int type;
    private BufferedImage bufferedImage;
    private WritableRaster imageRaster;

    Picture(BufferedImage image){
        this.bufferedImage = image;
    }

    Picture(String filename) {
        this.type = BufferedImage.TYPE_INT_RGB;
    	  importPicture(filename);
    }

    public int getImageType(){
        return this.type;
    }

    public int getWidth() {
        return this.bufferedImage.getWidth();
    }

    public int getHeight() {
        return this.bufferedImage.getHeight();
    }

    public BufferedImage getBufferedImage(){
        return this.bufferedImage;
    }

    public WritableRaster getRaster() {
        return this.imageRaster;
    }

    public void importPicture(String filename) {
      	ImageIcon icon = new ImageIcon(filename);
      	Image image = icon.getImage();
      	this.bufferedImage = new BufferedImage(image.getWidth(null),image.getHeight(null), this.type);
      	Graphics graphics = bufferedImage.getGraphics();
      	graphics.drawImage(image, 0, 0, null);
      	graphics.dispose();
      	this.imageRaster = bufferedImage.getRaster();
      }

    public void copyPicture(Picture picture) {
      	this.type = picture.getImageType();
      	this.bufferedImage = new BufferedImage(picture.getWidth(), picture.getHeight(), this.type);
      	this.imageRaster = bufferedImage.getRaster();
      	for (int x = 0; x < bufferedImage.getWidth(); x++) {
      	    for (int y = 0; y < bufferedImage.getHeight(); y++) {
      		      setPixel(x,y, picture.getPixel(x, y));
      	    }
      	}
    }

    public Pixel getPixel(int x, int y){
      	int[] pixelArray = null;
      	try {
      	    pixelArray = this.imageRaster.getPixel(x, y, pixelArray);
      	}
        catch (Exception e) {
        	  System.out.println("That's out of bounds!");
        	  System.exit(1);
      	}
        	Pixel pixel = new Pixel(pixelArray);
        	return pixel;
    }


    public void setPixel(int x, int y, Pixel pixel){
  	    int[] pixelArray = pixel.getPixel();
      	try {
      	    this.imageRaster.setPixel(x, y, pixelArray);
      	}
        catch (Exception e) {
      	    System.out.println("That's out of bounds!");
      	    System.exit(1);
      	}
    }
}
