package com.borchowiec;

import java.io.File;
import java.net.URISyntaxException;

public class Properties {
    public static final String PROJECT_DIR = getProjectDir();
    public static final String INIT_STRUCTURE_DIR_NAME = "init-structure";
    public static final String SOURCE_DIR = PROJECT_DIR + "/src";
    public static final String BUILD_DIR = PROJECT_DIR + "/build";

    private static String getProjectDir() {
        try {
            File jarFile = new File(Properties.class.getProtectionDomain().getCodeSource().getLocation()
                                                 .toURI());
            return jarFile.getParentFile().getPath();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Cannot get project dir, due to: " + e.getMessage(), e);
        }
    }
}
