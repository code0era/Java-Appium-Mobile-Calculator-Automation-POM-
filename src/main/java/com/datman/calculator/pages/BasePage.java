package com.datman.calculator.pages;

import com.datman.calculator.driver.AppiumDriverManager;
import com.datman.calculator.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

/**
 * BasePage — Abstract base class for all Page Objects.
 *
 * <p>Provides common functionality:
 * <ul>
 *   <li>PageFactory initialization with AppiumFieldDecorator</li>
 *   <li>WaitUtils access</li>
 *   <li>Click/sendKeys helpers with logging</li>
 *   <li>Element visibility checks</li>
 * </ul>
 * </p>
 *
 * <p>All Page Objects extend this class.</p>
 *
 * @author DatMan QA Team
 */
public abstract class BasePage {

    protected final AppiumDriver driver;
    protected final WaitUtils wait;
    protected final Logger logger = LogManager.getLogger(this.getClass());

    protected BasePage() {
        this.driver = AppiumDriverManager.getDriver();
        this.wait = new WaitUtils(driver);
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(15)),
                this
        );
        logger.debug("PageObject initialized: {}", this.getClass().getSimpleName());
    }

    // ─── Common Actions ──────────────────────────────────────

    /**
     * Safe click with explicit wait for clickability.
     *
     * @param element WebElement to click
     * @param description Human-readable element description for logging
     */
    protected void click(WebElement element, String description) {
        try {
            wait.waitForClickable(element);
            element.click();
            logger.info("Clicked: [{}]", description);
        } catch (Exception e) {
            logger.error("Failed to click [{}]: {}", description, e.getMessage());
            throw new RuntimeException("Click failed on: " + description, e);
        }
    }

    /**
     * Clear and type text into an element.
     */
    protected void sendKeys(WebElement element, String text, String description) {
        try {
            wait.waitForVisible(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Typed '{}' into [{}]", text, description);
        } catch (Exception e) {
            logger.error("Failed to type into [{}]: {}", description, e.getMessage());
            throw new RuntimeException("SendKeys failed on: " + description, e);
        }
    }

    /**
     * Get text from element with explicit wait.
     */
    protected String getText(WebElement element, String description) {
        try {
            wait.waitForVisible(element);
            String text = element.getText();
            logger.info("Got text from [{}]: '{}'", description, text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from [{}]: {}", description, e.getMessage());
            throw new RuntimeException("GetText failed on: " + description, e);
        }
    }

    /**
     * Check if element is displayed without throwing exception.
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
