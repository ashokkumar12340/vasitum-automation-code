package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import com.vasitum.automation.listeners.TestListener;

import java.time.Duration;
import java.util.List;

@Listeners(TestListener.class)
public class OfferLetterTemplateTest {

        WebDriver driver;
        WebDriverWait wait;

        @BeforeMethod
        public void setup() {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                wait = new WebDriverWait(driver, Duration.ofSeconds(30));

                driver.manage().window().maximize();
                driver.get("https://vasitum.com/login");
        }

        @Test
        public void createOfferLetterTemplate() throws Exception {

                String templateName = "java developer sde1 optionFix " + System.currentTimeMillis();

                // --------------------------------
                // LOGIN
                // --------------------------------
                WebElement email = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//input[@type='email']")));
                // Correcting typo from user input: 'ashok.kuma r' -> 'ashok.kumar' and using
                // .com to match other tests
                email.sendKeys("ashok.kumar@vasitum.in");
                email.sendKeys(Keys.ENTER);
                Thread.sleep(1500);

                WebElement pwd = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//input[@name='Password']")));
                pwd.sendKeys("000000");
                pwd.sendKeys(Keys.ENTER);

                Thread.sleep(3500);
                Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
                System.out.println("✔ Login Successful");

                // --------------------------------
                // OPEN OFFER LETTER TEMPLATE PAGE
                // --------------------------------
                driver.get("https://hire.vasitum.com/offerlettertemplates");
                Thread.sleep(2500);
                System.out.println("✔ Offer Letter Template Page Opened");

                // --------------------------------
                // CLICK CREATE NEW TEMPLATE
                // --------------------------------
                WebElement createBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(text(),'Create new template')]")));
                createBtn.click();
                System.out.println("✔ Create New Template Clicked");

                // --------------------------------
                // ENTER TEMPLATE NAME
                // --------------------------------
                WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//input[@placeholder='Enter template name here']")));
                nameInput.sendKeys(templateName);
                System.out.println("✔ Template Name Entered: " + templateName);

                // NEXT
                WebElement nextBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[text()='Next']")));
                nextBtn.click();
                System.out.println("✔ Next Clicked");

                // SKIP
                WebElement skipBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[text()='Skip']")));
                skipBtn.click();
                System.out.println("✔ Skip Clicked");

                // GENERATE WITH AI
                WebElement aiBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[contains(text(),'Generate with AI')]")));
                aiBtn.click();
                System.out.println("🤖 AI Offer Letter Generation Started (35 sec wait)");
                Thread.sleep(35000);

                // PREVIEW
                WebElement previewBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[text()='Preview']")));
                previewBtn.click();
                System.out.println("✔ Preview Clicked");

                Thread.sleep(3000);

                // CLOSE PREVIEW
                WebElement closePreview = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//img[contains(@src,'close_icon')]")));
                closePreview.click();
                System.out.println("✔ Preview Closed");

                // NEXT AFTER PREVIEW
                WebElement nextBtn2 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[text()='Next']")));
                nextBtn2.click();
                System.out.println("✔ Next Clicked After Preview");

                // --------------------------------
                // CANDIDATE NAME AUTOSUGGEST
                // --------------------------------
                WebElement candidateName = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//input[@placeholder='Enter Name']")));
                candidateName.click(); // User requested click only
                System.out.println("✔ Candidate Input Clicked");
                Thread.sleep(2000);

                List<WebElement> nameSuggestions = wait.until(
                                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                                                By.xpath("//div[contains(@class,'dropdown_item')]")));

                if (!nameSuggestions.isEmpty()) {
                        nameSuggestions.get(0).click();
                        System.out.println("✔ First auto-suggest candidate selected");
                } else {
                        System.out.println("⚠ No suggestions found for candidate name");
                }

                // --------------------------------
                // SIGNEE DROPDOWN OPEN
                // --------------------------------
                try {
                        // User provided specific CSS selector for the input (Latest)
                        WebElement signeeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.cssSelector("#__next > div.jsx-b04565efaf5ef842 > main > div > div > div.jsx-b04565efaf5ef842.content > div > div:nth-child(2) > div.jsx-2730439225.frame > div.jsx-2730439225.dropDownContainer > div.jsx-2248294532 > div > div > div")));
                        signeeDropdown.click();
                        System.out.println("✔ Signee Dropdown Opened (User Selector)");
                } catch (Exception e) {
                        System.out.println("⚠ User selector failed, trying previous CSS...");
                        try {
                                WebElement signeeDropdownOld = wait.until(ExpectedConditions.elementToBeClickable(
                                                By.cssSelector("#__next > div.jsx-b04565efaf5ef842 > main > div > div > div.jsx-b04565efaf5ef842.content > div > div:nth-child(2) > div.jsx-2730439225.frame > div.jsx-2730439225.dropDownContainer > div.jsx-325976892 > div > div > div.jsx-325976892.input")));
                                signeeDropdownOld.click();
                                System.out.println("✔ Signee Dropdown Opened (Previous User Selector)");
                        } catch (Exception ex) {
                                System.out.println("⚠ Both CSS failed, trying text...");
                                WebElement signeeDropdownText = wait.until(ExpectedConditions.elementToBeClickable(
                                                By.xpath("//div[contains(text(),'Select Signee')]")));
                                signeeDropdownText.click();
                        }
                }

                Thread.sleep(1500);

                // --------------------------------
                // SIGNEE OPTIONS
                // --------------------------------
                try {
                        // Priority 1: User's Specific CSS for first option
                        WebElement specificOption = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.cssSelector("#__next > div.jsx-b04565efaf5ef842 > main > div > div > div.jsx-b04565efaf5ef842.content > div > div:nth-child(2) > div.jsx-2730439225.frame > div.jsx-2730439225.dropDownContainer > div.jsx-325976892 > div > div > div.jsx-325976892.options > div:nth-child(1)")));
                        specificOption.click();
                        System.out.println("✔ Signee Selected by User CSS");
                } catch (TimeoutException e1) {
                        System.out.println("ℹ Specific CSS not found, trying 'Approver'...");
                        try {
                                // Priority 1: Text 'Approver' (User suggestion)
                                WebElement approverOption = wait.until(ExpectedConditions.elementToBeClickable(
                                                By.xpath("//div[contains(text(),'Approver')]")));
                                // Use JS Click to avoid ElementClickInterceptedException
                                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", approverOption);
                                System.out.println("✔ 'Approver' Signee Selected (JS Click)");
                        } catch (TimeoutException e2) {
                                System.out.println("ℹ 'Approver' text not found, trying 'Ashok'...");
                                try {
                                        // Priority 2: Text 'Ashok' (Logged in user)
                                        WebElement ashokOption = wait.until(ExpectedConditions.elementToBeClickable(
                                                        By.xpath("//div[contains(text(),'Ashok')]")));
                                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
                                                        ashokOption);
                                        System.out.println("✔ 'Ashok' Signee Selected (JS Click)");
                                } catch (TimeoutException e3) {
                                        System.out.println("ℹ 'Ashok' text not found, trying generic options...");
                                        // Priority 3: First available option using generic classes
                                        try {
                                                WebElement firstOption = wait
                                                                .until(ExpectedConditions.elementToBeClickable(
                                                                                By.xpath("(//div[contains(@class,'option') or contains(@class,'dropdown_item')])[1]")));
                                                firstOption.click();
                                                System.out.println("✔ Generic Option Selected");
                                        } catch (Exception e4) {
                                                System.out.println(
                                                                "⚠ Failed to select ANY Signee. Save will likely fail.");
                                        }
                                }
                        }
                }
                // --------------------------------
                // SAVE TEMPLATE
                // --------------------------------
                WebElement saveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[text()='Save']")));
                saveBtn.click();
                System.out.println("✔ Template Saved");

                Thread.sleep(3000);

                // --------------------------------
                // GO BACK TO DASHBOARD
                // --------------------------------
                WebElement backToDash = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[text()='Go back to Dashboard']")));
                backToDash.click();
                System.out.println("✔ Returned to Dashboard");

                Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
                System.out.println("✔ Dashboard Validation Passed");

                // --------------------------------
                // VERIFY TEMPLATE EXISTS
                // --------------------------------
                driver.get("https://hire.vasitum.com/offerlettertemplates");
                Thread.sleep(2500);

                List<WebElement> templateNames = driver.findElements(
                                By.xpath("//span[contains(text(),'" + templateName + "')]"));

                Assert.assertTrue(templateNames.size() > 0, "❌ Template NOT found in list!");
                System.out.println("✔ Template Found In List: " + templateName);
        }

        @AfterMethod
        public void teardown() {
                if (driver != null) {
                        driver.quit();
                }
        }
}
