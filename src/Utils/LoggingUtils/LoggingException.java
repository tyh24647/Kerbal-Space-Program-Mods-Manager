package Utils.LoggingUtils;

/**
 * Logging exception class which provides specific details for errors caused by
 * logging information.
 *
 * This class is a superclass that has children which have specific messages and
 * attributes which apply only to them.
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class LoggingException extends Exception {

    //region CONSTANTS
    private static final String
            DEFAULT_MSG = "An unknown logging exception occurred",
            DEFAULT_TITLE = "Default Logging Exception",
            DEFAULT_HEADER = "> ERROR: Unable to generate log message. "
    ;
    //endregion


    //region GLOBAL VARS
    private String message, title, header;
    //endregion


    //region CONSTRUCTORS
    /**
     * Default constructor
     */
    public LoggingException() {
        setMessage(DEFAULT_MSG);
        setTitle(DEFAULT_TITLE);
        setHeader(DEFAULT_HEADER);
    }

    /**
     * Constructs a logging exception with the specified message
     * @param msg   The exception message
     */
    public LoggingException(String msg) {
        setMessage(msg == null || msg.isEmpty() ? DEFAULT_MSG : msg);
        setTitle(DEFAULT_TITLE);
        setHeader(DEFAULT_HEADER);
    }

    /**
     * Constructs a logging exception with the specified title and message
     *
     * @param titleStr  The title attribute of the exception
     * @param msg       The message of the exception
     */
    public LoggingException(String titleStr, String msg) {
        setMessage(msg == null || msg.isEmpty() ? DEFAULT_MSG : msg);
        setTitle(titleStr == null || titleStr.isEmpty() ? DEFAULT_TITLE : titleStr);
        setHeader(DEFAULT_HEADER);
    }

    /**
     * Constructs a logging exception with the specified title, message, and header
     *
     * @param headerStr The header attribute of the exception
     * @param titleStr  The title attribute of the exception
     * @param msg       The exception message
     */
    public LoggingException(String headerStr, String titleStr, String msg) {
        setMessage(msg == null || msg.isEmpty() ? DEFAULT_MSG : msg);
        setTitle(titleStr == null || titleStr.isEmpty() ? DEFAULT_TITLE : titleStr);
        setHeader(headerStr == null || headerStr.isEmpty() ? DEFAULT_HEADER : headerStr);
    }
    //endregion


    //region SETTERS
    /**
     * Assigns the title to the specified string
     * @param tStr The title to be set
     */
    public String setTitle(String tStr) {
        return title = tStr == null || tStr.isEmpty() ? DEFAULT_TITLE : tStr;
    }

    /**
     * Assigns the exception's message to the specified string
     * @param msg   The specific error message
     */
    public String setMessage(String msg) {
        return message = msg == null || msg.isEmpty() ? DEFAULT_MSG : msg;
    }

    /**
     *
     * @return
     */
    public String setHeader(String hStr) {
        return header = hStr == null || hStr.isEmpty() ? DEFAULT_HEADER : hStr;
    }
    //endregion


    //region GETTERS
    /**
     * Retrieves the specific error message
     * @return  The appropriate error message
     */
    public String getMsg() {
        return message == null || message.isEmpty() ? DEFAULT_MSG : message;
    }

    /**
     * Retrieves the title for the specific error
     * @return  The title string
     */
    public String getTitle() {
        return title == null || title.isEmpty() ? DEFAULT_TITLE : title;
    }

    /**
     * Retrieves the header for the specific error
     * @return  The header string
     */
    public String getHeader() {
        return header == null || header.isEmpty() ? DEFAULT_HEADER : header;
    }

    /**
     * Retrieves the default message string
     * @return  The default message
     */
    public String getDefaultMsg() {
        return DEFAULT_MSG;
    }

    /**
     * Retrieves the default header string
     * @return  The default header
     */
    public String getDefaultHeader() {
        return DEFAULT_HEADER;
    }

    /**
     * Retrieves the default title
     * @return  The title
     */
    public String getDefaultTitle() {
        return DEFAULT_TITLE;
    }
    //endregion
}
