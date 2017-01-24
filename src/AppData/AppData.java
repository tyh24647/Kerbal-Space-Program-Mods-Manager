package AppData;

import Utils.DataUtilities.DateTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Delegate object to handle, store, and modify application data
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class AppData {
    private static final DateTime DATE_TIME = new DateTime();
    public static final String APP_INSTANCE_TIME = DATE_TIME.getDateTime();
    private String userModDirectoryPath;
    private ArrayList<String> modNames;
    private HashMap<Object, Object> dataMap;
    private Object[][] userData;


    public AppData() {
        //
    }

    public static String getAppInstanceTime() {
        return APP_INSTANCE_TIME;
    }

    public void setUserData(Object[][] userData) {
        this.userData = userData;
    }

    public void setDataMap(HashMap<Object, Object> dataMap) {
        this.dataMap = dataMap == null ? new HashMap<>() : dataMap;
    }

    public void setUserModDirectoryPath(String userModDirectoryPath) {
        this.userModDirectoryPath = userModDirectoryPath == null
                ? Constants.DEFAULT_USER_KSP_PATH : userModDirectoryPath;
    }

    public void setModNames(ArrayList<String> modNames) {
        this.modNames = modNames == null ? new ArrayList<>() : modNames;
    }

    public Object[][] getUserData() {
        return userData == null ? new Object[][]{} : userData;
    }

    public ArrayList<String> getModNames() {
        return modNames == null ? new ArrayList<>() : modNames;
    }

    public HashMap<Object, Object> getDataMap() {
        return dataMap;
    }

    public String getUserModDirectoryPath() {
        return userModDirectoryPath;
    }
}

