package com.vasitum.automation.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public class ReportArchiver {

    private static final String REPORT_ROOT = System.getProperty("user.dir") + "/test-output/reports";
    private static final String ARCHIVE_DIR = REPORT_ROOT + "/archive";
    private static final String INDEX_FILE = REPORT_ROOT + "/index.html";

    public static void archiveReport() {
        log("Starting archiveReport...");
        try {
            // 1. Identify source report
            log("REPORT_ROOT: " + REPORT_ROOT);
            String dateName = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Path sourcePath = Paths.get(REPORT_ROOT, dateName, "AutomationReport.html");

            if (!Files.exists(sourcePath)) {
                System.out.println("No report found to archive at: " + sourcePath);
                log("No report found at: " + sourcePath);
                return;
            }

            // 2. Prepare archive destination
            File archiveDir = new File(ARCHIVE_DIR);
            if (!archiveDir.exists()) {
                archiveDir.mkdirs();
            }

            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String archiveName = "AutomationReport_" + timestamp + ".html";
            Path destPath = Paths.get(ARCHIVE_DIR, archiveName);

            // 3. Copy file
            Files.copy(sourcePath, destPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Report archived to: " + destPath);
            log("Report successfully archived to: " + destPath);

        } catch (Exception e) {
            log("Exception in archiveReport: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void log(String msg) {
        try (FileWriter fw = new FileWriter("archiver_debug.log", true)) {
            fw.write(new Date() + ": " + msg + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateIndex() {
        try {
            File archiveDir = new File(ARCHIVE_DIR);
            if (!archiveDir.exists() || !archiveDir.isDirectory()) {
                System.out.println("Archive directory not provided for indexing.");
                return;
            }

            File[] reports = archiveDir.listFiles((dir, name) -> name.endsWith(".html"));
            if (reports == null)
                return;

            // Sort by name (which includes timestamp) descending
            Arrays.sort(reports, Comparator.comparing(File::getName).reversed());

            StringBuilder html = new StringBuilder();
            html.append("<!DOCTYPE html>\n");
            html.append("<html lang='en'>\n");
            html.append("<head>\n");
            html.append("<meta charset='UTF-8'>\n");
            html.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>\n");
            html.append("<title>Vasitum Automation Reports</title>\n");
            html.append("<style>\n");
            html.append(
                    "body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #1e1e2f; color: #fff; margin: 0; padding: 20px; }\n");
            html.append("h1 { text-align: center; color: #00d2d3; margin-bottom: 30px; }\n");
            html.append(
                    ".container { max-width: 900px; margin: 0 auto; background: #27293d; padding: 30px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.3); }\n");
            html.append("ul { list-style-type: none; padding: 0; }\n");
            html.append(
                    "li { padding: 15px 20px; border-bottom: 1px solid #444; display: flex; justify-content: space-between; align-items: center; transition: all 0.2s; }\n");
            html.append("li:hover { background-color: #3b3e4f; transform: translateX(5px); }\n");
            html.append("li:last-child { border-bottom: none; }\n");
            html.append(
                    "a { color: #4facfe; text-decoration: none; font-weight: bold; padding: 8px 16px; border: 1px solid #4facfe; border-radius: 4px; transition: all 0.2s; }\n");
            html.append(
                    "a:hover { background-color: #4facfe; color: #fff; box-shadow: 0 0 10px rgba(79, 172, 254, 0.4); }\n");
            html.append(".timestamp { font-size: 1.1em; color: #e0e0e0; display: flex; align-items: center; }\n");
            html.append(".timestamp::before { content: '📄'; margin-right: 10px; }\n");
            html.append(
                    ".refresh-btn { display: block; width: fit-content; margin: 20px auto; padding: 10px 20px; background: #e14eca; color: white; border: none; border-radius: 5px; cursor: pointer; font-size: 1em; text-transform: uppercase; letter-spacing: 1px; }\n");
            html.append(".refresh-btn:hover { background: #c542b3; }\n");
            html.append("</style>\n");
            html.append("<script>function refresh() { location.reload(); }</script>\n");
            html.append("</head>\n");
            html.append("<body>\n");
            html.append("<div class='container'>\n");
            html.append("<h1>Automation Test Reports</h1>\n");
            html.append("<button class='refresh-btn' onclick='refresh()'>Refresh List</button>\n");
            html.append("<ul>\n");

            for (File report : reports) {
                String name = report.getName();
                String link = "archive/" + name;
                String displayDate = name.replace("AutomationReport_", "").replace(".html", "").replace("_", " ");

                html.append("<li>");
                html.append("<span class='timestamp'>").append(displayDate).append("</span>");
                html.append("<a href='").append(link).append("' target='_blank'>View Report</a>");
                html.append("</li>\n");
            }

            html.append("</ul>\n");
            html.append("</div>\n");
            html.append("</body>\n");
            html.append("</html>");

            FileWriter writer = new FileWriter(INDEX_FILE);
            writer.write(html.toString());
            writer.close();
            System.out.println("Index file updated at: " + INDEX_FILE);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
