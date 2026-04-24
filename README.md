# 📱 Java Appium Mobile Calculator Automation — POM Framework

> **DatMan QA Trainee Assignment — Task 3**  
> Enterprise-grade mobile calculator test automation using Appium 2.x, TestNG 7, and the Page Object Model design pattern.

![Java](https://img.shields.io/badge/Java-11-orange?logo=openjdk)
![Appium](https://img.shields.io/badge/Appium-8.6-purple?logo=appium)
![TestNG](https://img.shields.io/badge/TestNG-7.9-blue)
![Maven](https://img.shields.io/badge/Maven-3.9-red?logo=apache-maven)
![ExtentReports](https://img.shields.io/badge/ExtentReports-5.x-green)

---

## 🎯 Assignment Goal

> Design and implement a mobile automation framework using Appium (Java) in POM architecture to test a calculator app (addition, subtraction, multiplication, division).

---

## 🏗️ Framework Architecture

```
src/
├── main/java/com/datman/calculator/
│   ├── config/
│   │   └── ConfigManager.java          ← Singleton, loads config.properties
│   ├── driver/
│   │   └── AppiumDriverManager.java    ← Thread-safe driver lifecycle (Android/iOS)
│   ├── pages/
│   │   ├── BasePage.java               ← Abstract base with PageFactory + helpers
│   │   └── CalculatorPage.java         ← POM: all calculator UI interactions
│   └── utils/
│       ├── WaitUtils.java              ← Explicit wait wrappers
│       ├── ScreenshotUtils.java        ← Auto-capture on test failure
│       └── ExtentReportManager.java    ← HTML report with dark theme
│
└── test/java/com/datman/calculator/tests/
    ├── BaseTest.java                   ← TestNG lifecycle: setup, teardown, reporting
    ├── CalculatorArithmeticTests.java  ← 15 arithmetic test cases
    └── CalculatorEdgeCaseTests.java    ← 15 edge case test cases
```

---

## 🧪 Test Coverage (30 Test Cases)

### Arithmetic Tests (15)
| TC ID | Operation | Test Case |
|-------|-----------|-----------|
| TC-CALC-001 | Addition | 5 + 3 = 8 |
| TC-CALC-002 | Addition | Large: 9999 + 1 = 10000 |
| TC-CALC-003 | Addition | 5 + (-5) = 0 |
| TC-CALC-004 | Addition | 0.1 + 0.2 = 0.3 |
| TC-CALC-005 | Subtraction | 10 - 3 = 7 |
| TC-CALC-006 | Subtraction | 3 - 10 = -7 (negative result) |
| TC-CALC-007 | Subtraction | 99 - 99 = 0 |
| TC-CALC-008 | Multiplication | 6 × 7 = 42 |
| TC-CALC-009 | Multiplication | 999 × 0 = 0 |
| TC-CALC-010 | Multiplication | Identity: 12345 × 1 = 12345 |
| TC-CALC-011 | Multiplication | (-5) × (-3) = 15 |
| TC-CALC-012 | Division | 15 ÷ 3 = 5 |
| TC-CALC-013 | Division | 10 ÷ 3 = 3.333... (recurring) |
| TC-CALC-014 | Division | Divide by zero → Error/∞ |
| TC-CALC-015 | Division | 0 ÷ 5 = 0 |

### Edge Case Tests (15)
| TC ID | Feature | Test Case |
|-------|---------|-----------|
| TC-EC-001 | Clear (AC) | Resets display to 0 |
| TC-EC-002 | Backspace (DEL) | Removes last digit |
| TC-EC-003 | Percent (%) | 200 × 50% = 100 |
| TC-EC-004 | Chained ops | 2 + 3 + 4 = 9 |
| TC-EC-005 | Toggle Sign | 7 → -7 |
| TC-EC-006 | Decimal Input | Enter 3.14 |
| TC-EC-007 | Double Decimal | Ignored by calculator |
| TC-EC-008 | Large Numbers | 12345678 displayed |
| TC-EC-009 | Continue from Result | 5+3=8, +2=10 |
| TC-EC-010 | Square | 9 × 9 = 81 |
| TC-EC-011 | Neg × Pos | -5 × 4 = -20 |
| TC-EC-012 | Decimal Subtraction | 1 - 0.5 = 0.5 |
| TC-EC-013 | Clean Division | 100 ÷ 4 = 25 |
| TC-EC-014 | Multiple Clears | Result valid after clears |
| TC-EC-015 | Add Zero | 42 + 0 = 42 (identity) |

---

## 🚀 Setup & Run

### Prerequisites
- Java 11+
- Maven 3.9+
- Appium Server 2.x (`npm install -g appium`)
- Android SDK + AVD (or physical device)

### 1. Clone Repository
```bash
git clone https://github.com/code0era/Java-Appium-Mobile-Calculator-Automation-POM-.git
cd Java-Appium-Mobile-Calculator-Automation-POM-
```

### 2. Configure Device
Edit `src/test/resources/config.properties`:
```properties
platform=android
device.name=emulator-5554       # Run 'adb devices -l'
platform.version=14
app.package=com.google.android.calculator
app.activity=com.google.android.calculator.Calculator
```

### 3. Start Appium Server
```bash
appium --port 4723
```

### 4. Start Android Emulator
```bash
# List AVDs
emulator -list-avds

# Start AVD
emulator -avd Pixel_7_API_34
```

### 5. Run Tests
```bash
# All tests
mvn test

# Specific test class
mvn test -Dtest=CalculatorArithmeticTests

# Run specific TestNG suite via Maven
mvn clean test "-DsuiteXmlFile=src/test/resources/testng.xml"
```

---

## 📊 Reports

After running, open the HTML report:

```
extent-reports/calculator-test-report.html
```

Features:
- ✅ Dark theme UI with test status dashboard
- 📸 Auto-attached failure screenshots
- ⏱️ Execution duration per test
- 📋 Test log with step-by-step details

---

## 🎨 Design Patterns

| Pattern | Implementation |
|---------|---------------|
| Page Object Model (POM) | `CalculatorPage.java` — all locators + actions |
| Singleton | `AppiumDriverManager`, `ConfigManager` |
| ThreadLocal | Driver per-thread for parallel safety |
| Factory Method | `AppiumDriverManager.initDriver()` — Android/iOS |
| Fluent Interface | `calculatorPage.pressClear().enterNumber("5").pressPlus()...` |

---

## 🛠️ Tech Stack

| Component | Technology |
|-----------|-----------|
| Language | Java 11 |
| Framework | TestNG 7.9 |
| Automation | Appium 8.6 (Java Client) |
| Browser/Device | Android (UiAutomator2) + iOS (XCUITest) |
| Build | Maven 3.9 |
| Report | ExtentReports 5.x (HTML, Dark Theme) |
| Logging | Apache Log4j2 |

---

*Built for DatMan QA Trainee Assignment — demonstrating enterprise Appium POM framework with 30 calculator test cases.*
