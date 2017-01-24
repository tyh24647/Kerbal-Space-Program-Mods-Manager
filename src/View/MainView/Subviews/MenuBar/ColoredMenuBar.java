package View.MainView.Subviews.MenuBar;

import javax.swing.*;
import java.awt.*;

import static com.sun.glass.ui.Window.TRANSPARENT;

/**
 * @author Tyler Hostager
 * @version 1/21/17
 */
public class ColoredMenuBar extends JMenuBar {
    Color bgColor = new Color(TRANSPARENT);

    public void setColor(Color color) {
        //bgColor = color;
        bgColor = Color.CYAN.darker().darker();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
    }
}
