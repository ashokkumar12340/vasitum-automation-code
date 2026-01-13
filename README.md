# Vasitum Automation Platform

A professional-grade automation framework using Java, Selenium, Maven, TestNG, and Extent Reports.

## Technologies
- **Language**: Java
- **Library**: Selenium WebDriver
- **Build Tool**: Maven
- **Runner**: TestNG
- **Reporting**: Extent Reports

## Features
- **Page Object Model (POM)**: Modular and maintainable design.
- **BaseTest**: Centralized browser management.
- **ConfigReader**: Managing environment variables.
- **RetryAnalyzer**: Automatically retries flaky tests.
- **Extent Reports**: Beautiful HTML reports with screenshots on failure.
- **Parallel Execution**: Supported via TestNG.

## Prerequisites
- Java JDK 17+
- Maven 3.6+
- Chrome/Firefox Browser

## Setup
1. Clone the repository.
2. Navigate to the project directory.
3. Install dependencies:
   ```sh
   mvn clean install -DskipTests
   ```

## Running Tests
Run the tests using Maven:
```sh
mvn test
```
Or run the `testng.xml` file directly from your IDE.

## Viewing Reports
After execution, the report will be generated at:
`test-output/ExtentReport.html`

Open this file in any browser to view the execution details, pass/fail status, and screenshots.
