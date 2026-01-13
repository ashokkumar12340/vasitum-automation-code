package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class MultiUserLoginTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.manage().window().maximize();

        // Both recruiter & candidate start from same link
        driver.get("https://vasitum.com/login");
    }

    @DataProvider(name = "loginUsers")
    public Object[][] loginUsers() {
        return new Object[][] {

                // Recruiter Login
                { "ashok.kumar@vasitum.com", "000000", "hire.vasitum.com/dashboard" },

                // Candidate Login
                { "ashok.kumar+1@vasitum.com", "000000", "vasitum.com/dashboard" }
        };
    }

    @Test(dataProvider = "loginUsers")
    public void multiUserLoginTest(String email, String password, String expectedUrl) throws InterruptedException {

        System.out.println("\n--------------------------------------------");
        System.out.println("Running Login Test For: " + email);
        System.out.println("--------------------------------------------");

        // Step 1 — Enter Email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='email']")));

        emailInput.sendKeys(email);
        System.out.println("✔ Email typed: " + email);

        // Step 2 — Press ENTER
        emailInput.sendKeys(Keys.ENTER);
        Thread.sleep(2000);

        // Step 3 — Wait for Password field
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@name='Password']")));

        System.out.println("✔ Password field visible");

        // Step 4 — Enter Password
        passwordInput.sendKeys(password);

        // Step 5 — Press ENTER to login
        passwordInput.sendKeys(Keys.ENTER);
        System.out.println("✔ Password submitted");

        // Wait for redirect
        Thread.sleep(4000);

        // Step 6 — Validate dashboard URL
        String url = driver.getCurrentUrl();
        System.out.println("➡ Redirected URL: " + url);

        Assert.assertTrue(
                url.contains(expectedUrl),
                "❌ Login failed for: " + email);

        System.out.println("✔ Login Success for: " + email);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
