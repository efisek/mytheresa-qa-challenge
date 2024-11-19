package config;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {
    private static Properties properties;

    static {
        try (FileInputStream inputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
        String env = getProperty("env"); // to get the environment
        return switch (env != null ? env.toLowerCase() : "production") {
            case "local" -> getProperty("localUrl");
            case "staging" -> getProperty("stagingUrl");
            default -> getProperty("baseUrl");
        };
    }
}
