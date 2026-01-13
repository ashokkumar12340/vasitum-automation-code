package com.vasitum.automation.tests;

import com.vasitum.automation.base.BaseTest;
import com.vasitum.automation.pages.DashboardPage;
import com.vasitum.automation.pages.JobCreationPage;
import com.vasitum.automation.pages.LoginPage;
import com.vasitum.automation.pages.MyJobsPage;
import org.testng.annotations.Test;

public class PostJobTest extends BaseTest {

    private void performLogin() {
        LoginPage loginPage = new LoginPage(driver);
        // Using hardcoded credentials as per user's snippet, for verify specific user
        // flows
        driver.get("https://vasitum.com/login");
        loginPage.login("ashok.kumar@vasitum.in", "000000");
    }

    private void refreshJobCreationPage() {
        try {
            // Wait for URL to be correct before refreshing?
            // Assuming goToPostJob lands us on /job/create
            Thread.sleep(2000);
            for (int i = 0; i < 2; i++) {
                driver.navigate().refresh();
                Thread.sleep(3000); // Wait for reload
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test(description = "Verify posting a job manually and saving as draft")
    public void postJob_manual_saveDraft_and_verify() {
        performLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.goToPostJob();
        refreshJobCreationPage();

        JobCreationPage createJob = new JobCreationPage(driver);
        createJob.fillJobDetails("Automation QA", "Engineering", "Accounting", "Noida", "Hybrid", "Contract");
        createJob.fillSalary("40000", "80000");

        // Description Logic from user snippet
        String shortText = "We are looking for a Java Selenium Automation Engineer. ";
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            longDescription.append(shortText);
        }

        createJob.fillDescriptionAndGenerate(longDescription.toString());

        String jobId = createJob.getJobId();
        System.out.println("✔ Captured Job ID: " + jobId);

        createJob.saveDraft();

        dashboard.goToDraftJobs();

        MyJobsPage myJobs = new MyJobsPage(driver);
        myJobs.verifyJobIdInDrafts(jobId);
    }

    @Test(description = "Verify importing a previous job and saving as draft")
    public void postJob_importPrevious_saveDraft_and_verify() {
        performLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.goToPostJob();
        refreshJobCreationPage();

        JobCreationPage createJob = new JobCreationPage(driver);
        createJob.importFromPreviousJob();

        String jobId = createJob.getJobId();
        System.out.println("✔ Captured Job ID: " + jobId);

        createJob.saveDraft();

        dashboard.goToDraftJobs();

        MyJobsPage myJobs = new MyJobsPage(driver);
        myJobs.verifyJobIdInDrafts(jobId);
    }

    @Test(description = "Verify posting a job without screening")
    public void postJob_withoutScreening_and_verify() {
        performLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.goToPostJob();
        refreshJobCreationPage();

        JobCreationPage createJob = new JobCreationPage(driver);
        createJob.fillJobDetails("Selenium Tester - AutoGen & Post", "Engineering", "Accounting", "Noida", "Hybrid",
                "Contract");
        createJob.fillSalary("40000", "80000");

        // Description Logic from user snippet
        String shortText = "We are hiring an Automation Engineer skilled in Java and Selenium. ";
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            longDescription.append(shortText);
        }
        longDescription.append(
                "This is the minimum 75+ character description required to trigger the Auto Generate function.");

        createJob.fillDescriptionAndGenerate(longDescription.toString());

        String jobId = createJob.getJobId();
        System.out.println("✔ Captured Job ID: " + jobId);

        createJob.postWithoutScreening();

        dashboard.goToMyJobs();

        MyJobsPage myJobs = new MyJobsPage(driver);
        myJobs.verifyJobIdInMyJobs(jobId);
    }

    @Test(description = "Verify posting a job with API screening and publish")
    public void postJob_withScreening_and_publish() {
        performLogin();

        DashboardPage dashboard = new DashboardPage(driver);
        dashboard.goToPostJob();
        refreshJobCreationPage();

        JobCreationPage createJob = new JobCreationPage(driver);
        createJob.fillJobDetails("Automation Lead - With AI Screening", "Engineering", "Accounting", "Noida", "Hybrid",
                "Contract");
        createJob.fillSalary("80000", "120000");

        // Description Logic from user snippet
        String shortText = "Senior Automation Lead position requiring expertise in Java, Selenium, and CI/CD pipelines. ";
        StringBuilder longDescription = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            longDescription.append(shortText);
        }
        longDescription.append("This initial seed text is used to trigger the Auto Generate feature successfully.");

        createJob.fillDescriptionAndGenerate(longDescription.toString());

        String jobId = createJob.getJobId();
        System.out.println("✔ Captured Job ID: " + jobId);

        createJob.addScreeningAndPublish();

        dashboard.goToMyJobs();

        MyJobsPage myJobs = new MyJobsPage(driver);
        myJobs.verifyJobIdInMyJobs(jobId);
    }
}
