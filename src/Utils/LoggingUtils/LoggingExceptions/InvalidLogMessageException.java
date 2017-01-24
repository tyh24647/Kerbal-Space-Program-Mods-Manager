package Utils.LoggingUtils.LoggingExceptions;

import Utils.LoggingUtils.LoggingException;

/**
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class InvalidLogMessageException extends LoggingException {

    private static final String
            DEFAULT_MSG = "Check to make sure it is not null or empty",
            DEFAULT_TITLE = "Invalid Message Exception",
            DEFAULT_HEADER = "> ERROR: Invalid log message specified. "
    ;

    //region GLOBAL VARS
    private String title, message, header;
    //endregion




    //region CONSTRUCTORS
    public InvalidLogMessageException() {
        initDefaults();
    }
    //endregion




    private void initDefaults() {
        setTitle(DEFAULT_TITLE);
    }

    /**
     * Overrides parent title
     *
     * @param title The specified title
     * @return      The updated title
     */
    @Override
    public String setTitle(String title) {
        return this.title = title == null || this.title.isEmpty() ?
                super.getTitle() == null ? super.getDefaultTitle()
                        : super.getTitle() : DEFAULT_TITLE;
    }

    /**
     * Overrides parent header
     *
     * @param header    The specified header
     * @return          The updated header
     */
    @Override
    public String setHeader(String header) {
        return this.header = header == null || this.header.isEmpty() ?
                super.getHeader() == null ? super.getDefaultHeader()
                        : super.getHeader() : DEFAULT_HEADER;
    }

    /**
     * Overrides parent message
     *
     * @param message   The specified message
     * @return          The updated message
     */
    @Override
    public String setMessage(String message) {
        return this.message = message == null || this.message.isEmpty() ?
                super.getMessage() == null ? super.getDefaultMsg()
                        : super.getMessage() : DEFAULT_MSG;
    }

    /**
     * Retrieves the overridden title for the specific error case
     * @return  The overridden title
     */
    @Override
    public String getTitle() {
        return this.title == null || this.title.isEmpty() ?
                super.getTitle() == null ? super.getDefaultTitle() :
                        super.getTitle() : DEFAULT_TITLE;
    }

    /**
     * Retrieves the overridden header string
     * @return  The overridden header
     */
    @Override
    public String getHeader() {
        return this.header == null || this.header.isEmpty() ?
                super.getHeader() == null ? super.getDefaultHeader()
                        : super.getHeader() : DEFAULT_HEADER;
    }

    /**
     * Retrieves the overridden message string
     * @return  The overridden message
     */
    @Override
    public String getMsg() {
        return this.message == null || this.message.isEmpty() ?
                super.getMsg() == null ? super.getDefaultMsg()
                        : super.getMsg() : DEFAULT_MSG;
    }
}
