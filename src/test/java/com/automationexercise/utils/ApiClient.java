package com.automationexercise.utils;

import com.automationexercise.models.User;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Talks to the Automation Exercise public API (https://automationexercise.com/api_list).
 *
 * Why use the API in UI tests?
 *  - Creating a user through the API takes 1 second instead of filling a long form.
 *  - Deleting the user after the test keeps the site clean.
 * The UI tests then focus only on the feature they are testing.
 *
 * Note: this API always returns HTTP 200. The real result is the "responseCode" inside the JSON body.
 */
public final class ApiClient {

    private static final HttpClient HTTP = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(20))
            .build();

    private static final Pattern RESPONSE_CODE = Pattern.compile("\"responseCode\"\\s*:\\s*(\\d+)");

    private ApiClient() {
    }

    /** Creates the user. Throws an error if the API does not answer with responseCode 201. */
    public static void createAccount(User user) {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("name", user.name());
        form.put("email", user.email());
        form.put("password", user.password());
        form.put("title", user.title());
        form.put("birth_date", user.birthDay());
        form.put("birth_month", user.birthMonth());
        form.put("birth_year", user.birthYear());
        form.put("firstname", user.firstName());
        form.put("lastname", user.lastName());
        form.put("company", user.company());
        form.put("address1", user.address1());
        form.put("address2", user.address2());
        form.put("country", user.country());
        form.put("zipcode", user.zipcode());
        form.put("state", user.state());
        form.put("city", user.city());
        form.put("mobile_number", user.mobileNumber());

        String body = send("POST", "/api/createAccount", form);
        int code = readResponseCode(body);
        if (code != 201) {
            throw new IllegalStateException("Could not create test user via API. Response: " + body);
        }
    }

    /** Deletes the user. Returns true when the API confirms the deletion. */
    public static boolean deleteAccount(String email, String password) {
        Map<String, String> form = new LinkedHashMap<>();
        form.put("email", email);
        form.put("password", password);

        String body = send("DELETE", "/api/deleteAccount", form);
        return readResponseCode(body) == 200;
    }

    // ---------------------------------------------------------------- helpers

    private static String send(String method, String path, Map<String, String> form) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(ConfigReader.getBaseUrl() + path))
                .timeout(Duration.ofSeconds(30))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .method(method, HttpRequest.BodyPublishers.ofString(toFormBody(form)))
                .build();
        try {
            return HTTP.send(request, HttpResponse.BodyHandlers.ofString()).body();
        } catch (Exception e) {
            throw new IllegalStateException("API call " + method + " " + path + " failed", e);
        }
    }

    /** Turns {"name": "QA Tester"} into "name=QA+Tester". */
    private static String toFormBody(Map<String, String> form) {
        return form.entrySet().stream()
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"));
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

    private static int readResponseCode(String body) {
        Matcher matcher = RESPONSE_CODE.matcher(body);
        return matcher.find() ? Integer.parseInt(matcher.group(1)) : -1;
    }
}
