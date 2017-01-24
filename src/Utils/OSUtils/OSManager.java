package Utils.OSUtils;

import java.util.HashMap;

import static Utils.OSUtils.OSConstants.*;

/**
 * <p>
 *     Manages and provides functionality related to different operating systems, which
 *     is initially specified in the main method of the program upon execution and cannot
 *     be modified at a later time once it is set in the user's preferences
 * </p>
 *
 * @see Main.KSPMMDriver
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class OSManager {

    //region CONSTANTS
    private static final String[] hashKeys = {
            WINDOWS, MAC, LINUX, OTHER
    };

    private static final Boolean DEFAULT_BOOL_VAL = null;
    //endregion

    private HashMap<String, Boolean> operatingSystems;
    private String currentOperatingSystem;

    /**
     * Default constructor
     */
    public OSManager() {
        initOperatingSystemsHashMap();
    }

    /**
     * Assigns the current operating system to the given value, if it is valid
     * @param osName    The name of the user's OS
     */
    public void setCurrentOperatingSystem(String osName) {
        if (osNameSpecificationIsValid(osName)) {
            for (String key : hashKeys) {
                operatingSystems.replace(key, key.equals(osName));  // only one can be true
            }

            currentOperatingSystem = osName;
        }
    }

    /**
     * Retrieves the current operating system name
     * @return  The OS
     */
    public String getCurrentOS() {
        return currentOperatingSystem == null ? DEFAULT : currentOperatingSystem;
    }

    /**
     * Retrieves the HashMap object of the valid operating systems, as well as the
     * Boolean value indicating whether or not it is the OS in use, or if it is null
     *
     * @return  The HashMap of operating systems and the corresponding boolean values
     */
    public HashMap<String, Boolean> getOperatingSystems() {
        if (operatingSystems == null || operatingSystems.isEmpty()
                || operatingSystems.size() == 0) {
            initOperatingSystemsHashMap();
        }

        return operatingSystems;
    }

    /**
     * Ensures that the submitted OS name is a valid option that is compatable with
     * this program.
     *
     * @param osName    The name of the user's OS
     * @return          Whether the name is a valid OS type
     */
    private boolean osNameSpecificationIsValid(String osName) {
        if (operatingSystems.containsKey(osName)) {
            return true;
        } else if (operatingSystems.containsKey(osName.toLowerCase())
                || operatingSystems.containsKey(osName.toUpperCase())) {
            return true;
        }

        return false;
    }

    /**
     * Initializes map object of operating systems using the array of hashkeys
     */
    private void initOperatingSystemsHashMap() {
        if (operatingSystems == null) {
            operatingSystems = new HashMap<>(hashKeys.length);
        }

        for (String key : hashKeys) {
            operatingSystems.put(key, DEFAULT_BOOL_VAL);
        }
    }


}
