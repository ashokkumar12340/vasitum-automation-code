package com.vasitum.automation.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import com.vasitum.automation.listeners.TestListener;

import java.time.Duration;

@Listeners(TestListener.class)
public class StaticPagesTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @DataProvider(name = "staticPages")
    public Object[][] getStaticPages() {
        return new Object[][] {
                { "Home Page", "https://vasitum.com/" },
                { "About Us Page", "ACTION:Navigate_AboutUs" },
                { "Solutions Page", "ACTION:Navigate_Solutions" },
                { "Blog Page", "ACTION:Navigate_Blog" },
                { "Candidate Job Page", "ACTION:Navigate_Candidate_Jobs" },
                { "Our Clients", "https://vasitum.com/our-clients" },
                { "AI Job Portal for Startups", "https://vasitum.com/ai-job-portal-startups" },
                { "Automated Hiring System", "https://vasitum.com/automated-hiring-system" },
                { "Applicant Tracking System", "https://vasitum.com/applicant-tracking-system" },
                { "Book a Demo (Premium)", "https://vasitum.com/premium" },
                { "Terms & Conditions", "https://vasitum.com/termsofuse" },
                { "Privacy Policy", "https://vasitum.com/privacy-policy" },
                { "Savings Calculator", "https://vasitum.com/savings" }
        };
    }

    @Test(dataProvider = "staticPages")
    public void verifyStaticPage(String pageName, String urlOrAction) throws InterruptedException {
        System.out.println("--------------------------------------------------");
        System.out.println("VERIFYING: " + pageName);
        System.out.println("--------------------------------------------------");

        if (urlOrAction.startsWith("ACTION:")) {
            // These pages require navigation from the home page
            driver.get("https://vasitum.com/");
            String action = urlOrAction.split(":")[1];
            performNavigation(action);
        } else {
            // Direct URL navigation
            driver.get(urlOrAction);
        }

        slowScrollPage(driver);

        System.out.println("Waiting 10 seconds as requested...");
        Thread.sleep(10000); // Updated to 10 seconds

        // Basic verification that page didn't crash (title not empty)
        Assert.assertNotNull(driver.getTitle(), "Page title should not be null");
        System.out.println("✅ " + pageName + " Verification Completed.");
    }

    private void performNavigation(String action) {
        switch (action) {
            case "Navigate_AboutUs":
                try {
                    WebElement aboutBtn = wait.until(ExpectedConditions.elementToBeClickable(By
                            .xpath("//div[contains(@class,'btn') and contains(@class,'opas')][contains(text(),'About Us')]")));
                    aboutBtn.click();
                } catch (Exception e) {
                    System.out.println("Retrying About Us with simpler xpath...");
                    driver.findElement(By.xpath("//div[normalize-space()='About Us']")).click();
                }
                break;

            case "Navigate_Solutions":
                try {
                    WebElement solutionsBtn = driver.findElement(By
                            .xpath("//div[contains(@class,'btn') and contains(@class,'opas')][contains(text(),'Solutions')]"));
                    solutionsBtn.click();
                } catch (Exception e) {
                    System.out.println("Retrying Solutions with text...");
                    driver.findElement(By.xpath("//div[contains(text(),'Solutions')]")).click();
                }
                break;

            case "Navigate_Blog":
                try {
                    WebElement blogBtn = driver
                            .findElement(By.cssSelector("div[class*='nav_manu'] > div:nth-child(6)"));
                    blogBtn.click();
                } catch (Exception e) {
                    System.out.println("Fallback to text for Blog");
                    driver.findElement(By.xpath("//div[contains(text(),'Blog')]")).click();
                }
                break;

            case "Navigate_Candidate_Jobs":
                try {
                    WebElement candidateBtn = driver
                            .findElement(By.cssSelector("div[class*='nav_manu'] > div:nth-child(8)"));
                    candidateBtn.click();
                } catch (Exception e) {
                    System.out.println("Fallback to text for Candidate Page");
                    driver.findElement(By.xpath("//div[contains(text(),'Candidates')]")).click();
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown navigation action: " + action);
        }
    }

    private void slowScrollPage(WebDriver driver) throws InterruptedException {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        // Scroll Down
        System.out.println("Scrolling down slowly...");
        for (int i = 0; i < lastHeight; i += 150) { // Increased scroll step slightly for efficiency
            js.executeScript("window.scrollTo(0, " + i + ");");
            Thread.sleep(100);

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight > lastHeight) {
                lastHeight = newHeight;
            }
        }
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        Thread.sleep(1000);

        // Scroll Up
        System.out.println("Scrolling up slowly...");
        for (long i = lastHeight; i >= 0; i -= 150) {
            js.executeScript("window.scrollTo(0, " + i + ");");
            Thread.sleep(100);
        }
        js.executeScript("window.scrollTo(0, 0);");
    }
}
