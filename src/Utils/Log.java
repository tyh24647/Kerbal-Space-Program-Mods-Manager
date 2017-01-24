package Utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.TimeZone;

import static AppData.Constants.*;

/**
 * Debug logger to write and record debug and error messages, and saves
 * them to a log file which can be accessed if the application is being
 * run in debug mode.
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class Log {


    //region CONSTRUCTORS
    /**
     * Default constructor
     */
    public Log() {
        initDefaultLogResources();
    }
    //endregion


    //region FUNCTIONS
    /**
     * Logs the specified message to the console and current log file
     * @param debugMsg  The debug message
     */
    public static void d(String debugMsg) {
        if (debugMsg == null || debugMsg.isEmpty()) {
            return;
        }

        // create log message with the correct header
        String logMsg = DEFAULT_DEBUG_HEADER + debugMsg;

        // print msg to console
        System.out.println(logMsg);

        // write msg to log
        writeToLog(logMsg, false);
    }

    /**
     * Logs an error message with the specified string
     * @param errMsg    The error message
     */
    public static void e(String errMsg) {
        if (errMsg == null || errMsg.isEmpty()) {
            return;
        }

        String logMsg = DEFAULT_ERROR_HEADER + errMsg;

        // print error in console
        System.err.println(logMsg);

        // write error to log
        writeToLog(errMsg, true);
    }

    /**
     * Writes the specified message to the log file, using the corresponding
     * methods depending on whether or not it is an error
     *
     * @param msg           The message to be written
     * @param isErrorMsg    Whether or not the message is an error
     */
    private static void writeToLog(String msg, boolean isErrorMsg) {
        try {
            if (isErrorMsg) {
                writeErrToLogFile(msg);
            } else {
                writeMsgToLogFile(msg);
            }
        } catch (Exception e) {
            handleErrFromTryCatch(e);
        }
    }

    /**
     * Handles the error from a try catch block properly
     * @param e The exception that triggered the error
     */
    private static void handleErrFromTryCatch(Exception e) {
        String msg = e.getMessage();
        System.err.println(msg);
        e.printStackTrace();
        writeErrToLogFile(msg);
    }

    /**
     * Initializes the resource folder to contain a log file, as well as
     * a log file for the current instance using the current date and time
     */
    private boolean initDefaultLogResources() {
        File logFileFolder = new File(DEFAULT_APP_DATA_PATH);

        //return logFileFolder.exists() || logFileFolder.mkdir();
        if (!logFileFolder.exists()) {
            return logFileFolder.mkdir();
        }

        return logFileFolder.exists();
    }

    /**
     * Writes the specified debug log message to the default log file
     * for the current instanace of the application
     *
     * @param logMsg    The message to be logged
     * @return          If the write was successful
     */
    private static void writeMsgToLogFile(String logMsg) throws Exception {

        /*

        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO
        TODO
        TODO     MASSIVE! MOVE LOG FILE NAME TO ANOTHER CLASS SO IT CAN BE APPENDED
        TODO        or change the method the writer uses
        TODO
        TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO TODO

        See: http://stackoverflow.com/questions/30713287/java-filewriter-only-writing-last-line-to-file

         */


        // write message using TxtWriter object
        writeMsgToLogFile(generateLogFileName(), logMsg);
    }

    /**
     * Writes the debug message to the corresponding log file according
     * to the generated <code>DateTime</code> object, using the precision of
     * the number of milliseconds since January 1, 1970.
     *
     * @param logFileName   The name of the log file
     * @param msg           The message to be written
     */
    private static void writeMsgToLogFile(String logFileName, String msg) {

        // validate the log message
        msg = msg == null ? NULL_MSG_ERROR : msg;

        // validate the log file name
        logFileName = logFileName == null || logFileName.isEmpty()
                ? generateLogFileName() : logFileName;

        // create the log file
        File logFile = INSTANCE_LOG_FILE;
        String writeStr = NEW_LINE + msg;

        // verify the log file exists, and if not, create it    <-- should be in Constants
        try {
            boolean sessionLogFileExists = false;
            File dir = new File(DEFAULT_LOG_FILE_PATH);
            File[] dirLst = dir.listFiles();
            if (dirLst != null && dirLst.length > 0) {
                ArrayList<String> fileNames = new ArrayList<>();
                for (File f : dirLst) {
                    String fName = f.getName();
                    if (fName.charAt(0) != '.') {      // ignores hidden files such as '.DS_Store'
                        fileNames.add(f.getName());
                    }
                }

                for (String fileName : fileNames) {
                    if (fileName.contains(logFileName.substring(0, 10))) {
                        sessionLogFileExists = true;
                        break;
                    }
                }
            }

            BufferedWriter bufferedWriter;
            FileWriter fileWriter;

            if (!sessionLogFileExists) {
                fileWriter = new FileWriter(logFile.getAbsolutePath());
                bufferedWriter = new BufferedWriter(fileWriter);
                logFile.createNewFile();
                bufferedWriter.write(writeStr);
                bufferedWriter.newLine();
            } else {
                // Debug messages will output to the generated log file and the console
            }
        } catch (Exception err) {
            e(err.getMessage());
        }
    }

    /**
     * Writes the specified error message to the log file
     * @param errMsg    The error message
     */
    private static void writeErrToLogFile(String errMsg) {
        errMsg = DEFAULT_ERROR_HEADER + errMsg;
        writeMsgToLogFile(generateLogFileName(), errMsg);
    }

    /**
     * Retrieves the formatted date string using the current UTC time and the
     * specified date format
     *
     * @return                          The formatted date string
     * @throws NullPointerException     If the string cannot be generated
     */
    private static String getFormattedDateStr() throws NullPointerException {
        // set date format
        SimpleDateFormat dateFormat = DATE_FORMAT;

        // set time zone
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(DATE);
    }

    /**
     * Generates the date format
     * @return  The SimpleDateFormat object
     */
    private static SimpleDateFormat generateDateFormat() {
        return new SimpleDateFormat(DEFAULT_DATE_FORMAT_STR);
    }

    /**
     * Generates the default log file name
     * @return  The generated name
     */
    private static String generateLogFileName() {
        String fileName = getFormattedDateStr() + DEFAULT_LOG_FILE_EXTENSION;

        return getFormattedDateStr() + new String(
                Base64.getEncoder().encode(fileName.getBytes())
        ) + DEFAULT_LOG_FILE_EXTENSION;
    }
    //endregion
}
