package View.MainView.Subviews.MenuBar.Menus.MenuItems;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tyler Hostager
 * @version 1/21/17
 */
public class TMenuItem extends JMenuItem {

    @Override
    public void setOpaque(boolean isOpaque) {
        super.setOpaque(true);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.CYAN.darker());
    }
}
