import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.UIManager;
import java.math.*;

public class Manager {

    private JFrame menuFrame;
    private MyButton[] buttons;
    private Frame pictureFrame, comparisonFrame;
    private Picture originalPicture, picture, comparisonPicture;
    private String filename = null;
    private String comparisonFilename = null;

    private static final int MENU_BUTTONS = 4;
    private static final int BASIC_BUTTONS = 6;
    private static final int ADVANCED_BUTTONS = 6;
    private static final String FILE = "file";
    private static final String COMPARISON = "comp";

    Manager(String filename) {
    	setupMenuPanel();
    	setupPrimaryImage(filename);
      setupComparisonImage(filename);
      comparisonFrame.setVisible(false);
      this.filename = filename;
    }

    /* Frame setup methods */

    public void setupMenuPanel() {
    	makeMenuButtons();

    	JPanel menuPanel = new JPanel();
    	menuPanel.setLayout(new GridLayout(buttons.length/2,2));
    	for (int i = 0; i < buttons.length; i++) {
    	    menuPanel.add(buttons[i]);
    	}

    	menuFrame = new JFrame("Image Processing Menu");
    	menuFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);
    	menuFrame.addWindowListener(new WindowAdapter() {
    		public void windowClosing(WindowEvent e) {
    		    System.exit(0);
    		}
    	});
    	menuFrame.pack();
    	menuFrame.setLocation(10, 50);
    	menuFrame.setVisible(true);
    }

    public void setupBasicPanel() {
        makeBasicButtons();

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(buttons.length/2,2));
        for (int i = 0; i < buttons.length; i++) {
            menuPanel.add(buttons[i]);
        }
        menuPanel.add(buttons[0]);
        menuPanel.add(buttons[1]);

        menuFrame = new JFrame("Basic Image Processing");
        menuFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);
        menuFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        menuFrame.pack();
        menuFrame.setLocation(10, 50);
        menuFrame.setVisible(true);
    }

    public void setupAdvancedPanel() {
        makeAdvancedButtons();

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(Color.black);
        menuPanel.setLayout(new GridLayout(buttons.length/2,2));
        for (int i = 0; i < buttons.length; i++) {
            menuPanel.add(buttons[i]);
        }
        menuPanel.add(buttons[2]);
        menuPanel.add(buttons[3]);
        menuPanel.add(buttons[0]);
        menuPanel.add(buttons[1]);

        menuFrame = new JFrame("Advanced Image Processing");
        menuFrame.getContentPane().add(menuPanel, BorderLayout.CENTER);
        menuFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        menuFrame.pack();
        menuFrame.setLocation(10, 50);
        menuFrame.setVisible(true);
    }

    /* Image setup */

    public void setupPrimaryImage(String filename) {
      this.picture = new Picture(filename);
      this.originalPicture = new Picture(filename);
      originalPicture.copyPicture(picture);
      pictureFrame = new Frame("Image", picture);
      pictureFrame.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
      });
      Point menuLocation = menuFrame.getLocation();
      pictureFrame.setLocation(menuLocation.x, menuLocation.y + menuFrame.getHeight() + 40);
      pictureFrame.pack();
      pictureFrame.setVisible(true);
    }

    public void setupComparisonImage(String filename) {
      this.comparisonPicture = new Picture(filename);
      comparisonFrame = new Frame("Comparator", comparisonPicture);
      comparisonFrame.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
      });
      Point pictureLocation = pictureFrame.getLocation();
      comparisonFrame.setLocation(pictureLocation.x + pictureFrame.getSize().width + 0, pictureLocation.y);
      comparisonFrame.pack();
      comparisonFrame.setVisible(true);
    }

    /* Button making methods */

    public void makeMenuButtons() {
    	  buttons = new MyButton[MENU_BUTTONS];
        buttons[0] = new MyButton("Basic Processing", new BasicWindow(), Color.yellow);
        buttons[1] = new MyButton("Advanced Processing", new AdvancedWindow(), Color.yellow);
        buttons[2] = new MyButton("Exit", new Quit(), Color.blue);
        buttons[3] = new MyButton("Choose File", new ChoiceWindow(), Color.blue);
    }

    public void makeBasicButtons() {
        buttons = new MyButton[BASIC_BUTTONS];
        buttons[0] = new MyButton("Export Image", new SaveImage(), Color.blue);
        buttons[1] = new MyButton("Main Menu", new Return(), Color.blue);
        buttons[2] = new MyButton("Flip Horizontal", new FlipHorizontal(), Color.yellow);
        buttons[3] = new MyButton("Flip Vertical", new FlipVertical(), Color.yellow);
        buttons[4] = new MyButton("Make Negative", new MakeNegative(), Color.yellow);
        buttons[5] = new MyButton("Make Grayscale", new MakeGrayscale(), Color.yellow);
    }

    public void makeAdvancedButtons(){
        buttons = new MyButton[ADVANCED_BUTTONS];
        buttons[0] = new MyButton("Exit", new Quit(), Color.blue);
        buttons[1] = new MyButton("Main Menu", new Return(), Color.blue);
        buttons[2] = new MyButton("Export Image", new SaveImage(), Color.blue);
        buttons[3] = new MyButton("Choose File", new ChoiceWindow(), Color.blue);
        buttons[4] = new MyButton("Compare Images", new ImageComparison(), Color.yellow);
        buttons[5] = new MyButton("Detect Edges", new EdgeDetection(), Color.yellow);
    }

    /* ActionListener Classes */
    /* Functional methods */

    class ChoiceWindow implements ActionListener {
        public void actionPerformed (ActionEvent e) {
          JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
          if(chooser.showSaveDialog(chooser) == JFileChooser.APPROVE_OPTION){
            String filename = chooser.getSelectedFile().getName();
            setFileName(filename, FILE);
            menuFrame.setVisible(false);
            pictureFrame.setVisible(false);
            setupPrimaryImage(filename);
            setupMenuPanel();
          }
        }
    }

    class SaveImage implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            try {
                JFrame popUpFrame = new JFrame();
                JLabel label = new JLabel("Please enter the name of the file (do not include the file extension):");
                label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
                String input = JOptionPane.showInputDialog(label);
                if (input != null){
                    String fullPath = input + ".jpg";
                    JOptionPane.showMessageDialog(popUpFrame, "The image has been saved as " + fullPath);
                    BufferedImage export = picture.getBufferedImage();
                    ImageIO.write(export, "JPG", new File(fullPath));
                }
                else{
                    JLabel cancelLabel = new JLabel("Image export cancelled!");
                    cancelLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
                    JOptionPane.showMessageDialog(popUpFrame, cancelLabel);
                }
            } catch (IOException a){
                System.err.println("An IOException was caught :" + a.getMessage());
            }
        }
    }

    class Return implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            menuFrame.setVisible(false);
            pictureFrame.setVisible(true);
            comparisonFrame.setVisible(false);
            setupMenuPanel();
        }
    }

    class AdvancedWindow implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            menuFrame.setVisible(false);
            pictureFrame.setVisible(true);
            setupAdvancedPanel();
        }
    }

    class BasicWindow implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            menuFrame.setVisible(false);
            pictureFrame.setVisible(true);
            setupBasicPanel();
        }
    }

    class Quit implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            System.exit(0);
      }
    }

    /* Basic processing methods */

    class FlipHorizontal implements ActionListener {
    	  public void actionPerformed (ActionEvent e) {
            BasicProcess basic = new BasicProcess();
            picture = basic.flipHorizontal(picture, picture.getWidth(), picture.getHeight());
            pictureFrame.refreshPicture(picture);
    	  }
    }

    class FlipVertical implements ActionListener {
        public void actionPerformed (ActionEvent e){
            BasicProcess basic = new BasicProcess();
            picture = basic.flipVertical(picture, picture.getWidth(), picture.getHeight());
            pictureFrame.refreshPicture(picture);
        }
    }

    class MakeNegative implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            BasicProcess basic = new BasicProcess();
            picture = basic.makeNegative(picture, picture.getWidth(), picture.getHeight());
            pictureFrame.refreshPicture(picture);
        }
    }

    class MakeGrayscale implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            BasicProcess basic = new BasicProcess();
            picture = basic.makeGrayscale(picture, picture.getWidth(), picture.getHeight());
            pictureFrame.refreshPicture(picture);
        }
    }

    /* Advanced processing methods */

    class ImageComparison implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            JFrame frame = new JFrame();
            ImageCompare compare = new ImageCompare();
            JOptionPane.showMessageDialog(frame, "Please choose first image: ",
            "Image Comparison", JOptionPane.PLAIN_MESSAGE);
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
                String image1 = chooser.getSelectedFile().getName();
                JOptionPane.showMessageDialog(frame, "Please choose second image: ",
                "Image Comparison", JOptionPane.PLAIN_MESSAGE);
                JFileChooser chooser1 = new JFileChooser(System.getProperty("user.dir"));
                if(chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION){
                    String image2 = chooser.getSelectedFile().getName();
                    setFileName(image1, FILE); setFileName(image2, COMPARISON);
                    pictureFrame.setVisible(false);
                    setupPrimaryImage(image1);
                    setupComparisonImage(image2);
                    BufferedImage test = picture.getBufferedImage();
                    BufferedImage validation = comparisonPicture.getBufferedImage();
                    double result = BigDecimal.valueOf(compare.calculateDifference(test, validation)).setScale(1, RoundingMode.HALF_UP).doubleValue();
                    JOptionPane.showMessageDialog(frame, "Image difference: " + result + "%",
                    "Image Comparison", JOptionPane.PLAIN_MESSAGE);
                  }
              }
          }
      }

    class EdgeDetection implements ActionListener {
        public void actionPerformed (ActionEvent e) {
            EdgeDetector edgeDetector = new EdgeDetector();
            BufferedImage bufferedImage = edgeDetector.detectEdges(picture.getBufferedImage());
            picture = new Picture(bufferedImage);
            pictureFrame.refreshPicture(picture);
        }
    }

    public void setFileName(String filename, String s){
        if(s == FILE){
            this.filename = filename;
        }
        else if (s == COMPARISON){
            this.comparisonFilename = filename;
        }
    }

    public static void main (String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            Manager manager = new Manager("welcome.jpg");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
