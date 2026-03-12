package com.vasitum.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            createInstance();
        }
        return extent;
    }

    private static void createInstance() {
        // Generate dynamic report path based on current date
        String dateName = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        String reportDir = System.getProperty("user.dir") + "/test-output/reports/" + dateName;

        // Ensure directory exists
        java.io.File directory = new java.io.File(reportDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String reportPath = reportDir + "/AutomationReport.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);

        sparkReporter.config().setTheme(Theme.DARK); // Switch to Dark Theme for premium look
        sparkReporter.config().setDocumentTitle("Vasitum Automation Report");
        sparkReporter.config().setReportName("Vasitum Automation Dashboard");
        sparkReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");

        // Custom CSS for Vasitum Branding - Dark Theme Compatible
        String css = ".nav-wrapper { background: linear-gradient(to right, #141E30, #243B55) !important; }" +
                ".brand-logo { color: #fff !important; font-weight: 700 !important; font-size: 1.5rem !important; }" +
                ".logo { background-image: none !important; display: flex !important; align-items: center !important; height: 100% !important; padding-left: 10px !important; margin-right: 30px !important; }"
                +
                ".card { background-color: #2F323E !important; box-shadow: 0 4px 6px rgba(0,0,0,0.3) !important; border: 1px solid #3E4251 !important; }"
                +
                ".card-header { background-color: #262933 !important; border-bottom: 1px solid #3E4251 !important; }" +
                ".badge-success { background-color: #00b894 !important; color: white !important; }" +
                ".badge-fail { background-color: #d63031 !important; color: white !important; }" +
                ".badge-warning { background-color: #fdcb6e !important; color: black !important; }" +
                ".badge-info { background-color: #0984e3 !important; color: white !important; }" +
                "body, .table { color: #dfe6e9 !important; font-family: 'Segoe UI', Roboto, Helvetica, Arial, sans-serif; }"
                +
                ".table thead th { border-bottom: 2px solid #3E4251 !important; color: #b2bec3 !important; }" +
                ".search-box, .search-input { display: none !important; }"; // Hide Search Bar

        sparkReporter.config().setCss(css);

        // JS to replace logo text and default to dashboard view if possible, or enhance
        // existing graphs
        String js = "var logo = document.getElementsByClassName('logo')[0];" +
                "if(logo) {" +
                "   logo.innerHTML = '<span style=\"font-family: sans-serif; letter-spacing: 1px; margin-top: 4px; display: inline-block;\">VASITUM</span>';"
                +
                "   logo.style.backgroundImage = 'none';" +
                "   logo.style.color = '#fff';" +
                "   logo.style.fontSize = '1.4rem';" +
                "   logo.style.fontWeight = '700';" +
                "}" +
                "setTimeout(function() { " +
                "  var charts = document.getElementsByClassName('card-body');" + // Attempt to make charts larger if
                                                                                 // needed
                "}, 1000);";

        sparkReporter.config().setJs(js);

        // Enable Charts View explicitly if supported by the version, essentially
        // ensuring Dashboard is prominent
        // For Extent 5+, the dashboard view already contains graphs. We focus on clear
        // visibility.

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Vasitum QA Team");
    }
}
