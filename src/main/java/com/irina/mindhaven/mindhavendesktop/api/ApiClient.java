package com.irina.mindhaven.mindhavendesktop.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.irina.mindhaven.mindhavendesktop.MainApplication;
import com.irina.mindhaven.mindhavendesktop.activity.ActivityEvent;
import com.irina.mindhaven.mindhavendesktop.auth.AccountLockedException;
import com.irina.mindhaven.mindhavendesktop.log.LogDTO;
import com.irina.mindhaven.mindhavendesktop.rule.RuleDTO;
import com.irina.mindhaven.mindhavendesktop.rule.RuleDecision;
import com.irina.mindhaven.mindhavendesktop.user.UserDTO;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public ApiClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS)
                .cookieHandler(cookieManager).build();
    }

    // REGISTER + AUTH + LOGOUT
    public boolean register(String firstname, String lastname,
                            String email, String password,
                            String confirmPassword)
        throws IOException, InterruptedException {
        String form = "firstname=" + firstname +
                      "&lastname=" + lastname +
                      "&email=" + email +
                      "&password=" + password +
                      "&confirmPassword=" + confirmPassword;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/v1/auth/register"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //verifyRateLimit(response);
        return response.statusCode() == 200 || response.statusCode() == 302;
    }

    public boolean authenticate(String email, String password) throws IOException, InterruptedException {
        String form = "email=" + email + "&password=" + password;
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(BASE_URL + "/perform_login"))
                                        .header("Content-Type", "application/x-www-form-urlencoded")
                                        .POST(HttpRequest.BodyPublishers.ofString(form))
                                        .build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String finalUrl = response.uri().toString();
        if (finalUrl.contains("error=true"))
            return false;
        if (finalUrl.contains("lock=true")) {
            URI uri = response.uri();
            String query = uri.getQuery();
            long ttl = 0;
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] parts = param.split("=");

                    if (parts.length == 2 && parts[0].equals("ttl")) {
                        ttl = Long.parseLong(parts[1]);
                        break;
                    }
                }
            }
            throw new AccountLockedException(ttl);
        }
        //verifyRateLimit(response);
        return response.statusCode() == 200 || response.statusCode() == 302;
    }

    public void logout() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(BASE_URL + "/api/v1/auth/logout"))
                                        .POST(HttpRequest.BodyPublishers.noBody()).build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        System.out.println(response.body());
        //verifyRateLimit(response);
    }

    // PASSWORD
    public void forgotPassword(String email) throws IOException, InterruptedException {
        String form = "email=" + email;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/password/forgot-password"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //verifyRateLimit(response);
    }

    // USERS
    public List<UserDTO> getUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/users"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //verifyRateLimit(response);
        return mapper.readValue(response.body(),
                new TypeReference<List<UserDTO>>() {});
    }

    public UserDTO getCurrentUser() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/me"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //verifyRateLimit(response);
        return mapper.readValue(response.body(), UserDTO.class
        );
    }

    // LOGS
    public List<LogDTO> getLogs() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/logs"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        //verifyRateLimit(response);
        return mapper.readValue(response.body(),
                new TypeReference<List<LogDTO>>() {});
    }

    // ACTIVITY
    public RuleDecision sendActivity(ActivityEvent event) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String json = mapper.writeValueAsString(event);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(BASE_URL + "/api/activity"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),  RuleDecision.class);
        //verifyRateLimit(response);
    }

    // RULE
//    public List<RuleDTO> getRulesByUser(String userUuid) throws Exception {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(BASE_URL + "/rules/" + userUuid))
//                .GET().build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        if (response.statusCode() == 200)
//            return mapper.readValue(response.body(), new TypeReference<List<RuleDTO>>() {});
//        return new ArrayList<>();
//    }

    private void verifyRateLimit(HttpResponse<?> response) {
        try {
            if (response.statusCode() == 429) {
                System.out.println("RATE LIMIT CATCH");
                MainApplication.showRateLimit();
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
