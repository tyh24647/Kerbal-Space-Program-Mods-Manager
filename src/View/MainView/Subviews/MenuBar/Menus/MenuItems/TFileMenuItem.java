package View.MainView.Subviews.MenuBar.Menus.MenuItems;

import javax.swing.*;

/**
 * File menu for the main JMenu
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class TFileMenuItem extends JMenuItem {
    private static final String OPEN = "Open", NEW = "New", PREFERENCES = "Preferences",
            SAVE = "Save", SAVE_AS = "Save as...", EXIT = "Exit", DEFAULT_NAME = "File";

    private static final TFileMenuItem
            OPEN_ITEM = new TFileMenuItem(OPEN),
            NEW_ITEM = new TFileMenuItem(NEW),
            PREFS_ITEM = new TFileMenuItem(PREFERENCES),
            SAVE_ITEM = new TFileMenuItem(SAVE),
            SAVE_AS_ITEM = new TFileMenuItem(SAVE_AS),
            EXIT_ITEM = new TFileMenuItem(EXIT);

    private static final TFileMenuItem[] DEFAULT_FILE_MENU_ITEMS = {
            OPEN_ITEM, NEW_ITEM, PREFS_ITEM, SAVE_ITEM, SAVE_AS_ITEM, EXIT_ITEM
    };


    /**
     * Default constructor
     */
    public TFileMenuItem() {
        initDefaults();
    }

    /**
     * Constructs a file menu object with the specified name
     * @param name  The name to be assigned to the menu
     */
    public TFileMenuItem(String name) {
        setName(name);
        setText(name);
        initDefaults();
    }



    private void initDefaults() {
        if (getText() == null || getText().isEmpty()) {
            setText(DEFAULT_NAME);
        }

        addMenuItems();
    }

    private void addMenuItems() {
        for (TFileMenuItem item : DEFAULT_FILE_MENU_ITEMS) {
            add(item);

            // adds separator line after the following menu items
            if (item.getName().equals(NEW) || item.getName().equals(PREFERENCES)
                    || item.getName().equals(SAVE_AS)) {
                add(new JSeparator());
            }
        }
    }

    public static TFileMenuItem getOpenItem() {
        return OPEN_ITEM;
    }

    public static TFileMenuItem getNewItem() {
        return NEW_ITEM;
    }

    public static TFileMenuItem getPrefsItem() {
        return PREFS_ITEM;
    }

    public static TFileMenuItem getSaveItem() {
        return SAVE_ITEM;
    }

    public static TFileMenuItem getSaveAsItem() {
        return SAVE_AS_ITEM;
    }

    public static TFileMenuItem getExitItem() {
        return EXIT_ITEM;
    }
}
