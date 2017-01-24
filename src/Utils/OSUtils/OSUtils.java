package Utils.OSUtils;

import java.util.Locale;

/**
 * <p>
 *     Function object which is used to determine which operating system
 *     environment the user is running the app in.
 *     &nbsp
 *     This is used to determine where the KSP folder is located, as it is different
 *     in Windows, Mac, and Linux, which shows the application where to save the mods.
 * </p>
 *
 * @author Tyler Hostager
 * @version 1/18/17
 */
public final class OSUtils {
    private static Boolean isWindows, isMacOS, isLinux, isOther;

    /**
     * Types of compatable operating systems
     */
    public enum OSType {
        Windows,
        MacOS,
        Linux,
        Other
    }

    protected static OSType detectedOS;

    /**
     * detect the operating system from the os.name System property and cache
     * the result
     *
     * @returns - the operating system detected
     */
    public static OSType getOperatingSystemType() {
        if (detectedOS == null) {

            // grab operating system string type
            String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

            if (OS.contains("mac") || OS.contains("darwin")) {
                setMacOSDetected();
                detectedOS = OSType.MacOS;
            } else if (OS.contains("win")) {
                setWindowsDetected();
                detectedOS = OSType.Windows;
            } else if (OS.contains("nux")) {
                setLinuxDetected();
                detectedOS = OSType.Linux;
            } else {
                setOtherDetected();
                detectedOS = OSType.Other;
            }
        }

        return detectedOS;
    }

    private static void setMacOSDetected() {
        isWindows = isLinux = isOther = false;
        isMacOS = true;
    }

    private static void setWindowsDetected() {
        isLinux = isOther = isMacOS = false;
        isWindows = true;
    }

    private static void  setLinuxDetected() {
        isWindows = isMacOS = isOther = false;
        isLinux = true;
    }

    private static void setOtherDetected() {
        isWindows = isMacOS = isLinux = false;
        isOther = true;
    }

    public static boolean isWindows() {
        if (isWindows == null) {
            getOperatingSystemType();
        }

        return isWindows;
    }

    public static boolean isMacOS() {
        if (isMacOS == null) {
            getOperatingSystemType();
        }

        return isMacOS;
    }

    public static boolean isLinux() {
        if (isLinux == null) {
            getOperatingSystemType();
        }

        return isLinux;
    }

    public static boolean isOther() {
        if (isOther == null) {
            getOperatingSystemType();
        }

        return isOther;
    }
}
