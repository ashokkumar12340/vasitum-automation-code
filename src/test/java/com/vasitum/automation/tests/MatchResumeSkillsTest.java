package com.vasitum.automation.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;

public class MatchResumeSkillsTest {

    private static final String AUTH_TOKEN = "696e20b1824d64404d37a93e";
    private static final String WORKSPACE_ID = "67f2c7c987a1bb2ca13d2a10";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36";

    @Test
    public void testResumeSkillMatching() {
        try {
            // Step 1: Parse Document to get JD Skills
            System.out.println("Step 1: Parsing JD Document for Skills...");
            Set<String> jdSkills = parseJobDescription();
            System.out.println("JD Skills (" + jdSkills.size() + "): " + jdSkills);

            // Step 2: Fetch Applicants
            System.out.println("\nStep 2: Fetching Applicants...");
            JsonNode applicants = fetchApplicants();
            System.out.println("Fetched " + applicants.size() + " applicants.");

            // Step 3: Match Skills and Report
            System.out.println("\nStep 3: Matching Skills...");
            System.out.println("\n--- SKILL MATCH REPORT ---");

            for (JsonNode applicant : applicants) {
                String name = applicant.path("userName").asText("Unknown Name");
                if (name.equals("Unknown Name")) {
                    String first = applicant.path("firstName").asText("");
                    String last = applicant.path("lastName").asText("");
                    name = (first + " " + last).trim();
                }

                Set<String> applicantSkills = new HashSet<>();
                JsonNode skillsNode = applicant.path("skills");
                if (skillsNode.isArray()) {
                    for (JsonNode skill : skillsNode) {
                        applicantSkills.add(skill.asText().toLowerCase());
                    }
                }

                // Calculate Match
                Set<String> matchedSkills = new HashSet<>(jdSkills);
                matchedSkills.retainAll(applicantSkills); // Intersection

                System.out.println("Candidate: " + name);
                System.out.println("  📋 Total Skills (" + applicantSkills.size() + "): " + applicantSkills);
                System.out.println("  ✅ Matched (" + matchedSkills.size() + "): " + matchedSkills);
                System.out.println("  ❌ Missing (" + (jdSkills.size() - matchedSkills.size()) + "): "
                        + getMissingSkills(jdSkills, matchedSkills));
                System.out.println("------------------------------------------");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Set<String> getMissingSkills(Set<String> all, Set<String> matched) {
        Set<String> missing = new HashSet<>(all);
        missing.removeAll(matched);
        return missing;
    }

    private Set<String> parseJobDescription() throws Exception {
        String boundary = "----WebKitFormBoundaryCcd6pEZQuccCKxvr";
        String prompt = "<h2>Job Description: Sales Executive (Mid-Level)</h2><p><strong>Experience:</strong> 2–7 Years</p><p><strong>Location:</strong> [City, State / Remote]</p><p><strong>Industry:</strong> [Insert Industry, e.g., SaaS, Real Estate, Logistics]</p><h3>Role Summary</h3><p>We are looking for a results-oriented Sales Executive to drive revenue growth by identifying new business opportunities and managing the sales lifecycle from prospecting to closing. The ideal candidate has a proven track record of meeting targets and the ability to build long-term relationships with B2B/B2C clients.</p><h3>Key Responsibilities</h3><ul><li><p><strong>Lead Generation:</strong> Proactively identify and qualify new business leads through cold calling, networking, and social selling.</p></li><li><p><strong>Pipeline Management:</strong> Manage the entire sales funnel, ensuring high conversion rates at each stage.</p></li><li><p><strong>Product Demonstrations:</strong> Conduct persuasive presentations and product demos to C-level executives or decision-makers.</p></li><li><p><strong>Negotiation:</strong> Handle objections and negotiate contract terms to ensure \"win-win\" outcomes for both the client and the company.</p></li></ul><hr><h3>Core Skills &amp; Competencies</h3><h4>Sales Strategy &amp; Execution</h4><ol><li><p><strong>Prospecting &amp; Lead Sourcing:</strong> Expert at using LinkedIn Sales Navigator, ZoomInfo, or similar tools to find the right stakeholders.</p></li><li><p><strong>Consultative Selling:</strong> Ability to diagnose client pain points and position products as solutions rather than just features.</p></li><li><p><strong>Revenue Forecasting:</strong> Skilled in predicting monthly/quarterly sales outcomes with high accuracy.</p></li><li><p><strong>Closing Techniques:</strong> Proven ability to move prospects through the \"final mile\" of the decision-making process.</p></li></ol><h4>Technical &amp; Analytical Tools</h4><ol start=\"5\"><li><p><strong>CRM Proficiency:</strong> Advanced knowledge of <strong>Salesforce, Hubspot, or Pipedrive</strong> for lead tracking and reporting.</p></li><li><p><strong>Data Analysis:</strong> Ability to interpret sales metrics (conversion rates, CAC, LTV) to optimize personal performance.</p></li><li><p><strong>Tech Stack Mastery:</strong> Comfortable using tools like Slack, Trello, and Video Conferencing (Zoom/Teams) for remote selling.</p></li></ol><h4>Communication &amp; Relationship Building</h4><ol start=\"8\"><li><p><strong>Active Listening:</strong> The ability to let the customer talk and pick up on subtle cues that lead to sales opportunities.</p></li><li><p><strong>Public Speaking:</strong> Confidence in delivering pitches to large groups or high-stakes boardrooms.</p></li><li><p><strong>Written Communication:</strong> Crafting personalized, high-conversion email sequences and formal proposals.</p></li><li><p><strong>Emotional Intelligence (EQ):</strong> Reading the room and adapting communication styles to match different personality types.</p></li></ol><h4>Professional Attributes</h4><ol start=\"12\"><li><p><strong>Resilience &amp; Grit:</strong> The mental toughness to handle rejection and maintain high activity levels.</p></li><li><p><strong>Time Management:</strong> Balancing high-volume outreach with deep-dive research for key accounts.</p></li><li><p><strong>Strategic Networking:</strong> Building a \"referral engine\" through existing clients and industry partners.</p></li><li><p><strong>Competitive Awareness:</strong> Keeping a pulse on market trends and competitor moves to pivot sales pitches effectively.</p></li></ol><hr><h3>Preferred Qualifications</h3><ul><li><p>Bachelor’s degree in Business, Marketing, or a related field.</p></li><li><p>A documented history of over-achieving sales quotas (e.g., 110% of target).</p></li><li><p>Experience in [Specific Industry, e.g., Fintech/SaaS] is a major plus.</p></li></ul>";

        String body = "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"fromDocument\"\r\n\r\n" +
                "false\r\n" +
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"prompt\"\r\n\r\n" +
                prompt + "\r\n" +
                "--" + boundary + "\r\n" +
                "Content-Disposition: form-data; name=\"jobTitle\"\r\n\r\n" +
                "Sales Executive\r\n" +
                "--" + boundary + "--\r\n";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://core.vasitum.com/api/v1/job/parse/document"))
                .header("content-type", "multipart/form-data; boundary=" + boundary)
                .header("accept", "*/*")
                .header("x-maven-rest-auth-token", AUTH_TOKEN)
                .header("x-workspace-id", WORKSPACE_ID)
                .header("user-agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Raw JD Response: " + response.body());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Parse Document API failed: " + response.statusCode() + " " + response.body());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());
        JsonNode skillsNode = root.path("data").path("skills");

        // Handle if data or skills is missing, fallback to root level if structure
        // varies
        if (skillsNode.isMissingNode()) {
            skillsNode = root.path("skills");
        }

        Set<String> jdSkills = new HashSet<>();
        if (skillsNode.isArray()) {
            for (JsonNode skill : skillsNode) {
                jdSkills.add(skill.asText().toLowerCase());
            }
        }
        return jdSkills;
    }

    private JsonNode fetchApplicants() throws Exception {
        String payload = "{\"myApplicants\":false,\"jobIds\":[\"68778fe840b31644ebedb886\"],\"page\":1,\"pageSize\":50}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://core.vasitum.com/api/v1/myApplicants/all"))
                .header("content-type", "application/json")
                .header("accept", "*/*")
                .header("x-maven-rest-auth-token", AUTH_TOKEN)
                .header("x-workspace-id", WORKSPACE_ID)
                .header("user-agent", USER_AGENT)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Raw Applicants Response: " + response.body());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Fetch Applicants API failed: " + response.statusCode() + " " + response.body());
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.body());

        JsonNode candidates = root.path("data").path("candidates"); // Assumed structure from previous tests
        if (candidates.isMissingNode()) {
            candidates = root.path("candidates");
        }

        return candidates;
    }
}
