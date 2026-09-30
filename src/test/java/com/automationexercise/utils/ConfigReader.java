package com.automationexercise.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads settings from config.properties.
 * A value passed on the command line (-Dkey=value) always wins over the file.
 */
public final class ConfigReader {

    private static final Properties PROPERTIES = loadProperties();

    private ConfigReader() {
        // Utility class - no instances needed
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties was not found in src/test/resources");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read config.properties", e);
        }
        return properties;
    }

    public static String get(String key) {
        String fromCommandLine = System.getProperty(key);
        if (fromCommandLine != null && !fromCommandLine.isBlank()) {
            return fromCommandLine;
        }
        String fromFile = PROPERTIES.getProperty(key);
        if (fromFile == null) {
            throw new IllegalStateException("Missing setting '" + key + "' in config.properties");
        }
        return fromFile.trim();
    }

    public static String getBaseUrl() {
        return get("baseUrl");
    }

    public static String getBrowser() {
        return get("browser").toLowerCase();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(get("headless"));
    }

    public static int getTimeoutSeconds() {
        return Integer.parseInt(get("timeoutSeconds"));
    }
}
