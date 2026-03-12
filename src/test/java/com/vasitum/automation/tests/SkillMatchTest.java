package com.vasitum.automation.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class SkillMatchTest {

    private static final String AUTH_TOKEN = "6972efba8fc2a479d19bff6c";
    private static final String WORKSPACE_ID = "6732fdbd231fb33eed61e40e";
    private static final String JOB_ID = "6970971b8fc2a479d19bfac7";
    private static final String COMPANY_ID = "6732fdbd231fb33eed61e40e";

    private final Map<String, List<String>> synonymMap = new HashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client = HttpClient.newHttpClient();

    @BeforeClass
    public void setupSynonyms() {
        // Populating Synonyms based on User Request
        addSynonym("C Programming Language", "C programming skills", "C software development", "C coding");
        addSynonym("Java", "Core Java", "Java SE", "Java Programming", "Java development", "Java software engineering",
                "Java proficiency");
        addSynonym("HTML", "HyperText Markup Language", "Web Markup Language");
        addSynonym("CSS", "Cascading Style Sheets", "Styling language", "Front-end design", "HTML styling",
                "User interface design");
        addSynonym("SQL", "Structured Query Language", "Database Query Language", "Relational Database Language",
                "SQL programming");
        addSynonym("React", "ReactJS", "React framework", "React library");
        addSynonym("Angular", "Angular Framework", "AngularJS", "TypeScript", "Single Page Application development");
        addSynonym("Node.js", "Node", "nodejs");
        addSynonym("Python", "Python Programming", "Python Scripting", "Django", "Flask", "FastAPI", "NumPy", "Pandas",
                "TensorFlow", "PyTorch", "Tkinter", "PyQt", "Jupyter", "Virtualenv", "pip");
        addSynonym("Vue.js", "VueJS", "Vue.js framework", "Vue.js library");
        addSynonym("Ruby", "Ruby Programming Language", "Ruby on Rails", "Ruby development");
        addSynonym("Go", "Go Programming Language", "Golang");
        addSynonym("PHP", "Hypertext Preprocessor");
        addSynonym("TypeScript", "TS", "Superset");
        addSynonym("Statistical Computing", "R Programming");
        addSynonym("Scala", "Scala Programming", "JVM Language");
        addSynonym("Rust", "Systems Programming", "Rust Language");
        addSynonym("Dart", "Dart Programming", "Flutter Language");
        addSynonym("Shell Scripting", "Bash", "Shell Programming");
        addSynonym("Perl", "Perl Programming", "Scripting Language");
        addSynonym("Objective-C", "iOS Development", "Objective-C Programming");
        addSynonym("Android", "Android OS", "Android Development");
        addSynonym("iOS", "Apple iOS", "iOS App Development");
        addSynonym("Firebase", "Google Firebase", "Mobile Backend");
        addSynonym("MongoDB", "NoSQL Database", "MongoDB Storage");
        addSynonym("MySQL", "SQL Database", "Database MySQL");
        addSynonym("PostgreSQL", "Postgres Database", "PostgreSQL Server");
        addSynonym("Oracle", "Oracle Database", "Oracle SQL");
        addSynonym("Spring", "Java Spring Framework", "Java EE");
        addSynonym("Hibernate", "Java Hibernate", "ORM Framework");
        addSynonym("Express.js", "ExpressJS", "Node.js Framework");
        addSynonym("RESTful API", "REST API", "REST Web Services");
        addSynonym("GraphQL", "GraphQL API", "Query Language");
        addSynonym("Git", "Version Control", "Git SCM");
        addSynonym("Jenkins", "CI/CD Jenkins", "Jenkins Automation");
        addSynonym("Docker", "Containerization", "Docker Containers");
        addSynonym("Kubernetes", "Container Orchestration");
        addSynonym("AWS", "Amazon Web Services", "Cloud AWS");
        addSynonym("Azure", "Microsoft Azure", "Cloud Platform");
        addSynonym("Google Cloud", "GCP", "Cloud Computing");
        addSynonym("DevOps", "DevSecOps", "Development Operations");
        addSynonym("Machine Learning", "ML", "AI/ML");
        addSynonym("Deep Learning", "DL", "Neural Networks");
        addSynonym("Artificial Intelligence", "AI", "Machine Intelligence");
        addSynonym("NLP", "Natural Language Processing", "Text Analysis");
        addSynonym("Computer Vision", "CV", "Image Recognition");
        addSynonym("Reinforcement Learning", "RL", "AI Learning");
        addSynonym("Big Data", "Data Analytics", "Large-Scale Data");
        addSynonym("Hadoop", "Big Data Processing", "Apache Hadoop");
        addSynonym("Spark", "Apache Spark", "Data Processing");
        addSynonym("Flutter", "Dart Flutter", "Cross-Platform Framework");
        addSynonym("React Native", "Cross-Platform Mobile Development");
        addSynonym("TensorFlow", "TF", "TensorFlow Machine Learning");
        addSynonym("PyTorch", "PyTorch Framework", "PyTorch Deep Learning");
        addSynonym("Keras", "Keras API", "Keras Machine Learning");
        addSynonym("Tableau", "Tableau Software", "Power BI", "Microsoft Power BI");
        addSynonym("MATLAB", "MATLAB Programming", "Data Analysis");
        addSynonym("SAS", "SAS Programming", "Analytics Software");
        addSynonym("Apache Kafka", "Kafka Messaging", "Data Streaming");
        addSynonym("Elasticsearch", "Elastic Search Engine", "Data Search");
        addSynonym("Ansible", "IT Automation", "Configuration Management");
        addSynonym("Puppet", "Configuration Management", "Deployment Automation");
        addSynonym("Chef", "Infrastructure Automation", "DevOps");
        addSynonym("Terraform", "Infrastructure as Code", "Cloud Infrastructure");
        addSynonym("Apache Camel", "Camel Integration", "Message Routing");
        addSynonym("Airflow", "Apache Airflow", "Workflow Automation");
        addSynonym("Prometheus", "Monitoring", "Time Series Data");
        addSynonym("Splunk", "Data Analysis", "Monitoring Analytics");
        addSynonym("ELK Stack", "Elasticsearch Logstash Kibana");
        addSynonym("Neo4j", "Graph Database", "Data Modeling");
        addSynonym("Couchbase", "NoSQL Database", "Couchbase Storage");
        addSynonym("Redis", "In-Memory Database", "Data Cache");
        addSynonym("Cassandra", "NoSQL Database", "Data Management");
        addSynonym("HBase", "NoSQL Database", "Big Data Storage");
        // Duplicate GraphQL handled by map compute
        addSynonym("RabbitMQ", "Message Broker", "Messaging");
        addSynonym("ActiveMQ", "Apache Messaging", "Broker Server");
        addSynonym("Kafka Streams", "Streaming API", "Real-Time Processing");
        addSynonym("Flume", "Data Ingestion", "Analytics");
        addSynonym("Zookeeper", "Distributed Systems", "Coordination Service");
        addSynonym("Logstash", "Log Data Processing", "Data Pipelines");
        addSynonym("Spark Streaming", "Real-Time Processing");
        addSynonym("Apache Nifi", "Dataflow Automation", "Data Integration");
        addSynonym("Presto", "SQL Query Engine", "Data Analysis");
        addSynonym("Superset", "Data Visualization", "Dashboard BI");
        addSynonym("HDFS", "Hadoop Storage", "Distributed File System");
        addSynonym("Impala", "SQL Query Engine", "Big Data");
        addSynonym("Drill", "Data Query", "Big Data SQL");
        addSynonym("QlikView", "Data Visualization", "Analytics");
        addSynonym("MicroStrategy", "BI Analytics", "Reporting");
        addSynonym("Looker", "BI Analytics", "Data Visualization");
        addSynonym("Pentaho", "Data Integration", "Analytics");
        addSynonym("Jupyter", "Interactive Computing", "Data Science");
        addSynonym("VS Code", "IDE", "Development Editor");
        addSynonym("Eclipse", "IDE", "Java Development");
        addSynonym("NetBeans", "Java IDE", "Development Environment");
        addSynonym("IntelliJ IDEA", "Java IDE", "Development Environment");
        addSynonym("Xcode", "iOS Development IDE");
        addSynonym("Android Studio", "Android Development IDE");
        addSynonym("Apache Hive", "Data Warehouse", "Big Data");
        addSynonym("Spark SQL", "Data Query", "Structured Query");
        addSynonym("NumPy", "Numerical Computation", "Data Analysis");
        addSynonym("SciPy", "Scientific Computing", "Data Analysis");
        addSynonym("NLTK", "Text Processing", "NLP Toolkit");
        addSynonym("Spacy", "Natural Language Processing");
        addSynonym("OpenCV", "Computer Vision", "Image Processing");
        addSynonym("TensorBoard", "Model Visualization", "Deep Learning");
        addSynonym("Matplotlib", "Data Visualization", "Plotting Library");
        addSynonym("Plotly", "Graphing", "Visualization");
        addSynonym("Altair", "Data Visualization", "Charting");
        addSynonym("ggplot2", "Data Visualization", "R Library");
        addSynonym("d3.js", "Data Visualization");
        addSynonym("Highcharts", "Data Visualization");
        addSynonym("Three.js", "3D Graphics", "Visualization");
        addSynonym("Unity", "Game Development", "Unity3D");
        addSynonym("Unreal Engine", "Game Development", "VR");
        addSynonym("Blender", "3D Modeling", "Animation");
        addSynonym("Autodesk Maya", "3D Design", "Animation");
        addSynonym("Cinema 4D", "3D Modeling", "Animation");
        addSynonym("SketchUp", "3D Modeling, Design");
        addSynonym("AutoCAD", "CAD Software", "Design");
        addSynonym("SolidWorks", "CAD", "Engineering Design");
        addSynonym("Fusion 360", "CAD", "3D Modeling");
        addSynonym("Revit", "BIM", "Architecture Design");
        addSynonym("TensorFlow.js", "Web ML", "TensorFlow");
        addSynonym("ONNX", "Neural Network Exchange", "AI Models");
        addSynonym("OpenGL", "3D Graphics", "Rendering");
        addSynonym("Vulkan", "Graphics API", "3D Programming");
        addSynonym("DirectX", "Graphics API", "3D Development");
        addSynonym("OpenCL", "Parallel Computing", "GPU Programming");
        addSynonym("CUDA", "GPU Programming", "Parallel Computing");
        addSynonym("Houdini", "3D Animation", "Visual Effects");
        addSynonym("CryEngine", "Game Development", "3D Engine");
        addSynonym("xgboost", "Machine Learning", "Gradient Boosting");
        addSynonym("LightGBM", "Machine Learning", "Gradient Boosting");
        addSynonym("CatBoost", "Machine Learning", "Gradient Boosting");
        addSynonym("Theano", "Deep Learning", "Mathematical Library");
        addSynonym("CNTK", "Machine Learning", "Microsoft Toolkit");
        addSynonym("MxNet", "Deep Learning", "Apache Library");
        addSynonym("DLib", "Computer Vision", "Machine Learning");
        addSynonym("Project Management", "Planning", "Execution");
        addSynonym("Team Leadership", "Team Management");
        addSynonym("Communication Skills", "Verbal Communication", "Interpersonal Communication");
        addSynonym("Problem-Solving", "Analytical Skills", "Critical Thinking", "Creativity", "Innovation");
        addSynonym("Time Management", "Scheduling", "Efficiency");
        addSynonym("Customer Service", "Client Support", "Customer Relations");
        addSynonym("Sales Skills", "Sales Techniques", "Strategy");
        addSynonym("Marketing Skills", "Marketing Strategy", "Analysis");
        addSynonym("Negotiation Skills", "Deal Making", "Negotiation Tactics");
        addSynonym("Leadership", "Leadership Development");
        addSynonym("Public Speaking", "Presentation Skills");
        addSynonym("Adaptability", "Flexibility", "Resilience");
        addSynonym("Emotional Intelligence", "EQ", "Emotional Awareness");
        addSynonym("Conflict Resolution", "Dispute Management");
        addSynonym("DSA", "Data Structure and Algorithms", "data structure & algorithms");
    }

    private void addSynonym(String primary, String... aliases) {
        List<String> list = synonymMap.getOrDefault(primary.toLowerCase(), new ArrayList<>());
        list.add(primary.toLowerCase()); // Add self
        for (String alias : aliases) {
            list.add(alias.toLowerCase());
        }
        synonymMap.put(primary.toLowerCase(), list);
    }

    private String normalizeSkill(String skill) {
        String lowerSkill = skill.toLowerCase().trim();
        for (Map.Entry<String, List<String>> entry : synonymMap.entrySet()) {
            if (entry.getValue().contains(lowerSkill)) {
                return entry.getKey(); // Return Canonical Name
            }
        }
        return lowerSkill; // Return original if no synonym found
    }

    @Test
    public void validateSkillMatching() throws IOException, InterruptedException {
        System.out.println("--------------------------------------------------");
        System.out.println("SKILL MATCHING VALIDATION TEST");
        System.out.println("--------------------------------------------------");

        // 1. Fetch Job Details
        Set<String> jobSkills = fetchJobSkills();
        System.out.println("JOB REQUIREMENT SKILLS (" + jobSkills.size() + "): " + jobSkills);

        // 2. Fetch Candidate Recommendations
        JsonNode candidates = fetchCandidates();
        System.out.println("\nANALYZING CANDIDATES...");

        // 3. Analyze Each Candidate
        if (candidates.isArray()) {
            int count = 0;
            for (JsonNode candidate : candidates) {
                count++;
                String candidateId = candidate.path("candidateId").asText("Unknown");
                String name = candidate.path("firstName").asText("Candidate") + " "
                        + candidate.path("lastName").asText("");

                System.out.println("\n[" + count + "] Candidate: " + name + " (ID: " + candidateId + ")");

                Set<String> candidateSkills = new HashSet<>();
                JsonNode skillsNode = candidate.path("skills");
                // Note: User said 'total skill' in response, checking where it might be.
                // Adapting to look for "skills" array or "totalSkills" if user meant
                // aggregated.
                // Defaulting to "skills" array in candidate object based on usual structure,
                // but checking if user data showed "total skill" as a specific field or just
                // concept.
                // User example: [ "problem-solving", "java", ... ] - looks like a simple array.

                if (skillsNode.isArray()) {
                    for (JsonNode s : skillsNode) {
                        candidateSkills.add(normalizeSkill(s.asText()));
                    }
                } else {
                    // Try another field if 'skills' is empty or missing, e.g. "parsedSkills"
                    JsonNode parsedSkills = candidate.path("parsedSkills");
                    if (parsedSkills.isArray()) {
                        for (JsonNode s : parsedSkills) {
                            candidateSkills.add(normalizeSkill(s.asText()));
                        }
                    }
                }

                System.out.println("    Raw Skills Count: " + skillsNode.size());

                // 4. Compare
                List<String> matchedSkills = new ArrayList<>();
                List<String> jobMissingSkills = new ArrayList<>(); // Job skills candidate lacks
                List<String> candidateExtraSkills = new ArrayList<>(); // Candidate skills job doesn't need

                // Check matches against Job Requirements
                for (String jobSkill : jobSkills) {
                    if (candidateSkills.contains(jobSkill)) {
                        matchedSkills.add(jobSkill);
                    } else {
                        jobMissingSkills.add(jobSkill);
                    }
                }

                // Identify Candidate's Extra Skills
                for (String cSkill : candidateSkills) {
                    if (!jobSkills.contains(cSkill)) {
                        candidateExtraSkills.add(cSkill);
                    }
                }

                System.out
                        .println("    🔹 CANDIDATE TOTAL SKILLS (" + candidateSkills.size() + "): " + candidateSkills);
                System.out.println("    ✅ MATCHED (Candidate ∩ Job): " + matchedSkills);
                System.out.println("    ⚪ UNMATCHED (Candidate - Job): " + candidateExtraSkills);
                // System.out.println(" ❌ MISSING FROM JOB (Job - Candidate): " +
                // jobMissingSkills); // Optional, keeping for clarity if needed

                // Validation against expected baseline for all candidates
                String fullName = (candidate.path("firstName").asText() + " " + candidate.path("lastName").asText())
                        .toLowerCase();
                validateCandidate(fullName, candidateSkills, matchedSkills, candidateExtraSkills);
            }
        } else {
            Assert.fail("No candidates found in recommendation API response.");
        }
    }

    // Method to validate matched skills against known expected results
    private void validateCandidate(String fullName, Set<String> actualTotal, List<String> actualMatches,
            List<String> actualUnmatched) {
        Map<String, CandidateExpectation> expectedData = new HashMap<>();

        // Populating expected matches based on verified baseline from User JSON
        addExpectation(expectedData, "shrim garg",
                new String[] { "problem-solving", "java", "microsoft office 365", "google suite", "sql", "python",
                        "matlab", "arduino", "azure", "figma", "ssms", "technical proficiency", "analytical thinking",
                        "consultative selling", "client relationship management", "communication skills",
                        "collaboration", "adaptability" },
                new String[] { "problem-solving", "java" });
        addExpectation(expectedData, "varsha kumari",
                new String[] { "critical thinking", "basics of aws", "azure tsplus", "crm", "basics of c", "c++",
                        "html", "basic python", "basics of data science", "basics of networking",
                        "effective communication", "problem solving", "time management", "team handling",
                        "project management", "client relationship management" },
                new String[] { "critical thinking" });
        addExpectation(expectedData, "vishesh mishra",
                new String[] { "critical thinking", "aws solutions", "microsoft azure solutions", "devops solutions",
                        "networking products", "google cloud solutions", "linkedin sales navigator expert",
                        "salesforce", "zoho", "decision making", "leadership", "multi-tasking", "crm expertise",
                        "lead generation specialist", "sales management", "business development",
                        "business growth and revenue", "marketing strategy" },
                new String[] { "critical thinking" });
        addExpectation(expectedData, "vasundhara dubey",
                new String[] { "critical thinking", "creative problem solving", "interpersonal communication",
                        "adaptability", "time management", "leadership", "user experience design",
                        "user interface design", "user research", "design thinking", "wireframing", "prototyping",
                        "usability testing" },
                new String[] { "critical thinking" });
        addExpectation(expectedData, "aditya jain",
                new String[] { "creativity", "c++", "python", "javascript (react js, redux, typescript)", "html",
                        "css (bootstrap, tailwind, sass)", "graphql (rest api)", "git (github)",
                        "ai (machine learning, generative ai)", "sql (mysql)",
                        "database (firebase, supabase, appwrite)", "aws", "communication", "teamwork",
                        "problem solving", "time management", "leadership" },
                new String[] { "creativity" });
        addExpectation(expectedData, "raunak gupta",
                new String[] { "critical thinking", "c++", "python", "html", "css", "sql", "dax", "ms excel", "git",
                        "google colab", "jira", "tableau", "powerbi", "figma", "pandas", "numpy", "seaborn",
                        "matplotlib", "advanced excel", "data analysis", "business acumen", "market research",
                        "statistics" },
                new String[] { "critical thinking" });
        addExpectation(expectedData, "himanshu kulyal",
                new String[] { "critical thinking", "graphic design", "ui & ux designing", "ai-driven web hosting",
                        "project management", "public relations", "teamwork", "time management", "leadership",
                        "effective communication" },
                new String[] { "critical thinking" });
        addExpectation(expectedData, "anuj shukla",
                new String[] { "problem-solving", "user research", "wireframing & prototyping", "interaction design",
                        "visual design", "web design", "responsive design", "design thinking", "design system" },
                new String[] { "problem-solving" });
        addExpectation(expectedData, "maxwell puhlman",
                new String[] { "c++", "tomcat", "systems analysis & design", "platinum", "java", "apache", "gtb",
                        "endevor", "intertest", "rmo", "basic +", "changeman", "ir360", "ms/mf cobol", "lotus notes",
                        "html", "shadow services", "client/server architecture", "mainframe security",
                        "ims dl/i and db/dc", "vsam", "smarttest", "mq series", "software development",
                        "ibm tools (fm, debug, fa, eti)", "ms dos", "dyl280", "idms", "sdf",
                        "customer 1 (design/1 & install/1)", "roscoe", "os utilities", "ramis", "hod", "edit plus",
                        "ms office", "db2 client", "macss", "db2/sql v11", "dos/vse", "eztrieve", "cms", "testbase",
                        "fileaid", "qmf", "mainframe ftp", "problem-solving", "total", "jcl", "cics ts", "linux",
                        "tso/ispf", "legacy system integration", "continuous learning",
                        "nt,7, and 10, universal client", "sar", "librarian", "os/mvs", "extra multi-session",
                        "system troubleshooting", "spufi", "cobol", "leech ftp", "powerbuilder", "xpediter",
                        "team collaboration" },
                new String[] { "java", "software development", "problem-solving" });
        addExpectation(expectedData, "samriddh srivastava",
                new String[] { "trello", "osint and web scraping tools (octoparse and web scraper)", "redis",
                        "wireshark", "application security assurance", "conflict resolution", "java", "postman",
                        "looker", "google cloud", "attention to detail", "negotiation", "intellij ide", "mysql", "nmap",
                        "prompt engineering", "python", "management skills", "data analysis/analytics",
                        "documentation and presentation skills", "adaptability", "kali linux", "google sheets",
                        "cybersecurity", "industrial business knowledge and intelligence", "prioritization",
                        "creativity and innovation", "spring framework", "nikto", "chrome devtools", "decision-making",
                        "kafka", "data structures and algorithms (dsa)", "agile", "shodan", "ms office",
                        "customer-centric mindset", "interpersonal skills", "communication and stakeholder management",
                        "google forms", "problem-solving", "sql", "nessus", "google docs",
                        "research and analytical skills", "hql (hibernate)", "leadership", "quick learning",
                        "oracle virtual box", "chatgpt", "empathy", "burp suite", "consulting",
                        "data extraction, collection, and consolidation", "problem solving", "spring boot",
                        "strategic thinking", "compliance", "collaboration", "google slides",
                        "vapt (vulnerability assessment and penetration testing)" },
                new String[] { "java", "problem-solving" });
        addExpectation(expectedData, "akshay mishra",
                new String[] { "css", "c++", "time management", "android", "kotlin", "problem-solving", "javascript",
                        "java", "teamwork", "leadership", "html", "web development", "effective communication" },
                new String[] { "java", "problem-solving" });
        addExpectation(expectedData, "sakshi goenka",
                new String[] { "event management", "css", "react js", "anaconda", "problem-solving", "dart", "java",
                        "git", "web", "figma", "linux", "leadership", "html", "mysql", "communication", "c/c++",
                        "python", "github", "time management", "jupyter notebook", "adaptability", "windows",
                        "javascript", "visual studio", "team collaboration" },
                new String[] { "java", "problem-solving" });
        addExpectation(expectedData, "aditya aggarwal",
                new String[] { "problem-solving", "java", "c/c++", "amazon web services (aws)", "html", "css",
                        "bootstrap", "javascript", "jquery", "node.js", "react.js", "mysql", "rdbms",
                        "google ad services", "leadership", "communication skills", "analytical thinking",
                        "creativity & innovation" },
                new String[] { "problem-solving", "java" });
        addExpectation(expectedData, "hardik singhal",
                new String[] { "critical thinking", "java", "python", "c++", "javascript", "lua", "sql",
                        "machine learning", "agile development", "data structures and algorithm", "communication",
                        "teamwork", "versatility", "leadership" },
                new String[] { "critical thinking", "java" });
        addExpectation(expectedData, "adithya sriharshithadapaka",
                new String[] { "java", "creativity", "c++", "python", "sql", "html/css", "javascript", "tensorflow",
                        "scikit-learn", "keras", "opencv", "data structures and algorithms", "cloud computing",
                        "machine learning", "image & video processing", "teamwork", "effective communication",
                        "empathy", "versatility" },
                new String[] { "java", "creativity" });
        addExpectation(expectedData, "aditya sharma",
                new String[] { "critical thinking", "java", "creativity", "javascript", "python", "sql", "javafx",
                        "swing", "jdbc", "spring boot (beginner)", "mysql", "mongodb", "posgres", "superbase", "git",
                        "github", "postman", "jira", "oop", "data structures & algorithms", "rest apis",
                        "mvc architecture", "docker", "aws ec2/s3 (basics)", "ci/cd (github actions)",
                        "problem solving", "team collaboration", "adaptability", "time management", "communication",
                        "attention to detail", "leadership", "self-motivation" },
                new String[] { "critical thinking", "java", "creativity" });
        addExpectation(expectedData, "nikhil rana",
                new String[] { "python", "java", "nlp", "selenium", "scikit-learn", "looker", "beautifulsoup",
                        "data structures", "cntk", "opencv" },
                new String[] { "java", "selenium" });

        // Only validate if we have expected data for this candidate
        if (expectedData.containsKey(fullName)) {
            CandidateExpectation expectation = expectedData.get(fullName);

            Set<String> actTotal = new HashSet<>(actualTotal);
            Set<String> actMatch = new HashSet<>(actualMatches);
            Set<String> actUnmatch = new HashSet<>(actualUnmatched);

            boolean totalPass = actTotal.equals(expectation.total);
            boolean matchPass = actMatch.equals(expectation.matched);
            boolean unmatchPass = actUnmatch.equals(expectation.unmatched);

            if (totalPass && matchPass && unmatchPass) {
                System.out.println("    🌟 VALIDATION PASSED: " + fullName);
            } else {
                System.err.println("    ❌ VALIDATION FAILED: " + fullName);
                if (!totalPass) {
                    System.err.println("       Total Mismatch!");
                    System.err.println("         Exp: " + expectation.total);
                    System.err.println("         Act: " + actTotal);
                    // Find Diff
                    Set<String> missing = new HashSet<>(expectation.total);
                    missing.removeAll(actTotal);
                    Set<String> extra = new HashSet<>(actTotal);
                    extra.removeAll(expectation.total);
                    if (!missing.isEmpty())
                        System.err.println("         Missing: " + missing);
                    if (!extra.isEmpty())
                        System.err.println("         Extra: " + extra);
                }
                if (!matchPass) {
                    System.err.println("       Matched Mismatch!");
                    System.err.println("         Exp: " + expectation.matched);
                    System.err.println("         Act: " + actMatch);
                }
                if (!unmatchPass) {
                    System.err.println("       Unmatched Mismatch!");
                    System.err.println("         Exp: " + expectation.unmatched);
                    System.err.println("         Act: " + actUnmatch);
                }
            }
        }
    }

    private void addExpectation(Map<String, CandidateExpectation> map, String name, String[] total, String[] matched) {
        map.put(name, new CandidateExpectation(Set.of(total), Set.of(matched)));
    }

    private static class CandidateExpectation {
        public final Set<String> total;
        public final Set<String> matched;
        public final Set<String> unmatched;

        public CandidateExpectation(Set<String> total, Set<String> matched) {
            this.total = total;
            this.matched = matched;
            this.unmatched = new HashSet<>(total);
            this.unmatched.removeAll(matched);
        }
    }

    private Set<String> fetchJobSkills() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://core.vasitum.com/api/v1/jobdetail/" + JOB_ID))
                .header("accept", "*/*")
                .header("origin", "https://hire.vasitum.com")
                .header("referer", "https://hire.vasitum.com/")
                .header("x-maven-rest-auth-token", AUTH_TOKEN)
                .header("x-workspace-id", WORKSPACE_ID)
                .header("user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), 200, "Job Detail API Failed: " + response.body());

        JsonNode root = objectMapper.readTree(response.body());
        Set<String> skills = new HashSet<>();

        // Extract skills from 'job' object -> 'skills' array
        JsonNode jobNode = root.path("job");
        JsonNode skillsArray = jobNode.path("skills");

        if (skillsArray.isArray()) {
            for (JsonNode s : skillsArray) {
                // Skills are objects: {"name":"Java", ...}
                if (s.has("name")) {
                    skills.add(normalizeSkill(s.get("name").asText()));
                } else {
                    // Fallback if simple string
                    skills.add(normalizeSkill(s.asText()));
                }
            }
        }

        // Fallback or Legacy paths if empty
        if (skills.isEmpty()) {
            System.out.println("⚠ No skills found in 'job.skills'. Checking legacy paths...");
            JsonNode primary = root.path("primarySkills");
            if (primary.isArray()) {
                for (JsonNode s : primary)
                    skills.add(normalizeSkill(s.asText()));
            }
        }

        return skills;
    }

    private JsonNode fetchCandidates() throws IOException, InterruptedException {
        String jsonBody = "{\"companyId\":\"" + COMPANY_ID + "\",\"jobId\":\"" + JOB_ID
                + "\",\"limit\":\"20\",\"pageNo\":1,\"vasitumUser\":false}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://core.vasitum.com/api/v1/candidates/recommendations"))
                .header("content-type", "application/json")
                .header("accept", "*/*")
                .header("origin", "https://hire.vasitum.com")
                .header("referer", "https://hire.vasitum.com/")
                .header("x-maven-rest-auth-token", AUTH_TOKEN)
                .header("x-workspace-id", WORKSPACE_ID)
                .header("user-agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/143.0.0.0 Safari/537.36")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        Assert.assertEquals(response.statusCode(), 200, "Candidate Recommendation API Failed: " + response.body());

        JsonNode root = objectMapper.readTree(response.body());

        // Correct path found: "candidates"
        if (root.has("candidates") && root.get("candidates").isArray()) {
            return root.get("candidates");
        }

        // Fallbacks
        if (root.has("data") && root.get("data").isArray()) {
            return root.get("data");
        } else if (root.isArray()) {
            return root;
        }

        return objectMapper.createArrayNode();
    }
}
