package View.MainView.Subviews.MenuBar;

import javax.swing.*;

/**
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class TMenuBar extends JMenuBar {
    private static final String     // TODO <-- eventually make shared
            FILE = "File", EDIT = "Edit", VIEW = "View";

    private TMenu fileMenu;

    public TMenuBar() {
        setFileMenu(generateFileMenu());
    }

    public void setFileMenu(TMenu fMenu) {
        fileMenu = fMenu == null ? generateFileMenu() : fMenu;
    }

    private TMenu generateFileMenu() {
        return new TMenu(FILE);
    }



    public TMenu getFileMenu() {
        return fileMenu == null ? generateFileMenu() : fileMenu;
    }
}
