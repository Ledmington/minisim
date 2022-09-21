package org.minisim;

/**
 * This class contains only public static final fields useful for logging
 * and environment information.
 * This class cannot be istantiated.
 */
public final class AppConstants {
    private AppConstants() {}

    /**
     * Shorthand field which holds the name of the Operating System MiniSim is running on.
     * NOTE: for Linux-based distributions, this will be just "Linux".
     */
    public static final String OSName = System.getProperty("os.name");

    /**
     * Shorthand field which holds the version of the Operating System MiniSim is running on.
     * NOTE: for Linux-based distributions, this will be the Linux kernel version.
     */
    public static final String OSVersion = System.getProperty("os.version");

    /**
     * Shorthand field which holds the version of Java MiniSim is running on.
     */
    public static final String javaVersion = System.getProperty("java.version");

    /**
     * Shorthand field which holds the version of JVM MiniSim is running on.
     * NOTE: usually this is similar to {@link #javaVersion}.
     */
    public static final String jvmVersion = System.getProperty("java.vm.version");

    /**
     * Shorthand field which holds the version of JavaFX MiniSim is running on.
     */
    public static final String javafxVersion = System.getProperty("javafx.version");
}
