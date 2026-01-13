package com.vasitum.automation.pages;

import org.openqa.selenium.WebDriver;

public class DashboardPage {
    private WebDriver driver;

    // Hardcoding URLs for now based on user's snippet, but could be config driven
    private static final String POST_JOB_URL = "https://hire.vasitum.com/job/create";
    private static final String DRAFT_JOBS_URL = "https://hire.vasitum.com/draftjobs?sortBy=DESC&page=0";
    private static final String MY_JOBS_URL = "https://hire.vasitum.com/myjobs";

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToPostJob() {
        driver.get(POST_JOB_URL);
    }

    public void goToDraftJobs() {
        driver.get(DRAFT_JOBS_URL);
    }

    public void goToMyJobs() {
        driver.get(MY_JOBS_URL);
    }
}
