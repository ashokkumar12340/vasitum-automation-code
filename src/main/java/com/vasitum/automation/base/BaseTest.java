package com.vasitum.automation.base;

import com.vasitum.automation.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;
    // ThreadLocal for parallel execution support
    private static ThreadLocal<WebDriver> threadLocalDriver = new ThreadLocal<>();

    @BeforeMethod
    public void setup() {
        String browser = ConfigReader.getProperty("browser").toLowerCase();

        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                if (Boolean.parseBoolean(ConfigReader.getProperty("headless"))) {
                    options.addArguments("--headless");
                }
                driver = new ChromeDriver(options);
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                throw new RuntimeException("Browser not supported: " + browser);
        }

        threadLocalDriver.set(driver);
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(Long.parseLong(ConfigReader.getProperty("implicitInfo"))));
        getDriver().get(ConfigReader.getUrl());
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            threadLocalDriver.remove();
        }
    }

    public static synchronized WebDriver getDriver() {
        return threadLocalDriver.get();
    }
}
