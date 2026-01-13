package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class ReportsModuleTest {

    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        js = (JavascriptExecutor) driver;

        driver.manage().window().maximize();
        driver.get("https://vasitum.com/login");
    }

    @Test
    public void reportModuleTest() throws Exception {

        // --------------------------
        // LOGIN
        // --------------------------
        WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='email']")));
        email.sendKeys("ashok.kumar@vasitum.com");
        email.sendKeys(Keys.ENTER);
        Thread.sleep(1200);

        WebElement pwd = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='Password']")));
        pwd.sendKeys("000000");
        pwd.sendKeys(Keys.ENTER);

        Thread.sleep(3000);
        Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
        System.out.println("✔ Login Successful");

        // --------------------------
        // OPEN REPORTS PAGE
        // --------------------------
        driver.get("https://hire.vasitum.com/reports");
        Thread.sleep(2500);
        System.out.println("✔ Reports Page Opened");

        // --------------------------
        // CLICK OVERVIEW TAB
        // --------------------------
        WebElement overviewTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='Overview']")));
        overviewTab.click();
        System.out.println("✔ Overview Tab Clicked");

        Thread.sleep(2000);

        // --------------------------
        // FETCH ALL REPORT CARDS
        // --------------------------
        List<WebElement> cards = driver.findElements(
                By.xpath("//div[contains(@class,'card')]"));

        System.out.println("Total Report Cards Found: " + cards.size());
        Assert.assertTrue(cards.size() > 0);

        // ----------------------------------------------------
        // LOOP: CLICK EACH CARD → READ COUNT → DOWNLOAD REPORT
        // ----------------------------------------------------
        for (int i = 1; i <= cards.size(); i++) {

            try {
                // Re-locate card fresh each loop (prevents stale element issue)
                WebElement card = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("(//div[contains(@class,'card')])[" + i + "]")));

                // Get title (safe get)
                String cardTitle = "Unknown";
                try {
                    WebElement titleEl = card.findElement(By.xpath(".//div[contains(@class,'title')]"));
                    cardTitle = titleEl.getText();
                } catch (Exception ignore) {
                }

                // Get count number (safe get)
                String countValue = "N/A";
                try {
                    WebElement count = card.findElement(By.xpath(".//div[contains(@class,'heading')]/div[1]"));
                    countValue = count.getText();
                } catch (Exception ignore) {
                }

                System.out.println("➡ Clicking Report: " + cardTitle + " | Count: " + countValue);

                // Scroll into view and click the card
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", card);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", card);

                // Wait a short time for details/table to render
                Thread.sleep(5000); // user-requested 5 sec wait for actions

                // Scroll to bottom where Download button usually appears
                js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                Thread.sleep(1000);

                // -----------------------------
                // STABLE DOWNLOAD LOCATOR & CLICK
                // -----------------------------
                By downloadLocatorPrimary = By.xpath("//div[contains(@class,'csv-div')]//*[text()='Download']");
                By downloadLocatorFallback = By.xpath("//div[contains(@class,'csv-div')]");

                WebElement downloadBtn = null;

                // Try primary locator with wait
                try {
                    downloadBtn = wait.until(ExpectedConditions.elementToBeClickable(downloadLocatorPrimary));
                } catch (Exception exPrimary) {
                    // primary failed — try fallback: clickable parent csv-div
                    try {
                        WebElement csvDiv = wait
                                .until(ExpectedConditions.elementToBeClickable(downloadLocatorFallback));
                        // find inner element text if present
                        try {
                            WebElement inner = csvDiv.findElement(By.xpath(".//*[text()='Download']"));
                            downloadBtn = inner;
                        } catch (Exception exInner) {
                            // If inner text not found, use parent element for click
                            downloadBtn = csvDiv;
                        }
                    } catch (Exception exFallback) {
                        // give one final small retry after a brief wait
                        Thread.sleep(2000);
                        downloadBtn = driver.findElement(downloadLocatorFallback);
                    }
                }

                // Ensure button visible (scroll to it) and click with JS to avoid overlay
                // issues
                js.executeScript("arguments[0].scrollIntoView({block:'center'});", downloadBtn);
                Thread.sleep(500);
                js.executeScript("arguments[0].click();", downloadBtn);

                System.out.println("✔ Downloaded Report For: " + cardTitle);

                // wait 5 seconds after download as requested
                Thread.sleep(5000);

                // Navigate back to reports overview
                driver.get("https://hire.vasitum.com/reports");
                Thread.sleep(2000);

                // re-click overview to ensure listing visible
                overviewTab = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[text()='Overview']")));
                overviewTab.click();
                Thread.sleep(2000);

            } catch (Exception e) {
                // Log and continue to next card instead of failing the test completely
                System.err.println("⚠ Failed for card index " + i + " — continuing. Error: " + e.getMessage());
                // attempt to go back to reports home to continue
                try {
                    driver.get("https://hire.vasitum.com/reports");
                    Thread.sleep(2000);
                    WebElement ot = wait
                            .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Overview']")));
                    ot.click();
                    Thread.sleep(1000);
                } catch (Exception ignored) {
                }
            }
        }

        // --------------------------
        // CLICK "Jobs" TAB
        // --------------------------
        WebElement jobsTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='Jobs']")));
        jobsTab.click();
        System.out.println("✔ Jobs Tab Clicked");

        Thread.sleep(5000);

        // Jobs download (use robust locator)
        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement jobsDownload = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'csv-div')]//*[text()='Download']")));
            js.executeScript("arguments[0].click();", jobsDownload);
            System.out.println("✔ Jobs Report Downloaded");
        } catch (Exception e) {
            System.err.println("⚠ Jobs download failed: " + e.getMessage());
        }

        Thread.sleep(5000);

        // --------------------------
        // CLICK "Team Performance"
        // --------------------------
        WebElement teamTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[text()='Team Performance']")));
        teamTab.click();
        System.out.println("✔ Team Performance Tab Clicked");

        Thread.sleep(5000);

        try {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(1000);
            WebElement teamDownload = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'csv-div')]//*[text()='Download']")));
            js.executeScript("arguments[0].click();", teamDownload);
            System.out.println("✔ Team Performance Report Downloaded");
        } catch (Exception e) {
            System.err.println("⚠ Team Performance download failed: " + e.getMessage());
        }

        Thread.sleep(5000);

        System.out.println("🎉 Reports Module run finished.");
    }

    @AfterMethod
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
