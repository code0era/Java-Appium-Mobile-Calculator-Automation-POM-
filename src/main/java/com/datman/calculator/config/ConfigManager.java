package com.datman.calculator.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigManager — Thread-safe Singleton configuration loader.
 *
 * <p>Reads from src/test/resources/config.properties.
 * Provides typed accessors for all configuration values.</p>
 *
 * @author DatMan QA Team
 */
public class ConfigManager {

    private static final Logger logger = LogManager.getLogger(ConfigManager.class);
    private static volatile ConfigManager instance;
    private final Properties props = new Properties();
    private static final String CONFIG_FILE = "/config.properties";

    private ConfigManager() {
        loadProperties();
    }

    /**
     * Double-checked locking for thread-safe singleton initialization.
     */
    public static ConfigManager getInstance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream is = getClass().getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new RuntimeException("Could not find config.properties in classpath");
            }
            props.load(is);
            logger.info("✅ Configuration loaded from {}", CONFIG_FILE);
        } catch (IOException e) {
            logger.error("❌ Failed to load config: {}", e.getMessage());
            throw new RuntimeException("Failed to load configuration", e);
        }
    }

    // ─── Accessors ─────────────────────────────────────────────

    public String getPlatform() {
        return getRequired("platform");
    }

    public String getAppiumServerUrl() {
        return getOrDefault("appium.server.url", "http://127.0.0.1:4723");
    }

    public String getDeviceName() {
        return getRequired("device.name");
    }

    public String getPlatformVersion() {
        return getRequired("platform.version");
    }

    public String getAppPackage() {
        return getOrDefault("app.package", "com.google.android.calculator");
    }

    public String getAppActivity() {
        return getOrDefault("app.activity", "com.google.android.calculator.Calculator");
    }

    public String getBundleId() {
        return getOrDefault("bundle.id", "com.apple.mobileslideshow");
    }

    public int getImplicitWaitSeconds() {
        return Integer.parseInt(getOrDefault("implicit.wait.seconds", "10"));
    }

    public int getExplicitWaitSeconds() {
        return Integer.parseInt(getOrDefault("explicit.wait.seconds", "30"));
    }

    public String getScreenshotDir() {
        return getOrDefault("screenshot.dir", "screenshots");
    }

    public String getExtentReportPath() {
        return getOrDefault("extent.report.path", "extent-reports/calculator-test-report.html");
    }

    // ─── Helpers ──────────────────────────────────────────────

    private String getRequired(String key) {
        String val = props.getProperty(key);
        if (val == null || val.trim().isEmpty()) {
            throw new RuntimeException("Required config property missing: " + key);
        }
        return val.trim();
    }

    private String getOrDefault(String key, String defaultValue) {
        return props.getProperty(key, defaultValue).trim();
    }
}
