package com.datman.calculator.utils;

import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * WaitUtils — Explicit wait helper utility.
 *
 * <p>Wraps Selenium WebDriverWait to provide semantic wait methods
 * with clear logging and timeouts from configuration.</p>
 *
 * @author DatMan QA Team
 */
public class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    private final WebDriverWait wait;
    private static final int DEFAULT_TIMEOUT_SECONDS = 30;

    public WaitUtils(AppiumDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT_SECONDS));
    }

    public WaitUtils(AppiumDriver driver, int timeoutSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    /**
     * Wait until element is visible on screen.
     *
     * @param element target element
     * @return the visible element
     */
    public WebElement waitForVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Element not visible within {}s: {}", DEFAULT_TIMEOUT_SECONDS, e.getMessage());
            throw new RuntimeException("Element not visible: " + e.getMessage(), e);
        }
    }

    /**
     * Wait until element is clickable (visible and enabled).
     *
     * @param element target element
     * @return the clickable element
     */
    public WebElement waitForClickable(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("Element not clickable within {}s", DEFAULT_TIMEOUT_SECONDS);
            throw new RuntimeException("Element not clickable: " + e.getMessage(), e);
        }
    }

    /**
     * Wait until element contains specific text.
     *
     * @param element the element to check
     * @param text expected text
     * @return true if text matches within timeout
     */
    public boolean waitForText(WebElement element, String text) {
        try {
            return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            logger.warn("Text '{}' not found in element within {}s", text, DEFAULT_TIMEOUT_SECONDS);
            return false;
        }
    }

    /**
     * Pause for specified milliseconds.
     * Use sparingly — prefer explicit waits.
     *
     * @param milliseconds duration to wait
     */
    public void sleep(long milliseconds) {
        try {
            logger.debug("Sleeping for {}ms", milliseconds);
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
