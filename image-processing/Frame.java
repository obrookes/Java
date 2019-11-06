import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.*;
import javax.swing.*;

public class Frame extends JFrame {

    private JLabel imageLabel;
    private JPanel imagePanel;
    private ImageIcon imageIcon;

    Frame(String name, Picture picture) {
    	  setName(name);
    	  framePicture(picture);
    }

    public void framePicture (Picture picture) {
        this.imageIcon = new ImageIcon();
        this.imageIcon.setImage(picture.getBufferedImage());
        imageLabel = new JLabel(imageIcon);
        this.imageLabel.setIcon(imageIcon);
        this.imagePanel = new JPanel();
        this.imagePanel.setBackground(Color.black);
        this.imagePanel.add(imageLabel);
        getContentPane().add(this.imagePanel, BorderLayout.CENTER);
        pack();
    }

    public void refreshPicture(Picture picture) {
    	  ImageIcon icon = new ImageIcon();
    	  icon.setImage(picture.getBufferedImage());
    	  imageLabel.setIcon(icon);
    	  pack();
    }

}
