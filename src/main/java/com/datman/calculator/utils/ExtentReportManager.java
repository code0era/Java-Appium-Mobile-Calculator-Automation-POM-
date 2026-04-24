package com.datman.calculator.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.datman.calculator.config.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ExtentReportManager — Manages ExtentReports lifecycle and thread-local test logging.
 *
 * <p>Initializes the ExtentSparkReporter (HTML) with dark theme.
 * Uses ThreadLocal to support parallel test execution.</p>
 *
 * <p>Call order:
 * <ol>
 *   <li>ExtentReportManager.initReports() — once in @BeforeSuite</li>
 *   <li>ExtentReportManager.createTest(name) — @BeforeMethod</li>
 *   <li>ExtentReportManager.getTest() — in tests</li>
 *   <li>ExtentReportManager.flushReports() — @AfterSuite</li>
 * </ol>
 * </p>
 *
 * @author DatMan QA Team
 */
public class ExtentReportManager {

    private static final Logger logger = LogManager.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    private ExtentReportManager() { /* Utility class */ }

    /**
     * Initialize ExtentReports with HTML Spark Reporter.
     * Must be called once before any tests run.
     */
    public static synchronized void initReports() {
        if (extent != null) return; // Already initialized

        ConfigManager config = ConfigManager.getInstance();
        String reportPath = config.getExtentReportPath();

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("DatMan Calculator Automation Report");
        sparkReporter.config().setReportName("Appium Calculator Test Suite — POM Framework");
        sparkReporter.config().setEncoding("UTF-8");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Framework", "Appium + TestNG + POM");
        extent.setSystemInfo("Platform", config.getPlatform().toUpperCase());
        extent.setSystemInfo("Device", config.getDeviceName());
        extent.setSystemInfo("OS Version", config.getPlatformVersion());
        extent.setSystemInfo("Author", "DatMan QA Team");
        extent.setSystemInfo("App", config.getAppPackage());

        logger.info("✅ ExtentReports initialized at: {}", reportPath);
    }

    /**
     * Create a new test entry in the report for the current thread.
     *
     * @param testName the test name to display in the report
     */
    public static ExtentTest createTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        testThreadLocal.set(test);
        return test;
    }

    /**
     * Get the current thread's ExtentTest instance.
     */
    public static ExtentTest getTest() {
        ExtentTest test = testThreadLocal.get();
        if (test == null) {
            throw new IllegalStateException("ExtentTest not created for this thread. Call createTest() first.");
        }
        return test;
    }

    /**
     * Flush (write) the report to disk.
     * Must be called after all tests complete.
     */
    public static synchronized void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("📊 ExtentReports flushed successfully.");
        }
    }

    /**
     * Remove current thread's test from ThreadLocal.
     */
    public static void removeTest() {
        testThreadLocal.remove();
    }
}
