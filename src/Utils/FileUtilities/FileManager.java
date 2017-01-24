package Utils.FileUtilities;

import Utils.ApplicatioinUtilities.SystemCommandExecutor;
import Utils.Log;
import Utils.OSUtils.OSManager;
import Utils.OSUtils.OSUtils;

import javax.naming.InvalidNameException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static AppData.Constants.*;
import static Utils.OSUtils.OSConstants.DEFAULT;

/**
 * <p>
 *     File manager which interacts with the files on the individual local system and
 *     the application itself. It also is used to save files in custom locations, and
 *     handle the file system correctly based on the user's operating system.
 * </p>
 * &nbsp
 * <p>
 *     This class contains functions for the following:
 *     <ul>
 *         <li>Generating documents</li>
 *         <li>Editing documents</li>
 *         <li>Reading documents</li>
 *         <li>Verifying documents</li>
 *         <li>Determining user operating system</li>
 *         <li>Setting file defaults</li>
 *         <li>Setting path locations and defaults</li>
 *         <li>Allows user to change/customize save locations</li>
 *         <li>Creating files with any filetype</li>
 *     </ul>
 * </p>
 * &nbsp
 * <p>
 *     This is used throughout the program by various classes for files, however certain
 *     filetypes will be managed better through the language-specific function classes.
 * </p>
 *
 * @see Utils.XMLUtilities.XMLWriter for an example of how it has specific
 *     functionality that would not be implemented in this class
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class FileManager {
    private String currentOS;
    private OSManager osManager;

    /**
     * Default constructor
     */
    public FileManager() {
        verifyAppDataFolder();
        verifyLogsFolder();
        purgeOldLogFilesFromAppData();
    }

    private void verifyLogsFolder() {
        File logsFolder = new File(DEFAULT_LOG_FILE_PATH);
        if (!logsFolder.exists() || !logsFolder.isDirectory()) {
            logsFolder.mkdir();
        }
    }

    private void purgeOldLogFilesFromAppData() {
        File dir = new File(DEFAULT_LOG_FILE_PATH);
        File[] dirListing = dir.listFiles();

        if (dirListing != null && dir.isDirectory()) {

            // generate HashMap to store the names of the current log files
            HashMap<String, String> logFileNames = new HashMap<>();
            for (File file : dirListing) {
                String fileName = file.getName();
                if (!fileName.startsWith(".")) {
                    Log.d("Adding log file with name \'" + fileName + "\' to the HashMap:");
                    logFileNames.put(fileName.substring(0, 10), fileName);
                }
            }

            System.out.println("> " + logFileNames);
            if (logFileNames.size() > DEFAULT_MAX_NUM_LOG_FILES) {
                List<String> fileKeys = new ArrayList<>(logFileNames.size());
                fileKeys.addAll(logFileNames.values());
                fileKeys.sort(Comparator.naturalOrder());

                System.out.println("\n> HashMap values before deletions:");
                logFileNames.keySet().forEach(
                        key -> System.out.println(key + "->" + logFileNames.get(key))
                );


                ArrayList<String> cmds = new ArrayList<>();
                for (int i = fileKeys.size(); i > DEFAULT_MAX_NUM_LOG_FILES && fileKeys.size() > 0; i--) {

                    // print the hashmap value to the debug console
                    System.out.println(
                            ">\tObject at index: \'" + i + "\' --> Key=\""
                            + fileKeys.get(i) + "\" : Value=\""
                            + logFileNames.get(fileKeys.get(i)) + "\""
                    );

                    /*
                    ArrayList<String> cmds = new ArrayList<>();
                    Log.d("Searching for HashMap value for key \'");
                    if (logFileNames.containsKey(fileKeys.get(i))) {
                        String trimmedFileName = logFileNames.get(fileKeys.get(i));
                        Log.d("Removing file at index \'" + i + "\' with key/value pair: \'"
                                + trimmedFileName + "\', with value: \'"
                                + logFileNames.get(fileKeys.get(i)) + "\'");

                        String removeFileCmd;
                        if (OSUtils.isMacOS() || OSUtils.isLinux()) {
                            removeFileCmd = "rm -rf " + fileKeys.get(i);
                        } else {
                            removeFileCmd = "del /f " + fileKeys.get(i);
                        }

                        cmds.add(removeFileCmd);
                        //logFileNames.remove(trimmedFileName);
                    }
                    */
                    String currentFileName = logFileNames.get(fileKeys.get(i));
                    if (new File(DEFAULT_LOG_FILE_PATH + currentFileName).exists()) {
                        if (OSUtils.isMacOS() || OSUtils.isLinux()) {
                            cmds.add("rm -rf " + DEFAULT_LOG_FILE_PATH + currentFileName);
                        } else if (OSUtils.isWindows()) {
                            cmds.add("del /f " + DEFAULT_LOG_FILE_PATH + currentFileName);
                        }
                    }

                    // Execute command to remove the specified files
                    SystemCommandExecutor cmdExecutor = new SystemCommandExecutor(cmds);
                    try {
                        cmdExecutor.executeCommand();
                    } catch (Exception e) {
                        Log.e(e.getMessage());
                        e.printStackTrace();
                    }

                    fileKeys.remove(i);
                }


                System.out.println("> Updated map: values:");
                logFileNames.keySet().forEach(
                        key -> System.out.println(key + "->" + logFileNames.get(key))
                );
            }
        } else {
            if (!dir.exists()) {
                dir.mkdir();
            }

            verifyAppDataFolder();
            verifyLogsFolder();
        }
    }

    /**
     * Creates a new folder at the default AppData path with the specified name
     * @param folderName    The name of the folder to be created
     */
    public void createFolderAtDefaultPath(String folderName) {
        verifyAppDataFolder();
        String newFolderPath = DEFAULT_APPDATA_PATH + folderName;
        File newFolder = new File(newFolderPath);
        if (!newFolder.exists() || !newFolder.isDirectory()) {
            newFolder.mkdir();
        }
    }

    private void verifyAppDataFolder() {
        File appDataFolder = new File(DEFAULT_APPDATA_PATH);
        if (!appDataFolder.exists() || !appDataFolder.isDirectory()) {
            appDataFolder.mkdir();
        }
    }

    /**
     * Generates a new folder at the requested path with the specified folder name
     *
     * @param path          The path in which the folder will be created
     * @param folderName    The specified name of the new folder
     * @return              True if folder was successfully created
     */
    private boolean createFolderAtPath(String path, String folderName) {
        boolean success = false;
        File folderFile = new File(DEFAULT_LOG_FILE_PATH + folderName);
        return folderFile.exists() && folderFile.isDirectory() || folderFile.mkdir();
    }

    /**
     * Returns the appropriate resource URL that corresponds with the specified resource
     * name.
     *
     * @param resourceName              The name of the resource to be found
     * @return                          The resource URL -> String
     * @throws InvalidNameException     Any exception involving an invalid resource name
     * @throws FileNotFoundException    File is not found
     */
    private URL getResource(String resourceName) throws InvalidNameException, FileNotFoundException {

        // check resource name and throw the appropriate exception if invalid
        if (resourceName == null || resourceName.equals(EMPTY)) {
            throw new InvalidNameException(NULL_RESOURCE_NAME);
        } else if (resourceName.isEmpty() || getClass().getResource(resourceName).getFile().isEmpty()) {
            throw new InvalidNameException(EMPTY_RESOURCE_NAME);
        } else if (getClass().getResource(resourceName).getFile() == null) {
            throw new FileNotFoundException(FILE_NOT_FOUND);
        }

        // file was specified correctly
        return getClass().getResource(resourceName);
    }

    /**
     * Retrieves a resource file with the specified file name from the src folder
     *
     * @param fileName      The name of the file to be retrieved
     * @return              The corresponding file to the file name
     * @throws Exception    If the file cannot be found
     */
    private File getResourceFile(String fileName) throws Exception {

        // attempt to retrieve the specified resource
        try {
            getResource(fileName);
        } catch (Exception e) {
            throw e.getMessage() != null ? e: new Exception(INVALID_FILE_SPECIFICATIONS);
        }

        // file does not exist
        return null;
    }

    public void setOSManager(OSManager osManager) {
        if (osManager == null) {
            osManager = new OSManager();
        }

        this.osManager = osManager;
    }

    public OSManager getOSManager() {
        return osManager == null ? new OSManager() : osManager;
    }

    public String getCurrentOS() {
        return currentOS == null ? osManager.getCurrentOS() == null
                ? DEFAULT : osManager.getCurrentOS() : currentOS;
    }

    public void setOS(String userOS) {
        currentOS = userOS == null ? DEFAULT : userOS;
        osManager.setCurrentOperatingSystem(userOS);
    }
}
