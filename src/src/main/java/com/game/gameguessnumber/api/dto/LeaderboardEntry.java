package com.game.gameguessnumber.api.dto;

public record LeaderboardEntry(
        int rank,
        int attempts,
        long durationMs,
        String playedAt
) {
}
