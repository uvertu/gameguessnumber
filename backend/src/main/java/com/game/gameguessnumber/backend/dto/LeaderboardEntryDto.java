package com.game.gameguessnumber.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "LeaderboardEntry")
public record LeaderboardEntryDto(
        @Schema(example = "1")
        int rank,
        @Schema(example = "3")
        int attempts,
        @Schema(example = "8500")
        long durationMs,
        @Schema(example = "2025-12-21T18:00:00Z")
        Instant playedAt
) {
}
