package com.vasitum.automation.tests;

import com.vasitum.automation.base.BaseTest;
import com.vasitum.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import org.testng.annotations.Listeners;
import com.vasitum.automation.listeners.TestListener;

@Listeners(TestListener.class)
public class LoginTest extends BaseTest {

    @DataProvider(name = "loginUsers")
    public Object[][] loginUsers() {
        return new Object[][] {
                // Recruiter Login
                { "ashok.kumar@vasitum.com", "000000", "hire.vasitum.com/dashboard" },
                // Candidate Login
                { "ashok.kumar+1@vasitum.com", "000000", "vasitum.com/dashboard" }
        };
    }

    @Test(dataProvider = "loginUsers", description = "Verify multi-user login functionality")
    public void testMultiUserLogin(String email, String password, String expectedUrlFragment)
            throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);

        System.out.println("\n--------------------------------------------");
        System.out.println("Running Login Test For: " + email);
        System.out.println("--------------------------------------------");

        loginPage.login(email, password);

        // Wait for redirect
        Thread.sleep(4000);

        String currentUrl = driver.getCurrentUrl();
        System.out.println("➡ Redirected URL: " + currentUrl);

        Assert.assertTrue(currentUrl.contains(expectedUrlFragment),
                "Login failed for: " + email + ". Expected URL to contain: " + expectedUrlFragment + " but got: "
                        + currentUrl);

        System.out.println("✔ Login Success for: " + email);
    }
}
