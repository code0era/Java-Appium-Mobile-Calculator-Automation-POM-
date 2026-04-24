package com.datman.calculator.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

/**
 * CalculatorPage — Page Object Model for the Calculator application.
 *
 * <p>
 * Encapsulates all UI interactions with the calculator.
 * Uses dual locators for both Android and iOS support via @AndroidFindBy
 * and @iOSXCUITFindBy.
 * </p>
 *
 * <p>
 * Android target: com.google.android.calculator (AOSP Calculator)
 * </p>
 * <p>
 * iOS target: com.apple.mobilecalculator
 * </p>
 *
 * @author DatMan QA Team
 * @version 1.0
 */
public class CalculatorPage extends BasePage {

    // ══════════════════════════════════════════════════════════
    // NUMBER BUTTONS
    // ══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "0")
    @iOSXCUITFindBy(accessibility = "0")
    private WebElement btn0;

    @AndroidFindBy(accessibility = "1")
    @iOSXCUITFindBy(accessibility = "1")
    private WebElement btn1;

    @AndroidFindBy(accessibility = "2")
    @iOSXCUITFindBy(accessibility = "2")
    private WebElement btn2;

    @AndroidFindBy(accessibility = "3")
    @iOSXCUITFindBy(accessibility = "3")
    private WebElement btn3;

    @AndroidFindBy(accessibility = "4")
    @iOSXCUITFindBy(accessibility = "4")
    private WebElement btn4;

    @AndroidFindBy(accessibility = "5")
    @iOSXCUITFindBy(accessibility = "5")
    private WebElement btn5;

    @AndroidFindBy(accessibility = "6")
    @iOSXCUITFindBy(accessibility = "6")
    private WebElement btn6;

    @AndroidFindBy(accessibility = "7")
    @iOSXCUITFindBy(accessibility = "7")
    private WebElement btn7;

    @AndroidFindBy(accessibility = "8")
    @iOSXCUITFindBy(accessibility = "8")
    private WebElement btn8;

    @AndroidFindBy(accessibility = "9")
    @iOSXCUITFindBy(accessibility = "9")
    private WebElement btn9;

    // ══════════════════════════════════════════════════════════
    // OPERATOR BUTTONS
    // ══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "plus")
    @iOSXCUITFindBy(accessibility = "Add")
    private WebElement btnPlus;

    @AndroidFindBy(accessibility = "minus")
    @iOSXCUITFindBy(accessibility = "Subtract")
    private WebElement btnMinus;

    @AndroidFindBy(accessibility = "multiply")
    @iOSXCUITFindBy(accessibility = "Multiply")
    private WebElement btnMultiply;

    @AndroidFindBy(accessibility = "divide")
    @iOSXCUITFindBy(accessibility = "Divide")
    private WebElement btnDivide;

    @AndroidFindBy(accessibility = "equals")
    @iOSXCUITFindBy(accessibility = "Equals")
    private WebElement btnEquals;

    // ══════════════════════════════════════════════════════════
    // SPECIAL BUTTONS
    // ══════════════════════════════════════════════════════════

    @AndroidFindBy(accessibility = "clear")
    @iOSXCUITFindBy(accessibility = "Clear")
    private WebElement btnClear;

    @AndroidFindBy(accessibility = "delete")
    @iOSXCUITFindBy(accessibility = "Delete")
    private WebElement btnBackspace;

    @AndroidFindBy(accessibility = "point")
    @iOSXCUITFindBy(accessibility = "Decimal Separator")
    private WebElement btnDecimal;

    @AndroidFindBy(accessibility = "toggle sign")
    @iOSXCUITFindBy(accessibility = "Plus/Minus")
    private WebElement btnToggleSign;

    @AndroidFindBy(accessibility = "percent")
    @iOSXCUITFindBy(accessibility = "Percent")
    private WebElement btnPercent;

    // ══════════════════════════════════════════════════════════
    // RESULT DISPLAY
    // ══════════════════════════════════════════════════════════

    @AndroidFindBy(id = "com.google.android.calculator:id/result_preview")
    @iOSXCUITFindBy(accessibility = "Result")
    private WebElement resultPreview;

    @AndroidFindBy(id = "com.google.android.calculator:id/result_final")
    @iOSXCUITFindBy(accessibility = "Result")
    private WebElement resultFinal;

    @AndroidFindBy(id = "com.google.android.calculator:id/formula")
    @iOSXCUITFindBy(accessibility = "Formula")
    private WebElement formulaDisplay;

    // ══════════════════════════════════════════════════════════
    // ACTIONS — Business logic methods
    // ══════════════════════════════════════════════════════════

    /**
     * Press a digit button (0-9).
     *
     * @param digit The digit to press (0-9)
     * @return this — for method chaining
     */
    public CalculatorPage pressDigit(int digit) {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Digit must be 0-9, got: " + digit);
        WebElement btn = getDigitButton(digit);
        click(btn, "digit " + digit);
        return this;
    }

    /**
     * Enter a multi-digit number by pressing individual digits.
     */
    public CalculatorPage enterNumber(String number) {
        logger.info("Entering number: {}", number);
        for (char ch : number.toCharArray()) {
            if (ch == '.') {
                click(btnDecimal, "decimal point");
            } else if (ch == '-') {
                click(btnMinus, "Minus (-)");
            } else {
                pressDigit(Character.getNumericValue(ch));
            }
        }
        return this;
    }

    public CalculatorPage pressPlus() {
        click(btnPlus, "Plus (+)");
        return this;
    }

    public CalculatorPage pressMinus() {
        click(btnMinus, "Minus (-)");
        return this;
    }

    public CalculatorPage pressMultiply() {
        click(btnMultiply, "Multiply (×)");
        return this;
    }

    public CalculatorPage pressDivide() {
        click(btnDivide, "Divide (÷)");
        return this;
    }

    public CalculatorPage pressEquals() {
        click(btnEquals, "Equals (=)");
        return this;
    }

    public CalculatorPage pressClear() {
        click(btnClear, "Clear (AC)");
        return this;
    }

    public CalculatorPage pressBackspace() {
        click(btnBackspace, "Backspace (⌫)");
        return this;
    }

    public CalculatorPage pressDecimal() {
        click(btnDecimal, "Decimal (.)");
        return this;
    }

    public CalculatorPage pressPercent() {
        click(btnPercent, "Percent (%)");
        return this;
    }

    public CalculatorPage pressToggleSign() {
        click(btnToggleSign, "Toggle Sign (+/-)");
        return this;
    }

    // ══════════════════════════════════════════════════════════
    // RESULT RETRIEVAL
    // ══════════════════════════════════════════════════════════

    /**
     * Get the final result after pressing equals.
     * Falls back to preview result if final is not visible.
     */
    public String getResult() {
        try {
            return getText(resultFinal, "Result Final").replace("−", "-");
        } catch (Exception e) {
            logger.warn("resultFinal not visible, trying resultPreview");
            return getText(resultPreview, "Result Preview").replace("−", "-");
        }
    }

    /**
     * Get the formula/expression currently displayed.
     */
    public String getFormula() {
        return getText(formulaDisplay, "Formula Display").replace("−", "-");
    }

    /**
     * High-level: perform a complete binary operation.
     * Example: calculate("5", "+", "3") → presses 5, +, 3, =
     *
     * @param a  first operand
     * @param op one of "+", "-", "*", "/"
     * @param b  second operand
     * @return the result string
     */
    public String calculate(String a, String op, String b) {
        logger.info("Calculating: {} {} {}", a, op, b);
        pressClear();
        enterNumber(a);
        switch (op) {
            case "+": pressPlus(); break;
            case "-": pressMinus(); break;
            case "*": pressMultiply(); break;
            case "/": pressDivide(); break;
            default: throw new IllegalArgumentException("Unknown operator: " + op + ". Use +, -, *, /");
        }
        enterNumber(b);
        pressEquals();
        String result = getResult();
        logger.info("Result: {} {} {} = {}", a, op, b, result);
        return result;
    }

    /**
     * Clear the calculator display.
     */
    public CalculatorPage clearAll() {
        pressClear();
        return this;
    }

    // ══════════════════════════════════════════════════════════
    // PRIVATE HELPERS
    // ══════════════════════════════════════════════════════════

    private WebElement getDigitButton(int digit) {
        switch (digit) {
            case 0: return btn0;
            case 1: return btn1;
            case 2: return btn2;
            case 3: return btn3;
            case 4: return btn4;
            case 5: return btn5;
            case 6: return btn6;
            case 7: return btn7;
            case 8: return btn8;
            case 9: return btn9;
            default: throw new IllegalArgumentException("Invalid digit: " + digit);
        }
    }
}
