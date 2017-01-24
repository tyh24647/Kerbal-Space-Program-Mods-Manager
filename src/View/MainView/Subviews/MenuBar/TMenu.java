package View.MainView.Subviews.MenuBar;

import View.MainView.Subviews.MenuBar.Menus.MenuItems.TFileMenuItem;

import javax.swing.*;

/**
 * Custom implementation of JMenu objects to allow for customizability of the menus and
 * menu items
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class TMenu extends JMenu {
    private static final String FILE = "File";
    private String name;
    private TFileMenuItem fileMenuItem;


    public TMenu() {
        // do nothing
    }

    public TMenu(String name) {
        setName(name);
        //initDefaults();
    }


    private void initDefaults() {
        setFileMenuItem(new TFileMenuItem(FILE));

        // TODO

        add(fileMenuItem);
    }


    public void setFileMenuItem(TFileMenuItem fileMItem) {
        if (fileMItem == null) {
            fileMItem = (TFileMenuItem) new JMenuItem("File");
        }

        fileMenuItem = fileMItem;
    }


    public TFileMenuItem getFileMenuItem() {
        return fileMenuItem == null ? (TFileMenuItem) new JMenuItem("File") : fileMenuItem;
    }

    /**
     * Assigns the name of the menu
     * @param name  The name to be assigned
     */
    @Override
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            return;
        }

        this.name = name;
    }


}
