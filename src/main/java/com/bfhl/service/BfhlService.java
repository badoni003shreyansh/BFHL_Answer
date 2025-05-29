package com.bfhl.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class BfhlService {

    public void execute() {
        RestTemplate restTemplate = new RestTemplate();
        String generateUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        // Prepare the request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("regNo", "REG12348"); // Even regNo for Question 2
        requestBody.put("email", "john@example.com");

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Call generate webhook endpoint
            ResponseEntity<Map> response = restTemplate.postForEntity(generateUrl, entity, Map.class);

            // Log response
            System.out.println("🔍 Response Status Code: " + response.getStatusCode());
            System.out.println("🔍 Response Body: " + response.getBody());

            // Validate and extract webhook URL and accessToken
            if (response.getBody() == null || !response.getBody().containsKey("webhook")) {
                throw new IllegalArgumentException("webhook is missing in the response");
            }

            Object rawWebhookUrl = response.getBody().get("webhook");
            String accessToken = (String) response.getBody().get("accessToken");

            if (rawWebhookUrl == null) {
                throw new IllegalArgumentException("webhook is null in the response");
            }

            String webhookUrl = rawWebhookUrl.toString().trim();

            // Handle relative URLs
            if (!webhookUrl.startsWith("http")) {
                webhookUrl = "https://bfhldevapigw.healthrx.co.in" + webhookUrl;
            }

            System.out.println("✅ Submitting to webhook URL: " + webhookUrl);

            // Prepare SQL query
            String finalQuery = "SELECT e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME, " +
                    "COUNT(e2.EMP_ID) AS YOUNGER_EMPLOYEES_COUNT " +
                    "FROM EMPLOYEE e1 " +
                    "JOIN DEPARTMENT d ON e1.DEPARTMENT = d.DEPARTMENT_ID " +
                    "LEFT JOIN EMPLOYEE e2 ON e1.DEPARTMENT = e2.DEPARTMENT AND e2.DOB > e1.DOB " +
                    "GROUP BY e1.EMP_ID, e1.FIRST_NAME, e1.LAST_NAME, d.DEPARTMENT_NAME " +
                    "ORDER BY e1.EMP_ID DESC";

            // Prepare request to webhook
            HttpHeaders postHeaders = new HttpHeaders();
            postHeaders.setContentType(MediaType.APPLICATION_JSON);
            postHeaders.set("Authorization", accessToken);

            Map<String, String> postBody = new HashMap<>();
            postBody.put("finalQuery", finalQuery);

            HttpEntity<Map<String, String>> postEntity = new HttpEntity<>(postBody, postHeaders);

            // Post the final query to the webhook
            ResponseEntity<String> webhookResponse = restTemplate.postForEntity(webhookUrl, postEntity, String.class);
            System.out.println("✅ Webhook Response: " + webhookResponse.getBody());

        } catch (Exception e) {
            System.err.println("❌ Error occurred:");
            e.printStackTrace();
        }
    }
}
