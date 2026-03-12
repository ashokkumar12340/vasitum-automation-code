package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.vasitum.automation.pages.CandidateSearchPage;

public class CandidateSearchTest extends com.vasitum.automation.base.BaseTest {

    @Test
    public void verifyCandidateSearch() {
        // Navigate to Candidate Search Page since BaseTest might open Login or
        // Dashboard
        getDriver().get("https://vasitum.com/candidate");

        CandidateSearchPage cp = new CandidateSearchPage(getDriver());

        String skill = "java";
        String location = "Noida";

        cp.enterSearch(skill);
        cp.enterLocation(location);
        cp.clickSearch();

        // Wait for URL to update
        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(
                getDriver(), java.time.Duration.ofSeconds(10));
        wait.until(driver -> driver.getCurrentUrl().contains("query="));

        String url = getDriver().getCurrentUrl();
        System.out.println("Current URL: " + url);

        Assert.assertTrue(url.contains("query=" + skill), "Skill not present in URL. Got: " + url);
        Assert.assertTrue(url.contains("location=" + location) || url.contains("locations=" + location),
                "Location not present in URL. Got: " + url);

        System.out.println("✅ Candidate Search Test Passed!");
    }
}
