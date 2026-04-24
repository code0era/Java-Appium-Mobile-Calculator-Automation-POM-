package com.datman.calculator.driver;

import com.datman.calculator.config.ConfigManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URI;
import java.time.Duration;

/**
 * AppiumDriverManager — Thread-safe Singleton driver management.
 *
 * <p>Manages Android (UiAutomator2) and iOS (XCUITest) driver lifecycle.
 * Uses ThreadLocal to support parallel test execution safely.</p>
 *
 * <p>Design: Singleton per thread — each thread gets its own driver instance.
 * Configuration is read from config.properties via ConfigManager.</p>
 *
 * @author DatMan QA Team
 * @version 1.0
 */
public class AppiumDriverManager {

    private static final Logger logger = LogManager.getLogger(AppiumDriverManager.class);
    private static final ThreadLocal<AppiumDriver> driverThreadLocal = new ThreadLocal<>();

    private AppiumDriverManager() {
        // Singleton — prevent external instantiation
    }

    /**
     * Initialize and return the Appium driver.
     * Thread-safe — creates a new driver per thread.
     *
     * @return AppiumDriver instance
     */
    public static AppiumDriver initDriver() {
        if (driverThreadLocal.get() != null) {
            logger.warn("Driver already initialized for this thread. Returning existing instance.");
            return driverThreadLocal.get();
        }

        ConfigManager config = ConfigManager.getInstance();
        String platform = config.getPlatform();
        AppiumDriver driver;

        try {
            URI appiumServerUri = new URI(config.getAppiumServerUrl());

            if ("android".equalsIgnoreCase(platform)) {
                driver = createAndroidDriver(appiumServerUri, config);
            } else if ("ios".equalsIgnoreCase(platform)) {
                driver = createIOSDriver(appiumServerUri, config);
            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platform + ". Use 'android' or 'ios'.");
            }

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWaitSeconds()));
            driverThreadLocal.set(driver);
            logger.info("✅ Appium driver initialized for platform: {} | Session ID: {}", platform, driver.getSessionId());

        } catch (Exception e) {
            logger.error("❌ Failed to initialize Appium driver: {}", e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }

        return driverThreadLocal.get();
    }

    /**
     * Create Android driver with UiAutomator2 options.
     */
    private static AppiumDriver createAndroidDriver(URI serverUri, ConfigManager config) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setDeviceName(config.getDeviceName())
                .setPlatformVersion(config.getPlatformVersion())
                .setAppPackage(config.getAppPackage())
                .setAppActivity(config.getAppActivity())
                .setNoReset(false)
                .setFullReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(300));

        logger.info("Creating AndroidDriver | Device: {} | Package: {}", config.getDeviceName(), config.getAppPackage());
        return new AndroidDriver(serverUri.toURL(), options);
    }

    /**
     * Create iOS driver with XCUITest options.
     */
    private static AppiumDriver createIOSDriver(URI serverUri, ConfigManager config) throws Exception {
        XCUITestOptions options = new XCUITestOptions()
                .setDeviceName(config.getDeviceName())
                .setPlatformVersion(config.getPlatformVersion())
                .setBundleId(config.getBundleId())
                .setNoReset(false)
                .setNewCommandTimeout(Duration.ofSeconds(300));

        logger.info("Creating IOSDriver | Device: {} | BundleId: {}", config.getDeviceName(), config.getBundleId());
        return new IOSDriver(serverUri.toURL(), options);
    }

    /**
     * Get the current thread's driver instance.
     *
     * @return AppiumDriver for current thread
     * @throws IllegalStateException if driver has not been initialized
     */
    public static AppiumDriver getDriver() {
        AppiumDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized. Call AppiumDriverManager.initDriver() first.");
        }
        return driver;
    }

    /**
     * Quit and remove the driver for the current thread.
     * Should be called in @AfterMethod or @AfterClass.
     */
    public static void quitDriver() {
        AppiumDriver driver = driverThreadLocal.get();
        if (driver != null) {
            try {
                driver.quit();
                logger.info("🔴 Appium driver quit successfully.");
            } catch (Exception e) {
                logger.error("Error quitting driver: {}", e.getMessage());
            } finally {
                driverThreadLocal.remove();
            }
        }
    }

    /**
     * Check if driver is initialized for the current thread.
     */
    public static boolean isDriverInitialized() {
        return driverThreadLocal.get() != null;
    }
}
