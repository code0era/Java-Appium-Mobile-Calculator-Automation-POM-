package com.datman.calculator.utils;

import com.datman.calculator.config.ConfigManager;
import com.datman.calculator.driver.AppiumDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ScreenshotUtils — Screenshot capture utility for test failure documentation.
 *
 * <p>Captures screenshots and saves them with timestamped filenames.
 * Used in TestNG @AfterMethod to auto-capture on test failure.</p>
 *
 * @author DatMan QA Team
 */
public class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtils() { /* Utility class */ }

    /**
     * Capture a screenshot and save to the configured screenshots directory.
     *
     * @param testName Name of the current test (used in filename)
     * @return absolute path to the saved screenshot file, or null if capture failed
     */
    public static String captureScreenshot(String testName) {
        if (!AppiumDriverManager.isDriverInitialized()) {
            logger.warn("Cannot capture screenshot — driver not initialized");
            return null;
        }

        try {
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
            String safeName = testName.replaceAll("[^a-zA-Z0-9_-]", "_");
            String filename = safeName + "_" + timestamp + ".png";

            String screenshotDir = ConfigManager.getInstance().getScreenshotDir();
            File destDir = new File(screenshotDir);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            File destFile = new File(destDir, filename);
            File srcFile = ((TakesScreenshot) AppiumDriverManager.getDriver())
                    .getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, destFile);

            logger.info("📸 Screenshot saved: {}", destFile.getAbsolutePath());
            return destFile.getAbsolutePath();

        } catch (Exception e) {
            logger.error("❌ Failed to capture screenshot for '{}': {}", testName, e.getMessage());
            return null;
        }
    }
}
