package dev.ssjvirtually;

/**
 * Launcher class to bypass JavaFX module path issues in shaded JARs.
 */
public class Launcher {
    public static void main(String[] args) {
        Main.main(args);
    }
}
