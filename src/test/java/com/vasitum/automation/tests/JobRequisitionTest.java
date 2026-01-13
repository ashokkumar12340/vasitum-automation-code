package com.vasitum.automation.tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class JobRequisitionTest {

        WebDriver driver;
        WebDriverWait wait;

        @BeforeMethod
        public void setup() {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                wait = new WebDriverWait(driver, Duration.ofSeconds(35));
                driver.manage().window().maximize();
        }

        @Test
        public void testJobRequisition() throws InterruptedException {
                // Step 1 — Open login page
                driver.get("https://vasitum.com/login");
                System.out.println("Page URL: " + driver.getCurrentUrl());
                System.out.println("Page title: " + driver.getTitle());

                // Step 2 — Enter email
                WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//input[@placeholder='example@email.com']")));
                emailField.sendKeys("ashok.kumar+7@vasitum.com");
                emailField.sendKeys(Keys.ENTER);
                System.out.println("Email entered and submitted.");

                // Step 3 — Enter password
                WebElement passwordField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//input[@placeholder='Enter Password']")));
                passwordField.sendKeys("000000");
                passwordField.sendKeys(Keys.ENTER);
                System.out.println("Password entered and submitted.");

                // Step 4 — Wait for login to complete and verify dashboard URL
                wait.until(ExpectedConditions.urlContains("https://hire.vasitum.com/dashboard"));
                System.out.println("Successfully logged in and redirected to Dashboard.");

                // Step 5 — Requisition button
                WebElement requisitionButton = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-971386168.flex1.section-1.gap > div.jsx-971386168.flex.gap.section-1-1 > div > div.jsx-1751245973.action.btn-flex.gap.height-section > div.jsx-1751245973.btn.flex.flex-col.gap-1.width-section > div:nth-child(1) > div")));
                requisitionButton.click();
                System.out.println("Requisition button clicked.");

                Thread.sleep(2000);

                // switching the window
                String originalWindow = driver.getWindowHandle();
                for (String windowHandle : driver.getWindowHandles()) {
                        if (!windowHandle.equals(originalWindow)) {
                                driver.switchTo().window(windowHandle);
                                break;
                        }
                }

                // Wait for the Job Create page URL
                wait.until(ExpectedConditions.urlContains("/job/create"));
                System.out.println("Job Create page loaded.");
                Thread.sleep(700);

                // Enter Job Title
                WebElement jobTitleField = new WebDriverWait(driver, Duration.ofSeconds(20))
                                .until(ExpectedConditions.visibilityOfElementLocated(
                                                By.xpath("//input[@placeholder= 'Enter the Job Title']")));
                jobTitleField.sendKeys("Java Developer");
                System.out.println("Job Title entered: Java Developer");
                Thread.sleep(500);

                // Step A — click the input to open dropdown (Department)
                WebElement dropdownInput = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(1) > " +
                                                "div.jsx-4294477224.flex-between.input-section-2 > div:nth-child(1) > "
                                                +
                                                "div.jsx-2482785486.relative-container > input")));
                dropdownInput.click();
                Thread.sleep(500);

                // Step B — select option 28
                WebElement option28 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(1) > " +
                                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                                "div.jsx-3739095401.typography.wrapper-container > " +
                                                "div.jsx-3739095401.options-container.option-container-gap > div:nth-child(28)")));
                option28.click();
                System.out.println("Department Selected");
                Thread.sleep(500);

                // Step — Click the 2nd dropdown input (Industry)
                WebElement secondDropdownInput = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > "
                                                + "div.jsx-4294477224.form-container.width-section > "
                                                + "div.jsx-4294477224.section.typography > div:nth-child(1) > "
                                                + "div.jsx-4294477224.flex-between.input-section-2 > div:nth-child(2) > "
                                                + "div.jsx-2482785486.relative-container > input")));
                secondDropdownInput.click();
                System.out.println("2nd dropdown input clicked.");

                // Step — Select option 21
                WebElement option21 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > "
                                                + "div.jsx-4294477224.form-container.width-section > "
                                                + "div.jsx-4294477224.section.typography > div:nth-child(1) > "
                                                + "div.jsx-4294477224.flex-between.input-section-2 > "
                                                + "div.jsx-3739095401.typography.wrapper-container > "
                                                + "div.jsx-3739095401.options-container.option-container-gap > "
                                                + "div:nth-child(21)")));
                option21.click();
                System.out.println("Industry Selected ");
                Thread.sleep(500);

                // Step — Click the input field in section 2 (Work Mode)
                WebElement section2Input = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > "
                                                + "div.jsx-4294477224.form-container.width-section > "
                                                + "div.jsx-4294477224.section.typography > div:nth-child(2) > "
                                                + "div.jsx-4294477224.flex-between.input-section-1 > "
                                                + "div.jsx-2482785486.typography.wrapper-container > "
                                                + "div.jsx-2482785486.relative-container > input")));
                section2Input.click();
                System.out.println("Section 2 input clicked.");

                // Step — Select Option 1 from dropdown
                WebElement option1 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > "
                                                + "div.jsx-4294477224.form-container.width-section > "
                                                + "div.jsx-4294477224.section.typography > div:nth-child(2) > "
                                                + "div.jsx-4294477224.flex-between.input-section-1 > "
                                                + "div.jsx-3739095401.typography.wrapper-container > "
                                                + "div.jsx-3739095401.options-container.option-container-gap > "
                                                + "div:nth-child(1)")));
                option1.click();
                System.out.println("Work Mode Selected");
                Thread.sleep(500);

                // Step — Click the input field in section-2 input-section-2 (Employment Type)
                WebElement section2Input2 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(2) > " +
                                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                                "div.jsx-2482785486.typography.wrapper-container > " +
                                                "div.jsx-2482785486.relative-container > input")));
                section2Input2.click();

                // Step — Click the first option in dropdown
                WebElement option1Section2 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(2) > " +
                                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                                "div.jsx-3739095401.typography.wrapper-container > " +
                                                "div.jsx-3739095401.options-container.option-container-gap > div:nth-child(1)")));
                option1Section2.click();
                System.out.println("Done Employment Type Chooses.");
                Thread.sleep(500);

                // 1. Open the Job Location dropdown
                WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//input[@placeholder='Select / Search Location']")));
                dropdown.click();
                System.out.println("Dropdown opened");

                // 2. Click the option
                WebElement delhiOption = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("//div[@title='Delhi , Delhi , India']")));
                delhiOption.click();
                System.out.println("location selected");
                Thread.sleep(500);

                // Experience From
                WebElement experienceFrom = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("(//input[@placeholder='Eg : 00'])[1]")));
                experienceFrom.clear();
                experienceFrom.sendKeys("1");
                System.out.println("Experience from filled with 1");

                // Experience To
                WebElement experienceTo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("(//input[@placeholder='Eg : 00'])[2]")));
                experienceTo.clear();
                experienceTo.sendKeys("2");
                System.out.println("Experience To filled with 2");
                Thread.sleep(500);

                // Select Currency/Type (assumed from css)
                WebElement inputField = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-4294477224.form-container.width-section > div.jsx-4294477224.section.typography > div:nth-child(3) > div.jsx-4294477224.flex-between.input-section-1 > div.jsx-3260046002.typography.wrapper-container > div.jsx-3260046002.relative-container > input")));
                inputField.click();
                System.out.println("Input field clicked");

                WebElement firstOption = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(3) > " +
                                                "div.jsx-4294477224.flex-between.input-section-1 > " +
                                                "div.jsx-2276534645.typography.wrapper-container > " +
                                                "div.jsx-2276534645.options-container.option-container-gap > " +
                                                "div:nth-child(1)")));
                firstOption.click();
                System.out.println("First option selected successfully");
                Thread.sleep(500);

                WebElement inputfield = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector(
                                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-4294477224.form-container.width-section > div.jsx-4294477224.section.typography > div:nth-child(3) > div.jsx-4294477224.flex-between.input-section-2 > div > div.jsx-3260046002.relative-container > input")));
                inputfield.click();
                System.out.println("Input field clicked");

                // Select 5th option
                WebElement option5 = wait.until(ExpectedConditions.elementToBeClickable(
                                By.xpath("(//div[contains(@class,'options-container')]//div)[5]")));
                option5.click();
                System.out.println("5th option selected using stable XPath");
                Thread.sleep(500);

                // STEP 1: Click the input field
                By inputField1 = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-4294477224.section.typography > div:nth-child(4) > " +
                                "div.jsx-4294477224.flex-between.input-section-1 > " +
                                "div:nth-child(1) > div.jsx-3260046002.relative-container > input");
                WebElement input = wait.until(ExpectedConditions.elementToBeClickable(inputField1));
                input.click();
                System.out.println("Input field clicked");

                // STEP 2: Select the first option
                By firstOption1Part2 = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-4294477224.section.typography > div:nth-child(4) > " +
                                "div.jsx-4294477224.flex-between.input-section-1 > " +
                                "div.jsx-2276534645.typography.wrapper-container > " +
                                "div.jsx-2276534645.options-container.option-container-gap > " +
                                "div:nth-child(1)");
                WebElement option = wait.until(ExpectedConditions.elementToBeClickable(firstOption1Part2));
                option.click();
                System.out.println("First option selected");
                Thread.sleep(500);

                By inputField2 = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-4294477224.section.typography > div:nth-child(4) > " +
                                "div.jsx-4294477224.flex-between.input-section-1 > " +
                                "div:nth-child(2) > div.jsx-3260046002.relative-container > input");
                WebElement input2 = wait.until(ExpectedConditions.elementToBeClickable(inputField2));
                input2.click();
                System.out.println("Second input field clicked");

                By thirdOption = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-4294477224.section.typography > div:nth-child(4) > " +
                                "div.jsx-4294477224.flex-between.input-section-1 > " +
                                "div.jsx-2276534645.typography.wrapper-container > " +
                                "div.jsx-2276534645.options-container.option-container-gap > " +
                                "div:nth-child(3)");
                WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(thirdOption));
                option2.click();
                System.out.println("3rd dropdown option selected");
                Thread.sleep(500);

                // Locate Min Salary
                WebElement minSalaryInput = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(4) > " +
                                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                                "div.jsx-4294477224.experience-section.flex-between > " +
                                                "div.jsx-730656165.wrapper-div.typography > input")));
                minSalaryInput.clear();
                minSalaryInput.sendKeys("20000");
                System.out.println("Minimum salary field filled with 20000");
                Thread.sleep(300);

                // Step: Fill maximum salary
                WebElement maxSalaryInput = wait.until(ExpectedConditions.elementToBeClickable(
                                By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-4294477224.form-container.width-section > " +
                                                "div.jsx-4294477224.section.typography > div:nth-child(4) > " +
                                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                                "div.jsx-4294477224.experience-section.flex-between > " +
                                                "div.jsx-2813645991.wrapper-div.typography > input")));
                maxSalaryInput.clear();
                maxSalaryInput.sendKeys("40000");
                System.out.println("Maximum salary field filled with 40000");
                Thread.sleep(500);

                // STEP 1: Click date picker button
                By datePickerButton = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-4294477224.section.typography > div:nth-child(5) > " +
                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                "div > div.react-datepicker-wrapper > div > button > div");
                WebElement dateButton = wait.until(ExpectedConditions.elementToBeClickable(datePickerButton));
                dateButton.click();
                System.out.println("Date picker opened");

                // STEP 2: Click date (30th)
                By day30 = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-4294477224.section.typography > div:nth-child(5) > " +
                                "div.jsx-4294477224.flex-between.input-section-2 > " +
                                "div > div.react-datepicker__tab-loop > " +
                                "div.react-datepicker-popper > div > div > " +
                                "div.react-datepicker__month-container > " +
                                "div.react-datepicker__month > div:nth-child(5) > " +
                                "div.react-datepicker__day.react-datepicker__day--030");
                WebElement date1 = wait.until(ExpectedConditions.elementToBeClickable(day30));
                date1.click();
                System.out.println("Date 30 selected");
                Thread.sleep(500);

                // Editor Field
                By editorField = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-266213263.editor-container.width-section.flex-start.flex-col.gap.typography > "
                                +
                                "div.jsx-266213263.editor-section.width-section > " +
                                "div.jsx-3551623135.editor-div.width-section > " +
                                "div:nth-child(3) > div");
                WebElement editor = wait.until(ExpectedConditions.elementToBeClickable(editorField));
                editor.click();
                System.out.println("Editor field clicked");

                // STEP 2: Type content (>100 words as requested)
                String longDescription = "We are seeking a highly skilled Java Developer to join our dynamic development team. "
                                +
                                "The ideal candidate will be responsible for designing, developing, and maintaining " +
                                "high-quality Java-based applications that meet business requirements and deliver exceptional "
                                +
                                "user experiences. You will collaborate closely with product managers, UI/UX designers, and "
                                +
                                "other developers to translate functional specifications into robust, scalable, and secure solutions.\\n\\n"
                                +

                                "Key responsibilities include writing clean, efficient, and reusable code while adhering to "
                                +
                                "industry best practices, performing code reviews, debugging issues, and optimizing application "
                                +
                                "performance. You will work with frameworks such as Spring Boot, Hibernate, JPA, and RESTful APIs, "
                                +
                                "as well as relational and NoSQL databases. Experience with microservices architecture, cloud "
                                +
                                "platforms (AWS, Azure, or GCP), and CI/CD pipelines will be a strong advantage.\\n\\n"
                                +

                                "The ideal candidate should possess strong problem-solving skills, a deep understanding of "
                                +
                                "object-oriented programming, and familiarity with Agile methodologies. You should be comfortable "
                                +
                                "working in a fast-paced environment and capable of managing multiple tasks while maintaining high "
                                +
                                "attention to detail.\\n\\n" +

                                "If you are passionate about building innovative Java applications and eager to grow within a "
                                +
                                "collaborative and tech-focused organization, we encourage you to apply.";

                editor.sendKeys(longDescription);
                System.out.println("Job description entered successfully (Length: " + longDescription.length() + ")");
                Thread.sleep(500);

                By submitButton = By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                "div.jsx-4294477224.form-container.width-section > " +
                                "div.jsx-266213263.editor-container.width-section.flex-start.flex-col.gap.typography > "
                                +
                                "div.jsx-266213263.editor-section.width-section > " +
                                "div.jsx-266213263.flex-end.btn > div");
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
                button.click();
                System.out.println("Automate_Generate Button clicked successfully");
                Thread.sleep(8000);

                // Next Button - using robust XPath
                try {
                        WebElement button2 = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//div[contains(text(),'Next') and contains(@class,'button')]")));
                        button2.click();
                        System.out.println("Next Button clicked successfully");
                } catch (TimeoutException e) {
                        System.out.println("Failed to click Next button with robust XPath. Trying CSS...");
                        WebElement button2 = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.cssSelector("#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-4294477224.form-container.width-section > div.jsx-2a50d17b7fad7f51.flex.btn-container.btn-gap > div.jsx-3541744815.button.flex")));
                        button2.click();
                }
                Thread.sleep(2000);

                // Raise without screening - using robust XPath
                try {
                        WebElement secondOption = wait.until(ExpectedConditions.elementToBeClickable(
                                        By.xpath("//div[contains(text(),'Raise without screening')]")));
                        secondOption.click();
                        System.out.println("Raise without screening option clicked successfully");
                } catch (Exception e) {
                        System.out.println("Failed to click Raise without screening. Dumping page source...");
                        System.out.println(driver.getPageSource());
                        throw e;
                }
                Thread.sleep(2000);

                // Final Button - using robust XPath
                // Fallback or strict? User used CSS. keeping user CSS as backup or just using
                // user CSS.
                // Actually sticking to user CSS for final button if it was specific, but let's
                // try to be safe.
                // User CSS: div.jsx-2198409523.button.flex inside popup.

                // Let's use the CSS from user but wrapped in try/catch or just leave it if
                // previous steps pass.
                // Actually, let's use the CSS provided by user for the final button as I don't
                // have a better one and didn't fail there.
                WebElement finalBtn = driver.findElement(By.cssSelector(
                                "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > " +
                                                "div.jsx-2858949808.popup-overlay > div > div > div > " +
                                                "div.jsx-3524772205.action-container.flex-end.btn-gap > " +
                                                "div.jsx-2198409523.button.flex"));
                finalBtn.click();

                System.out.println("Job raise Successfully");
                Thread.sleep(4000);
        }

        @AfterMethod
        public void teardown() {
                if (driver != null) {
                        driver.quit();
                }
        }
}
