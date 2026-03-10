package com.irina.mindhaven.mindhavendesktop.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.irina.mindhaven.mindhavendesktop.models.LogDTO;
import com.irina.mindhaven.mindhavendesktop.models.UserDTO;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URI;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.util.List;
public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080";
    private final HttpClient client;
    private final ObjectMapper mapper = new ObjectMapper();

    public ApiClient() {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        client = HttpClient.newBuilder().followRedirects(Redirect.ALWAYS)
                .cookieHandler(cookieManager).build();
    }

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
        if (finalUrl.contains("lock=true"))
            throw new RuntimeException("Account locked");
        return response.statusCode() == 200 || response.statusCode() == 302;
    }

    public String getDashboardData() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(BASE_URL + "/users/showall"))
                                        .GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        return response.body();
    }

    public void logout() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(BASE_URL + "/api/v1/auth/logout"))
                                        .POST(HttpRequest.BodyPublishers.noBody()).build();
        client.send(request, BodyHandlers.ofString());
    }

    public void forgotPassword(String email) throws IOException, InterruptedException {
        String form = "email=" + email;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/password/forgot-password"))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public List<UserDTO> getUsers() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/users"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),
                new TypeReference<List<UserDTO>>() {});
    }

    public List<LogDTO> getLogs() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/logs"))
                .GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return mapper.readValue(response.body(),
                new TypeReference<List<LogDTO>>() {});
    }
}
