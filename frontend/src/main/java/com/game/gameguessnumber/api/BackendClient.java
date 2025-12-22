package com.game.gameguessnumber.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.gameguessnumber.api.dto.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Locale;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class BackendClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;

    public BackendClient() {
        this.baseUrl = loadBaseUrl();
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .build();
    }

    public CompletableFuture<StartGameResponse> startGame(Locale locale) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/v1/games"))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .header("Accept-Language", acceptLanguage(locale))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return sendJson(request, StartGameResponse.class);
    }

    public CompletableFuture<GuessResponse> makeGuess(UUID gameId, int guess, Locale locale) {
        GuessRequest payload = new GuessRequest(guess);
        String json;
        try {
            json = objectMapper.writeValueAsString(payload);
        } catch (IOException e) {
            CompletableFuture<GuessResponse> failed = new CompletableFuture<>();
            failed.completeExceptionally(e);
            return failed;
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/v1/games/" + gameId + "/guesses"))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Accept-Language", acceptLanguage(locale))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return sendJson(request, GuessResponse.class);
    }

    public CompletableFuture<LeaderboardResponse> getLeaderboard(int limit) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/api/v1/leaderboard?limit=" + limit))
                .timeout(Duration.ofSeconds(5))
                .header("Accept", "application/json")
                .GET()
                .build();

        return sendJson(request, LeaderboardResponse.class);
    }

    private <T> CompletableFuture<T> sendJson(HttpRequest request, Class<T> type) {
        return httpClient
                .sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenCompose(resp -> {
                    if (resp.statusCode() >= 200 && resp.statusCode() < 300) {
                        try {
                            T result = objectMapper.readValue(resp.body(), type);
                            return CompletableFuture.completedFuture(result);
                        } catch (IOException e) {
                            CompletableFuture<T> failed = new CompletableFuture<>();
                            failed.completeExceptionally(e);
                            return failed;
                        }
                    }
                    CompletableFuture<T> failed = new CompletableFuture<>();
                    failed.completeExceptionally(new IllegalStateException("Backend returned HTTP " + resp.statusCode() + ": " + resp.body()));
                    return failed;
                });
    }

    private static String acceptLanguage(Locale locale) {
        if (locale == null) {
            return "ru";
        }
        String tag = locale.toLanguageTag();
        if (tag == null || tag.isBlank()) {
            return "ru";
        }
        return tag;
    }

    private static String loadBaseUrl() {
        Properties props = new Properties();
        try (InputStream is = BackendClient.class.getResourceAsStream("/app.properties")) {
            if (is != null) {
                props.load(is);
            }
        } catch (IOException ignored) {
            // Fallback is used below.
        }
        return props.getProperty("backend.baseUrl", "http://localhost:8080");
    }
}
