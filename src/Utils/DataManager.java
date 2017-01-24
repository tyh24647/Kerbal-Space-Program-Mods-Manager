package Utils;

import java.io.File;

/**
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class DataManager {

    /* Init constants */
    private static final String
            DEFAULT_USER_DATA_FILE_NAME = "UserData.xml",
            DEFAULT_USER_DATA_FILE_PATH = "/Users/tyhostager/IdeaProjects/KSPMM/src/Models/UserData/UserData.xml";

    /* Init global vars */
    private File userDataXML, currentLogFile;
    private boolean canChangeLogFile;


    /**
     * Default constructor
     */
    public DataManager() {
        initDefaults();
    }


    private void initDefaults() {
        canChangeLogFile = true;
        Log.d("Generating user data manager");
        canChangeLogFile = false;
    }



    public boolean hasDefaultUserDataFile() {
        // TODO

        return true;
    }

    public boolean checkForUserData() {
        // TODO

        return true;
    }

    public void setCanChangeLogFile(boolean canChangeLogFile) {
        this.canChangeLogFile = canChangeLogFile;
    }

    public boolean canChangeLogFile() {
        return canChangeLogFile;
    }

    public void setCurrentLogFile(File file) {
        if (canChangeLogFile) {
            currentLogFile = file;
        }
    }

    public File getCurrentLogFile() {
        return currentLogFile;
    }
}
