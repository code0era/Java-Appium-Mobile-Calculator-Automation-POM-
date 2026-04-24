package com.datman.calculator.tests;

import com.aventstack.extentreports.Status;
import com.datman.calculator.utils.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CalculatorArithmeticTests — Happy path arithmetic tests for the calculator.
 *
 * <p>Tests all basic arithmetic operations (CRUD of calculator):
 * Addition, Subtraction, Multiplication, Division.
 *
 * Priority: Critical — These are the core calculator functions that must work.</p>
 *
 * @author DatMan QA Team
 */
public class CalculatorArithmeticTests extends BaseTest {

    // ══════════════════════════════════════════════════════════
    // ADDITION TESTS
    // ══════════════════════════════════════════════════════════

    @Test(priority = 1, description = "TC-CALC-001: Verify addition of two positive integers")
    public void testAdditionPositiveIntegers() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-001: Testing 5 + 3 = 8");
        String result = calculatorPage.calculate("5", "+", "3");
        Assert.assertEquals(result, "8", "Expected 5 + 3 = 8");
        ExtentReportManager.getTest().log(Status.PASS, "Addition verified: 5 + 3 = " + result);
    }

    @Test(priority = 2, description = "TC-CALC-002: Verify addition with large numbers")
    public void testAdditionLargeNumbers() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-002: Testing 9999 + 1 = 10000");
        String result = calculatorPage.calculate("9999", "+", "1");
        Assert.assertTrue(result.equals("10,000") || result.equals("10000"), "Expected 9999 + 1 = 10000");
        ExtentReportManager.getTest().log(Status.PASS, "Large number addition: " + result);
    }

    @Test(priority = 3, description = "TC-CALC-003: Verify addition resulting in zero (positive + negative)")
    public void testAdditionResultingInZero() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-003: Testing -5 + 5 = 0");
        String result = calculatorPage.calculate("-5", "+", "5");
        Assert.assertEquals(result, "0", "Expected -5 + 5 = 0");
        ExtentReportManager.getTest().log(Status.PASS, "Zero result addition: " + result);
    }

    @Test(priority = 4, description = "TC-CALC-004: Verify addition of decimals")
    public void testAdditionDecimals() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-004: Testing 0.1 + 0.2");
        String result = calculatorPage.calculate("0.1", "+", "0.2");
        Assert.assertTrue(result.startsWith("0.3"), "Expected 0.1 + 0.2 ≈ 0.3, got: " + result);
        ExtentReportManager.getTest().log(Status.PASS, "Decimal addition: " + result);
    }

    // ══════════════════════════════════════════════════════════
    // SUBTRACTION TESTS
    // ══════════════════════════════════════════════════════════

    @Test(priority = 5, description = "TC-CALC-005: Verify subtraction of two positive integers")
    public void testSubtractionPositiveIntegers() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-005: Testing 10 - 3 = 7");
        String result = calculatorPage.calculate("10", "-", "3");
        Assert.assertEquals(result, "7", "Expected 10 - 3 = 7");
        ExtentReportManager.getTest().log(Status.PASS, "Subtraction: 10 - 3 = " + result);
    }

    @Test(priority = 6, description = "TC-CALC-006: Verify subtraction resulting in negative number")
    public void testSubtractionResultingNegative() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-006: Testing 3 - 10 = -7");
        String result = calculatorPage.calculate("3", "-", "10");
        Assert.assertEquals(result, "-7", "Expected 3 - 10 = -7");
        ExtentReportManager.getTest().log(Status.PASS, "Negative subtraction: " + result);
    }

    @Test(priority = 7, description = "TC-CALC-007: Verify subtraction of same number (result = 0)")
    public void testSubtractionSameNumbers() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-007: Testing 99 - 99 = 0");
        String result = calculatorPage.calculate("99", "-", "99");
        Assert.assertEquals(result, "0", "Expected 99 - 99 = 0");
        ExtentReportManager.getTest().log(Status.PASS, "Same number subtraction: " + result);
    }

    // ══════════════════════════════════════════════════════════
    // MULTIPLICATION TESTS
    // ══════════════════════════════════════════════════════════

    @Test(priority = 8, description = "TC-CALC-008: Verify basic multiplication")
    public void testMultiplicationBasic() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-008: Testing 6 × 7 = 42");
        String result = calculatorPage.calculate("6", "*", "7");
        Assert.assertEquals(result, "42", "Expected 6 × 7 = 42");
        ExtentReportManager.getTest().log(Status.PASS, "Multiplication: 6 × 7 = " + result);
    }

    @Test(priority = 9, description = "TC-CALC-009: Verify multiplication by zero")
    public void testMultiplicationByZero() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-009: Testing 999 × 0 = 0");
        String result = calculatorPage.calculate("999", "*", "0");
        Assert.assertEquals(result, "0", "Expected 999 × 0 = 0");
        ExtentReportManager.getTest().log(Status.PASS, "Multiply by zero: " + result);
    }

    @Test(priority = 10, description = "TC-CALC-010: Verify multiplication by 1 (identity)")
    public void testMultiplicationByOne() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-010: Testing 12345 × 1 = 12345");
        String result = calculatorPage.calculate("12345", "*", "1");
        Assert.assertTrue(result.contains("12345") || result.equals("12,345"),
                "Expected 12345 × 1 = 12345, got: " + result);
        ExtentReportManager.getTest().log(Status.PASS, "Multiply by one: " + result);
    }

    @Test(priority = 11, description = "TC-CALC-011: Verify multiplication of two negative numbers")
    public void testMultiplicationNegativeNumbers() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-011: Testing -5 × -3 = 15");
        String result = calculatorPage.calculate("-5", "*", "-3");
        Assert.assertEquals(result, "15", "Expected (-5) × (-3) = 15");
        ExtentReportManager.getTest().log(Status.PASS, "Negative × Negative: " + result);
    }

    // ══════════════════════════════════════════════════════════
    // DIVISION TESTS
    // ══════════════════════════════════════════════════════════

    @Test(priority = 12, description = "TC-CALC-012: Verify basic division")
    public void testDivisionBasic() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-012: Testing 15 ÷ 3 = 5");
        String result = calculatorPage.calculate("15", "/", "3");
        Assert.assertEquals(result, "5", "Expected 15 ÷ 3 = 5");
        ExtentReportManager.getTest().log(Status.PASS, "Division: 15 ÷ 3 = " + result);
    }

    @Test(priority = 13, description = "TC-CALC-013: Verify division resulting in decimal")
    public void testDivisionDecimalResult() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-013: Testing 10 ÷ 3 = 3.333...");
        String result = calculatorPage.calculate("10", "/", "3");
        Assert.assertTrue(result.startsWith("3.3"), "Expected 10 ÷ 3 to start with 3.3, got: " + result);
        ExtentReportManager.getTest().log(Status.PASS, "Decimal division: " + result);
    }

    @Test(priority = 14, description = "TC-CALC-014: Verify division by zero shows error")
    public void testDivisionByZero() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-014: Testing X ÷ 0 → Error");
        String result = calculatorPage.calculate("5", "/", "0");
        // Different calculators show different messages: "Error", "∞", "Infinity", "Can't divide by 0"
        boolean isError = result.toLowerCase().contains("error") ||
                result.contains("∞") ||
                result.contains("Infinity") ||
                result.contains("Can") ||
                result.contains("undefined");
        Assert.assertTrue(isError, "Expected error for divide by zero, got: " + result);
        ExtentReportManager.getTest().log(Status.PASS, "Division by zero handled: " + result);
    }

    @Test(priority = 15, description = "TC-CALC-015: Verify 0 ÷ non-zero = 0")
    public void testZeroDividedByNumber() {
        ExtentReportManager.getTest().log(Status.INFO, "TC-CALC-015: Testing 0 ÷ 5 = 0");
        String result = calculatorPage.calculate("0", "/", "5");
        Assert.assertEquals(result, "0", "Expected 0 ÷ 5 = 0");
        ExtentReportManager.getTest().log(Status.PASS, "Zero dividend: " + result);
    }
}
