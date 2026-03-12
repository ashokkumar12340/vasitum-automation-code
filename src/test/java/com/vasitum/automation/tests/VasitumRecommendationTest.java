package com.vasitum.automation.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.testng.annotations.Test;

public class VasitumRecommendationTest {

    @Test
    public void testRecommendationFlow() {
        try {
            System.out.println("Step 1: Fetching Candidate Recommendations via API...");

            // API Payload
            String jsonPayload = """
                    {
                      "companyId":"6732fdbd231fb33eed61e40e",
                      "jobId":"696e217b824d64404d37a959",
                      "limit":"20",
                      "pageNo":1,
                      "vasitumUser":false
                    }
                    """;

            // Call API and get response
            String responseBody = callVasitumApi(jsonPayload);

            // Step 2: Parse and Process Skill Data
            processSkillData(responseBody);

        } catch (Exception e) {
            // Comprehensive Exception Handling
            System.err.println("TEST FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Logic to process Matched and Missing skills from the API JSON
     */
    public static void processSkillData(String json) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);

        // Navigate to candidates array (root level)
        JsonNode candidates = root.path("candidates");

        if (candidates.isMissingNode() || !candidates.isArray()) {
            throw new Exception("Invalid API response structure: 'candidates' array not found.");
        }

        System.out.println("\n--- SKILL ANALYSIS REPORT ---");
        for (JsonNode candidate : candidates) {
            String firstName = candidate.path("firstName").asText("");
            String lastName = candidate.path("lastName").asText("");
            String name = (firstName + " " + lastName).trim();
            if (name.isEmpty())
                name = "Unknown Candidate";

            // Extract Total Skills
            JsonNode totalSkills = candidate.path("skills");
            int totalSkillsCount = totalSkills.size();

            // Extract Matched Skills
            JsonNode matched = candidate.path("matchedSkills");
            int matchCount = matched.size();

            // Extract Missing Skills
            JsonNode missing = candidate.path("missingSkills");
            int missingCount = missing.size();

            System.out.println("Candidate: " + name);
            System.out.println("  📋 Total Skills (" + totalSkillsCount + "): " + totalSkills.toString());
            System.out.println("  ✅ Matched (" + matchCount + "): " + matched.toString());
            System.out.println("  ❌ Missing (" + missingCount + "): " + missing.toString());
            System.out.println("------------------------------------------");
        }
    }

    /**
     * API implementation using HttpClient
     */
    public static String callVasitumApi(String payload) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://core.vasitum.com/api/v1/candidates/recommendations"))
                .header("content-type", "application/json")
                .header("accept", "*/*")
                .header("x-maven-rest-auth-token", "696e20b1824d64404d37a93e")
                .header("x-workspace-id", "6732fdbd231fb33eed61e40e")
                .header("user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                    "API error! Status Code: " + response.statusCode() + " Body: " + response.body());
        }

        return response.body();
    }
}
