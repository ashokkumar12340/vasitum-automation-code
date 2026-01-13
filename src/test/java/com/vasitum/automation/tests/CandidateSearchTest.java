package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.vasitum.automation.pages.CandidateSearchPage;

public class CandidateSearchTest {

    WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://vasitum.com/candidate");
    }

    @Test
    public void verifyCandidateSearch() {
        CandidateSearchPage cp = new CandidateSearchPage(driver);

        String skill = "java";
        String location = "Noida";

        cp.enterSearch(skill);
        cp.enterLocation(location);
        cp.clickSearch();

        // Wait for URL to update
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }

        String url = driver.getCurrentUrl();
        System.out.println("Current URL: " + url);

        Assert.assertTrue(url.contains("query=" + skill), "Skill not present in URL. Got: " + url);
        Assert.assertTrue(url.contains("location=" + location) || url.contains("locations=" + location),
                "Location not present in URL. Got: " + url);

        System.out.println("✅ Candidate Search Test Passed!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
