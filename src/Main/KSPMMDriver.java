package Main;

import AppData.Constants;
import Models.KSPMMModel;
import Models.KSPMMTableModel;
import Models.TableViewController;
import Utils.FileUtilities.FileManager;
import Utils.Log;
import Utils.OSUtils.OSManager;
import Utils.OSUtils.OSUtils;
import Utils.XMLUtilities.XMLWriter;
import View.MainView.MainGUI2;
import View.UIViewController;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

import static Utils.OSUtils.OSConstants.*;
import static AppData.Constants.*;

/**
 * <p>
 *     Main driver for the application, which executes once the program is run as
 *     well as creates new instances of the model, view controller, and user data
 *     objects.
 * </p>
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class KSPMMDriver {
    private static File currentLogFile;

    /**
     * Main method which initializes the program
     * @param args  The arguments taken in to run the program
     */
    public static void main(String[] args) {
        Constants constants;

        FileManager fileManager = initFileManager();
        Log.d("Initializing application...");

        // generate 'AppData' folder if missing
        Log.d("Initializing AppData...");
        fileManager.createFolderAtDefaultPath(DEFAULT_APPDATA_FOLDER_TITLE);

        // initialize user interface
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                Log.e(ex.getMessage());
                ex.printStackTrace();
            }

            File xmlFile = generateUserData();
            if (!xmlFile.exists()) {
                try {
                    xmlFile.createNewFile();
                } catch (IOException e) {
                    Log.e(e.getMessage());
                    e.printStackTrace();
                }

                Constants.setIsFirstRun(true);
            } else {
                Constants.setIsFirstRun(false);
            }

            KSPMMModel model = new KSPMMModel();
            KSPMMTableModel KSPMMTableModel = new KSPMMTableModel();
            TableViewController tViewComtroller = new TableViewController(KSPMMTableModel);
            MainGUI2 view = new MainGUI2(tViewComtroller);
            new UIViewController(model, view);
        });
    }

    /**
     * Generates the instance of file manager
     */
    private static FileManager initFileManager() {
        FileManager manager = new FileManager();
        configureOSTypeWithFileManager(manager);
        return manager;
    }

    /**
     * Determines which operating system the user is using
     */
    private static void configurePath(FileManager fileManager, String currentOS) {

        // store operating system in user text file

        // check for existing mods folder or create new one
        if (OSUtils.isWindows()) {
            // TODO
        } else if (OSUtils.isMacOS()) {
            // TODO
        } else if (OSUtils.isLinux()) {
            // TODO
        } else if (OSUtils.isOther()) {
            // TODO
        }


        //fileManager.createFolderAtDefaultPath();

    }

    /**
     * Sets the configuration for the program's functionality based on the
     * user's operating system
     *
     * @param fileManager    The current file manager instance
     */
    private static void configureOSTypeWithFileManager(FileManager fileManager) {
        OSManager osManager = new OSManager();
        fileManager = fileManager == null ? new FileManager() : fileManager;
        fileManager.setOSManager(osManager);

        if (OSUtils.isWindows()) {
            fileManager.setOS(WINDOWS);
        } else if (OSUtils.isMacOS()) {
            fileManager.setOS(MAC);
        } else if (OSUtils.isLinux()) {
            fileManager.setOS(LINUX);
        } else if (OSUtils.isOther()) {
            fileManager.setOS(OTHER);
        } else {
            fileManager.setOS(DEFAULT);
        }
    }


    private static File generateUserData() {
        XMLWriter xmlWriter = new XMLWriter();

        xmlWriter.addXMLElement("test1", "HEYYO!");
        xmlWriter.addXMLElement("test2", "YO YO YO YO");
        xmlWriter.addXMLElement("test3", "qqqqq");

        /*
        TODO generate user data using this method: http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
        */

        return xmlWriter.getXmlFile();
    }

    public static void setCurrentLogFile(File file) {
        currentLogFile = file;
    }

    public static File getCurrentLogFile() {
        return currentLogFile;
    }

}
