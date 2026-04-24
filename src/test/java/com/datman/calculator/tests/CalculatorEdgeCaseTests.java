package com.datman.calculator.tests;

import com.aventstack.extentreports.Status;
import com.datman.calculator.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CalculatorEdgeCaseTests — Edge cases, boundary conditions, and special functions.
 *
 * <p>Tests:
 * <ul>
 *   <li>Percent calculations</li>
 *   <li>Chained operations</li>
 *   <li>Clear (AC) and Backspace (DEL)</li>
 *   <li>Repeated equals behavior</li>
 *   <li>Very large and very small numbers</li>
 *   <li>Floating point precision</li>
 * </ul>
 * </p>
 *
 * @author DatMan QA Team
 */
public class CalculatorEdgeCaseTests extends BaseTest {

    @Test(priority = 1, description = "TC-EC-001: Verify Clear (AC) resets display to 0")
    public void testClearButtonResetsDisplay() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-001: Press 12345, then AC, expect 0");
        calculatorPage.enterNumber("12345");
        calculatorPage.pressClear();
        String result = calculatorPage.getResult();
        Assert.assertEquals(result, "0", "After AC, display should show 0");
        ExtentReportManager.getTest().log(Status.PASS, "Clear functionality verified: " + result);
    }

    @Test(priority = 2, description = "TC-EC-002: Verify Backspace (DEL) removes last digit")
    public void testBackspaceRemovesLastDigit() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-002: Enter 123, press DEL, expect 12");
        calculatorPage.pressClear().enterNumber("123").pressBackspace();
        String result = calculatorPage.getFormula();
        Assert.assertTrue(result.endsWith("12") || result.equals("12"),
                "After DEL from 123, expected 12, got: " + result);
        ExtentReportManager.getTest().log(Status.PASS, "Backspace verified: " + result);
    }

    @Test(priority = 3, description = "TC-EC-003: Verify percent operation (50% of 200 = 100)")
    public void testPercentOperation() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-003: 200 × 50% = 100");
        calculatorPage.pressClear()
                .enterNumber("200")
                .pressMultiply()
                .enterNumber("50")
                .pressPercent()
                .pressEquals();
        String result = calculatorPage.getResult();
        Assert.assertEquals(result, "100", "Expected 200 × 50% = 100");
        ExtentReportManager.getTest().log(Status.PASS, "Percent operation: " + result);
    }

    @Test(priority = 4, description = "TC-EC-004: Verify chained operations (2 + 3 + 4 = 9)")
    public void testChainedOperations() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-004: 2 + 3 + 4 = 9");
        calculatorPage.pressClear()
                .pressDigit(2).pressPlus()
                .pressDigit(3).pressPlus()
                .pressDigit(4).pressEquals();
        String result = calculatorPage.getResult();
        Assert.assertEquals(result, "9", "Expected chained 2 + 3 + 4 = 9");
        ExtentReportManager.getTest().log(Status.PASS, "Chained ops: " + result);
    }

    @Test(priority = 5, description = "TC-EC-005: Verify toggle sign changes positive to negative")
    public void testToggleSignPositiveToNegative() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-005: Enter 7, toggle sign → -7");
        calculatorPage.pressClear().pressDigit(7).pressToggleSign();
        String result = calculatorPage.getFormula();
        Assert.assertTrue(result.contains("-7") || result.equals("-7"),
                "Expected -7 after toggle, got: " + result);
        ExtentReportManager.getTest().log(Status.PASS, "Toggle sign: " + result);
    }

    @Test(priority = 6, description = "TC-EC-006: Verify decimal input (3.14)")
    public void testDecimalNumberInput() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-006: Enter 3.14");
        calculatorPage.pressClear()
                .pressDigit(3).pressDecimal().pressDigit(1).pressDigit(4);
        String formula = calculatorPage.getFormula();
        Assert.assertTrue(formula.contains("3.14"),
                "Expected formula to contain 3.14, got: " + formula);
        ExtentReportManager.getTest().log(Status.PASS, "Decimal input: " + formula);
    }

    @Test(priority = 7, description = "TC-EC-007: Verify multiple decimal points not accepted")
    public void testMultipleDecimalPointsIgnored() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-007: Press decimal twice - should be ignored");
        calculatorPage.pressClear().pressDigit(3).pressDecimal().pressDecimal().pressDigit(5);
        String formula = calculatorPage.getFormula();
        Assert.assertFalse(formula.contains("..") || formula.contains("3..5"),
                "Double decimal should be ignored, got: " + formula);
        ExtentReportManager.getTest().log(Status.PASS, "Double decimal handled: " + formula);
    }

    @Test(priority = 8, description = "TC-EC-008: Verify large number display (12345678)")
    public void testLargeNumberDisplay() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-008: Enter large number 12345678");
        calculatorPage.pressClear().enterNumber("12345678");
        String formula = calculatorPage.getFormula();
        Assert.assertTrue(formula.contains("12345678") || formula.contains("12,345,678"),
                "Expected large number display, got: " + formula);
        ExtentReportManager.getTest().log(Status.PASS, "Large number: " + formula);
    }

    @Test(priority = 9, description = "TC-EC-009: Verify operation after result (result as first operand)")
    public void testContinueCalculationFromResult() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-009: 5 + 3 = 8, then + 2 = 10");
        calculatorPage.calculate("5", "+", "3"); // result = 8
        calculatorPage.pressPlus().pressDigit(2).pressEquals();
        String result = calculatorPage.getResult();
        Assert.assertEquals(result, "10", "Expected 8 + 2 = 10");
        ExtentReportManager.getTest().log(Status.PASS, "Chained from result: " + result);
    }

    @Test(priority = 10, description = "TC-EC-010: Verify square of a number via multiplication")
    public void testSquareViaMultiplication() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-010: 9 × 9 = 81");
        String result = calculatorPage.calculate("9", "*", "9");
        Assert.assertEquals(result, "81", "Expected 9² = 81");
        ExtentReportManager.getTest().log(Status.PASS, "Square: " + result);
    }

    @Test(priority = 11, description = "TC-EC-011: Verify negative × positive = negative")
    public void testNegativeTimesPositive() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-011: -5 × 4 = -20");
        calculatorPage.pressClear()
                .pressDigit(5).pressToggleSign()
                .pressMultiply()
                .pressDigit(4)
                .pressEquals();
        String result = calculatorPage.getResult();
        Assert.assertEquals(result, "-20", "Expected -5 × 4 = -20");
        ExtentReportManager.getTest().log(Status.PASS, "Neg × Pos: " + result);
    }

    @Test(priority = 12, description = "TC-EC-012: Verify subtraction resulting in decimal")
    public void testSubtractionDecimalResult() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-012: 1 - 0.5 = 0.5");
        String result = calculatorPage.calculate("1", "-", "0.5");
        Assert.assertEquals(result, "0.5", "Expected 1 - 0.5 = 0.5");
        ExtentReportManager.getTest().log(Status.PASS, "Decimal subtraction: " + result);
    }

    @Test(priority = 13, description = "TC-EC-013: Verify 100 ÷ 4 = 25")
    public void testDivisionCleanResult() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-013: 100 ÷ 4 = 25");
        String result = calculatorPage.calculate("100", "/", "4");
        Assert.assertEquals(result, "25", "Expected 100 ÷ 4 = 25");
        ExtentReportManager.getTest().log(Status.PASS, "Clean division: " + result);
    }

    @Test(priority = 14, description = "TC-EC-014: Verify result after multiple clears")
    public void testResultAfterMultipleClears() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-014: Calculate 5+5, clear, then 3+3");
        calculatorPage.calculate("5", "+", "5");
        calculatorPage.pressClear();
        String result = calculatorPage.calculate("3", "+", "3");
        Assert.assertEquals(result, "6", "Expected 3 + 3 = 6 after clear");
        ExtentReportManager.getTest().log(Status.PASS, "After multiple clears: " + result);
    }

    @Test(priority = 15, description = "TC-EC-015: Verify addition of zero doesn't change result")
    public void testAddZeroIsIdentity() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-EC-015: 42 + 0 = 42");
        String result = calculatorPage.calculate("42", "+", "0");
        Assert.assertEquals(result, "42", "Expected 42 + 0 = 42 (additive identity)");
        ExtentReportManager.getTest().log(Status.PASS, "Add zero identity: " + result);
    }
}
