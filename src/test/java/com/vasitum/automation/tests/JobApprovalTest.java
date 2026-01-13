package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.Set;

public class JobApprovalTest {

        WebDriver driver;
        WebDriverWait wait;

        @BeforeMethod
        public void setup() {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                wait = new WebDriverWait(driver, Duration.ofSeconds(65));
                driver.manage().window().maximize();
        }

        @Test
        public void testJobApprovalWorkflow() throws InterruptedException {
                // --- PART 1: Initial Login & Company Insight ---
                driver.get("https://vasitum.com/login");
                System.out.println("Page URL: " + driver.getCurrentUrl());

                WebElement emailField = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='example@email.com']")));
                emailField.sendKeys("ashok.kumar@vasitum.com");
                emailField.sendKeys(Keys.ENTER);

                WebElement passwordField = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='Enter Password']")));
                passwordField.sendKeys("000000");
                passwordField.sendKeys(Keys.ENTER);

                wait.until(ExpectedConditions.urlContains("https://hire.vasitum.com/dashboard"));
                System.out.println("Part 1: Login successful.");
                Thread.sleep(3000);

                // Profile Click
                WebElement profileClick = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > div.jsx-91780021.container.flex > div.jsx-91780021.nav-actions.flex > div:nth-child(3) > div > div > img")));
                profileClick.click();
                Thread.sleep(1000);

                // Second option (Company Insight potentially)
                // Second option (Company Insight potentially) with Retry
                for (int i = 0; i < 3; i++) {
                        try {
                                WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(
                                                By.cssSelector(
                                                                "#__next > div.jsx-91780021.container.flex > div.jsx-91780021.nav-actions.flex > div:nth-child(3) > div > div.jsx-3553824937.position-absolute.profile-container.flex > div.jsx-3553824937.profile-actions.flex > div:nth-child(2)")));
                                secondOption.click();
                                break;
                        } catch (StaleElementReferenceException e) {
                                System.out.println("StaleElementReferenceException caught. Retrying... " + (i + 1));
                        }
                }
                Thread.sleep(1000);

                // Switch to Company Insight window
                String originalWindow = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                        if (!windowHandle.equals(originalWindow)) {
                                driver.switchTo().window(windowHandle);
                                break;
                        }
                }
                wait.until(ExpectedConditions.urlContains("/company/insight"));
                System.out.println("Navigated to Company Insight page");
                Thread.sleep(700);

                // Click on ME Icon (or relevant user icon)
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#top-header > div.sc-1815f5c5-0.fAgpqL > div > div.jsx-f52be28500f9f4bf.col > div:nth-child(6) > div > div:nth-child(1)")));
                element.click();
                Thread.sleep(1000);

                // Click on Ashok Enterprise company
                for (int i = 0; i < 3; i++) {
                        try {
                                WebElement tooltipOption = wait.until(ExpectedConditions.elementToBeClickable(
                                                By.cssSelector(
                                                                "#tippy-tooltip-4 > div > div.tippy-tooltip-content > div > div > div:nth-child(2) > div:nth-child(2) > a:nth-child(5) > div")));
                                tooltipOption.click();
                                break;
                        } catch (StaleElementReferenceException e) {
                                System.out.println("StaleElementReferenceException caught at tooltip. Retrying... "
                                                + (i + 1));
                        }
                }
                Thread.sleep(1000);

                // Back to Dashboard
                wait.until(ExpectedConditions.urlToBe("https://hire.vasitum.com/dashboard"));
                System.out.println("Successfully navigated back to Dashboard");
                Thread.sleep(1000);

                // --- PART 2: Approve Pending Requisition ---
                WebElement pendingButton = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-1783853876.flex1.section-1.gap > div.jsx-1783853876.section-1-2 > div > div.jsx-1224327479.width-section.flex-center.flex-col.height-section > div.jsx-1224327479.scrollable-container.width-section.flex.flex-col.items-gap > div:nth-child(1) > div.jsx-1224327479.offer-title.title-width.flex.item-width > div > div")));
                pendingButton.click();
                System.out.println("Pending Button clicked");

                wait.until(d -> d.getWindowHandles().size() >= 2);
                for (String window : driver.getWindowHandles()) {
                        driver.switchTo().window(window);
                        if (driver.getCurrentUrl().contains("/requisition")) {
                                System.out.println("Switched to requisition tab");
                                break;
                        }
                }
                wait.until(ExpectedConditions.urlContains("requisition"));
                Thread.sleep(2000);

                WebElement viewDetails = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-1277721251.card-div.flex-start.flex-col.typography.gap > div.jsx-1277721251.btn-div.width-section.flex-col.flex-start.gap-1 > div.jsx-1277721251.flex-center.width-section.view-details > span")));
                viewDetails.click();
                System.out.println("View Details clicked");
                Thread.sleep(500);

                ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
                System.out.println("Side panel scrolled down");

                WebElement sidebarBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2027401544.sidebar-panel > div > div.jsx-3517488073.btn-section.flex-end.width-section > div > div.jsx-2198409523.button.flex > div")));
                sidebarBtn.click();
                System.out.println("Sidebar Button clicked");
                Thread.sleep(500);

                WebElement approveConfirm = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-610984425.btn-container.flex > div.jsx-2198409523.button.flex > div")));
                approveConfirm.click();
                System.out.println("Approval confirmed");
                Thread.sleep(3000);

                // Logout
                WebElement profileIcon = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > div.jsx-2183865905.container.flex > div.jsx-2183865905.nav-actions.flex > div:nth-child(3) > div > div > img")));
                profileIcon.click();
                Thread.sleep(700);

                WebElement logoutOption = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > div.jsx-2183865905.container.flex > div.jsx-2183865905.nav-actions.flex > div:nth-child(3) > div > div.jsx-3553824937.position-absolute.profile-container.flex > div.jsx-3553824937.profile-actions.flex > div:nth-child(5) > img")));
                logoutOption.click();
                System.out.println("Logged out successfully");
                Thread.sleep(5000);

                // --- PART 3: Second User Login (ashok.kumar+4@vasitum.in in user snippet? No,
                // ashok.kumar@vasitum.in) ---
                driver.get("https://vasitum.com/login");
                WebElement secondEmail = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='example@email.com']")));
                secondEmail.sendKeys("ashok.kumar@vasitum.in");
                secondEmail.sendKeys(Keys.ENTER);

                WebElement secondPwd = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='Enter Password']")));
                secondPwd.sendKeys("000000");
                secondPwd.sendKeys(Keys.ENTER);

                wait.until(ExpectedConditions.urlContains("https://hire.vasitum.com/dashboard"));
                System.out.println("Part 3: Second Login successful.");
                Thread.sleep(2000);

                // Click Pending Button again
                WebElement secondPendingBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-1783853876.flex1.section-1.gap > div.jsx-1783853876.section-1-2 > div > div.jsx-1224327479.width-section.flex-center.flex-col.height-section > div.jsx-1224327479.scrollable-container.width-section.flex.flex-col.items-gap > div:nth-child(1) > div.jsx-1224327479.offer-title.title-width.flex.item-width > div > div")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", secondPendingBtn);

                Set<String> windows1 = driver.getWindowHandles();
                for (String window : windows1) {
                        driver.switchTo().window(window);
                        if (driver.getCurrentUrl().contains("/requisition")) {
                                System.out.println("Switched to requisition tab (2nd time)");
                                break;
                        }
                }
                wait.until(ExpectedConditions.urlContains("requisition"));
                Thread.sleep(1000);

                // View Details -> Approve -> Assign
                WebElement secondViewDetails = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-1277721251.card-div.flex-start.flex-col.typography.gap > div.jsx-1277721251.btn-div.width-section.flex-col.flex-start.gap-1 > div.jsx-1277721251.flex-center.width-section.view-details > span")));
                secondViewDetails.click();
                Thread.sleep(500);

                WebElement approveBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2027401544.sidebar-panel > div > div.jsx-3517488073.btn-section.flex-end.width-section > div > div.jsx-2198409523.button.flex > div")));
                approveBtn2.click();
                Thread.sleep(500);

                // Helper for popup interactions
                WebElement popupInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-8d8ceb7162c3ee6e.dropdown-div.flex-start.flex-col.width-section > div.jsx-2558438272.typography.wrapper-container > div.jsx-2558438272.relative-container > input")));
                popupInput.click();
                Thread.sleep(500);

                WebElement dropdownOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-8d8ceb7162c3ee6e.dropdown-div.flex-start.flex-col.width-section > div.jsx-2009989543.typography.wrapper-container > div.jsx-2009989543.options-container.option-container-gap > div")));
                dropdownOption.click();
                Thread.sleep(500);

                WebElement finalApprove = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-8d8ceb7162c3ee6e.action-div.flex-center.width-section.gap-1 > div.jsx-2198409523.button.flex > div")));
                finalApprove.click();
                System.out.println("Final approve clicked for assigning to Recruting Manager");
                Thread.sleep(1000);

                // Logout again
                WebElement profileIcon2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > div.jsx-2183865905.container.flex > div.jsx-2183865905.nav-actions.flex > div:nth-child(3) > div > div > img")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", profileIcon2);
                Thread.sleep(500);
                WebElement logoutOption2 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > div.jsx-2183865905.container.flex > div.jsx-2183865905.nav-actions.flex > div:nth-child(3) > div > div.jsx-3553824937.position-absolute.profile-container.flex > div.jsx-3553824937.profile-actions.flex > div:nth-child(5) > img")));
                logoutOption2.click();
                System.out.println("Logged out successfully");
                Thread.sleep(5000);

                // --- PART 4: Third User Login (ashok.kumar+4@vasitum.com) ---
                driver.get("https://vasitum.com/login");
                WebElement thirdEmail = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='example@email.com']")));
                thirdEmail.sendKeys("ashok.kumar+4@vasitum.com"); // User snippet says +6, logic said +4?? User snippet
                                                                  // has +6.
                thirdEmail.sendKeys(Keys.ENTER);

                WebElement thirdPwd = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='Enter Password']")));
                thirdPwd.sendKeys("000000");
                thirdPwd.sendKeys(Keys.ENTER);

                wait.until(ExpectedConditions.urlContains("https://hire.vasitum.com/dashboard"));
                System.out.println("Part 4: Third Login successful.");
                Thread.sleep(2000);

                // Requisition Icon click
                WebElement requisitionIcon = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.jsx-1522378224.container > div > div.jsx-2208575927 > div > div > div.jsx-2208575927.top.flex > div:nth-child(5) > div > div > img")));
                requisitionIcon.click(); // NOTE: user snippet clicks image
                Thread.sleep(500);

                // Job Title click
                WebElement jobTitle = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.jsx-1522378224.container > div > div.jsx-3611648327 > div > div > div.jsx-3611648327.top.flex > div:nth-child(5) > div > div.jsx-991912878.sub-menu-container > div > div:nth-child(2) > div")));
                jobTitle.click();

                Set<String> windows2 = driver.getWindowHandles();
                for (String window : windows2) {
                        driver.switchTo().window(window);
                        if (driver.getCurrentUrl().contains("/requisition")) {
                                System.out.println("Switched to requisition tab (3rd time)");
                                break;
                        }
                }
                wait.until(ExpectedConditions.urlContains("requisition"));

                WebElement thirdViewDetails = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div:nth-child(1) > div.jsx-1469888551.btn-div.width-section.flex-col.flex-start.gap-1 > div.jsx-1469888551.flex-center.width-section.view-details > span")));
                thirdViewDetails.click();
                Thread.sleep(500);

                // Sidebar Assign
                WebElement sidebarAssignBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2027401544.sidebar-panel > div > div.jsx-3517488073.btn-section.flex-end.width-section > div > div.jsx-2198409523.button.flex > div")));
                sidebarAssignBtn.click();

                // Recruiter Layout -> Option -> Confirm
                WebElement recruiterInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-8d8ceb7162c3ee6e.dropdown-div.flex-start.flex-col.width-section > div.jsx-2558438272.typography.wrapper-container > div.jsx-2558438272.relative-container > input")));
                recruiterInput.click();
                Thread.sleep(500);

                WebElement recruiterOption = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-8d8ceb7162c3ee6e.dropdown-div.flex-start.flex-col.width-section > div.jsx-2009989543.typography.wrapper-container > div.jsx-2009989543.options-container.option-container-gap > div:nth-child(3)")));
                recruiterOption.click();
                Thread.sleep(500);

                WebElement confirmAssign = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-8d8ceb7162c3ee6e.action-div.flex-center.width-section.gap-1 > div.jsx-2198409523.button.flex > div")));
                confirmAssign.click();
                System.out.println("Assignment confirmed");
                Thread.sleep(1000);

                // Logout again
                WebElement profileIcon3 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > div.jsx-2183865905.container.flex > div.jsx-2183865905.nav-actions.flex > div:nth-child(3) > div > div > img")));
                profileIcon3.click();
                Thread.sleep(500);
                WebElement logoutOption3 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
                                "#__next > div.jsx-2183865905.container.flex > div.jsx-2183865905.nav-actions.flex > div:nth-child(3) > div > div.jsx-3553824937.position-absolute.profile-container.flex > div.jsx-3553824937.profile-actions.flex > div:nth-child(5) > img")));
                logoutOption3.click();
                Thread.sleep(5000);

                // --- PART 5: Fourth User Login (ashok.kumar+6@vasitum.com... wait, is this the
                // same one?) ---
                // User snippet repeats login for `ashok.kumar+6@vasitum.com`. I already did
                // that in step 4 in the snippet.
                // Wait, Part 3 was +4 in the text description but user code says
                // "ashok.kumar@vasitum.in".
                // Part 4 in snippet says "ashok.kumar+6" in Step 2.
                // I will follow the user snippet explicitly as it seems to be the flow.

                driver.get("https://vasitum.com/login");
                WebElement fourthEmail = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='example@email.com']")));
                fourthEmail.sendKeys("ashok.kumar+6@vasitum.com");
                fourthEmail.sendKeys(Keys.ENTER);

                WebElement fourthPwd = wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder='Enter Password']")));
                fourthPwd.sendKeys("000000");
                fourthPwd.sendKeys(Keys.ENTER);

                wait.until(ExpectedConditions.urlContains("https://hire.vasitum.com/dashboard"));
                System.out.println("Part 5: Fourth Login successful.");
                Thread.sleep(2000);

                WebElement fourthElement = driver.findElement(By.cssSelector(
                                "#__next > main > div.jsx-1522378224.container > div > div.jsx-2208575927 > div > div > div.jsx-2208575927.top.flex > div:nth-child(5) > div > div > img"));
                fourthElement.click();

                WebElement fourthJobTitle = driver.findElement(By.cssSelector(
                                "#__next > main > div.jsx-1522378224.container > div > div.jsx-3611648327 > div > div > div.jsx-3611648327.top.flex > div:nth-child(5) > div > div.jsx-991912878.sub-menu-container > div > div:nth-child(2) > div"));
                fourthJobTitle.click();

                Set<String> windows3 = driver.getWindowHandles();
                for (String window : windows3) {
                        driver.switchTo().window(window);
                        if (driver.getCurrentUrl().contains("/requisition")) {
                                System.out.println("Switched to requisition tab (4th time)");
                                break;
                        }
                }
                wait.until(ExpectedConditions.urlContains("requisition"));
                Thread.sleep(1000);

                driver.findElement(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div > div.jsx-1469888551.btn-div.width-section.flex-col.flex-start.gap-1 > div > div.jsx-2198409523.button.flex > div"))
                                .click();
                System.out.println("click on post Button");

                driver.findElement(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div.jsx-3842333274.width-section.flex-start.flex-col.gap > div.jsx-3842333274.scrollable-section > div.jsx-2858949808.popup-overlay > div > div > div.jsx-610984425.btn-container.flex > div.jsx-2198409523.button.flex > div"))
                                .click();
                System.out.println("Job Raise Successfully");
                Thread.sleep(3000);
        }

        @AfterMethod
        public void teardown() {
                if (driver != null) {
                        driver.quit();
                }
        }
}
