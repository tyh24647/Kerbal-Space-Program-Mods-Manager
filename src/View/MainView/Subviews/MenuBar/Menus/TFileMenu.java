package View.MainView.Subviews.MenuBar.Menus;

import View.MainView.Subviews.MenuBar.Menus.MenuItems.TFileMenuItem;
import View.MainView.Subviews.MenuBar.TMenu;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * File menu for the menu bar
 *
 * @author Tyler Hostager
 * @version 1/19/17
 */
public class TFileMenu extends TMenu implements ActionListener {
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


    public TFileMenu() {
        // do nothing
    }

    public TFileMenu(String name) {
        setName(name);
        setText(name);
        addItemsToMenu();
    }

    private void addItemsToMenu() {
        for (TFileMenuItem item : DEFAULT_FILE_MENU_ITEMS) {
            item.addActionListener(this);
            add(item);

            // add separators between grouped menu items
            if (item.equals(NEW_ITEM)
                    || item.equals(PREFS_ITEM)
                    || item.equals(SAVE_AS_ITEM)) {
                add(new JSeparator());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(OPEN_ITEM)) {
            openItemSelected();
        } else if (e.getSource().equals(NEW_ITEM)) {
            // TODO
        } else if (e.getSource().equals(PREFS_ITEM)) {
            // TODO
        } else if (e.getSource().equals(SAVE_ITEM)) {
            saveItemSelected();
        } else if (e.getSource().equals(SAVE_AS_ITEM)) {

        } else if (e.getSource().equals(EXIT_ITEM)) {
            System.exit(0);
        }
    }




    private void openItemSelected() {

        /*
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
         */
        FileSystemView fsView = new FileSystemView() {

            @Override
            public File createNewFolder(File containingDir) throws IOException {
                return null;
            }
        };
        /*
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
         */

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSystemView(fsView);

        // TODO TEST TEST TEST TEST TEST TEST TEST TEST
        fileChooser.showOpenDialog(OPEN_ITEM);
        // TODO TEST TEST TEST TEST TEST TEST TEST TEST
    }



    private void saveItemSelected() {

        /*
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
         */
        FileSystemView fsView = new FileSystemView() {

            @Override
            public File createFileObject(File dir, String filename) {
                return super.createFileObject(dir, filename);
            }

            @Override
            public File createNewFolder(File containingDir) throws IOException {
                return null;
            }
        };
        /*
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
         */

        JFileChooser fileChooser = new JFileChooser("src/UserData/" + getName());
        fileChooser.setFileSystemView(fsView);
        fileChooser.showSaveDialog(new JDialog());
    }



    private void saveAsItemSelected() {
        /*
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
         */
        FileSystemView fsView = new FileSystemView() {

            @Override
            public File createFileObject(File dir, String filename) {
                return super.createFileObject(dir, filename);
            }

            @Override
            public File createNewFolder(File containingDir) throws IOException {
                return null;
            }
        };
        /*
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
         */

        JFileChooser fileChooser = new JFileChooser("src/UserData/" + getName());
        fileChooser.setFileSystemView(fsView);
        fileChooser.showSaveDialog(new JDialog());
        fileChooser.setDialogTitle("Save as...");
    }

    public TFileMenuItem[] getFileMenuItems() {
        return DEFAULT_FILE_MENU_ITEMS;
    }
}
