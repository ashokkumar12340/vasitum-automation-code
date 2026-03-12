package com.vasitum.automation.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class VasitumApiTest {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();

        try {
            // 1. Define the API Request Body
            String jsonPayload = """
                    {
                      "companyId": "6732fdbd231fb33eed61e40e",
                      "jobId": "696e217b824d64404d37a959",
                      "limit": "20",
                      "pageNo": 1,
                      "vasitumUser": false
                    }
                    """;

            // 2. Call the Third-Party API
            String apiResponse = callRecommendationApi(jsonPayload);
            System.out.println("API Success! Response received.");

            // 3. Selenium Logic (Example: Navigate based on API success)
            driver.get("https://vasitum.com");
            // You can now parse 'apiResponse' and use data to find elements

        } catch (Exception e) {
            System.err.println("CRITICAL ERROR in Test Flow: " + e.getMessage());
        } finally {
            driver.quit();
        }
    }

    public static String callRecommendationApi(String body) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://core.vasitum.com/api/v1/candidates/recommendations"))
                    .header("accept", "*/*")
                    .header("content-type", "application/json")
                    .header("x-maven-rest-auth-token", "696e20b1824d64404d37a93e")
                    .header("x-workspace-id", "6732fdbd231fb33eed61e40e")
                    .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Exception Handling for HTTP Status Codes
            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return response.body();
            } else {
                throw new RuntimeException("API Request Failed with Status: " + response.statusCode() +
                        " Response: " + response.body());
            }

        } catch (java.io.IOException e) {
            throw new Exception("Network/IO Error during API call: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new Exception("API Call was interrupted: " + e.getMessage());
        }
    }
}
