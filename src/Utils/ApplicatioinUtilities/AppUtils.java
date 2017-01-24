package Utils.ApplicatioinUtilities;

import Utils.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Utilities for managing the application itself, such as
 * running commands in the background, closing the
 * program, etc.
 *
 * @author Tyler Hostager
 * @version 1/20/17
 */
public class AppUtils {
    private static final char SEMICOLON = ';';
    private static final String EMPTY = "";

    /**
     * Closes the current instance of the program by killing
     * the application process in the main thread
     */
    public static void exitApplication() {
        dumpDebugStack();
        System.exit(0);
    }

    /**
     * Allows for the user to execute a command directly with
     * Unix to perform tasks that interact with the physical
     * machine instead of the JVM
     *
     * @param command   The command for Unix to execute
     */
    public static void executeUnixCmd(String command) {
        try {
            ArrayList<String> cmds = new ArrayList<>();
            SystemCommandExecutor commandExecutor = new SystemCommandExecutor(initCmdLstFromCmd(command, cmds));
            commandExecutor.executeCommand();
        } catch (Exception e) {
            System.out.println("> ERROR: Invalid command syntax - unable to execute: " + e.getMessage());
            Log.e(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Takes the list object taken in as well as the command, in order to return
     * the command list which is generated recursively.
     *
     * @param cmdStr        The command string
     * @param cLst          The current list of commands
     * @return              the resulting list of commands
     */
    private static ArrayList<String> initCmdLstFromCmd(String cmdStr, ArrayList<String> cLst) {

        // base case for recursion to return the full list of commands
        if (cmdStr == null || cmdStr.isEmpty() && cLst.size() > 0) {
            return cLst;
        }

        cLst = cLst == null ? new ArrayList<>() : cLst;
        StringBuilder sb = new StringBuilder();
        for (char ch : cmdStr.toCharArray()) {
            sb.append(ch);
        }

        cLst.add(sb.toString());
        if (cmdStr.contains(sb.toString())) {
            cmdStr = cmdStr.replace(sb.toString(), "");
        }

        return initCmdLstFromCmd(cmdStr, cLst);
    }

    /**
     * Allows for the user to execute a command directly with
     * Windows to perform tasks that interact with the physical
     * machine instead of the JVM
     *
     * @param command   The command for Windows to execute
     */
    public static void executeWinCmd(String command) {

    }

    private static void dumpDebugStack() {
        File f = new File("threads.dmp");
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                Log.e(e.getMessage());
                e.printStackTrace();
            }
        }

        f.setWritable(true);

        // command to create the threads dump file
        String unixCmd = "a=$(jps -mv | grep 'KSPMMDriver'); IFS=' '; set $a; jstack -l $1 > threads.dmp";
        executeUnixCmd(unixCmd);
    }
}

