import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyButton extends JButton {

    private static final Font FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 22);

    MyButton(String buttonName, ActionListener listener, Color buttonColour) {
    	setFont(FONT);
    	setText(buttonName);
    	addActionListener(listener);
      setBackground(buttonColour);
    }
}
