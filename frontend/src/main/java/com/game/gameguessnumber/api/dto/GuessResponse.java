package com.game.gameguessnumber.api.dto;

import java.util.UUID;

public record GuessResponse(
        UUID gameId,
        GuessStatus status,
        int attempts,
        String message,
        Integer targetNumber,
        Long durationMs
) {
}
