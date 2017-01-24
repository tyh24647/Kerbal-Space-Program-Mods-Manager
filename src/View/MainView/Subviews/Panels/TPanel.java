package View.MainView.Subviews.Panels;

import javax.swing.*;
import java.awt.*;

/**
 * Custom implementation of the JPanel class which modifies the original class
 * in order to have custom attributes.
 *
 * @author Tyler Hostager
 * @version 1/19/17
 */
public class TPanel extends JPanel {
    private static final Dimension DEFAULT_SIZE = new Dimension(800, 600);
    private static final BorderLayout BORDER_LAYOUT = new BorderLayout();
    private static final Font DEFAULT_FONT = new Font("Helvetica", Font.PLAIN, 14);
    private static final String DEFAULT_NAME = "Main Panel";
    private static final boolean DEFAULT_VISIBILITY = true;

    private static final Color
            DEFAULT_BACKTROUND_COLOR = Color.WHITE,
            DEFAULT_FOREGROUND_COLOR = Color.BLACK
    ;

    public TPanel() {
        initDefaults();
    }


    private void initDefaults() {
        setSize(DEFAULT_SIZE);
        setLayout(BORDER_LAYOUT);
        setBackground(DEFAULT_BACKTROUND_COLOR);
        setForeground(DEFAULT_FOREGROUND_COLOR);
        setFont(DEFAULT_FONT);
        setName(DEFAULT_NAME);
        setVisible(DEFAULT_VISIBILITY);
        //setBorder(DEFAULT_BORDER);
    }



}
