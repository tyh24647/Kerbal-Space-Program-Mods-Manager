package Utils.ApplicatioinUtilities;

import Utils.Log;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class can be used to execute a system command from a Java application.
 * See the documentation for the public methods of this class for more
 * information.
 *
 * Documentation for this class is available at this URL:
 *
 * http://devdaily.com/java/java-processbuilder-process-system-exec
 *
 *
 * Copyright 2010 alvin j. alexander, devdaily.com.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser Public License for more details.

 * You should have received a copy of the GNU Lesser Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Please ee the following page for the LGPL license:
 * http://www.gnu.org/licenses/lgpl.txt
 *
 * This class was modified by Tyler Hostager on 1/20/16 to add additional functionality
 */
public class SystemCommandExecutor {
    private List<String> commandInformation;
    private String adminPassword;
    private ThreadedStreamHandler inputStreamHandler;
    private ThreadedStreamHandler errorStreamHandler;

    /**
     * Pass in the system command you want to run as a List of Strings, as shown here:
     *
     * List<String> commands = new ArrayList<String>();
     * commands.add("/sbin/ping");
     * commands.add("-c");
     * commands.add("5");
     * commands.add("www.google.com");
     * SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
     * commandExecutor.executeCommand();
     *
     * Note: I've removed the other constructor that was here to support executing
     *       the sudo command. I'll add that back in when I get the sudo command
     *       working to the point where it won't hang when the given password is
     *       wrong.
     *
     * @param commandInformation The command you want to run.
     */
    public SystemCommandExecutor(final List<String> commandInformation) {
        if (commandInformation == null) {
            throw new NullPointerException("The commandInformation is required.");
        }

        this.commandInformation = commandInformation;
        //this.adminPassword = null;
        this.adminPassword = "763671Baseball-";
    }

    /**
     * Executes the given commands that are taken in as a list of Strings
     *
     * @param commandInfo               The list of commands to execute
     * @return                          The exit code result for the command
     * @throws NullPointerException     If command is null and cannot be executed
     */
    public int executeCommand(final List<String> commandInfo) throws NullPointerException {
        this.commandInformation = commandInfo;

        String cmdNote = "Executing Unix command: \"" + commandInfo.toString() + "\"";
        Log.d(cmdNote);

        try {
            return executeCommand();
        } catch (Exception e) {
            System.out.println("> Unable to execute the requested command");
            System.out.println(e.getMessage());
            Log.e(e.getMessage());
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Executes the command given to the command executor object
     *
     * @return                          The exit code for the command execution
     * @throws IOException              Input/output error
     * @throws InterruptedException     System process was interrupted--could not execute command
     */
    public int executeCommand() throws IOException, InterruptedException {
        int exitValue = -99;

        ArrayList<String> cmds = new ArrayList<>();
        cmds.add("/bin/bash");
        cmds.add("-c");
        cmds.addAll(commandInformation);

        try {
            ProcessBuilder pb = new ProcessBuilder(commandInformation);
            pb.command(cmds);
            Map<String, String> env = pb.environment();

            // set environment variable t
            //env.put("t", "test/");

            Process p = pb.start();
            String output = loadStream(p.getInputStream());
            String error = loadStream(p.getErrorStream());
            int rc = p.waitFor();
            if (!output.isEmpty()) {
                System.out.println("Process ended with rc=" + rc);
                System.out.println("Standard Output:");
                System.out.println(output);
            }
            if (!error.isEmpty()) {
                System.out.println("Standard Error:");
                System.out.println(error);
            }

            File dir = new File(System.getProperty("user.dir"));
            if (!dir.exists()) {
                dir.mkdir();
            }

            if (dir.isDirectory()) {
                dir.setExecutable(true);
            }

            pb.command(cmds);
            pb.directory(dir);

            Process process = pb.start();
            IOUtils.copy(process.getInputStream(), System.out);
            process.waitFor();

            // you need this if you're going to write something to the command's input stream
            // (such as when invoking the 'sudo' command, and it prompts you for a password).
            OutputStream stdOutput = process.getOutputStream();

            // i'm currently doing these on a separate line here in case i need to set them to null
            // to get the threads to stop.
            // see http://java.sun.com/j2se/1.5.0/docs/guide/misc/threadPrimitiveDeprecation.html
            InputStream inputStream = process.getInputStream();
            InputStream errorStream = process.getErrorStream();

            // these need to run as java threads to get the standard output and error from the command.
            // the inputstream handler gets a reference to our stdOutput in case we need to write
            // something to it, such as with the sudo command
            inputStreamHandler = new ThreadedStreamHandler(inputStream, stdOutput, adminPassword);
            errorStreamHandler = new ThreadedStreamHandler(errorStream);


            // TODO the inputStreamHandler has a nasty side-effect of hanging if the given password is wrong; fix it
            inputStreamHandler.start();
            errorStreamHandler.start();

            // TODO a better way to do this?
            exitValue = process.waitFor();

            // TODO a better way to do this?
            inputStreamHandler.interrupt();
            errorStreamHandler.interrupt();
            inputStreamHandler.join();
            errorStreamHandler.join();

            Log.d("Success");
        } catch (IOException e) {
            // TODO deal with this here, or just throw it?
            Log.e("Failure");
            Log.e(e.getMessage());
            throw e;
        } catch (InterruptedException e) {
            // generated by process.waitFor() call
            // TODO deal with this here, or just throw it?
            Log.e("Failure");
            Log.e(e.getMessage());
            throw e;
        } finally {
            return exitValue;
        }
    }

    private static String loadStream(InputStream s) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(s));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null)
            sb.append(line).append("\n");
        return sb.toString();
    }

    /**
     * Get the standard output (stdout) from the command you just exec'd.
     */
    public StringBuilder getStandardOutputFromCommand() {
        return inputStreamHandler.getOutputBuffer();
    }

    /**
     * Get the standard error (stderr) from the command you just exec'd.
     */
    public StringBuilder getStandardErrorFromCommand() {
        return errorStreamHandler.getOutputBuffer();
    }
}
