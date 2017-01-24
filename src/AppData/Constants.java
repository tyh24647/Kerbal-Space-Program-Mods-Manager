package AppData;

import org.apache.commons.io.output.TeeOutputStream;
import org.jetbrains.annotations.Contract;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.TimeZone;

/**
 * Object that is instantiated at runtime, and the values do not change. This allows for
 * these same values to be accessed anywhere in the program because it is a singleton.
 *
 * @author Tyler Hostager
 * @version 1/20/17
 */
public final class Constants {
    //region Constants that depend on internal methods which can not be accessed or modified elsewhere
    public static final String DEFAULT_APPDATA_FOLDER_TITLE = "UserData/Preferences";
    public static final String DEFAULT_USER_KSP_PATH = "";     // TODO actually set this!!
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    public static final String APP_INIT_DATETIME = generateDateTime();
    public static final Date DATE = getInstanceDate();
    public static final String DATE_TIME = getAppInitDatetime();
    public static final File INSTANCE_LOG_FILE = generateLogFile();
    public static final Color DEFAULT_SELECTION_COLOR = new Color(200, 250, 250);

    // init JComponent dimension constants
    public static final Dimension
            DEFAULT_WINDOW_SIZE = new Dimension(1000, 600),
            DEFAULT_CENTER_PANEL_SIZE = new Dimension(800, 480),
            TOP_PANE_PREFERRED_SIZE = new Dimension(800, 50),
            BOTTOM_PANE_PREFERRED_SIZE = new Dimension(800, 40),
            SPLIT_PANE_LEFT_MIN_SIZE = new Dimension(300, 480),
            SPLIT_PANE_LEFT_MAX_SIZE = new Dimension(600, 480)
    ;

    //region Constant integers
    public static final int
            DEFAULT_MAX_NUM_LOG_FILES = 20,
            //DEFAULT_ENABLED_COLUMN_WIDTH = 60,
            DEFAULT_ENABLED_COLUMN_WIDTH = 50,
            DEFAULT_STRING_COLUMN_WIDTH = 300,
            DEFAULT_DATE_COLUMN_WIDTH = 200,
            DEFAULT_ROW_HEIGHT = 20
    ;
    //endregion

    // creates a singleton single instants of the class by using a private constructor - prevents other creations
    private static Constants constants = new Constants();
    //endregion

    //region Logging-related constants
    public static final String
            DEFAULT_APP_DATA_PATH = "src/AppData/",
            DEFAULT_LOG_FILE_PATH = "src/AppData/Logs/",
            DEFAULT_LOG_FILE_EXTENSION = ".log",
            DEFAULT_DATE_FORMAT_STR = "yyyy-MM-dd-HH-mm-ss",
            DEFAULT_DEBUG_HEADER = "> ",
            DEFAULT_ERROR_HEADER = "ERROR: ",
            INVALID_DATE_FORMAT_HEADER = "Invalid date format: ",
            NULL_DATE_EXCEPTION = "The generated date is null",
            EMPTY_DATE_EXCEPTION = "The generated date is empty",
            INVALID_MSG_HEADER = "Invalid log message: ",
            INVALID_MSG_EXCEPTION = INVALID_MSG_HEADER + "Message is empty",
            NULL_MSG_ERROR = INVALID_MSG_HEADER + "Message is null",
            NEW_LINE = "\n", EMPTY = "",
            DEFAULT_APPDATA_PATH = "src/AppData",
            EMPTY_RESOURCE_NAME = "Unable to locate resource - the specified resource name is empty",
            NULL_RESOURCE_NAME = "Unable to locate resource - the specified resource name is null",
            FILE_NOT_FOUND = "File not found. Please ensure the proper name is being used",
            INVALID_FILE_SPECIFICATIONS = "Unable to locate file - invalid file specifications";
    //endregion

    public static final String
            FILE = "File",
            EDIT = "Edit",
            VIEW = "View",
            TOOLS = "Tools",
            WINDOW = "Window",
            HELP = "Help",
            OPEN = "Open KSP Folder",
            NEW = "New",
            IMPORT = "Import Mod...",
            SAVE = "Save",
            SAVE_AS = "Save as...",
            PREFERENCES = "Preferences",
            EXIT = "Exit",
            UNDO = "Undo",
            REDO = "Redo",
            CUT = "Cut",
            COPY = "Copy",
            PASTE = "Paste",
            FIND = "Find...",
            SHOW_DEBUG_CONSOLE = "Debug Console",
            SHOW_ERROR_MSGS = "Error messages",
            SHOW_APP_LOGS = "Application logs",
            ENABLE_DEBUG = "Enable debug mode",
            DISABLE_DEBUG = "Disable debug mode",
            SHOW_CONSOLE = "Show debug console",
            FULLSCREEN = "Enable Fullscreen (F11)",
            EXIT_FSCREEN = "Disable fullscreen (F11)",
            TABLE_COLUMNS = "Table columns preferences",
            ABOUT = "About",
            CONTACT = "Contact us",
            WINDOW_TITLE = "Kerbal Space Program Mods Manager",
            COPYWRITE_MSG = "© Tyler Hostager, 2016"
    ;

    //region Default test data for the JTable
    public static final String[] COLUMN_NAMES = {
            "Enabled", "Mod Name", "Installation Directory", "Date Added"
    };

    // TODO remove this eventually and create data based on mods directory contents
    ///*
    public static final Object[][] TEST_DATA = {
            {Boolean.TRUE, "c2", "c3", "c4"},
            {Boolean.TRUE, "AFGGc4", "q346q", "asdf"},
            {Boolean.TRUE, "c6", "3433333", "tmp"}
    };
    //*/
    //endregion

    public static boolean IS_FIRST_RUN = getIsFirstRun();
    public static boolean canEditFirstRun = true;

    private Constants() {
        initDebugWriteToFile();
    }

    static {
        logFile();
        dateTime();
        date();
        getIsFirstRun();
    }

    public static Constants getInstance() {
        return constants;
    }

    private static String generateDateTime() {
        DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
        return DATE_FORMAT.format(new Date());
    }

    private static File generateLogFile() {
        File returnFile = new File("src/AppData/Logs/" + generateLogFileName());
        try {
            returnFile.createNewFile();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return returnFile;
    }

    public static String getAppInitDatetime() {
        //return getInstance().APP_INIT_DATETIME;     // always access the datetime from this instance
        return APP_INIT_DATETIME;
    }

    private static Date getInstanceDate() {
        //return getInstance().DATE;
        //return DATE;
        return date();
    }

    @Contract (" -> !null")
    private static File logFile() {
        //return getInstance().INSTANCE_LOG_FILE;
        return INSTANCE_LOG_FILE;
    }

    @Contract (" -> !null")
    private static Date date() {
        return new Date();
    }

    private static String dateTime() {
        //return getInstance().DATE_TIME;
        return DATE_TIME;
    }

    private static String generateLogFileName() {
        return generateDateTime() + new String(
                Base64.getEncoder().encode(
                        (generateDateTime() + ".log").getBytes()
                )
        ) + ".log";
    }

    public static void setIsFirstRun(boolean isFirstRun) {
        if (canEditFirstRun) {
            IS_FIRST_RUN = isFirstRun;
        }

        canEditFirstRun = false;
    }

    public static boolean getIsFirstRun() {
        return IS_FIRST_RUN;
    }

    public static void initDebugWriteToFile() {
        try {
            FileInputStream inputStream = new FileInputStream(INSTANCE_LOG_FILE);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            BufferedReader errReader = new BufferedReader(new InputStreamReader(inputStream));
            FileOutputStream fileOutputStream = new FileOutputStream(INSTANCE_LOG_FILE, true);
            FileOutputStream errOutputStream = new FileOutputStream(INSTANCE_LOG_FILE, true);
            TeeOutputStream teeOutputStream = new TeeOutputStream(fileOutputStream, System.out);
            TeeOutputStream teeErrOutputStream = new TeeOutputStream(errOutputStream, System.err);
            PrintStream ps = new PrintStream(teeOutputStream);
            PrintStream errPS = new PrintStream(teeErrOutputStream);
            System.setOut(ps);
            System.setErr(errPS);
            String lStr;

            // append the file's current line text to the file
            while ((lStr = reader.readLine()) != null) {
                System.out.println(lStr);
                ps.append(lStr);
            }

            while ((lStr = errReader.readLine()) != null) {
                System.err.println(lStr);
                errPS.append(lStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}