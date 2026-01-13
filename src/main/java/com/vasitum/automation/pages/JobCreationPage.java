package com.vasitum.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class JobCreationPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // --- Locators ---

    @FindBy(xpath = "//input[@placeholder='Enter the Job Title']")
    private WebElement jobTitleInput;

    @FindBy(xpath = "//input[@placeholder='Enter Min']")
    private WebElement salaryMinInput;

    @FindBy(xpath = "//input[@placeholder='Enter Max']")
    private WebElement salaryMaxInput;

    @FindBy(css = ".tiptap.ProseMirror")
    private WebElement descriptionEditor;

    @FindBy(xpath = "//div[text()='Auto Generate'] | //div[contains(text(),'Auto Generate')][contains(@class,'button')]")
    private WebElement autoGenerateBtn;

    @FindBy(xpath = "//input[@placeholder='Enter Job ID']")
    private WebElement jobIdInput;

    // The logic is in the method, but keeping this locator if needed elsewhere,
    // though the method uses local By.xpath as requested.
    @FindBy(xpath = "//button[contains(.,'Save Draft')] | //div[contains(.,'Save Draft')]")
    private WebElement saveDraftBtn;

    @FindBy(xpath = "//div[contains(.,'Next') and contains(@class,'button')]")
    private WebElement nextBtn;

    @FindBy(xpath = "//div[contains(.,'Post without screening') and contains(@class,'button')]")
    private WebElement postWithoutScreeningBtn;

    @FindBy(xpath = "//div[contains(.,'Post') and contains(@class,'button')]")
    private WebElement finalPostBtn;

    @FindBy(xpath = "//div[contains(.,'Import from previous jobs') and contains(@class,'button')]")
    private WebElement importPreviousBtn;

    @FindBy(xpath = "//div[contains(.,'Add') and contains(@class,'button')]")
    private WebElement addImportBtn;

    @FindBy(xpath = "//div[contains(.,'Add screening round') and contains(@class,'btn-name')]")
    private WebElement addScreeningRoundBtn;

    @FindBy(xpath = "//div[contains(.,'Generate with AI') and contains(@class,'btn-section')]")
    private WebElement generateAIBtn;

    @FindBy(xpath = "//div[text()='Add'] | //div[contains(@class,'btn-container') and contains(.,'Add')]")
    private WebElement addQuestionsBtn;

    @FindBy(xpath = "//div[contains(.,'Publish Job') and contains(@class,'btn-name')]")
    private WebElement publishJobBtn;

    public JobCreationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // --- Actions ---

    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    private void safeClick(WebElement el) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(el));
            el.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", el);
        }
    }

    public void fillJobDetails(String title, String dept, String industry, String loc, String mode, String empType) {
        wait.until(ExpectedConditions.visibilityOf(jobTitleInput)).clear();
        jobTitleInput.sendKeys(title);

        selectOptionByClickingInput("Department", dept);
        selectOptionByClickingInput("Industry", industry);
        selectOptionByClickingInput("Job Location", loc);
        selectOptionByClickingInput("Work Mode", mode);
        selectOptionByClickingInput("Employment Type", empType);

        // Experience
        try {
            WebElement expMin = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[contains(text(),'Experience')]//following::input[1]")));
            expMin.sendKeys("2");
            WebElement expMax = driver.findElement(
                    By.xpath("//div[contains(text(),'Experience')]//following::input[2]"));
            expMax.sendKeys("4");
        } catch (Exception e) {
            System.out.println("⚠ Experience fields not found");
        }

        selectOptionByClickingInput("Notice Period", "1");
        selectOptionByClickingInput("Salary Frequency", "Monthly");
        selectOptionByClickingInput("Education Level", "Diploma");
        selectOptionByClickingInput("Currency", "Canadian Dollar");
    }

    public void fillSalary(String min, String max) {
        wait.until(ExpectedConditions.visibilityOf(salaryMinInput));
        salaryMinInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), min);
        salaryMaxInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), max);
    }

    public void fillDescriptionAndGenerate(String descriptionText) {
        wait.until(ExpectedConditions.visibilityOf(descriptionEditor)).click();
        descriptionEditor.sendKeys(descriptionText);
        sleep(2000);

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", autoGenerateBtn);
        safeClick(autoGenerateBtn);
        sleep(8000); // Allow generation
    }

    public String getJobId() {
        WebElement input = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Job ID']")));
        String id = input.getAttribute("value");
        if (id == null || id.isEmpty()) {
            throw new RuntimeException("Job ID was empty in the input field!");
        }
        return id;
    }

    public void saveDraft() {
        try {
            WebElement saveDraftBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(
                            "#__next > main > div.sc-1ebf5a05-0.bJKltv > div > div > div.jsx-4294477224.form-container.width-section > div.jsx-2a50d17b7fad7f51.flex.btn-container.btn-gap > div.jsx-2147724526.button.flex > div")));
            js.executeScript("arguments[0].scrollIntoView({block:'center'});", saveDraftBtn);
            safeClick(saveDraftBtn);
            System.out.println("✔ Save Draft clicked");

            // Wait a bit for the server to process the save
            sleep(10000);
        } catch (Exception e) {
            Assert.fail("⚠ Save Draft button not found or not clickable");
        }
    }

    public void importFromPreviousJob() {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", importPreviousBtn);
        safeClick(importPreviousBtn);
        sleep(2000);

        // Select first job
        WebElement firstJobItem = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class,'item') and contains(@title,'(') and contains(@title,')')]")));
        safeClick(firstJobItem);
        sleep(1000);

        safeClick(addImportBtn);
        sleep(5000);
    }

    public void clickNext() {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", nextBtn);
        safeClick(nextBtn);
        sleep(3000);
    }

    public void postWithoutScreening() {
        clickNext();
        safeClick(postWithoutScreeningBtn);
        sleep(3000);
        safeClick(finalPostBtn);
        sleep(10000);
    }

    public void addScreeningAndPublish() {
        clickNext();
        safeClick(addScreeningRoundBtn);
        sleep(3000);
        safeClick(generateAIBtn);
        sleep(10000); // Wait for AI
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", addQuestionsBtn);
        safeClick(addQuestionsBtn);
        sleep(4000);

        js.executeScript("arguments[0].scrollIntoView({block:'center'});", publishJobBtn);
        safeClick(publishJobBtn);
        sleep(3000);

        safeClick(finalPostBtn);
        sleep(10000);
    }

    // --- Complex Helper ---

    public void selectOptionByClickingInput(String labelText, String valueToSelect) {
        System.out.println("Selecting '" + valueToSelect + "' for '" + labelText + "'");
        try {
            WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//*[contains(text(),'" + labelText + "')]/following::input[1]")));
            try {
                input.click();
            } catch (Exception e) {
                js.executeScript("arguments[0].click()", input);
            }
            sleep(500);

            input.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
            input.sendKeys(valueToSelect);
            sleep(2000);

            String optionXPath = String.format(
                    "//div[contains(@class,'option') and text()='%s'] | //li[contains(@class,'option') and text()='%s'] | //div[text()='%s'] | //*[contains(@title, '%s')]",
                    valueToSelect, valueToSelect, valueToSelect, valueToSelect);
            WebElement optionToSelect = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath(optionXPath)));
            safeClick(optionToSelect);
        } catch (Exception e) {
            System.out.println(
                    "⚠ Failed to select '" + valueToSelect + "' for '" + labelText + "'. Error: " + e.getMessage());
        }
    }
}
