package com.vasitum.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class MyJobsPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    public MyJobsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
    }

    public void verifyJobIdInDrafts(String jobId) {
        System.out.println("Verifying Job ID " + jobId + " exists in Drafts list...");
        verifyJobId(jobId, "//div[contains(@class,'alias') and normalize-space()='" + jobId + "']");
    }

    public void verifyJobIdInMyJobs(String jobId) {
        System.out.println("Verifying Job ID " + jobId + " exists in My Jobs list...");
        verifyJobId(jobId, "//div[contains(@class,'job-alise') and normalize-space()='" + jobId + "']");
    }

    private void verifyJobId(String jobId, String xpath) {
        try {
            WebElement jobInList = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            Assert.assertTrue(jobInList.isDisplayed(), "Job ID found but not visible");
            js.executeScript("arguments[0].style.border='3px solid green'", jobInList);
            System.out.println("✔ TEST PASSED: Job ID " + jobId + " found successfully.");
        } catch (Exception e) {
            Assert.fail("❌ TEST FAILED: Job ID " + jobId + " was NOT found. " + e.getMessage());
        }
    }
}
