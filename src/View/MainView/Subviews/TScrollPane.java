package View.MainView.Subviews;

import javax.swing.*;

/**
 * Scroll pane which is stored in the center panel on the main frame. This pane
 * displays the JTable containing a list of the installed mods
 *
 * @author Tyler Hostager
 * @version 1/19/17
 */
public class TScrollPane extends JScrollPane {

    public TScrollPane() {
        super();
        initDefaults();
    }


    private void initDefaults() {
        setSize(800, 600);

    }


}
