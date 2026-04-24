package com.datman.calculator.tests;

import com.datman.calculator.driver.AppiumDriverManager;
import com.datman.calculator.pages.CalculatorPage;
import com.datman.calculator.utils.ExtentReportManager;
import com.datman.calculator.utils.ScreenshotUtils;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

/**
 * BaseTest — Parent class for all TestNG test classes.
 *
 * <p>Handles lifecycle:
 * <ul>
 *   <li>@BeforeSuite: Initialize ExtentReports</li>
 *   <li>@BeforeMethod: Start Appium driver, create CalculatorPage, log test start</li>
 *   <li>@AfterMethod: Capture screenshot on failure, log pass/fail to Extent</li>
 *   <li>@AfterClass: Quit driver</li>
 *   <li>@AfterSuite: Flush ExtentReports</li>
 * </ul>
 * </p>
 *
 * @author DatMan QA Team
 */
public abstract class BaseTest {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected CalculatorPage calculatorPage;

    @BeforeSuite
    public void suiteSetup() {
        logger.info("═══════════════════════════════════════════════════════");
        logger.info("  DatMan QA — Calculator Automation Suite Starting     ");
        logger.info("═══════════════════════════════════════════════════════");
        ExtentReportManager.initReports();
    }

    @BeforeMethod
    public void setUp(java.lang.reflect.Method method) {
        String testName = method.getName();
        logger.info("▶ Starting Test: {}", testName);

        // Initialize Appium driver
        AppiumDriverManager.initDriver();

        // Initialize Page Object
        calculatorPage = new CalculatorPage();

        // Create Extent test entry
        ExtentReportManager.createTest(testName);
        ExtentReportManager.getTest().log(Status.INFO, "Test Started: " + testName);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        // Log result to Extent report with screenshot on failure
        if (result.getStatus() == ITestResult.FAILURE) {
            String screenshotPath = ScreenshotUtils.captureScreenshot(testName);
            ExtentReportManager.getTest().log(Status.FAIL, "Test FAILED: " + result.getThrowable().getMessage());
            if (screenshotPath != null) {
                try {
                    ExtentReportManager.getTest()
                            .addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
                } catch (Exception e) {
                    logger.warn("Could not attach screenshot to report: {}", e.getMessage());
                }
            }
            logger.error("✗ FAILED: {}", testName);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            ExtentReportManager.getTest().log(Status.PASS, "Test PASSED ✓");
            logger.info("✓ PASSED: {}", testName);
        } else if (result.getStatus() == ITestResult.SKIP) {
            ExtentReportManager.getTest().log(Status.SKIP, "Test SKIPPED");
            logger.warn("⊘ SKIPPED: {}", testName);
        }

        ExtentReportManager.removeTest();
    }

    @AfterClass
    public void driverTearDown() {
        AppiumDriverManager.quitDriver();
    }

    @AfterSuite
    public void suiteTearDown() {
        ExtentReportManager.flushReports();
        logger.info("═══════════════════════════════════════════════════════");
        logger.info("  Suite Complete. Report: extent-reports/calculator-test-report.html");
        logger.info("═══════════════════════════════════════════════════════");
    }
}
