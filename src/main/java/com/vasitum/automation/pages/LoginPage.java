package com.vasitum.automation.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    @FindBy(xpath = "//input[@type='email']")
    private WebElement emailInput;

    @FindBy(name = "Password")
    private WebElement passwordInput;

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    // Actions
    public void enterEmail(String email) {
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        emailInput.sendKeys(email);
        emailInput.sendKeys(Keys.ENTER);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
        passwordInput.sendKeys(Keys.ENTER);
    }

    public void login(String email, String password) {
        enterEmail(email);
        try {
            // Include a small wait to mimic user behavior/allow UI transition if needed
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        enterPassword(password);

        // Wait for login to complete by checking URL redirect
        wait.until(ExpectedConditions.urlContains("dashboard"));
    }
}
