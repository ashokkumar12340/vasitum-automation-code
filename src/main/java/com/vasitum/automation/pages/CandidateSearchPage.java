package com.vasitum.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CandidateSearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators (Best Guess - will refine if test fails)
    @FindBy(xpath = "//input[contains(@placeholder, 'Search') or contains(@placeholder, 'Skill') or @name='query']")
    private WebElement searchInput;

    @FindBy(xpath = "//input[contains(@placeholder, 'Location') or @name='location' or @id='location']")
    private WebElement locationInput;

    @FindBy(xpath = "//button[contains(text(), 'Search') or contains(@class, 'search')]")
    private WebElement searchButton;

    public CandidateSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void enterSearch(String skill) {
        wait.until(ExpectedConditions.visibilityOf(searchInput));
        searchInput.clear();
        searchInput.sendKeys(skill);
    }

    public void enterLocation(String location) {
        wait.until(ExpectedConditions.visibilityOf(locationInput));
        locationInput.clear();
        locationInput.sendKeys(location);
        try {
            // Wait for autocomplete to appear and click first suggestion if possible
            // Or just hit ENTER to select top result
            Thread.sleep(1000);
            locationInput.sendKeys(org.openqa.selenium.Keys.ARROW_DOWN);
            locationInput.sendKeys(org.openqa.selenium.Keys.ENTER);
        } catch (Exception e) {
            System.out.println("Autocomplete selection failed: " + e.getMessage());
        }
    }

    public void clickSearch() {
        wait.until(ExpectedConditions.elementToBeClickable(searchButton));
        searchButton.click();
    }
}
