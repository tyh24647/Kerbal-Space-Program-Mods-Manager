package Utils.TxtUtilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;

/**
 * @author Tyler Hostager
 * @version 1/18/17
 */
public class TxtWriter extends BufferedWriter {

    public TxtWriter(Writer out) {
        super(out);
    }

    public static void writeToFile(File file, String msg) {

    }
}
